package esa.uebungen.jws4;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;
import org.jboss.logging.Logger;

/*
 * UE JWS4: machen Sie die Funktionalitaet dieser Klasse als Web Service verfuegbar und verwenden Sie fuer 
 * die Umetzung der beiden Methoden die Instanz von GenericCRUDExecutor<AbstractProduct>, 
 * die Sie aus dem ServletContext auslesen koennen
 */

@WebService(targetNamespace = "http://uebungen.esa/jws4", serviceName = "ProductCRUDWebService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class ProductCRUDWebService {
	
	protected static Logger logger = Logger
			.getLogger(ProductCRUDWebService.class);

	@Resource
	private WebServiceContext wscontext;
	
	@PostConstruct
	@WebMethod(exclude = true)
	public void initialiseContext() {
		logger.info("@PostConstruct: the wscontext is: " + wscontext);

	}
	
	@WebMethod
	public List<AbstractProduct> readAllProducts() {
		logger.info("readAllProducts()");

		logger.info("readAllProducts(): I am: " + this);
		
		// we obtain the servlet context from the wscontext
		ServletContext ctx = (ServletContext) wscontext.getMessageContext()
				.get(MessageContext.SERVLET_CONTEXT);
		logger.info("readAllProducts(): servlet context is: " + ctx);
		
		// we also read out the http request
		HttpServletRequest httpRequest = (HttpServletRequest) wscontext
				.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		logger.info("readAllProducts(): servlet request is: " + httpRequest);

		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ctx
				.getAttribute("productCRUD");
		logger.info("readAllProducts(): read productCRUD from servletContext: "
				+ productCRUD);

		return productCRUD.readAllObjects();
	}

	@WebMethod
	public AbstractProduct createProduct(AbstractProduct product) {				
		// obtain the CRUD executor from the servlet context
		GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) wscontext
				.getMessageContext().get(MessageContext.SERVLET_CONTEXT))
				.getAttribute("productCRUD");

		return (AbstractProduct) productCRUD
				.createObject(product);
	}
	
}
