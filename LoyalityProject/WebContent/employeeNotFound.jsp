
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

  Copyright (c) 1998-2001 360Commerce, Inc.    All Rights Reserved.



  $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>



<html:form action="/employee/searchEmployeeView" >

<html:hidden property="employeeId" />
<html:hidden property="employeeFirstName" />
<html:hidden property="employeeLastName"  />

<html:hidden property="employeeMiddleName"  />
<html:hidden property="employeeAccessPassword"  />
<html:hidden property="employeeRole" />
<html:hidden property="employeeStatus" />

<html:hidden property="employeeStoreId" />
    <html:hidden property="employeeValidity" />
    <html:hidden property="searchEmployeeId" />
    <html:hidden property="searchEmployeeFirstName" />
    <html:hidden property="searchEmployeeLastName" />

<script language="javascript">

</script>
<table width=100% cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat"><bean:message key="employee.employeeForm.error.label"/></td>
    <td>&nbsp;</td>
    </tr>

    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>

    <tr>
    <td colspan=2>
    <table name=CustomAreaEncloser width=100% class="tableoutline">

    <tr><td class="label" align="center"> <bean:message key="employee.employeeNotFoundPage.header"/></td></tr>
    <tr><td class="label"> &nbsp</td></tr>
    <tr><td class="label"> &nbsp</td></tr>
    <tr><td class="ButtonCell1" align="center">
    <html:submit property="submit"><bean:message key="button.back"/></html:submit>
    </td></tr>

    </table>

    </td></tr>
    </table>


</html:form>

