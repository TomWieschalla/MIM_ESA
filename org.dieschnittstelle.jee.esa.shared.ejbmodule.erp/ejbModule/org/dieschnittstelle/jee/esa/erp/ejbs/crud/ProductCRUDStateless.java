package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.jboss.logging.Logger;

@Stateless
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws", serviceName = "ProductWebService", endpointInterface = "org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ProductCRUDStateless  implements ProductCRUDRemote{

	protected static Logger logger = Logger.getLogger(PointOfSaleCRUDStateless.class);
	
	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;
	
	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		
		logger.info("createProduct(): before persist: " + prod);
		
		em.persist(prod);
		
		logger.info("createProduct(): after persist: " + prod);
		
		return prod;
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		
		logger.info("readAllProducts()");
		
		Query query = em.createQuery("FROM AbstractProduct");
		List<AbstractProduct> prods = (List<AbstractProduct>) query.getResultList();
		
		logger.info("readAllProducts(): " + prods);
		
		return prods;
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		
		logger.info("updateProduct(): before merge: " + update);
		
		update = em.merge(update);
		
		logger.info("updateProduct(): after merge: " + update);
		
		return update;
	}

	@Override
	public AbstractProduct readProduct(int productID) {
		
		logger.info("readProduct with ID: " + productID);
		
		AbstractProduct product = em.find(AbstractProduct.class, productID);
		
		logger.info("readProduct with ID: " + productID + ": " + product);
		
		return product;
	}

	@Override
	public boolean deleteProduct(int productID) {
		
		logger.info("deleteProduct with ID: " + productID);
		
		AbstractProduct product = em.find(AbstractProduct.class, productID);
		em.remove(product);
		
		logger.info("deleteProduct with ID: " + productID + ": " + product);
		
		return true;
	}

}
