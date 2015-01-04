package org.dieschnittstelle.jee.esa.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

public class TouchpointWebServiceServlet extends HttpServlet {

	protected static Logger logger = Logger
			.getLogger(TouchpointWebServiceServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("doGet()");

		// we assume here that GET will only be used to return the list of all
		// touchpoints

		// obtain the executor for reading out the touchpoints
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			// set the status
			response.setStatus(HttpServletResponse.SC_OK);
			// obtain the output stream from the response and write the list of
			// touchpoints into the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			// write the object
			oos.writeObject(exec.readAllTouchpoints());
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}
	
	
	@Override	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("doPost-Method");
		// assume POST will only be used for touchpoint creation, i.e. there is
		// no need to check the uri that has been used

		// obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");
		
		try {
			// create an ObjectInputStream from the request's input stream
			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			
			// read an AbstractTouchpoint object from the stream
			AbstractTouchpoint tp = (AbstractTouchpoint) ois.readObject();
			ois.close();
		
			// call the create method on the executor and take its return value
			tp = exec.createTouchpoint(tp);
			
			// set the response status as successful, using the appropriate
			// constant from HttpServletResponse
			response.setStatus(HttpServletResponse.SC_CREATED);
			
			// then write the object to the response's output stream, using a
			// wrapping ObjectOutputStream
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			
			// ... and write the object to the stream
			oos.writeObject(tp);
			oos.close();
			
		} catch (Exception e) {
			String err = "got exception in post: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}
	
@Override
protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	logger.info("doDelete-Method");

	// obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute
	TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext().getAttribute("touchpointCRUD");
	
	try {
		String pathinfo = req.getPathInfo();
		String subId = pathinfo.substring(1);
		int id = Integer.parseInt(subId);
		
		if(exec.deleteTouchpoint(id)){
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
	} catch (Exception e) {
		String err = "got exception in delete: " + e;
		logger.error(err, e);
		throw new RuntimeException(e);
	}
}

	
}
