
<% /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

 Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 

  $Revision: /rgbustores_13.4x_generic_branch/1 $
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */ %>
<%@ page language = "java" contentType = "text/html; charset=UTF-8" import = "oracle.retail.stores.commerceservices.employee.EmployeeDTO, java.util.List,oracle.retail.stores.domain.employee.EmployeeTypeEnum"%>
<%@ page import="oracle.retail.stores.common.utility.LocaleMap" %>
<%@ page import="oracle.retail.stores.domain.utility.LocaleConstantsIfc" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<html:form action="/employee/addEmployee" >
    <html:hidden property="employeeName" />
    <html:hidden property="employeeFormattedName" />
    <html:hidden property="employeeRole" />
    <html:hidden property="employeeStatus" />
    <html:hidden property="socialSecurityNumber" />
    <html:hidden property="groupID" />
    <html:hidden property="employeeType" value="<%=Integer.toString(EmployeeTypeEnum.STANDARD.getDBVal())%>"/>
    <html:hidden property="employeeStoreId" value="000"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="employeeValidity" value="0"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeId" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeFirstName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeLastName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
<table width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="white">
    <tr height="15" name="TitleBuffer"><td colspan=2>&nbsp;</td></tr>
    <tr name="TitleRow" valign="middle" height="35">
    <td align=left class="pageheadercat"><bean:message  key="employee.employeeAddEmployeePage.title"/></td>
    <td>&nbsp;</td>
    </tr>
    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr>
    <td class="smallprompt">
         <bean:message  key="employee.employeeAddUpdatePage.selectHeader"/>
    </td>
    <td name=ButtonCell1>
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
        <tr>
        <td NOWRAP>
        <html:submit property="submitProperty" tabindex="9"><bean:message  key="button.save"/></html:submit>
    </td>
        </tr>
        </table></td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr valign="top"><td colspan=2>

    <table name=CustomAreaEncloser width=100% class="tableoutline">
        <tr>
        <td>

<!-- Hidden Fields -->
<input type="hidden" name="userName" value="">
<input type="hidden" name="store" value="">

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
                    <html:text styleClass="data" property="employeeFirstName"  size="22" maxlength="30" tabindex="1" /> *</td>

                  </tr>

                   <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeMiddleName.label"/>:  </td>

                    <td align="left">
                    <html:text styleClass="data" property="employeeMiddleName"  size="22" maxlength="30" tabindex="2" /></td>

                  </tr>

                     <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeLastName.label"/>:  </td>

                    <td align="left">
                    <html:text styleClass="data" property="employeeLastName"  size="22" maxlength="30" tabindex="3" /> *</td>

                  </tr>

                    <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeId.label"/>:  </td>

                    <td align="left">
                    <html:hidden property="employeeId" write="true" styleClass="data"/>

                    </td>
                  </tr>

                    <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeLoginId.label"/>:  </td>

                    <td align="left">
                    <html:text styleClass="selectlist"  property="employeeLoginId"  size="22" maxlength="10" tabindex="4" /> *

                    </td>
                  </tr>

                  <tr>

                    <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeRole.label"/>:  </td>

                    <td align="left">
                    <html:select styleClass="selectlist" property="workGroupId"  tabindex="5">
                    <html:options collection="employeeRoleList" property="ID" labelProperty="name"/>
                    </html:select>
                    </td>

                  </tr>

                   <tr>

            <td align="right" class="fieldname">  <bean:message  key="employee.employeeForm.employeeStatus.label"/>:  </td>

                    <td align="left">
                    <html:select property="employeeStatusCode" tabindex="6" styleClass="selectlist">

                        <html:option value="1" key="employee.employeeForm.employeeStatus.option1.value"/>
                        <html:option value="0" key="employee.employeeForm.employeeStatus.option0.value"/>

                    </html:select>

                    </td>
                  </tr>
                    <%
                      Locale locale = (Locale) LocaleMap.getLocale(LocaleMap.DEFAULT);
                      String empLocale = LocaleMap.getBestMatch(locale).toString();
                    %>
                      <tr>
                          <td align="right" class="fieldname"><bean:message key="employee.employeeForm.employeePreferredLanguage.label"/>:</td>
                          <td>
                             <%
                              if(request.isUserInRole("modify_employee"))
                              {%>
                              <html:select styleClass="selectlist" property="employeeLocale" value="<%=empLocale%>" tabindex="7">
                                  <html:options collection="supportedLocales" property="localeString" labelProperty="displayName"/>
                              </html:select>
                             <%}
                              else
                              {%>
                              <html:select styleClass="selectlist" property="employeeLocale" value="<%=empLocale%>" tabindex="7" disabled="true">
                                  <html:options collection="supportedLocales" property="localeString" labelProperty="displayName"/>
                              </html:select>
                             <%}%>
                          </td>
                      </tr>

                  </table>
                 </td>
                </tr>
           </table>
        </td>
    </tr>
    <!-- Action Row -->
    <tr valign="bottom">
         <td align="left" class="label">
        <span class="messagetext">
         <bean:message key="employee.employeeForm.required.label"/>
        </span></td>
    </tr>
</table>

<!-- Close Form -->
</td>
        </tr>
        </table>
        </td></tr>
        <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
<tr>
    <td>&nbsp;</td>
    <td name=ButtonCell2 >
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">

        <tr>
        <td NOWRAP>
        <html:submit property="submitProperty" tabindex="10" ><bean:message  key="button.save"/></html:submit>
        </td>
        </tr>
        </table></td>
    </tr>
    </table>

</html:form>


<script>
    document.forms[0].employeeFirstName.focus();
</script>
