<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="oracle.retail.stores.webmodules.transaction.ui.SearchTransactionForm"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="oracle.retail.stores.common.utility.LocaleMap" %>
<%@ page import="oracle.retail.stores.domain.utility.LocaleConstantsIfc" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webmodules.tld" prefix="webmodules" %>

<bean:define id="transactionCriteria" name="expandSections" scope="request" type="java.lang.String"/>
<bean:define id="searchTransactionForm" name="searchTransactionForm" scope="request" type="oracle.retail.stores.webmodules.transaction.ui.SearchTransactionForm" />

<webmodules:calendar/>

<%
    boolean expandTransaction = false;
    if(transactionCriteria != null && transactionCriteria.indexOf("transactionCriteria") >= 0)
    {
        expandTransaction = true;
    }
%>
<script>

  function enableDefault(fromText, toText, minimumSize)
  {
    if (toText.value == '' || +fromText.value > +toText.value) {
      toText.value = fromText.value;
    }

    return true;
  }

  function updateFromTime()
    {
      for (i = 0; i < 24; i++)
      {
        if (document.searchTransactionForm.fromTransactionTime.options[i].selected)
        {
          document.searchTransactionForm.fromTransactionTimeStr = document.searchTransactionForm.fromTransactionTime.options[i].id;
        }
      }


    }

    function updateToTime()
    {
      for (i = 0; i < 24; i++)
      {
        if (document.searchTransactionForm.toTransactionTime.options[i].selected)
            {
            document.searchTransactionForm.toTransactionTimeStr.value = document.searchTransactionForm.toTransactionTime.options[i].id;

        }
    }
    }

</script>
<table width="93%" border="0" cellspacing="1" cellpadding="3">
  <tr>
    <td height="10" colspan="4">
      <div align="left" class="heading2"><br>
    <input type="checkbox" <%=expandTransaction ? "checked=\"true\" " : " "%>  name="<%=SearchTransactionForm.SEARCH_BY_TRANSACTION_CRITERIA%>" onClick="showHide(this,'transactionCriteria');"/>
        <bean:message  key="transaction.centej.trancrit.heading"/>
        </div>
    </td>
  </tr>
  <tr>
    <td colspan="4" bgcolor="cccccc"></td>
  </tr>
</table>


<table width="93%" border="0" cellspacing="1" cellpadding="3" id='transactionCriteria' class='<%=expandTransaction ? "expand" : "collapse"%>'>
   <tr>
    <td height="20" width="22%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.trannum"/>:</div>
    </td>
    <td height="20" width="27%">
      <input type="text" class="data" name="<%=SearchTransactionForm.TRANSACTION_NUMBER%>" size="15" MAXLENGTH="20" value="">
    </td>
  </tr>
  <tr>
     <td class="fieldname" align="right"  width="37%"> <bean:message  key="prompt.or"/> </td>
  </tr>
   <tr>
    <td height="20" width="22%" class="fieldname">
      <div align="right">Loyalty ID:</div>
    </td>
    <td height="20" width="27%">
      <input type="text" class="data" name="loyalityId" size="15" MAXLENGTH="20" value="">
    </td>
  </tr>
   <tr>
     <td class="fieldname" align="right"  width="37%"> <bean:message  key="prompt.or"/> </td>
  </tr>
   <tr>
    <td height="20" width="22%" class="fieldname">
      <div align="right">Loyalty Email:</div>
    </td>
    <td height="20" width="27%">
      <input type="text" class="data" name="loyaltyEmail" size="15" MAXLENGTH="30" value="">
    </td>
  </tr>
  <tr>
     <td class="fieldname" align="right"  width="37%"> <bean:message  key="prompt.or"/> </td>
  </tr>
  <tr>
    <td height="20" width="22%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.extnum"/>:</div>
    </td>
    <td height="20" width="27%">
      <input type="text" class="data" name="<%=SearchTransactionForm.EXTERNAL_ORDER_NUMBER%>" size="30" MAXLENGTH="30" value="">
    </td>
  </tr>
  <tr>
     <TD class="fieldname" align="right"  width="37%"> <bean:message  key="prompt.or"/> </td>
  </tr>
  <tr>
    <td height="20" width="22%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.startseqnum"/>:</div>
    </td>
    <td height="20" width="27%">
      <input type="text" class="data" name="<%=SearchTransactionForm.FROM_SEQUENCE_NUMBER%>" size="4" MAXLENGTH="4"  value="" onkeyup="enableDefault(this, document.searchTransactionForm.<%=SearchTransactionForm.TO_SEQUENCE_NUMBER%>, 1)">
    </td>
    <td height="20" width="20%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.endseqnum"/>:</div>
    </td>
    <td height="20" width="31%">
      <input type="text" class="data" name="<%=SearchTransactionForm.TO_SEQUENCE_NUMBER%>" size="4" MAXLENGTH="4" value="">
    </td>
  </tr>
  <tr>
    <td height="20" width="22%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.fromregnum"/>:</div>
    </td>
    <td height="20" width="27%">
      <input type="text" class="data" maxlength="3" size="3" name="<%=SearchTransactionForm.FROM_REGISTER_NUMBER%>" value=""  onkeyup="enableDefault(this, document.searchTransactionForm.<%=SearchTransactionForm.TO_REGISTER_NUMBER%>, 3)">
    </td>
    <td height="20" width="20%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.toregnum"/>:</div>
    </td>
    <td height="20" width="31%">
      <input type="text" class="data" maxlength="3" size="3" name="<%=SearchTransactionForm.TO_REGISTER_NUMBER%>" value="">
    </td>
  </tr>

  <TR>

      <TD colspan="4" bgcolor="cccccc"> </TD>

  </TR>
  <%
    Locale locale = (Locale) LocaleMap.getLocale(LocaleConstantsIfc.DEFAULT_LOCALE);
    SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT,locale);
    String dateformat = formatter.toPattern();
  %>
  <tr>
    <td height="20" colspan="2" width="31%" class="fieldname">
      <input type="radio" id="<%=SearchTransactionForm.DATE_TYPE%>" name="<%=SearchTransactionForm.DATE_TYPE%>" value="systemDate" class="data" checked> 
      <bean:message  key="transaction.centej.trancrit.label.datetype.systemdate"/>
    </td>
  </tr>  
  <tr>
    <td width="22%">
      <div align="right" class="fieldname"><bean:message  key="transaction.centej.trancrit.label.starttrandate"/>:<span class="fieldformat"><br> <webmodules:dateFormatlabel/></span></div>
    </td>
    <td width="27%">
      <input type="text" class="calendar"  id="<%=SearchTransactionForm.FROM_TRANSACTION_DATE%>" name="<%=SearchTransactionForm.FROM_TRANSACTION_DATE%>" size="10" value='<bean:write name="transactionFromDate"/>'>
    </td>
    <td width="20%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.endtrandate"/>:<br>
        <span class="fieldformat"><webmodules:dateFormatlabel/></span></div>
    </td>
    <td width="31%">
      <input type="text" class="calendar" id="<%=SearchTransactionForm.TO_TRANSACTION_DATE%>" name="<%=SearchTransactionForm.TO_TRANSACTION_DATE%>" size="10" value='<bean:write name="transactionToDate"/>'>
    </td>
  </tr>
  <tr>
    <td width="22%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.starttime"/>:</div>
    </td>
    <td width="27%">
     <webmodules:timeSelection name="<%=SearchTransactionForm.FROM_TRANSACTION_TIME%>" onClickAction="updateFromTime()"/>    </td>

    <td width="20%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.endtime"/>:</div>

    </td>
    <td width="31%">
           <webmodules:timeSelection name="<%=SearchTransactionForm.TO_TRANSACTION_TIME%>" onClickAction="updateToTime()"/>     </td>
  </tr>
  <tr>
    <td height="20" colspan="2" width="31%" class="fieldname">
      <input type="radio" id="<%=SearchTransactionForm.DATE_TYPE%>" name="<%=SearchTransactionForm.DATE_TYPE%>" value="businessDate" class="data"> 
      <bean:message  key="transaction.centej.trancrit.label.datetype.businessdate"/>
    </td>
  </tr>    
  <tr>
    <td width="22%">
      <div align="right" class="fieldname"><bean:message  key="transaction.centej.trancrit.label.businessdate"/>:<span class="fieldformat"><br> <webmodules:dateFormatlabel/></span></div>
    </td>
    <td width="27%">
      <input type="text" class="calendar"  id="<%=SearchTransactionForm.BUSINESS_DATE%>" name="<%=SearchTransactionForm.BUSINESS_DATE%>" size="10" value=''>
    </td>
  </tr>  
  <tr> 
    <td>&nbsp;</td> <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="22%" class="fieldname">
      <div align="right"><bean:message  key="transaction.centej.trancrit.label.trantype"/>:</div>
    </td>
    <td width="27%">
      <select class="selectlist" name="<%=SearchTransactionForm.TRANSACTION_TYPE%>" size="7" multiple>
      <option selected value="0"><bean:message  key="option.all"/></option>

        <!-- Option values must correspond to numeric type descriptor Strings defined
              in oracle.retail.stores.commerceservices.transaction.TransactionType -->

        <option value="1"><bean:message  key="transaction.centej.trancrit.trantype.sale"/></option>
        <option value="2"><bean:message  key="transaction.centej.trancrit.trantype.return"/></option>
        <option value="3"><bean:message  key="transaction.centej.trancrit.trantype.void"/></option>
        <option value="4"><bean:message  key="transaction.centej.trancrit.trantype.nosale"/></option>
        <option value="6"><bean:message  key="transaction.centej.trancrit.trantype.openstore"/></option>
        <option value="7"><bean:message  key="transaction.centej.trancrit.trantype.closestore"/></option>
        <option value="8"><bean:message  key="transaction.centej.trancrit.trantype.openregister"/></option>
        <option value="9"><bean:message  key="transaction.centej.trancrit.trantype.closeregister"/></option>
        <option value="10"><bean:message  key="transaction.centej.trancrit.trantype.opentill"/></option>
        <option value="11"><bean:message  key="transaction.centej.trancrit.trantype.closetill"/></option>
        <option value="12"><bean:message  key="transaction.centej.trancrit.trantype.tillloan"/></option>
        <option value="13"><bean:message  key="transaction.centej.trancrit.trantype.tillpickup"/></option>
        <option value="16"><bean:message  key="transaction.centej.trancrit.trantype.tillpayin"/></option>
        <option value="17"><bean:message  key="transaction.centej.trancrit.trantype.tillpayout"/></option>
        <option value="18"><bean:message  key="transaction.centej.trancrit.trantype.houseaccountpayment"/></option>
        <option value="19"><bean:message  key="transaction.centej.trancrit.trantype.layaway"/></option>
        <option value="20"><bean:message  key="transaction.centej.trancrit.trantype.layawaycomplete"/></option>
        <option value="21"><bean:message  key="transaction.centej.trancrit.trantype.layawaypayment"/></option>
        <option value="22"><bean:message  key="transaction.centej.trancrit.trantype.layawaydelete"/></option>
        <option value="23"><bean:message  key="transaction.centej.trancrit.trantype.specialorder"/></option>
        <option value="24"><bean:message  key="transaction.centej.trancrit.trantype.ordercomplete"/></option>
        <option value="25"><bean:message  key="transaction.centej.trancrit.trantype.ordercancel"/></option>
        <option value="26"><bean:message  key="transaction.centej.trancrit.trantype.orderpartial"/></option>
        <option value="35"><bean:message  key="transaction.centej.trancrit.trantype.instantcreditenrollment"/></option>
        <option value="36"><bean:message  key="transaction.centej.trancrit.trantype.redeem"/></option>
        <option value="44"><bean:message  key="transaction.centej.trancrit.trantype.billpayment"/></option>
    </select>
      </td>
      <td width="20%" class="fieldname">
          <div align="right"><bean:message  key="transaction.centej.trancrit.label.trainingmode"/>:</div>
      </td>
      <td width="31%">
          <table class="normal" width="100%" border="0" cellspacing="1" cellpadding="3">
             <tr><td><input type="radio" id="<%=SearchTransactionForm.TRAINING_MODE%>" name="<%=SearchTransactionForm.TRAINING_MODE%>" value="on" class="data"> <bean:message  key="transaction.centej.trancrit.switch.on"/> </td></tr>
             <tr><td><input type="radio" id="<%=SearchTransactionForm.TRAINING_MODE%>" name="<%=SearchTransactionForm.TRAINING_MODE%>" value="off" class="data"> <bean:message  key="transaction.centej.trancrit.switch.off"/> </td></tr>
             <tr><td><input type="radio" id="<%=SearchTransactionForm.TRAINING_MODE%>" name="<%=SearchTransactionForm.TRAINING_MODE%>" value="both" class="data" checked> <bean:message  key="transaction.centej.trancrit.switch.both"/> </td></tr>
          </table>
      </td>
    </tr>

    <tr>
      <td width="22%" class="fieldname">
        &nbsp;
      </td>
      <td width="27%">
      &nbsp;
      </td>
      <td width="20%" class="fieldname">
        <div align="right"><bean:message  key="transaction.centej.trancrit.label.reentrymode"/>:</div>
      </td>
    <td width="31%">
        <table class="normal" width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr><td><input class="data" type="radio" id="<%=SearchTransactionForm.REENTRY_MODE%>" name="<%=SearchTransactionForm.REENTRY_MODE%>" value="on"> <bean:message  key="transaction.centej.trancrit.switch.on"/> </td></tr>
          <tr><td><input class="data" type="radio" id="<%=SearchTransactionForm.REENTRY_MODE%>" name="<%=SearchTransactionForm.REENTRY_MODE%>" value="off"> <bean:message  key="transaction.centej.trancrit.switch.off"/> </td></tr>
          <tr><td><input class="data" type="radio" id="<%=SearchTransactionForm.REENTRY_MODE%>" name="<%=SearchTransactionForm.REENTRY_MODE%>" value="both" checked> <bean:message  key="transaction.centej.trancrit.switch.both"/> </td></tr>
        </table>
    </td>
    </tr>

<!-- not yet implemented on db side

      <tr>
        <td width="37%" class="fieldname">
          <div align="right"><bean:message  key="transaction.centej.trancrit.label.mgroverride"/>:</div>
        </td>
        <td width="19%">
          <input type="checkbox" name="<%=SearchTransactionForm.MANAGER_OVERRIDE%>" value="checkbox">
        </td>
        <td width="28%" class="fieldname">&nbsp;</td>
        <td width="16%">&nbsp;</td>
      </tr>
-->

</table>
<!--- This is the expandTransaction??  - <%=transactionCriteria%>-->
