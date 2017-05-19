package gdyn.retail.stores.webmodules.employee.ui;



import gdyn.retail.stores.webmodules.employee.app.ejb.GDYNEmployeeManagerHome;
import gdyn.retail.stores.webmodules.employee.app.ejb.GDYNEmployeeManagerRemote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.retail.stores.foundation.common.ServiceLocator;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GDYNBeforeAddEmployeeAction extends Action
{
  public static final String SESS_ATTR_OPERATION = "Operation";
  public static final String SESS_ATTR_VAL_ADD_EMP = "AddEmployee";

  private static final Logger logger = Logger.getLogger(GDYNBeforeAddEmployeeAction.class);
 
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	
	GDYNEmployeeManagerRemote employeeManager = getEmployeeManager();
	List<String> empDiscGrpCodes= employeeManager.getEmployeeDiscGrpCodes();
	
	String [] emplDiscGrpCodesArray = new String[empDiscGrpCodes.size()];
	if(empDiscGrpCodes!=null&&empDiscGrpCodes.size()!=0)
	{
		for(int i=0;i<empDiscGrpCodes.size();i++)
		{
			emplDiscGrpCodesArray[i] = empDiscGrpCodes.get(i);
		}
	}
	request.setAttribute("emplDiscGrpCodesArray", emplDiscGrpCodesArray);
	request.setAttribute("empDiscGrpCodes", empDiscGrpCodes);
	logger.debug("In GDYNBeforeAddEmployeeAction" + empDiscGrpCodes.get(0));
    return mapping.findForward("success");
  }

private GDYNEmployeeManagerRemote getEmployeeManager()
{
  return (GDYNEmployeeManagerRemote)ServiceLocator.getInstance().getRemoteService("gdEmployeeManagerSB", GDYNEmployeeManagerHome.class);
}
}