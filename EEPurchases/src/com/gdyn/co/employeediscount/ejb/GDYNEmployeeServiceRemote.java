/**
 * 
 */
package com.gdyn.co.employeediscount.ejb;

import javax.ejb.EJBObject;

import com.gdyn.co.employeediscount.response.GDYNEmployeeDiscResponseObject;

/**
 * Remote interface for EmployeeDiscountServiceSB
 * @author Monica
 *
 */
public interface GDYNEmployeeServiceRemote extends EJBObject 
{
	/**
	 * Gets the GDYNEmployeeDiscResponseObject based on the passed request criteria.
	 * @param employeeNumber
	 * @return GDYNEmployeeDiscResponseObject
	 * @throws Exception
	 */
	public GDYNEmployeeDiscResponseObject[] getEmployeeResponseObject(String employeeNumber) throws Exception;

	
}
	