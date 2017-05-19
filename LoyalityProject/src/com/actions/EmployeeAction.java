package com.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.employee.dao.EmployeeDAO;
import com.forms.EmployeeForm;
import com.forms.HelloWorldForm;

public class EmployeeAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HelloWorldForm hwForm = (HelloWorldForm) form;
		
		String emplNumber = hwForm.getEmplNumber();
		String emplFirstName = hwForm.getEmplFirstName();
		System.out.println("execute method in EmployeeAction entered  "+emplFirstName+ " : "+emplNumber);
		if(emplNumber!=null && !"".equalsIgnoreCase(emplNumber)){
			System.out.println("searching by employee number "+emplNumber);
		List<EmployeeForm> forms = new EmployeeDAO()
				.getEmployeeDetails(Integer.parseInt(emplNumber));
		request.setAttribute("forms", forms);
		if(forms!=null && forms.size()!=0){
		return mapping.findForward("success");
		}else{
			request.setAttribute("emplNumber", emplNumber);
		return mapping.findForward("failure");
		}
		}else if(emplFirstName!=null&& !"".equalsIgnoreCase(emplFirstName)){
			System.out.println("searching by employee first name  "+emplFirstName);
			List<EmployeeForm> forms = new EmployeeDAO().getEmployeeDetailsOnFirstName(emplFirstName);
			request.setAttribute("forms", forms);
			if(forms!=null && forms.size()!=0){
			return mapping.findForward("allEmplSuccess");
			}else{
				request.setAttribute("emplNumber", emplNumber);
			return mapping.findForward("failure");
			}
		}
		return mapping.findForward("failure");
	}

}
