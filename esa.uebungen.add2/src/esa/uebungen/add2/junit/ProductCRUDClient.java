package esa.uebungen.add2.junit;

import java.util.List;

import org.dieschnittstelle.jee.esa.erp.ejbs.crud.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote;
import org.dieschnittstelle.jee.esa.jaxws.ProductWebService;



public class ProductCRUDClient implements ProductCRUDRemote {

	private ProductWebService productService;
	private ProductCRUDRemote proxy;

	public ProductCRUDClient() throws Exception {
		productService = new ProductWebService();
		proxy = productService.getProductCRUDStatelessPort();
	}

	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		AbstractProduct created = proxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	@Override
	public boolean deleteProduct(int arg0) {
		return proxy.deleteProduct(arg0);
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct arg0) {
		return proxy.updateProduct(arg0);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return proxy.readAllProducts();
	}

	@Override
	public AbstractProduct readProduct(int arg0) {
		return proxy.readProduct(arg0);
	}


}
