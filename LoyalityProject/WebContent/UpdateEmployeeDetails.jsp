<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<bean:define id="searchAction" name="gdemployeeSearchAction" scope="request" type="java.lang.String" />
<bean:define id="searchTitle" name="gdemployeeSearchTitle" scope="request" type="java.lang.String" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Iterating through collection Using JSTL and EL</title>
<script type="text/javascript">



	function PreselectDiscGroCode(itemToSelect) {
		// Get a reference to the drop-down
		var myDropdownList = document.employeeSearchForm.emplDiscGrpCode;

		// Loop through all the items
		for (var iLoop = 0; iLoop < myDropdownList.options.length; iLoop++) {
			if (myDropdownList.options[iLoop].value == itemToSelect) {
				// Item is found. Set its selected property, and exit the loop
				myDropdownList.options[iLoop].selected = true;
				break;
			}
		}

	}
	
	
	function validateForm() {
		
	    var firstName = document.forms["updateEmployeeForm"]["firstName"].value;
	   
	    if (firstName == "") {
	        alert("Enter value for first name");
	        return false;
	    }
	    
	    var lastName = document.forms["updateEmployeeForm"]["lastName"].value;
		   
	    if (lastName == "") {
	        alert("Enter value for last name");
	        return false;
	    }
	    
	   
	    return true;
	}
</script>


 <style type="text/css">
 .container {
    width:100%;
    position: relative;
    top :10px;
    
}

.left-element {
	width:40%;
    left :100px;
    display: inline-block;
    position: absolute;
    left: 0;
}

.right-element {
	width:40%;
    display: inline-block;
    position: absolute;
    right: 0;
}
 .header-content {
    position: relative;
    bottom: 0;
    left: 0;
  }
  
  .serviceBox
{
   
    border:1px solid black;
}
  
 div h1{
    text-align: left;
    margin-top: -10px; 
    /* height: 20px; */
    /* line-height: 20px; */
    margin-left:10px;
    font-size: 15px;
}

 div h1 span{
    background-color: white;
} 
 
 .smallprompt {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: #3c3c3c;
	text-decoration: none;
	text-transform: none;
}
 </style>
</head>
<body onLoad="PreselectDiscGroCode('${selectedgrpcode}')">
<%!

String []emplDiscGrpCodesArray ;
%>
	<form id="updateEmployeeForm" id="updateEmployeeForm" action="<%=searchAction%>"  method="post" onsubmit="return validateForm()">

	<table id="newEmplDiv" width="100%">
			<tbody>
				<tr>
					<td width="40%" style="color: #3c3c3c;"><span
						class="pageheadercat">Employee Details </span></td>
				</tr>
				<tr bgcolor="#3c3c3c">
					<td colspan="2"></td>
				</tr>
     		</tbody>
	</table>
	 <table style="width: 100%; height: 65px; top: 5px;">
			<tr>
				<td class="smallprompt">Edit Employee data as needed,press Save when complete.</td>
				<td align="right"><input type="submit" value="save" /></td>
			</tr>
	</table>
	  
	  <table id="emplIdNewTab" style="width: 100%;" class="tableoutline">
			<tr>
				<td  class="heading2" align="left">Employee Number:</td> <td  class="heading2" align="left">${employeeSearchForm.emplNumber}</td>
			</tr>
		</table>
	
	<table width="100%" style="position: relative; top: 20px;">
			<tbody>
				<tr>
					<td width="130px">
						<table width="50%" style="position: relative; left: 10%;">
							<tr>
								<td class="smallprompt" align="center">First Name</td>
								<td></td>
								<td class="smallprompt" align="center">Last Name</td>
							</tr>
							<tr>
								<td align="center"><input type="text" name="firstName"
									id="firstName" value = "${employeeSearchForm.emplFirstName}"/>*</td>
								<td></td>	
								<td align="center"><input type="text" name="lastName"
									id="lastName" value = "${employeeSearchForm.emplLastName}"/>*</td>
								<td></td>	
							</tr>
						</table>
				    </td>
					<td width="130px">
						<table width="30%">
							<tr>
								<td class="smallprompt" align="center">Job Name</td>
							</tr>
							<tr>
								<td align="center">
									<input type="text" name="positionCode" id = "positionCode" value = "${employeeSearchForm.positionCode}"/>
									</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="80%" style="position: relative; left: 10%;">
							<tr>
								<td class="smallprompt" align="left" width="130px">Discount
									Group Code</td>
								<td class="fieldname" align="left">
									<%
										emplDiscGrpCodesArray = (String[]) request
												.getAttribute("emplDiscGrpCodesArray");
									%> <select name="emplDiscGrpCode" id="emplDiscGrpCode">
										<%
											for (int i = 0; i < emplDiscGrpCodesArray.length; i++) {
										%>
										<option>
											<%=emplDiscGrpCodesArray[i]%></option>
										<%
										}
									%>
								</select> *</td>
							</tr>
							<tr>
								<td class="smallprompt" align="left" width="130px">Employee
									Status</td>
								<td align="left"><select name="emplStatus">
										<option value="A">A</option>
										<option value="L">L</option>
										<option value="T">T</option>
								</select>*</td>
							</tr>
						</table>
					</td>
					<td>
						<fieldset
							style="height: 120px; border-color: #3c3c3c; font-weight: bold;">
							<legend>Contact and Preferences</legend>
							<table style="top: 10px; position: relative; left: 5%;">
								<tr>
									<td align="left" class="smallprompt">Email:</td>
									<td>
									<input type="text" name="emailId" id= "emailId" style="width: 150px;" value = "${employeeSearchForm.emplEmail}"/>	
										</td>
								</tr>
								<tr>
									<td align="left" class="smallprompt">Language:</td>
									<td>
									<select name="language" id="language">
											<option value="English">English</option>
									</select>*</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</tbody>
		</table>
	
	<input type="hidden" name = "emplNumber" value = "${employeeSearchForm.emplNumber}"/>
	

</form>
</body>
</html>