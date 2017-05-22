<%--
 Copyright (c) 2004, 2011, Oracle and/or its affiliates. All rights reserved. 

  Displays application-generated temporary password.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    import = "oracle.retail.stores.webmodules.employee.ui.AddEmployeeAction,
              oracle.retail.stores.webmodules.employee.ui.AddTempEmployeeAction"
%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%-- Page to display, after this one, depends on Session's "operation" attribute --%>
<%
    String endURL = "searchEmployeeView.do";
    String operation = (String)request.getSession().getAttribute(AddEmployeeAction.SESS_ATTR_OPERATION);
    if ((operation != null) && operation.equals(AddEmployeeAction.SESS_ATTR_VAL_ADD_EMP))
        endURL = "addEmployeeView.do";
    else if ((operation != null) && operation.equals(AddTempEmployeeAction.SESS_ATTR_VAL_ADD_TEMP_EMP))
        endURL = "addTempEmployeeView.do";
    request.getSession().setAttribute(AddEmployeeAction.SESS_ATTR_OPERATION, "");
%>

<form action='<html:rewrite page="/employee"/>/<%=endURL%>' method="POST"/>

    <table width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="white">
        <tr height="15" name="TitleBuffer">
            <td colspan=2>&nbsp;</td>
        </tr>
        <tr name="TitleRow" valign="middle" height="35">
            <td align=left class="pageheadercat"><bean:message key="employee.temppwd.heading"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan=2 bgcolor="#CCCCCC"></td>
        </tr>
        <tr>
            <td colspan=2>
                <table name=CustomAreaEncloser width=100% class="tableoutline" cellspacing="3" cellpadding="3">
                    <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="normal" align="center"><bean:message key="employee.temppwd.prompt1" arg0='<%=(String)request.getAttribute("employeeName")%>' arg1='<%=(String)request.getAttribute("temporaryPassword")%>'/></td>
                    </tr>
                    <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="normal" align="center"><bean:message key="employee.temppwd.prompt2" arg0='<%=(String)request.getAttribute("employeeName")%>'/></td>
                    </tr>
                    <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="normal" align="center"><bean:message key="employee.temppwd.prompt3"/></td>
                    </tr>
                    <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="ButtonCell1" align="center">
                            <input type="submit" name="enter" value="<bean:message key="button.enter"/>">
                        </td>
                    </tr>
                    <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
