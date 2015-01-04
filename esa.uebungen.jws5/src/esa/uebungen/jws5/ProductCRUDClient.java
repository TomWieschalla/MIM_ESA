package esa.uebungen.jws5;

import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import esa.uebungen.jws4.AbstractProduct;
import esa.uebungen.jws4.IndividualisedProductItem;
import esa.uebungen.jws4.ProductCRUDWebService;
import esa.uebungen.jws4.ProductCRUDWebService_Service;

public class ProductCRUDClient {
	
	ProductCRUDWebService_Service productService;
	
	ProductCRUDWebService operations;
	
	protected static Logger logger = Logger.getLogger(ProductCRUDClient.class);

	public ProductCRUDClient() throws Exception {

		/*
		 * create a client for the web service passing the interface
		 */
		productService = new ProductCRUDWebService_Service();
		operations = productService.getProductCRUDWebServicePort();
		
	}

	public AbstractProduct createProduct(IndividualisedProductItem prod) {
		AbstractProduct created = operations.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<?> readAllProducts() {
		return operations.readAllProducts().getItem();
	}

}
