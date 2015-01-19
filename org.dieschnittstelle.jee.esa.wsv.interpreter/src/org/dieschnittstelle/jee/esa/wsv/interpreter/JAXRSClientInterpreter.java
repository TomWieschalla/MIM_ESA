package org.dieschnittstelle.jee.esa.wsv.interpreter;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.deser.FromStringDeserializer;
import org.dieschnittstelle.jee.esa.wsv.interpreter.json.JSONObjectSerialiser;
import org.dieschnittstelle.jee.esa.wsv.ITouchpointCRUDWebService;

/*
 * TODO: implement this class such that all crud operations declared on ITouchpointCRUDWebService in 
 * .esa.wsv can be successfully called from the class UseJAXRSClientInterpreter in the .esa.wsv.client project
 */
public class JAXRSClientInterpreter implements InvocationHandler {

	// declare a baseurl
	private final String baseurl;

	// declare a common path segment
	private final String commonPath;
	
	private final Class<?> serviceInterface;

	// use a JSONObjectSerialiser
	JSONObjectSerialiser serializer = new JSONObjectSerialiser();

	// use an attribute that holds the serviceInterface for providing a
	// toString() method
	protected static Logger logger = Logger
			.getLogger(JAXRSClientInterpreter.class);	

	// only for demo, later: use a constructor that only takes the
	// serviceInterface and reads
	// the baseurl from the DefaultBaseUrl annotation - this is only done for
	// demoing how annotation types may be declared!

	// use a constructor that takes an annotated service interface and a baseurl
	// the implementation should read out the path annotation, we assume we
	// produce and consume
	// json, i.e. these annotations will not be considered here
	public JAXRSClientInterpreter(Class<?> serviceInterface, String baseurl){
		this.baseurl = baseurl;
		this.serviceInterface = serviceInterface;
		this.commonPath = serviceInterface.getAnnotation(Path.class).value();
	}

	// implement this method
	@Override
	public Object invoke(Object proxy, Method meth, Object[] args)
			throws Throwable {
		logger.debug("<< invoke start >>");
		logger.debug("method =" + meth);
		logger.debug("args = " + Arrays.toString(args));
		
		if (meth.getName() == "toString") {
			logger.debug("<< invoke end >>");
			return serviceInterface.toString();
		}
		
		// use a default http client
		HttpClient defaultClient = new DefaultHttpClient();
		
		// argsList for requestBody
		ArrayList<Object> argsList = new ArrayList<Object>();
		
		// create the url using baseurl and commonpath
		StringBuilder url = new StringBuilder();
		url.append(baseurl);
		url.append(commonPath);
		logger.debug("url = " + url.toString());
		
		// check whether we have a path annotation and append the url (path
		// params will be handled when looking at the method arguments
		final Method method = serviceInterface.getDeclaredMethod(meth.getName(), meth.getParameterTypes());
		
		if (args != null){
				if(method.getAnnotation(Path.class) != null){
					Annotation[][] parameterAnnotations = method.getParameterAnnotations();
					for (int j = 0; j < parameterAnnotations.length; j++) {
						Annotation[] preAnnotation = parameterAnnotations[j];
						if(preAnnotation.length > 0){
							for (int i = 0; i < parameterAnnotations[j].length; i++) {
								Annotation obj = parameterAnnotations[j][i];
								if (obj.annotationType() == PathParam.class) {
									url.append("/");
									url.append(args[i]);
									logger.debug("Method with PathParam = " + url.toString());
								} else {
									argsList.add(args[i]);
								}	
							}
						} else {
							argsList.add(args[j]);
						}
					}
				} else {
					for (Object object : args) {
						argsList.add(object);
					}
				}
		}
		
		// declare a variable for the entity / body
		HttpEntity entity = null;
		
		// check which of the http method annotation is present and
		// instantiate request accordingly passing the url
		if (method.getAnnotation(GET.class) != null){
			logger.debug("GET-Method");
			HttpGet request = new HttpGet(url.toString());
			request.setHeader("content-type", "application/json");
			HttpResponse response = defaultClient.execute(request);
			try {
				entity = response.getEntity();
				logger.debug("Status Code: " + response.getStatusLine().getStatusCode());
				if(entity != null) {
					JSONObjectSerialiser serializer = new JSONObjectSerialiser();
					logger.debug("<< invoke end >>");
					
					return serializer.readObject(entity.getContent(),method.getGenericReturnType());
				}
			} finally {
				EntityUtils.consume(entity);
			}

		} else if (method.getAnnotation(POST.class) != null){
			logger.debug("POST-Method");
			HttpPost request = new HttpPost(url.toString());
			request.setHeader("Accept", "application/json");
			request.setHeader("content-type", "application/json");
			HttpResponse response;
			
			// if we have body content create the entity
			if(argsList.size() > 0){
				JSONObjectSerialiser serializer = new JSONObjectSerialiser();
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				for (Object object : argsList) {
					serializer.writeObject(object, outStream);
				}
				ByteArrayEntity byteEntity = new ByteArrayEntity(outStream.toByteArray());
				request.setEntity(byteEntity);
				response = defaultClient.execute(request);
				try {
					logger.debug("Status-Code: " + response.getStatusLine().getStatusCode());
					entity = response.getEntity();
					if(entity != null) {
						logger.debug("<< invoke end >>");
						
						return serializer.readObject(entity.getContent(),method.getReturnType());
					}
				} finally {
					EntityUtils.consume(entity);
				}
			}
			
		} else if (method.getAnnotation(PUT.class) != null){
			logger.debug("PUT-Method");
			HttpPut request = new HttpPut(url.toString());
			request.setHeader("Accept", "application/json");
			request.setHeader("content-type", "application/json");
			HttpResponse response;
			
			// if we have body content create the entity
			if(args.length > 0){
				JSONObjectSerialiser serializer = new JSONObjectSerialiser();
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				for (Object object : argsList) {
					serializer.writeObject(object, outStream);
				}
				ByteArrayEntity byteEntity = new ByteArrayEntity(outStream.toByteArray());
				request.setEntity(byteEntity);
				response = defaultClient.execute(request);
				try {
					logger.debug("Status-Code: " + response.getStatusLine().getStatusCode());
					entity = response.getEntity();
					if(entity != null) {
						logger.debug("<< invoke end >>");
						
						return serializer.readObject(entity.getContent(),method.getReturnType());
					}
				} finally {
					EntityUtils.consume(entity);
				}
			}
		} else if (method.getAnnotation(DELETE.class) != null){
			logger.debug("DELETE-Method");
			HttpDelete request = new HttpDelete(url.toString());
			request.setHeader("Accept", "application/json");
			request.setHeader("content-type", "application/json");
			HttpResponse response = defaultClient.execute(request);
			try {
				entity = response.getEntity();
				logger.debug("Status Code: " + response.getStatusLine().getStatusCode());
				if(entity != null) {
					JSONObjectSerialiser serializer = new JSONObjectSerialiser();
					logger.debug("<< invoke end >>");
					
					return serializer.readObject(entity.getContent(),method.getGenericReturnType());
				}
			} finally {
				EntityUtils.consume(entity);
			}
		}
		
		// use an output stream for writing json

		// write the object to the stream using the jsonSerialiser

		// create an ByteArrayEntity from the stream's content

		// set the entity on the request which must be cast to
		// HttpEntityEnclosingRequest

		// and add a content type header

		// then send the request to the server and get the response

		// check the response code

		// declare a variable for the return value

		// if the return type is a generic type, getGenericReturnType() will
		// return allow to access the generic type and its type parameter,
		// accessible by casting the
		// Type object to ParameterizedType

		// don't forget to cleanup the entity using EntityUtils.consume()

		return null;
	}

	public static void show(Object content) {
		System.err.println(content + "\n");
	}

}
