package org.dieschnittstelle.jee.esa.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.dieschnittstelle.jee.esa.erp.entities.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.erp.entities.ProductType;

/*
UE JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDWebServiceImpl implements IProductCRUDWebService {
	
	protected static Logger logger = Logger
			.getLogger(ProductCRUDWebServiceImpl.class);
	
	@Context ServletContext context;
	
	@Override
	public AbstractProduct createProduct(
			AbstractProduct prod) {
		
		logger.info("doPost()");
		
		GenericCRUDExecutor<AbstractProduct> exec = (GenericCRUDExecutor<AbstractProduct>) context.getAttribute("touchpointCRUD");
		
		try {
			
			AbstractProduct prodItem = exec.createObject(prod);
			
			return prodItem;
			
		} catch (Exception e) {
			String err = "got exception in Post-Method: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		logger.info("doGetAll()");
		
		GenericCRUDExecutor<AbstractProduct> exec = (GenericCRUDExecutor<AbstractProduct>) context.getAttribute("touchpointCRUD");
		
		try {
			
			List<AbstractProduct> allAbstractProdItems = exec.readAllObjects();
			
			return allAbstractProdItems;
			
		} catch (Exception e) {
			String err = "got exception in GetAll-Method: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public AbstractProduct updateProduct(int id,
			AbstractProduct update) {
		logger.info("doUpdate()");
		
		GenericCRUDExecutor<AbstractProduct> exec = (GenericCRUDExecutor<AbstractProduct>) context.getAttribute("touchpointCRUD");
		
		try {
			
			AbstractProduct prodItem = exec.updateObject(update);
			
			return prodItem;
			
		} catch (Exception e) {
			String err = "got exception in Update-Method: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean deleteProduct(int id) {
		logger.info("doDelete()");
		
		GenericCRUDExecutor<AbstractProduct> exec = (GenericCRUDExecutor<AbstractProduct>) context.getAttribute("touchpointCRUD");
		
		try {
			
			boolean delete = exec.deleteObject(id);
			
			return delete;
			
		} catch (Exception e) {
			String err = "got exception in Delete-Method: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public AbstractProduct readProduct(int id) {
		logger.info("doGet()");
		
		GenericCRUDExecutor<AbstractProduct> exec = (GenericCRUDExecutor<AbstractProduct>) context.getAttribute("touchpointCRUD");
		
		try {
			
			AbstractProduct prodItem = exec.readObject(id);
			
			return prodItem;
			
		} catch (Exception e) {
			String err = "got exception in Get-Method: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}
	
}
