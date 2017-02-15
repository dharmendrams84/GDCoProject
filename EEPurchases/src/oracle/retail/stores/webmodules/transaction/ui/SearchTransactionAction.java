/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *   ohorne    08/19/11 - InputCheck renamed InputMaskedMicr
 *   ohorne    08/19/11 - fix for SigCap search by tender id
 *   ohorne    08/05/11 - modified setTenderInformation() to handle different
 *                        tender id input configurations that display in the UI
 *                        based on selection of tender
 *   vtemker   05/04/11 - Moved getMask() method to Util
 *   vtemker   05/02/11 - Transaction tracker changes: 1. Remove Tender Types
 *                        and 2. Validate Tender ID via configured mask
 *   cgreene   03/28/11 - XbranchMerge cgreene_itemsearch from main
 *   cgreene   03/28/11 - updating deprecated method
 *   cgreene   12/21/10 - XbranchMerge cgreene_bug-10416485 from
 *                        rgbustores_13.3x_generic_branch
 *   cgreene   12/21/10 - removed need for flushing card data since entire card
 *                        number is not transmitted
 *   asinton   12/20/10 - XbranchMerge asinton_bug-10407292 from
 *                        rgbustores_13.3x_generic_branch
 *   asinton   12/17/10 - Deprecated hashed account numbers.
 *   cgreene   11/24/10 - remove keystore connector from bo and co and just use
 *                        simkeystore for hashing
 *   abondala  09/27/10 - Now both the system date and business date are
 *                        displyed where ever required. TransactionTracker can
 *                        search transactions by business date.
 *   cgreene   05/26/10 - convert to oracle packaging
 *   abondala  05/26/10 - Siebel - CO integration
 *   abondala  01/03/10 - update ade header date
 *   acadar    12/24/09 - always do a like search
 *   acadar    12/24/09 - always do LIKE searches for customer
 *   abondala  03/16/09 - If session is expired during search, the Chinese time
 *                        format which is multi-byte string is lost. So to fix
 *                        this problem, so to fix this problem do a time
 *                        validation and it fails redirect to the input page.
 *   mahising  03/05/09 - Fixed issue of session timeout scenerio for
 *                        transaction search
 *   sswamygo  03/02/09 - Updated to consider signature capture search
 *                         criteria
 *    mahising  02/25/09 - Fixed issue of session time out for china
 *    acadar    02/23/09 - additional changes for time
 *    acadar    02/19/09 - refactor time display for all supported locale
 *    mkochumm  01/24/09 - add country
 *    ohorne    12/03/08 - getEncryptionService() now logs when incompatible
 *                         versions of encryptionclient.jar exist
 *
 * ===========================================================================
 * $Log:
 *  21   360Commerce 1.20        4/30/2008 3:15:17 PM   Christian Greene 31523
 *       Limit Sig-Cap trans tracker results to trans types that take credit
 *       cards. Change StringBuffer to StringBuilder and add generics to
 *       collections.
 *  20   360Commerce 1.19        4/24/2008 9:32:34 AM   Edward B. Thorne
 *       Removed the Style search criteria from the user interface.  Reviewed
 *       by Tony Zgarba.
 *  19   360Commerce 1.18        4/11/2008 4:28:43 PM   Edward B. Thorne Header
 *        updates.  Removing unused code.  Reviewed by Tony Z.
 *  18   360Commerce 1.17        2/25/2008 3:30:19 PM   Rabindra P. Kar
 *       Updgrade from struts v1.1 to v1.2.9
 *  17   360Commerce 1.16        2/21/2008 4:33:39 PM   Anil Bondalapati fixed
 *       the Transaction Tracker issues.
 *  16   360Commerce 1.15        1/29/2008 1:04:07 PM   Tony Zgarba     CR
 *       29825.  Refactored the store hierarchy filtering to move the check
 *       into getStoreIDs and make it intrinsic to the lookup.  Removed the
 *       action and search criteria updates.  Fixed the 5 unit tests that were
 *        failing due to the addition of the filter.  Reviewed by Michael
 *       Barnett.
 *  15   360Commerce 1.14        12/27/2007 1:41:51 PM  Tim Solley      Fixed
 *       transaction tracker to only allow access to transactions in a user's
 *       store hierarchy
 *  14   360Commerce 1.13        12/19/2007 5:02:19 PM  Anda D. Cadar   FR42:
 *       remediation for SV.TAINT and SV.INT_OVF
 *  13   360Commerce 1.12        8/2/2007 3:16:52 PM    Brett J. Larsen CR
 *       20512 - changed the code which sets the business date in
 *       changed the "oneResult" case to get the formatted date in a way
 *       consistent with other code in this webapp
 *   12   360Commerce 1.11        7/17/2007 6:30:09 AM   Manikandan Chellapan
 *        CR27695 Modified ActionError to show search result exceede message
 *   11   360Commerce 1.10        5/31/2007 12:40:06 PM  Charles D. Baker CR
 *        26364 - Removed last references to parseNumber in favor of
 *        parseCurrency.
 *   10   360Commerce 1.9         5/23/2007 12:57:25 PM  Anil Bondalapati
 *        updated file related to I18N date changes.
 *   9    360Commerce 1.8         5/20/2007 11:11:58 PM  Anil Bondalapati
 *        updated files related to I18N currency changes. (Transaction
 *        Tracker)
 *   8    360Commerce 1.7         5/11/2007 5:39:02 PM   Naveen Ganesh
 *        Modified to support I18N
 *   7    360Commerce 1.6         4/18/2007 9:45:45 AM   Owen D. Horne   CR
 *        26172 - v7.2.2 merge to trunk
 *        *   7    .v7x       1.4.1.1     9/1/2006 2:20:23 AM    Dinesh Gautam
 *           CR
 *        *        7429: Researched in the case of multiple results.
 *   6    360Commerce 1.5         8/10/2006 11:23:05 AM  Sandy Gu        Merge
 *        fix for CR10474 from v7x into trunk
 *   5    360Commerce 1.4         1/25/2006 4:11:46 PM   Brett J. Larsen merge
 *        7.1.1 changes (aka. 7.0.3 fixes) into 360Commerce view
 *   4    360Commerce 1.3         12/13/2005 4:44:49 PM  Barry A. Pape
 *        Base-lining of 7.1_LA
 *   3    360Commerce 1.2         3/31/2005 3:29:52 PM   Robert Pearse
 *   2    360Commerce 1.1         3/2/2005 10:30:18 AM   Robert Pearse
 *   1    360Commerce 1.0         2/24/2005 6:06:06 PM   Robert Pearse
 *  $
 *   5    .v700     1.2.1.1     10/28/2005 16:20:30    Deepanshu       CR 6276:
 *        Business date was extracted from transID. As transaction ID consist
 *        of <store id><register id><sequence no>, removed business date
 *        extraction from trans ID.
 *   4    .v700     1.2.1.0     9/12/2005 13:38:49     Ravi Kalli      Modified
 *        execute and getTransactionCriteria methods
 *  Revision 1.11  2004/10/13 21:56:46  ksharma
 *  Updated with entry for serial number search criteria
 *
 *  Revision 1.10  2004/10/07 22:40:30  ksharma
 *  Updated with "Reentry Mode" search criteria
 *
 *  Revision 1.9  2004/10/07 17:26:05  ehoq
 *  Fix the regression due to transaction ID changes made previously
 *
 *  Revision 1.8  2004/10/06 22:58:48  jbudet
 *  Implementation of "CO - Transaction Tracker - Search by Tender Type" story.
 *
 *  Revision 1.7  2004/10/06 22:16:20  ksharma
 *  Updated with "Training Mode" search criteria
 *
 *  Revision 1.6  2004/10/05 19:50:14  slloyd
 *  Remove formatting from TransactionKey class, create TransactionKeyFormatterIfc and TransactionKeyFormatter for formatting/parsing of transactionnumber into key class.
 *
 *  Revision 1.5  2004/05/21 21:37:28  rdunsmore
 *  refactoring pagination
 *
 *  Revision 1.4  2004/05/05 16:17:44  cmitchell
 *  Merge from Branch.  This should be fun.
 *
 *  Revision 1.3  2004/04/12 21:12:18  sgu
 *  moved TenderType, TenderDescriptorIfc, and TenderDescriptor
 *  classes to be under currency package
 *
 *  Revision 1.2  2004/04/06 18:08:37  mcs
 *  Organized imports.
 *
 *  Revision 1.1  2004/03/17 23:28:48  jlee
 *  @scr move transaction tracker entire out and deleted the last of the commerceservices  stuff from co
 *
 *  Revision 1.7  2004/03/14 08:06:57  slloyd
 *  Fix some MySQL issues and add the Hierarchy Function ID to all queries utilizing store directory.
 *
 *  Revision 1.6  2004/03/05 08:19:51  slloyd
 *  Refactor StoreDirectory service to include 'store' nodes in Hierarchy.  Update all references to work with a single-store node as well as nodes that contain mixture of nodes/stores.
 *
 *  Revision 1.5  2004/02/04 21:49:34  mspeich
 *  Organized imports.
 *
 *  Revision 1.4  2004/01/12 22:43:58  slloyd
 *  Import fixes.
 *
 *  Revision 1.3  2003/12/26 19:03:08  jlee
 *  moved report ui level java code into its own module
 *
 *  Revision 1.2  2003/12/02 18:55:09  build
 *  conversion of ctrl-m's
 *
 *  Revision 1.1.1.1  2003/12/01 04:37:08  build
 *  Import from PVCS ${date}
 *
 *
 *    Rev 1.7   Nov 05 2003 16:20:00   jlee
 * search by new sigcap criteria
 *
 *    Rev 1.6   Nov 04 2003 16:27:56   jlee
 * ui panel for sig cap search
 *
 *    Rev 1.5   Oct 27 2003 13:29:36   jlee
 * redid ui for transaction tracker to make customer info access independent of transaction details access
 *
 * ===========================================================================
 */
package oracle.retail.stores.webmodules.transaction.ui;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.retail.stores.commerceservices.common.EmptySearchResultsException;
import oracle.retail.stores.commerceservices.common.SearchResultSizeExceededException;
import oracle.retail.stores.commerceservices.common.currency.CurrencyServiceException;
import oracle.retail.stores.commerceservices.common.currency.CurrencyServiceIfc;
import oracle.retail.stores.commerceservices.common.currency.CurrencyServiceLocator;
import oracle.retail.stores.commerceservices.currency.TenderType;
import oracle.retail.stores.commerceservices.storedirectory.HierarchyNodeKey;
import oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria;
import oracle.retail.stores.commerceservices.transaction.CustomerCriteria;
import oracle.retail.stores.commerceservices.transaction.LineItemCriteria;
import oracle.retail.stores.commerceservices.transaction.SalesAssociateCriteria;
import oracle.retail.stores.commerceservices.transaction.SearchCriteria;
import oracle.retail.stores.commerceservices.transaction.SignatureCaptureCriteria;
import oracle.retail.stores.commerceservices.transaction.TenderCriteria;
import oracle.retail.stores.commerceservices.transaction.TransactionCriteria;
import oracle.retail.stores.common.context.BeanLocator;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.foundation.common.ServiceLocator;
import oracle.retail.stores.foundation.util.DateTimeUtils;
import oracle.retail.stores.foundation.util.DateTimeUtilsIfc;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;
import oracle.retail.stores.keystoreencryption.KeyStoreEncryptionServiceIfc;
import oracle.retail.stores.utility.PagedList;
import oracle.retail.stores.webmodules.transaction.app.TransactionListViewDTO;
import oracle.retail.stores.webmodules.transaction.app.ejb.EJournalManagerHome;
import oracle.retail.stores.webmodules.transaction.app.ejb.EJournalManagerRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Parses user input to search for transactions by all sorts of criteria
 */
public class SearchTransactionAction extends Action
{
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        Locale defLocale = LocaleMap.getLocale(LocaleMap.DEFAULT);
        String loyalityId = request.getParameter("loyalityId");
        String optionVal[] = null;
        ActionMessages errors = new ActionMessages();
        String action = request.getParameter("action");

        if (request.getAttribute("action") != null)
        {
            action = (String)request.getAttribute("action");
        }
        if (action == null)
        {
            action = "NewSearch";
        }

        int startIndex;
        int pageSize;

        SearchTransactionForm searchTransactionForm = (SearchTransactionForm)form;
        SearchCriteria searchCriteria = null;
        StoreSelectionCriteria storeSelectionCriteria = null;

        if (action.equals("NewSearch"))
        {
            // if this is a new search, clear the searchCriteria in the HTTP
            // session
            request.getSession().setAttribute("searchCriteria", null);
            Integer startIndexInt = new Integer(1);
            startIndex = startIndexInt.intValue();
            Integer pageSizeInt = searchTransactionForm.getPageSize();
            pageSize = pageSizeInt.intValue();

            if (StringUtils.isNotEmpty(searchTransactionForm.getTenderTypeAccepted()))
            {
                String tenderType = searchTransactionForm.getTenderTypeAccepted();
                if (tenderType.equals("All"))
                {
                    optionVal = request.getParameterValues("optionVal");

                }
                else
                {
                    optionVal = new String[1];
                    optionVal[0] = tenderType;
                }

                request.getSession().setAttribute("optionVal", optionVal);
            }
        }
        else if ("Research".equals(action))
        {
            searchCriteria = (SearchCriteria)request.getSession().getAttribute("searchCriteria");
            storeSelectionCriteria = (StoreSelectionCriteria)request.getSession()
                    .getAttribute("storeSelectionCriteria");
            Integer startIndexInt = new Integer(1);
            startIndex = startIndexInt.intValue();
            Integer pageSizeInt = new Integer(30);
            pageSize = pageSizeInt.intValue();

            optionVal = (String[])request.getSession().getAttribute("optionVal");
        }
        else
        {
            // otherwise, they are paging and we can grab the searchCriteria
            // from the session
            searchCriteria = (SearchCriteria)request.getSession().getAttribute("searchCriteria");
            storeSelectionCriteria = (StoreSelectionCriteria)request.getSession()
                    .getAttribute("storeSelectionCriteria");
            optionVal = (String[])request.getSession().getAttribute("optionVal");
            Integer startIndexInt = searchTransactionForm.getStartIndex();
            Integer pageSizeInt = searchTransactionForm.getPageSize();
            startIndex = startIndexInt.intValue();
            pageSize = pageSizeInt.intValue();

            if (action.equals("Next"))
            {
                startIndex += (pageSize);
            }
            else if (action.equals("Prev"))
            {
                startIndex -= (pageSize);
            }
            else if (action.equals("GoToPage"))
            {
                String pageStr = request.getParameter("page");
                int page = Integer.parseInt(pageStr);
                // PCI - mitigation for SV.INT_OVF: tainted data may lead to
                // Integer Overflow
                // added check for negative value
                if (page < 0)
                {
                    page = 0;
                }
                startIndex = ((page - 1) * pageSize) + 1;
            }
        }

        if (searchCriteria == null)
        {
            storeSelectionCriteria = getStoreSelectionCriteria(searchTransactionForm);

            String[] typeCodes = request.getParameterValues("transactionType");

            // Search session time out senario for china
            String fromTime = searchTransactionForm.getFromTransactionTime();
            String toTime = searchTransactionForm.getToTransactionTime();
            if (this.servlet.getServletContext().getAttribute("fromTime") == null
                    && defLocale.getCountry().equalsIgnoreCase(Locale.CHINA.getCountry()))
            {
                this.servlet.getServletContext().setAttribute("fromTime", fromTime);
                this.servlet.getServletContext().setAttribute("toTime", toTime);
                this.servlet.getServletContext().setAttribute("fromTimeAMPM", searchTransactionForm.getFromTimeAMPM());
                this.servlet.getServletContext().setAttribute("toTimeAMPM", searchTransactionForm.getToTimeAMPM());
                request.getSession().setAttribute("fromTime", fromTime);
            }
            if (this.servlet.getServletContext().getAttribute("fromTime") != null
                    && defLocale.getCountry().equalsIgnoreCase(Locale.CHINA.getCountry())
                    && request.getSession().getAttribute("fromTime") == null)
            {
                searchTransactionForm.setFromTransactionTime(this.servlet.getServletContext().getAttribute("fromTime")
                        .toString());
                searchTransactionForm.setToTransactionTime(this.servlet.getServletContext().getAttribute("toTime")
                        .toString());
                searchTransactionForm.setFromTimeAMPM(this.servlet.getServletContext().getAttribute("fromTimeAMPM")
                        .toString());
                searchTransactionForm.setToTimeAMPM(this.servlet.getServletContext().getAttribute("toTimeAMPM")
                        .toString());
                this.servlet.getServletContext().removeAttribute("fromTime");
                this.servlet.getServletContext().removeAttribute("toTime");
                this.servlet.getServletContext().removeAttribute("fromTimeAMPM");
                this.servlet.getServletContext().removeAttribute("toTimeAMPM");
            }

            // After session is expired, Chinese time is lost. So just do validation and see if we get
            // correct time format. If not then redirect to the input page.
            searchTransactionForm.validateTime(errors);
            boolean sessionExpired = errors.size() > 0;
            if (sessionExpired)
            {
                saveErrors (request, errors);
                return (mapping.getInputForward());
            }

            searchCriteria = new SearchCriteria(getTransactionCriteria(searchTransactionForm, typeCodes, defLocale),
                    getTenderCriteria(searchTransactionForm, defLocale),
                    getSalesAssociateCriteria(searchTransactionForm), getLineItemCriteria(searchTransactionForm),
                    getCustomerCriteria(searchTransactionForm), getSignatureCaptureCriteria(searchTransactionForm));
            searchCriteria.setLocale(getLocale(request));
            /*code changes added by Dharmendra on 23/11/2016 to set Loyality Id entered by the user to TransactionCriteria*/
            if(searchCriteria!=null && searchCriteria.getTransactionCriteria()!=null
                    &&loyalityId!=null&&loyalityId.length()!=0){
                   
                  searchCriteria.getTransactionCriteria().setLoyalityId(loyalityId);
                  
                  }

            // put searchCriteria in the session for paging
            request.getSession().setAttribute("searchCriteria", searchCriteria);
            request.getSession().setAttribute("storeSelectionCriteria", storeSelectionCriteria);
        }

        EJournalManagerRemote eJournalManager = (EJournalManagerRemote)ServiceLocator.getInstance().getRemoteService(
                "java:comp/env/ejb/EJournalManager", EJournalManagerHome.class);
        // make the call to the EJournalManagerBean
     
        PagedList resultList = null;

        try
        {
            resultList = eJournalManager.getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize,
                    optionVal, getLocale(request));
        }
        catch (SearchResultSizeExceededException e)
        {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.ejournal.search.results.toomany",
                    new Integer(e.getTotalCount())));
            saveErrors(request, errors);
            return (mapping.getInputForward());
        }
        catch (EmptySearchResultsException es)
        {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("label.noRecordsFound"));
            saveErrors(request, errors);
            return (mapping.getInputForward());
        }

        // put results into the request for the jsp to display
        int endIndex = startIndex + pageSize - 1;

        if (endIndex > resultList.getTotalResultCount())
        {
            endIndex = resultList.getTotalResultCount();
        }

        // put this stuff in the request so the jsp can parse things easily
        String forwardString = null;

        if (resultList.getTotalResultCount() == 1)
        {
            forwardString = "oneResult";
            TransactionListViewDTO dto = (TransactionListViewDTO)resultList.getPageData().get(0);
            request.setAttribute("transactionNumber", dto.getTransactionNumber());
            request.setAttribute("businessDate", dto.getPrimaryKey().getFormattedDateString());
            if (StringUtils.isNotEmpty(dto.getCustomerID()))
            {
                request.setAttribute("customerID", dto.getCustomerID());
            }
        }
        else
        {
            forwardString = "multipleResults";

            request.setAttribute("startIndex", Integer.toString(startIndex));
            request.setAttribute("endIndex", Integer.toString(endIndex));
            request.setAttribute("pageSize", Integer.toString(pageSize));
            request.setAttribute("totalCount", Integer.toString(resultList.getTotalResultCount()));
            request.setAttribute("resultList", resultList.getPageData());
        }

        // put the result size in the session
        request.getSession().setAttribute("resultSize", Integer.toString(resultList.getTotalResultCount()));
        // forward control to the specified success URI
        return (mapping.findForward(forwardString));
    }

    /**
     * Loads a TransactionCriteria object with values from a
     * SearchTransactionForm and from the HttpRequest.
     *
     * @return
     *         oracle.retail.stores.commerceservices.transaction.TransactionCriteria.
     *         Null if no criteria entered.
     */
    protected TransactionCriteria getTransactionCriteria(SearchTransactionForm form, String[] typeCodes, Locale locale)
            throws ParseException, CurrencyServiceException
    {
        TransactionCriteria criteria = null;
        boolean criteriaPresent = false;
        int styleShort = DateFormat.SHORT;

        CurrencyServiceIfc currencyService = CurrencyServiceLocator.getCurrencyService();
        DateTimeUtilsIfc dateTimeUtils = DateTimeUtils.getInstance();

        if (form.getSearchByTransactionCriteria().booleanValue()) // if
        // "Transaction Info"
        // checkbox
        // checked
        {
            criteria = new TransactionCriteria();

            if (StringUtils.isNotEmpty(form.getSearchTransactionNumber()))
            {
                criteria.setTransactionNumber(form.getSearchTransactionNumber());
                criteriaPresent = true;
            }
            else if (StringUtils.isNotEmpty(form.getSearchExternalOrderNumber()))
            {
                criteria.setExternalOrderNumber(form.getSearchExternalOrderNumber());
                criteriaPresent = true;
            }
            else
            {
                if (StringUtils.isNotEmpty(form.getFromSequenceNumber()))
                {
                    criteria.setFromSequenceNumber(Integer.parseInt(form.getFromSequenceNumber()));
                    criteriaPresent = true;
                }
                if (StringUtils.isNotEmpty(form.getToSequenceNumber()))
                {
                    criteria.setToSequenceNumber(Integer.parseInt(form.getToSequenceNumber()));
                    criteriaPresent = true;
                }
                if (StringUtils.isNotEmpty(form.getFromRegisterNumber()))
                {
                    criteria.setFromWorkstationID(form.getFromRegisterNumber());
                    criteriaPresent = true;
                }
                if (StringUtils.isNotEmpty(form.getToRegisterNumber()))
                {
                    criteria.setToWorkstationID(form.getToRegisterNumber());
                    criteriaPresent = true;
                }
            }

            if (StringUtils.isNotEmpty(form.getDateType()) && (form.getDateType().equals("systemDate")))
            {
                if (StringUtils.isNotEmpty(form.getFromTransactionDate()))
                {
    
                    Date fromTransactionEndDateTime = dateTimeUtils.parseDateTime(form.getFromTransactionDate(), form
                            .getFromTransactionTime(), form.getFromTimeAMPM(), locale, styleShort);
                    criteria.setFromEndDateTime(fromTransactionEndDateTime);
                    criteriaPresent = true;
                }
                if (StringUtils.isNotEmpty(form.getToTransactionDate()))
                {
                    Date toTransactionEndDateTime = dateTimeUtils.parseDateTime(form.getToTransactionDate(), form
                            .getToTransactionTime(), form.getToTimeAMPM(), locale, styleShort);
                    criteria.setToEndDateTime(toTransactionEndDateTime);
                    criteriaPresent = true;
                }
            }
            //Search Ejournals by business date where the system date and business date may not be the same
            else 
            {
                Date businessDate = dateTimeUtils.parseDate(form.getBusinessDate(), locale, styleShort);
                String businessDateStr = dateTimeUtils.formatBusinessDate(businessDate);
                criteria.setBusinessDate(businessDateStr);
                criteriaPresent = true;
            }
            
            
            if (StringUtils.isNotEmpty(form.getTrainingMode()))
            {
                criteria.setTrainingMode(form.getTrainingMode());
                criteriaPresent = true;
            }
            if (StringUtils.isNotEmpty(form.getReentryMode()))
            {
                criteria.setReentryMode(form.getReentryMode());
                criteriaPresent = true;
            }
            criteria.setTransactionTypes(convertTypes(typeCodes));
        }

        // Read signature capture search criteria input by user
        if (form.getSearchBySignatureCaptureCriteria().booleanValue()) // if
        // "Sig Cap Info"
        // checkbox
        // checked
        {
            criteria = ((criteria == null) ? new TransactionCriteria() : criteria);
            if (StringUtils.isNotEmpty(form.getSearchTransactionNumber()))
            {
                criteria.setTransactionNumber(form.getSearchTransactionNumber());
                criteriaPresent = true;
            }
            if (StringUtils.isNotEmpty(form.getTransactionTotal()))
            {
                BigDecimal transTotal = currencyService.parseCurrency(form.getTransactionTotal(), locale);
                criteria.setTransactionTotal(transTotal);
                criteriaPresent = true;
            }
            if (StringUtils.isNotEmpty(form.getFromTransactionDate()))
            {
                Date d = dateTimeUtils.parseDate(form.getFromTransactionDate(), locale, styleShort);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);

                criteria.setFromEndDateTime(cal.getTime());
                cal.add(Calendar.DATE, 1);
                criteria.setToEndDateTime(cal.getTime());
                criteriaPresent = true;
            }
            if (typeCodes == null)
            {
                // CR31523 limit SearchBySignatureCapture to these types
                // @see
                // oracle.retail.stores.commerceservices.transaction.TransactionType
                criteria.setTransactionTypes(new int[] { 1, // Sale
                        2, // Return
                        18, // House Acct payment
                        19, // Layaway
                        20, // Layaway Complete
                        21, // Layaway Payment
                        23, // Special Order
                        24, // Order Complete
                        26, // Order Partial
                        35, // Instant Credit
                        36 // Redeem
                        });
                criteriaPresent = true;
            }
            else
            {
                criteria.setTransactionTypes(convertTypes(typeCodes));
            }
        }

        if (!criteriaPresent)
        {
            criteria = null;
        }
        return criteria;
    }

    /**
     * Converts a String array containing transaction type codes to an int[]
     *
     * @return int[] parsed input numerical Strings
     */
    int[] convertTypes(String[] types)
    {
        int[] values = new int[types.length];

        for (int i = 0; i < types.length; i++)
        {
            values[i] = Integer.parseInt(types[i]);
        }

        return values;
    }

    /**
     * Returns a TenderCriteria object based on values from a
     * SearchTransactionForm.
     *
     * @return oracle.retail.stores.commerceservices.transaction.TenderCriteria
     */
    protected TenderCriteria getTenderCriteria(SearchTransactionForm form, Locale defLocale) throws ParseException,
            CurrencyServiceException, EncryptionServiceException
    {

        CurrencyServiceIfc currencyService = CurrencyServiceLocator.getCurrencyService();

        TenderCriteria tenderCriteria = null;

        // Collect Tender Information if checked on form
        if (form.getSearchByTenderCriteria().booleanValue())
        {
            tenderCriteria = new TenderCriteria();
            String selectedTenderType = form.getTenderType();

            // Tender Type
            if (StringUtils.isNotEmpty(selectedTenderType))
            {
                if (selectedTenderType.equals("All"))
                {
                    for (int i = 0; i < TenderType.allTenderTypes.length; i++)
                    {
                        TenderType tt = TenderType.allTenderTypes[i];
                        tenderCriteria.addSearchType(tt);
                    }
                }
                else
                {
                    for (int i = 0; i < TenderType.allTenderTypes.length; i++)
                    {
                        TenderType tt = TenderType.allTenderTypes[i];

                        if (selectedTenderType.equals(tt.getDisplayString()))
                        {
                            tenderCriteria.addSearchType(tt);
                        }
                    }
                }
            }

            // Tender ID search criteria -- supplied as complete number or 
            // as leading & trailing number sequence)
            if (form.containsTenderIdentification())
            {
                setTenderInformation(tenderCriteria, form);
            }

            // Minimum Tender Amount
            if (StringUtils.isNotEmpty(form.getMinTenderAmount()))
            {
                BigDecimal minAmount = currencyService.parseCurrency(form.getMinTenderAmount(), defLocale);
                tenderCriteria.setMinimumTenderAmount(minAmount);
            }

            // Maximum Tender Amount
            if (StringUtils.isNotEmpty(form.getMaxTenderAmount()))
            {
                BigDecimal maxAmount = currencyService.parseCurrency(form.getMaxTenderAmount(), defLocale);
                tenderCriteria.setMaximumTenderAmount(maxAmount);
            }
        }

        if (form.getSearchBySignatureCaptureCriteria().booleanValue())
        {
            tenderCriteria = ((tenderCriteria == null) ? new TenderCriteria() : tenderCriteria);
            tenderCriteria.addSearchType(TenderType.CREDIT);
            if (form.containsTenderIdentification())
            {
                setTenderInformation(tenderCriteria, form);
            }
            if (StringUtils.isNotEmpty(form.getTenderAmount()))
            {
                try
                {
                    BigDecimal maxTenderAmount = currencyService.parseCurrency(form.getTenderAmount(), defLocale);
                    tenderCriteria.setMinimumTenderAmount(maxTenderAmount);
                    tenderCriteria.setMaximumTenderAmount(maxTenderAmount);
                }
                catch (CurrencyServiceException cse)
                {
                    if (cse.getCause() != null && cse.getCause() instanceof ParseException)
                    {
                        throw (ParseException)cse.getCause();
                    }
                    
                    throw new ParseException(cse.getMessage(), 0);
                }

            }
        }
        return tenderCriteria;
    }

    /**
     * Returns a SignatureCaptureCriteria object based on values from a
     * SearchTransactionForm.
     *
     * @return
     *         oracle.retail.stores.commerceservices.transaction.SignatureCaptureCriteria
     *         Null if no criteria entered.
     */
    protected SignatureCaptureCriteria getSignatureCaptureCriteria(SearchTransactionForm form) throws ParseException
    {
        SignatureCaptureCriteria criteria = null;

        if (form.getSearchBySignatureCaptureCriteria().booleanValue())
        {
            criteria = new SignatureCaptureCriteria();

            if (StringUtils.isNotEmpty(form.getTenderTypeAccepted()))
            {
                criteria.setTenderTypeAccepted(form.getTenderTypeAccepted());
            }
        }
        return criteria;
    }

    /**
     * Reads store selection criteria from a SearchTransactionForm.
     *
     * @return
     *         oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria
     *         . Null if no criteria entered.
     */
    protected StoreSelectionCriteria getStoreSelectionCriteria(SearchTransactionForm form)
    {
        StoreSelectionCriteria criteria = null;
        logger.debug("Search by Store? " + form.getSearchByStoreCriteria());
        if (form.getSearchByStoreCriteria().booleanValue()) // if
        // "(Store) Hierarchy Info"
        // checkbox checked
        {
            String searchMethod = form.getStoreSearchMethod();
            boolean useHierarchy = SearchTransactionForm.HIERARCHY_METHOD.equals(searchMethod);
            if (useHierarchy)
            {

                criteria = new StoreSelectionCriteria(new HierarchyNodeKey(form.getHierarchyId(), form
                        .getHierarchyNodeId(), form.getNodeType()));
            }
            else
            {
                criteria = new StoreSelectionCriteria(null, form.getFromStoreNumber(), form.getToStoreNumber());
            }
        }
        return criteria;
    }

    /**
     * Returns a SalesAssociateCriteria object based on values from a
     * SearchTransactionForm.
     *
     * @return
     *         oracle.retail.stores.commerceservices.transaction.SalesAssociateCriteria
     *         Null if no criteria entered.
     */
    protected SalesAssociateCriteria getSalesAssociateCriteria(SearchTransactionForm form)
    {
        SalesAssociateCriteria criteria = null;
        if (form.getSearchBySalesAssociateCriteria().booleanValue())
        {
            criteria = new SalesAssociateCriteria(form.getSalesAssociateNumber(), form.getCashierNumber());
        }

        return criteria;
    }

    /**
     * Returns a LineItemCriteria object based on values from a
     * SearchTransactionForm.
     *
     * @return oracle.retail.stores.commerceservices.transaction.LineItemCriteria.
     *         Null if no criteria have been entered.
     */
    protected LineItemCriteria getLineItemCriteria(SearchTransactionForm form)
    {
        LineItemCriteria criteria = null;

        if (form.getSearchByItemCriteria().booleanValue()) // if "Item Info"
        // checkbox checked
        {
            criteria = new LineItemCriteria();
            boolean criteriaPresent = false;

            if (StringUtils.isNotEmpty(form.getSerialNumber()))
            {
                criteria.setSerialNumber(form.getSerialNumber());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getItemNumber()))
            {
                criteria.setItemNumber(form.getItemNumber());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getUpc()))
            {
                criteria.setUPC(form.getUpc());
                criteriaPresent = true;
            }

            if (form.isItemCleared())
            {
                criteria.setItemCleared(form.isItemCleared());
                criteriaPresent = true;
            }

            if (form.isPriceOverrideApplied())
            {
                criteria.setPriceOverride(form.isPriceOverrideApplied());
                criteriaPresent = true;
            }

            if (!criteriaPresent)
            {
                criteria = null;
            }
        }

        return criteria;
    }

    /**
     * Returns the configured KeyStoreEncryptionService
     */
    protected KeyStoreEncryptionServiceIfc getEncryptionService()
    {
        Object service = BeanLocator.getServiceBean(KeyStoreEncryptionServiceIfc.KEY_STORE_ENCRYPTION_SERVICE_BEAN_KEY);
        KeyStoreEncryptionServiceIfc encryptionService = null;
        try
        {
            encryptionService = (KeyStoreEncryptionServiceIfc)service;
        }
        catch (Throwable t)
        {
            logger
                    .error(
                            "Failed cast to KeyStoreEncryptionServiceIfc -- likely cause: incompatible versions of encryptionclient.jar",
                            t);
            throw new ClassCastException();
        }
        return encryptionService;
    }

    /**
     * Configures the Search Criteria with user's desired tender id, if any
     * was indicated
     *
     * @param tenderCriteria  the search criteria
     * @param form the user input
     */
    protected void setTenderInformation(TenderCriteria tenderCriteria, SearchTransactionForm form)
    {
        //Search by TenderID is only supported when searching by a single tender type
        if (tenderCriteria.getSearchTypes() != null && tenderCriteria.getSearchTypes().size() == 1)
        {
            TenderType tenderType = tenderCriteria.getSearchTypes().iterator().next();
            
            //InputMasked: input fields for leading and trailing unmasked card numbers 
            if (form.isInputMaskedCard(tenderType))
            {
                String maskedCardNumber = form.getUnmaskedFirstDigits(tenderType) + SearchTransactionForm.getPanMask() + form.getUnmaskedLastDigits(tenderType);
                if (TenderType.CREDIT.equals(tenderType) || TenderType.DEBIT.equals(tenderType) )
                {
                    tenderCriteria.setMaskedAccountNumber(maskedCardNumber);
                }
                if (TenderType.GIFTCARD.equals(tenderType))
                {
                    tenderCriteria.setMaskedGiftCardNumber(maskedCardNumber);
                }                
            }
            
            //InputCheck: input fields for trailing unmasked bank routing & bankaccount numbers  
            if (form.isInputMaskedMicr(tenderType))
            {
                if (TenderType.CHECK.equals(tenderType) || TenderType.ECHECK.equals(tenderType) )
                {
                    String maskedMicrNumber = form.getUnmaskedFirstDigits(tenderType) + SearchTransactionForm.getPanMask() + form.getUnmaskedLastDigits(tenderType);
                    tenderCriteria.setMaskedMicrNumber(maskedMicrNumber);
                }    
            }
            
            //InputNumber: single input field for an unmasked number
            if (form.isInputNumber(tenderType))
            {
                if (TenderType.STORECREDIT.equals(tenderType))
                {
                    tenderCriteria.setStoreCreditNumber(form.getTenderID());
                }
            }            
        }
    }

    /**
     * Returns a CustomerCriteria object based on values from a
     * SearchTransactionForm.
     *
     * @return oracle.retail.stores.commerceservices.transaction.CustomerCriteria.
     *         Null if no criteria enetered.
     */
    protected CustomerCriteria getCustomerCriteria(SearchTransactionForm form)
    {
        CustomerCriteria criteria = null;

        if (form.getSearchByCustomerCriteria().booleanValue())
        {
            criteria = new CustomerCriteria();
            boolean criteriaPresent = false;

            if (StringUtils.isNotEmpty(form.getCustomerID()))
            {
                criteria.setCustomerID(form.getCustomerID());
                criteriaPresent = true;
            }

            //always use LIKE searches
            if (StringUtils.isNotEmpty(form.getCustomerFirstName()))
            {
                String firstName = form.getCustomerFirstName();
                if (firstName.lastIndexOf('*') == -1)
                {
                    firstName = firstName + '*';
                }
                criteria.setCustomerFirstName(firstName);
                criteriaPresent = true;
            }

            //Always use LIKE searches
            if (StringUtils.isNotEmpty(form.getCustomerLastName()))
            {
                String lastName = form.getCustomerLastName();
                if (lastName.lastIndexOf('*') == -1)
                {
                    lastName = lastName + '*';
                }
                criteria.setCustomerLastName(lastName);
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerAddressLine1()))
            {
                criteria.setCustomerAddressLine1(form.getCustomerAddressLine1());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerAddressLine2()))
            {
                criteria.setCustomerAddressLine2(form.getCustomerAddressLine2());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerAddressCity()))
            {
                criteria.setCustomerAddressCity(form.getCustomerAddressCity());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerAddressState()))
            {
                criteria.setCustomerAddressState(form.getCustomerAddressState());
                criteriaPresent = true;
            }
            if (StringUtils.isNotEmpty(form.getCustomerAddressCountry()))
            {
                criteria.setCustomerAddressCountry(form.getCustomerAddressCountry());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerAddressZipCode()))
            {
                criteria.setCustomerAddressZipCode(form.getCustomerAddressZipCode());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerAddressZipCodeExt()))
            {
                criteria.setCustomerAddressZipCodeExt(form.getCustomerAddressZipCodeExt());
                criteriaPresent = true;
            }

            if (StringUtils.isNotEmpty(form.getCustomerTelephoneNumber()))
            {
                criteria.setCustomerTelephoneNumber(form.getCustomerTelephoneNumber());
                criteriaPresent = true;
            }

            if (!criteriaPresent)
            {
                criteria = null;
            }
        }

        return criteria;
    }

    private static final Logger logger = Logger.getLogger(SearchTransactionAction.class);

}
