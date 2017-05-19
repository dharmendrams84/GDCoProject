package com.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.employee.dao.EmployeeDAO;
import com.forms.EmployeeForm;


public class DisplayEmplAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("--------");
	/*	System.out.println("request.getParameter(employeeId) "+request.getParameter("emplNumber"));
		
		String emplNumber = request.getParameter("emplNumber");
		
		System.out.println("execute method in HelloWorldAction entered  "+" : "+emplNumber);
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
		}*/
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		String [] listArray = new String[3];
		listArray[0] ="one";
		listArray[1] ="two";
		listArray[2] ="three";
		request.setAttribute("emplDiscGrpCodesArray", listArray);
		request.setAttribute("selectedDiscGrpCodeVal", "three");
		return mapping.findForward("success");
	}

}
