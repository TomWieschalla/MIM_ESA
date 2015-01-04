package org.dieschnittstelle.jee.esa.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.6
 * 2014-11-18T16:35:46.527+01:00
 * Generated source version: 2.4.6
 * 
 */
@WebServiceClient(name = "TouchpointCRUDWebService", 
                  wsdlLocation = "http://localhost:8080/org.dieschnittstelle.jee.esa.jaxws/TouchpointCRUDWebService?wsdl",
                  targetNamespace = "http://dieschnittstelle.org/jee/esa/jaxws") 
public class TouchpointCRUDWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://dieschnittstelle.org/jee/esa/jaxws", "TouchpointCRUDWebService");
    public final static QName TouchpointCRUDWebServiceSOAPPort = new QName("http://dieschnittstelle.org/jee/esa/jaxws", "TouchpointCRUDWebServiceSOAPPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/org.dieschnittstelle.jee.esa.jaxws/TouchpointCRUDWebService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TouchpointCRUDWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/org.dieschnittstelle.jee.esa.jaxws/TouchpointCRUDWebService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TouchpointCRUDWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TouchpointCRUDWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TouchpointCRUDWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public TouchpointCRUDWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public TouchpointCRUDWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public TouchpointCRUDWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns TouchpointCRUDWebServiceSOAP
     */
    @WebEndpoint(name = "TouchpointCRUDWebServiceSOAPPort")
    public TouchpointCRUDWebServiceSOAP getTouchpointCRUDWebServiceSOAPPort() {
        return super.getPort(TouchpointCRUDWebServiceSOAPPort, TouchpointCRUDWebServiceSOAP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TouchpointCRUDWebServiceSOAP
     */
    @WebEndpoint(name = "TouchpointCRUDWebServiceSOAPPort")
    public TouchpointCRUDWebServiceSOAP getTouchpointCRUDWebServiceSOAPPort(WebServiceFeature... features) {
        return super.getPort(TouchpointCRUDWebServiceSOAPPort, TouchpointCRUDWebServiceSOAP.class, features);
    }

}
