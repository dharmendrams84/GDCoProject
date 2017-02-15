<!--jsp page added by Dharmendra on 28/11/2016 to display loyality details -->
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
<jsp:useBean id="eJournalViewBean" scope="request" class="gdyn.retail.stores.webmodules.ejournal.GDYNLoyalityEJournalViewBean"/>
<form name="export" action="<html:rewrite page="/centralizedElectronicJournal/updateLoyalityEJournal.do"/>" method="POST"/>




<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
    <tr>
        <td>
            <div align="center">
                <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                    <tr bordercolor="#CCCCCC">
                        <td width="28%"><b class="heading2">Loyality Details</b></td>
                    </tr>
                    <tr>
                        <td bgcolor="#CCCCCC"></td>
                    </tr>
                    <!-- <tr>
                        <td height="20">
                            <span class="normal">
                                    Corporate >> Oracle >> South >> Texas >> Austin >> 04241-Lakeline Mall >> Register 129
                            </span>
                        </td>
                    </tr> -->
                </table>
            </div>
            <div align="center">
                <div align="center">
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
                        <tr>
                            <td height="11" width="27%">&nbsp;</td>
                            <td height="11" width="17%">&nbsp;</td>
                            <!-- <td height="11" width="14%">&nbsp;</td>
                            <td width="15%" height="11">&nbsp;</td>
                            <td width="9%" height="11">&nbsp;</td>
                            <td width="18%" height="11">&nbsp;</td> -->
                        </tr>
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Full Transaction
										Number:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getFullTransactionNumber()%></td>
							</tr>
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Store Number:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getStoreNumber()%></td>
							</tr>
							
							
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Register Number:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getWorkstationId()%></td>
							</tr>
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Business Date:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getBusinessDayString()%></td>
							</tr>
							
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">System Date:</div>
								</td>
								<td height="20" width="17%" class="normal"><%= new java.util.Date() %></td>
							</tr>
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Sales Channel:</div>
								</td>
								<td height="20" width="17%" class="normal"><%=eJournalViewBean.getSalesChannel()%></td>
							</tr>
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Loyalty ID:</div>
								</td>
								<td height="20" width="17%" class="normal">
								<input type="text" name ="loyalityId" id="loyalityId" value="<%=eJournalViewBean.getLoyalityId()%>"/>
								</td>
							</tr>
							<tr>
								<td height="20" width="27%">
									<div align="right" class="fieldname">Loyalty Email:</div>
								</td>
								<td height="20" width="17%" class="normal">
								<input type="text" name ="loyalityEmail" id="loyalityEmail" value="<%=eJournalViewBean.getLoyalityEmail()%>"/></td>
							</tr>
							<tr>
								<td height="20" width="27%">
									
								</td>
								<td height="20" width="17%" class="normal">
								<input type="submit" value="Save or Done"/></td>
							</tr>
                    </table>
					<input type="hidden" name ="storeNumber" id="storeNumber" value="<%=eJournalViewBean.getStoreNumber()%>"/>
					<input type="hidden" name ="workstationId" id="workstationId" value="<%=eJournalViewBean.getWorkstationId()%>"/>
					<input type="hidden" name ="sequenceNumber" id="sequenceNumber" value="<%=eJournalViewBean.getSequenceNumber()%>"/>
					
                </div>
            </div>
        </td>
    </tr>
</table>




</form>
<!-- #EndEditable -->