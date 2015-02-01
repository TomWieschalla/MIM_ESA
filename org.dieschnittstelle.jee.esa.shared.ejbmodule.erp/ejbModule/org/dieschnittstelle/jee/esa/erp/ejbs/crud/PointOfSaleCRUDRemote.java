package org.dieschnittstelle.jee.esa.erp.ejbs.crud;

import javax.ejb.Remote;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.dieschnittstelle.jee.esa.erp.entities.PointOfSale;

@Remote
public interface PointOfSaleCRUDRemote {

	public PointOfSale createPointOfSale(PointOfSale pos);

	public PointOfSale readPointOfSale(int posId);

	public boolean deletePointOfSale(int posId);

}
