<%-- Copyright (c) 2006, 2008, Oracle. All Rights Reserved. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script language="JavaScript">

</script>

<form>
<table width="100%" border="0" cellspacing="3" cellpadding="3">

    <tr>
        <td width="40%" class="pageheadercat"><bean:message  key="security.selectusertoedit.heading"/></td>
        <td></td>
    </tr>
    <tr bgcolor="#CCCCCC">
        <td colspan="2"></td>
    </tr>
    <tr>
        <td class="smallprompt"><bean:message key="security.selectusertoedit.prompt"/></td>
        <td valign="top" align="right">
  <% if (request.isUserInRole("users_remove")) { %>

            <table class="tableoutline" cellpadding="3" cellspacing="3">
                <tr>
                    <td NOWRAP>
                        <input type="submit" name="Remove" disabled="true" value="<bean:message  key="button.remove"/>">
                    </td>
                </tr>
            </table>
  <%  } %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <table width="100%" class="tableoutline">
                <tr>
                    <td>
                        <html:errors/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="93%" border="0" cellspacing="1" cellpadding="3">
                            <tr>
                                <td colspan="2" align="left" class="heading2"><bean:message  key="security.selectusertoedit.header.search"/></td>
                            </tr>
                            <tr>
                                <td colspan="2" bgcolor="cccccc"></td>
                            </tr>
                            <tr>
                                <td width="17%" align="right" class="fieldname"><bean:message  key="security.selectusertoedit.label.firstname"/>:</td>
                                <td class="normal" align="left">
                                    <logic:present name="searchFirstName">
                                        <bean:write name="searchFirstName"/>
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                                <td width="17%" class="fieldname" align="right"><bean:message  key="security.selectusertoedit.label.lastname"/>:</td>
                                <td class="normal" align="left">
                                    <logic:present name="searchLastName">
                                        <bean:write name="searchLastName"/>
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="left" class="heading2"><br>
                                    <bean:message  key="security.selectusertoedit.header.results"/>
                                    <bean:write name="numEmployees"/>
                                    <bean:message  key="security.selectusertoedit.header.of"/>
                                    <bean:write name="numEmployees"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <table width="95%" border="1" frame="HSIDES" rules="ROWS" bordercolorlight="WHITE" bordercolor="#CCCCCC" cellspacing="1">
                            <tr>
      <% if (request.isUserInRole("users_remove")) { %>

                                <td width="5%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.select"/></td>
                                <td width="15%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.lastname"/></td>
      <% } else { %>

                                <td width="20%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.lastname"/></td>
      <% } %>

                                <td width="15%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.firstname"/></td>
                                <td width="15%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.middlename"/></td>
                                <td width="15%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.userid"/></td>
                                <td width="15%" class="tableheadrow"><bean:message  key="security.selectusertoedit.row.status"/></td>
                            </tr>
                            <logic:iterate id="employee" name="employees">
                            <tr>
      <% if (request.isUserInRole("users_remove")) { %>

                                <td class="normal" align="left">
                                    <html:multibox property="selectedUsers">
                                    <bean:write name="employee" property="employeeId"/>
                                    </html:multibox>
                                </td>
      <% } %>

                                <td class="normal" align="left">
                                    <bean:write name="employee" property="employeeLastName"/>
                                </td>
                                <td class="normal" align="left">
                                    <bean:write name="employee" property="employeeFirstName"/>
                                </td>
                                <td class="normal" align="left">
                                    <bean:write name="employee" property="employeeMiddleName"/>
                                </td>
                                <td class="normal" align="left">
                                    <html:link page="/security/editUser.do" paramId="employeeId" paramName="employee" paramProperty="employeeId">
                                    <bean:write name="employee" property="employeeLoginId"/>
                                    </html:link>
                                </td>
                                <td class="normal" align="left">
                                    <bean:define id="empStatus" name="employee" property="employeeStatus" type="java.lang.String" />
                                    <%
                                        String empStatusKey = "security.edituser.status." + empStatus.toLowerCase();
                                    %>
                                    <bean:message key="<%= empStatusKey %>"/>
                                </td>
                            </tr>
                            </logic:iterate>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2" valign="top">
            <div align="right">
  <% if (request.isUserInRole("users_remove")) { %>

                <table class="tableoutline" cellpadding="3" cellspacing="3">
                    <tr>
                        <td NOWRAP>
                            <input type="submit" name="Remove" disabled="true" value="<bean:message  key="button.remove"/>">
                        </td>
                    </tr>
                </table>
  <% } %>
        </td>
    </tr>
</table>
</form>
<script>
    document.forms[0].selectedUsers[0].focus();
</script>
