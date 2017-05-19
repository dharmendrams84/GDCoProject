package gdyn.retail.stores.webmodules.employee.ui;



import gdyn.retail.stores.commerceservices.employee.GDYNEmployeeDetailsDTO;
import gdyn.retail.stores.webmodules.employee.app.ejb.GDYNEmployeeManagerHome;
import gdyn.retail.stores.webmodules.employee.app.ejb.GDYNEmployeeManagerRemote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.retail.stores.foundation.common.ServiceLocator;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class GDYNUpdateEmplDtlsAction extends Action{

	private static final Logger logger = Logger.getLogger(GDYNUpdateEmplDtlsAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
	
		GDYNEmployeeDetailsDTO employeeDTO= new GDYNEmployeeDetailsDTO();
		GDYNEmployeeManagerRemote employeeManager = getEmployeeManager();
		
		String firstName = request.getParameter("firstName");		
		 String lastName =  request.getParameter("lastName");
		 String emplDiscGrpCode = request.getParameter("emplDiscGrpCode");
		 String emplStatus = request.getParameter("emplStatus");
		 String positionCode  = request.getParameter("positionCode");
		 String email  = request.getParameter("emailId");
		 
		 employeeDTO.setFirstName(firstName);
		 employeeDTO.setLastName(lastName);
		 employeeDTO.setEmplDiscGrpCode(emplDiscGrpCode);
		 employeeDTO.setEmplStatus(emplStatus);
		 employeeDTO.setPositionCode(positionCode);
		 employeeDTO.setEmail(email);	
		 
		 employeeDTO.setEmplNumber(request.getParameter("emplNumber"));	
		 logger.debug("execute method in UpdateAction entered  "+employeeDTO.getEmplNumber()+ " : "+employeeDTO.getPositionCode() +employeeDTO.getEmail());
		employeeManager.updateEmployeeDtls(employeeDTO);
		request.setAttribute("gdEmployeeNumber", employeeDTO.getEmplNumber());
		
		return mapping.findForward("success");
	}
	
	private GDYNEmployeeManagerRemote getEmployeeManager()
	{
	  return (GDYNEmployeeManagerRemote)ServiceLocator.getInstance().getRemoteService("gdEmployeeManagerSB", GDYNEmployeeManagerHome.class);
	}
}
