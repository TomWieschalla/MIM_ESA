package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.StockItemCRUDLocal;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

@Singleton
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", serviceName = "StockSystemWebService", endpointInterface = "org.dieschnittstelle.jee.esa.erp.ejbs.StockSystemRemote")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class StockSystemSingleton implements StockSystemRemote, StockSystemLocal{

	protected static Logger logger = Logger.getLogger(StockSystemSingleton.class);
	
	@EJB
	private PointOfSaleCRUDLocal posCRUD;
	
	@EJB
	private StockItemCRUDLocal itemCRUD;
	
	public StockSystemSingleton() {
		logger.info("<constructor>: " + this);
	}
	
	@Override
	public StockItem addToStock(AbstractProduct product,
			int pointOfSaleId, int units) {
		logger.info("addtoStock() with product: " + product.getId() + " pointOfSaleId: " + pointOfSaleId + " units: " + units);
		
		PointOfSale point = posCRUD.readPointOfSale(pointOfSaleId);
		
		if (point != null) {
			StockItem item = itemCRUD.readStockItem(product,pointOfSaleId);
			if (item != null) {
				item.setUnits(units + item.getUnits());
				itemCRUD.updateStockItem(item);
				logger.info("addToStock() done");
				return item;
			} else {
				item = new StockItem(product, point, units);
				logger.info("addToStock() done");
				return itemCRUD.createStockItem(item);
			}

		} else {
			// not relevant (TODO)
			logger.info("addToStock() failed");
			return null;
		}
		
	}

//	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	@Override
	public boolean removeFromStock(AbstractProduct product,
			int pointOfSaleId, int units)  {
		logger.info("removeFromStock()");
		StockItem item = itemCRUD.readStockItem(product, pointOfSaleId);
		if (item != null) {
			if ((item.getUnits() - units) >= 0) {
				item.setUnits(item.getUnits() - units);
				itemCRUD.updateStockItem(item);
				logger.info("removeFromStock() done");
				return true;
			} else {
				itemCRUD.removeStockItem(item);
				logger.info("removeFromStock() done");
				return true;
			}
		}
		logger.info("removeFromStock() failed");
		return false;
	}

	@Override
	public List<AbstractProduct> getProductsOnStock(int pointOfSaleId) {
		logger.info("getProductsOnStock() with ID: "+pointOfSaleId);
		
		PointOfSale pointOfSale = posCRUD.readPointOfSale(pointOfSaleId);
		List<StockItem> items = itemCRUD.readAllStockItems();
		List<AbstractProduct> products = new ArrayList<AbstractProduct>();
		
		for (StockItem stockItem : items) {
			if (stockItem.getPos() == pointOfSale) {
				products.add((AbstractProduct)stockItem.getProduct());
			}
		}
		logger.info("getProductsOnStock(): "+products);
		return products;
	}

	@Override
	public List<AbstractProduct> getAllProductsOnStock() {
		logger.info("getAllProductsOnStock");
		List<StockItem> items = itemCRUD.readAllStockItems();
		List<AbstractProduct> products = new ArrayList<AbstractProduct>();
		
		for (StockItem stockItem : items) {
			products.add((AbstractProduct)stockItem.getProduct());
		}
		logger.info("getAllProductsOnStock(): "+products);
		return products;
	}

	@Override
	public int getUnitsOnStock(AbstractProduct product,
			int pointOfSaleId) {
			logger.info("getUnitsOnStock(): " + product);
			
			return itemCRUD.readStockItem(product, pointOfSaleId).getUnits();
	}

	@Override
	public int getTotalUnitsOnStock(AbstractProduct product) {
		logger.info("getTotalUnitsOnStock() with product: "+product);
		List<StockItem> items = itemCRUD.readAllStockItems();
		int result = 0;
		System.out.println("Items: "+items.size());
		for (StockItem stockItem : items) {
			if (stockItem.getProduct().getId() == product.getId()) {
				System.out.println("Units: "+stockItem.getUnits());
				result += stockItem.getUnits();
			}
		}
		logger.info("getTotalUnitsOnStock(): done");
		return result;
	}

	@Override
	public List<Integer> getPointsOfSale(AbstractProduct product) {
		logger.info("getPointsOfSale() with product: "+product);
		List<StockItem> items = itemCRUD.readAllStockItems();
		List<Integer> ids = new ArrayList<Integer>();
		
		for (StockItem stockItem : items) {
			if (stockItem.getProduct().getId() == product.getId()) {
				ids.add(stockItem.getPos().getId());
			}
		}
		logger.info("getPointsOfSale() done");
		return ids;
	}

	@Override
	public int getUnitsOnStockWithId(int productId, int pointOfSaleId) {
		return itemCRUD.getUnitsOnStock(productId, pointOfSaleId);
	}

	@Override
	public List<StockItem> getCompleteStock() {
		return itemCRUD.readAllStockItems();
	}

	@Override
	public StockItem updateSpecificStockItem(AbstractProduct product, int pointOfSaleId) {
		List<StockItem> items = itemCRUD.readAllStockItems();
		StockItem newItem = null;
		for (StockItem stockItem : items) {
			if (stockItem.getProduct().getId() == product.getId() && stockItem.getPos().getId() == pointOfSaleId) {
				stockItem.setProduct(product);
				newItem = stockItem;
				logger.info("UPDATESPECIFICSTOCKITEM " + stockItem.toString());
			}
		}
		return itemCRUD.updateStockItem(newItem);
	}

}
