package gdyn.retail.stores.webmodules.employee.ui;

import gdyn.retail.stores.commerceservices.employee.GDYNEmployeeDetailsDTO;
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



public class GDYNEmployeeSearchAction extends Action
{

	private static final Logger logger = Logger.getLogger(GDYNEmployeeSearchAction.class);
	GDYNEmployeeManagerRemote employeeManager = getEmployeeManager();
	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
		GDYNEmployeeSearchForm hwForm = (GDYNEmployeeSearchForm) form;		
		String emplNumber = hwForm.getEmplNumber();
		String emplFirstName = hwForm.getEmplFirstName();
		List<String> empDiscGrpCodes=employeeManager.getEmployeeDiscGrpCodes();
		 
        logger.debug("execute method in EmployeeAction entered  "+emplFirstName+ " : "+emplNumber);
        logger.error("execute method in EmployeeAction entered  "+emplFirstName+ " : "+emplNumber);
		System.out.println("execute method in EmployeeAction entered  "+emplFirstName+ " : "+emplNumber);
		if(emplNumber!=null && !"".equalsIgnoreCase(emplNumber))
		{
			
		logger.debug("searching by employee number "+emplNumber);		
		List<GDYNEmployeeDetailsDTO> forms=employeeManager.getEmployeeDetails(emplNumber);		
		
		logger.debug("employee forms"+ forms);
		request.setAttribute("forms", forms);
		
		String [] emplDiscGrpCodesArray = new String[empDiscGrpCodes.size()];
		if(empDiscGrpCodes!=null&&empDiscGrpCodes.size()!=0){
			for(int i=0;i<empDiscGrpCodes.size();i++){
				emplDiscGrpCodesArray[i] = empDiscGrpCodes.get(i);
			}
		}
		request.setAttribute("emplDiscGrpCodesArray", emplDiscGrpCodesArray);
		request.setAttribute("empDiscGrpCodes", empDiscGrpCodes);
		
		
		if(forms!=null &&forms.size()!=0)
		{
		request.setAttribute("selectedgrpcode",forms.get(0).getEmplDiscGrpCode());
		request.setAttribute("emplstatus",forms.get(0).getEmplStatus());	
		logger.debug("display employee discgroup"+ forms.get(0).getEmplDiscGrpCode());
		hwForm.setEmplFirstName(forms.get(0).getFirstName());
		hwForm.setEmplNumber(forms.get(0).getEmplNumber());
		hwForm.setEmplLastName(forms.get(0).getLastName());
		hwForm.setEmplEmail(forms.get(0).getEmail());
		hwForm.setPositionCode(forms.get(0).getPositionCode());
		hwForm.setDiscGrpCodes(empDiscGrpCodes);
		
		
		logger.debug("get forms size" +forms.get(0).getFirstName());
		return mapping.findForward("success");
		}
		else
		{
		request.setAttribute("emplNumber", emplNumber);
		logger.debug("employee details not found for employee number " +emplNumber);
		return mapping.findForward("failure");
		}
		
		}
		else if(emplFirstName!=null&& !"".equalsIgnoreCase(emplFirstName))
		{
			logger.debug("searching by employee first name  "+emplFirstName);			
			List<GDYNEmployeeDetailsDTO> forms = employeeManager.getEmployeeDetailsOnFirstName(emplFirstName);
			
			if (forms!=null &&forms.size() == 1)
		      {
				hwForm.setEmplFirstName(forms.get(0).getFirstName());
				hwForm.setEmplNumber(forms.get(0).getEmplNumber());
				hwForm.setEmplLastName(forms.get(0).getLastName());
				hwForm.setEmplEmail(forms.get(0).getEmail());
				hwForm.setPositionCode(forms.get(0).getPositionCode());	
				String [] emplDiscGrpCodesArray = new String[empDiscGrpCodes.size()];
				if(empDiscGrpCodes!=null&&empDiscGrpCodes.size()!=0){
					for(int i=0;i<empDiscGrpCodes.size();i++){
						emplDiscGrpCodesArray[i] = empDiscGrpCodes.get(i);
					}
				}
				request.setAttribute("emplstatus",forms.get(0).getEmplStatus());
				request.setAttribute("emplDiscGrpCodesArray", emplDiscGrpCodesArray);
				request.setAttribute("empDiscGrpCodes", empDiscGrpCodes);
				request.setAttribute("selectedgrpcode",forms.get(0).getEmplDiscGrpCode());
				logger.debug("get forms size" +forms.get(0).getFirstName());
				return mapping.findForward("success");
		      }
			else if(forms!=null && forms.size()!=0)
			{				
			request.setAttribute("employees", forms);
		    request.setAttribute("numEmployees", "" + forms.size());
			return mapping.findForward("allEmplSuccess");
			}
			else
			{
			request.setAttribute("emplNumber", emplNumber);
			return mapping.findForward("failure");
			}
		}
		return mapping.findForward("failure");
	}
	private GDYNEmployeeManagerRemote getEmployeeManager()
	{
	  return (GDYNEmployeeManagerRemote)ServiceLocator.getInstance().getRemoteService("gdEmployeeManagerSB", GDYNEmployeeManagerHome.class);
	}
	
	
}
