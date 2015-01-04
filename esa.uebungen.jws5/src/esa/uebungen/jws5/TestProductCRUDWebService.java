package esa.uebungen.jws5;

import java.util.List;

import esa.uebungen.jws4.AbstractProduct;
import esa.uebungen.jws4.IndividualisedProductItem;
import esa.uebungen.jws4.ProductCRUDWebService;
import esa.uebungen.jws4.ProductCRUDWebService_Service;
import esa.uebungen.jws4.ProductType;

/*
 * UE JWS5: rufen Sie hier den in JWS4 implementierten Web Service auf.
 */
public class TestProductCRUDWebService {
	
	public static void main(String[] args) {
		
		try { 
			
		
		ProductCRUDWebService_Service productService = new ProductCRUDWebService_Service();
		
		ProductCRUDWebService operations = productService.getProductCRUDWebServicePort();
		
		// create two products and add them to the list of products
		IndividualisedProductItem prod1 = new IndividualisedProductItem();
		prod1.setName("Schrippe");
		prod1.setProductType(ProductType.ROLL);
		prod1.setPrice(720);
		
		IndividualisedProductItem prod2 = new IndividualisedProductItem();
		prod2.setName("Kirschplunder");
		prod2.setProductType(ProductType.PASTRY);
		prod2.setPrice(1080);
		
		/*
		 * initialisieren ein Service Interface fuer den in JWS4 erstellten Web Service
		 */		
		
		
		/*
		 * rufen Sie die im Interface deklarierte Methode fuer das Erzeugen von Produkten fuer prod1 und prod2 auf und geben Sie die Rueckgabewerte auf der Kosole aus.
		 */
		AbstractProduct newProd1 = operations.createProduct(prod1);
		System.out.println("Created new Abstractproduct: " + newProd1);
		AbstractProduct newProd2 = operations.createProduct(prod2);
		System.out.println("Created new Abstractproduct: " + newProd2);
		
		/*
		 * rufen Sie die im Interface deklarierte Methode fuer das Auslesen aller Produkte auf und geben Sie den Rueckgabewert auf der Konsole aus.
		 */

		List<AbstractProduct> list = operations.readAllProducts().getItem();
		System.out.println("All Products: " + list);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
}
