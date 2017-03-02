<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules"%>

<%@ page import="oracle.retail.stores.security.SecurityFactory"%>

<bean:define id="searchAction" name="customerSearchAction" scope="request" type="java.lang.String"/>
<bean:define id="searchTitle" name="customerSearchTitle" scope="request"
    type="java.lang.String" />
<%
  String customerName= SecurityFactory.getEncoder().encodeForHTML((String)request.getAttribute("customerName"));
  String customerID=(String)request.getAttribute("customerId");
  String requestFrom=(String)request.getAttribute("result");
  String action="/customer/customerSearchCriteria.do";

  if(requestFrom!=null)
  {
      if(requestFrom.equals("fromAddCustomer"))
      {
          action="/customer/addCustomerInfo.do";
      }
      else
      {
          action="/customer/customerSearchCriteria.do";
      }

  }
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
                       <%if(requestFrom.equals("fromDeleteCustomer"))
                       { %>
                        <td align="center" class="normal"><bean:message key="customer.delete.result.message"/></td>
                       <%}
                        else
                         {
                        %>
                         <td align="center" class="normal"><bean:message key="customer.add.confirmation.message"/></td>
                        <%} %>
                    </tr>
                    <tr>
                        <td class="normal"> </td>
                    </tr>
                    <tr>
                    <%if(requestFrom.equals("fromDeleteCustomer"))
                      { %>
                        <td align="center" class="normal"><bean:message key="customer.add.confirmation.partOne"/> <%=customerName%>/<%=customerID%> <bean:message key="customer.delete.result.partOne"/></td>
                       <%
                        }
                        else
                         {
                        %>
                        <td align="center" class="normal"><bean:message key="customer.add.confirmation.partOne"/> <%=customerName%>/<%=customerID%> <bean:message key="customer.add.confirmation.partTwo"/></td>
                     <%} %>
                    </tr>
                    <tr>
                        <td class="normal"> </td>
                    </tr>
                    <tr>
                        <td align="center" class="normal"><bean:message key="customer.add.confirmation.target"/></td>
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
