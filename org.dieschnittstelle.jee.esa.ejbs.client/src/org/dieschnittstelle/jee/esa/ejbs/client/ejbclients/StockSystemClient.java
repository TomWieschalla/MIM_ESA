package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

public class StockSystemClient implements StockSystemRemote {

	private StockSystemRemote proxy;
	
	public StockSystemClient() throws Exception {
		Context context = new InitialContext();
		
		this.proxy = (StockSystemRemote) context
				.lookup(Constants.STOCK_SYSTEM_BEAN);
	}
	
	
	@Override
	public StockItem addToStock(AbstractProduct product, int pointOfSaleId, int units) {
		return this.proxy.addToStock(product, pointOfSaleId, units);
	}

	@Override
	public boolean removeFromStock(AbstractProduct product, int pointOfSaleId,
			int units) {
		return this.proxy.removeFromStock(product, pointOfSaleId, units);
	}

	@Override
	public List<AbstractProduct> getProductsOnStock(int pointOfSaleId) {
		return this.proxy.getProductsOnStock(pointOfSaleId);
	}

	@Override
	public List<AbstractProduct> getAllProductsOnStock() {
		return this.proxy.getAllProductsOnStock();
	}

	@Override
	public int getUnitsOnStock(AbstractProduct product, int pointOfSaleId) {
		return this.proxy.getUnitsOnStock(product, pointOfSaleId);
	}

	@Override
	public int getTotalUnitsOnStock(AbstractProduct product) {
		return this.proxy.getTotalUnitsOnStock(product);
	}

	@Override
	public List<Integer> getPointsOfSale(AbstractProduct product) {
		return this.proxy.getPointsOfSale(product);
	}


	@Override
	public int getUnitsOnStock(int productId, int pointOfSaleId) {
		return proxy.getUnitsOnStock(productId, pointOfSaleId);
	}


}
