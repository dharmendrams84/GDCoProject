<!--jsp page added by Dharmendra on 28/11/2016 to display loyality details -->
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules" %>

<SCRIPT LANGUAGE="JavaScript">
function cancelLoyalityTransaction()
{
	
    document.forms[0].action='/centraloffice/centralizedElectronicJournal/ejTransactionSearch.do';
    document.forms[0].submit();
}
</SCRIPT>
<jsp:useBean id="eJournalViewBean" scope="request" class="gdyn.retail.stores.webmodules.ejournal.GDYNLoyalityEJournalViewBean"/>
<form name="loyTransactionDtlsForm" id="loyTransactionDtlsForm" action="<html:rewrite page="/centralizedElectronicJournal/updateLoyalityEJournal.do"/>"  method="POST"/>




<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
    <tr>
        <td>
            <div align="center">
                <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                    <tr bordercolor="#CCCCCC">
                        <td width="28%"><b class="heading2">Loyalty Details</b></td>
                    </tr>
                    <tr>
                        <td bgcolor="#CCCCCC"></td>
                    </tr>
                   
                </table>
            </div>
            <div align="left">
                <div align="left">
                    <br>
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                        <tr>
                            <td colspan="6">
                                <div align="left" class="heading2">
                                    Transaction
                                </div>
                                <div align="center">
                                    
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" bgcolor="cccccc"></td>
                        </tr>
                        </table>
                        <table width="35%" border="0" cellspacing="1" cellpadding="1">
                        <tr>
                            <td height="11" width="18%">&nbsp;</td>
                            <td height="11" width="17%">&nbsp;</td>
                            <!-- <td height="11" width="14%">&nbsp;</td>
                            <td width="15%" height="11">&nbsp;</td>
                            <td width="9%" height="11">&nbsp;</td>
                            <td width="18%" height="11">&nbsp;</td> -->
                        </tr>
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Full Transaction
										Number:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getFullTransactionNumber()%></td>
							</tr>
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Store Number:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getStoreNumber()%></td>
							</tr>
							
							
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Register Number:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getWorkstationId()%></td>
							</tr>
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Business Date:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getBusinessDayString()%></td>
							</tr>
							
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">System Date:</div>
								</td>
								<td height="20" width="17%" class="normal"><%= new java.util.Date() %></td>
							</tr>
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Sales Channel:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getSalesChannel()%></td>
							</tr>
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Loyalty ID:</div>
								</td>
								<td height="20" width="17%" class="normal">
								<input type="text" name ="loyalityId" id="loyalityId" value="<%=eJournalViewBean.getLoyalityId()%>"/>
								</td>
							</tr>
							<tr>
								<td height="20" width="18%">
									<div align="left" class="fieldname">Loyalty Email:</div>
								</td>
								<td height="20" width="17%" class="normal">
								<input type="text" name ="loyalityEmail" id="loyalityEmail" value="<%=eJournalViewBean.getLoyalityEmail()%>"/></td>
							</tr>
							<tr>
								<td height="20" width="18%">
									<input type="submit" value="Save"/>
								</td>
								<td height="20" width="17%" class="normal">
								<html:button
									property="submitProperty" onclick="return cancelLoyalityTransaction();">
									<bean:message key="parameter.paramvalue.Cancel" />
								</html:button></td>
							</tr>
                    </table>
					<input type="hidden" name ="storeNumber" id="storeNumber" value="<%=eJournalViewBean.getStoreNumber()%>"/>
					<input type="hidden" name ="workstationId" id="workstationId" value="<%=eJournalViewBean.getWorkstationId()%>"/>
					<input type="hidden" name ="sequenceNumber" id="sequenceNumber" value="<%=eJournalViewBean.getSequenceNumber()%>"/>
					<input type="hidden" name ="fullTransactionNumber" id="fullTransactionNumber" value="<%=eJournalViewBean.getFullTransactionNumber()%>"/>
					
                </div>
            </div>
        </td>
    </tr>
</table>




</form>
<!-- #EndEditable -->