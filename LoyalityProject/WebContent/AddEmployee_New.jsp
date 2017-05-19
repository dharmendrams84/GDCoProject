<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%-- <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<bean:define id="searchAction" name="gdemployeeSearchAction" scope="request" type="java.lang.String" />
<bean:define id="searchTitle" name="gdemployeeSearchTitle" scope="request" type="java.lang.String" /> --%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Iterating through collection Using JSTL and EL</title>
<style type="text/css">
.container {
	width: 100%;
	position: relative;
	top: 10px;
}

.left-element {
	width: auto;
	left: 100px;
	display: inline-block;
	position: absolute;
	left: 0;
}

.right-element {
	width: auto;
	display: inline-block;
	position: absolute;
	right: 0;
}

.header-content {
	position: relative;
	bottom: 0;
	left: 0;
}

.serviceBox {
	border: 1px solid black;
}

div h1 {
	text-align: left;
	margin-top: -10px;
	/* height: 20px; */
	/* line-height: 20px; */
	margin-left: 10px;
	font-size: 8px;
}

div h1 span {
	background-color: white;
}

.pageheadercat {
	color: #3c3c3c;
	font-size: 9pt;
	font-family: Arial, Helvetica;
	font-weight: bold;
}

.smallprompt {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: #3c3c3c;
	text-decoration: none;
	text-transform: none;
}

.tableoutline {
	height: 50px;
	border: #3c3c3c;
	border-style: solid;
	border-top-width: 1px solid;
	border-right-width: 1px solid;
	border-bottom-width: 1px solid;
	border-left-width: 1px solid;
}

.heading2 {
	color: #3c3c3c;
	font-size: 10.5pt;
	font-family: Tahoma, Arial, Helvetica, sans-serif;;
	font-weight: bold;
}

.fieldname {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
}
</style>

 <script type="text/javascript">
 
 function validateForm() {
	
	    var firstName = document.forms["addEmployeeForm"]["firstName"].value;
	   
	    if (firstName == "") {
	        alert("Enter value for first name");
	        return false;
	    }
	    
	    var lastName = document.forms["addEmployeeForm"]["lastName"].value;
		   
	    if (lastName == "") {
	        alert("Enter value for last name");
	        return false;
	    }
	    
	    var employeeId = document.forms["addEmployeeForm"]["employeeId"].value;
		   
	    if (employeeId == "") {
	        alert("Enter value for employee Id");
	        return false;
	    }
	    
	    return true;
	}
 </script>
</head>
<body>
<%!

String []emplDiscGrpCodesArray ;
%>
	<form id="addEmployeeForm" id="addEmployeeForm" method="post" action="<%=searchAction%>" onsubmit="return validateForm()">


		<table id="newEmplDiv" width="100%">
			<tbody>
				<tr>
					<td width="40%" style="color: #3c3c3c;"><span
						class="pageheadercat">New Employee</span></td>
				</tr>
     		</tbody>
		</table>

		<table style="width: 100%; height: 65px; top: 5px;">
			<tr>
				<td class="smallprompt">Enter Employee purchase information and press save.</td>
				<td align="right"><input type="submit" value="save" /></td>
			</tr>
		</table>

		<table id="emplIdNewTab" style="width: 100%;" class="tableoutline">
			<tr>
				<td  class="heading2" align="left">Employee ID : New</td>
			</tr>
		</table>

		<table width="100%" style="position: relative; top: 20px;">
			<tbody>
				<tr>
					<td width="130px">
						<table width="50%" style="position: relative; left: 10%;">
							<tr>
								<td class="fieldname" align="center">First Name</td>
								<td></td>
								<td class="fieldname" align="center">Last Name</td>
							</tr>
							<tr>
								<td align="center"><input type="text" name="firstName"
									id="firstName" /></td>
								<td>*</td>	
								<td align="center"><input type="text" name="lastName"
									id="lastName" /></td>
								<td>*</td>	
							</tr>
						</table>
				    </td>
					<td width="130px">
						<table width="50%">
							<tr>
								<td class="fieldname" align="center">Employee ID:</td>
								<td></td>
								<td class="fieldname" align="center">Job Name</td>
							</tr>
							<tr>
								<td align="center"><input type="text" name="employeeId"
									id="firstName" /></td>
								<td>*</td>	
								<td align="center"><input type="text" name="jobName"
									id="lastName" /></td>
								<td>*</td>		
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="80%" style="position: relative; left: 10%;">
							<tr>
								<td class="fieldname" align="left" width="130px">Discount
									Group Code</td>
								<td class="fieldname" align="left">
									<%
										emplDiscGrpCodesArray = (String[]) request
												.getAttribute("emplDiscGrpCodesArray");
									%> <select name="discGrpCode">
										<%
											for (int i = 0; i < emplDiscGrpCodesArray.length; i++) {
										%>
										<option>
											<%=emplDiscGrpCodesArray[i]%></option>
										<%
									}
								%>
								</select>*</td>
							</tr>
							<tr>
								<td class="fieldname" align="left" width="130px">Employee
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
									<td align="left" class="fieldname">Email:</td>
									<td><input type="text" name="emailId" id="emailId"
										style="width: 150px;" /></td>
								</tr>
								<tr>
									<td align="left" class="fieldname">Language:</td>
									<td><select name="language" id="language">
											<option value="English">English</option>
									</select>*</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
  </body>
</html>