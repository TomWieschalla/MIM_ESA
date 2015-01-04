package org.dieschnittstelle.jee.esa.servlets.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

import com.sun.security.ntlm.Client;

public class ShowTouchpointService {

	protected static Logger logger = Logger
			.getLogger(ShowTouchpointService.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShowTouchpointService service = new ShowTouchpointService();
		service.run();
	}

	/**
	 * the http client that can be used for accessing the service on tomcat
	 */
	private HttpClient client;
	
	/**
	 * the attribute that controls whether we are running through (when called from the junit test) or not
	 */
	private boolean stepwise = true;

	/**
	 * constructor
	 */
	public ShowTouchpointService() {
		/*
		 * create a http client and access the web application to read out the
		 * list of touchpoints
		 */
		client = new DefaultHttpClient();
	}

	/**
	 * run
	 */
	public void run() {

		// 1) read out all touchpoints
		List<AbstractTouchpoint> touchpoints = readAllTouchpoints();

		// 2) delete the touchpoint after next console input
		if (touchpoints != null && touchpoints.size() > 0) {
			if (stepwise)
				step();

			deleteTouchpoint(touchpoints.get(0));
		}

		// 3) wait for input and create a new touchpoint
		if (stepwise) {
			step();
		}

		Address addr = new Address("Luxemburger Strasse", "11", "13353",
				"Berlin");
		StationaryTouchpoint tp = new StationaryTouchpoint(-1,
				"BHT Verkaufsstand", addr);

		createNewTouchpoint(tp);


		System.err.println("TestTouchpointService: done.\n");
	}

	/**
	 * read all touchpoints
	 * 
	 * @return
	 */
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoints()");

		try {

			// create a GetMethod

			// UE SER1: Aendern Sie die URL von api->gui
			HttpGet get = new HttpGet(
					"http://localhost:8080/org.dieschnittstelle.jee.esa.servlets/api/touchpoints");

			// execute the method and obtain the response
			HttpResponse response = client.execute(get);
			
			// mittels der response.setHeader() Methode koennen Header-Felder
			// gesetzt werden
			
			// TouchpointGUIServletFilter is schuld - accept-language muss gesetzt werden (oder status auf SC_OK gesetzt) 
//			response.setHeader("accept-language", "de-DE,de;q=0.8,en-US;q=0.6,en;q=0.4");
//			reponse.setStatus(HttpStatus.SC_OK);
			
			// check the response status
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// try to read out an object from the response entity
				ObjectInputStream ois = new ObjectInputStream(response
						.getEntity().getContent());

				List<AbstractTouchpoint> touchpoints = (List<AbstractTouchpoint>) ois
						.readObject();

				logger.info("read touchpoints: " + touchpoints);

				// this is necessary to be able to use the client for a
				// subsequent request
				EntityUtils.consume(response.getEntity());

				return touchpoints;

			} else {
				String err = "could not successfully execute request. Got status code: "
						+ response.getStatusLine().getStatusCode();
				logger.error(err);
				throw new RuntimeException(err);
			}

		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * UE SER3
	 * 
	 * @param tp
	 */
	public void deleteTouchpoint(AbstractTouchpoint tp) {
		logger.info("deleteTouchpoint(): " + tp);

		try {
			HttpDelete delete =  new HttpDelete("http://localhost:8080/org.dieschnittstelle.jee.esa.servlets/api/touchpoints/" + String.valueOf(tp.getId()));
			HttpResponse response = client.execute(delete);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
				System.err.println("delete successfull");
			} else {
				System.err.println("delete function caused a problem: " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		
	}

	/**
	 * UE SER4
	 * 
	 * fuer das Schreiben des zu erzeugenden Objekts als Request Body siehe die
	 * Hinweise auf:
	 * http://stackoverflow.com/questions/10146692/how-do-i-write-to
	 * -an-outpustream-using-defaulthttpclient
	 * 
	 * @param tp
	 */
	public AbstractTouchpoint createNewTouchpoint(AbstractTouchpoint tp) {
		logger.info("createNewTouchpoint(): " + tp);

		try {

			// create post request for the api/touchpoints uri
			HttpPost request = new HttpPost("http://localhost:8080/org.dieschnittstelle.jee.esa.servlets/api/touchpoints");
			
			// create an ObjectOutputStream from a ByteArrayOutputStream - the
			// latter must be accessible via a variable
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			
			// write the object to the output stream
			oos.writeObject(tp);
			oos.close();
			
			byte[] data = bos.toByteArray();
			
			// create a ByteArrayEntity and pass it the byte array from the
			// output stream
			ByteArrayEntity bae = new ByteArrayEntity(data);

			// set the entity on the request
			request.setEntity(bae);
			
			// execute the request, which will return a HttpResponse object
			HttpResponse response = client.execute(request);
			
			// log the status line
			System.err.println(response.getStatusLine());

			// evaluate the result using getStatusLine(), use constants in
			// HttpStatus
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				ObjectInputStream ois =  new ObjectInputStream(response.getEntity().getContent());
				tp = (AbstractTouchpoint) ois.readObject();
				
				System.err.println(tp);
				
				EntityUtils.consume(response.getEntity());
				
				return tp;
			} else {
				System.err.println("got not OK status code: " + response.getStatusLine());
			}
			
			/* if successful: */

			// create an object input stream using getContent() from the
			// response entity (accessible via getEntity())

			// read the touchpoint object from the input stream

			// cleanup the request
			

			return null;
		} catch (Exception e) {
			logger.error("got exception: " + e, e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 * @param stepwise
	 */
	public void setStepwise(boolean stepwise) {
		this.stepwise = stepwise;
	}

	/**
	 * utility...
	 */
	private void step() {
		try {
			System.out.println("/>");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
