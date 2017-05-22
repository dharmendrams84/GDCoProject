<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="gdyn.retail.stores.webmodules.employee.ui.GDYNEmployeeSearchForm"%>

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

.pageheadercat {
	color: 3a5a87;
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
	border: #99bedc;
	border-style: solid;
	border-top-width: 1px solid;
	border-right-width: 1px solid;
	border-bottom-width: 1px solid;
	border-left-width: 1px solid;
}

.heading2 {
	color: #3a5a87;
	font-size: 12pt;
	font-family: Arial, Helvetica;
	font-weight: bold;
}

.fieldname {
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
}
</style>
 

 
 </style>
 <script type="text/javascript">
 
 
		/* function loadPage() {
			alert("Window loaded");
			clearFields();
		}
		window.onload = loadPage;
 */
		function clearFields() {
			document.getElementById('emplNumber').value = "";
			document.getElementById('emplFirstName').value = "";
		}

		function showHide(div1, div2) {

			document.getElementById(div1).style.display = "block";
			document.getElementById(div2).style.display = "none";
		}

		function validateForm() {
			
			//alert(document.employeeSearchForm.empFirstNameRadioBtn.value);
			if (document.employeeSearchForm.empFirstNameRadioBtn.value == 'emplNumber') {
				document.employeeSearchForm.emplFirstName.value="";
				var x = document.employeeSearchForm.emplNumber.value;					
				if (x == null || x == "") {
					alert("Enter value for employee number");
					return false;
				}
			} else {
				//alert('inside else value');
				var x = document.employeeSearchForm.emplFirstName.value;
				document.employeeSearchForm.emplNumber.value="";					
				if (x == null || x == "") {
					alert("Enter value for employee first name");
					return false;
				}
			}
			/*  if (x == null || x == "") {
			     alert("Enter value for employee number");
			     return false;
			 } */
			return true;
		}
	</script>
</head>
<body>
	<form action="<%=searchAction%>" name="employeeSearchForm" id="employeeSearchForm" method="post" onsubmit="return validateForm()">

		<table id="newEmplDiv" width="100%">
			<tbody>
				<tr>
					<td width="40%" style="color: #3a5a87;"><span
						class="pageheadercat">Employee Search</span></td>

				</tr>
				<tr bgcolor="#cccccc">
					<td colspan="2"></td>
				</tr>
			</tbody>
		</table>

<table style="width: 100%; height: 65px; top: 5px;">
			<tr>

				<td class="smallprompt">
				Select the radio button to include the area's information in the search, enter criteria and press Search.
				</td>
				<td align="right"><input type="submit" value="Search" /> <input
					type="button" value="ClearSearch" onclick="clearFields()" /></td>
			</tr>
		</table>
		<!-- 
		<div id="empSearchDiv" style="border-bottom:1px solid; height: 50px;">
	 <span style="top:30px; position: relative; font-weight: bold; ">Employee Search</span>
	</div>	 -->
		<!-- <div id="searchBtnDiv"
			style="height: 40px; position: relative; top: 15px;">
			<table style="width: 100%;">
				<tr>
					<th align="left">Select the radio button to include the area's
						information in the search, enter criteria and press Search.</th>
					<th align="right"><input type="submit" value="Search" /> <input
						type="button" value="ClearSearch" onclick="clearFields()" /></th>
				</tr>
			</table>
		</div> -->
		<div id="outerDiv" style="border: 1px solid; height :500px; position: relative; top: 10px; border-color: #99bedc;">
	 <div id= "empFirstNameRdioDiv" style="height: 40px; position: relative; top: 10px; border-bottom: 2px solid; border-color: #99bedc;">
				<table>
					<tr>
						<td class="smallprompt"><input type="radio"
							name="empFirstNameRadioBtn" id="empFirstNameRadioBtn"
							value="emplFirstName"
							onclick="showHide('emplFirstNameDiv','enterEmplNumberDiv')" />

							Employee FirstName</td>
					</tr>
				</table>

			<!-- 	<input type="radio" name="empFirstNameRadioBtn" id="empFirstNameRadioBtn" value="emplFirstName" onclick="showHide('emplFirstNameDiv','enterEmplNumberDiv')"/> 
	 <span style="position: relative; font-weight: bold; "> Employee FirstName</span> -->
	 </div>
	 <div id= "emplFirstNameDiv" style="position: relative;top: 20px;display: none; height: 40px; ">
				<table>
					<tr>
						<td class="smallprompt">Enter Employee First Name 
						<input type="text" name="emplFirstName" id = "emplFirstName" style="width: 150px;">
						</td>
					</tr>
				</table>
				<!-- <span style="font-weight: bold; left: 20px;">Enter Employee First Name
	 <input type="text" name="emplFirstName" id = "emplFirstName" style="width: 150px;"></span> -->
	 </div>
	 <div id= "emplNumberDiv" style="height: 40px; position: relative;">
	 <div id = "emplNumberTxtDiv" style= "position:relative; top: 20px;   border-bottom: 2px solid; border-color: #99bedc;">
					<table>
						<tr>
							<td class="smallprompt"><input type="radio"
								name="empFirstNameRadioBtn" id="empFirstNameRadioBtn"
								checked="checked" value="emplNumber"
								onclick="showHide('enterEmplNumberDiv','emplFirstNameDiv')" />
								Employee Number</td>
						</tr>
					</table>

					<!-- <input type="radio" name="empFirstNameRadioBtn" id="empFirstNameRadioBtn" checked="checked" value="emplNumber" onclick="showHide('enterEmplNumberDiv','emplFirstNameDiv')"/>
	 <span style="font-weight: bold;"> Employee Number</span> -->
	 </div>	 
	 </div>
	 <div id = "enterEmplNumberDiv" style="position: relative; top: 20px;" >

				<table>
					<tr>
						<td class="smallprompt">Enter Employee Number <input type="text" name="emplNumber"  id = "emplNumber"style="width: 150px; left: 30px;"/>
						</td>
					</tr>
				</table>    
	 </div>
	 
	 </div> 
	
</form>
</body>
</html>