<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>



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


 
 </style>
 <script type="text/javascript">
 
 		function clearFields(){
 			document.getElementById('message').value="";
 			document.getElementById('emplFirstName').value="";
 		}
 		
 
		function showHide(div1, div2) {

			document.getElementById(div1).style.display = "block";
			document.getElementById(div2).style.display = "none";
		}
	</script>
</head>
<body>
	<form action="employee.do" name="helloWorldForm" method="post">

 No Employee found for employee id ${requestScope.emplNumber} 
	
</form>
</body>
</html>