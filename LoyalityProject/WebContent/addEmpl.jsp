<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

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

.smallprompt {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	color: #3c3c3c;
	text-decoration: none;
	text-transform: none;
}

.heading2 {
	color: #3a5a87;
	font-size: 12pt;
	font-family: Arial, Helvetica;
	font-weight: bold;
}

.heading3 {
    font-family: Tahoma, Arial, Helvetica, sans-serif;
    font-size: 9pt;
    font-weight: bold;
    color: #3a5a87;
    text-decoration: none;
}
.fieldname {
    font-family: Tahoma, Arial, Helvetica, sans-serif;
    font-size: 9pt;
    font-weight: bold;
}

.tableoutline {
    border: #99bedc;
    border-style: solid;
    border-top-width: thin;
    border-right-width: thin;
    border-bottom-width: thin;
    border-left-width: thin;
    position: relative;
    height: 100%;
    width: 100%;
}
</style>
</head>
<body>
	<form>
	<div id = "mainDiv" class="tableoutline">
	<div id = "newEmplDiv" width="100%" height="auto" style="position: relative;" >
	  
	  <span style="position: relative;  color: #3a5a87; font-size: 9pt; font-family: Arial, Helvetica; font-weight:bold;">New Employee</span>  
	 </div>
	 <div id = "enterEmplDiv" style="width: 100%; position: relative;">
	  <table style="width: 100%">
	  <tr><td align="left"  class ="smallprompt">Enter Employee purchase information and press save</td><th align="right"><input type="submit" value="save"/></th></tr>
	   </table>
	 </div>
	 
	
	 <div id = "emplIdNew" style="width: 100%; position: relative; height: 40px;">
	  <table>
	  <tr><td align="left" class= "heading3">Employee ID : New</td></tr>
	   </table>
	 </div>
	 
		 
	<div class="container">
	
    <div class="left-element">
        <table style="position: relative;left: 40px;">
	  <tr class="fieldname"><td align="left">First Name</td><td>Last Name</td></tr>
	  	<tr><td><input type="text" name="firstName" id = "firstName"/>*</td><td><input type="text" name="lastName" id = "lastName"/>*</td></tr>
	  </table>
	  
	  <table style = "top: 60px; position: relative;"  class="fieldname">	 
	  	<tr ><th align="left">Discount Group Code</th><td> <select name="discGrpCode"  class="fieldname">
	  	<option value="S01">S01</option>
	  	<option value="S02">S02</option>
	  	<option value="H01">H01</option>
	  	<option value="H02">H02</option>
	  	<option value="HO3">HO3</option>
	  	</select>*</td></tr>
	  	<tr><td></td></tr>
	  	<tr><th align="left">Employee Status</th><td> <select name="emplStatus"  class="fieldname">
	  	<option value="A">A</option>
	  	<option value="L">L</option>
	  	<option value="T">T</option>
	  	</select>*</td></tr>
	  </table>
    </div>
    
    <div class="right-element">
         <table>
	  <tr class="fieldname"><td>Employee ID:</td><td>Job Name</td></tr>
	  	<tr><td><input type="text" name="employeeId" id = "employeeId"/>*</td><td><input type="text" name="jobName" id = "jobName"/></td></tr>
	  </table>
	 <div	 style="position: relative; top: 15px; padding:5px; border:thin solid #99BEDC; height:100px;width: 450px;">
	<!--  <h1><span>Contact and Preferences </span></h1> -->
					<span class="heading3"
						style="position: relative; top: 5px; left: 10px; text-align: left; background-color: #FFFFFF; width: auto; padding-left: 10px; padding-right: 10px;">Contact
						and Preference</span>

					<table style = "top:10px; position: relative;" >
	  
	  	<tr><td align="left" class="fieldname">Email:</td><td><input type="text" name="emailId" id= "emailId" style="width: 150px;"/></td></tr>
	  	<tr><td></td></tr>
	  	<tr><td align="left" class="fieldname">Language</td><td> <select name="language" id="language">
	  	<option value="English">English</option>
	  	</select>*</td></tr>
	  </table>
	 </div>
    </div>
 </div>
 
</div>
</form>
</body>
</html>