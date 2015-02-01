package org.dieschnittstelle.jee.esa.erp.ejbs;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.StockItem;

@Remote
@WebService
@Path("/stock")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface StockSystemRemote {
	
		@POST
		public StockItem addToStock(AbstractProduct product,int pointOfSaleId,int units);

		@DELETE
		public boolean removeFromStock(AbstractProduct product,int pointOfSaleId,int units);
		
		@GET
		public List<AbstractProduct> getProductsOnStock(int pointOfSaleId);

		@GET
		public List<AbstractProduct> getAllProductsOnStock();

		@GET
		public int getUnitsOnStock(AbstractProduct product, int pointOfSaleId);

		@GET
		public int getUnitsOnStockWithId(int productId, int pointOfSaleId);
				
		@GET
		public int getTotalUnitsOnStock(AbstractProduct product);
		
		@GET
		public List<Integer> getPointsOfSale(AbstractProduct product);

}
