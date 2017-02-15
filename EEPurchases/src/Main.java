import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.gdyn.co.employeediscount.ejb.GDYNEmployeeServiceBean;
import com.gdyn.co.employeediscount.response.GDYNEmployeeDiscResponseObject;

/**
 * 
 */

/**
 * @author Monica
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger(Main.class);
		GDYNEmployeeServiceBean bean=new GDYNEmployeeServiceBean();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//logger.info("Driver Loaded");
			//Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "co1341", "co1341");
			//Connection con = DriverManager.getConnection("jdbc:oracle:thin:@stotst-scan.corp.gdglobal.ca:1521/stotst.corp.gdglobal.ca", "RCO_SCHEMA", "Rco_1120#");
			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@saodah01.corp.gdglobal.ca:1521/stoprd.corp.gdglobal.ca", "RCO_SCHEMA", "Rco_1120#");
			GDYNEmployeeDiscResponseObject[] responseObject = null;
			Hashtable<String, String> environment = new Hashtable<String, String>();
	        responseObject=bean.getEmployeeResponseObject("87938",con);
			//responseObject=bean.getEmployeeResponseObject("77757",con);
		}
		
		catch (SQLException e1)
		{
			System.out.println(e1);
		}
			/*environment.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			environment.put(Context.PROVIDER_URL, "t3s://sastoh81.corp.gdglobal.ca:7002");
			environment.put(Context.SECURITY_PRINCIPAL, "pos");
			environment.put(Context.SECURITY_CREDENTIALS, "Web_0801");
			InitialContext initialContext = null;
			
		try{
			if(initialContext == null) {
				initialContext = getInitialContext("t3s://sastoh81.corp.gdglobal.ca:7002","pos","Web_0801");
				}
			//InitialContext context = new InitialContext(environment);
			Object objref =  initialContext
						.lookup("employeeDiscount-ejb_EmployeeDiscountServiceSB");
	        	logger.info("requestIpin evoucher-ejb_RechargeServiceSB lookup Successfully!!!");			
				
				EJBHome home = (EJBHome) PortableRemoteObject.narrow(objref,
						GDYNEmployeeServiceHome.class);

				Method m = null;
				try {
					m = home.getClass().getMethod("create", new Class[0]);
				} catch (SecurityException e1) {
					logger.error("Security exception  : " + e1);
				} catch (NoSuchMethodException e1) {
					logger.error("NoSuchMethodException exception  : " + e1);
				}
				GDYNEmployeeServiceRemote rechargeServiceRemote = null;
				try {
					rechargeServiceRemote = (GDYNEmployeeServiceRemote) m.invoke(home, new Object[0]);
				} catch (IllegalArgumentException e1) {
					logger.error("IllegalArgumentException exception  : " + e1);
				} catch (IllegalAccessException e1) {
					logger.error("IllegalAccessException exception  : " + e1);
				} catch (InvocationTargetException e1) {
					logger.error("InvocationTargetException exception  : " + e1);
				}
				try {
					responseObject = rechargeServiceRemote.getRechargeResponseObject("12345",con);
				} catch (Exception e) {
					logger.error("Exception exception  : " + e);
				}
			} catch (Exception exception) {
				logger.error("NamingException exception  : " + exception);
			}
			//bean.getRechargeResponseObject("75608",con);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	private static InitialContext getInitialContext(String url,String userId,String password) throws NamingException {
		Hashtable<String, String> env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, userId);
		env.put(Context.SECURITY_CREDENTIALS, password);
		return new InitialContext(env);
	}*/
	}
	
}
