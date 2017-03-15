<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules"%>

<%@ page import="oracle.retail.stores.security.SecurityFactory"%>



<%
	String loyalityId = (String) request
			.getAttribute("loyalityId");
	
%>

<form method="POST" action="/centraloffice/centralizedElectronicJournal/ejTransactionSearch.do">

    <table cellspacing="0" cellpadding="0" width="100%" border="0" bgcolor="white" height="100%">
        <tbody><tr height="15" name="TitleBuffer">
            <td colspan="2"> &nbsp;</td>
        </tr>
        <tr height="35" valign="middle" name="TitleRow">
            <td align="left" class="pageheadercat"></td>
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

						<td align="center" class="normal">Loyalty ID <%=loyalityId%>
									entered is not valid</td>


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
