package org.dieschnittstelle.jee.esa.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;

import org.jboss.logging.Logger;
import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.CampaignTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.CustomerTrackingRemote;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartLocal;
import org.dieschnittstelle.jee.esa.crm.ejbs.ShoppingCartRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.CrmProductBundle;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.crm.entities.CustomerTransaction;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.Campaign;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductBundle;

@Stateful
@StatefulTimeout(value=10, unit=TimeUnit.MINUTES)
public class ShoppingSessionFacadeStateful implements
		ShoppingSessionFacadeRemote, ShoppingSessionFacadeLocal {

	protected static Logger logger = Logger
			.getLogger(ShoppingSessionFacadeStateful.class);

	@EJB
	private ShoppingCartLocal shoppingCart;

	@EJB
	private CustomerTrackingLocal customerTracking;

	@EJB
	private CampaignTrackingLocal campaignTracking;

	@EJB
	private StockSystemLocal stockSystem;
	
	@EJB
	private ProductCRUDRemote productCRUD;

	/**
	 * the customer
	 */
	private Customer customer;

	/**
	 * the touchpoint
	 */
	private AbstractTouchpoint touchpoint;

	private boolean isCompleted;

	public ShoppingSessionFacadeStateful() {
		logger.info("<constructor>");
		isCompleted = false;
	}

	@Override
	public void setTouchpoint(AbstractTouchpoint touchpoint) {
		this.touchpoint = touchpoint;
	}

	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void addProduct(AbstractProduct product, int units) {
		this.shoppingCart.addProductBundle(new CrmProductBundle(
				product.getId(), units, product instanceof Campaign));
	}

	/*
	 * verify whether campaigns are still valid
	 */
	@Override
	public void verifyCampaigns() {
		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot verify campaigns! No touchpoint has been set!");
		}

		for (CrmProductBundle productBundle : this.shoppingCart
				.getProductBundles()) {
			if (productBundle.isCampaign()) {
				int availableCampaigns = this.campaignTracking
						.existsValidCampaignExecutionAtTouchpoint(
								productBundle.getErpProductId(),
								this.touchpoint);
				logger.info("got available campaigns for product "
						+ productBundle.getErpProductId() + ": "
						+ availableCampaigns);
				// we check whether we have sufficient campaign items available
				if (availableCampaigns < productBundle.getUnits()) {
					throw new RuntimeException(
							"verifyCampaigns() failed for productBundle "
									+ productBundle + " at touchpoint "
									+ this.touchpoint + "! Need "
									+ productBundle.getUnits()
									+ " instances of campaign, but only got: "
									+ availableCampaigns);
				}
			}
		}
	}

	@Override
	public void purchase() {
		logger.info("purchase()");

		if (this.customer == null || this.touchpoint == null) {
			throw new RuntimeException(
					"cannot commit shopping session! Either customer or touchpoint has not been set: "
							+ this.customer + "/" + this.touchpoint);
		}

		// verify the campaigns
		verifyCampaigns();

		// read out the products from the cart
		List<CrmProductBundle> products = this.shoppingCart.getProductBundles();
		List<AbstractProduct> productsOnStock = stockSystem
				.getProductsOnStock(touchpoint.getErpPointOfSaleId());
		logger.info("AllproductOnStock: " + productsOnStock);

		// iterate over the products and purchase the campaigns
		for (CrmProductBundle productBundle : products) {

			
			if (!productBundle.isCampaign()) {
				AbstractProduct prod = null;
				for (AbstractProduct abstractProduct : productsOnStock) {
					if (productBundle.getErpProductId() == abstractProduct.getId()) {
						prod = abstractProduct;
						break;
					}
				}
				int units = stockSystem.getUnitsOnStockWithId(
						productBundle.getErpProductId(),
						touchpoint.getErpPointOfSaleId());
				if (units >= productBundle.getUnits()) {
					System.out.println("REICHT AUS");
					if (prod != null) {
						stockSystem.removeFromStock(prod,
								this.touchpoint.getErpPointOfSaleId(),
								productBundle.getUnits());
					}
				}
			} else {
				AbstractProduct prod =productCRUD.readProduct(productBundle.getErpProductId());
				
				this.campaignTracking.purchaseCampaignAtTouchpoint(
						productBundle.getErpProductId(), this.touchpoint,
						productBundle.getUnits());

				logger.info("get productBundle: " + productBundle);
				logger.info("get campaign: "
						+ ((Campaign) productBundle.getProductObj()));

				for (ProductBundle bundle : ((Campaign) prod).getBundles()) {
					IndividualisedProductItem currentCampaignprod = bundle
							.getProduct();
					stockSystem.removeFromStock(currentCampaignprod,
							this.touchpoint.getErpPointOfSaleId(),
							bundle.getUnits());
				}
			}

		}

		// then we add a new customer transaction for the current purchase
		CustomerTransaction transaction = new CustomerTransaction(
				this.customer, this.touchpoint, products);
		transaction.setCompleted(true);

		customerTracking.createTransaction(transaction);
		isCompleted = true;

		logger.info("purchase(): done.\n");
	}

	@PreDestroy
	public void preDestroy() {
		if (!isCompleted) {
			// then we add a new customer transaction for the current purchase
			CustomerTransaction transaction = new CustomerTransaction(
					this.customer, this.touchpoint,
					this.shoppingCart.getProductBundles());
			transaction.setCompleted(true);

			customerTracking.createTransaction(transaction);
			isCompleted = true;
		}
	}

}
