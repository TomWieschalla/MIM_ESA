package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.codehaus.jackson.io.MergedStream;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;
import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;
import org.jboss.logging.Logger;

@Stateless
public class StockItemCRUDStateless implements StockItemCRUDLocal{

	protected static Logger logger = Logger.getLogger(StockItemCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@EJB
	private PointOfSaleCRUDLocal posCRUD;
	
	@Override
	public StockItem createStockItem(StockItem stockItem) {
		logger.info("createStockItem() : "+ stockItem);
		stockItem.setProduct(em.merge(stockItem.getProduct()));
		em.persist(stockItem);
		return stockItem;
	}

	@Override
	public StockItem readStockItem(AbstractProduct product,
			int pointOfSaleId) {
		logger.info("getStockItem()");

		Query query = em.createQuery("SELECT a FROM StockItem a WHERE a.product =" + product.getId() + " AND a.pos =" + pointOfSaleId);
		List<StockItem> items = query.getResultList();
		if (items.size() > 0) {
			return items.get(0);
		}
		return null;
	}

	@Override
	public List<StockItem> readAllStockItems() {
		logger.info("getAllStockItems()");

		Query query = em.createQuery("FROM StockItem");
		List<StockItem> items = query.getResultList();
		
		return items;
	}

	@Override
	public boolean removeStockItem(StockItem stockitem) {
		logger.info("removeStockItem()");
		em.remove(stockitem);
		return true;
	}

	@Override
	public StockItem updateStockItem(StockItem stockitem) {
		logger.info("updateStockItem()");
		em.merge(stockitem);
		return stockitem;
	}

	@Override
	public int getUnitsOnStock(int productId, int pointOfSaleId) {
		logger.info("getUnitsOnStock() with ID: "+productId);

		Query query = em.createQuery("SELECT a FROM StockItem a WHERE a.product =" + productId + " AND a.pos =" + pointOfSaleId);
		List<StockItem> items = query.getResultList();
		if (items.size() > 0) {
			logger.info("getUnitsOnStock() RESULT: "+items.get(0).getProduct().getId());
			return items.get(0).getProduct().getId();
		}
		logger.info("getUnitsOnStock() NO RESULT");
		return 0;
	}

}
