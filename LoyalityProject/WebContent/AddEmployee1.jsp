<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <bean:define id="searchAction" name="gdemployeeSearchAction" scope="request" type="java.lang.String" />
<bean:define id="searchTitle" name="gdemployeeSearchTitle" scope="request" type="java.lang.String" /> --%>


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
	        alert("Enter value for firstname");
	        return false;
	    }
	    
	    alert("evaluates true");
	}
 </script>
 
</head>
<body>

	<%!List<String> list = new ArrayList<String>();%>
	<% list.add("one");
	list.add("two");
	%>
	<%=list.get(0) %>
<form method="post" id="addEmployeeForm" id="addEmployeeForm" onsubmit="return validateForm()">
	
	
<%-- <c:forEach var="lis"  items="${list}">
 <c:out value="$lis" />
</c:forEach> --%>

<%-- <logic:iterate id="cityname" collection="<%=list%>" >
City Name : <%=cityname%> 
</logic:iterate> --%>
</body>
	<div id = "newEmplDiv" width="100%" height="auto" cellspacing="0" cellpadding="0">
	  
	  <span style="position: relative;  color: 3a5a87; font-size: 9pt; font-family: Arial, Helvetica; font-weight:bold;">New Employee</span>  
	 </div>
	 <div id = "enterEmplDiv" style="width: 100%; position: relative; height: 65px; top: 5px;">
	  <table style="width: 100%">
	  <tr><th align="left">Enter Employee purchase information and press save</th><th align="right"><input type="submit" value="save"/></th></tr>
	   </table>
	 </div>
	 
	 <div id = "emplIdNew" style="width: 100%; position: relative; height: 40px;border:2px solid;">
	  <table>
	  <tr><th align="left">Employee ID : New</th></tr>
	   </table>
	 </div>
	 
		 
	<div class="container">
	
    <div class="left-element">
        <table style="position: relative;left: 40px;">
	  <tr ><th>First Name</th><th>Last Name</th></tr>
	  	<tr><td><input type="text" name="firstName" id = "firstName"/>*</td><td><input type="text" name="lastName" id = "lastName"/>*</td></tr>
	  </table>
	  
	  <table style = "top: 60px; position: relative;">	 
	  	<tr><th align="left">Discount Group Code</th><td> <select name="discGrpCode">
	  	<option value="S01">S01</option>
	  	<option value="S02">S02</option>
	  	<option value="H01">H01</option>
	  	<option value="H02">H02</option>
	  	<option value="HO3">HO3</option>
	  	</select>*</td></tr>
	  	<tr><td></td></tr>
	  	<tr><th align="left">Employee Status</th><td> <select name="emplStatus">
	  	<option value="A">A</option>
	  	<option value="L">L</option>
	  	<option value="T">T</option>
	  	</select>*</td></tr>
	  </table>
    </div>
    
    <div class="right-element">
         <table>
	  <tr><th>Employee ID:</th><th>Job Name</th></tr>
	  	<tr><td><input type="text" name="employeeId" id = "employeeId"/>*</td><td><input type="text" name="jobName" id = "jobName"/></td></tr>
	  </table>
	 <div style="top: 15px;border:1px solid; position: relative; height: 100px; width: 450px;">
	 <h1><span>Contact and Preferences </span></h1>
	 
	  <table style = "top:10px; position: relative;" >
	  
	  	<tr><th align="left">Email:</th><td><input type="text" name="emailId" id= "emailId" style="width: 150px;"/></td></tr>
	  	<tr><td></td></tr>
	  	<tr><th align="left">Language</th><td> <select name="language" id="language">
	  	<option value="English">English</option>
	  	</select>*</td></tr>
	  </table>
	 </div>
    </div>
 </div>

</form>
</body>
</html>