
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

 Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 



    $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" %>
<%@ page import = "oracle.retail.stores.commerceservices.employee.EmployeeDTO"%>
<%@ page import = "oracle.retail.stores.webmodules.shared.ui.UIUtilsIfc" %>
<%@ page import = "oracle.retail.stores.webmodules.shared.ui.UIUtils" %>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag-12.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules" %>

<webmodules:localizedLink href="table/html-table.css" media="all" title="system"/>

<html:form action="/employee/searchEmployee" >

    <html:hidden property="employeeName" />
    <html:hidden property="employeeFormattedName" />
    <html:hidden property="employeeFirstName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="employeeId" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="employeeLastName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="employeeMiddleName" />

    <html:hidden property="employeeAlternateId" />
    <html:hidden property="employeeLoginId" value="xxx" /> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="employeeRole" />
    <html:hidden property="employeeStatus" />
    <html:hidden property="socialSecurityNumber" />
    <html:hidden property="employeeStatusCode" />
    <html:hidden property="employeeLocale" />
    <html:hidden property="groupID" />
    <% String tmpStoreID = (String)request.getSession().getAttribute("storeID"); %>
    <html:hidden property="employeeStoreId" value="<%= tmpStoreID %>"/>
    <html:hidden property="employeeValidity" value="0"/> <!--harcoded value just to satisfy struts validator-->

<script language="javascript">
 function formSubmit(employeeId)
 {
    document.forms[0].employeeId.value=employeeId;
    document.forms[0].searchEmployeeId.value=employeeId;
    document.forms[0].submit();
 }
 function formReset()
 {
     document.forms[0].searchEmployeeId.value="";
      document.forms[0].searchEmployeeFirstName.value="";
     document.forms[0].searchEmployeeLastName.value="";
 }
 function formCancel()
 {
    document.forms[0].action='<%=request.getContextPath()+"/dashboard/dashboard.do"%>';
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
 function isEnterKey(evt)
 {
     evt = (evt) ? evt : (window.event) ? window.event : "";
     var theKey;
     if (evt)
     {
         theKey = (evt.which) ? evt.which : evt.keyCode;
     }
     return (theKey == 13);
 }
 function selectSubmit(fld, event)
 {
     if (isEnterKey(event))
     {
         document.forms[0].submit();
         return false;
     }
     return true;
 }
</script>

<table width=100% cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat"><bean:message  key="employee.employeeSearchPage.title"/></td>
    <td>&nbsp;

    </td>
    </tr>
    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr>
    <td class="smallprompt">
        <logic:present name="messagePrompt">
            <html:messages id="msg" name="messagePrompt">
                  <bean:write name="msg"/>
            </html:messages>
        </logic:present>
        <logic:notPresent name="messagePrompt">
            <bean:message  key="employee.employeeSearchPage.message0"/>
        </logic:notPresent>
    </td>
    <td name=ButtonCell1>
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
        <tr>
        <td NOWRAP>
        <html:submit property="submitProperty"><bean:message  key="button.search"/></html:submit>
            <!--<html:button property="submitProperty" onclick="return formReset();"><bean:message  key="button.reset"/></html:button>
            <html:button property="submitProperty" onclick="return formCancel();"><bean:message  key="button.cancel"/></html:button> -->
</td>
        </tr>
        </table></td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr valign="top"><td colspan=2>

    <table name=CustomAreaEncloser width=100% class="tableoutline">
        <tr>
        <td>



<!-- Content Table -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
    <!-- Use a "table jack" to force a consistent height -->
    <tr valign="top">
        <td rowspan="3"><img src="table_jack.gif"></td>

            <td colspan="2" align="center" class="prompt">
                <html:errors property="searchEmployeeId"/>
                <html:errors property="searchEmployeeFirstName"/>
                <html:errors property="searchEmployeeLastName"/>
                <html:errors property="workGroupId"/>
            </td>
    </tr>
    <tr>
            <td colspan="4" align="center">
            <logic:notEmpty name="employeeForm" property="errorMessage">
            <table align="center" class="errortableoutline" cellpadding=3 cellspacing=3>
            <tr>
            <td><img src="/backoffice/images/red_exclamation.gif"/></td>
            <td><div class="errortext">
            <bean:write name="employeeForm" property="errorMessage"/><br>
            </div></td>
            </tr>
            </table>
            </logic:notEmpty>
            </td>
    </tr>
    <!-- Content Row -->
    <tr valign="middle">
        <td align="middle" colspan="2">
            <table width="90%" border="0">
                   <tr align="center">
                 <td>
                  <table border="0">
                     <tr>

            <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeId.label"/>:  </td>

                    <td align="left">
                    <html:text styleClass="data" property="searchEmployeeId" size="20" maxlength="10" tabindex="1" />

                    </td>
                  </tr>
                     <tr>
                   <td align="right" class="fieldname">--<bean:message  key="prompt.or"/>--</td>
                    <td align="right">&nbsp;</td>
                  </tr>
                     <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeFirstName.label"/>: </td>

                    <td align="left">
                    <html:text styleClass="data" property="searchEmployeeFirstName"  size="20" maxlength="30" tabindex="2" /></td>

                  </tr>
                     <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeLastName.label"/>:  </td>

                    <td align="left">
                    <html:text styleClass="data" property="searchEmployeeLastName"  size="20" maxlength="30" tabindex="3" /></td>

                  </tr>

                   <tr>
                   <td align="right" class="fieldname">--<bean:message  key="prompt.or"/>--</td>
                    <td align="right">&nbsp;</td>
                  </tr>

                <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeRole.label"/>:  </td>

                    <td align="left">
                    <html:select styleClass="selectlist" property="workGroupId" onkeydown="return selectSubmit(this, event)" value="">
                        <html:option value=""><bean:message  key="employee.employeeForm.employeeRoleNone"/></html:option>
                        <html:options collection="employeeRoleList" property="ID" labelProperty="name"/>
                    </html:select>
                    </td>

                  </tr>

                  </table>
                 </td>
                </tr>
           </table>
        </td>
    </tr>

    <table height=10 width="90%" cellpadding="0" cellspacing="0" border="0" align=center>
    <%
    if((request.getAttribute("employeeList")!=null))
    {
    %>

    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr align="left" class="fieldname"><td><bean:message key="employee.employeesearch.results.label"/></td><tr>
    <tr valign="top"><td colspan=2>
    <table name=CustomAreaEncloser  width=100% class="tableoutline"  border=0>
    <tr>
    <td>
    <!-- CR31480 Displaying search criteria here does not match requirements and is redundant.
    <td align="center">
        <table width="100%" border=0>
        <%
        if(!(request.getAttribute("employeeFirstName").toString()).equals(""))
        {
        %>
        <tr align="right" class="normal"><td width="40%"><bean:message  key="employee.employeeForm.employeeFirstName.label"/>:</td>
        <td align="left" width="40%" class="normal"><%=request.getAttribute("employeeFirstName").toString()%></td></tr>
        <tr align="right" class="normal"><td width="40%"><bean:message  key="employee.employeeForm.employeeLastName.label"/>:</td>
        <td align="left" width="40%" class="normal"><%=request.getAttribute("employeeLastName").toString()%></td></tr>
        <%
        }
        %>
        </table>

    </td>
    END CR31480 -->

    <td align="center">
        <table width=100%  height="35" border=0>
            <tr>
            <td align="center">
            <%
                UIUtilsIfc uiUtils = UIUtils.getInstance();
            %>
            <display:table class="tableWidth"   frame="HSIDES" cellspacing="2" name="employeeList"
            decorator="oracle.retail.stores.webmodules.employee.ui.EmployeeTableDecorator"
            requestURI='<%=request.getContextPath()+"/employee/searchEmployee.do"%>'
            pagesize="20">
            <display:caption class="tableHeader">
            <bean:message  key="employee.employeeForm.possibleMatches.label"/>
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
    <% }%>
    </table>
    <!-- Action Row -->
</table>

<!-- Close Form -->
</td>
        </tr>
        </table>
        </td></tr>

        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
<tr>

    <%
    if((request.getAttribute("employeeList")!=null))
    {
    %>
    <td>&nbsp;</td>
    <% }%>

    <td name=ButtonCell2 >
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">

        <tr>
        <td NOWRAP>
            <html:submit property="submitProperty"><bean:message  key="button.search"/></html:submit>
            <!--<html:button property="submitProperty" onclick="return formReset();"><bean:message  key="button.reset"/></html:button>
            <html:button property="submitProperty" onclick="return formCancel();"><bean:message  key="button.cancel"/></html:button> -->
        </td>
        </tr>
        </table></td>
    </tr>
    </table>

</html:form>

<script>
    document.forms[0].searchEmployeeId.focus();
</script>
