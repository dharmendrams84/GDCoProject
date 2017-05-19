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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GDYNAddEmployeeAction extends Action
{
  public static final String SESS_ATTR_OPERATION = "Operation";
  public static final String SESS_ATTR_VAL_ADD_EMP = "AddEmployee";
  private static final Logger logger = Logger.getLogger(GDYNAddEmployeeAction.class);

 
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	 ActionMessages errors = new ActionMessages();
    try
    {
	     GDYNEmployeeManagerRemote employeeManager = getEmployeeManager();
	     GDYNEmployeeDetailsDTO employeeDTO = new GDYNEmployeeDetailsDTO();
	     employeeDTO.setFirstName(request.getParameter("firstName"));
	     employeeDTO.setLastName(request.getParameter("lastName"));
	     employeeDTO.setEmplDiscGrpCode(request.getParameter("discGrpCode"));
	     employeeDTO.setEmplStatus(request.getParameter("emplStatus"));
	     employeeDTO.setEmplNumber(request.getParameter("employeeId"));
	     employeeDTO.setPositionCode(request.getParameter("jobName"));
	     employeeDTO.setEmail(request.getParameter("emailId"));		 
		 employeeManager.addEmployeeDetails(employeeDTO);
		 
		 request.setAttribute("gdEmployeeNumber", request.getParameter("employeeId").toString());
		 logger.debug("In GDYNAddEmployeeAction" + GDYNAddEmployeeAction.class);
    }       
         
     catch (Exception e)
       {
    	 logger.error("In GDYNAddEmployeeAction" + GDYNAddEmployeeAction.class);
             errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unexpected"));
             saveErrors(request, errors);
             return (mapping.getInputForward());
         }
    return mapping.findForward("success");
  }
private GDYNEmployeeManagerRemote getEmployeeManager()
{
  return (GDYNEmployeeManagerRemote)ServiceLocator.getInstance().getRemoteService("gdEmployeeManagerSB", GDYNEmployeeManagerHome.class);
}

/*private GDYNEmployeeServiceRemote getEmployeeService()
{
  return (GDYNEmployeeServiceRemote)ServiceLocator.getInstance().getRemoteService("java:comp/env/ejb/EmployeeService", EmployeeServiceHome.class);
}*/
}