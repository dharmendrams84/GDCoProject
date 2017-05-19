package gdyn.retail.stores.webmodules.employee.ui;

import gdyn.retail.stores.commerceservices.employee.GDYNEmployeeDetailsDTO;
import gdyn.retail.stores.webmodules.employee.app.ejb.GDYNEmployeeManagerHome;
import gdyn.retail.stores.webmodules.employee.app.ejb.GDYNEmployeeManagerRemote;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.retail.stores.foundation.common.ServiceLocator;
import oracle.retail.stores.webmodules.employee.app.ejb.EmployeeManagerHome;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class GDYNDisplayEmplAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		System.out.println("request.getParameter(employeeId) "+request.getParameter("emplNumber"));
		
		String emplNumber = request.getParameter("emplNumber");
		GDYNEmployeeManagerRemote employeeManager = getEmployeeManager();
		System.out.println("execute method in HelloWorldAction entered  "+" : "+emplNumber);
		if(emplNumber!=null && !"".equalsIgnoreCase(emplNumber)){
			System.out.println("searching by employee number "+emplNumber);
		//List<GDYNEmployeeDetailsForm> forms = new GDYNEmployeeServiceBean().getEmployeeDetails(emplNumber);
	    List<GDYNEmployeeDetailsDTO> dtos =employeeManager.getEmployeeDetails(emplNumber);
	    GDYNEmployeeDetailsForm employeeForm = null;
	    List<GDYNEmployeeDetailsForm> employeeDetailsFormsList = new ArrayList<GDYNEmployeeDetailsForm>(); 
	    
	    for(int i=0;i < dtos.size(); i++)
	    {
	    	employeeForm = new GDYNEmployeeDetailsForm();
	    	employeeForm.setEmail(dtos.get(i).getEmail());
	    	
	    	
	    	employeeDetailsFormsList.add(employeeForm);
	    }
		request.setAttribute("forms", employeeDetailsFormsList);
		if(dtos!=null && dtos.size()!=0){
		return mapping.findForward("success");
		}else{
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
