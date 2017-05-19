package com.actions;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.employee.dao.EmployeeDAO;
import com.forms.EmployeeForm;
import com.forms.HelloWorldForm;


public class UpdateEmplDtlsAction extends Action{

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
	
		EmployeeForm employeeForm = new EmployeeForm();
		/*System.out.println(employeeForm.getFirstName()+ " : "+employeeForm.getLastName()+ " : "
				+employeeForm.getEmplDiscGrpCode()+ " : "+employeeForm.getEmplId()+ " : "+employeeForm.getPositionCode());
		*/
		
		String firstName = request.getParameter("firstName");
		
		 String lastName =  request.getParameter("lastName");
		 String emplDiscGrpCode = request.getParameter("emplDiscGrpCode");
		 String emplStatus = request.getParameter("emplStatus");
		 String positionCode  = request.getParameter("positionCode");
		 String email  = request.getParameter("emailId");
		 /*	 
		
		 int emplId = new Integer(request.getParameter("emplId"));
	
		 String emplIdSrc  = request.getParameter("emplIdSrc");*/
		 
		 employeeForm.setFirstName(firstName);
		 employeeForm.setLastName(lastName);
		 employeeForm.setEmplDiscGrpCode(emplDiscGrpCode);
		 employeeForm.setEmplStatus(emplStatus);
		 employeeForm.setPositionCode(positionCode);
		 employeeForm.setEmail(email);
		 /*
		 employeeForm.setEmplId(emplId);
		
		 employeeForm.setEmplIdSrc(emplIdSrc);*/
		 
		 employeeForm.setEmplNumber(Integer.parseInt(request.getParameter("emplNumber")));
		//System.out.println(request.getParameter("firstName"));
		EmployeeDAO.updateEmployeeDtls(employeeForm);
		return mapping.findForward("success");
	}
}
