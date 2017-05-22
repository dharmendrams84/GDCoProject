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
	<form id="updateEmployeeForm" id="updateEmployeeForm" action="<%=searchAction%>" name="employeeSearchForm" id="employeeSearchForm" method="post" onsubmit="return validateForm()">

	<div id = "newEmplDiv" style=" position: relative; width: 100%; height: 40px; border-bottom:1px solid; ">
	  
	  <table>
	   <tr class="smallprompt"><td>Employee Details</td></tr>
	  </table>
	   
	 </div>
	 <div id = "enterEmplDiv" style="width: 100%; position: relative; height: 65px; top: 5px;">
	  <table style="width: 100%">
	  <tr class="smallprompt"><th align="left">Edit Employee data as needed,press Save when complete.</th><th align="right"><input type="submit" value="save"/></th></tr>
	   </table>
	 </div>
	  
	 <div id = "emplIdNew" style="width: 100%; position: relative; height: 40px;border:2px solid;">
	  <table>
	  <tr class="smallprompt"><th align="left">Employee Number:</th> <th>${employeeSearchForm.emplNumber}</th></tr>
	  
	   </table>
	 </div>	
		 
	<div class="container">
	
    <div class="left-element">
        <table style="position: relative;left: 40px;">
	  <tr class="smallprompt"><th>First Name</th><th>Last Name</th></tr>
	  	<tr class="smallprompt"><td><input type="text" name="firstName" id = "firstName" value = "${employeeSearchForm.emplFirstName}"/>*</td>
	  	<td><input type="text" name="lastName" id = "lastName" value = "${employeeSearchForm.emplLastName}"/>*</td></tr>
	  </table>
	  
	  <table style = "top: 60px; position: relative;">	 
	  	<tr class="smallprompt"><th align="left">Discount Group Code</th>
		<td> 	
			<%
		
	emplDiscGrpCodesArray = (String[]) request.getAttribute("emplDiscGrpCodesArray");
	%>
	<select name="emplDiscGrpCode" id="emplDiscGrpCode">
	<%
		for (int i = 0; i < emplDiscGrpCodesArray.length; i++) {
	%>
	<option> <%=emplDiscGrpCodesArray[i]%></option>
	<%
		}
	%>
</select>
	  	</td></tr>
	  	<tr><td></td></tr>
	  	<tr class="smallprompt"><th align="left">Employee Status</th><td> 
	  	<select name="emplStatus">
	  	<option value="A">A</option>
	  	<option value="L">L</option>
	  	<option value="T">T</option>
	  	</select>
	  	*</td></tr>

	 </table>
	  
    </div>
    
    <div class="right-element">
         <table>
	  <tr class="smallprompt"><th>Job Name</th></tr>
	  	<tr><td><input type="text" name="positionCode" id = "positionCode" value = "${employeeSearchForm.positionCode}"/></td></tr>
	  </table>
	 <div style="top: 15px;border:1px solid; position: relative; height: 100px; width: 450px;">
	 <h1><span>Contact and Preferences </span></h1>
	 
	  <table style = "top:10px; position: relative;" >
	  
	  	<tr class="smallprompt"><th align="left">Email:</th><td><input type="text" name="emailId" id= "emailId" style="width: 150px;" value = "${employeeSearchForm.emplEmail}"/></td></tr>
	  	<tr><td></td></tr>
	  	<tr class="smallprompt"><th align="left">Language</th><td> <select name="language" id="language">
	  	<option value="English">English</option>
	  	</select>*</td></tr>
	  </table>
	 </div>
    </div>
 </div>
 <input type="hidden" name = "emplNumber" value = "${employeeSearchForm.emplNumber}"/>
	


</form>
</body>
</html>