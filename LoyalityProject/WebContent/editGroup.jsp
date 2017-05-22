<%-- Copyright (c) 2006, 2008, Oracle. All Rights Reserved. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="oracle.retail.stores.webmodules.admin.app.GroupDTO" %>
<%@ page import="oracle.retail.stores.webmodules.admin.app.PermissionDTO" %>
<%@ page import="oracle.retail.stores.security.SecurityFactory"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<%
   boolean isAllowed = false;
   String newGroup = SecurityFactory.getValidator().getValidInput("editGroup",request, "newGroup", "Boolean", 5, true);
   if (request.isUserInRole("roles_permissions_update") || request.isUserInRole("permissions_update") || (newGroup != null && "true".equals(newGroup)))
   {
       isAllowed = true;
   }
%>

<SCRIPT LANGUAGE="JavaScript">
function applicationFilter()
{
    url='<html:rewrite page="/employee/editGroup.do" paramId="workGroupID" paramName="group" paramProperty="workGroupID"/>';
    url+="&application=" + document.editGroupForm.application.value;
    url+="&newGroup=" + <%=newGroup%>;

    window.location=url;
}

function resourceFilter()
{
    url='<html:rewrite page="/employee/editGroup.do" paramId="workGroupID" paramName="group" paramProperty="workGroupID"/>';
    url+="&application=" + document.editGroupForm.application.value;
    url+="&resource=" + document.editGroupForm.resource.value;
    url+="&newGroup=" + <%=newGroup%>;

    window.location=url;
}

function selectAll(selectAllBox)
{
    boxes = document.forms[0].selectedItems;
    if (boxes[0])
    {
        for (i=0; i<boxes.length; i++)
        {
            boxes[i].checked = selectAllBox.checked;
        }
    }
    else
    {
        boxes.checked = selectAllBox.checked;
    }
}
</SCRIPT>


<html:form action="/employee/savePermissions">
<html:hidden name="group" property="workGroupID"/>
<bean:define id="workGroupName" name="group" property="workGroupName"/>
<bean:define id="editGroupForm" name="editGroupForm"/>
<bean:define id="workGroupID" name="group" property="workGroupID"/>
<table width="100%" border="0" cellspacing="3" cellpadding="3">
  <tr>
    <td align=left class="pageheadercat">
        <bean:message key="employee.editgroup.heading"/> <bean:write name="workGroupName" />
      </td>
    </tr>
    <tr>
    <td colspan=2 bgcolor="#CCCCCC"></td>
    </tr>
 <tr>
  <td class="smallprompt">
  <bean:message  key="employee.editgroup.prompt"/>
  </td>
  <td valign="bottom" align="right">
  <table class="tableoutline" cellpadding="3" cellspacing="3">
    <tr>
      <td>
      <html:submit disabled="<%=!isAllowed%>" property="submit">
         <bean:message  key="button.save"/>
      </html:submit>
      </td>
    </tr>
  </table>
</td>
</tr>
<tr>
  <td colspan="2">
  <table width="100%" class="tableoutline">
    <tr>
      <td>
      <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr>
          <td width=40% valign=top align=left>
          <!--left table-->
          <table width=100% border=0 cellspacing=1 cellpadding=3 frame=HSIDES rules=ROWS bordercolorlight=WHITE bordercolor=#FFFFFF>
             <tr class="tableheadrow">
                <td rowspan=2><bean:message key="button.selectall"/><input type="checkbox" <%= !isAllowed? "disabled" : "" %> name="selectAllCheckbox" onclick="selectAll(this);"></td>
                <td colspan=2><bean:message key="employee.editgroup.application.label"/></td>
                <td colspan=2><bean:message key="employee.editgroup.module.label"/></td>
                <td rowspan=2 colspan=5 valign="center" halign="center"><bean:message key="employee.editgroup.feature.label"/></td>
             </tr>
             <tr class="tableheadrow">
                <td colspan=2>

                    <html:select property="application" onchange="return applicationFilter();">
                        <html:optionsCollection property="applications" label="displayName" value="applicationID"/>
                    </html:select>


                </td>
                <td colspan=2>
                    <html:select property="resource" onchange="return resourceFilter();">
                        <html:optionsCollection property="resources" label="description" value="resourceID"/>
                    </html:select>
                </td>
             </tr>
                <logic:iterate id="permission" name="editGroupForm" property="permissions" type="PermissionDTO">
                    <tr>
                        <td halign="center" valign="center" >
                               <html:multibox disabled="<%=!isAllowed%>" property="selectedItems" >
                                    <bean:write name="permission" property="groupResourceID" />
                               </html:multibox>

                        </td>
                        <td class="normal" colspan=2>
                            <bean:write name="permission" property="applicationName" />
                        </td>
                        <td colspan=2>
                            <div class="normal" align=left>
                                <bean:write name="permission" property="parentName"/>
                             </div>
                        </td>

                        <td colspan=5 class="normal">
                            <bean:write name="permission" property="displayName"/>
                            <html:hidden name="permission" property="description"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=10 bgcolor="#CCCCCC"></td>
                    </tr>
                 </logic:iterate>
          </table><!--end of left table-->
       </td>
     </tr>
   </table>
</td>
</tr>
</table>
<tr>
  <td colspan="2" valign="top" align="right">
  <table class="tableoutline" cellpadding="3" cellspacing="3">
    <tr>
      <td>
      <html:submit disabled="<%=!isAllowed%>" property="submit">
      <bean:message  key="button.save"/>
      </html:submit>
      </td>
    </tr>
  </table>
</td>
</tr>
</table>
</html:form>
<script>
    document.forms[0].selectAllCheckbox.focus();
</script>
