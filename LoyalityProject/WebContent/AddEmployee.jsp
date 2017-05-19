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
 <style type="text/css">
 .container {
    width:100%;
    position: relative;
    top :10px;
    
}

.left-element {
	width:auto;
    left :100px;
    display: inline-block;
    position: absolute;
    left: 0;
}

.right-element {
	width:auto;
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
  
  .smallprompt {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: #3c3c3c;
	text-decoration: none;
	text-transform: none;
}
 div h1{
    text-align: left;
    margin-top: -10px; 
    /* height: 20px; */
    /* line-height: 20px; */
    margin-left:10px;
    font-size: 8px;
}

 div h1 span{
    background-color: white;
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
	<form id="addEmployeeForm" id="addEmployeeForm" action="<%=searchAction%>" method="post" onsubmit="return validateForm()">
	<div id = "newEmplDiv" width="100%" height="auto" cellspacing="0" cellpadding="0" style="position: relative;">
	  
	  <span style="position: relative;  color: 3a5a87; font-size: 9pt; font-family: Arial, Helvetica; font-weight:bold;">New Employee</span>  
	 </div>
	 <div id = "enterEmplDiv" style="width: 100%; position: relative; height: 65px; top: 5px;">
	  <table style="width: 100%">
	  <tr class="smallprompt"><th align="left">Enter Employee purchase information and press save</th><th align="right"><input type="submit" value="save"/></th></tr>
	   </table>
	 </div>
	 
	 <div id = "emplIdNew" style="width: 100%; position: relative; height: 40px;border:2px solid;">
	  <table>
	  <tr class="smallprompt"><th align="left">Employee ID : New</th></tr>
	   </table>
	 </div>
	 
		 
	<div class="container">
	
    <div class="left-element">
        <table style="position: relative;left: 40px;">
	  <tr class="smallprompt"><th>First Name</th><th>Last Name</th></tr>
	  	<tr><td><input type="text" name="firstName" id = "firstName"/>*</td><td><input type="text" name="lastName" id = "lastName"/>*</td></tr>
	  </table>
	  
	  <table style = "top: 60px; position: relative;">	 
	  	<tr class="smallprompt"><th align="left">Discount Group Code</th>
		<td> <%
		
	emplDiscGrpCodesArray = (String[]) request.getAttribute("emplDiscGrpCodesArray");
	%>
	<select name="discGrpCode">
	<%
		for (int i = 0; i < emplDiscGrpCodesArray.length; i++) {
	%>
	<option> <%=emplDiscGrpCodesArray[i]%></option>
	<%
		}
	%>
	  	</select></td></tr>
	  	<tr><td></td></tr>
	  	<tr class="smallprompt"><th align="left">Employee Status</th><td> <select name="emplStatus">
	  	<option value="A">A</option>
	  	<option value="L">L</option>
	  	<option value="T">T</option>
	  	</select>*</td></tr>
	  </table>
    </div>
    
    <div class="right-element">
         <table>
	  <tr class="smallprompt"><th>Employee ID:</th><th>Job Name</th></tr>
	  	<tr><td><input type="text" name="employeeId" id = "employeeId"/>*</td><td><input type="text" name="jobName" id = "jobName"/></td></tr>
	  </table>
	 <div style="top: 15px;border:1px solid; position: relative; height: 100px; width: 450px;">
	 <h1><span>Contact and Preferences </span></h1>
	 
	  <table style = "top:10px; position: relative;" >
	  
	  	<tr class="smallprompt"><th align="left">Email:</th><td><input type="text" name="emailId" id= "emailId" style="width: 150px;"/></td></tr>
	  	<tr><td></td></tr>
	  	<tr class="smallprompt"><th align="left">Language</th><td> <select name="language" id="language">
	  	<option value="English">English</option>
	  	</select>*</td></tr>
	  </table>
	 </div>
    </div>
 </div>

</form>
</body>
</html>