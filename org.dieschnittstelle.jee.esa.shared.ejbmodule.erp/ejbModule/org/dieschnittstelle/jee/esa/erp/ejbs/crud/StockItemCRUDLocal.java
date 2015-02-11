package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Local
public interface StockItemCRUDLocal {
	
	public StockItem createStockItem(StockItem stockitem);
	
	public List<StockItem> readAllStockItems();
	
	public StockItem readStockItem(AbstractProduct product,
			int pointOfSaleId);
	
	public StockItem updateStockItem(StockItem stockitem);
	
	public boolean removeStockItem(StockItem stockitem);
	
	public int getUnitsOnStock(int productId, int pointOfSaleId);

}
