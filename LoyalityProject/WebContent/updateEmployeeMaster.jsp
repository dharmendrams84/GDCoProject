
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

 Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 


    $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" import = "oracle.retail.stores.commerceservices.employee.EmployeeDTO, java.util.List"%>
<%@ page import="oracle.retail.stores.common.utility.LocaleMap" %>
<%@ page import="oracle.retail.stores.domain.utility.LocaleConstantsIfc" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules" %>

<html:form action="/employee/editEmployeeChoice" >

    <html:hidden property="employeeName" />
    <html:hidden property="employeeFormattedName" />
    <html:hidden property="employeeRole" />
    <html:hidden property="employeeRoleName" />
    <html:hidden property="employeeStatus" />
    <html:hidden property="socialSecurityNumber" />

    <html:hidden property="groupID" />
    <html:hidden property="employeeType" />

    <html:hidden property="searchEmployeeId" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeFirstName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeLastName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->

    <input type="hidden" name="method" value=""/>

<script language="javascript">

function formReset()
{
    document.forms[0].employeeFirstName.value="";
    document.forms[0].employeeMiddleName.value="";
    document.forms[0].employeeLastName.value="";
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

<table width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat">
    <%
    if( (session.getAttribute("employeeTypeCheck").toString()).equals("0") )
    {
    %>
    <bean:message  key="employee.employeeAddUpdatePage.title"/>
    <%
    }
    else
    {

    %>
    <bean:message  key="employee.employeeTemporaryPage.title"/>
    <%
    }
    %>
    </td>
    <td>&nbsp;

    </td>
    </tr>
    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr>
    <td width=450 class="smallprompt">
           <webmodules:isUserInRoleList roleList="modify_employee">
               <bean:message  key="employee.employeeAddUpdatePage.selectHeader"/>
           </webmodules:isUserInRoleList>
           <webmodules:isUserInRoleList roleList="reset_employee_password">
               <bean:message  key="employee.employeeAddUpdatePage.updateHeader"/>
           </webmodules:isUserInRoleList>
    </td>
    <%
    if(request.isUserInRole("modify_employee") || request.isUserInRole("reset_employee_password"))
    {
    %>
    <td name=ButtonCell1>
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
        <tr>
        <td NOWRAP>
        <webmodules:isUserInRoleList roleList="reset_employee_password">
            <html:submit onclick="document.forms[0].method.value='resetEmployeePassword';"><bean:message key="employee.button.reset.pwd"/></html:submit>
        </webmodules:isUserInRoleList>
        <webmodules:isUserInRoleList roleList="modify_employee">
            <html:submit property="submitProperty" onclick="document.forms[0].method.value='save';"><bean:message key="button.save"/></html:submit>
        </webmodules:isUserInRoleList>
    <!--	<html:button property="submitProperty"  onclick="return formBack();"><bean:message  key="button.back"/></html:button>
        <html:button property="submitProperty" onclick="return formReset();"><bean:message  key="button.reset"/></html:button>
        <html:button property="submitProperty" onclick="return formCancel();"><bean:message  key="button.cancel"/></html:button> -->
        </td>
        </tr>
        </table>
    </td>
    <%
    }
    %>
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

                <html:errors />

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

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeFirstName.label"/>: </td>

                    <td align="left">
                    <%
                    if(request.isUserInRole("modify_employee"))
                    {
                    %>
                    <html:text styleClass="data" property="employeeFirstName"  size="22" maxlength="30" tabindex="1" /> *
                    <%
                    }
                    else
                    {
                    %>
                    <html:hidden styleClass="data" property="employeeFirstName"  write="true" />
                    <%
                    }
                    %>
                    </td>

                  </tr>

                   <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeMiddleName.label"/>:  </td>

                    <td align="left">
                    <%
                    if(request.isUserInRole("modify_employee"))
                    {
                    %>
                    <html:text styleClass="data" property="employeeMiddleName"  size="22" maxlength="30" tabindex="2" />
                    <%
                    }
                    else
                    {
                    %>
                    <html:hidden styleClass="data" property="employeeMiddleName"  write="true" />
                    <%
                    }
                    %>
                    </td>

                  </tr>

                     <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeLastName.label"/>:  </td>

                    <td align="left">
                    <%
                    if(request.isUserInRole("modify_employee"))
                    {
                    %>
                    <html:text styleClass="data" property="employeeLastName"  size="22" maxlength="30" tabindex="3" /> *
                    <%
                    }
                    else
                    {
                    %>
                    <html:hidden styleClass="data" property="employeeLastName"  write="true" />
                    <%
                    }
                    %>
                    </td>

                  </tr>


                    <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeId.label"/>:  </td>

                    <td align="left">
                    <html:hidden styleClass="data" property="employeeId" write="true" />

                    </td>
                  </tr>





                    <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeLoginId.label"/>:  </td>

                    <td align="left">
                    <%
                    if(request.isUserInRole("modify_employee"))
                    {
                    %>
                    <html:text styleClass="data" readonly="true" property="employeeLoginId"  size="22" maxlength="10" tabindex="4" /> *
                    <%
                    }
                    else
                    {
                    %>
                    <html:hidden styleClass="data" property="employeeLoginId"  write="true" />
                    <%
                    }
                    %>

                    </td>
                  </tr>

                    <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeRole.label"/>:  </td>

                    <td align="left">
                    <%
                    if(request.isUserInRole("modify_employee"))
                    {
                    %>
                    <html:select styleClass="selectlist" property="workGroupId" tabindex="7">
                    <html:options collection="employeeRoleList" property="ID" labelProperty="name"/>
                    </html:select>
                    <%
                    }
                    else
                    {
                    %>
                    <% String tmpRoleName = (String)request.getSession().getAttribute("employeeRoleName"); %>
                    <html:hidden styleClass="data" property="employeeRoleName" write="true" value="<%= tmpRoleName %>"/>
                    <%
                    }
                    %>
                    </td>

                  </tr>

                   <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeStatus.label"/>:  </td>

                    <td align="left">
                    <%
                    if(request.isUserInRole("modify_employee"))
                    {
                    %>
                    <html:select styleClass="selectlist" property="employeeStatusCode" tabindex="8">
                    <html:option value="0" key="employee.employeeForm.employeeStatus.option0.value"/>
                    <html:option value="1" key="employee.employeeForm.employeeStatus.option1.value"/>
                </html:select>
                    <%
                    }
                    else
                    {
                    %>
                    <html:hidden styleClass="data" property="employeeStatus"  write="true" />
                    <%
                    }
                    %>

                    </td>
                  </tr>
                  <%
                        Locale locale = (Locale) LocaleMap.getLocale(LocaleMap.DEFAULT);
                    String empLocale = LocaleMap.getBestMatch(locale).toString();
                    if(request.getAttribute("employeeLocale") != null)
                    {
                        empLocale = request.getAttribute("employeeLocale").toString();
                    }
                  %>
                     <tr>
                         <td align="right" class="fieldname"><bean:message key="employee.employeeForm.employeePreferredLanguage.label"/>:</td>
                         <td>
                             <%
                             if(request.isUserInRole("modify_employee"))
                             {%>
                             <html:select styleClass="selectlist" property="employeeLocale" value="<%=empLocale%>"  tabindex="9">
                                 <html:options collection="supportedLocales" property="localeString" labelProperty="displayName"/>
                             </html:select>
                             <%}
                             else
                             {%>
                             <html:select styleClass="selectlist" property="employeeLocale" value="<%=empLocale%>"  tabindex="9" disabled="true">
                                 <html:options collection="supportedLocales" property="localeString" labelProperty="displayName"/>
                             </html:select>
                             <%}%>
                         </td>
                     </tr>
                   <%
                    if((session.getAttribute("employeeTypeCheck").toString()).equals("0") )
                    {
                    %>
                    <html:hidden property="employeeActualStatusCode" />
                    <% String tmpStoreID = (String)request.getSession().getAttribute("storeID"); %>
                    <html:hidden property="employeeStoreId" value="<%= tmpStoreID %>"/>

                    <html:hidden property="employeeValidity" value="0"/>
                    <%
                    }
                    else if( (session.getAttribute("employeeTypeCheck").toString()).equals("1") )
                    {
                    %>
                  <tr>

            <td align="right" class="fieldname">  <bean:message  key="employee.employeeTemporaryPage.employeeValidity.label"/>:  </td>

                    <td align="left">
                    <html:select styleClass="data" property="employeeValidity">
                    <html:option value="0" />
                    <html:option value="1" />
                    <html:option value="2" />
                    <html:option value="3" />
                    <html:option value="4" />
                    <html:option value="5" />
                    <html:option value="6" />
                    <html:option value="7" />
                    <html:option value="8" />
                    <html:option value="9" />
                    <html:option value="10" />
                    <html:option value="11" />
                    <html:option value="12" />
                    <html:option value="13" />
                    <html:option value="14" />
                    <html:option value="15" />
                    </html:select>



                    <html:hidden property="employeeStatusCode" /> <!--harcoded value just to satisfy struts validator-->
                    <html:hidden property="employeeActualStatusCode" /> <!--harcoded value just to satisfy struts validator-->

                    </td>
                  </tr>



                   <%}%>


                  </table>

                 </td>
                </tr>
           </table>

        </td>
    </tr>
    <!-- Action Row -->
    <%
    if(request.isUserInRole("modify_employee"))
    {
    %>
    <tr valign="bottom">
        <td align="left" class="label">
        <span class="messagetext">
         <bean:message key="employee.employeeForm.required.label"/>
        </span></td>
    </tr>
    <%
    }
    %>
</table>

<!-- Close Form -->
</td>
        </tr>
        </table>
        </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
<tr>
    <td>&nbsp;</td>
    <%
    if(request.isUserInRole("modify_employee") || request.isUserInRole("reset_employee_password"))
    {
    %>
    <td name=ButtonCell2 >
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">

        <tr>
        <td NOWRAP>
            <webmodules:isUserInRoleList roleList="reset_employee_password">
                <html:submit onclick="document.forms[0].method.value='resetEmployeePassword';"><bean:message key="employee.button.reset.pwd"/></html:submit>
            </webmodules:isUserInRoleList>
            <webmodules:isUserInRoleList roleList="modify_employee">
                <html:submit property="submitProperty" onclick="document.forms[0].method.value='save';"><bean:message key="button.save"/></html:submit>
            </webmodules:isUserInRoleList>
        <!--<html:button property="submitProperty"  onclick="return formBack();"><bean:message  key="button.back"/></html:button>
        <html:button property="submitProperty" onclick="return formReset();"><bean:message  key="button.reset"/></html:button>
        <html:button property="submitProperty" onclick="return formCancel();"><bean:message  key="button.cancel"/></html:button> -->
        </td>
        </tr>
        </table>
    </td>
    <%
    }
    %>
</tr>
</table>

</html:form>

<script>
    document.forms[0].employeeFirstName.focus();
</script>
