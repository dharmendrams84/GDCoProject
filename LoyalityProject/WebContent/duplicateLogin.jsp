
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

  Copyright (c) 1998-2001 360Commerce, Inc.    All Rights Reserved.



    $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script language="javascript">


function formBack()
{
    if(document.forms[0].employeeStoreId.value!="000")
        document.forms[0].action='<%=request.getContextPath()+"/employee/addTempEmployeeView.do"%>';
    else
        document.forms[0].action='<%=request.getContextPath()+"/employee/addEmployeeView.do"%>';
    document.forms[0].submit();
}

</script>

<html:form action="/employee/addEmployeeView" >

<html:hidden property="employeeId"  />
<html:hidden property="employeeLoginId"  />
<html:hidden property="employeeFirstName"/>
<html:hidden property="employeeLastName"  />

<html:hidden property="employeeMiddleName"  />
<html:hidden property="employeeType" />
<html:hidden property="employeeRole"  />
<html:hidden property="employeeStatus"  />

<html:hidden property="employeeStoreId" />
    <html:hidden property="employeeValidity" />
    <html:hidden property="searchEmployeeId" />
    <html:hidden property="searchEmployeeFirstName" />
    <html:hidden property="searchEmployeeLastName" />

<table width=100% cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat"><bean:message key="employee.employeeForm.duplicateId.header"/></td>
    <td>&nbsp;</td>
    </tr>

    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>

    <tr>
    <td colspan=2>
    <table name=CustomAreaEncloser width=100% class="tableoutline">

    <tr><td class="normal" align="center"> <bean:message key="employee.employeeForm.duplicateId.message0"/></td></tr>
    <tr><td class="normal" align="center"> <bean:message key="employee.employeeForm.duplicateId.message1"/></td></tr>
    <tr><td class="normal"> &nbsp</td></tr>
    <tr><td class="normal"> &nbsp</td></tr>
    <tr><td class="ButtonCell1" align="center">
    <html:button property="submitProperty" onclick="return formBack();"><bean:message key="button.enter"/></html:button>
    </td></tr>

    </table>

    </td></tr>
    </table>


</html:form>

