package org.dieschnittstelle.jee.esa.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.ProductCRUDRemote;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2015-02-01T17:07:31.355+01:00
 * Generated source version: 2.4.6
 * 
 */
@WebServiceClient(name = "ProductWebService", 
                  wsdlLocation = "http://localhost:8080/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/ProductWebService/ProductCRUDStateless?wsdl",
                  targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws") 
public class ProductWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://dieschnittstelle.org/jee/esa/jaxws", "ProductWebService");
    public final static QName ProductCRUDStatelessPort = new QName("http://dieschnittstelle.org/jee/esa/jaxws", "ProductCRUDStatelessPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/ProductWebService/ProductCRUDStateless?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ProductWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/org.dieschnittstelle.jee.esa.shared.ejbmodule.erp/ProductWebService/ProductCRUDStateless?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ProductWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ProductWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ProductWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ProductWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ProductWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ProductWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns ProductCRUDRemote
     */
    @WebEndpoint(name = "ProductCRUDStatelessPort")
    public ProductCRUDRemote getProductCRUDStatelessPort() {
        return super.getPort(ProductCRUDStatelessPort, ProductCRUDRemote.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ProductCRUDRemote
     */
    @WebEndpoint(name = "ProductCRUDStatelessPort")
    public ProductCRUDRemote getProductCRUDStatelessPort(WebServiceFeature... features) {
        return super.getPort(ProductCRUDStatelessPort, ProductCRUDRemote.class, features);
    }

}