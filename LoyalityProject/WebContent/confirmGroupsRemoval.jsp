<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>


<%@ page import="oracle.retail.stores.persistence.security.Role" %>
<%@ page import="oracle.retail.stores.webmodules.admin.app.ejb.AdminManagerRemote" %>
<%@ page import="oracle.retail.stores.webmodules.admin.app.ejb.AdminManagerHome" %>
<%@ page import="oracle.retail.stores.commerceservices.audit.AuditLoggerI18NHelper" %>
<%@ page import="oracle.retail.stores.foundation.common.ServiceLocator" %>
<%@ page import="oracle.retail.stores.webmodules.admin.ui.ViewGroupsForm" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>


<html:form action="/employee/removeGroups.do">
<input type="hidden" name="method" value=""/>

    <table width="100%" align="center" border="0" cellspacing="3" cellpadding="3">
        <tr align="left">
            <td width="40%" class="pageheadercat">
          <bean:message key="employee.confirmegrpremove.heading" />
      </td>
        </tr>
        <tr bgcolor="#CCCCCC">
            <td colspan="2"></td>
        </tr>
        <tr align="center">
            <td class="smallprompt"><bean:message
                key="employee.confirmegrpremove.prompt" />
      </td>
        </tr>
        </table>
        <table width="50%" align="center" border="0" cellspacing="1" cellpadding="3">
            <%
                AdminManagerRemote adminMgr = (AdminManagerRemote) ServiceLocator.getInstance().getRemoteService("java:comp/env/ejb/AdminManager", AdminManagerHome.class);
                Collection<Role> roles = adminMgr.getRoles(AuditLoggerI18NHelper.LOCALE);
                Iterator it = roles.iterator();
                ViewGroupsForm viewGroupsForm = (ViewGroupsForm)request.getSession().getAttribute("viewGroupsForm");
                String[] removedGroups = viewGroupsForm.getRemovedGroups();
                while (it.hasNext())
                {
                   Role element = (Role) it.next();
                   for ( int i = 0; i < removedGroups.length; i++ )
                   {
                      int id = Integer.parseInt(removedGroups[i]);
                      if(id == element.getID())
                      {%>
                         <tr align="center">
                             <td width="40%">  </td>
                             <td align="left" class="normal"><%=element.getName()%></td>
                        </tr>
                    <%}
                    }
                }%>
            <tr align="center">
                <td><html:submit property="Submit">
                    <bean:message key="button.yes" />
                </html:submit></td>
                <td><input type="submit" name="remove" value=" <bean:message key='button.no' /> "
                    onclick="form.action='<html:rewrite page="/employee/viewGroups.do"/>'" />
                </td>
            </tr>
       </table>
</html:form>
