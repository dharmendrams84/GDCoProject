<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="oracle.retail.stores.domain.transaction.TransactionConstantsIfc,
                 oracle.retail.stores.commerceservices.transaction.RetailTransactionDTO
                 "%>
<%@ page import="java.text.DateFormat" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules"%>

<jsp:useBean id="transactionDTO" scope="request" class="oracle.retail.stores.commerceservices.transaction.TransactionDTO"/>
<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
    <tr>
        <td>
            <div align="center">
                <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                    <tr bordercolor="#CCCCCC">
                        <td width="28%"><b class="heading2"><bean:message  key="transaction.centej.trandet.header.header.hierarchy"/></b></td>
                    </tr>
                    <tr>
                        <td bgcolor="#CCCCCC"></td>
                    </tr>
                    <tr>
                        <td height="20">
                            <span class="normal">
                                    <%= transactionDTO.getPathToStore() %>
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
            <div align="center">
                <div align="center">
                    <br>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                        <tr>
                            <td colspan="6">
                                <div align="left" class="heading2">
                                    <bean:message  key="transaction.centej.trandet.header.header.trans"/>
                                </div>
                                <div align="center">
                                    <html:errors/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" bgcolor="cccccc"></td>
                        </tr>
                        <tr>
                            <td height="11" width="27%">&nbsp;</td>
                            <td height="11" width="17%">&nbsp;</td>
                            <td height="11" width="14%">&nbsp;</td>
                                        <td width="15%" height="11">&nbsp;</td>
                            <td width="9%" height="11">&nbsp;</td>
                            <td width="18%" height="11">&nbsp;</td>
                          </tr>
                        <tr>
                            <td height="20" width="27%">
                                <div align="right" class="fieldname">
                                    <bean:message  key="transaction.centej.trandet.header.label.trannum"/>:
                                 </div>
                             </td>
                            <td height="20" width="17%" class="normal"><%=transactionDTO.getTransactionNumber()%></td>
                            <td height="20" width="14%">
                                <div align="right">
                                    <font face="Verdana, Arial, Helvetica, sans-serif" size="1"><b><font face="Verdana, Arial, Helvetica, sans-serif" size="1">
                                                            <span class="fieldname">
                                    <bean:message  key="transaction.centej.trandet.header.label.system.date"/>:
                                                            </span>
                                    </font></b></font>
                                </div>
                            </td>
                            <td width="15%">
                                   <div align="left">
                                       <font face="Verdana, Arial, Helvetica, sans-serif" size="1" class="normal">
                                            <webmodules:localDate date="<%=transactionDTO.getTransactionEndDateTime()%>" format="<%=DateFormat.SHORT%>"/>&nbsp;<webmodules:localDate time="<%=transactionDTO.getTransactionEndDateTime()%>" format="<%=DateFormat.SHORT%>"/>
                                       </font>
                                   </div>
                            </td>
                            <td width="9%">
                                <div align="right">
                                    <font face="Verdana, Arial, Helvetica, sans-serif" size="1"><b><font face="Verdana, Arial, Helvetica, sans-serif" size="1">
                                    <span class="fieldname">
                                       <bean:message  key="transaction.centej.trandet.header.label.business.date"/>:
                                    </span>
                                    </font></b></font>
                                </div>
                              </td>
                            <td width="18%">
                                <div align="left">
                                  <font face="Verdana, Arial, Helvetica, sans-serif" size="1" class="normal">
                                         <webmodules:localDate date="<%=transactionDTO.getBusinessDate()%>" format="<%=DateFormat.SHORT%>"/>
                                  </font>
                                </div>
                            </td>
                          </tr>
                        <tr>
                            <td width="27%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.type"/>:</b>
                                </div>
                             </td>

                                        <%
                                            String transactionType = transactionDTO.getType();
                                            String transactionTypeLookupCode = "transaction.centej.trantype." + transactionType.replaceAll(" ","");
                                        %>
                            <td width="17%" class="normal"><bean:message key='<%=transactionTypeLookupCode%>'/></td>

                            <td width="14%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.empname"/>:</b>
                                </div>
                            </td>
                            <td width="15%" class="normal"><%=transactionDTO.getOperatorName()%></td>
                            <td width="14%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.empid"/>:</b>
                                </div>
                            </td>
                            <td width="15%" class="normal"><%=transactionDTO.getOperatorID()%></td>

                           </tr>
                        <tr>
                            <td width="27%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trancrit.label.trainingmode"/>:</b>
                                  </div>
                            </td>
                                            <%
                                                String trainingMode = "";
                                                if(transactionDTO.isTrainingMode())
                                                trainingMode = "On";
                                                else trainingMode = "Off";
                                                String trainingModeLookupCode = "transaction.centej.trainingMode." + trainingMode;
                                            %>
                            <td width="17%" class="normal"><bean:message key='<%=trainingModeLookupCode%>'/></td>
                            <td width="14%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trancrit.label.reentrymode"/>:</b>
                                </div>
                            </td>
                                            <%
                                                String reentryMode = "";
                                                if(transactionDTO.isReentryMode())
                                                reentryMode = "On";
                                                else reentryMode = "Off";
                                                String reentryModeLookupCode = "transaction.centej.reentrymode." + reentryMode;
                                            %>
                            <td width="15%" class="normal"><bean:message key='<%=reentryModeLookupCode%>'/></td>
         				 <td width="14%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.loyaltyid"/></b>
                                </div>
                            </td>
                            <td width="15%" class="normal"><%=transactionDTO.getLoyaltyID()%></td>
                                            <% if (transactionDTO.isRetailTransaction() && ((RetailTransactionDTO)transactionDTO).getTaxExemptionDTO()!=null) { %>
                            <td width="14%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.showcustomer.label.taxcert"/>:</b>
                                </div>
                            </td>
                            <td width="15%" class="normal"><%=((RetailTransactionDTO)transactionDTO).getTaxExemptionDTO().getCertificateNumber()%></td>
                                            <% } %>
                        </tr>

                        <tr>
                            <td width="27%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.status"/>:</b>
                                </div>
                            </td>
                                        <%
                                            String statusDescriptor = transactionDTO.getStatusDescriptor();
                                            String statusDescriptorLookupCode = "transaction.centej.transtatus." + statusDescriptor;
                                        %>
                            <td width="17%" class="normal"><bean:message key='<%=statusDescriptorLookupCode%>'/></td>

                                        <% if (transactionDTO.isRetailTransaction() && transactionDTO.getStatus() == TransactionConstantsIfc.STATUS_SUSPENDED) { %>
                            <td width="14%">
                                    <div align="right">
                                        <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.suspendreasoncode"/>:</b>
                                    </div>
                            </td>
                            <td width="15%" class="normal">
                                <%=((RetailTransactionDTO)transactionDTO).getSuspendedTransactionReasonCode()%></td>
                                        <% } else { %>
                            <td width="14%">
                                    <div align="right">
                                                <b class="fieldname">&nbsp;</b>
                                    </div>
                            </td>
							<td width="15%" class="normal">&nbsp;</td>
                                              <% } %>
                                            <%
                                                 if (transactionDTO.isRetailTransaction() && ((RetailTransactionDTO)transactionDTO).getTaxExemptionDTO()!=null)
                                                 {
                                            %>
                            <td width="14%">
                                    <div align="right">
                                        <b class="fieldname"><bean:message  key="transaction.centej.showcustomer.label.exemptreason"/>:</b>
                                    </div>
                            </td>
                            <%
                                String exemptReason=((RetailTransactionDTO)transactionDTO).getTaxExemptionDTO().getReasonCodeDescription();
                                String exemptReasonLookupCode = "transaction.centej.exemptreason." + exemptReason.replaceAll(" ","");
                            %>
                            <td width="15%" class="normal"><bean:message key="<%=exemptReasonLookupCode%>"/></td>
                                       <% } %>
                                       <td width="14%">
                                <div align="right">
                                    <b class="fieldname"><bean:message  key="transaction.centej.trandet.header.label.loyaltyemail"/></b>
                                </div>
                            </td>
                            <td width="15%" class="normal"><%=transactionDTO.getLoyaltyEmail()%></td>
                        </tr>

                        <tr>
                            <% if (transactionDTO.isRetailTransaction() && ((RetailTransactionDTO)transactionDTO).getExternalOrderNumber()!=null) { %>
                            <td height="20" width="27%">
                                <div align="right" class="fieldname">
                                    <bean:message  key="transaction.centej.trancrit.label.extnum"/>:
                                </div>
                            </td>

                            <td height="20" width="17%" class="normal"><%=((RetailTransactionDTO)transactionDTO).getExternalOrderNumber()%></td>
                             <% } %>
                        </tr>
                  </table>
                </div>
            </div>
        </td>
    </tr>
</table>
