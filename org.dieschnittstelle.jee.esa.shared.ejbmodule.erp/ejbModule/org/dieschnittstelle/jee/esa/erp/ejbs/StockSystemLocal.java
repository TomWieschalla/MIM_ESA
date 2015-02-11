package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.List;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Local
public interface StockSystemLocal {

	public StockItem addToStock(AbstractProduct product,int pointOfSaleId,int units);

	public boolean removeFromStock(AbstractProduct product,int pointOfSaleId,int units);
	
	public List<AbstractProduct> getProductsOnStock(int pointOfSaleId);

	public List<AbstractProduct> getAllProductsOnStock();

	public int getUnitsOnStock(AbstractProduct product, int pointOfSaleId);
	
	public int getUnitsOnStockWithId(int productId, int pointOfSaleId);

	public int getTotalUnitsOnStock(AbstractProduct product);

	public List<Integer> getPointsOfSale(AbstractProduct product);
	
	public List<StockItem> getCompleteStock();
	
	public StockItem updateSpecificStockItem(AbstractProduct product, int pointOfSaleId);

}
