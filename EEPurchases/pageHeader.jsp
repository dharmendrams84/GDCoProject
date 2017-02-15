<%-- Copyright (c) 2006, 2008, Oracle. All Rights Reserved. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="oracle.retail.stores.commerceservices.transaction.RetailTransactionDTO"%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules"%>

<jsp:useBean id="transactionDTO" scope="request" class="oracle.retail.stores.commerceservices.transaction.TransactionDTO"/>

    <tr>
        <td width="40%">
            <p class="pageheadercat">
            <bean:message  key="transaction.centej.trandet.pageheader.heading"/>
            </p>
        </td>
        <td class="smallprompt" valign="top">
            <div align="right" class="breadcrumbs">
                <bean:message key="breadcrumbs.search"/> > <bean:message key="breadcrumbs.results"/> > <b><bean:message key="breadcrumbs.details"/></b>
            </div>
        </td>
    </tr>
    <tr bgcolor="#CCCCCC">
        <td colspan="2"></td>
    </tr>
    <tr>
        <td class="smallprompt">

            <webmodules:isUserInRoleList roleList="view_journalview">
                <bean:message  key="transaction.centej.goto"/>&nbsp;
                <webmodules:secureQueryLink url="/centralizedElectronicJournal/showEJournal.do" addContextPath="true" key="transaction.centej.link.journal">
                    <webmodules:secureQueryParameter name="transactionNumber" value="<%=transactionDTO.getTransactionNumber()%>"/>
                    <webmodules:secureQueryParameter name="businessDate" value="<%=transactionDTO.getBusinessDateString()%>"/>
                </webmodules:secureQueryLink><br>
            </webmodules:isUserInRoleList>

            <webmodules:isUserInRoleList roleList="view_sigcap">
            <% if (transactionDTO != null && transactionDTO.isRetailTransaction() && !transactionDTO.isCanceled()) { %>
              <bean:message  key="transaction.centej.goto"/>&nbsp;
                <webmodules:secureQueryLink url="/centralizedElectronicJournal/showSigCapView.do" addContextPath="true" key="transaction.centej.link.sigcap">
                    <webmodules:secureQueryParameter name="transactionNumber" value="<%=transactionDTO.getTransactionNumber()%>"/>
                    <webmodules:secureQueryParameter name="businessDate" value="<%=transactionDTO.getBusinessDateString()%>"/>
                </webmodules:secureQueryLink><br>
            <% } %>
            </webmodules:isUserInRoleList>
            <webmodules:isUserInRoleList roleList="view_customerinfo">
                <% if (transactionDTO != null && transactionDTO.isCustomerLinkedToTransaction()) { %>
                    <bean:message  key="transaction.centej.goto"/>&nbsp;
                        <webmodules:secureQueryLink url="/centralizedElectronicJournal/showCustomer.do" addContextPath="true" key="transaction.centej.link.custinfo">
                            <webmodules:secureQueryParameter name="transactionNumber" value="<%=transactionDTO.getTransactionNumber()%>"/>
                            <webmodules:secureQueryParameter name="businessDate" value="<%=transactionDTO.getBusinessDateString()%>"/>
                        </webmodules:secureQueryLink><br>
                <% } %>
            </webmodules:isUserInRoleList>
            <!-- new tab added by Dharmendra to display loyality Ejournal link -->
            <webmodules:isUserInRoleList roleList="view_journalview">
                <bean:message  key="transaction.centej.goto"/>&nbsp;
                <webmodules:secureQueryLink url="/centralizedElectronicJournal/showLoyalityEJournal.do" addContextPath="true" key="transaction.centej.link.loyality">
                    <webmodules:secureQueryParameter name="transactionNumber" value="<%=transactionDTO.getTransactionNumber()%>"/>
                    <webmodules:secureQueryParameter name="businessDate" value="<%=transactionDTO.getBusinessDateString()%>"/>
                </webmodules:secureQueryLink><br>
            </webmodules:isUserInRoleList>
            <p>
            <bean:message  key="transaction.centej.trandet.pageheader.prompt"/>
            </p>
        </td>
        <td valign="bottom">
            <div align="right">
                <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
                    <tr>
                        <td NOWRAP>
                            <div align="right">
                                 <% if (request.isUserInRole("export_transaction")) { %>
                                                                <input type="submit" name="Export" value="<bean:message  key="button.export"/>">
                                                            <% } %>
                                                    <input type="button" value="<bean:message  key="button.done"/>" id="doneButton" onClick="done();"/>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>

<script>
    document.forms[0].doneButton.focus();
</script>
