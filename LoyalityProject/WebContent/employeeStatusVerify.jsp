
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

  Copyright (c) 1998-2001 360Commerce, Inc.    All Rights Reserved.



  $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>



<html:form action="/employee/searchEmployeeView" >

<html:hidden property="searchEmployeeId"  />
<html:hidden property="employeeName"  />
<html:hidden property="employeeFormattedName"  />
<html:hidden property="employeeFirstName"  />
<html:hidden property="employeeMiddleName"  />
<html:hidden property="employeeLastName"  />
<html:hidden property="employeeId"  />
<html:hidden property="employeeLoginId"  />
    <html:hidden property="employeeRole"  />
    <html:hidden property="employeeStatus"  />
    <html:hidden property="employeeStatusCode"  />
    <html:hidden property="workGroupId"  />
    <html:hidden property="employeeLocale"  />
    <html:hidden property="groupID"  />
    <html:hidden property="employeeType" />
    <html:hidden property="employeeStoreId"  />
    <html:hidden property="employeeValidity"  />
    <html:hidden property="employeeActualStatusCode"  />

<script language="javascript">
function formForward()
{
    document.forms[0].employeeActualStatusCode.value = document.forms[0].employeeStatusCode.value;
    document.forms[0].action='<%=request.getContextPath()+"/employee/saveEmployee.do"%>';
    document.forms[0].submit();
}
function formBack()
{
    document.forms[0].action='<%=request.getContextPath()+"/employee/searchEmployee.do"%>';
    document.forms[0].submit();
}

</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat"><bean:message key="employee.employeeForm.confirmEmployeeStatusPage.title"/></td>
    <td>&nbsp;</td>
    </tr>

    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>

    <tr>
    <td colspan=2>
    <table name=CustomAreaEncloser width=100% class="tableoutline">

    <tr><td class="normal" align="center"> <bean:message key="employee.employeeForm.confirmEmployeeStatusPage.message0"/></td></tr>
    <tr><td class="normal" align="center"> <bean:message key="employee.employeeForm.confirmEmployeeStatusPage.message1"/></td></tr>
    <tr><td class="normal" align="center"> <bean:message key="employee.employeeForm.confirmEmployeeStatusPage.message2"/></td></tr>
    <tr><td class="normal"> &nbsp</td></tr>
    <tr><td class="normal"> &nbsp</td></tr>
    <tr><td class="ButtonCell1" align="center">
    <html:button property="submitProperty" onclick="return formForward();"><bean:message  key="button.yes"/></html:button>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    <html:button property="submitProperty" onclick="return formBack();"><bean:message  key="button.no"/></html:button>
    </td></tr>

    </table>

    </td></tr>
    </table>


</html:form>

