<%-- Copyright (c) 2006, 2008, Oracle. All Rights Reserved. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
    String principalName = "";
    if(request.getUserPrincipal() != null) {
        principalName = request.getUserPrincipal().getName();
    }
%>

<html:form action="/employee/changePassword.do" focus="password">

        <table width="100%" border="0" cellspacing="3" cellpadding="3">
          <tr>
            <td class="pageheadercat"><bean:message key="employee.change.pwd.title"/></td>
          </tr>
          <tr bgcolor="#CCCCCC">
            <td></td>
          </tr>
          <tr>
            <td class="smallprompt"><bean:message key="employee.change.pwd.header"/></td>
          </tr>
          <tr>
            <td>
              <table width="100%" class="tableoutline">

                <tr><td><html:errors/>&nbsp;</td></tr>

                <tr>
                  <td>
                    <table width="93%" border="0" cellspacing="1" cellpadding="3">
                      <tr>
                        <td width="50%" align="right" class="fieldname">
                        <bean:message key="employee.change.pwd.label.userID"/>:
                        </td>
                        <td width="50%" class="data">
                          <%= principalName %>
                        </td>
                      </tr>
                      <tr>
                        <td width="50%" align="right" class="fieldname">
                        <bean:message key="employee.change.pwd.label.currentPwd"/>:
                        </td>
                        <td width="50%">
                          <input type="password" name="password" size="10" maxlength="22" class="passworddata" autocomplete="off"> *
                        </td>
                      </tr>

                      <tr><td colspan=2>&nbsp;</td></tr>

                      <tr>
                        <td width="50%" align="right" class="fieldname">
                        <bean:message key="employee.change.pwd.label.newPwd"/>:
                        </td>
                        <td width="50%">
                          <input type="password" name="newPassword" size="10" maxlength="22" class="passworddata" autocomplete="off"> *
                        </td>
                      </tr>
                      <tr>
                        <td width="50%" align="right" class="fieldname">
                        <bean:message key="employee.change.pwd.label.confirmPwd"/>:
                        </td>
                        <td width="50%">
                          <input type="password" name="confirmPassword" size="10" maxlength="22" class="passworddata" autocomplete="off"> *
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>

                <tr><td>&nbsp;</td></tr>

                <tr>
                    <td align="left" colspan="6">
                        <span class="smallprompt">
                            <bean:message key="info.required"/>
                        </span>
                    </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td valign="top" align=right>
                <table class="tableoutline" cellpadding="3" cellspacing="3">
                  <tr>
                    <td>
                        <input type="submit" name="update" value="<bean:message key="button.update"/>">
                    </td>
                  </tr>
                </table>
            </td>
        </tr>
    </table>
</html:form>
