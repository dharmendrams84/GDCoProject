<%@ page language = "java" contentType = "text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<html:form action="/employee/resetEmployeePassword.do">
    <html:hidden property="employeeName"/>

    <html:hidden property="employeeFormattedName"/>
    <html:hidden property="employeeFirstName"/>
    <html:hidden property="employeeId"/>
    <html:hidden property="employeeLastName"/>
    <html:hidden property="employeeMiddleName"/>

    <html:hidden property="employeeAlternateId"/>
    <html:hidden property="employeeLoginId"/>

    <html:hidden property="employeeRole"/>
    <html:hidden property="employeeStatus"/>
    <html:hidden property="socialSecurityNumber"/>
    <html:hidden property="employeeStatusCode"/>
    <html:hidden property="employeeLocale"/>
    <html:hidden property="groupID"/>
    <html:hidden property="employeeStoreId"/>
    <html:hidden property="employeeValidity"/>

    <html:hidden property="searchEmployeeId" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeFirstName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->
    <html:hidden property="searchEmployeeLastName" value="xxx"/> <!--harcoded value just to satisfy struts validator-->

<script language="javascript">
    function actionNo()
    {
        document.forms[0].action='<%=request.getContextPath()+"/employee/saveEmployeeView.do"%>';
        document.forms[0].submit();
    }
    function actionYes()
    {
        document.forms[0].action='<%=request.getContextPath()+"/employee/resetEmployeePassword.do"%>';
        document.forms[0].submit();
    }
</script>

    <table width="100%" border="0" cellspacing="3" cellpadding="3">
        <tr>
            <td width="40%" class="pageheadercat"><bean:message key="employee.confirm.reset.pwd.heading"/></td>
            <td></td>
        </tr>
        <tr bgcolor="#CCCCCC">
            <td colspan="2"></td>
        </tr>
        <tr>
            <td colspan="2">
                <table width="100%" class="tableoutline">
        <tr>
            <td>
                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                   <tr>
                       <td>&nbsp;</td>
                   </tr>
                   <tr>
                       <td colspan="2" align="center" class="normal"><bean:message key="employee.confirm.reset.pwd.prompt1"/></td>
                   </tr>
                   <tr>
                       <td>&nbsp;</td>
                   </tr>
                   <tr>
                       <td colspan="2" align="center" class="normal"><bean:message key="employee.confirm.reset.pwd.prompt2"/></td>
                   </tr>
                   <tr>
                       <td colspan="2" align="center" class="normal"><bean:message key="employee.confirm.reset.pwd.prompt3"/></td>
                   </tr>
                   <tr>
                       <td>&nbsp;</td>
                   </tr>
               </table>
           </td>
       </tr>
       <tr>
           <td>
               <table align="center" width="50%" cellpadding="0" cellspacing="0" border="0">
                   <tr align="center">
                       <td class="ButtonCell1" align="center">
                           <html:button property="submitProperty" onclick="return actionYes();">
                               <bean:message  key="button.yes"/>
                           </html:button>
                       </td>
                       <td class="ButtonCell1" align="center">
                           <html:button property="submitProperty" onclick="return actionNo();">
                              <bean:message  key="button.no"/>
                           </html:button>
                      </td>
                  </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td>&nbsp;</td>
      </tr>
     </table>
    </td>
   </tr>
 </table>
</html:form>
