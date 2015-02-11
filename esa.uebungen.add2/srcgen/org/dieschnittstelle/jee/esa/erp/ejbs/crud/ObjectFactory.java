
package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.dieschnittstelle.jee.esa.erp.ejbs.crud package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateProductResponse_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "updateProductResponse");
    private final static QName _DeleteProduct_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "deleteProduct");
    private final static QName _ReadAllProducts_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "readAllProducts");
    private final static QName _ReadAllProductsResponse_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "readAllProductsResponse");
    private final static QName _CreateProduct_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "createProduct");
    private final static QName _ReadProductResponse_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "readProductResponse");
    private final static QName _UpdateProduct_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "updateProduct");
    private final static QName _CreateProductResponse_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "createProductResponse");
    private final static QName _DeleteProductResponse_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "deleteProductResponse");
    private final static QName _ReadProduct_QNAME = new QName("http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", "readProduct");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.dieschnittstelle.jee.esa.erp.ejbs.crud
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateProductResponse }
     * 
     */
    public CreateProductResponse createCreateProductResponse() {
        return new CreateProductResponse();
    }

    /**
     * Create an instance of {@link DeleteProductResponse }
     * 
     */
    public DeleteProductResponse createDeleteProductResponse() {
        return new DeleteProductResponse();
    }

    /**
     * Create an instance of {@link ReadProduct }
     * 
     */
    public ReadProduct createReadProduct() {
        return new ReadProduct();
    }

    /**
     * Create an instance of {@link ReadProductResponse }
     * 
     */
    public ReadProductResponse createReadProductResponse() {
        return new ReadProductResponse();
    }

    /**
     * Create an instance of {@link UpdateProduct }
     * 
     */
    public UpdateProduct createUpdateProduct() {
        return new UpdateProduct();
    }

    /**
     * Create an instance of {@link CreateProduct }
     * 
     */
    public CreateProduct createCreateProduct() {
        return new CreateProduct();
    }

    /**
     * Create an instance of {@link ReadAllProductsResponse }
     * 
     */
    public ReadAllProductsResponse createReadAllProductsResponse() {
        return new ReadAllProductsResponse();
    }

    /**
     * Create an instance of {@link DeleteProduct }
     * 
     */
    public DeleteProduct createDeleteProduct() {
        return new DeleteProduct();
    }

    /**
     * Create an instance of {@link ReadAllProducts }
     * 
     */
    public ReadAllProducts createReadAllProducts() {
        return new ReadAllProducts();
    }

    /**
     * Create an instance of {@link UpdateProductResponse }
     * 
     */
    public UpdateProductResponse createUpdateProductResponse() {
        return new UpdateProductResponse();
    }

    /**
     * Create an instance of {@link IndividualisedProductItem }
     * 
     */
    public IndividualisedProductItem createIndividualisedProductItem() {
        return new IndividualisedProductItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "updateProductResponse")
    public JAXBElement<UpdateProductResponse> createUpdateProductResponse(UpdateProductResponse value) {
        return new JAXBElement<UpdateProductResponse>(_UpdateProductResponse_QNAME, UpdateProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "deleteProduct")
    public JAXBElement<DeleteProduct> createDeleteProduct(DeleteProduct value) {
        return new JAXBElement<DeleteProduct>(_DeleteProduct_QNAME, DeleteProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllProducts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "readAllProducts")
    public JAXBElement<ReadAllProducts> createReadAllProducts(ReadAllProducts value) {
        return new JAXBElement<ReadAllProducts>(_ReadAllProducts_QNAME, ReadAllProducts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllProductsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "readAllProductsResponse")
    public JAXBElement<ReadAllProductsResponse> createReadAllProductsResponse(ReadAllProductsResponse value) {
        return new JAXBElement<ReadAllProductsResponse>(_ReadAllProductsResponse_QNAME, ReadAllProductsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "createProduct")
    public JAXBElement<CreateProduct> createCreateProduct(CreateProduct value) {
        return new JAXBElement<CreateProduct>(_CreateProduct_QNAME, CreateProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "readProductResponse")
    public JAXBElement<ReadProductResponse> createReadProductResponse(ReadProductResponse value) {
        return new JAXBElement<ReadProductResponse>(_ReadProductResponse_QNAME, ReadProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "updateProduct")
    public JAXBElement<UpdateProduct> createUpdateProduct(UpdateProduct value) {
        return new JAXBElement<UpdateProduct>(_UpdateProduct_QNAME, UpdateProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "createProductResponse")
    public JAXBElement<CreateProductResponse> createCreateProductResponse(CreateProductResponse value) {
        return new JAXBElement<CreateProductResponse>(_CreateProductResponse_QNAME, CreateProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "deleteProductResponse")
    public JAXBElement<DeleteProductResponse> createDeleteProductResponse(DeleteProductResponse value) {
        return new JAXBElement<DeleteProductResponse>(_DeleteProductResponse_QNAME, DeleteProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://crud.ejbs.erp.esa.jee.dieschnittstelle.org/", name = "readProduct")
    public JAXBElement<ReadProduct> createReadProduct(ReadProduct value) {
        return new JAXBElement<ReadProduct>(_ReadProduct_QNAME, ReadProduct.class, null, value);
    }

}
