package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

public class ProductCRUDClient implements ProductCRUDRemote {

	private ProductCRUDRemote proxy;

	public ProductCRUDClient() throws Exception {
		// obtain the beans using a jndi context
		Context context = new InitialContext();
		proxy = (ProductCRUDRemote) context
				.lookup(Constants.PRODUCT_BEAN);
	}

	public AbstractProduct createProduct(AbstractProduct prod) {
		
		AbstractProduct created = proxy.createProduct(prod);
		prod.setId(created.getId());
		
		return created;

	}

	public List<AbstractProduct> readAllProducts() {
		return proxy.readAllProducts();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return proxy.updateProduct(update);
	}

	public AbstractProduct readProduct(int productID) {
		return proxy.readProduct(productID);
	}

	public boolean deleteProduct(int productID) {
		return proxy.deleteProduct(productID);
	}

}
