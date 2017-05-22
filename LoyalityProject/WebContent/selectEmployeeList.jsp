
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

  Copyright (c) 1998-2001 360Commerce, Inc.    All Rights Reserved.



    $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" %>
<%@ page import = "oracle.retail.stores.commerceservices.employee.EmployeeDTO" %>
<%@ page import = "java.util.List" %>
<%@ page import = "oracle.retail.stores.webmodules.shared.ui.UIUtilsIfc" %>
<%@ page import = "oracle.retail.stores.webmodules.shared.ui.UIUtils" %>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag-12.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules" %>

<webmodules:localizedLink href="table/html-table.css" media="all" title="system"/>

<script language="javascript">

function formSubmit(employeeId)
{
    document.forms[0].employeeId.value=employeeId;
    document.forms[0].searchEmployeeId.value=employeeId;
    document.forms[0].submit();
}
function formBack()
{
    document.forms[0].searchEmployeeId.value="";
    document.forms[0].searchEmployeeFirstName.value="";
    document.forms[0].searchEmployeeLastName.value="";
    document.forms[0].action='<%=request.getContextPath()+"/employee/searchEmployeeView.do"%>';
    document.forms[0].submit();
}
function formCancel()
{
    document.forms[0].action='<%=request.getContextPath()+"/dashboard/dashboard.do"%>';
    document.forms[0].submit();
}
</script>

<html:form action="/employee/searchEmployee.do" >


    <html:hidden property="employeeName" />
    <html:hidden property="employeeFormattedName" />
    <html:hidden property="employeeFirstName" />
    <html:hidden property="employeeMiddleName" />
    <html:hidden property="employeeLastName" />
    <html:hidden property="employeeId" />
    <html:hidden property="employeeAlternateId" />
    <html:hidden property="employeeLoginId" />
    <html:hidden property="employeeRole" />
    <html:hidden property="employeeStatus" />
    <html:hidden property="employeeActualStatusCode" />
    <html:hidden property="socialSecurityNumber" />
    <html:hidden property="employeeStatusCode" />
    <html:hidden property="workGroupId" />
    <html:hidden property="employeeLocale" />
    <html:hidden property="groupID" />
    <html:hidden property="employeeType" />
    <html:hidden property="employeeStoreId" />
    <html:hidden property="employeeValidity" />
    <html:hidden property="searchEmployeeId" />
    <html:hidden property="searchEmployeeFirstName" />
    <html:hidden property="searchEmployeeLastName"/>



<table width=100% cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat"><bean:message  key="employee.employeeSelectPage.title"/></td>
    <td>&nbsp;</td>
    </tr>

    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>


<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr>
    <td class="smallprompt"><bean:message  key="employee.employeeSelectPage.header"/></td>
    <td name=ButtonCell1>
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
        <tr>
        <td NOWRAP>
        <html:button property="submitProperty"  onclick="return formBack();"><bean:message  key="button.back"/></html:button>
        <html:button property="submitProperty" onclick="return formCancel();"><bean:message  key="button.cancel"/></html:button>
</td>
        </tr>
        </table></td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr valign="top"><td colspan=2>



    <table name=CustomAreaEncloser  width=100% class="tableoutline">
    <tr>
    <td align="center">

    <table width="100%" >
    <%
    if(!(request.getAttribute("employeeFirstName").toString()).equals(""))
    {
    %>
    <tr align="right" class="normal"><td width="40%"><bean:message  key="employee.employeeForm.employeeFirstName.label"/>:</td>
    <td align="left" width="40%"><%=request.getAttribute("employeeFirstName").toString()%></td></tr>
    <tr align="right" class="normal"><td width="40%"><bean:message  key="employee.employeeForm.employeeLastName.label"/>:</td>
    <td align="left" width="40%"><%=request.getAttribute("employeeLastName").toString()%></td></tr>
    <%
    }
    %>
    </table>

    </td>
    </tr>

    <tr><td>&nbsp;</td></tr>
    <tr>
    <td align="center">
    <table width="100%" >
    <tr>
    <td>
    <display:table class="tableWidth"   frame="HSIDES" cellspacing="1" name="employeeList"
    decorator="oracle.retail.stores.webmodules.employee.ui.EmployeeTableDecorator"
    requestURI='<%=request.getContextPath()+"/employee/searchEmployee.do"%>'
    pagesize="20">
    <display:caption class="tableHeader">
    <bean:message  key="employee.employeeForm.possibleMatches.label"/>
    <%
        UIUtilsIfc uiUtils = UIUtils.getInstance();

    %>

    </display:caption>
        <display:column property="employeeFormattedName" title='<%=uiUtils.getString(request, "employee.column.title.name")%>' class="alignWidth"/>
        <display:column property="employeeStatus" title='<%=uiUtils.getString(request, "employee.column.title.status")%>' class="alignWidth"/>
        <display:column property="employeeId" title='<%=uiUtils.getString(request, "employee.column.title.id")%>' class="alignWidth"/>
        <display:column property="workGroupName" title='<%=uiUtils.getString(request, "employee.column.title.role")%>' class="alignWidth"/>

        <display:setProperty name="paging.banner.placement" value="bottom" />
        <display:setProperty name="paging.banner.page.separator" value=" " />
        <display:setProperty name="paging.banner.onepage" value=""/>
        <display:setProperty name="basic.empty.showtable" value="true" />
        <display:setProperty name="basic.msg.empty_list" value='<%=uiUtils.getString(request, "basic.msg.empty_list")%>' />
        <display:setProperty name="paging.banner.no_items_found" value="" />
        <display:setProperty name="paging.banner.one_item_found" value='<%=uiUtils.getString(request, "paging.banner.one_item_found")%>' />
        <display:setProperty name="paging.banner.all_items_found" value='<%=uiUtils.getString(request, "paging.banner.all_items_found")%>'/>
        <display:setProperty name="paging.banner.some_items_found" value='<%=uiUtils.getString(request, "paging.banner.some_items_found")%>'/>
        <display:setProperty name="paging.banner.full" value='<%=uiUtils.getString(request, "paging.banner.full")%>'/>
        <display:setProperty name="paging.banner.first" value='<%=uiUtils.getString(request, "paging.banner.first")%>'/>
        <display:setProperty name="paging.banner.last" value='<%=uiUtils.getString(request, "paging.banner.last")%>'/>
    </display:table>

    </td>
    </tr>
    </table>


    </td></tr>


    </table>




    </td></tr>

    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr>
    <td>&nbsp;</td>
    <td name=ButtonCell1>
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
        <tr>
        <td NOWRAP>
        <html:button property="submitProperty"  onclick="return formBack();"><bean:message  key="button.back"/></html:button>
        <html:button property="submitProperty" onclick="return formCancel();"><bean:message  key="button.cancel"/></html:button>
</td>
        </tr>
        </table></td>
    </tr>
    </table>


</html:form>

