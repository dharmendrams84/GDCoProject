/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/webapp/transaction-webapp/src/app/oracle/retail/stores/webmodules/transaction/app/EJournalManagerIfc.java /rgbustores_13.4x_generic_branch/1 2011/03/28 14:19:55 cgreene Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   03/28/11 - XbranchMerge cgreene_itemsearch from main
 *    cgreene   03/28/11 - refactor PagedList and return via PluItemDAO search
 *                         query.
 *    blarsen   08/23/10 - Removed credit card type role filtering feature.
 *                         Renamed/deprecated misleading method name.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update ade header date
 *    aphulamb  11/24/08 - Checking files after code review by amrish
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *
 * ===========================================================================
 * $Log:
 *  5    .v12x      1.3.1.0     1/13/2008 2:33:18 AM   Brett J. Larsen CR 29867
 *        - non-transactional e-journal entries not supported by CO
 *
 *       Added support for e-journal begin trans date to several methods.
 *       This date is required to uniquely identify non-transactional
 *       e-journal entries.
 *
 *  4    360Commerce 1.3         8/10/2006 11:23:05 AM  Sandy Gu        Merge
 *       fix for CR10474 from v7x into trunk
 *  3    360Commerce 1.2         3/31/2005 4:27:54 PM   Robert Pearse
 *  2    360Commerce 1.1         3/2/2005 10:30:08 AM   Robert Pearse
 *  1    360Commerce 1.0         2/24/2005 5:59:42 PM   Robert Pearse
 * $
 * Revision 1.7  2004/10/20 16:51:34  jbudet
 * Updated Transaction Tracker to display Employee Discounts
 *
 * Revision 1.6  2004/10/06 22:58:48  jbudet
 * Implementation of "CO - Transaction Tracker - Search by Tender Type" story.
 *
 * Revision 1.5  2004/10/04 22:38:24  slloyd
 * Remove formatting from TransactionKey class, create TransactionKeyFormatterIfc and TransactionKeyFormatter for formatting/parsing of transactionnumber into key class.
 *
 * Revision 1.4  2004/05/21 21:37:28  rdunsmore
 * refactoring pagination
 *
 * Revision 1.3  2004/04/06 18:08:37  mcs
 * Organized imports.
 *
 * Revision 1.2  2004/03/18 21:04:51  jlee
 * @scr this is the last of the breaking stuff I swear
 *
 * Revision 1.1  2004/03/17 23:28:48  jlee
 * @scr move transaction tracker entire out and deleted the last of the commerceservices  stuff from co
 *
 * Revision 1.6  2004/02/04 21:49:35  mspeich
 * Organized imports.
 *
 * Revision 1.5  2004/01/22 16:38:59  jlee
 * dto converstion
 *
 * Revision 1.4  2003/12/18 01:00:20  jlee
 * Fixed import transaction functional test.  Iit now test both cases with workflow or not and actually verfies the transaction was inserted
 *
 * Revision 1.3  2003/12/11 04:37:07  slloyd
 * EJournal Search updates from PVCS.
 *
 * Revision 1.2  2003/12/02 18:52:06  build
 * conversion of ctrl-m's
 *
 * Revision 1.1.1.1  2003/12/01 04:37:10  build
 * Import from PVCS ${date}
 *
 *    Rev 1.44   Dec 09 2003 14:11:28   cmitchell
 * Gap fixes.  Allowing the export of ejournals and the ejournal list views.
 *
 *    Rev 1.43   Dec 05 2003 13:47:32   RDunsmore
 * added gap ejournal search
 *
 *    Rev 1.42   Nov 12 2003 23:31:18   slloyd
 *
 *    Rev 1.41   Nov 10 2003 11:22:58   jlee
 * security on cs tier
 *
 *    Rev 1.40   Nov 07 2003 16:05:46   slloyd
 * sig cap change to byte[] and blob
 *
 *    Rev 1.39   Oct 30 2003 16:19:56   slloyd
 * Fix ej detail view and no hierarchy issues.
 *
 *    Rev 1.38   Oct 27 2003 13:29:34   jlee
 * redid ui for transaction tracker to make customer info access independent of transaction details access
 *
 *    Rev 1.37   Aug 28 2003 10:36:50   slloyd
 * Move StoreHierarchy to StoreDirectory to better represent relationships between hierarchies and ad-hoc groups.
 *
 *    Rev 1.36   Jul 25 2003 13:16:58   cmitchell
 * Customer exports and security.
 *
 *    Rev 1.35   Jul 21 2003 17:18:58   pjf
 * Add retrieveCustomer()
 * Resolution for 1681: Display customer info/associate with transaction entities
 *
 *    Rev 1.34   Jul 15 2003 16:57:20   slloyd
 * Changes for making rendering successful.
 *
 *    Rev 1.32   Jul 10 2003 15:49:26   slloyd
 * Changed URL to use resource-refs.
 *
 *    Rev 1.31   Jul 07 2003 09:02:20   djefferson
 * modifications needed for Websphere migration
 *
 *    Rev 1.30   Jun 30 2003 18:47:52   jlee
 * Resolution for 1576: General development
 *
 *    Rev 1.29   Jun 06 2003 14:24:18   slloyd
 * changes to sig capture image storage
 *
 *    Rev 1.28   May 15 2003 05:53:18   pjf
 * Refactor RetailTransaction to SaleReturnTransaction
 * Resolution for 1637: Include additional transaction types in ejournal search
 *
 *    Rev 1.27   May 05 2003 15:31:18   slloyd
 * refactor transaction bean into RetailTransactionBean
 * Resolution for 1582: CentralOfficeUI
 *
 *    Rev 1.26   May 01 2003 15:12:46   cmitchell
 * Added filename to the display of all dtos.  Added Map to render method to alllow for non dto values (like filename) to be displayed along with the DTOs.
 * Resolution for 1638: Export Transactions and Transaction List Views
 *
 *    Rev 1.25   Apr 30 2003 16:29:58   cmitchell
 * -
 * Resolution for 1638: Export Transactions and Transaction List Views
 *
 *    Rev 1.24   Apr 30 2003 15:44:58   cmitchell
 * Exporting multiple transaction details and transaction list views.
 * Resolution for 1638: Export Transactions and Transaction List Views
 *
 *    Rev 1.23   Apr 16 2003 17:39:22   jlee
 * refactored exception handling to struts 1.1 way
 * Resolution for 1599: No matches found
 *
 *    Rev 1.22   Apr 15 2003 14:20:20   cmitchell
 * Minor changes made to better reflect the actual document with the generated PDF.
 * Resolution for 1579: Show TX Detail/eJournal/SigCap given a TX ID
 *
 *    Rev 1.21   Apr 09 2003 16:53:06   cmitchell
 * Adding logic to export transaction details.
 * Resolution for 1579: Show TX Detail/eJournal/SigCap given a TX ID
 *
 *    Rev 1.20   Mar 31 2003 14:40:56   jlee
 * fixed javadoc
 * Resolution for CentralOffice SCR-1579: Show TX Detail/eJournal/SigCap given a TX ID
 * ===========================================================================
 */

package oracle.retail.stores.webmodules.transaction.app;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.ObjectNotFoundException;

import oracle.retail.stores.commerceservices.common.InvalidTypeException;
import oracle.retail.stores.commerceservices.common.SearchException;
import oracle.retail.stores.commerceservices.customer.CustomerDTO;
import oracle.retail.stores.commerceservices.renderer.RendererDTO;
import oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria;
import oracle.retail.stores.commerceservices.transaction.EJournalCriteria;
import oracle.retail.stores.commerceservices.transaction.ImageDTO;
import oracle.retail.stores.commerceservices.transaction.SearchCriteria;
import oracle.retail.stores.commerceservices.transaction.TransactionDTO;
import oracle.retail.stores.commerceservices.transaction.TransactionKeyFormatterIfc;
import oracle.retail.stores.utility.PagedList;
import oracle.retail.stores.webmodules.ejournal.EJournalViewBean;
import oracle.retail.stores.webmodules.ejournal.LoyalityEJournalViewBean;

/**  EJournal Manager is a Session Facade into that exposes all the functionality needed to display
 * on the UI on the EJournal tab.  It wraps a number of methods exposed in the commerce services framework
 *
 * @author JRL
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public interface EJournalManagerIfc
{

	/**
	 *This method is created by Dharmendra on 28/11/2016 to update loyality details in table CT_TRN_LYLT table based on the below parameters
	 * @param seqNumber
	 * @param loyalityId
	 * @param loyalityEmail
	 * @param workstationId
	 * @param storeNumber
	 * @throws RemoteException
	 */
	void updateLoyalityDetails(String seqNumber,String loyalityId,String loyalityEmail,String workstationId,String storeNumber)throws RemoteException;
	
	/**
	 * This method is created by Dharmendra on 28/11/2016 to retrieve loyality details from CT_TRN_LYLT table based on the below parameters
	 * @param filteredTransactionNumber
	 * @param transactionKey
	 * @param businessDate
	 * @return
	 * @throws ParseException
	 * @throws RemoteException
	 */
	LoyalityEJournalViewBean retrieveLoyalityEJournal(String filteredTransactionNumber,String transactionKey,String businessDate) throws ParseException,RemoteException;
	
    /** gets a view dto containing the ejournal text for the specified transaction
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @return EJournalDTO
     */
	
	EJournalViewBean retrieveEJournalEntry(String transactionKey, String businessDate) throws ParseException,
            RemoteException;

    /** gets a view dto containing the ejournal text for the specified transaction
     *
     * this retrieve requires a beginDate for non-transactional ejournal entries
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @return EJournalDTO
     */
    EJournalViewBean retrieveEJournalEntry(String transactionKey, String businessDate, String transactionBeginDate)
            throws ParseException, RemoteException;

    /** gets a view dto containing the ejournal text for the specified transaction and renders it in the
     * specified format
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderEJournalEntry(String transactionKey, String businessDate, String transactionBeginDate,
            int renderType, Map extraElements, Locale locale) throws ParseException, RemoteException;

    /** gets a view dto containing the ejournal text for the specified transaction and renders it in the
     * specified format
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     * @deprecated As of 13.1 use {@link renderCustomerForTransaction(String, String, String, int, Map, Locale
     */
    RendererDTO renderEJournalEntry(String transactionKey, String businessDate, String transactionBeginDate,
            int renderType, Map extraElements) throws ParseException, RemoteException;

    /**
     *
     * @param transactionKey
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @return
     * @throws ParseException
     * @throws SearchException
     * @throws RemoteException
     */
    RendererDTO renderCustomerForTransaction(String transactionKey, String businessDate, int renderType,
            Map extraElements, Locale locale) throws ParseException, SearchException, RemoteException;

    /**
     *
     * @param transactionKey
     * @param businessDate
     * @param renderType
     * @param extraElements
     * @return
     * @throws ParseException
     * @throws SearchException
     * @throws RemoteException
     * @deprecated As of 13.1 use {@link renderCustomerForTransaction(String, String, String, int, Map, Locale
     */
    RendererDTO renderCustomerForTransaction(String transactionKey, String businessDate, int renderType,
            Map extraElements) throws ParseException, SearchException, RemoteException;

    /** gets a view dto containing all the information displayed on the Signature Capture view page
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param dateString the transaction date in yyyy-MM-dd format
     * @return SignatureCaptureViewDTO
     * @deprecated As of 13.1 use {@link retrieveSignatureCaptureData }
     */
    SignatureCaptureViewDTO retrieveSignatureCaptureData(String transactionKey, String dateString)
            throws ParseException, InvalidTypeException, RemoteException, ObjectNotFoundException;

    /**
     * gets a view dto containing all the information displayed on the Signature Capture view page
     * @param transactionKey
     * @param dateString
     * @param locale
     * @return SignatureCaptureViewDTO
     * @throws ParseException
     * @throws InvalidTypeException
     * @throws RemoteException
     * @throws ObjectNotFoundException
     */
    SignatureCaptureViewDTO retrieveSignatureCaptureData(String transactionKey, String dateString, Locale locale)
            throws ParseException, InvalidTypeException, RemoteException, ObjectNotFoundException;

    /** gets a view dto containing all the information displayed on the Signature Capture view page
     * and renders it in a specified format
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     * @deprecated As of 13.1 use {@link renderSignatureCaptureData }
     */
    RendererDTO renderSignatureCaptureData(String transactionKey, String businessDate, int renderType, Map extraElements)
            throws ParseException, InvalidTypeException, RemoteException;

    /** gets a view dto containing all the information displayed on the Signature Capture view page
     * and renders it in a specified format
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     * @param locale
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderSignatureCaptureData(String transactionKey, String businessDate, int renderType,
            Map extraElements, Locale locale) throws ParseException, InvalidTypeException, RemoteException;

    /** gets a byte array containing the PNG encoded image of the signature file
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param dateString the transaction date in yyyy-MM-dd format
     * @param lineItemID the index of the tender line item that has the image
     * @return byte[] containing a PNG images
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     * @deprecated As of 13.1 use {@link retrieveSignatureImage }
     */
    ImageDTO retrieveSignatureImage(String transactionKey, String dateString, int lineItemID) throws ParseException,
            RemoteException;

    /** gets a byte array containing the PNG encoded image of the signature file
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param dateString the transaction date in yyyy-MM-dd format
     * @param lineItemID the index of the tender line item that has the image
     * @param locale
     * @return byte[] containing a PNG images
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     */
    ImageDTO retrieveSignatureImage(String transactionKey, String dateString, int lineItemID, Locale locale)
            throws ParseException, RemoteException;

    /**
     *  Retrieves a page of EJournalListViewDTOs based on the specified search criteria
     *
     * @param storeSelectionCriteria specifies how to narrow down the search base on store
     * @param ejournalCriteria specifies how and what to search by
     * @param startIndex is the first index to return
     * @param pageSize the number of results to return
     * @return PagedList which contains the TransactionListViewDTOs
     */
    PagedList getEJournals(StoreSelectionCriteria storeSelectionCriteria, EJournalCriteria ejournalCriteria,
            int startIndex, int pageSize) throws SearchException, RemoteException;

    /**
     * @deprecated. Please use overloaded method getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,int startIndex, int pageSize, String [] optionVal) defined in this class
     *  Retrieves a page of TransactionListViewDTOs based on the specified search criteria
     * @param storeSelectionCriteria specifies how to narrow down the search base on store
     * @param searchCriteria specifies how and what to search by
     * @param startIndex is the first index to return
     * @param pageSize is the number of results to return
     * @return PagedList which contains the TransactionListViewDTOs
     * @deprecated As of 13.1 use {@link getTransactions }
     */
    PagedList getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize) throws SearchException, RemoteException;

    /**
     * @deprecated. Please use overloaded method getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,int startIndex, int pageSize, String [] optionVal) defined in this class
     *  Retrieves a page of TransactionListViewDTOs based on the specified search criteria
     * @param storeSelectionCriteria specifies how to narrow down the search base on store
     * @param searchCriteria specifies how and what to search by
     * @param startIndex is the first index to return
     * @param pageSize is the number of results to return
     * @param locale
     * @return PagedList which contains the TransactionListViewDTOs
     */
    PagedList getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize, Locale locale) throws SearchException, RemoteException;

    /** gets view dto containing all the information required to display on the transaction detail screen
     *
     * @param transactionKey the transation store, ws, and sequence number
     * @param dateString the transaction date in yyyy-MM-dd format
     * @return RetailTransactionDTO
     *
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     *  @deprecated As of 13.1 use {@link retrieveTransaction }
     */
    TransactionDTO retrieveTransaction(String transactionKey, String dateString) throws ParseException,
            ObjectNotFoundException, InvalidTypeException, RemoteException;

    /** gets view dto containing all the information required to display on the transaction detail screen
     *
     * @param transactionKey the transation store, ws, and sequence number
     * @param dateString the transaction date in yyyy-MM-dd format
     * @param locale
     * @return RetailTransactionDTO
     *
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     */
    TransactionDTO retrieveTransaction(String transactionKey, String dateString, Locale locale) throws ParseException,
            ObjectNotFoundException, InvalidTypeException, RemoteException;

    /**
     * Retrieve transaction details.
     *
     * @param transactionKey the transation store, ws, and sequence number
     * @param dateString the transaction date in yyyy-MM-dd format
     * @return RetailTransactionDTO
     *
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     * @deprecated As of 13.1 use {@link retrieveTransactionDetails }
     */
    TransactionDetailViewDTO retrieveTransactionDetails(String transactionKey, String dateString)
            throws ParseException, ObjectNotFoundException, InvalidTypeException, RemoteException;

    /**
     * Retrieve transaction details.
     *
     * @param transactionKey the transation store, ws, and sequence number
     * @param dateString the transaction date in yyyy-MM-dd format
     * @return RetailTransactionDTO
     *
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     */
    TransactionDetailViewDTO retrieveTransactionDetails(String transactionKey, String dateString, Locale locale)
            throws ParseException, ObjectNotFoundException, InvalidTypeException, RemoteException;

    /** gets a view dto containing all the information displayed on the Transaction Detail view page
     * and renders it in a specified format
     *
     * @param transactionNumber the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     * @deprecated As of 13.1 use {@link renderTransaction }
     */
    RendererDTO renderTransaction(String transactionNumber, String businessDate, int renderType, Map extraElements)
            throws ParseException, RemoteException, ObjectNotFoundException, InvalidTypeException;

    /** gets a view dto containing all the information displayed on the Transaction Detail view page
     * and renders it in a specified format
     *
     * @param transactionNumber the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     * @param locale
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderTransaction(String transactionNumber, String businessDate, int renderType, Map extraElements,
            Locale locale) throws ParseException, RemoteException, ObjectNotFoundException, InvalidTypeException;

    /**
     * Renders a collection of ejournals.
     *
     * @param transactionIDs a collection of transaction keys {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDates  a collection of the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderEJournals(Collection transactionIDs, Collection businessDates, Collection transactionBeginDates,
            int renderType, Map extraElements, Locale locale) throws ParseException, RemoteException,
            ObjectNotFoundException;

    /**
     * Renders a collection of ejournals.
     *
     * @param transactionIDs a collection of transaction keys {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDates  a collection of the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     * @deprecated as of 13.1 use {@link renderEJournals(Collection, Collection, Collection, int, Map, Locale)}
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderEJournals(Collection transactionIDs, Collection businessDates, Collection transactionBeginDates,
            int renderType, Map extraElements) throws ParseException, RemoteException, ObjectNotFoundException;

    /** gets a view dto containing all the information displayed on the Transaction Detail view page
     *  of each transaction in the collection and renders it in a specified format.
     *
     * @param transactionIDs a collection of transaction keys {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDates  a collection of the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     * @deprecated As of 13.1 use {@link renderTransactions }
     */
    RendererDTO renderTransactions(ArrayList transactionIDs, ArrayList businessDates, int renderType, Map extraElements)
            throws ParseException, RemoteException, ObjectNotFoundException, InvalidTypeException;

    /** gets a view dto containing all the information displayed on the Transaction Detail view page
     *  of each transaction in the collection and renders it in a specified format.
     *
     * @param transactionIDs a collection of transaction keys {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDates  a collection of the transaction date in yyyy-MM-dd format
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     * @param locale
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderTransactions(ArrayList transactionIDs, ArrayList businessDates, int renderType,
            Map extraElements, Locale locale) throws ParseException, RemoteException, ObjectNotFoundException,
            InvalidTypeException;

    /** gets a view dto containing all the information displayed on the transaction search results page, which
     *  is just a collection of TransactionListViewDTOs and renders it in a specified format.
     *
     * @param transactionListViewDTOs a collection of transaction list view dtos {@link oracle.retail.stores.webmodules.transaction.app.TransactionListViewDTO}
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderTransactionListViews(Collection transactionListViewDTOs, int renderType, Map extraElements,
            Locale locale) throws ParseException, RemoteException;

    /** gets a view dto containing all the information displayed on the transaction search results page, which
     *  is just a collection of TransactionListViewDTOs and renders it in a specified format.
     *
     * @param transactionListViewDTOs a collection of transaction list view dtos {@link oracle.retail.stores.webmodules.transaction.app.TransactionListViewDTO}
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     * @deprecated As of 13.1 use {@link renderTransactionListViews(Collection, int, Map, Locale }
     */
    RendererDTO renderTransactionListViews(Collection transactionListViewDTOs, int renderType, Map extraElements)
            throws ParseException, RemoteException;

    /** gets a view dto containing all the information displayed on the ejournal search results page, which
     *  is just a collection of EJournalListViewDTOs and renders it in a specified format.
     *
     * @param ejournalListViewDTOs a collection of ejournal list view dtos {@link oracle.retail.stores.webmodules.transaction.app.EJournalListViewDTO}
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     */
    RendererDTO renderEJournalListViews(Collection ejournalListViewDTOs, int renderType, Map extraElements,
            Locale locale) throws ParseException, RemoteException;

    /** gets a view dto containing all the information displayed on the ejournal search results page, which
     *  is just a collection of EJournalListViewDTOs and renders it in a specified format.
     *
     * @param ejournalListViewDTOs a collection of ejournal list view dtos {@link oracle.retail.stores.webmodules.transaction.app.EJournalListViewDTO}
     * @param renderType the type to render as (PDF, TEXT, etc)
     * @param extraElements  used to add elements not contained with the specific dto to the document
     * 						 key is the element name, value is the value of the element
     *
     * @return RendererDTO
     *
     * @see oracle.retail.stores.commerceservices.renderer.RendererIfc
     * @deprecated As of 13.1 use {@link renderEJournalListViews(Collection, int, Map, Locale }
     */
    RendererDTO renderEJournalListViews(Collection ejournalListViewDTOs, int renderType, Map extraElements)
            throws ParseException, RemoteException;

    /** gets a dto containing all the information required to display on the customer info screen
     *
     * @param transactionNumber the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @return CustomerDTOIfc or null if the given transaction does not have an associated customer
     *
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     */
    CustomerDTO getCustomerForTransaction(String transactionNumber, String businessDate, Locale locale)
            throws SearchException, ParseException, RemoteException;

    /** gets a dto containing all the information required to display on the customer info screen
     *
     * @param transactionNumber the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param businessDate the transaction date in yyyy-MM-dd format
     * @return CustomerDTOIfc or null if the given transaction does not have an associated customer
     *
     * @see oracle.retail.stores.commerceservices.transaction.TransactionKey
     * @deprecated As of 13.1 use {@link getCustomerForTransaction(String, String, Locale }
     */
    CustomerDTO getCustomerForTransaction(String transactionNumber, String businessDate) throws SearchException,
            ParseException, RemoteException;

    /**
     * Returns the list of tender issuers that application is configured to
     * accept.
     *
     * @return java.util.List
     * @deprecated use getAcceptedTenderIssuers().  Allowed doesn't make sense now that the card tender roles have been removed.
     */
    List<String> getAllowedTenderIssuers() throws RemoteException;

    /**
     * Returns the list of tender issuers that application is configured to
     * accept.
     *
     * @return java.util.List
     */
    List<String> getAcceptedTenderIssuers() throws RemoteException;

    /**
     * Returns the list of tender types that CentralOffice is configured to
     * accept.
     *
     * @return java.util.List of Strings - allowed tender types
     * @deprecated use getAcceptedTenderTypes().  Allowed doesn't make sense now that the card tender roles have been removed.
     */
    List<String> getAllowedTenderTypes() throws RemoteException;

    /**
     * Returns the list of tender types that CentralOffice is configured to
     * accept.
     *
     * @return java.util.List of Strings - allowed tender types
     */
    List<String> getAcceptedTenderTypes() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    TransactionKeyFormatterIfc getTransactionKeyFormatter() throws RemoteException;

    /**
     *  Retrieves a page of TransactionListViewDTOs based on the specified search criteria
     *
     * @param storeSelectionCriteria specifies how to narrow down the search base on store
     * @param searchCriteria specifies how and what to search by
     * @param startIndex is the first index to return
     * @param pageSize is the number of results to return
     * @param optionVal is all the Tender Types displayed
     * @return PagedList which contains the TransactionListViewDTOs
     */
    PagedList getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,
            int startIndex, int pageSize, String[] optionVal, Locale locale) throws SearchException, RemoteException;

    /** gets a byte array containing the PNG encoded image of the signature file
     *
     * @param transactionKey the transation store, ws, and sequence number {@link oracle.retail.stores.commerceservices.transaction.TransactionKey}
     * @param dateString the transaction date in yyyy-MM-dd format
     * @param the orderRecipient
     * @return byte[] containing a PNG images
     *
     */
    ImageDTO retrieveDelivarySignatureImage(String transactionKey, String dateString, int orderRecipient)
            throws ParseException, RemoteException;

}
