package org.dieschnittstelle.jee.esa.ejbs.client.ejbclients;

import static org.dieschnittstelle.jee.esa.ejbs.client.Constants.TOUCHPOINT_1;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dieschnittstelle.jee.esa.crm.ejbs.crud.TouchpointCRUDRemote;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.ejbs.client.Constants;
import org.dieschnittstelle.jee.esa.erp.ejbs.crud.PointOfSaleCRUDRemote;
import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;

public class TouchpointAndPointOfSaleCRUDClient implements TouchpointCRUDRemote {

	private TouchpointCRUDRemote touchpointCRUDProxy;
	private PointOfSaleCRUDRemote pointOfSaleCRUDProxy;
	
	public TouchpointAndPointOfSaleCRUDClient() throws Exception {
		Context context = new InitialContext();
		this.touchpointCRUDProxy = (TouchpointCRUDRemote) context
				.lookup(Constants.TOUCHPOINT_CRUD_BEAN);
		this.pointOfSaleCRUDProxy = (PointOfSaleCRUDRemote) context
				.lookup(Constants.POS_CRUD_BEAN);
	}
	
	
	public List<AbstractTouchpoint> readAllTouchpoints() {
		return touchpointCRUDProxy.readAllTouchpoints();
	}

	@Override
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
		// here, we will first create a new point of sale and then associate its id with the touchpoint object
		PointOfSale pos = pointOfSaleCRUDProxy.createPointOfSale(new PointOfSale());
		touchpoint.setErpPointOfSaleId(pos.getId());

		AbstractTouchpoint created = touchpointCRUDProxy.createTouchpoint(touchpoint);
		// as a side effect, we set the id of the created object on the argument before returning
		touchpoint.setId(created.getId());
		
		return created;
	}

	@Override
	public AbstractTouchpoint readTouchpoint(int id) {
		return touchpointCRUDProxy.readTouchpoint(id);
	}

	@Override
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint) {
		return touchpointCRUDProxy.updateTouchpoint(touchpoint);
	}

	@Override
	public boolean deleteTouchpoint(int id) {
		return touchpointCRUDProxy.deleteTouchpoint(id);
	}
		
}
