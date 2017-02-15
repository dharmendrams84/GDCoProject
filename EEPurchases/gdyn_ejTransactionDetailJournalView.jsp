<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules" %>

<SCRIPT LANGUAGE="JavaScript">
function done()
{
    window.location='<html:rewrite page="/centralizedElectronicJournal/searchDone.do" />';
}

</SCRIPT>
<jsp:useBean id="eJournalViewBean" scope="request" class="oracle.retail.stores.webmodules.ejournal.EJournalViewBean"/>
<form name="export" action="<html:rewrite page="/centralizedElectronicJournal/exportEJournalOptions.do"/>" method="POST"/>

<html:hidden property="transactionNumber" value="<%=eJournalViewBean.getTransactionNumber()%>"/>
<html:hidden property="businessDate" value="<%=eJournalViewBean.getBusinessDate()%>"/>
<html:hidden property="transactionBeginDate" value="<%=eJournalViewBean.getTransactionBeginDate()%>"/>
            <table width="100%" border="0" cellspacing="3" cellpadding="3">
              <tr>
                <td width="40%"><span class="pageheadercat"><bean:message  key="transaction.centej.ejtrandetailview.heading"/></span></td>
                <td class="smallprompt" valign="top">
                  <div align="right" class="breadcrumbs"><bean:message key="breadcrumbs.search"/> > <bean:message key="breadcrumbs.results"/> > <b><bean:message key="breadcrumbs.details"/></b></div>
                </td>
              </tr>
              <tr bgcolor="#CCCCCC">
                <td colspan="2"></td>
              </tr>
              <tr>
                <td class="smallprompt">
                  <logic:present name="transactionFound">
                    <webmodules:isUserInRoleList roleList="view_trandetail">
                        <p><bean:message  key="transaction.centej.goto"/>&nbsp;
                        <webmodules:secureQueryLink url="/centralizedElectronicJournal/ejTransactionDetailDataView.do" addContextPath="true" key="transaction.centej.link.data">
                            <webmodules:secureQueryParameter name="transactionNumber" value="<%=eJournalViewBean.getTransactionNumber()%>"/>
                            <webmodules:secureQueryParameter name="businessDate" value="<%=eJournalViewBean.getBusinessDate()%>"/>
                        </webmodules:secureQueryLink><br>
                       </webmodules:isUserInRoleList>
                </logic:present>
                  <logic:present name="signatureCaptureFound">
                    <webmodules:isUserInRoleList roleList="view_sigcap">
                       <bean:message  key="transaction.centej.goto"/>&nbsp;
                        <webmodules:secureQueryLink url="/centralizedElectronicJournal/showSigCapView.do" addContextPath="true" key="transaction.centej.link.sigcap">
                            <webmodules:secureQueryParameter name="transactionNumber" value="<%=eJournalViewBean.getTransactionNumber()%>"/>
                            <webmodules:secureQueryParameter name="businessDate" value="<%=eJournalViewBean.getBusinessDate()%>"/>
                        </webmodules:secureQueryLink><br>
                       </webmodules:isUserInRoleList>
                </logic:present>
                  <logic:present name="customerID">
                    <webmodules:isUserInRoleList roleList="view_customerinfo">
                        <bean:message  key="transaction.centej.goto"/>&nbsp;
                        <webmodules:secureQueryLink url="/centralizedElectronicJournal/showCustomer.do" addContextPath="true" key="transaction.centej.link.custinfo">
                            <webmodules:secureQueryParameter name="transactionNumber" value="<%=eJournalViewBean.getTransactionNumber()%>"/>
                            <webmodules:secureQueryParameter name="businessDate" value="<%=eJournalViewBean.getBusinessDate()%>"/>
                        </webmodules:secureQueryLink><br>
                       </webmodules:isUserInRoleList>
                </logic:present>
                  <p class="smallprompt"><bean:message  key="transaction.centej.ejtrandetailview.prompt"/>
                </td>
                <td valign="bottom">
                  <div align="right">
                    <table align="right" class="tableoutline" cellpadding="3" cellspacing="3">
                      <tr>
                        <td NOWRAP>
                          <div align="right">
                            <% if (request.isUserInRole("export_journalview")) { %>
                              <logic:notPresent name="EJNotFound">
                                    <input type="submit" name="Export" value="<bean:message  key="button.export"/>">
                              </logic:notPresent>
                            <% } %>
                                   <input type="button" name="Done2" value="<bean:message  key="button.done"/>" onClick="done();">
                          </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <table width="100%" class="tableoutline">
                    <tr>
                      <td>

                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr bordercolor="#CCCCCC" bordercolorlight="WHITE">
                            <td width="28%">
                              <div align="left" class="heading2"><bean:message  key="transaction.centej.ejtrandetailview.header.tranjournal"/></div>
                            </td>
                          </tr>
                          <tr>
                            <td bgcolor="cccccc"></td>
                          </tr>
                          <tr>
                            <td>
                              <div align="right"><b></b></div>
                              <html:errors/>
                              <pre><%= eJournalViewBean.getTape()%>
                              </pre>
                              </p>
                            </td>
                          </tr>
                        </table>
                        <p>&nbsp;</p>
                      </td>
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
                          <div align="right">
                            <% if (request.isUserInRole("export_journalview")) { %>
                              <logic:notPresent name="EJNotFound">
                                    <input type="submit" name="Export" value="<bean:message  key="button.export"/>">
                              </logic:notPresent>
                            <% } %>
                                   <input type="button" name="Done2" value="<bean:message  key="button.done"/>" onClick="done();">
                          </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
            <p>
            <p>
          </TD>
        </TR>
      </TABLE>
</form>
<!-- #EndEditable -->