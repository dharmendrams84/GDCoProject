package com.gdyn.test;
import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.gdyn.co.employeediscount.ejb.GDYNEmployeeServiceHome;

/**
 * 
 */

/**
 * @author Monica
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Hashtable<String, String> environment = new Hashtable<String, String>();

		environment.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		environment.put(Context.PROVIDER_URL, "t3s://localhost:7002");
		environment.put(Context.SECURITY_PRINCIPAL, "pos");
		environment.put(Context.SECURITY_CREDENTIALS, "pos12345");
		try {
			
			InitialContext context = new InitialContext(environment);
			Object objref =  context.lookup("employeeDiscount-ejb_EmployeeDiscountServiceSB");
        	System.out.println("requestIpin employeeDiscount-ejb_EmployeeDiscountServiceSB lookup Successfully!!!");			
			
			EJBHome home = (EJBHome) PortableRemoteObject.narrow(objref,GDYNEmployeeServiceHome.class);
			System.out.println("CO Connection Established");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
