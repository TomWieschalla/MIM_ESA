package org.dieschnittstelle.jee.esa.basics;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.ws.spi.Invoker;

import org.dieschnittstelle.jee.esa.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.jee.esa.basics.annotations.DisplayAs;
import org.dieschnittstelle.jee.esa.basics.annotations.StockItemProxyImpl;

public class ShowAnnotations {

	public static void main(String[] args) {
		// we initialise the collection
		StockItemCollection collection = new StockItemCollection(
				"stockitems_annotations.xml", new AnnotatedStockItemBuilder());
		// we load the contents into the collection
		collection.load();

		for (IStockItem consumable : collection.getStockItems()) {
			;
			showAttributes(((StockItemProxyImpl)consumable).getProxiedObject());
		}

		// we initialise a consumer
		Consumer consumer = new Consumer();
		// ... and let them consume
		consumer.doShopping(collection.getStockItems());
	}

	/*
	 * UE BAS2 
	 */
	private static void showAttributes(Object consumable) {
		try {
			
			Class<?> klass = consumable.getClass();
			
			String name = "";
			String result = "";
			String value = "";
			
			for(Field field : klass.getDeclaredFields()){

				for(Annotation anno : field.getDeclaredAnnotations()){
					if(anno instanceof DisplayAs){
						DisplayAs displayAnnotation = (DisplayAs) anno;
						name = displayAnnotation.param1();
					} else {
						name = klass.getSimpleName();
					}
				}
				
				if(field.getType() == java.lang.String.class) {
					for(Method method : klass.getDeclaredMethods()){
						if (method.getReturnType() == java.lang.String.class && method.getName().substring(0, 3).equals("get")){
							value = "" + method.invoke(consumable, null);
						}
					}
				} else if (field.getType() == Integer.TYPE) {
					for(Method method : klass.getDeclaredMethods()){
						if(method.getReturnType() == Integer.TYPE && method.getName().substring(0, 3).equals("get")){ 
							value = "" + method.invoke(consumable, null);
						}
					}
				}
				result = result + " " + field.getName() + ":" + value;
			}
			
			result = name + " " +  result;
			System.out.println(result);
			

			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
