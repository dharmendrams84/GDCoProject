/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/commerceservices/transaction/src/oracle/retail/stores/commerceservices/transaction/TransactionServiceIfc.java /rgbustores_13.4x_generic_branch/3 2011/09/30 00:20:20 abondala Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    abondala  09/30/11 - add encryption service api to the parameter bean
 *                         which is common across all webapps and can be used
 *                         during perf testing and sample returns loading
 *    abondala  09/29/11 - provide a hook for the encryption service outside
 *                         the container for testing purposes
 *    acadar    10/06/10 - remove check for transaction type - any retail
 *                         transaction can have a customer associated with it
 *    jkoppolu  09/27/10 - BUG# 872, Optimize transaction search.
 *    abondala  09/07/10 - Do not throw SearchResultSizeExceededException which
 *                         is hard to handle with the webservice call. Instead
 *                         return a SOAP message SearchResultsExceedElement.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    ohorne    04/01/10 - enhanced createTillTransaction having
 *                         recordedWorkstationID
 *    abondala  01/03/10 - update header date
 *    blarsen   07/09/09 - XbranchMerge
 *                         blarsen_bug8665016-get-store-id-failing-ft-on-was
 *                         from rgbustores_13.1x_branch
 *    blarsen   07/08/09 - WebSphere's JDK does not handle enum equality
 *                         correctly. Replacing recently added enum's equals
 *                         call with new workaroundEquals().
 *    blarsen   07/09/09 - XbranchMerge
 *                         blarsen_bug8629786-transaction-lookup-by-card-number-fails
 *                         from rgbustores_13.1x_branch
 *    blarsen   07/07/09 - Changed boolean filterStoresByLoggedInUser flag to
 *                         enum TransactionServiceIfc.REQUEST_TYPE.
 *    blarsen   07/01/09 - XbranchMerge
 *                         blarsen_bug8629786-customer-transaction-history-retrieval-fails-on-was
 *                         from main
 *    blarsen   06/26/09 - Added several overloaded methods. The new methods
 *                         accept a flag to control filtering of transactions
 *                         by stores available to the user currently logged on.
 *                         When request is coming CO directly from POS, there
 *                         is no logged in user. So, this filter should not be
 *                         done.
 *
 * ===========================================================================
 * $Log:
 *   10   .v12x      1.8.1.0     1/13/2008 2:08:58 AM   Brett J. Larsen CR
 *        29867 - non-transactional e-journal entries not supported by CO
 *
 *        Added an overloaded retrieveEJournalEntry method that accepts an
 *        EjournalKey (new).
 *
 *        An EJournalKey is required for non-transactional e-journal etries.
 *
 *   9    360Commerce 1.8         5/9/2007 6:40:12 AM     Sharath Swamygowda
 *        modified to include inclusiveTaxParameter while calling
 *        createRetailTransaction()
 *   8    360Commerce 1.7         11/9/2006 7:16:43 PM   Jack G. Swan
 *        C:\dev\projects\360Commerce\build
 *   7    360Commerce 1.6         8/10/2006 11:23:05 AM  Sandy Gu        Merge
 *        fix for CR10474 from v7x into trunk
 *   6    360Commerce 1.5         4/27/2006 7:25:03 PM   Brett J. Larsen CR
 *        17307 - remove inventory functionality - stage 2
 *   5    360Commerce 1.4         1/22/2006 11:47:48 AM  Ron W. Haight
 *        removed references to com.ibm.math.BigDecimal
 *   4    360Commerce 1.3         12/28/2005 2:32:57 PM  Sandy Gu        Merge
 *        inventory code from NEP63
 *   3    360Commerce 1.2         3/31/2005 6:36:41 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:26:25 AM  Robert Pearse
 *   1    360Commerce 1.0         2/14/2005 2:07:02 PM   Robert Pearse
 *  $
 *  Revision 1.21.2.2  2004/12/03 16:40:47  sgu
 *  @scr 3038 added support to check for suspended
 *  transactions at end of day
 *
 *  Revision 1.21.2.1  2004/11/23 01:22:53  rdunsmore
 *  fixing transaction search
 *
 *  Revision 1.21  2004/10/22 20:42:18  sgu
 *  @scr 3031. Added check for unreconciled tills during register close.
 *  cleaned up exceptions in open/close register api.
 *  fixed report export regression
 *
 *  Revision 1.20  2004/10/05 14:49:59  slloyd
 *  Remove formatting from TransactionKey class, create TransactionKeyFormatterIfc and TransactionKeyFormatter for formatting/parsing of transactionnumber into key class.
 *
 *  Revision 1.19  2004/10/04 22:38:04  slloyd
 *  Remove formatting from TransactionKey class, create TransactionKeyFormatterIfc and TransactionKeyFormatter for formatting/parsing of transactionnumber into key class.
 *
 *  Revision 1.18  2004/10/01 20:47:19  qnguyen
 *  1) deployment descriptors fixed for websphere for CO.
 *  2) take out the "EXCHANGE" transaction type as the support search transaction type for
 *     POSLog utility as well as TransactionHeader utility because TransactionServiceBean
 *     does not retrieve transaction based upon this type currently.
 *  2) add to transactionServiceBean, getTransactionHeader() method.
 *     This code is not yet tested.  Need FT and webservices test.
 *     But it does deploy on websphere.
 *
 *  Revision 1.17  2004/09/05 20:08:40  aarvesen
 *  added inventory transactions
 *
 *  Revision 1.16  2004/07/29 18:35:28  rdunsmore
 *  fix
 *
 *  Revision 1.15  2004/07/26 02:41:09  rdunsmore
 *  store close store history update
 *
 *  Revision 1.14  2004/07/12 16:14:33  tcronin
 *  removed unused imports
 *
 *  Revision 1.13  2004/06/23 18:40:45  sgu
 *  added calls to create till transactions
 *
 *  Revision 1.12  2004/06/16 21:52:13  ehoq
 *  1. Added WorkstationTenderHistory bean
 *  2. Added logic to tillOpen in the svcs layer
 *
 *  Revision 1.11  2004/06/16 13:36:34  djennings
 *  adding some exception handling, some new supported transactions, and suspension reason code
 *
 *  Revision 1.10  2004/06/15 18:58:11  rdunsmore
 *  updates
 *
 *  Revision 1.9  2004/06/15 12:16:52  djennings
 *  add searchResultMaxSizeExceeded exception
 *
 *  Revision 1.8  2004/06/09 22:01:20  slloyd
 *  Post Processor Functtionality.
 *
 *  Revision 1.7  2004/06/08 22:00:52  rdunsmore
 *  register close
 *
 *  Revision 1.6  2004/05/25 20:16:44  rdunsmore
 *  open workstation updates
 *
 *  Revision 1.5  2004/05/21 15:18:52  sgu
 *  integrated the bank deposit report.
 *  added transaction creation while saving bank deposit.
 *
 *  Revision 1.4  2004/05/19 20:19:18  slloyd
 *  Modify Transaction Entities and access strategy.
 *
 *  Revision 1.3  2004/05/19 08:59:22  slloyd
 *  Modify Transaction Entities and access strategy.
 *
 *  Revision 1.2  2004/05/15 17:30:16  djennings
 *  Adding initial QueryTransactionMessage support for Returns Management.@scr
 *
 *  Revision 1.1  2004/03/17 00:27:13  jlee
 *  @scr in theory move employee and transaction out entirely
 *
 *  Revision 1.7  2004/02/04 21:49:35  mspeich
 *  Organized imports.
 *
 *  Revision 1.6  2004/01/19 21:54:09  rdunsmore
 *  changing eployeeID dependency to loginID
 *
 *  Revision 1.5  2004/01/12 22:43:59  slloyd
 *  Import fixes.
 *
 *  Revision 1.4  2003/12/18 01:00:20  jlee
 *  Fixed import transaction functional test.  Iit now test both cases with workflow or not and actually verfies the transaction was inserted
 *
 *  Revision 1.3  2003/12/11 04:37:07  slloyd
 *  EJournal Search updates from PVCS.
 *
 *  Revision 1.2  2003/12/02 19:00:36  build
 *  conversion of ctrl-m's
 *
 *  Revision 1.1.1.1  2003/12/01 04:37:11  build
 *  Import from PVCS ${date}
 *
 *
 *    Rev 1.14   Dec 05 2003 13:24:12   RDunsmore
 * added methods for gap search by ejournal
 *
 *    Rev 1.13   Nov 12 2003 23:31:22   slloyd
 * Parameterize Signature for import and display.
 *
 *    Rev 1.12   Nov 11 2003 16:26:24   jlee
 * security finally works in jboss (even if it is rather hacky)
 *
 *    Rev 1.11   Nov 07 2003 16:05:52   slloyd
 * sig cap change to byte[] and blob
 *
 * ===========================================================================
 */
package oracle.retail.stores.commerceservices.transaction;

import gdyn.retail.stores.webmodules.ejournal.GDYNLoyalityEJournalViewBean;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;

import oracle.retail.stores.commerceservices.common.InvalidTypeException;
import oracle.retail.stores.commerceservices.common.SearchResultSizeExceededException;
import oracle.retail.stores.commerceservices.ejournal.EJournalDTO;
import oracle.retail.stores.commerceservices.ejournal.EJournalKey;
import oracle.retail.stores.commerceservices.store.WorkstationIfc;
import oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria;
import oracle.retail.stores.keystoreencryption.EncryptionServiceException;


import java.math.BigDecimal;
import java.util.Locale;

/** TransactionServiceIfc defines the main entry point for a number of Transaction related services.
 *  It includes search, import, export, ejournal, signature capture functions.
 *
 */
public interface TransactionServiceIfc
{
    /*
     * Requests coming from POS (not from, say, transaction tracker) do not have
     * reliable user credentials (WebSphere's user is always UNAUTHENTICATED).
     * Normally, the returned transactions are filtered by the stores available
     * to the currently logged in user. However, for requests from POS, special
     * handling of store criteria is required. For POS return requests, stores
     * should not be filtered by the currently logged in user. If no store
     * criteria is specified in the request from POS, transactions from all
     * stores will be returned.
     */
    enum REQUEST_TYPE
    {
        USER_REQUEST,
        POS_RETURN_REQUEST;

        /*
         * The JDK used by WebSphere for release 13.1.1 contains a bug where the
         * enum's built-in equals method does not return correct results. Until
         * WebSphere's JDK is fixed, use this equals
         * @param other another request type enum value
         * @return true of 2 request types are equal.
         */
        public boolean workaroundEquals(REQUEST_TYPE other)
        {
            if (other == null)
                return false;

            return this.toString().equals(other.toString());
        }
    };

    /**
     * Cancel a suspended transaction
     * @param key transaction key
     * @throws RemoteException
     */
    void cancelSuspendedTransaction(TransactionKey key) throws ObjectNotFoundException, RemoteException;

    TransactionKeyFormatterIfc getTransactionKeyFormatter() throws RemoteException;
    EJournalKeyFormatterIfc getEJournalKeyFormatter() throws RemoteException;

    /**
     * Create a RetailTransaction
     * @param storeID
     * @param workstationID
     * @param sequenceNumber
     * @param businessDay
     * @param type
     * @param operatorId
     * @param transactionEndDateTime
     * @param customerId
     * @param salesTotal
     * @param discountTotal
     * @param taxTotal
     * @param netTotal
     * @param inclusiveTax TODO
     * @param tenderTotal
     * @param shippingType
     * @param shippingCharge
     * @return
     * @throws TransactionCreationException
     */
    TransactionKey createRetailTransaction(String storeID,
                                           String workstationID,
                                           int sequenceNumber,
                                           String businessDay,
                                           TransactionType type,
                                           String operatorId,
                                           Date transactionEndDateTime,
                                           String customerId,
                                           BigDecimal salesTotal,
                                           BigDecimal discountTotal,
                                           BigDecimal taxTotal,
                                           BigDecimal netTotal,
                                           BigDecimal inclusiveTax,
                                           BigDecimal tenderTotal,
                                           String shippingType, BigDecimal shippingCharge) throws RemoteException, TransactionCreationException;

    /**
     * Create a Store transaction
     * @param storeID
     * @param workstationID
     * @param businessDay
     * @param operatorID
     * @param openStore
     * @return
     * @throws RemoteException
     * @throws TransactionCreationException
     * @throws NextTransactionSeqNumNotFoundException
     */
    TransactionKey createStoreOpenCloseTransaction(String storeID,
                                                   String workstationID, Date businessDay, String operatorID,
                                                   TransactionType openStore) throws RemoteException, TransactionCreationException,
            NextTransactionSeqNumNotFoundException;

    /**
     * Creates a Register Transaction. This is basically a control transaction to record the financial
     * event of a register being opened or closed.
     *
     * @param storeID
     * @param workstationID
     * @param businessDay
     * @param operatorID
     * @param type
     * @return
     * @throws TransactionCreationException
     * @throws NextTransactionSeqNumNotFoundException
     */
    TransactionKey createRegisterTransaction(String storeID,
                                             String workstationID, Date businessDay, String operatorID,
                                             TransactionType type, WorkstationIfc workstation,
                                             boolean countFloatAtOpen, boolean countFloatAtClose) throws RemoteException, TransactionCreationException,
            NextTransactionSeqNumNotFoundException;

    /**
     * Creates a Till Open/Close Transaction. This is basically a control transaction to record the financial
     * event of a till being opened or closed.
     *
     * @param storeID
     * @param workstationID
     * @param businessDay
     * @param operatorID
     * @param type
     * @return
     * @throws TransactionCreationException
     * @throws NextTransactionSeqNumNotFoundException
     */
    public TransactionKey createTillTransaction(String storeID,
            String workstationID, String recordedWorkstationID, String tenderRepositoryID, String operatorID,
            Date businessDay, TransactionType type, int tillStatusCode)
        throws RemoteException, TransactionCreationException, NextTransactionSeqNumNotFoundException;

    /**
     * @deprecated As of 13.3 use {@link #createTillTransaction(String, String, String, String, String, Date, TransactionType, int)
     */
    public TransactionKey createTillTransaction(String storeID,
            String workstationID, String tenderRepositoryID, String operatorID,
            Date businessDay, TransactionType type, int tillStatusCode)
        throws RemoteException, TransactionCreationException, NextTransactionSeqNumNotFoundException;


    /**
     * Creates bank deposit transaction
     * @param storeID
     * @param workstationID
     * @param businessDay
     * @param operatorID
     * @param type
     * @return
     * @throws RemoteException
     * @throws NextTransactionSeqNumNotFoundException
     */
    TransactionKey createBankDepositTransaction(String storeID,
                                                String workstationID, Date businessDay, String operatorID,
                                                TransactionType type) throws RemoteException,
            TransactionCreationException,
            NextTransactionSeqNumNotFoundException;

    /** Retrieves a transaction's type.
     *
     * @param transactionKey
     * @return TransactionType
     */
    TransactionType getType(TransactionKey transactionKey)
            throws RemoteException, InvalidTypeException,
            ObjectNotFoundException;

    /** Retrieves a transaction data transfer object given a TransactionKey as input.
     *
     * @param transactionKey
     * @return RetailTransactionDTO
     * @deprecated As of 13.1 use {@link retrieveTransaction(key, locale)}
     */
    TransactionDTO retrieveTransaction(TransactionKey transactionKey)
            throws RemoteException, ObjectNotFoundException,
            InvalidTypeException;

    /**
     * Retrieves a transaction data transfer object given a TransactionKey as input.
     * @param transactionKey
     * @param locale
     * @return RetailTransactionDTO
     * @throws RemoteException
     * @throws ObjectNotFoundException
     * @throws InvalidTypeException
     */
    TransactionDTO retrieveTransaction(TransactionKey transactionKey, Locale locale)
    throws RemoteException, ObjectNotFoundException,
    InvalidTypeException;


    /** Retrieves a set of ejournal information given ejournal search criteria as input.
     *
     * @param storeSelectionCriteria
     * @param ejournalCriteria
     * @param startIndex
     * @return returnLimit
     */
    EJournalSearchResultDTO getEJournals(
            StoreSelectionCriteria storeSelectionCriteria,
            EJournalCriteria ejournalCriteria, int startIndex, int returnLimit)
            throws RemoteException, SearchResultSizeExceededException;

    /**
     * Returns a CO-RM-TransactionsHeader.xsd type of xml Note that the list of
     * transactions returned are filtered to include only those transctions from
     * stores accessable by the logged in user
     *
     * @param searchCriteria CO-RM-QueryTransactionRequest.xsd type of xml
     *            string
     * @return Returns a CO-RM-TransactionsHeader.xsd type of xml
     * @throws TransactionRetrievalException
     * @throws SearchResultSizeExceededException
     */
    public String getTransactionHeader(String searchCriteria) throws TransactionRetrievalException, RemoteException;

     /**
     * Returns a CO-RM-TransactionsHeader.xsd type of xml
     * @param searchCriteria CO-RM-QueryTransactionRequest.xsd type of xml string
     * @param requestType - is the request from a user or from POS returns
     * @return Returns a CO-RM-TransactionsHeader.xsd type of xml
     * @throws TransactionRetrievalException
     * @throws SearchResultSizeExceededException
     */
    public String getTransactionHeader(String searchCriteria,
            TransactionServiceIfc.REQUEST_TYPE requestType) throws TransactionRetrievalException, RemoteException;

    /**
     * Returns a list of POSLog transactions that meet the specified search
     * criteria
     *
     * @param searchCriteriaXml
     * @return a poslog xml document
     * @throws SearchResultSizeExceededException
     * @throws TransactionRetrievalException
     */
    public String getTransactionPOSLog(String searchCriteriaXml) throws RemoteException, SearchResultSizeExceededException, TransactionRetrievalException;

    /** Retrieves a set of transaction information given search criteria as input.
     * @deprecated Please use overloaded method getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,int startIndex, int pageSize, String [] optionVal) defined in this class
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @return returnLimit
     */
    TransactionSearchResultDTO getTransactions(
            StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int returnLimit)
            throws RemoteException, SearchResultSizeExceededException;

    /** Retrieves a set of transaction information given search criteria as input.
     * @deprecated Please use overloaded method getTransactions(StoreSelectionCriteria storeSelectionCriteria, SearchCriteria searchCriteria,int startIndex, int pageSize, String [] optionVal) defined in this class
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param requestType - is the request from a user or from POS returns
     * @return returnLimit
     */
    TransactionSearchResultDTO getTransactions(
            StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int returnLimit,
            REQUEST_TYPE requestType)
            throws RemoteException, SearchResultSizeExceededException;

    /**
     * imports transactions to the database
     *
     */
    int importTransactions(String xml) throws RemoteException;


    /** imports transactions to the database
     *
     */
    //int importTransactions(Document document) throws RemoteException;


    void importEJournal(String xml) throws RemoteException;

    /**
     * retrieves an ejournal dto for the specified transaction id for EJournals
     * generated for a transaction, a TransactionKey is used for EJournals not
     * generated for transactions (non-transactional) such as system
     * online/offline entries, an EJournalKey is required non-transactional
     * EJournal entries do not have a sequence number and the transaction begin
     * date (in the pk) is used
     */
    EJournalDTO retrieveEJournalEntry(TransactionKey key) throws RemoteException;
    EJournalDTO retrieveEJournalEntry(EJournalKey key) throws RemoteException;

    /**
     * Imports a signature into the database, creating all missing table entries
     * for transaction for searching and display.
     *
     * @param xml
     * @throws RemoteException
     */
    void importSignatureCapture(String xml) throws RemoteException;

    void removeTransaction(TransactionKey transactionKey) throws RemoteException, RemoveException;

    /**
     * @param storeID
     * @param businessDay
     * @return
     * @throws RemoteException
     * @deprecated As of 13.1
     */
    Collection findTransactionsByStoreAndBusinessDay(String storeID, String businessDay) throws RemoteException;

    /**
     * @param storeID
     * @param workstationID
     * @param businessDay
     * @return
     * @throws RemoteException
     * @deprecated As of 13.1
     */
    Collection findTransactionsByWorkstationAndBusinessDay(String storeID, String workstationID, String businessDay) throws RemoteException;


    Collection getTransactionsForPostProcessing() throws RemoteException, FinderException;

    void changePostProcessingStatus(TransactionKey key, int status) throws RemoteException, FinderException;

    /**
     * Retrieves a set of transaction information given search criteria as
     * input. Note that the list of transactions returned are filtered to
     * include only those transctions from stores accessable by the logged in
     * user
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param optionVal Tender Types Values
     * @return returnLimit
     */
   TransactionSearchResultDTO getTransactions(
           StoreSelectionCriteria storeSelectionCriteria,
           SearchCriteria searchCriteria, int startIndex, int returnLimit, String [] optionVal)
           throws RemoteException, SearchResultSizeExceededException;

    /** Retrieves a set of transaction information given search criteria as input.
    * @param storeSelectionCriteria
    * @param searchCriteria
    * @param startIndex
    * @param optionVal Tender Types Values
    * @param requestType - is the request from a user or from POS returns
    * @return returnLimit
    */
   TransactionSearchResultDTO getTransactions(
           StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int returnLimit, String[] optionVal,
           TransactionServiceIfc.REQUEST_TYPE requestType)
           throws RemoteException, SearchResultSizeExceededException;

    /**
     * This method retrieves transactions based on the values in the XML Search Criteria
     * @param searchCriteriaString
     * @return Transactions in DataReplication XML format.
     * @throws TransactionRetrievalException
     */
    public String getTransactionReplication(String searchCriteriaString)
    throws RemoteException, TransactionRetrievalException;

    /**
     * This method determines if a transaction has already been voided
     * @param searchCriteriaString
     * @return Boolean indicating if transaction has already been voided
     * @throws TransactionRetrievalException
     */
    public Boolean isVoidedTransaction(String searchCriteriaString)
    throws RemoteException, TransactionRetrievalException;


    public String getOrderReplication(String xmlRequest)
        throws RemoteException, TransactionRetrievalException;

    public String getLayawayReplication(String xmlRequest)
        throws RemoteException, TransactionRetrievalException;

    public String getAlertListReplication(String xmlRequest) throws RemoteException;

    public String getEMessageReplication(String xmlRequest) throws RemoteException;

    public String getTransactionFirstItemDescription(String xmlRequest) throws RemoteException;

    public String getRetailTransactionCustomerId(TransactionKey key) throws RemoteException;
    
    /**
  	 * This method is created by Dharmendra on 28/11/2016 to retrieve loyality details from CT_TRN_LYLT table based on the below parameters
  	 It queries the CT_TRN_LYLT table 
  	 * @param filteredTransactionNumber
  	 * @param transactionKey
  	 * @param businessDate
  	 * @return
  	 * @throws ParseException
  	 * @throws RemoteException
  	 */
    public GDYNLoyalityEJournalViewBean getLoyalityDtls(String filteredTransactionNumber, String loyalityId,String businessDate)throws RemoteException;
    
    /**
   	 *This method is created by Dharmendra on 28/11/2016 to update loyality details in table CT_TRN_LYLT table based on the below parameters
   	 *It updated the loyality Id and loyality Email in CT_TRN_LYLT table 
   	 * @param seqNumber
   	 * @param loyalityId
   	 * @param loyalityEmail
   	 * @param workstationId
   	 * @param storeNumber
   	 * @throws RemoteException
   	 */
   public boolean updateLoyalityDtls(String seqNumber,String loyalityId,String loyalityEmail,String workstationId,String storeNumber)throws RemoteException;
    
    
    public void setLoyaltyDetails(TransactionDTO dto) throws RemoteException;

}
