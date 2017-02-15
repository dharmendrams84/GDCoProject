/**
 * 
 */
package com.gdyn.co.employeediscount.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for EmployeeDiscountServiceSB
 * @author Monica
 *
 */
public interface GDYNEmployeeServiceHome extends EJBHome 
{
	GDYNEmployeeServiceRemote create() throws CreateException, RemoteException;
}
