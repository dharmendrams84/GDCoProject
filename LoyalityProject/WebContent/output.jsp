<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
    
    
    <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">



	function PreselectDiscGroCode(itemToSelect ,emplStatusVal) {
	alert('item to select '+itemToSelect);
		// Get a reference to the drop-down
		var myDropdownList = document.myForm.emplDiscGrpCodes;

		// Loop through all the items
		for (var iLoop = 0; iLoop < myDropdownList.options.length; iLoop++) {
			if (myDropdownList.options[iLoop].value == itemToSelect) {
				// Item is found. Set its selected property, and exit the loop
				myDropdownList.options[iLoop].selected = true;
				break;
			}
		}

		
		 myDropdownList = document.myForm.emplDiscGrpCodes;

		// Loop through all the items
		for (var iLoop = 0; iLoop < myDropdownList.options.length; iLoop++) {
			if (myDropdownList.options[iLoop].value == emplStatusVal) {
				// Item is found. Set its selected property, and exit the loop
				myDropdownList.options[iLoop].selected = true;
				break;
			}
		}
	}
</script>


</head>

<body onLoad="PreselectDiscGroCode('${selectedDiscGrpCodeVal}')">
<form name="myForm" id="myForm">
JAI JAGANNATH
<%!List<String> list =  new ArrayList<String>() ;
String []tmp = new String[2];
String []emplDiscGrpCodesArray  ;
String strReportTo = "";

%>
	<%
		strReportTo = (String) request
				.getAttribute("selectedDiscGrpCodeVal ");
		emplDiscGrpCodesArray = (String[]) request
				.getAttribute("emplDiscGrpCodesArray");
	%>
	<select name = "emplDiscGrpCodes" id = "emplDiscGrpCodes">
	<%
		for (int i = 0; i < emplDiscGrpCodesArray.length; i++) {
	%>
	<option> <%=emplDiscGrpCodesArray[i]%></option>
	<%
		}
	%>
</select>

<input type="text" name="textName" id="textName"/>
</form>
</body>
</html>