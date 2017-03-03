/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/webapp/transaction-webapp/src/app/oracle/retail/stores/webmodules/transaction/app/ejb/EJournalManagerBean.java /rgbustores_13.4x_generic_branch/9 2011/10/31 16:15:02 ohorne Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    ohorne    10/29/11 - added scaling of sigcap svg
 *    ohorne    08/05/11 - removed isValidTenderId() no longer needed due to UI
 *                         Tender Search changes
 *    cgreene   07/15/11 - removed encrypted expiration date from datamodel
 *    vtemker   05/23/11 - Using spring to fetch PAN mask property
 *    blarsen   05/18/11 - Commented out getMask(). It is causing library
 *                         depency issues and may need to be moved for
 *                         consistency reasons.
 *    vtemker   05/17/11 - Added null check for TenderCriteria to avoid
 *                         NullPpointerException in getTransactions()
 *    vtemker   05/11/11 - Adding isValidatedTenderType method
 *    vtemker   05/04/11 - Calling Util.getMask() from isValidTenderId() method
 *    vtemker   05/02/11 - Added method to fetch mask character
 *    vtemker   05/02/11 - Implemented the isValidTenderId method
 *    cgreene   03/28/11 - XbranchMerge cgreene_itemsearch from main
 *    cgreene   03/28/11 - refactor PagedList and return via PluItemDAO search
 *                         query.
 *    rrkohli   12/22/10 - CO -BillPay with credit tender, at Trans detail
 *                         screen, select sig-cap link, get 'unexpected
 *                         exception' fixed
 *    cgreene   11/10/10 - log inof instead of warn when no sigcap
 *    tzgarba   10/18/10 - Optimized getTransactions to only retrieve the
 *                         customer ID instead of the entire customer
 *    acadar    10/06/10 - remove check for transaction type - any retail
 *                         transaction can have a customer associated with it
 *    jkoppolu  09/28/10 - Logged the error message incase of exception in
 *                         getCustomerForTransaction.
 *    jkoppolu  09/27/10 - BUG# 872, Optimize transaction search.
 *    blarsen   08/23/10 - Removed credit card type role filtering feature.
 *                         Renamed/deprecated misleading method name. Fixed a
 *                         few compiler warnings.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update ade header date
 *    acadar    07/07/09 - merge from 13.1.x
 *    acadar    07/07/09 - XbranchMerge acadar_bug-8650574 from
 *                         rgbustores_13.1x_branch
 *    acadar    07/02/09 - additional comments added
 *    acadar    07/02/09 - do not display unexpected exception if customer
 *                         linked to a transaction was removed from the
 *                         database
 *    ohorne    05/27/09 - removed deprecation from non-deprecated method
 *    npoola    04/18/09 - fixed the signature transaction tracker displaying
 *                         signature and data replication in to OR_RCPNT_DTL
 *                         table
 *    mahising  03/25/09 - change order type from special order to order for
 *                         order transaction
 *    aphulamb  11/24/08 - Checking files after code review by amrish
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *
 * ===========================================================================
 * $Log:
 *  15   360Commerce 1.14        6/4/2008 12:32:36 AM   Naveen Ganesh
 *       retrieveSignatureCaptureData method is modified to take redeem
 *       transactions also
 *  14   360Commerce 1.13        5/1/2008 2:34:37 PM    Christian Greene check
 *       for null results object
 *  13   360Commerce 1.12        4/30/2008 3:15:17 PM   Christian Greene 31523
 *       Limit Sig-Cap trans tracker results to trans types that take credit
 *       cards. Change StringBuffer to StringBuilder and add generics to
 *       collections.
 *  12   360Commerce 1.11        3/21/2008 1:49:13 PM   Christian Greene 29687
 *       Merge from v12x branch. Prevent duplicate EJ's from from being sent
 *       to store server
 *  11   360Commerce 1.10        3/7/2008 8:55:12 AM    Manas Sahu      For
 *       CreditDebitTenderLineItemsDTO set the expiration date as xx / xxxx in
 *        renderSignatureCapture
 *  10   360Commerce 1.9         6/1/2007 4:41:59 PM    Sandy Gu        added
 *       functional tests
 *  9    360Commerce 1.8         5/23/2007 11:24:59 AM  Sandy Gu        Enhance
 *        tracker to display and export vat summary
 *  8    360Commerce 1.7         8/10/2006 11:23:05 AM  Sandy Gu        Merge
 *       fix for CR10474 from v7x into trunk
 *  7    360Commerce 1.6         1/25/2006 5:58:42 PM   Kulbhushan Sharma
 *       Merged in 7.1.1. changes
 *  6    360Commerce 1.5         1/22/2006 11:48:56 AM  Ron W. Haight   removed
 *        references to com.ibm.math.BigDecimal
 *  5    360Commerce 1.4         12/13/2005 4:44:49 PM  Barry A. Pape
 *       Base-lining of 7.1_LA
 *  4    360Commerce 1.3         3/31/2005 4:27:54 PM   Robert Pearse
 *  3    360Commerce 1.2         3/10/2005 10:21:13 AM  Robert Pearse
 *  2    360Commerce 1.1         3/2/2005 10:30:08 AM   Robert Pearse
 *  1    360Commerce 1.0         2/24/2005 5:59:41 PM   Robert Pearse
 * $
 * ===========================================================================
 */
package oracle.retail.stores.webmodules.transaction.app.ejb;

import gdyn.retail.stores.webmodules.ejournal.GDYNLoyalityEJournalViewBean;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.ObjectNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import oracle.retail.stores.commerceservices.common.EmptySearchResultsException;
import oracle.retail.stores.commerceservices.common.InvalidTypeException;
import oracle.retail.stores.commerceservices.common.SearchException;
import oracle.retail.stores.commerceservices.common.SearchResultSizeExceededException;
import oracle.retail.stores.commerceservices.customer.CustomerDTO;
import oracle.retail.stores.commerceservices.customer.ejb.CustomerServiceHome;
import oracle.retail.stores.commerceservices.customer.ejb.CustomerServiceRemote;
import oracle.retail.stores.commerceservices.ejournal.EJournalDTO;
import oracle.retail.stores.commerceservices.ejournal.EJournalKey;
import oracle.retail.stores.commerceservices.employee.EmployeeDTO;
import oracle.retail.stores.commerceservices.employee.EmployeeNotFoundException;
import oracle.retail.stores.commerceservices.employee.ejb.EmployeeServiceHome;
import oracle.retail.stores.commerceservices.employee.ejb.EmployeeServiceRemote;
import oracle.retail.stores.commerceservices.parameter.access.ParameterIfc;
import oracle.retail.stores.commerceservices.parameter.ejb.ParameterServiceHome;
import oracle.retail.stores.commerceservices.parameter.ejb.ParameterServiceRemote;
import oracle.retail.stores.commerceservices.renderer.RendererDTO;
import oracle.retail.stores.commerceservices.renderer.UnsupportedFormatException;
import oracle.retail.stores.commerceservices.report.ReportCriteriaDTO;
import oracle.retail.stores.commerceservices.report.ejb.ReportServiceHome;
import oracle.retail.stores.commerceservices.report.ejb.ReportServiceRemote;
import oracle.retail.stores.commerceservices.report.period.ReportPeriod;
import oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria;
import oracle.retail.stores.commerceservices.tender.ejb.TenderServiceHome;
import oracle.retail.stores.commerceservices.tender.ejb.TenderServiceRemote;
import oracle.retail.stores.commerceservices.tender.lineitem.creditdebit.CreditDebitLineItemDTO;
import oracle.retail.stores.commerceservices.tender.lineitem.tender.TenderLineItemDTO;
import oracle.retail.stores.commerceservices.transaction.BillpayTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.EJournalCriteria;
import oracle.retail.stores.commerceservices.transaction.EJournalKeyFormatterIfc;
import oracle.retail.stores.commerceservices.transaction.EJournalSearchDTO;
import oracle.retail.stores.commerceservices.transaction.EJournalSearchResultDTO;
import oracle.retail.stores.commerceservices.transaction.ImageDTO;
import oracle.retail.stores.commerceservices.transaction.SearchCriteria;
import oracle.retail.stores.commerceservices.transaction.TransactionDTO;
import oracle.retail.stores.commerceservices.transaction.TransactionKey;
import oracle.retail.stores.commerceservices.transaction.TransactionKeyFormatterIfc;
import oracle.retail.stores.commerceservices.transaction.TransactionSearchDTO;
import oracle.retail.stores.commerceservices.transaction.TransactionSearchResultDTO;
import oracle.retail.stores.commerceservices.transaction.discount.PriceModifierDTO;
import oracle.retail.stores.commerceservices.transaction.ejb.TransactionServiceHome;
import oracle.retail.stores.commerceservices.transaction.ejb.TransactionServiceRemote;
import oracle.retail.stores.commerceservices.transaction.order.OrderRecipientDetailDTO;
import oracle.retail.stores.commerceservices.transaction.order.OrderTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.redeem.RedeemTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.salereturn.PLULineItemDTO;
import oracle.retail.stores.commerceservices.transaction.salereturn.SaleReturnTransactionDTO;
import oracle.retail.stores.commerceservices.util.DataTransferObjectIfc;
import oracle.retail.stores.commerceservices.util.ImageUtils;
import oracle.retail.stores.common.utility.BigDecimalConstants;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.common.utility.LocaleRequestor;
import oracle.retail.stores.foundation.common.SessionBeanAdapter;
import oracle.retail.stores.utility.PagedList;
import oracle.retail.stores.webmodules.ejournal.EJournalViewBean;

import oracle.retail.stores.webmodules.transaction.app.EJournalListViewDTO;
import oracle.retail.stores.webmodules.transaction.app.EJournalManagerIfc;
import oracle.retail.stores.webmodules.transaction.app.InclusiveTaxTotalViewDTO;
import oracle.retail.stores.webmodules.transaction.app.SignatureCaptureViewDTO;
import oracle.retail.stores.webmodules.transaction.app.TransactionDetailViewDTO;
import oracle.retail.stores.webmodules.transaction.app.TransactionListViewDTO;
import oracle.retail.stores.webmodules.transaction.util.TransactionRenderUtils;

/**
 * Stateless session EJB, managing access to TransactionServiceBean for various
 * EJournal and other retail transaction functions.
 *
 * @author JRL
 * @version $Revision: /rgbustores_13.4x_generic_branch/9 $
 */
public class EJournalManagerBean extends SessionBeanAdapter implements EJournalManagerIfc
{
    /**
     * Serial version uid
     */
    private static final long serialVersionUID = -6302311431498485783L;
    
    private static final Logger logger = Logger.getLogger(EJournalManagerBean.class); //constant

    /**
     * Returns the list of tender issuers that application is configured to
     * accept.
     *
     * @return java.util.List
     * @deprecated use getAcceptedTenderIssuers().  Allowed doesn't make sense now that the card tender roles have been removed.
     */
    public List getAllowedTenderIssuers()
    {
        return getAcceptedTenderIssuers();
    }

    /**
     * Returns the list of tender issuers that application is configured to
     * accept.
     *
     * @return java.util.List
     */
    public List getAcceptedTenderIssuers()
    {
        try
        {
            List l = new ArrayList(getTenderService().getAcceptedCardIssuers());
            Collections.sort(l);
            return l;
        }
        catch (RemoteException ex)
        {
            getLogger().error("", ex);
            throw new EJBException(ex);
        }
    }

    /**
     * Returns the list of tender types that CentralOffice is configured to
     * accept.
     *
     * @return java.util.List of Strings - allowed tender types
     * @deprecated use getAcceptedTenderTypes().  Allowed doesn't make sense now that the card tender roles have been removed.
     */
    public List<String> getAllowedTenderTypes()
    {
        return getAcceptedTenderTypes();
    }

    /**
     * Returns the list of tender types that CentralOffice is configured to
     * accept.
     *
     * @return java.util.List of Strings - allowed tender types
     */
    public List<String> getAcceptedTenderTypes()
    {
        try
        {
            ArrayList<String> defaultTenderTypeList = new ArrayList<String>();
            List<String> tenderTypeList = getParameterService().getApplicationParameter("AcceptedTenderTypes",
                    defaultTenderTypeList).getValues();
            return tenderTypeList;
        }
        catch (RemoteException ex)
        {
            getLogger().error("Error calling Parameter Service", ex);
            throw new EJBException(ex);
        }
    }

    /**
     * Searches a Customer DTO using the given arguments
     *
     * @param transactionKey
     * @param businessDate
     * @return CustomerDTOIfc
     */
    public CustomerDTO getCustomerForTransaction(String key, String businessDate, Locale locale)
            throws SearchException, ParseException
    {
        try
        {
            TransactionKey transactionKey = getTransactionKeyFormatter().parse(key, businessDate);
            String customerID;
            customerID = getTransactionService().getRetailTransactionCustomerId(transactionKey);
            if (StringUtils.isNotEmpty(customerID))
            {
                LocaleRequestor lclReq = new LocaleRequestor(locale);
                getLogger().debug(" customer id = " + customerID);
                CustomerDTO customer = getCustomerService().getCustomer(customerID, lclReq);
                return customer;
            }
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        return null;
    }
    /**
     * Searches a Customer DTO using the given arguments
     *
     * @param transactionKey
     * @param businessDate
     * @return CustomerDTOIfc
     * @deprecated As of 13.1 use {@link getCustomerForTransaction(String,
     *             String, Locale }
     */
    public CustomerDTO getCustomerForTransaction(String transactionKey, String businessDate) throws SearchException,
            ParseException
    {
        return getCustomerForTransaction(transactionKey, businessDate, LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Searches an EJournal entry using the given arguments
     *
     * @param transactionKey
     * @param businessDate
     * @return oracle.retail.stores.webmodules.ejournal.EJournalViewBean
     */
    public EJournalViewBean retrieveEJournalEntry(String transactionKey, String businessDate) throws ParseException
    {
        EJournalViewBean viewBean = new EJournalViewBean("", "", "");
        EJournalDTO dto = getEJournal(getTransactionKey(transactionKey, businessDate));
        viewBean = new EJournalViewBean(transactionKey, businessDate, dto.getTape());
        return viewBean;

    }

    /**
     * Searches an EJournal entry using the given arguments this retrieve is
     * used from the ejournal list view since it contains non-transaction
     * ejournal entries
     *
     * @param transactionKey
     * @param businessDate
     * @param transactionBeginDate
     * @return oracle.retail.stores.webmodules.ejournal.EJournalViewBean
     */
    public EJournalViewBean retrieveEJournalEntry(String transactionKey, String businessDate,
            String transactionBeginDate) throws ParseException
    {
        EJournalViewBean viewBean = new EJournalViewBean("", "", "", "");
        EJournalDTO dto = getEJournal(getEJournalKey(transactionKey, businessDate, transactionBeginDate));
        viewBean = new EJournalViewBean(transactionKey, businessDate, transactionBeginDate, dto.getTape());
        return viewBean;

    }

    private TransactionKey getTransactionKey(String transactionNumber, String businessDate) throws ParseException
    {
        return getTransactionKeyFormatter().parse(transactionNumber, businessDate);
    }

    private EJournalKey getEJournalKey(String transactionNumber, String businessDate, String transactoinBeginDate)
            throws ParseException
    {
        return getEJournalKeyFormatter().parse(transactionNumber, businessDate, transactoinBeginDate);
    }

    private EJournalDTO getEJournal(TransactionKey key)
    {
        try
        {
            return getTransactionService().retrieveEJournalEntry(key);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    private EJournalDTO getEJournal(EJournalKey key)
    {
        try
        {
            return getTransactionService().retrieveEJournalEntry(key);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }
    

    /**
	 *This method is created by Dharmendra on 28/11/2016 to update loyality details in table CT_TRN_LYLT table based on the below parameters
	 * @param seqNumber
	 * @param loyalityId
	 * @param loyalityEmail
	 * @param workstationId
	 * @param storeNumber
	 * @throws RemoteException
	 */
    public boolean updateLoyalityDetails(String seqNumber,String loyalityId,String loyalityEmail,String workstationId,String storeNumber)
    {	
        try
        {
         return	 getTransactionService().updateLoyalityDtls(seqNumber, loyalityId, loyalityEmail,workstationId,storeNumber);
            
        }
        catch (Exception e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
       
    }
       
  

    protected RendererDTO render(int renderType, DataTransferObjectIfc dto, Map extraElements, Locale locale)
    {
        try
        {
            extraElements.put("dto", dto);
            ReportCriteriaDTO parameters = new ReportCriteriaDTO(ReportPeriod.ABSOLUTE, null, new Date(), new Date(),
                    -1, 0, extraElements);
            parameters.setLocale(locale);
            ReportServiceRemote reportService = getReportService();
            getLogger().debug("rendering dto ---> " + dto);
            return reportService.execute(reportService.getReportNameFromClass(dto.getClass()), renderType, parameters);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        catch (UnsupportedFormatException e)
        {
            throw new EJBException(e);
        }
    }

    protected RendererDTO renderViews(int renderType, Collection dtos, Map extraElements, Locale locale)
    {
        try
        {
            extraElements.put("dtos", dtos);
            ReportCriteriaDTO parameters = new ReportCriteriaDTO(ReportPeriod.ABSOLUTE, null, new Date(), new Date(),
                    -1, 0, extraElements);
            parameters.setLocale(locale);
            ReportServiceRemote reportService = getReportService();
            getLogger().debug("rendering list views dtos ---> " + dtos);

            if (dtos.size() > 0)
            {
                DataTransferObjectIfc dto = (DataTransferObjectIfc)dtos.iterator().next();
                return reportService.execute(reportService.getReportNameFromClass(dto.getClass()), renderType,
                        parameters);
            }
            else
            {
                return new RendererDTO();
            }

        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        catch (UnsupportedFormatException e)
        {
            throw new EJBException(e);
        }
    }

    private RendererDTO render(int renderType, Collection<DataTransferObjectIfc> dtos, Map extraElements, Locale locale)
    {
        try
        {
            getLogger().debug("rendinger dtos ---> " + dtos);
            extraElements.put("dtos", dtos);
            extraElements.put("renderFormat", new Integer(renderType));
            ReportCriteriaDTO parameters = new ReportCriteriaDTO(ReportPeriod.ABSOLUTE, null, new Date(), new Date(),
                    -1, 0, extraElements);
            parameters.setLocale(locale);
            ReportServiceRemote reportService = getReportService();
            return reportService.execute("MultipleDTOReport", renderType, parameters);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        catch (UnsupportedFormatException e)
        {
            throw new EJBException(e);
        }
    }

    /*
     * Render an EJournal entry
     */
    public RendererDTO renderEJournalEntry(String transactionKey, String businessDate, String transactionBeginDate,
            int renderType, Map extraElements, Locale locale) throws ParseException
    {
        EJournalDTO dto;
        // non-transactional ejournals all have 0000 for their sequence #
        // so, they require the ejournal begin date to be uniquely identified
        // thus, non-transaction ejournal requests include a transaction begin
        // date
        if (transactionBeginDate != null && !transactionBeginDate.equals(""))
        {
            dto = getEJournal(getEJournalKey(transactionKey, businessDate, transactionBeginDate));
        }
        // ejournals created for a transactions are uniquely identified by
        // sequence # and biz date
        else
        {
            dto = getEJournal(getTransactionKey(transactionKey, businessDate));
        }
        return render(renderType, dto, extraElements, locale);
    }

    /**
     * @deprecated As of 13.1 use {@link renderEJournalEntry(String, String,
     *             String, int, Map, Locale
     */
    public RendererDTO renderEJournalEntry(String transactionKey, String businessDate, String transactionBeginDate,
            int renderType, Map extraElements) throws ParseException
    {
        return renderEJournalEntry(transactionKey, businessDate, transactionBeginDate, renderType, extraElements,
                LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Render customr for transaction
     *
     * @param transactionKey
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @param locale
     * @return
     * @throws ParseException
     * @throws SearchException
     */
    public RendererDTO renderCustomerForTransaction(String transactionKey, String businessDate, int renderType,
            Map extraElements, Locale locale) throws ParseException, SearchException
    {
        CustomerDTO customerDTO = getCustomerForTransaction(transactionKey, businessDate, locale);
        return render(renderType, customerDTO, extraElements, locale);
    }

    /**
     * @deprecated As of 13.1 use {@link renderEJournalEntry(String, String,
     *             String, int, Map, Locale
     */
    public RendererDTO renderCustomerForTransaction(String transactionKey, String businessDate, int renderType,
            Map extraElements) throws ParseException, SearchException
    {
        return renderCustomerForTransaction(transactionKey, businessDate, renderType, extraElements, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Renders a signature capture data view, consisting of transaction
     * information and signature images into various output formats, most
     * notably PDF.
     *
     * @param transactionKey
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     * @deprecated As of 13.1 use {@link renderSignatureCaptureData}
     */
    public RendererDTO renderSignatureCaptureData(String transactionKey, String businessDate, int renderType,
            Map extraElements) throws ParseException, InvalidTypeException
    {
        return renderSignatureCaptureData(transactionKey, businessDate, renderType, extraElements, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Renders a signature capture data view, consisting of transaction
     * information and signature images into various output formats, most
     * notably PDF.
     *
     * @param transactionKey
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @param locale
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     */
    public RendererDTO renderSignatureCaptureData(String transactionKey, String businessDate, int renderType,
            Map extraElements, Locale locale) throws ParseException, InvalidTypeException
    {
        try
        {
            SignatureCaptureViewDTO signatureCaptureViewDTO = retrieveSignatureCaptureData(transactionKey,
                    businessDate, locale);
            return render(renderType, signatureCaptureViewDTO, extraElements, locale);
        }
        catch (ObjectNotFoundException e)
        {
            getLogger().error("", e);
            throw new EJBException(e.getMessage());
        }
    }

    /**
     * @param transactionNumber
     * @param dateString
     * @param lineItemID
     * @return oracle.retail.stores.commerceservices.transaction.ImageDTO
     * @deprecated As of 13.1 use {@link retrieveSignatureImage}
     */
    public ImageDTO retrieveSignatureImage(String transactionNumber, String dateString, int lineItemID)
            throws ParseException
    {
        return retrieveSignatureImage(transactionNumber, dateString, lineItemID, LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * @param transactionNumber
     * @param dateString
     * @param lineItemID
     * @param locale
     * @return oracle.retail.stores.commerceservices.transaction.ImageDTO
     */
    public ImageDTO retrieveSignatureImage(String transactionNumber, String dateString, int lineItemID, Locale locale)
            throws ParseException
    {
        SignatureCaptureViewDTO dto;
        try
        {
            dto = retrieveSignatureCaptureData(transactionNumber, dateString, locale);
        }
        catch (InvalidTypeException e)
        {
            getLogger().error("", e);
            throw new EJBException(e.getMessage());
        }
        catch (ObjectNotFoundException e)
        {
            getLogger().error("", e);
            throw new EJBException(e.getMessage());
        }
        Collection lineItems = dto.getCreditLineItems();
        Iterator iter = lineItems.iterator();
        while (iter.hasNext())
        {
            CreditDebitLineItemDTO creditDTO = (CreditDebitLineItemDTO)iter.next();
            if (creditDTO.getLineItemSequenceNumber() == lineItemID)
            {
                ImageDTO imageDTO = null;
                byte[] signatureImage = null;
                if (SignatureCaptureViewDTO.SVG.equals(dto.getSignatureFormat()))
                {
                    signatureImage = ImageUtils.getInstance().convertXYStringToSVGString(
                            new String(creditDTO.getCustomerSignatureImage()),500, 250).getBytes();
                }
                else
                {
                    signatureImage = creditDTO.getCustomerSignatureImage();
                }
                imageDTO = new ImageDTO(signatureImage, dto.getSignatureFormat());
                return imageDTO;
            }
        }
        throw new EJBException("no image found for " + transactionNumber + " " + dateString + " " + lineItemID);
    }

    /**
     * @param transactionNumber
     * @param dateString
     * @param orderRecipient
     * @return oracle.retail.stores.commerceservices.transaction.ImageDTO
     */
    public ImageDTO retrieveDelivarySignatureImage(String transactionNumber, String dateString, int orderRecipient)
            throws ParseException
    {
        SignatureCaptureViewDTO dto;
        try
        {
            dto = retrieveSignatureCaptureData(transactionNumber, dateString);
        }
        catch (InvalidTypeException e)
        {
            getLogger().error("", e);
            throw new EJBException(e.getMessage());
        }
        catch (ObjectNotFoundException e)
        {
            getLogger().error("", e);
            throw new EJBException(e.getMessage());
        }
        Collection ordItems = dto.getOrderRecipientItems();
        Iterator iter = ordItems.iterator();
        while (iter.hasNext())
        {
            OrderRecipientDetailDTO ordDTO = (OrderRecipientDetailDTO)iter.next();
            if (ordDTO.getOrderRecipient() == orderRecipient)
            {
                ImageDTO imageDTO = null;
                byte[] signatureImage = null;
                if (SignatureCaptureViewDTO.SVG.equals(dto.getSignatureFormat()))
                {
                    signatureImage = ImageUtils.getInstance().convertXYStringToSVGString(
                            new String(ordDTO.getSignatureImage())).getBytes();
                }
                else
                {
                    signatureImage = ordDTO.getSignatureImage();
                }
                imageDTO = new ImageDTO(signatureImage, dto.getSignatureFormat());
                return imageDTO;
            }
        }
        throw new EJBException("no image found for " + transactionNumber + " " + dateString + " " + orderRecipient);
    }

    /**
     * Returns Transaction DTO, including any aggregate objects.
     *
     * @param keyString
     * @param dateString
     * @return
     * @deprecated As of 13.1 use {@link retrieveTransaction}
     */
    public TransactionDTO retrieveTransaction(String keyString, String dateString) throws ParseException,
            ObjectNotFoundException, InvalidTypeException
    {
        return retrieveTransaction(keyString, dateString, LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Returns Transaction DTO, including any aggregate objects.
     *
     * @param keyString
     * @param dateString
     * @param locale
     * @return
     */
    public TransactionDTO retrieveTransaction(String keyString, String dateString, Locale locale)
            throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        try
        {
            TransactionKey transactionKey = getTransactionKeyFormatter().parse(keyString, dateString);
            TransactionDTO transactionDTO = getTransactionService().retrieveTransaction(transactionKey, locale);

            String type = transactionDTO.getType();
            // change Special Order to Order
            if (type != null && type.equalsIgnoreCase("SpecialOrder"))
            {
                transactionDTO.setType("Order");
            }

            return transactionDTO;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * @param keyString
     * @param dateString
     * @return TransactionDetailViewDTO
     * @deprecated As of 13.1 use {@link retrieveTransactionDetails}
     */
    public TransactionDetailViewDTO retrieveTransactionDetails(String keyString, String dateString)
            throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        return retrieveTransactionDetails(keyString, dateString, LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * @param keyString
     * @param dateString
     * @param locale
     * @return TransactionDetailViewDTO
     */
    public TransactionDetailViewDTO retrieveTransactionDetails(String keyString, String dateString, Locale locale)
            throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        TransactionDetailViewDTO transactionDetailViewDTO = new TransactionDetailViewDTO();

        try
        {
            // Get the transaction information
            TransactionKey transactionKey = getTransactionKeyFormatter().parse(keyString, dateString);
            TransactionDTO transactionDTO = getTransactionService().retrieveTransaction(transactionKey, locale);
            transactionDetailViewDTO.setTransaction(transactionDTO);

            // Get the employee information
            if (transactionDTO instanceof SaleReturnTransactionDTO)
            {
                SaleReturnTransactionDTO saleReturnTransactionDTO = (SaleReturnTransactionDTO)transactionDTO;

                // set inclusive tax summary
                Collection inclusiveTaxSummary = TransactionRenderUtils
                        .getInclusiveTaxSummary(saleReturnTransactionDTO);
                transactionDetailViewDTO.setInclusiveTaxSummary(inclusiveTaxSummary);

                // set inclusive tax totals
                InclusiveTaxTotalViewDTO inclusiveTaxTotals = TransactionRenderUtils
                        .getInclusiveTaxTotals(inclusiveTaxSummary);
                transactionDetailViewDTO.setInclusiveTaxTotals(inclusiveTaxTotals);

                for (Iterator srlii = saleReturnTransactionDTO.getSaleReturnLineItemDTOs().iterator(); srlii.hasNext();)
                {
                    PLULineItemDTO lineItemDTO = (PLULineItemDTO)srlii.next();

                    BigDecimal discount = lineItemDTO.getDiscountAmount();
                    if (discount != null && !discount.equals(BigDecimalConstants.ZERO))
                    {
                        for (Iterator pmi = lineItemDTO.getPriceModifiers().iterator(); pmi.hasNext();)
                        {
                            PriceModifierDTO priceModifier = (PriceModifierDTO)pmi.next();
                            String employeeID = priceModifier.getEmployeeDiscountEmployeeID();
                            if (StringUtils.isNotEmpty(employeeID))
                            {
                                try
                                {
                                    EmployeeServiceRemote employeeService = getEmployeeService();
                                    EmployeeDTO employeeDTO = employeeService.getEmployee(employeeID);
                                    transactionDetailViewDTO.addEmployee(employeeDTO);
                                }
                                catch (EmployeeNotFoundException e)
                                {
                                    // It will be very common to fail an
                                    // employee search, so this isn't an error
                                    getLogger().debug(
                                            "could not find Employee for id: " + employeeID + "\n" + e.getMessage());
                                }
                                catch (RemoteException e)
                                {
                                    getLogger().error("unable to access EmployeeService", e);
                                }
                            }
                        }
                    }
                }
            }

            return transactionDetailViewDTO;
        }
        catch (RemoteException e)
        {
            getLogger().error("Error retrieving transaction", e);
            throw new EJBException(e);
        }
    }

    /**
     * Renders a transaction, consisting of tender line items and sale return
     * items into various output formats
     *
     * @param transactionNumber
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     * @deprecated As of 13.1 use {@link renderTransaction}
     */

    public RendererDTO renderTransaction(String transactionNumber, String businessDate, int renderType,
            Map extraElements) throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        return renderTransaction(transactionNumber, businessDate, renderType, extraElements, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Renders a transaction, consisting of tender line items and sale return
     * items into various output formats
     *
     * @param transactionNumber
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @param locale
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     */
    public RendererDTO renderTransaction(String transactionNumber, String businessDate, int renderType,
            Map extraElements, Locale locale) throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        TransactionDTO transactionDTO = retrieveTransaction(transactionNumber, businessDate, locale);
        return render(renderType, transactionDTO, extraElements, locale);
    }

    /**
     * Renders a collection of transactions, consisting of tender line items and
     * sale return items into various output formats
     *
     * @param transactionIDs - Collection of transaction numbers.
     * @param businessDates - Collection of corresponding business dates.
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     * @deprecated As of 13.1 use {@link renderTransactions}
     */
    public RendererDTO renderTransactions(ArrayList transactionIDs, ArrayList businessDates, int renderType,
            Map extraElements) throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        return renderTransactions(transactionIDs, businessDates, renderType, extraElements, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Renders a collection of transactions, consisting of tender line items and
     * sale return items into various output formats
     *
     * @param transactionIDs - Collection of transaction numbers.
     * @param businessDates - Collection of corresponding business dates.
     * @param renderType
     * @param extraElements
     * @param locale
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     */
    public RendererDTO renderTransactions(ArrayList transactionIDs, ArrayList businessDates, int renderType,
            Map extraElements, Locale locale) throws ParseException, ObjectNotFoundException, InvalidTypeException
    {
        ArrayList<DataTransferObjectIfc> transactionDTOs = new ArrayList<DataTransferObjectIfc>(transactionIDs.size());
        Iterator iterTransactionIDs = transactionIDs.iterator();
        Iterator iterBusinessDates = businessDates.iterator();
        while (iterTransactionIDs.hasNext() && iterBusinessDates.hasNext())
        {
            transactionDTOs.add(retrieveTransaction((String)iterTransactionIDs.next(),
                    (String)iterBusinessDates.next(), locale));
        }
        return render(renderType, transactionDTOs, extraElements, locale);
    }

    /**
     * Renders a collection of ejournals.
     *
     * @param transactionIDs - Collection of transaction numbers.
     * @param businessDates - Collection of corresponding business dates.
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     */
    public RendererDTO renderEJournals(Collection transactionIDs, Collection businessDates,
            Collection transactionBeginDates, int renderType, Map extraElements, Locale locale) throws ParseException,
            ObjectNotFoundException
    {
        Collection<DataTransferObjectIfc> ejournalDTOs = new ArrayList<DataTransferObjectIfc>(transactionIDs.size());
        Iterator iterTransactionIDs = transactionIDs.iterator();
        Iterator iterBusinessDates = businessDates.iterator();
        Iterator iterTransactionBeginDates = transactionBeginDates.iterator();

        while (iterTransactionIDs.hasNext() && iterBusinessDates.hasNext())
        {
            ejournalDTOs.add(getEJournal(getEJournalKey((String)iterTransactionIDs.next(), (String)iterBusinessDates
                    .next(), (String)iterTransactionBeginDates.next())));
        }
        return render(renderType, ejournalDTOs, extraElements, locale);
    }

    /**
     * Renders a collection of ejournals.
     *
     * @param transactionIDs - Collection of transaction numbers.
     * @param businessDates - Collection of corresponding business dates.
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     * @deprecated as of 13.1 use {@link renderEJournals(Collection, Collection,
     *             Collection, int, Map, Locale)}
     */
    public RendererDTO renderEJournals(Collection transactionIDs, Collection businessDates,
            Collection transactionBeginDates, int renderType, Map extraElements) throws ParseException,
            ObjectNotFoundException
    {
        return renderEJournals(transactionIDs, businessDates, transactionBeginDates, renderType, extraElements,
                LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Renders a collection of transaction list dtos into various output
     * formats.
     *
     * @param transactionListViewDTOs
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     */
    public RendererDTO renderTransactionListViews(Collection transactionListViewDTOs, int renderType,
            Map extraElements, Locale locale) throws ParseException
    {
        return renderViews(renderType, transactionListViewDTOs, extraElements, locale);
    }

    /**
     * Renders a collection of transaction list dtos into various output
     * formats.
     *
     * @param transactionListViewDTOs
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     * @deprecated As of 13.1 use {@link renderTransactionListViews(Collection,
     *             int, Map, Locale }
     */
    public RendererDTO renderTransactionListViews(Collection transactionListViewDTOs, int renderType, Map extraElements)
            throws ParseException
    {
        return renderTransactionListViews(transactionListViewDTOs, renderType, extraElements, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Renders a collection of ejournal list dtos into various output formats.
     *
     * @param ejournalListViewDTOs
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     */
    public RendererDTO renderEJournalListViews(Collection ejournalListViewDTOs, int renderType, Map extraElements,
            Locale locale) throws ParseException
    {
        return renderViews(renderType, ejournalListViewDTOs, extraElements, locale);
    }

    /**
     * Renders a collection of ejournal list dtos into various output formats.
     *
     * @param ejournalListViewDTOs
     * @param renderType
     * @param extraElements
     * @return RendererDTO - contains information about the content type and the
     *         actual bytes for the rendered object.
     * @deprecated As of 13.1 use {@link renderEJournalListViews(Collection,
     *             int, Map, Locale }
     */
    public RendererDTO renderEJournalListViews(Collection ejournalListViewDTOs, int renderType, Map extraElements)
            throws ParseException
    {
        return renderEJournalListViews(ejournalListViewDTOs, renderType, extraElements, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    /**
     * @param transactionNumber
     * @param dateString
     * @return
     * @deprecated As of 13.1 use {@link retrieveSignatureCaptureData}
     */
    public SignatureCaptureViewDTO retrieveSignatureCaptureData(String transactionNumber, String dateString)
            throws ParseException, InvalidTypeException, ObjectNotFoundException
    {
        return retrieveSignatureCaptureData(transactionNumber, dateString, LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * @param transactionNumber
     * @param dateString
     * @param locale
     * @return
     */
    public SignatureCaptureViewDTO retrieveSignatureCaptureData(String transactionNumber, String dateString,
            Locale locale) throws ParseException, InvalidTypeException, ObjectNotFoundException
    {
        try
        {
            TransactionKey transactionKey = getTransactionKeyFormatter().parse(transactionNumber, dateString);
            TransactionDTO transactionDTO = getTransactionService().retrieveTransaction(transactionKey, locale);
            if (!(transactionDTO instanceof SaleReturnTransactionDTO)
                    && !(transactionDTO instanceof RedeemTransactionDTO) && !(transactionDTO instanceof BillpayTransactionDTO))
            {
                String warning = transactionNumber + " is an invalid transaction type for retrieving signatures. Only SaleReturns allowed.";
                getLogger().info(warning);
                throw new InvalidTypeException(warning);
            }
            TransactionDTO transDTO = transactionDTO;
            Collection lineItems = null;
            Collection orItems = null;
            SignatureCaptureViewDTO dto = null;
            Collection<OrderRecipientDetailDTO> orderLineItems = null;
            if (transactionDTO instanceof SaleReturnTransactionDTO)
            {
                SaleReturnTransactionDTO srtDTO = (SaleReturnTransactionDTO)transactionDTO;
                lineItems = srtDTO.getTenderLineItemDTOs();

            }
            else if (transactionDTO instanceof RedeemTransactionDTO)
            {
                RedeemTransactionDTO rtDTO = (RedeemTransactionDTO)transactionDTO;
                lineItems = rtDTO.getTenderLineItemDTO();
            }
            else if (transactionDTO instanceof BillpayTransactionDTO)
            {
              BillpayTransactionDTO bpDTO = (BillpayTransactionDTO)transactionDTO;
              lineItems = bpDTO.getTenderLineItemDTOs();
            }
            if (transactionDTO instanceof OrderTransactionDTO)
            {
                OrderTransactionDTO otDTO = (OrderTransactionDTO)transactionDTO;
                orItems = otDTO.getReceipentDetails();

                Iterator ordIter = orItems.iterator();
                orderLineItems = new ArrayList<OrderRecipientDetailDTO>();
                while (ordIter.hasNext())
                {
                    OrderRecipientDetailDTO lineItem = (OrderRecipientDetailDTO)ordIter.next();
                    if (lineItem instanceof OrderRecipientDetailDTO)
                    {
                        orderLineItems.add((OrderRecipientDetailDTO)lineItem);
                        if (getLogger().isDebugEnabled())
                            getLogger().debug("Adding Order Recipient lineitem: " + lineItem);
                    }
                }
            }
            Iterator iter = lineItems.iterator();
            Collection<CreditDebitLineItemDTO> creditLineItems = new ArrayList<CreditDebitLineItemDTO>();
            while (iter.hasNext())
            {
                TenderLineItemDTO lineItem = (TenderLineItemDTO)iter.next();
                if (lineItem instanceof CreditDebitLineItemDTO)
                {
                    creditLineItems.add((CreditDebitLineItemDTO)lineItem);
                    if (getLogger().isDebugEnabled())
                        getLogger().debug("Adding lineitem: " + lineItem);
                }
            }
            if (orderLineItems != null)
            {
                dto = new SignatureCaptureViewDTO(transactionDTO, creditLineItems, orderLineItems, getSignatureFormat());
            }
            else
            {
                dto = new SignatureCaptureViewDTO(transactionDTO, creditLineItems, getSignatureFormat());
            }
            return dto;
        }
        catch (RemoteException re)
        {
            getLogger().error("", re);
            throw new EJBException(re); 
        }
    }

    /**
     * @param storeSelectionCriteria
     * @param ejournalCriteria
     * @param startIndex
     * @param pageSize
     * @return
     */
    public PagedList getEJournals(StoreSelectionCriteria storeSelectionCriteria, EJournalCriteria ejournalCriteria,
            int startIndex, int pageSize) throws SearchException
    {
        // setup returnList
        PagedList returnList = new PagedList();
        if (pageSize < 0)
        {
            return returnList;
        }
        try
        {
            // search for ejournals
            EJournalSearchResultDTO results = getTransactionService().getEJournals(storeSelectionCriteria,
                    ejournalCriteria, startIndex, pageSize);
            if (results.getTotalResultCount() < 1)
            {
                throw new EmptySearchResultsException();
            }
            List<EJournalListViewDTO> resultData = new ArrayList<EJournalListViewDTO>();
            EJournalSearchDTO[] resultArray = results.getEJournals();
            for (int i = 0; i < resultArray.length; i++)
            {
                EJournalSearchDTO tx = resultArray[i];
                EJournalListViewDTO dto = new EJournalListViewDTO();
                dto.setDate(tx.getTransactionBeginDate());
                dto.setRegisterNumber(tx.getWorkstationID());
                dto.setStoreName(tx.getStoreName());
                dto.setStoreNumber(tx.getStoreID());
                dto.setTime(tx.getTransactionBeginDate());
                dto.setTransactionNumber(getTransactionKeyFormatter().format(tx.getPrimaryKey()));
                dto.setPrimaryKey(tx.getPrimaryKey());
                resultData.add(dto);
            }
            returnList = new PagedList(results.getTotalResultCount(), results.getPageSize(),
                    results.getStartRowIndex(), resultData);
            return returnList;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param pageSize
     * @return oracle.retail.stores.commerceservices.util.PagedList
     */
    public PagedList getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize, Locale locale) throws SearchException, SearchResultSizeExceededException
    {
        return getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize, null, locale);
    }

    /**
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param pageSize
     * @return oracle.retail.stores.commerceservices.util.PagedList
     * @deprecated. Please use overloaded method
     *              {@link #getTransactions(StoreSelectionCriteria, SearchCriteria, int, int, String[], locale)}
     */
    public PagedList getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize) throws SearchException, SearchResultSizeExceededException
    {
        return getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize, null, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    private String getSignatureFormat()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter("SignatureFormat", "image/png");
            List<String> values = param.getValues();
            String value = values.get(0);
            return value;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    public TransactionKeyFormatterIfc getTransactionKeyFormatter()
    {
        try
        {
            return getTransactionService().getTransactionKeyFormatter();
        }
        catch (RemoteException e)
        {
            throw new EJBException(e);
        }
    }

    public EJournalKeyFormatterIfc getEJournalKeyFormatter()
    {
        try
        {
            return getTransactionService().getEJournalKeyFormatter();
        }
        catch (RemoteException e)
        {
            throw new EJBException(e);
        }
    }

    private TransactionServiceRemote getTransactionService()
    {
        return (TransactionServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/TransactionService",
                TransactionServiceHome.class);
    }

    private TenderServiceRemote getTenderService()
    {
        return (TenderServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/TenderService",
                TenderServiceHome.class);
    }

    private CustomerServiceRemote getCustomerService()
    {
        return (CustomerServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/CustomerService",
                CustomerServiceHome.class);
    }

    private ReportServiceRemote getReportService()
    {
        return (ReportServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/ReportService",
                ReportServiceHome.class);
    }

    private ParameterServiceRemote getParameterService()
    {
        return (ParameterServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/ParameterService",
                ParameterServiceHome.class);
    }

    private EmployeeServiceRemote getEmployeeService()
    {
        return (EmployeeServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/EmployeeService",
                EmployeeServiceHome.class);
    }

    /**
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param pageSize
     * @param optionVal Tender Types Values
     * @return oracle.retail.stores.commerceservices.util.PagedList
     * @deprecated As of 13.1 use {@link getTransactions}
     */
    public PagedList getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize, String[] optionVal) throws SearchException, SearchResultSizeExceededException
    {
        return getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize, optionVal, LocaleMap
                .getLocale(LocaleMap.DEFAULT));
    }

    
    /**
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param pageSize
     * @param optionVal Tender Types Values
     * @param locale
     * @return oracle.retail.stores.commerceservices.util.PagedList
     */
  
	public PagedList<TransactionListViewDTO> getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize, String[] optionVal, Locale locale) throws SearchException,
            SearchResultSizeExceededException
    {
        if ((storeSelectionCriteria == null) && searchCriteria.allNull())
        {
            throw new EmptySearchResultsException();
        }

        PagedList<TransactionListViewDTO> returnList = new PagedList<TransactionListViewDTO>(); // setup empty return List
        if (pageSize < 0)
        {
            return returnList;
        }

        try
        {
            getLogger().debug("storeSelectionCriteria = " + storeSelectionCriteria);
            // search for transactions
            TransactionSearchResultDTO results = getTransactionService().getTransactions(storeSelectionCriteria,
                    searchCriteria, startIndex, pageSize, optionVal);
            if (results == null || results.getTotalResultCount() < 1)
            {
                throw new EmptySearchResultsException();
            }
            List<TransactionListViewDTO> resultData = new ArrayList<TransactionListViewDTO>();
            TransactionSearchDTO[] resultArray = results.getTransactions();
            for (int i = 0; i < resultArray.length; i++)
            {
                TransactionSearchDTO tx = resultArray[i];
                TransactionListViewDTO dto = new TransactionListViewDTO();
                dto.setDate(tx.getPrimaryKey().getDate());
                dto.setRegisterNumber(tx.getWorkstationID());
                dto.setStoreName(tx.getStoreName());
                dto.setStoreNumber(tx.getStoreID());
                dto.setTime(tx.getTransactionEndDate());
                dto.setTransactionNumber(getTransactionKeyFormatter().format(tx.getPrimaryKey()));
                dto.setType(tx.getType());
                dto.setPrimaryKey(tx.getPrimaryKey());
                if (getSessionContext().isCallerInRole("view_customerinfo"))
                {
                    String customerID = getTransactionService().getRetailTransactionCustomerId(tx.getPrimaryKey());
                    if (StringUtils.isNotEmpty(customerID))
                    {
                        dto.setCustomerID(customerID);
                    }
                }
                resultData.add(dto);
            }
            returnList = new PagedList<TransactionListViewDTO>(results.getTotalResultCount(), results.getPageSize(),
                    results.getStartRowIndex(), resultData);
            return returnList;
        }
        catch (SearchResultSizeExceededException se)
        {
            getLogger().warn("result total = " + se.getTotalCount());
            throw se;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }
	
	/**
	 * This method is created by Dharmendra on 28/11/2016 to retrieve loyality details from CT_TRN_LYLT table based on the below parameters
	 * @param filteredTransactionNumber
	 * @param transactionKey
	 * @param businessDate
	 * @return
	 * @throws ParseException
	 * @throws RemoteException
	 */
    
    public GDYNLoyalityEJournalViewBean retrieveLoyalityEJournal(String filteredTransactionNumber,String loyalityId,String businessDate)
    {	
    	getLogger().debug("inside retrieveLoyalityEJournal filteredTransactionNumber "+filteredTransactionNumber+" loyalityId "+loyalityId+" businessDate "+businessDate);
    	getLogger().info("inside retrieveLoyalityEJournal filteredTransactionNumber "+filteredTransactionNumber+" loyalityId "+loyalityId+" businessDate "+businessDate);
    	getLogger().warn("inside retrieveLoyalityEJournal filteredTransactionNumber "+filteredTransactionNumber+" loyalityId "+loyalityId+" businessDate "+businessDate);
    	GDYNLoyalityEJournalViewBean loyalityEJournalViewBean =	new GDYNLoyalityEJournalViewBean();
        try
        {
        	loyalityEJournalViewBean =	 getTransactionService().getLoyalityDtls(filteredTransactionNumber,loyalityId,businessDate);
            
            
        }
        catch (Exception e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
       
        getLogger().info("loyalityEJournalViewBean "+ loyalityEJournalViewBean);
        getLogger().debug("loyalityEJournalViewBean "+ loyalityEJournalViewBean);
        getLogger().warn("loyalityEJournalViewBean "+ loyalityEJournalViewBean);
        return loyalityEJournalViewBean;
    }
}
