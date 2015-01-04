package esa.uebungen.jws5;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import esa.uebungen.jws4.IndividualisedProductItem;
import esa.uebungen.jws4.ProductType;

public class TestProductCRUDService {
	
	private ProductCRUDClient client;
	//"Schrippe",	ProductType.ROLL, 720
	private IndividualisedProductItem prod1 = new IndividualisedProductItem();
	//"Kirschplunder",ProductType.PASTRY, 1080
	private IndividualisedProductItem prod2 = new IndividualisedProductItem();
	
	@Before
	public void prepareContext() throws Exception {
		client = new ProductCRUDClient();

	}
		
	@Test
	public void crudWorks() {
		List<?> prodlistBefore;
		// read all products
		assertNotNull("product list can be read",prodlistBefore = client.readAllProducts());
		
		/* CREATE + READ */
		// create two products
		
		prod1.setName("Schrippe");
		prod1.setProductType(ProductType.ROLL);
		prod1.setPrice(720);
		
		prod2.setName("Kirschplunder");
		prod2.setProductType(ProductType.PASTRY);
		prod2.setPrice(1080);
		
		client.createProduct(prod1);
		client.createProduct(prod2);
		
		assertEquals("product list is appended on create",2,client.readAllProducts().size()-prodlistBefore.size());					
			
	}

}
