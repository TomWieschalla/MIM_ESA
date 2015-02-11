package org.dieschnittstelle.jee.esa.shopping;

import javax.ejb.Local;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Customer;
import org.dieschnittstelle.jee.esa.erp.entities.AbstractProduct;

@Local
public interface ShoppingSessionFacadeLocal {

	public void setTouchpoint(AbstractTouchpoint touchpoint);

	public void setCustomer(Customer customer);

	public void addProduct(AbstractProduct product, int units);

	public void verifyCampaigns();

	public void purchase();
}
