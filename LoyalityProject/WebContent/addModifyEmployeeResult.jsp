<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<%@ page import="oracle.retail.stores.security.SecurityFactory"%>

<bean:define id="searchAction" name="gdemployeeSearchAction" scope="request" type="java.lang.String"/>
<bean:define id="searchTitle" name="gdemployeeSearchTitle" scope="request"
    type="java.lang.String" />
<%  
  String employeeId=(String)request.getAttribute("emplNumber");
  String requestFrom=(String)request.getAttribute("result");
  String gdEmployeeNumber = (String)request.getAttribute("gdEmployeeNumber");
  String action="/employee/gdemployeeAdd.do";
  
%>

<form method="POST" action="<%=request.getContextPath() + action%>">

    <table cellspacing="0" cellpadding="0" width="100%" border="0" bgcolor="white" height="100%">
        <tbody><tr height="15" name="TitleBuffer">
            <td colspan="2"> &nbsp;</td>
        </tr>
        <tr height="35" valign="middle" name="TitleRow">
            <td align="left" class="pageheadercat"><bean:message key='<%=searchTitle%>'/></td>
            <td> </td>
        </tr>
        <tr>
            <td bgcolor="#cccccc" colspan="2"/>
        </tr>
        <tr>
            <td colspan="2">
                <table cellspacing="3" cellpadding="3" width="100%" class="tableoutline" name="CustomAreaEncloser">
                    <tbody><tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                    <tr>                 
                        
                         <td align="center" class="normal"><bean:message key="employee.add.confirmation.message"/></td>
                      
                    </tr>
                    <tr>
                        <td class="normal"> </td>
                    </tr>
                    <tr>
                   
                        <td align="center" class="normal"><bean:message key="employee.add.confirmation.partOne"/> <%=gdEmployeeNumber%> <bean:message key="customer.add.confirmation.partTwo"/></td>
                    
                    </tr>
                    <tr>
                        <td class="normal"> </td>
                    </tr>
                    <tr>
                        <td align="center" class="normal"><bean:message key="employee.add.confirmation.target"/></td>
                    </tr>
                    <tr>
                        <td class="normal"> </td>
                    </tr>
                    <tr>
                        <td align="center" class="ButtonCell1">
                            <input type="submit" value='<bean:message key="customer.button.enter"/>' />
                        </td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                      <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                     <tr>
                        <td class="normal">&nbsp;</td>
                    </tr>
                </tbody></table>
            </td>
        </tr>
    </tbody></table>
</form>
