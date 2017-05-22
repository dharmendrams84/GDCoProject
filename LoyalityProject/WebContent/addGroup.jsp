<%-- Copyright (c) 2006, 2008, Oracle. All Rights Reserved. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/employee/addGroup.do">
<table width="100%" border="0" cellspacing="3" cellpadding="3">
  <tr>
  <td align=left class="pageheadercat"><bean:message  key="employee.addrole.heading"/></td>
    <td class="smallprompt" valign="top">
      <div align="right"> </div>
    </td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td colspan="2"></td>
  </tr>
  <tr>
    <td class="smallprompt"><bean:message  key="employee.addrole.prompt"/></td>
    <td valign="bottom">
      <div align="right"> </div>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <table width="100%" class="tableoutline">
          <tr>
          <td colspan="2"><html:errors/></td>
        </tr>
        <tr>
          <td class="fieldname" width="21%">&nbsp;</td>
          <td width="79%">&nbsp;</td>
        </tr>
        <tr>
          <td class="fieldname" width="21%">
            <div align="right"><bean:message  key="employee.addrole.label.groupname"/>:</div>
          </td>
          <td width="79%">
            <html:text styleClass="data" property="name" value=""  maxlength="25"/>*
          </td>
        </tr>
        <tr>
          <td>
              <span class="smallprompt">
              <bean:message key="info.required"/>
              </span>
          </td>
          <!--<td class="fieldname" width="21%">&nbsp;</td>-->
          <td width="79%">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" valign="top">
      <div align="right">
        <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
          <tr>
            <td NOWRAP>
              <div align="center">
                    <html:submit property="submit">
                        <bean:message  key="button.next"/>
                    </html:submit>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
</html:form>
<script>
    document.forms[0].description.focus();
</script>
