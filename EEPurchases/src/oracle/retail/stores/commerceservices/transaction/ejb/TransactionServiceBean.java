/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/commerceservices/transaction/src/oracle/retail/stores/commerceservices/transaction/ejb/TransactionServiceBean.java /rgbustores_13.4x_generic_branch/20 2011/11/29 17:14:20 sgu Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    sgu       11/29/11 - fix quantity decimal places
 *    vtemker   10/28/11 - Reverting fix for defect 11074373 (Suspended
 *                         Canceled status not updated in CO)
 *    vtemker   10/11/11 - Fixed bug # 200 (Suspended canceled transactions
 *                         shown as Suspended in CO)
 *    vtemker   10/07/11 - Fixed issue with BO Flash Sales report (Bug 653)
 *    abondala  09/30/11 - add encryption service api to the parameter bean
 *                         which is common across all webapps and can be used
 *                         during perf testing and sample returns loading
 *    abondala  09/29/11 - provide a hook for the encryption service outside
 *                         the container for testing purposes
 *    abondala  08/19/11 - configurable transaction id.
 *    ohorne    08/10/11 - TrnsTrckr fix - search by TenderID
 *    mchellap  08/09/11 - Moved replication configuration files to templates
 *                         directory
 *    cgreene   08/08/11 - Make formatting of export configurable and set
 *                         default to false
 *    asinton   07/28/11 - removed GF_ from ID_NCRPT_ACNT_GF_CRD and
 *                         ID_MSK_ACNT_GF_CRD, Removed column ID_ACNT_TND.
 *    ohorne    07/25/11 - replaced TR_LTM_CHK_TND.NR_MICR with
 *                         TR_LTM_CHK_TND.NR_MSK_MICR
 *    cgreene   07/19/11 - store layaway and order ids in separate column from
 *                         house account number
 *    blarsen   07/15/11 - Fix misspelled word: retrival
 *    cgreene   07/15/11 - change where sanity check for tender id is placed
 *    cgreene   07/15/11 - removed encrypted expiration date from datamodel
 *    cgreene   06/30/11 - removed encrytped columns from credit table and
 *                         added token column.
 *    ohorne    06/29/11 - search by upc fix
 *    abondala  06/07/11 - XbranchMerge
 *                         abondala_send_tx_to_co_through_webservice from main
 *    abondala  06/01/11 - organize imports
 *    abondala  05/27/11 - XbranchMerge abondala_cache_jaxb_context from main
 *    abondala  05/26/11 - do not create multiple instances of JAXB context.
 *                         This will impact the performance. Cache the Jaxb
 *                         context and use that for further calls.
 *    ohorne    03/01/11 - ItemNumber can be ItemID or PosItemID
 *    sgu       02/16/11 - add upc to tracker
 *    sgu       02/15/11 - remove unused reference
 *    sgu       02/15/11 - fix identation
 *    sgu       02/15/11 - check in all
 *    cgreene   01/06/11 - corrected maximum size thrown when error
 *    cgreene   12/23/10 - XbranchMerge cgreene_transserviceft from
 *                         rgbustores_13.3x_generic_branch
 *    cgreene   12/23/10 - corrected store search criteria clause
 *    cgreene   12/22/10 - XbranchMerge cgreene_coseltests_txnservicebean from
 *                         main
 *    cgreene   12/22/10 - fix discrepency in using StoreSelectionCriteria in
 *                         cluase
 *    cgreene   12/21/10 - XbranchMerge cgreene_bug-10416485 from
 *                         rgbustores_13.3x_generic_branch
 *    cgreene   12/21/10 - removed need for flushing card data since entire
 *                         card number is not transmitted
 *    asinton   12/20/10 - XbranchMerge asinton_bug-10407292 from
 *                         rgbustores_13.3x_generic_branch
 *    asinton   12/17/10 - deprecated hashed account ID.
 *    cgreene   12/15/10 - remove table TEMP_STORE_JOIN causing contention in
 *                         transaction queries
 *    cgreene   12/02/10 - made JAXB context an instance member
 *    cgreene   11/24/10 - remove keystore connector from bo and co and just
 *                         use simkeystore for hashing
 *    acadar    10/15/10 - unit test fixes
 *    acadar    10/06/10 - remove check for transaction type - any retail
 *                         transaction can have a customer associated with it
 *    jkoppolu  09/27/10 - BUG# 872, Optimize transaction search.
 *    abondala  09/27/10 - Now both the system date and business date are
 *                         displyed where ever required. TransactionTracker can
 *                         search transactions by business date.
 *    cgreene   09/10/10 - updated tt search by item to treat UPC search as
 *                         POSItemID
 *    abondala  09/07/10 - Do not throw SearchResultSizeExceededException which
 *                         is hard to handle with the webservice call. Instead
 *                         return a SOAP message SearchResultsExceedElement.
 *    npoola    08/27/10 - db2 is throwing exception while calling next method
 *                        on resultset if it is exhausted, removed 2nd while
 *                         condition
 *    mchellap  08/24/10 - Billpay datamodel changes
 *    cgreene   08/19/10 - tweak SQL to improve performance
 *    jkoppolu  07/30/10 - Fix for Bug#9926976
 *    mchellap  07/22/10 - Added support for billpayment transactions
 *    cgreene   06/17/10 - upgrade to jaxb 2.0
 *    abondala  06/15/10 - rename AI_ORD_EXT to ID_ORD_EXT_NMB
 *    abondala  06/10/10 - renamed external order number column
 *    abondala  06/07/10 - rename externalOrderId to externalOrderNumber as we
 *                         are using the externalOrderNumber on the UI for
 *                         display
 *    jswan     06/01/10 - Modified to support transaction retrieval
 *                         performance and data requirements improvements.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  05/26/10 - Siebel - CO integration
 *    ohorne    04/01/10 - enhanced createTillTransaction() having
 *                         recordedWorkstationID
 *    acadar    02/15/10 - forward port for spurkaya_8581588_303
 *    mchellap  02/11/10 - BUG#9148779 Fixed kit transaction tracker report
 *    vapartha  01/22/10 - Changed code to set the TaxMode only if the Item is
 *                         taxable else default to the Non Taxable Category.
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
 *                         enum TransactionServiceIfc.REQUEST_TYPE. Reworked
 *                         logic in getStoreIDs to return default hierarchy's
 *                         stores when a request comes from POS returns and the
 *                         request does not include store search criteria.
 *    acadar    07/02/09 - backporting fixes for 8595656,8645690 and 8650572
 *    acadar    07/02/09 - XbranchMerge acadar_bug-8595656 from main
 *    acadar    07/02/09 - refresh
 *    acadar    06/30/09 - save till id in tr_trn during till operations;
 *                         change Agencies type from STRING to LIST
 *    blarsen   06/29/09 - In some cases store ids should *not* be filtered by
 *                         the currently logged in user. For instance, when a
 *                         request is coming from POS to get a customers
 *                         transaction history. In this case, on WAS, no user
 *                         is logged in. The login is UNAUTHENTICATED. An
 *                         exception was being thrown when the users store
 *                         hierarchy was being retrieved for user
 *                         UNAUTHENTICATED. A new flag is now passed down into
 *                         the getStoreIDs methods to bypass the user store
 *                         filter to avoid the exception.
 *    blarsen   06/26/09 - Changed getTransactions() to check a flag before
 *                         filtering the transaction list by stores accessible
 *                         to the current user. When POS requests a transaction
 *                         from CO, the logged in user no longer makes sense.
 *                         On RED stack the user is a POS user. On BLUE stack
 *                         the user is UNAUTHORIZED. On BLUE stack performing
 *                         this check throws an exception when certain
 *                         parameters are set to disable using a default store
 *                         hierarchy. Added several overloaded methods to
 *                         accept a new flag: filterStoresByLoggedInUser
 *    tzgarba   06/12/09 - Updated register and till transactions to use the
 *                         backoffice workstation ID.
 *    ohorne    05/22/09 - unquoted CO_MDFR_RTL_PRC.CD_TY_PRDV in where clause
 *    npoola    04/28/09 - refreshed to tip
 *    npoola    04/28/09 - inserted quotes for the GET_ORDER_ID_SQL for IN
 *                         clause
 *    npoola    04/18/09 - fixed the signature transaction tracker displaying
 *                         signature and data replication in to OR_RCPNT_DTL
 *                         table
 *    sgu       04/09/09 - pass in TransactionServiceBean to
 *                         TransactionHeaderUtility
 *    sgu       04/09/09 - localize first item description and store location
 *                         of a transaction header
 *    sgu       04/08/09 - localize store location name when retrieving
 *                         transaction header centrally.
 *    mahising  03/06/09 - fixed issue to display register accountability in
 *                         open/close register transaction detail page.
 *    mahising  03/03/09 - Fixed signature capture view for pickup-delivery
 *                         order issue
 *    abondala  02/27/09 - updated
 *    abondala  02/27/09 - LayawayLocation and OrderLocation parameters are
 *                         changed to ReasonCodes.
 *    ranojha   02/11/09 - Fixed transaction timestamp from a forward port
 *                         defect
 *    npoola    02/05/09 - fixed gift card tax display issue
 *    mkochumm  02/02/09 - add country
 *    acadar    01/29/09 - display reason code text when applicable
 *    acadar    01/28/09 - forward port for bug: 7417468- close database
 *                         connections
 *    tzgarba   12/11/08 - Fixed FindBugs issues.
 *    vikini    12/02/08 - Checking in merged code
 *    vikini    12/02/08 - Changes done for Converting variable type
 *                         merchandiseHierarchyGroupId to String
 *    vikini    11/17/08 - Forward Port: Changing Variable type of
 *                         getMerchandiseHierarchyGroupId from int to String
 *    aphulamb  11/27/08 - checking files after merging code for receipt
 *                         printing by Amrish
 *    aphulamb  11/25/08 - Merge
 *    aphulamb  11/24/08 - Checking files after code review by amrish
 *    aphulamb  11/13/08 - Check in all the files for Pickup Delivery Order
 *                         functionality
 *    deepshar  11/17/08 - forward port 7041244
 *    deghosh   11/14/08 - Forward port bug# 7292467
 *    sgu       10/30/08 - refactor layaway and transaction summary object to
 *                         take localized text
 *
 * ===========================================================================
 * $Log:
 *   51   360Commerce 1.50        4/11/2008 4:25:04 PM   Edward B. Thorne
 *        Header & Javadoc updates.  Removed unused code.  Reviewed by Tony Z.
 *   50   360Commerce 1.49        4/4/2008 3:44:07 AM    Manas Sahu
 *        Removed the padding of cashier number.
 *   49   360Commerce 1.48        4/3/2008 12:07:12 PM   Christian Greene
 *        Removed "throws IOException" from base64decode as unneeded
 *   48   360Commerce 1.47        3/31/2008 9:35:13 AM   Mathews Kochummen
 *        forward port from v12x to trunk. reviewed by anil
 *   47   360Commerce 1.46        3/27/2008 2:17:17 PM   Anil Bondalapati fixed
 *         the transaction search by gift card number.
 *   46   360Commerce 1.45        3/21/2008 3:44:25 PM   Christian Greene 30674
 *         merge from v12x, set businessdate as string yyyyMMdd
 *   45   360Commerce 1.44        3/21/2008 1:49:13 PM   Christian Greene 29687
 *         Merge from v12x branch. Prevent duplicate EJ's from from being sent
 *         to store server
 *   44   360Commerce 1.43        3/18/2008 8:38:52 AM   Mathews Kochummen
 *        forward port v12x fix to trunk. reviewed by michael
 *   43   360Commerce 1.42        3/7/2008 8:48:54 AM    Manas Sahu      Set
 *        the renetry mode in DTO in retrieveTransaction method
 *   42   360Commerce 1.41        2/21/2008 4:34:31 PM   Anil Bondalapati fixed
 *         the Transaction Tracker issues.
 *   41   360Commerce 1.40        2/7/2008 3:25:10 PM    Alan N. Sinton  CR
 *        30132: updated database (tr_ltm_pyan) to save encrypted, hashed, and
 *         masked house account card values.  Code was reviewed by Anil
 *        Bondalapati.
 *   40   360Commerce 1.39        1/29/2008 1:04:07 PM   Tony Zgarba     CR
 *        29825.  Refactored the store hierarchy filtering to move the check
 *        into getStoreIDs and make it intrinsic to the lookup.  Removed the
 *        action and search criteria updates.  Fixed the 5 unit tests that
 *        were failing due to the addition of the filter.  Reviewed by Michael
 *         Barnett.
 *   39   360Commerce 1.38        1/28/2008 4:19:17 PM   Sandy Gu        Set
 *        base and alternate currencies for POSLog import.
 *   38   360Commerce 1.37        12/27/2007 1:40:38 PM  Tim Solley      Fixed
 *        the transaction tracker to only allow access to transactions in a
 *        user's store hierarchy
 *   37   360Commerce 1.36        11/30/2007 4:58:46 PM  Anil Bondalapati
 *        deprecated import signature capture.
 *   36   360Commerce 1.35        11/19/2007 5:31:55 PM  Anil Bondalapati
 *        updated related to CTR.
 *   35   360Commerce 1.34        11/12/2007 6:46:37 PM  Anil Bondalapati
 *        updated related to PABP.
 *   34   360Commerce 1.33        11/12/2007 2:14:22 PM  Tony Zgarba
 *        Deprecated all existing encryption APIs and migrated the code to the
 *         new encryption API.
 *   33   360Commerce 1.32        8/8/2007 5:46:09 AM    Manas Sahu
 *        ItemSequenceNumber is added to the query so that unique row will be
 *        returned from the query result.
 *   32   360Commerce 1.31        7/25/2007 4:29:19 PM   Tony Zgarba     CR
 *        19962.  Updated TransactionServiceBean to check for a null product
 *        item ID since unknown items do not have them.  I believe this issue
 *        came up because the product item ID column on the item table has
 *        recently been made nullable.  As a result, the if-check in
 *        TransactionServiceBean needed to be updated to check for null before
 *         checking for blank string.
 *   31   360Commerce 1.30        7/17/2007 6:26:06 AM   Manikandan Chellapan
 *        CR27695 Added catch block to catch SearchResultExceededException
 *   30   360Commerce 1.29        6/7/2007 11:22:35 AM   Owen D. Horne   CR
 *        26172 - Rolled out fix for CR 7429 from v7.2.2 merge because
 *        fractional units are supported
 *   29   360Commerce 1.28        5/16/2007 5:54:28 PM   Sandy Gu        Fixed
 *        transaction tracker break
 *   28   360Commerce 1.27        5/10/2007 9:50:59 AM   Sandy Gu        Fixed
 *        functional test failures
 *   27   360Commerce 1.26        5/9/2007 6:51:13 AM     Sharath Swamygowda
 *        Modified to call new constructor of PLULineItemDTO
 *   26   360Commerce 1.25        5/8/2007 6:09:25 AM     Sharath Swamygowda
 *        Modified getItemsForTransaction() &
 *        getShippingRecordsForTransaction()
 *   25   360Commerce 1.24        4/18/2007 2:32:36 PM   Owen D. Horne   CR
 *        26172 - v7.2.2 merge to trunk
 *        *   13   .v7x       1.7.1.4     10/6/2006 10:37:28 AM  Rajul Goyal
 *           CR
 *        *        17267: The file has been modified to add two more methods.
 *        *        Modification has been made at another method. New methods
 *        are public
 *        *         String getSellingTaxMode(String storeID, String
 *        workstationID,
 *        *        String businessDate, int sequenceNumber) and private String
 *        *        getTaxCategory(int taxMode).    private List
 *        *        getItemsForTransaction(TransactionKey key) throws
 *        FinderException
 *        *        method has been modified.
 *   24   360Commerce 1.23        4/18/2007 9:43:30 AM   Owen D. Horne   CR
 *        26172 - v7.2.2 merge to trunk
 *        *   12   .v7x       1.7.1.3     9/1/2006 2:13:50 AM    Dinesh Gautam
 *           CR
 *        *        7429: Modified the item quantity as per the item�s Unit of
 *        Measure.
 *   23   360Commerce 1.22        4/13/2007 7:39:34 AM   Peter J. Fierro
 *        Merging 20412 from 7.2.2 to trunk:
 *
 *        11   .v7x      1.7.1.2     8/11/2006 1:21:34 PM   Deepanshu       CR
 *        20412: Where Clause in query not to be included if TenderType
 *        selected is 'All'
 *
 *   22   360Commerce 1.21        3/29/2007 2:49:31 PM   Michael Boyd    CR
 *        26172 - v8x merge to trunk
 *
 *
 *        22   .v8x      1.18.1.2    3/2/2007 5:50:50 PM    Brett J. Larsen CR
 *         25703
 *        - transaction tracker cannot find transactions via sig capture
 *        search page
 *
 *        query builder for sig cap criteria not properly handling case where
 *        tender type = "All"
 *
 *        the following where clause was erroneously getting into the query
 *
 *        TR_LTM_CRDB_CRD_TN.ID_ISSR_TND_MD = 'All'
 *   21   360Commerce 1.20        2/15/2007 12:56:28 PM  Nathan Syfrig
 *        CR25463:  Undo previous fix.  While the defect was fixed, it was
 *        very ugly and prone to cause trouble down the road.  The real fix
 *        was in LayawayBean.
 *   20   360Commerce 1.19        2/14/2007 11:19:51 PM  Nathan Syfrig
 *        CR25463:  Caught the exception being generated if no Layaway was
 *        found, allowing canceled suspended transactions to look at
 *        transactions.
 *   19   360Commerce 1.18        12/14/2006 3:18:16 PM  Christian Greene fix
 *        double assignment
 *   18   360Commerce 1.17        11/9/2006 7:16:44 PM   Jack G. Swan
 *        C:\dev\projects\360Commerce\build
 *   17   360Commerce 1.16        8/31/2006 4:34:20 PM   Sandy Gu        Clean
 *        up apis to set/get credit debit line item track bitmaps.
 *   16   360Commerce 1.15        7/24/2006 8:33:57 PM   Tony Zgarba     Merged
 *         changes from v7x for CR 18872.  Direct merge not possible due to
 *        inventory removal and UDM work.
 *   15   360Commerce 1.14        6/12/2006 11:49:26 AM  Brett J. Larsen CR
 *        18490 - UDM - new PK for gift card table: line-item-sequence-number
 *   14   360Commerce 1.13        6/8/2006 3:54:23 PM    Brett J. Larsen CR
 *        18490 - UDM - columns CD_MTH_PRDV, CD_SCP_PRDV and CD_BAS_PRDV's
 *        type was changed to INTEGER
 *   13   360Commerce 1.12        6/5/2006 2:43:00 PM    Brendan W. Farrell
 *        Remove mini_snd.
 *   12   360Commerce 1.11        6/2/2006 2:31:16 PM    Brendan W. Farrell
 *        Remove Mini_snd.
 *   11   360Commerce 1.10        5/30/2006 10:40:30 AM  Brett J. Larsen CR
 *        18490 - UDM - misc changes to AS_ITM* tables
 *   10   360Commerce 1.9         5/12/2006 5:26:18 PM   Charles D. Baker
 *        Merging with v1_0_0_53 of Returns Managament
 *   9    360Commerce 1.8         4/27/2006 7:25:03 PM   Brett J. Larsen CR
 *        17307 - remove inventory functionality - stage 2
 *   8    360Commerce 1.7         3/23/2006 12:33:37 PM  Brett J. Larsen CR
 *        16522 - merge returns management build 1.0.0.6 into trunk
 *   7    360Commerce 1.6         2/3/2006 1:04:53 PM    Deepanshu       CR
 *        5910: Check for voided transaction
 *   6    360Commerce 1.5         1/22/2006 11:47:49 AM  Ron W. Haight
 *        removed references to com.ibm.math.BigDecimal
 *   5    360Commerce 1.4         12/28/2005 2:32:58 PM  Sandy Gu        Merge
 *        inventory code from NEP63
 *   4    360Commerce 1.3         12/13/2005 4:43:39 PM  Barry A. Pape
 *        Base-lining of 7.1_LA
 *   3    360Commerce 1.2         3/31/2005 6:36:40 PM   Robert Pearse
 *   2    360Commerce 1.1         3/10/2005 10:26:25 AM  Robert Pearse
 *   1    360Commerce 1.0         2/14/2005 2:07:00 PM   Robert Pearse
 *  $
 *
 *  Revision 1.100.2.3  2005/01/03 21:22:56  jvangala
 *  update per funtional test completion
 *
 *  Revision 1.100.2.2  2004/12/21 23:06:14  djennings
 *  @scr 1. optionally filter out voided txns from getTransaction
 *           2. when searching for an item, optionally specify if we only want to search salelineitems, or returnlineitems, etc
 *           3. making transactiondto extend from extensibledto
 *           4. making some methods protected s.t. the can be customized.
 *           5. adding some methods explicitly for overriding
 *           6. stop passing transactionservicebean explicity to poslogutility and transactionheaderutility. make them do a jndi lookup ( so that the deployment descriptor changes will work properly ).
 *           7. parameterizing the instantiation of poslogutility and transactionheaderutility so they can be overridden
 *           8. allow getTransaction() result set to be sorted by transaction id or transaction timestamp
 *
 *  Revision 1.100.2.1  2004/12/21 19:31:26  rkar
 *  Changed importTransactions(Document) and supporting methods to read POSlog customVersion attribute instead of customerExtensionNamespace. Changed local var names for readability.
 *
 *  Revision 1.100  2004/12/17 17:53:40  mbranch
 *  Copying v700 to trunk
 *
 *  Revision 1.98.2.7  2004/12/09 02:04:18  mwright
 *  SCR 2206: The SCR describes the symptoms of a deeper problem.
 *  A workaround is in place until the call to TransactionLocal.getTenderRepositoryId() can be fixed.
 *
 *  Revision 1.98.2.6  2004/12/08 00:03:27  mwright
 *  SCR2224: Set transaction re-entry flag in SaleReturnTransactionDTO
 *
 *  Revision 1.98.2.5  2004/12/06 22:15:54  slloyd
 *  @scr Sears, finding txn by item id.
 *
 *  Revision 1.98.2.4  2004/12/06 09:00:54  mwright
 *  Added code for till suspend/resume transactions
 *  Replaced TenderType.getTypeFromDescriptor(local.getTenderType()).getDisplayString() with local.getTenderType() for till pickup and loan
 *
 *  Revision 1.98.2.3  2004/12/03 16:40:47  sgu
 *  @scr 3038 added support to check for suspended
 *  transactions at end of day
 *
 *  Revision 1.98.2.2  2004/11/23 01:22:53  rdunsmore
 *  fixing transaction search
 *
 *  Revision 1.98.2.1  2004/11/22 16:06:55  jlee
 *  @scr2170 made the join on commission modifier an outer join in case there was no commission for a sales associate search

 *   7    360Commerce1.6         2/3/2006 1:04:53 PM    Deepanshu       CR
 *        5910: Check for voided transaction
 *   6    360Commerce1.5         1/22/2006 11:47:49 AM  Ron W. Haight   removed
 *        references to com.ibm.math.BigDecimal
 *   5    360Commerce1.4         12/28/2005 2:32:58 PM  Sandy Gu        Merge
 *        inventory code from NEP63
 *   4    360Commerce1.3         12/13/2005 4:43:39 PM  Barry A. Pape
 *        Base-lining of 7.1_LA
 *   3    360Commerce1.2         3/31/2005 5:36:40 PM   Robert Pearse
 *   2    360Commerce1.1         3/10/2005 10:26:25 AM  Robert Pearse
 *   1    360Commerce1.0         2/14/2005 2:07:00 PM   Robert Pearse
 *  $
 *  Revision 1.98.2.10  2005/02/11 16:56:42  cmitchell
 *  @scr 2218
 *
 *  See developer note for full details.  Slight order refactor.
 *
 *  Revision 1.98.2.9  2005/02/07 22:16:10  cmitchell
 *  @scr 2307
 *
 *  Mostly a quick change to make use of the actual primary key for OrderBean.  Refer to the text within the scr for more details.
 *
 *  Revision 1.98.2.8  2004/12/21 00:49:25  jbudet
 *  @scr 3967
 *
 *  Revision 1.98.2.7  2004/12/09 02:04:18  mwright
 *  SCR 2206: The SCR describes the symptoms of a deeper problem.
 *  A workaround is in place until the call to TransactionLocal.getTenderRepositoryId() can be fixed.
 *
 *  Revision 1.98.2.6  2004/12/08 00:03:27  mwright
 *  SCR2224: Set transaction re-entry flag in SaleReturnTransactionDTO
 *
 *  Revision 1.98.2.5  2004/12/06 22:15:54  slloyd
 *  @scr Sears, finding txn by item id.
 *
 *  Revision 1.98.2.4  2004/12/06 09:00:54  mwright
 *  Added code for till suspend/resume transactions
 *  Replaced TenderType.getTypeFromDescriptor(local.getTenderType()).getDisplayString() with local.getTenderType() for till pickup and loan
 *
 *  Revision 1.98.2.3  2004/12/03 16:40:47  sgu
 *  @scr 3038 added support to check for suspended
 *  transactions at end of day
 *
 *  Revision 1.98.2.2  2004/11/23 01:22:53  rdunsmore
 *  fixing transaction search
 *
 *  Revision 1.98.2.1  2004/11/22 16:06:55  jlee
 *  @scr2170 made the join on commission modifier an outer join in case there was no commission for a sales associate search
 *
 *  Revision 1.98  2004/11/17 22:36:55  sgu
 *  @scr 3521. Rewrote the way how we retrieve suspended
 *  transaction during store close.
 *
 *  Revision 1.97  2004/11/16 16:55:33  rdunsmore
 *  @scr 2140 when line items or transaction details not found in transaction search we now return what we have if possible
 *
 *  Revision 1.96  2004/11/15 20:25:47  rdunsmore
 *  @scr 2136 & 2148 - fixed sql query joins when no tender criteria passed in (sig caps and price override)
 *
 *  Revision 1.95  2004/11/09 18:01:31  djennings
 *  @scr defects 2033, 2034, 2035
 *
 *  Revision 1.93  2004/11/05 22:17:45  rkar
 *  Database Connection parameter dropped in call to importTransaction() - its non-serializable.
 *
 *  Revision 1.92  2004/11/01 23:34:09  jlee
 *  Fixed an unexpected exception when search for tx with the default data
 *
 *  Revision 1.91  2004/10/28 16:26:51  rdunsmore
 *  added comments
 *
 *  Revision 1.90  2004/10/28 16:24:23  rdunsmore
 *  poslog transaction granularity
 *
 *  Revision 1.89  2004/10/27 23:24:22  rdunsmore
 *  fixing poslog transaction granularity
 *
 *  Revision 1.88  2004/10/22 20:42:18  sgu
 *  @scr 3031. Added check for unreconciled tills during register close.
 *  cleaned up exceptions in open/close register api.
 *  fixed report export regression
 *
 *  Revision 1.87  2004/10/19 20:48:46  sgu
 *  @scr 3033. modified the transaction service bean and
 *  financial total service bean to handle store bank deposit
 *  transaction.
 *
 *  Revision 1.86  2004/10/19 19:01:36  rkar
 *  importTransactions(): removed problematic database commit.
 *  Re-enabled throwing of Exceptions for POSlog import errors. Added comments, javadoc.
 *  Re-arranged import statements.
 *
 *  Revision 1.85  2004/10/18 18:59:34  ksharma
 *  Updated with Gift Card Entry
 *
 *  Revision 1.84  2004/10/15 19:24:41  tcronin
 *  changed search to use customer service
 *
 *  Revision 1.83  2004/10/14 21:44:33  tcronin
 *  backed out untested change...
 *
 *  Revision 1.82  2004/10/14 20:19:29  tcronin
 *  refactored transaction search to use customer service
 *
 *  Revision 1.81  2004/10/13 22:01:13  rdunsmore
 *  backing out autocommit
 *
 *  Revision 1.80  2004/10/13 21:55:52  ksharma
 *  Updated with entry for serial number search criteria
 *
 *  Revision 1.79  2004/10/13 21:49:01  mwright
 *  Removed unused imports
 *  POSLog import: Set connection auto-commit to false, and commit transaction after successful import
 *
 *  Revision 1.78  2004/10/07 22:39:17  ksharma
 *  Updated with "Reentry Mode"
 *
 *  Revision 1.77  2004/10/06 22:58:48  jbudet
 *  Implementation of "CO - Transaction Tracker - Search by Tender Type" story.
 *
 *  Revision 1.76  2004/10/06 22:15:18  ksharma
 *  Updated with "Training Mode" search criteria
 *
 *  Revision 1.75  2004/10/06 18:47:34  jlee
 *  Removed Sears specific code and made them deployment time env-entries.
 *
 *  Revision 1.74  2004/10/06 14:09:01  qnguyen
 *  1)Test for TransactionHeaderUtility.  More to come
 *  2)Editing default policy, buttons to configure items/stores/merchanhier/storehier are disabled.
 *
 *  Revision 1.73  2004/10/05 20:02:07  slloyd
 *  Updates for TransactionNumber
 *
 *  Revision 1.72  2004/10/05 15:05:43  ksharma
 *  Updated wityh redeem and instant credit transaction types
 *
 *  Revision 1.71  2004/10/04 22:38:04  slloyd
 *  Remove formatting from TransactionKey class, create TransactionKeyFormatterIfc and TransactionKeyFormatter for formatting/parsing of transactionnumber into key class.
 *
 *  Revision 1.70  2004/10/04 20:41:12  rkar
 *  In POSLog import code, changed schemaLocation to searsNamespace
 *
 *  Revision 1.69  2004/10/04 19:42:10  ehoq
 *  Detail View - House Account Payment
 *
 *  Revision 1.68  2004/10/04 16:33:22  ksharma
 *  Updated with redeem transaction enteries
 *
 *  Revision 1.67  2004/10/01 21:27:23  ehoq
 *  Final checkin - Detail view of Till Pay In/Out
 *
 *  Revision 1.66  2004/10/01 20:47:19  qnguyen
 *  1) deployment descriptors fixed for websphere for CO.
 *  2) take out the "EXCHANGE" transaction type as the support search transaction type for
 *     POSLog utility as well as TransactionHeader utility because TransactionServiceBean
 *     does not retrieve transaction based upon this type currently.
 *  2) add to transactionServiceBean, getTransactionHeader() method.
 *     This code is not yet tested.  Need FT and webservices test.
 *     But it does deploy on websphere.
 *
 *  Revision 1.65  2004/09/30 23:09:55  ehoq
 *  Retrieve till pay in/out transactions
 *
 *  Revision 1.64  2004/09/30 17:08:41  epd
 *  Fixing Red Iron gaffes
 *
 *  Revision 1.63  2004/09/24 14:21:59  akarpov
 *  @scr 2705
 *  createInventoryReturnTransaction fixed to create InventoryReturnTransaction
 *
 *  Revision 1.62  2004/09/23 21:20:54  rkar
 *  Changed importTransaction(Document) to handle Sears extensions to POSLog v2.1. Updated javadoc.
 *
 *  ... earlier history deleted ...
 * ===========================================================================
 */
package oracle.retail.stores.commerceservices.transaction.ejb;

import gdyn.retail.stores.commerceservices.transaction.GDYNCOLoyalitConstants;
import gdyn.retail.stores.webmodules.ejournal.GDYNLoyalityEJournalViewBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import oracle.retail.stores.commerceservices.CommerceservicesGateway;
import oracle.retail.stores.commerceservices.codelist.CodeListConstantsIfc;
import oracle.retail.stores.commerceservices.codelist.ejb.CodeListServiceHome;
import oracle.retail.stores.commerceservices.codelist.ejb.CodeListServiceRemote;
import oracle.retail.stores.commerceservices.common.InvalidTypeException;
import oracle.retail.stores.commerceservices.common.SearchException;
import oracle.retail.stores.commerceservices.common.SearchResultSizeExceededException;
import oracle.retail.stores.commerceservices.common.currency.CurrencyDecimal;
import oracle.retail.stores.commerceservices.currency.TenderType;
import oracle.retail.stores.commerceservices.currency.ejb.CurrencyServiceHome;
import oracle.retail.stores.commerceservices.currency.ejb.CurrencyServiceRemote;
import oracle.retail.stores.commerceservices.customer.CustomerSearchCriteria;
import oracle.retail.stores.commerceservices.customer.CustomerSearchResult;
import oracle.retail.stores.commerceservices.customer.ejb.CustomerServiceHome;
import oracle.retail.stores.commerceservices.customer.ejb.CustomerServiceRemote;
import oracle.retail.stores.commerceservices.ejournal.EJournalDTO;
import oracle.retail.stores.commerceservices.ejournal.EJournalKey;
import oracle.retail.stores.commerceservices.ejournal.ejb.EJournalLocal;
import oracle.retail.stores.commerceservices.ejournal.ejb.EJournalLocalHome;
import oracle.retail.stores.commerceservices.employee.EmployeeDTO;
import oracle.retail.stores.commerceservices.employee.EmployeeNotFoundException;
import oracle.retail.stores.commerceservices.employee.ejb.EmployeeServiceHome;
import oracle.retail.stores.commerceservices.employee.ejb.EmployeeServiceRemote;
import oracle.retail.stores.commerceservices.item.giftcard.GiftCardPK;
import oracle.retail.stores.commerceservices.item.giftcard.ejb.GiftCardLocal;
import oracle.retail.stores.commerceservices.item.giftcard.ejb.GiftCardLocalHome;
import oracle.retail.stores.commerceservices.layaway.LayawayDTO;
import oracle.retail.stores.commerceservices.layaway.ejb.LayawayLocal;
import oracle.retail.stores.commerceservices.layaway.ejb.LayawayLocalHome;
import oracle.retail.stores.commerceservices.parameter.access.ParameterIfc;
import oracle.retail.stores.commerceservices.parameter.ejb.ParameterServiceHome;
import oracle.retail.stores.commerceservices.parameter.ejb.ParameterServiceRemote;
import oracle.retail.stores.commerceservices.store.EmployeeHierarchyNotFoundException;
import oracle.retail.stores.commerceservices.store.WorkstationIfc;
import oracle.retail.stores.commerceservices.store.ejb.WorkstationDrawerIfc;
import oracle.retail.stores.commerceservices.store.ejb.WorkstationLocal;
import oracle.retail.stores.commerceservices.store.ejb.WorkstationLocalHome;
import oracle.retail.stores.commerceservices.store.ejb.WorkstationPK;
import oracle.retail.stores.commerceservices.storedirectory.HierarchyNodeKey;
import oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria;
import oracle.retail.stores.commerceservices.storedirectory.ejb.StoreDirectoryHome;
import oracle.retail.stores.commerceservices.storedirectory.ejb.StoreDirectoryRemote;
import oracle.retail.stores.commerceservices.tender.ejb.TenderServiceHome;
import oracle.retail.stores.commerceservices.tender.ejb.TenderServiceRemote;
import oracle.retail.stores.commerceservices.tender.lineitem.TenderLineItemKey;
import oracle.retail.stores.commerceservices.tender.lineitem.creditdebit.ejb.CreditDebitLineItemLocal;
import oracle.retail.stores.commerceservices.tender.lineitem.creditdebit.ejb.CreditDebitLineItemLocalHome;
import oracle.retail.stores.commerceservices.tender.lineitem.giftcard.GiftCardLineItemDTO;
import oracle.retail.stores.commerceservices.tender.lineitem.payment.PaymentDTO;
import oracle.retail.stores.commerceservices.tender.lineitem.payment.ejb.PaymentLocal;
import oracle.retail.stores.commerceservices.tender.lineitem.payment.ejb.PaymentLocalHome;
import oracle.retail.stores.commerceservices.tender.lineitem.tender.TenderLineItemDTO;
import oracle.retail.stores.commerceservices.till.ejb.TillIfc;
import oracle.retail.stores.commerceservices.transaction.BillDTO;
import oracle.retail.stores.commerceservices.transaction.BillpayTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.CustomerCriteria;
import oracle.retail.stores.commerceservices.transaction.EJournalCriteria;
import oracle.retail.stores.commerceservices.transaction.EJournalKeyFormatter;
import oracle.retail.stores.commerceservices.transaction.EJournalKeyFormatterIfc;
import oracle.retail.stores.commerceservices.transaction.EJournalSearchDTO;
import oracle.retail.stores.commerceservices.transaction.EJournalSearchResultDTO;
import oracle.retail.stores.commerceservices.transaction.LineItemCriteria;
import oracle.retail.stores.commerceservices.transaction.NextTransactionSeqNumNotFoundException;
import oracle.retail.stores.commerceservices.transaction.SalesAssociateCriteria;
import oracle.retail.stores.commerceservices.transaction.SearchCriteria;
import oracle.retail.stores.commerceservices.transaction.SignatureCaptureCriteria;
import oracle.retail.stores.commerceservices.transaction.TaxLineItemCriteria;
import oracle.retail.stores.commerceservices.transaction.TenderCriteria;
import oracle.retail.stores.commerceservices.transaction.TransactionCreationException;
import oracle.retail.stores.commerceservices.transaction.TransactionCriteria;
import oracle.retail.stores.commerceservices.transaction.TransactionDTO;
import oracle.retail.stores.commerceservices.transaction.TransactionKey;
import oracle.retail.stores.commerceservices.transaction.TransactionKeyFormatter;
import oracle.retail.stores.commerceservices.transaction.TransactionKeyFormatterIfc;
import oracle.retail.stores.commerceservices.transaction.TransactionRetrievalException;
import oracle.retail.stores.commerceservices.transaction.TransactionSearchDTO;
import oracle.retail.stores.commerceservices.transaction.TransactionSearchResultDTO;
import oracle.retail.stores.commerceservices.transaction.TransactionServiceIfc;
import oracle.retail.stores.commerceservices.transaction.TransactionType;
import oracle.retail.stores.commerceservices.transaction.TransactionUtils;
import oracle.retail.stores.commerceservices.transaction.control.ejb.ControlTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.discount.DiscountLineItemDTO;
import oracle.retail.stores.commerceservices.transaction.discount.PriceModifierDTO;
import oracle.retail.stores.commerceservices.transaction.discount.ejb.DiscountLineItemLocal;
import oracle.retail.stores.commerceservices.transaction.discount.ejb.DiscountLineItemLocalHome;
import oracle.retail.stores.commerceservices.transaction.discount.ejb.PriceModifierLocal;
import oracle.retail.stores.commerceservices.transaction.discount.ejb.PriceModifierLocalHome;
import oracle.retail.stores.commerceservices.transaction.instantcredit.InstantCreditTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.instantcredit.ejb.InstantCreditTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.instantcredit.ejb.InstantCreditTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.layaway.LayawayTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.nosale.NoSaleTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.nosale.ejb.NoSaleTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.nosale.ejb.NoSaleTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.order.OrderDTO;
import oracle.retail.stores.commerceservices.transaction.order.OrderLineItemDTO;
import oracle.retail.stores.commerceservices.transaction.order.OrderRecipientDetailDTO;
import oracle.retail.stores.commerceservices.transaction.order.OrderTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderLineItemLocal;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderLineItemLocalHome;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderLocal;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderLocalHome;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderPK;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderRecipientDetailLocal;
import oracle.retail.stores.commerceservices.transaction.order.ejb.OrderRecipientDetailLocalHome;
import oracle.retail.stores.commerceservices.transaction.postvoid.VoidTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.postvoid.ejb.VoidTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.postvoid.ejb.VoidTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.redeem.RedeemTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.redeem.ejb.RedeemTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.redeem.ejb.RedeemTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.register.RegisterTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.register.ejb.RegisterTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.register.ejb.RegisterTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.resource.ConstantsIfc;
import oracle.retail.stores.commerceservices.transaction.retail.ejb.RetailTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.retail.ejb.RetailTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.salereturn.PLULineItemDTO;
import oracle.retail.stores.commerceservices.transaction.salereturn.SaleReturnLineItemDTO;
import oracle.retail.stores.commerceservices.transaction.salereturn.SaleReturnLineItemTaxDTO;
import oracle.retail.stores.commerceservices.transaction.salereturn.SaleReturnTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.salereturn.ejb.SaleReturnLineItemLocal;
import oracle.retail.stores.commerceservices.transaction.salereturn.ejb.SaleReturnLineItemLocalHome;
import oracle.retail.stores.commerceservices.transaction.salereturn.ejb.SaleReturnLineItemTaxLocal;
import oracle.retail.stores.commerceservices.transaction.salereturn.ejb.SaleReturnLineItemTaxLocalHome;
import oracle.retail.stores.commerceservices.transaction.shipping.SaleReturnShippingRecordDTO;
import oracle.retail.stores.commerceservices.transaction.shipping.SaleReturnShippingRecordTaxDTO;
import oracle.retail.stores.commerceservices.transaction.shipping.ejb.SaleReturnShippingRecordLocal;
import oracle.retail.stores.commerceservices.transaction.shipping.ejb.SaleReturnShippingRecordLocalHome;
import oracle.retail.stores.commerceservices.transaction.shipping.ejb.SaleReturnShippingRecordTaxLocal;
import oracle.retail.stores.commerceservices.transaction.shipping.ejb.SaleReturnShippingRecordTaxLocalHome;
import oracle.retail.stores.commerceservices.transaction.store.StoreTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.store.ejb.StoreTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.store.ejb.StoreTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.tax.TaxExemptionDTO;
import oracle.retail.stores.commerceservices.transaction.tax.ejb.TaxExemptionLocal;
import oracle.retail.stores.commerceservices.transaction.tax.ejb.TaxExemptionLocalHome;
import oracle.retail.stores.commerceservices.transaction.till.TillLoanTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.till.TillPayInOutTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.till.TillPickupTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.till.TillStatus;
import oracle.retail.stores.commerceservices.transaction.till.TillTransactionDTO;
import oracle.retail.stores.commerceservices.transaction.till.ejb.TillTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.till.ejb.TillTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.till.loan.ejb.TillLoanTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.till.loan.ejb.TillLoanTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.till.payinout.ejb.FinancialAccountingTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.till.payinout.ejb.FinancialAccountingTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.till.payinout.ejb.FundReceiptTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.till.payinout.ejb.FundReceiptTransactionLocalHome;
import oracle.retail.stores.commerceservices.transaction.till.pickup.ejb.TillPickupTransactionLocal;
import oracle.retail.stores.commerceservices.transaction.till.pickup.ejb.TillPickupTransactionLocalHome;
import oracle.retail.stores.commerceservices.util.ImageUtils;
import oracle.retail.stores.commerceservices.util.XSDElementConversionUtil;
import oracle.retail.stores.commerceservices.util.xml.Ejournal;
import oracle.retail.stores.commerceservices.util.xml.EjournalEntry;
import oracle.retail.stores.commerceservices.util.xml.LayawaySearchCriteria;
import oracle.retail.stores.commerceservices.util.xml.LocalizedTextElement;
import oracle.retail.stores.commerceservices.util.xml.ObjectFactory;
import oracle.retail.stores.commerceservices.util.xml.OrderSearchCriteria;
import oracle.retail.stores.commerceservices.util.xml.OrderSearchInfo;
import oracle.retail.stores.commerceservices.util.xml.QueryTransactionRequest;
import oracle.retail.stores.commerceservices.util.xml.SearchResultsExceedElement;
import oracle.retail.stores.commerceservices.util.xml.Signature;
import oracle.retail.stores.commerceservices.util.xml.SignatureEntry;
import oracle.retail.stores.commerceservices.util.xml.TransactionFirstItemDescriptionSearchCriteria;
import oracle.retail.stores.commerceservices.util.xml.TransactionFirstItemDescriptionSearchResponse;
import oracle.retail.stores.commerceservices.util.xml.TransactionHeader;
import oracle.retail.stores.commerceservices.util.xml.TransactionID;
import oracle.retail.stores.commerceservices.util.xml.TransactionKeyElement;
import oracle.retail.stores.commerceservices.xmltosql.JdbcSaveIXRetailTransactionCS;
import oracle.retail.stores.commerceservices.xmltosql.JdbcSaveIXRetailTransactionCSIfc;
import oracle.retail.stores.common.context.BeanLocator;
import oracle.retail.stores.common.data.JdbcUtilities;
import oracle.retail.stores.common.utility.ApplicationConfigurationBean;
import oracle.retail.stores.common.utility.EncodingIfc;
import oracle.retail.stores.common.utility.LanguageResourceBundleUtil;
import oracle.retail.stores.common.utility.LocaleMap;
import oracle.retail.stores.common.utility.LocaleRequestor;
import oracle.retail.stores.common.utility.LocaleUtilities;
import oracle.retail.stores.common.utility.LocalizedText;
import oracle.retail.stores.common.utility.LocalizedTextIfc;
import oracle.retail.stores.common.utility.Util;
import oracle.retail.stores.domain.financial.LayawayConstantsIfc;
import oracle.retail.stores.domain.order.OrderConstantsIfc;
import oracle.retail.stores.domain.tax.TaxConstantsIfc;
import oracle.retail.stores.domain.tender.TenderTypeMap;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.foundation.common.SessionBeanAdapter;
import oracle.retail.stores.foundation.manager.data.DataException;
import oracle.retail.stores.foundation.util.DBUtils;
import oracle.retail.stores.foundation.util.DateTimeUtils;
import oracle.retail.stores.persistence.common.ContextDBConnectionManager;
import oracle.retail.stores.persistence.item.Item;
import oracle.retail.stores.persistence.item.ItemFacadeIfc;
import oracle.retail.stores.persistence.reasoncode.ReasonCode;
import oracle.retail.stores.persistence.store.RetailStore;
import oracle.retail.stores.persistence.utility.ARTSDatabaseIfc;
import oracle.retail.stores.persistence.utility.BasicDBUtils;
import oracle.retail.stores.persistence.utility.DAOFactoryIfc;
import oracle.retail.stores.xmlreplication.ExtractorObjectFactoryIfc;
import oracle.retail.stores.xmlreplication.ImporterObjectFactoryIfc;
import oracle.retail.stores.xmlreplication.ReplicationObjectFactoryContainer;
import oracle.retail.stores.xmlreplication.extractor.EntityReaderCatalogIfc;
import oracle.retail.stores.xmlreplication.extractor.EntitySearchField;
import oracle.retail.stores.xmlreplication.extractor.EntitySearchFieldIfc;
import oracle.retail.stores.xmlreplication.extractor.EntitySearchIfc;
import oracle.retail.stores.xmlreplication.extractor.ReaderCatalogConfiguratorIfc;
import oracle.retail.stores.xmlreplication.extractor.ReplicationExportException;
import oracle.retail.stores.xmlreplication.importer.ReplicationImportException;
import oracle.retail.stores.xmlreplication.importer.TablePersistanceConfigurationCatalogIfc;
import oracle.retail.stores.xmlreplication.importer.TablePersistanceConfiguratorIfc;
import oracle.retail.stores.xmlreplication.importer.XMLReplicationImporterHandler;
import oracle.retail.stores.xmlreplication.result.EntityBatchIfc;
import oracle.retail.stores.xmlreplication.result.EntityIfc;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * TransactionServiceBean is the facade for a number of transaction-related
 * services. It provides transaction search, POSLog import, ejournal and
 * signature capture functionality.
 */
public class TransactionServiceBean extends SessionBeanAdapter implements TransactionServiceIfc
{
    private static final long serialVersionUID = -3595636617123857751L;
    private static final Logger logger = Logger.getLogger(TransactionServiceBean.class);


    /**
     * Item Data Access Object
     */
    private DAOFactoryIfc itemFacadeFactory;

    /** The package name by which the JAXBContext is created. */
    private static final String JAXB_CONTEXT = "oracle.retail.stores.commerceservices.util.xml";

    private static final BigDecimal SHIPPING_CHARGE = CurrencyDecimal.BIG_ZERO_TWO;

    /**
     * Constant SQL declaration for finding an order ID by Transaction
     */
    private static final String GET_ORDER_ID_SQL = " SELECT ORD.ID_ORD FROM TR_RTL ORD ,TR_TRN TRN WHERE TRN.ID_STR_RT=ORD.ID_STR_RT AND "
            + " TRN.ID_WS=ORD.ID_WS AND TRN.AI_TRN=ORD.AI_TRN AND TRN.DC_DY_BSN=ORD.DC_DY_BSN AND "
            + " TRN.TY_TRN IN ("
            + JdbcUtilities.inQuotes(TransactionType.ORDER_COMPLETE.getOrdinal())
            + ","
            + JdbcUtilities.inQuotes(TransactionType.ORDER_PARTIAL.getOrdinal())
            + ") AND "
            + " ORD.ID_STR_RT = ? AND ORD.ID_WS = ? AND ORD.AI_TRN = ? AND ORD.DC_DY_BSN = ?";
    private static final String READ_LAYAWAY_TRAN_IDS = "SELECT ID_STR_RT, ID_WS, AI_TRN, DC_DY_BSN FROM TR_RTL WHERE ID_LY = ?";
    private static final String READ_TRAN_FIRST_DESC = "SELECT DE_ITM_LCL, DE_ITM_SHRT_RCPT FROM TR_LTM_SLS_RTN WHERE ID_STR_RT = ? AND ID_WS = ? "
            + "AND AI_TRN = ? AND DC_DY_BSN = ? AND AI_LN_ITM = 0";
    private static final String READ_TRAN_TAX_GROUPS_BDATE = "SELECT ID_GP_TX FROM TR_LTM_SLS_RTN WHERE ID_STR_RT = ? ID_WS = ? "
            + "AND AI_TRN = ? AND DC_DY_BSN = ?";
    private static final String READ_TRAN_TAX_GROUPS = "SELECT ID_GP_TX FROM TR_LTM_SLS_RTN WHERE ID_STR_RT = ? AND ID_WS = ? "
            + "AND AI_TRN = ?";
    private static final String READ_TRAN_TAX_AUTHORITIES = "SELECT ID_ATHY_TX FROM PA_STR_RTL str, CO_TX_JUR_ATHY_LNK lnk WHERE ID_STR_RT = ? AND str.ID_CD_GEO = lnk.ID_CD_GEO";

    private static String schemaTypeFactoryName = null;
    private static String posLogTransactionUtilityName = null;
    private static String transactionHeaderUtilityName = null;

    private String SHIPPING_TYPE = "";
    private org.xml.sax.XMLReader replicationParser = null;
    private XMLReplicationImporterHandler replicationHandler = null;
    private EntityReaderCatalogIfc entityReaderCatalog = null;
    private List<Object> bindVariables;
    private JdbcSaveIXRetailTransactionCS jdbcSaveIXRetailTransaction;
    private JAXBContext jaxbContext;

    public TransactionKeyFormatterIfc getTransactionKeyFormatter()
    {
        return new TransactionKeyFormatter(0, getStoreIDLength(), getStoreIDLength(), getWorkstationIDLength(), getStoreIDLength()+ getWorkstationIDLength(), getSequenceNumberLength(), -1, -1);
    }

    public EJournalKeyFormatterIfc getEJournalKeyFormatter()
    {
        return new EJournalKeyFormatter(0, getStoreIDLength(), getStoreIDLength(), getWorkstationIDLength(), getStoreIDLength()+ getWorkstationIDLength(), getSequenceNumberLength(), -1, -1);
    }

    /**
     * Cancel a suspended transaction
     * @param key transaction key
     * @throws ObjectNotFoundException
     */
    public void cancelSuspendedTransaction(TransactionKey key) throws ObjectNotFoundException
    {
        if (getLogger().isDebugEnabled())
            getLogger().debug("cancelSuspendedTransaction(): key = " + key);

        // cancel any layaways corresponding to suspended
        // layaway-initiate transactions
        try
        {
            LayawayLocal layawayLocal = getLayawayHome().findByTransaction(key.getStoreID(), key.getWorkstationID(),
                    key.getFormattedDateString(), key.getSequenceNumber());
            layawayLocal.setPreviousLayawayStatus(layawayLocal.getStatus());
            layawayLocal.setStatus(LayawayConstantsIfc.STATUS_SUSPENDED_CANCELED);
            layawayLocal.setRecordCreationTimestamp(new Date());
            layawayLocal.setRecordLastModifiedTimestamp(new Date());
        }
        catch (FinderException e)
        {
            getLogger().info("This is not a layaway-initiated transaction");
        }

        // cancel any special orders corresponding to suspended
        // special order initiatee transactions
        try
        {
            RetailTransactionLocal localRetail = getRetailTransactionHome().findByPrimaryKey(key);
            String orderID = localRetail.getOrderID();
            Collection<OrderLocal> orders = getOrderHome().findByOrderId(orderID);

            if (orders.size() == 0)
            {
                throw new FinderException("No special order found");
            }
            OrderLocal ol = orders.iterator().next();
            OrderPK orderPK = (OrderPK)ol.getPrimaryKey();
            OrderLocal orderLocal = getOrderHome().findByPrimaryKey(orderPK);
            orderLocal.setOrderStatus(OrderConstantsIfc.ORDER_STATUS_SUSPENDED_CANCELED);
        }
        catch (FinderException e)
        {
            getLogger().info("This is not a special order-initiated transaction");
        }

        //update transaction status
        try
        {
            TransactionLocal local = getTransactionHome().findByPrimaryKey(key);
            local.setTransactionStatusCode(TransactionConstantsIfc.STATUS_SUSPENDED_CANCELED);
        }
        catch (FinderException e)
        {
            getLogger().error("", e);
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    /**
     * Returns the type of a transaction given its key
     *
     * @param key TransactionKey
     * @return TransactionType type
     */
    public TransactionType getType(TransactionKey key) throws InvalidTypeException, ObjectNotFoundException
    {
        TransactionType type = TransactionType.UNKNOWN;
        getLogger().debug("getType() : Getting type for " + key);
        try
        {
            TransactionLocal local = getTransactionHome().findByPrimaryKey(key);
            type = TransactionType.getType(local.getTransactionTypeCode());
            getLogger().debug("getType() : Type is " + type);
            return type;
        }
        catch (FinderException e)
        {
            if (e instanceof ObjectNotFoundException)
            {
                throw (ObjectNotFoundException)e;
            }
            getLogger().error("", e);
            throw new InvalidTypeException("Not Found " + e.getMessage());
        }
    }

    public Collection<TransactionDTO> findTransactionsByStoreAndBusinessDay(String storeID, String businessDay)
    {
        getLogger().debug("findByStoreAndBusinessDay(" + storeID + ", " + businessDay + ")");
        Collection<TransactionDTO> c = new ArrayList<TransactionDTO>();
        try
        {
            Collection<TransactionLocal> trnCollection = getTransactionHome().findByStoreAndBusinessDay(storeID,
                    businessDay);
            for (TransactionLocal t : trnCollection)
            {
                TransactionDTO dto = retrieveTransaction((TransactionKey)t.getPrimaryKey(), LocaleMap
                        .getLocale(LocaleMap.DEFAULT));
                c.add(dto);
            }

        }
        catch (Exception e)
        {
            throw new EJBException(e);
        }
        return c;
    }

    public Collection<TransactionDTO> findTransactionsByWorkstationAndBusinessDay(String storeID, String workstationID,
            String businessDay)
    {
        if (getLogger().isDebugEnabled())
            getLogger().debug(
                    "findTransactionsByWorkstationAndBusinessDay(" + storeID + ", " + workstationID + ", "
                            + businessDay + ")");
        Collection<TransactionDTO> c = new ArrayList<TransactionDTO>();
        try
        {
            Collection<TransactionLocal> trnCollection = getTransactionHome().findByWorkstationAndBusinessDay(storeID,
                    workstationID, businessDay);
            for (TransactionLocal t : trnCollection)
            {
                TransactionDTO dto = retrieveTransaction((TransactionKey)t.getPrimaryKey(), LocaleMap
                        .getLocale(LocaleMap.DEFAULT));
                c.add(dto);
            }
        }
        catch (Exception e)
        {
            throw new EJBException(e);
        }
        return c;
    }

    public void removeTransaction(TransactionKey transactionKey) throws RemoveException
    {
        getLogger().debug("removeTransaction() " + transactionKey);
        try
        {
            TransactionLocal transactionLocal = getTransactionHome().findByPrimaryKey(transactionKey);
            TransactionType type = TransactionType.getType(transactionLocal.getTransactionTypeCode());
            if (TransactionType.SALE.equals(type) || TransactionType.RETURN.equals(type))
            {
                getRetailTransactionHome().remove(transactionKey);
            }
            else if (TransactionType.VOID.equals(type))
            {
                getVoidTransactionLocalHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if (TransactionType.NO_SALE.equals(type))
            {
                getNoSaleTransactionHome().remove(transactionKey);
            }
            else if (TransactionType.OPEN_STORE.equals(type) || TransactionType.CLOSE_STORE.equals(type))
            {
                getStoreTransactionHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if (TransactionType.OPEN_REGISTER.equals(type) || TransactionType.CLOSE_REGISTER.equals(type))
            {
                getRegisterTransactionHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if (TransactionType.OPEN_TILL.equals(type) || TransactionType.CLOSE_TILL.equals(type)
                    || TransactionType.SUSPEND_TILL.equals(type) || TransactionType.RESUME_TILL.equals(type))
            {
                getTillTransactionLocalHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if (TransactionType.TILL_LOAN.equals(type))
            {
                getTillLoanTransactionLocalHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if (TransactionType.TILL_PICKUP.equals(type))
            {
                getTillPickupTransactionLocalHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if ((TransactionType.TILL_PAY_IN.equals(type)) || (TransactionType.TILL_PAY_OUT.equals(type)))
            {
                getFinancialAccountingTransactionLocalHome().remove(transactionKey);
                getFundReceiptTransactionLocalHome().remove(transactionKey);
            }
            else if (TransactionType.REDEEM.equals(type))
            {
                getRedeemTransactionHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else if (TransactionType.INSTANT_CREDIT_ENROLLMENT.equals(type))
            {
                getInstantCreditTransactionHome().remove(transactionKey);
                removeControlTransaction(transactionKey);
            }
            else
            {
                throw new RemoveException("TransactionType unknown for key " + transactionKey);
            }
            getTransactionHome().remove(transactionKey);
        }
        catch (FinderException e)
        {
            throw new RemoveException("Could not find transaction");
        }
    }

    private void removeControlTransaction(TransactionKey transactionKey)
    {
        try
        {
            getControlTransactionHome().remove(transactionKey);
        }
        catch (RemoveException e)
        {
            //TODO - WHAT TO DO? IS IT ALWAYS THERE?
        }
        catch (EJBException e)
        {
            //TODO - WHAT TO DO? IS IT ALWAYS THERE?
        }
    }

    /**
     * Retrieves a transaction data transfer object given a TransactionKey as input.
     *
     * @param key
     * @return RetailTransactionDTO
     * @deprecated As of 13.1 use {@link retrieveTransaction(key, locale)}
     */
    public TransactionDTO retrieveTransaction(TransactionKey key) throws ObjectNotFoundException, InvalidTypeException
    {
        return retrieveTransaction(key, LocaleMap.getLocale(LocaleMap.DEFAULT));
    }

    /**
     * Retrieves a transaction data transfer object given a TransactionKey as input.
     *
     * @param key
     * @param locale
     * @return RetailTransactionDTO
     * @throws ObjectNotFoundException
     * @throws InvalidTypeException
     */
    public TransactionDTO retrieveTransaction(TransactionKey key, Locale locale) throws ObjectNotFoundException,
            InvalidTypeException
    {
        TransactionDTO dto = null;

        // This is a workaround until ??? works:
        String tillCloseTillID = null;

        if (getLogger().isDebugEnabled())
            getLogger().debug("retrieveTransaction() " + key);
        try
        {
            TransactionLocal transactionLocal = getTransactionHome().findByPrimaryKey(key);
            TransactionType type = TransactionType.getType(transactionLocal.getTransactionTypeCode());

            if (TransactionType.SALE.equals(type) || TransactionType.RETURN.equals(type))
            {

                RetailTransactionLocal local = getRetailTransactionHome().findByPrimaryKey(key);
                dto = createSaleReturnTransactionDTO(local, locale);

            }
            else if (TransactionType.VOID.equals(type))
            {
                VoidTransactionLocal local = getVoidTransactionLocalHome().findByPrimaryKey(key);
                TransactionKey voidedKey = null;
                try
                {
                    String voidedStoreId = local.getStoreID();
                    String voidedWorkstationId = local.getVoidedTransactionWorkstationID();
                    if (StringUtils.isEmpty(voidedWorkstationId))
                    {
                        voidedWorkstationId = key.getWorkstationID();
                    }
                    int voidedSequenceNumber = local.getVoidedTranactionSequenceNumber();
                    String voidedBusinessDate = local.getBusinessDay();
                    voidedKey = new TransactionKey(voidedStoreId, voidedWorkstationId, voidedSequenceNumber,
                            voidedBusinessDate);
                }
                catch (ParseException e)
                {
                    getLogger().error("Error parsing ", e);
                }
                TransactionKeyFormatterIfc formatter = getTransactionKeyFormatter();
                Collection<PLULineItemDTO> items = getItemsForTransaction(voidedKey, false, locale);
                dto = new VoidTransactionDTO(key, voidedKey, formatter.format(voidedKey), formatter
                        .formatBusinessDate(key), items);
                TaxExemptionDTO taxExemptionDTO = getTaxExemptionRecordForTransaction(voidedKey, locale);
                ((VoidTransactionDTO)dto).setTaxExemptionDTO(taxExemptionDTO);
            }
            else if (TransactionType.NO_SALE.equals(type))
            {
                NoSaleTransactionLocal local = getNoSaleTransactionHome().findByPrimaryKey(key);
                if(local.getNoSaleReasonCode()!=null && local.getNoSaleReasonCode().length()>0)
                {
                    Collection reasonCodeDTOs = getCodeListService().getReasonCodeByStoreAndDescription(key.getStoreID(), CodeListConstantsIfc.NO_SALE, locale);
                    Iterator iter = reasonCodeDTOs.iterator();
                    while(iter.hasNext())
                    {
                        ReasonCode reasonCodeDTO = (ReasonCode) iter.next();
                        if(reasonCodeDTO.getEntryCode().equals(local.getNoSaleReasonCode()))
                        {
                            dto = new NoSaleTransactionDTO(key, reasonCodeDTO.getCodeEntryDescription());
                            break;
                        }
                    }
                }
                else
                {
                    dto = new NoSaleTransactionDTO(key, "");
                }


            }
            else if (TransactionType.OPEN_STORE.equals(type) || TransactionType.CLOSE_STORE.equals(type))
            {
                StoreTransactionLocal local = getStoreTransactionHome().findByPrimaryKey(key);
                dto = new StoreTransactionDTO(key, local.getOperatorID(), transactionLocal.getType().toDisplayString(),
                        local.getTransactionTimestamp());
            }
            else if (TransactionType.STORE_BANK_DEPOSIT.equals(type))
            {
                dto = new TransactionDTO(key, transactionLocal.getType().toDisplayString(), transactionLocal
                        .getOperatorID(), transactionLocal.getTransactionEndDateTime(), "");
            }
            else if (TransactionType.OPEN_REGISTER.equals(type) || TransactionType.CLOSE_REGISTER.equals(type))
            {
                RegisterTransactionLocal local = getRegisterTransactionHome().findByPrimaryKey(key);
                dto = new RegisterTransactionDTO(key, local.getOperatorID(), transactionLocal.getType()
                        .toDisplayString(), local.getTransactionTimestamp(), local.getTillFloatAmount(), local
                        .getAccountability(), //Register Accountability is true when accountability is false i.e '0'
                        local.getCountTillAtClose(), local.getCountCashLoan(), local.getCountCashPickup(), local
                                .getCountCheckPickup());
            }
            else if (TransactionType.LAYAWAY.equals(type) || TransactionType.LAYAWAY_COMPLETE.equals(type)
                    || TransactionType.LAYAWAY_DELETE.equals(type) || TransactionType.LAYAWAY_PAYMENT.equals(type))
            {
                // for cancel transactions no details get saved so nothing to retrieve
                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    RetailTransactionLocal local = getRetailTransactionHome().findByPrimaryKey(key);
                    dto = createLayawayTransactionDTO(local, locale);
                }
                else
                {
                    dto = new LayawayTransactionDTO(key);
                }
            }
            else if (TransactionType.SPECIAL_ORDER.equals(type) || TransactionType.ORDER_COMPLETE.equals(type)
                    || TransactionType.ORDER_CANCEL.equals(type) || TransactionType.ORDER_PARTIAL.equals(type))
            {
                // for cancel transactions no details get saved so nothing to retrieve
                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    RetailTransactionLocal local = getRetailTransactionHome().findByPrimaryKey(key);
                    dto = createOrderTransactionDTO(local, locale);
                }
                else
                {
                    dto = new OrderTransactionDTO(key);
                }
            }
            else if (TransactionType.OPEN_TILL.equals(type) || TransactionType.CLOSE_TILL.equals(type)
                    || TransactionType.SUSPEND_TILL.equals(type) || TransactionType.RESUME_TILL.equals(type))
            {
                TillTransactionLocal local = getTillTransactionLocalHome().findByPrimaryKey(key);
                tillCloseTillID = local.getTenderRepositoryID();
                dto = new TillTransactionDTO(key, local.getTenderRepositoryID(), TillStatus.getStatus(
                        String.valueOf(local.getTillStatusCode())).toDisplayString(), local.getTransactionTimestamp());
            }
            else if (TransactionType.TILL_LOAN.equals(type))
            {
                // for cancel transactions no details get saved so nothing to retrieve
                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    TillLoanTransactionLocal local = getTillLoanTransactionLocalHome().findByPrimaryKey(key);
                    String tenderType = local.getTenderType();
                    getLogger().debug("Till loan: " + tenderType);
                    String reason = local.getLoanReasonCode();
                    //TillLoan Transaction does not have reason codes, in order to avoid displaying null on the screen, replace with empty String
                    if(reason == null)
                    {
                        reason = "";
                    }
                    dto = new TillLoanTransactionDTO(key, local.getRepositoryID(), reason,
                            tenderType, local.getLoanAmount());
                }
                else
                {
                    dto = new TillLoanTransactionDTO(key);
                }
            }
            else if (TransactionType.TILL_PICKUP.equals(type))
            {
                // for cancel transactions no details get saved so nothing to retrieve
                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    TillPickupTransactionLocal local = getTillPickupTransactionLocalHome().findByPrimaryKey(key);
                    String tenderType = local.getTenderType();
                    getLogger().debug("Till pickup: " + tenderType);
                    String reason = local.getPickupReasonCode();
                    //Till Pickup Transaction does not have reason codes, in order to avoid displaying null on the screen, replace with empty String

                    if(reason == null)
                    {
                        reason = "";
                    }
                    dto = new TillPickupTransactionDTO(key, local.getRepositoryID(), reason,
                            tenderType, local.getCountryCode(), local.getPickupAmount(), local.getExpectedAmount(),
                            local.getExpectedCount());
                }
                else
                {
                    dto = new TillPickupTransactionDTO(key);
                }
            }
            else if (TransactionType.REDEEM.equals(type))
            {
                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    // for cancel transactions no details get saved so nothing to retrieve
                    RedeemTransactionLocal local = getRedeemTransactionHome().findByPrimaryKey(key);
                    dto = createRedeemTransactionDTO(local);
                }
                else
                {
                    // for cancel transactions no details get saved so nothing to retrieve
                    dto = new RedeemTransactionDTO(key);
                }
            }
            else if (TransactionType.TILL_PAY_IN.equals(type) || TransactionType.TILL_PAY_OUT.equals(type))
            {
                dto = createTillPayInOutDTO(key);
                TillPayInOutTransactionDTO payInOutDTO= (TillPayInOutTransactionDTO)dto;

                String codeListDesc = null;
                if(TransactionType.TILL_PAY_IN.equals(type))
                {
                     codeListDesc = CodeListConstantsIfc.TILL_PAY_IN;
                }
                else
                {
                    codeListDesc = CodeListConstantsIfc.TILL_PAY_OUT;
                }

                if(payInOutDTO.getReasonCode()!=null && payInOutDTO.getReasonCode().length()>0)
                {
                    Collection reasonCodeDTOs = getCodeListService().getReasonCodeByStoreAndDescription(key.getStoreID(), codeListDesc, locale);
                    Iterator iter = reasonCodeDTOs.iterator();
                    while(iter.hasNext())
                    {
                        ReasonCode reasonCodeDTO = (ReasonCode) iter.next();
                        if(reasonCodeDTO.getEntryCode().equals(payInOutDTO.getReasonCode()))
                        {
                            payInOutDTO.setReasonCode(reasonCodeDTO.getCodeEntryDescription());
                            break;
                        }

                    }
                }
            }

            else if (TransactionType.INSTANT_CREDIT_ENROLLMENT.equals(type))
            {
                // for cancel transactions no details get saved so nothing to retrieve
                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    InstantCreditTransactionLocal local = getInstantCreditTransactionHome().findByPrimaryKey(key);
                    dto = createInstantCreditTransactionDTO(local);
                }
                else
                {
                    dto = new InstantCreditTransactionDTO(key);
                }
            }

            else if (TransactionType.HOUSE_ACCOUNT_PAYMENT.equals(type))
            {

                if (transactionLocal.getTransactionStatusCode() != TransactionConstantsIfc.STATUS_CANCELED)
                {
                    getLogger().debug("Retrieving payment transaction...");

                    RetailTransactionLocal local = getRetailTransactionHome().findByPrimaryKey(key);
                    dto = createSaleReturnTransactionDTO(local, locale);
                }
                else
                { // for cancel transactions no details get saved so nothing to retrieve
                    dto = new SaleReturnTransactionDTO(key);
                }
            }
            else if (TransactionType.BILL_PAY.equals(type))
            {

                RetailTransactionLocal local = getRetailTransactionHome().findByPrimaryKey(key);
                dto = createBillPayTransactionDTO(local, locale);

            }
            else
            {
                getLogger()
                        .error("TransactionType (" + type + ") unknown for key " + key + ". Returning generic info.");
                TransactionLocal t = getTransactionHome().findByPrimaryKey(key);
                dto = new TransactionDTO(key, transactionLocal.getType().toDisplayString(), transactionLocal
                        .getOperatorID(), transactionLocal.getTransactionEndDateTime(), "");

            }
            TransactionUtils.copyValues(transactionLocal, dto);

            /*
             * Because transactionLocal.getTenderRepositoryId() returns null for till reconcile, this workaround is in place
             * until the issue is resolved. The value in tillCloseTillID is set only for till open/close transactions.
             */
            if (tillCloseTillID != null)
            {
                dto.setTillID(tillCloseTillID);
            }

            dto.setRegisterId(key.getWorkstationID());
            setPathToStore(key.getHieararchyFunction(), dto, locale);
            //Monica
            setLoyaltyDetails(dto);
            setEmployeeName(dto);
            dto.setTransactionNumber(getTransactionKeyFormatter().format(key));
            dto.setReentryMode(transactionLocal.getTransactionReentryFlag());           
            return dto;
        }
        catch (ObjectNotFoundException e)
        {
            getLogger().debug("couldn't find the instance", e);
            throw e;
        }
        catch (FinderException e)
        {
            getLogger().error("Unknown error occurred", e);
            throw new EJBException(e);
        }
        catch (NamingException e)
        {
            getLogger().error("Unknown error occurred", e);
            throw new EJBException(e);
        }
        catch (RemoteException e)
        {
            getLogger().error("Unknown error occurred", e);
            throw new EJBException(e);
        }
        catch (CreateException e)
        {
            getLogger().error("Unknown error occurred", e);
            throw new EJBException(e);
        }
    }

    private RedeemTransactionDTO createRedeemTransactionDTO(RedeemTransactionLocal transaction) throws FinderException
    {
        try
        {
            getLogger().debug("Creating Redeem Transaction DTO " + transaction);
            TransactionKey key = (TransactionKey)transaction.getPrimaryKey();
            Collection<TenderLineItemDTO> tenderLineItems = getTenderService().getTenderItemsForTransaction(key);

            BigDecimal redeemAmount = transaction.getRedeemAmount();//new BigDecimal(50);
            BigDecimal redeemForeignAmount = transaction.getRedeemForeignAmount(); //new BigDecimal(50);
            BigDecimal amount = new BigDecimal("0.00");
            if (redeemAmount != amount)
            {
                amount = redeemAmount;
            }
            else if (redeemForeignAmount != amount)
            {
                amount = redeemForeignAmount;
            }
            RedeemTransactionDTO RedeemTransactionDTO = new RedeemTransactionDTO(key, tenderLineItems, TenderTypeMap
                    .getTenderTypeMap().getDescriptor(transaction.getTenderTypeCode()), transaction.getRedeemID(),
                    amount);
            return RedeemTransactionDTO;
        }
        catch (Exception e)
        {
            getLogger().error("", e);
            throw new FinderException(e.toString());
        }
    }

    private InstantCreditTransactionDTO createInstantCreditTransactionDTO(InstantCreditTransactionLocal transaction)
            throws FinderException
    {
        try
        {
            getLogger().debug("Creating Instant Credit Transaction DTO " + transaction);
            TransactionKey key = (TransactionKey)transaction.getPrimaryKey();
            InstantCreditTransactionDTO instantCrediTransactionDTO = new InstantCreditTransactionDTO(key, transaction
                    .getAuthorizationResponseCode());
            return instantCrediTransactionDTO;
        }
        catch (Exception e)
        {
            getLogger().error("", e);
            throw new FinderException(e.toString());
        }
    }

    /**
     * @param key
     * @return
     * @throws FinderException
     */
    private TransactionDTO createTillPayInOutDTO(TransactionKey key) throws FinderException
    {
        getLogger().debug("Creating TillPayInOutDTO...");

        FinancialAccountingTransactionLocalHome fatlh = getFinancialAccountingTransactionLocalHome();
        FinancialAccountingTransactionLocal fat = fatlh.findByPrimaryKey(key);

        FundReceiptTransactionLocalHome frtlh = getFundReceiptTransactionLocalHome();
        FundReceiptTransactionLocal frt = frtlh.findByPrimaryKey(key);

        String tmp = fat.getTenderTypeCode();
        getLogger().debug("TillPayInOutDTO: Tender type = " + tmp);

        return new TillPayInOutTransactionDTO(key, frt.getFundReceiptMonetaryAmount(), fat.getTenderTypeCode(), frt
                .getDisbursementReceiptReasonCode(), frt.getFullNameOfPayee(), frt.getAddressLine1(), frt
                .getAddressLine2(), frt.getApprovalCode(), frt.getComments());
    }

    /**
     * Find transactions that have not been post processed.
     *
     * FYI - This method
     * used to return TransactionDTO objects. However this was creating
     * pessimistic locks were causing problems with the new transactional
     * behavior of the PostProcessor service.
     *
     * @return Collection of transaction keys (TransactionKey).
     */
    public Collection<TransactionKey> getTransactionsForPostProcessing() throws FinderException
    {

        List<TransactionKey> transactionKeys = new ArrayList<TransactionKey>();
        Collection<TransactionLocal> transactionLocals = getTransactionHome().findByPostProcessingStatus(
                TransactionConstantsIfc.POST_PROCESSING_STATUS_UNPROCESSED);

        for (TransactionLocal transactionLocal : transactionLocals)
        {
            transactionKeys.add((TransactionKey)transactionLocal.getPrimaryKey());

        }

        return transactionKeys;
    }

    public void changePostProcessingStatus(TransactionKey key, int status) throws FinderException
    {
        TransactionLocal local = getTransactionHome().findByPrimaryKey(key);
        local.setTransactionPostProcessingStatusCode(status);
    }

    private LayawayTransactionDTO createLayawayTransactionDTO(RetailTransactionLocal transaction, Locale locale)
            throws FinderException
    {
        try
        {
            getLogger().debug("Creating a layaway dto from a retailtransactionlocal " + transaction);
            TransactionKey key = (TransactionKey)transaction.getPrimaryKey();
            Collection<PLULineItemDTO> items = getItemsForTransaction(key, false, locale);
            Collection<DiscountLineItemDTO> discountLineItems = getDiscountLineItemDTOs(key);
            Collection tenderItems = getTenderService().getTenderItemsForTransaction(key);
            BigDecimal unitsSold = calculateUnitsSold(items);
            TaxExemptionDTO taxExemptionDTO = getTaxExemptionRecordForTransaction(key, locale);
            LayawayTransactionDTO dto = new LayawayTransactionDTO(key, null, "", transaction.getCustomerId(), items,
                    discountLineItems, tenderItems,
                    "", //pathToStore filled in by TransactionServiceBean
                    transaction.getSalesTotal(), transaction.getDiscountTotal(), transaction.getTaxTotal(), transaction
                            .getNetTotal(), transaction.getTenderTotal(), unitsSold, SHIPPING_TYPE, SHIPPING_CHARGE,
                    transaction.getSuspendedTransactionReasonCode());
            dto.setPayment(getPayment(key));
            dto.setLayaway(getLayaway(transaction.getLayawayID(), key.getStoreID(), locale));
            dto.setTaxExemptionDTO(taxExemptionDTO);

            if (transaction.getCustomerId() != null)
                dto.setCustomerLinkedToTransaction(true);

            TransactionLocal transactionBase = getTransactionHome().findByPrimaryKey(key);
            dto.setReentryMode(transactionBase.getTransactionReentryFlag());

            return dto;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new FinderException(e.toString());
        }
    }

    /**
     * @param key
     * @return
     * @throws FinderException
     */
    private Collection<DiscountLineItemDTO> getDiscountLineItemDTOs(TransactionKey key) throws FinderException
    {
        Collection<DiscountLineItemLocal> discounts = getDiscountLineItemLocalHome().findByTransaction(key);
        List<DiscountLineItemDTO> discountDTOs = new LinkedList<DiscountLineItemDTO>();
        for (DiscountLineItemLocal local : discounts)
        {
            discountDTOs.add(local.toDTO());
        }
        return discountDTOs;
    }

    /**
     * Gets the collection of OrderRecipientDetails
     * @param key
     * @return Collection
     * @throws FinderException
     */
    private Collection<OrderRecipientDetailDTO> getOrderRecipientDetail(String key) throws FinderException
    {

        Collection<OrderRecipientDetailLocal> orderRecipients = getOrderRecipientDetailLocalHome().findByOrderId(key);
        List<OrderRecipientDetailDTO> orderRcipientDetailDTOs = new LinkedList<OrderRecipientDetailDTO>();
        for (OrderRecipientDetailLocal local : orderRecipients)
        {
            orderRcipientDetailDTOs.add(local.toDTO());
        }
        return orderRcipientDetailDTOs;
    }

    private OrderTransactionDTO createOrderTransactionDTO(RetailTransactionLocal transaction, Locale locale)
            throws FinderException
    {
        try
        {
            getLogger().debug("Creating an ordertxndto from a retail " + transaction);
            TransactionKey key = (TransactionKey)transaction.getPrimaryKey();
            Collection<PLULineItemDTO> items = getItemsForTransaction(key, false, locale);
            Collection<DiscountLineItemDTO> discountLineItems = getDiscountLineItemDTOs(key);
            Collection tenderItems = getTenderService().getTenderItemsForTransaction(key);
            BigDecimal unitsSold = calculateUnitsSold(items);
            TaxExemptionDTO taxExemptionDTO = getTaxExemptionRecordForTransaction(key, locale);
            OrderTransactionDTO dto = new OrderTransactionDTO(key, null, "", transaction.getCustomerId(), items,
                    discountLineItems, tenderItems,
                    "", //pathToStore filled in by TransactionServiceBean
                    transaction.getSalesTotal(), transaction.getDiscountTotal(), transaction.getTaxTotal(), transaction
                            .getNetTotal(), transaction.getTenderTotal(), unitsSold, SHIPPING_TYPE, SHIPPING_CHARGE,
                    transaction.getSuspendedTransactionReasonCode());
            dto.setPayment(getPayment(key));
            OrderDTO ordertDTOActual = getActualOrderDTO(getOrder(transaction.getOrderID(), key.getStoreID(), locale), items);
            dto.setOrder(ordertDTOActual);
            String orderRecipientDetailOrderId = getOrderRecipientDetailOrderId(key);
            Collection<OrderRecipientDetailDTO> orderRecipientDetailItems = getActualRecipientDetails(getOrderRecipientDetail(orderRecipientDetailOrderId), ordertDTOActual);
            dto.setTaxExemptionDTO(taxExemptionDTO);
            dto.setReceipentDetails(orderRecipientDetailItems);
            if (transaction.getCustomerId() != null)
                dto.setCustomerLinkedToTransaction(true);

            return dto;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }


    private Collection<OrderRecipientDetailDTO> getActualRecipientDetails(
            Collection<OrderRecipientDetailDTO> orderRecipientDetail, OrderDTO ordertDTOActual)
    {
        Collection<OrderRecipientDetailDTO> orderRecipientDetailTemp = new ArrayList<OrderRecipientDetailDTO>();
        Collection<OrderLineItemDTO> orderLineItemListActual = ordertDTOActual.getOrderLineItems();

        outer:
        for (OrderLineItemDTO lineItemDTO : orderLineItemListActual)
        {
            for ( OrderRecipientDetailDTO ordtlItem : orderRecipientDetail)
            {
                if(ordtlItem.getOrderRecipient() == lineItemDTO.getOrderRecipient())
                {
                    orderRecipientDetailTemp.add(ordtlItem);
                    break outer;
                }
            }
        }

        return orderRecipientDetailTemp;
    }

    private OrderDTO getActualOrderDTO(OrderDTO orderDTO, Collection<PLULineItemDTO> items)
    {
        Collection<OrderLineItemDTO> orderLineItemListActual = orderDTO.getOrderLineItems();
        Collection<OrderLineItemDTO> orderLineItemList = new ArrayList<OrderLineItemDTO>();

        for (PLULineItemDTO pItemDTO : items)
        {
            for ( OrderLineItemDTO lineItemDTO : orderLineItemListActual)
            {
                if(pItemDTO.getItemID().equals(lineItemDTO.getItemID()))
                {
                    orderLineItemList.add(lineItemDTO);
                }
            }
        }
        orderDTO.setOrderLineItems(orderLineItemList);
        return orderDTO;
    }

    private OrderDTO getOrder(String orderID, String storeID, Locale locale) throws FinderException
    {
        // Refer to SCR 2307 for the issue below.  For now, only returning one item from the collection
        Collection orders = getOrderHome().findByOrderId(orderID);
        if (orders.size() == 0)
        {
            throw new FinderException("No order found");
        }

        OrderLocal orderLocal = (OrderLocal)orders.iterator().next();
        OrderDTO order = orderLocal.toDTO();
        try
        {
            if(order.getOrderLocation()!=null)
            {
                Collection reasonCodeDTOs = getCodeListService().getReasonCodeByStoreAndDescription(storeID, CodeListConstantsIfc.CODE_LIST_ORDER_LOCATION_REASON_CODES, locale);
                Iterator iter = reasonCodeDTOs.iterator();
                while(iter.hasNext())
                {
                    ReasonCode reasonCodeDTO = (ReasonCode) iter.next();
                    if(reasonCodeDTO.getEntryCode().equals(order.getOrderLocation()))
                    {
                        order.setOrderLocation(reasonCodeDTO.getCodeEntryDescription());
                        break;
                    }
                }
            }
        }
        catch (RemoteException e)
        {
            getLogger().error("Unknown error occurred", e);
            throw new EJBException(e);
        }


        // add items to the order
        Collection<OrderLineItemLocal> localItems = getOrderLineItemLocalHome().findByOrderID(orderID);
        Collection<OrderLineItemDTO> localItemDTOs = new ArrayList<OrderLineItemDTO>(localItems.size());
        for (OrderLineItemLocal localItem : localItems)
        {
            localItemDTOs.add(localItem.toDTO());
        }
        order.setOrderLineItems(localItemDTOs);

        return order;
    }

    private LayawayDTO getLayaway(String layawayId, String storeID, Locale locale) throws FinderException
    {
        LayawayDTO layaway = null;
        try
        {
            LayawayLocal layawayLocal = getLayawayHome().findByPrimaryKey(layawayId);
            layaway = new LayawayDTO(layawayLocal.getLayawayID(), layawayLocal.getStoreID(), layawayLocal
                    .getOriginalTransactionWorkstationID(), layawayLocal.getOriginalTransactionBusinessDate(),
                    layawayLocal.getOriginalTransactionSequenceNumber(), layawayLocal.getCustomerID(), layawayLocal
                            .getStatus(), layawayLocal.getPreviousLayawayStatus(), layawayLocal
                            .getStatusModificationTimestamp(), layawayLocal.getExpirationDate(), layawayLocal
                            .getGracePeriodDate(), layawayLocal.getMinimumDownPayment(), layawayLocal
                            .getStorageLocation(), layawayLocal.getTotal(), layawayLocal.getCreationFee(), layawayLocal
                            .getDeletionFee(), layawayLocal.getTotalPaymentsCollected(), layawayLocal
                            .getCountPaymentsCollected(), layawayLocal.getBalanceDue(), layawayLocal
                            .getTrainingModeFlag(), layawayLocal.getLegalStatement(),
                    layawayLocal.getLegalStatement1(), layawayLocal.getLegalStatement2(), layawayLocal
                            .getLegalStatement3());

            if(layawayLocal.getStorageLocation()!=null)
            {
                Collection reasonCodeDTOs = getCodeListService().getReasonCodeByStoreAndDescription(storeID, CodeListConstantsIfc.CODE_LIST_LAYAWAY_LOCATION_REASON_CODES, locale);
                Iterator iter = reasonCodeDTOs.iterator();
                while(iter.hasNext())
                {
                    ReasonCode reasonCodeDTO = (ReasonCode) iter.next();
                    if(reasonCodeDTO.getEntryCode().equals(layawayLocal.getStorageLocation()))
                    {
                        layaway.setStorageLocation(reasonCodeDTO.getCodeEntryDescription());
                        break;
                    }
                }
            }
            else
            {
                layaway.setStorageLocation("");
            }

        }
        catch (FinderException e)
        {
            getLogger().error("Could not find layaway for a transaction that should have one - " + layawayId);
            layaway = new LayawayDTO(layawayId, "00000", "000", "1900-06-06", 1, "1", 1, 1, null, null, null,
                    new BigDecimal("0.00"), "", new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"),
                    new BigDecimal("0.00"), 0, new BigDecimal("0.00"), false, "", "", "", "");
        }
        catch (RemoteException e)
        {
            getLogger().error("Unknown error occurred", e);
            throw new EJBException(e);
        }

        return layaway;
    }

    private SaleReturnTransactionDTO createSaleReturnTransactionDTO(RetailTransactionLocal transaction, Locale locale)
            throws FinderException
    {
        try
        {
            if (getLogger().isDebugEnabled())
                getLogger().debug("Creating a dto from a salereturntransactionlocal " + transaction);

            TransactionKey key = (TransactionKey)transaction.getPrimaryKey();

            Collection<PLULineItemDTO> items = getItemsForTransaction(key, true, locale);
            Collection<DiscountLineItemDTO> discountLineItems = getDiscountLineItemDTOs(key);
            List<SaleReturnShippingRecordDTO> shippingRecords = getShippingRecordsForTransaction(key);
            Collection<TenderLineItemDTO> tenderItems = getTenderService().getTenderItemsForTransaction(key);
            TaxExemptionDTO taxExemptionDTO = getTaxExemptionRecordForTransaction(key, locale);

            for (TenderLineItemDTO tenderLineItemDTO : tenderItems)
            {
                if (tenderLineItemDTO instanceof GiftCardLineItemDTO)
                {
                    GiftCardLineItemDTO giftCardLineItemDTO = (GiftCardLineItemDTO)tenderLineItemDTO;
                    String serialNumber = giftCardLineItemDTO.getGiftCardSerialNumber();
                    int lineItemSequenceNumber = giftCardLineItemDTO.getLineItemSequenceNumber();
                    GiftCardPK giftCardPK = new GiftCardPK(serialNumber, key.getStoreID(),
                            key.getFormattedDateString(), key.getWorkstationID(), key.getSequenceNumber(),
                            lineItemSequenceNumber);
                    String state = "";
                    try
                    {
                        GiftCardLocal local = getGiftCardLocalHome().findByPrimaryKey(giftCardPK);
                        state = local.getCardRequestType();
                    }
                    catch (FinderException fe)
                    {
                    }

                    giftCardLineItemDTO.setState(state);
                }
            }

            BigDecimal unitsSold = calculateUnitsSold(items);
            SaleReturnTransactionDTO dto = new SaleReturnTransactionDTO(key, null, "", transaction.getCustomerId(),
                    items, shippingRecords, discountLineItems, tenderItems, "",
                    //pathToStore filled in by TransactionServiceBean
                    transaction.getSalesTotal(), transaction.getDiscountTotal(), transaction.getTaxTotal(), transaction
                            .getNetTotal(), transaction.getTenderTotal(), unitsSold, SHIPPING_TYPE, SHIPPING_CHARGE,
                    transaction.getSuspendedTransactionReasonCode());
            dto.setPayment(getPayment(key));
            dto.setTaxExemptionDTO(taxExemptionDTO);
            dto.setExternalOrderNumber(transaction.getExternalOrderNumber());

            dto.setVoided(isVoided(key));
            if (transaction.getCustomerId() != null)
                dto.setCustomerLinkedToTransaction(true);

            TransactionLocal transactionBase = getTransactionHome().findByPrimaryKey(key);
            dto.setReentryMode(transactionBase.getTransactionReentryFlag());

            return dto;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new FinderException(e.toString());
        }
    }

    private PaymentDTO getPayment(TransactionKey key)
    {
        PaymentDTO payment = null;
        try
        {
            PaymentLocal paymentLocal = getPaymentHome().findByPrimaryKey(key);
            payment = new PaymentDTO(key, paymentLocal.getStoreID(), paymentLocal.getWorkstationID(), paymentLocal
                    .getBusinessDay(), paymentLocal.getSequenceNumber(), paymentLocal.getCustomerAccountID(),
                    paymentLocal.getEncryptedCustomerAccountID(), paymentLocal.getMaskedCustomerAccountID(), paymentLocal
                            .getCustomerAccountCode(), paymentLocal.getAmount(), paymentLocal
                            .getBalanceDueAgainstAccount());
        }
        catch (FinderException finderException)
        {
            //no payment. ok.
        }
        return payment;
    }

    /**
     * Added an overloaded method to filter out deleted items from transactions.
     *
     * @param key
     * @param omitDeletedItems - Boolean to specify if the deleted items needs to be deleted.
     * @return
     * @throws FinderException
     */
    private List getItemsForTransaction(TransactionKey key, boolean omitDeletedItems, Locale locale)
            throws FinderException
    {
        if (getLogger().isDebugEnabled())
            getLogger().debug("getting line items for transaction key " + key);

        List<PLULineItemDTO> list = new ArrayList<PLULineItemDTO>();
        Collection<SaleReturnLineItemLocal> items = getSaleReturnLineItemLocalHome().findByTransaction(key);

        if (getLogger().isDebugEnabled())
        {
            getLogger().debug("getting line items for transaction " + items);
        }

        // TODO: Where does itemTransactionDiscountAmount come from?
        // old BO reference: oracle.retail.stores.domain.lineitem.ItemPrice
        BigDecimal itemTransactionDiscountAmount = BigDecimal.ZERO;

        for (SaleReturnLineItemLocal srli : items)
        {
            // convert locals to DTOs and store in collection
            SaleReturnLineItemDTO srliDTO = srli.toDTO();

            //Check if the deleted items needs to be omitted.
            if (omitDeletedItems && srliDTO.isVoided())
            {
                continue;
            }

            // If the item is a kit header,then need not add to the list.
            if (srliDTO.isKitHeader())
            {
                continue;
            }

            // retrieve tax information
            Collection<SaleReturnLineItemTaxLocal> taxItems = getSaleReturnLineItemTaxLocalHome().findBySaleReturnLineItem(key, srliDTO.getItemSequenceNumber());
            SaleReturnLineItemTaxDTO[] srliTaxDTO = new SaleReturnLineItemTaxDTO[taxItems.size()];
            int i = 0;
            for (SaleReturnLineItemTaxLocal txli : taxItems)
            {
                srliTaxDTO[i++] = txli.toDTO();
            }
            srliDTO.setTaxInformation(srliTaxDTO);

            // retrieve price modifier
            Collection priceModifiers = getPriceModifiers(srli);

            // create plu line item dto
            String itemDescription = getSaleReturnLineItemDescription(srliDTO, locale);
            boolean taxable = srliDTO.isTaxable();
            String department = srliDTO.getDepartmentID();
            boolean giftCard = srliDTO.isGiftCard();
            int itemType = srliDTO.getItemType();
            boolean giftCertificate = srliDTO.isGiftCertificate();
            String merchandiseHierarchyGroupId = srliDTO.getMerchandiseHierarchyGroupID();

            PLULineItemDTO dto = new PLULineItemDTO(srliDTO, itemType, itemTransactionDiscountAmount,
                                    itemDescription, taxable, department, giftCard, giftCertificate,
                                    priceModifiers, merchandiseHierarchyGroupId);

            // set tax mode
            String taxMode = getTaxCategory(TaxConstantsIfc.TAX_MODE_NON_TAXABLE);
            if (dto.isTaxable())
            {
                taxMode = getSellingTaxMode(srliDTO.getStoreID(),
                        srliDTO.getWorkstationID(),
                        srliDTO.getBusinessDate(),
                        srliDTO.getTransactionSequenceNumber(),
                        srliDTO.getItemSequenceNumber());
            }
            dto.setTaxMode(taxMode);

            list.add(dto);
        }
        return list;
    }

    /**
     * @param storeID    Store Id of the particular transaction
     * @param workstationID    Workstation Id of the particular transaction
     * @param businessDate    Business Date of the particular transaction
     * @param sequenceNumber Transaction Sequence Number of the particular transaction
     * @returns Tax Mode in form a character. This specifies the category of tax during this transaction
     *
     * getSellingTaxMode(String storeID, String workstationID, String businessDate, int sequenceNumber)
     * method used to return the transaction mode.
     */
    public String getSellingTaxMode(String storeID, String workstationID, String businessDate, int sequenceNumber,
            int itemSequenceNumber)
    {
        int taxMode = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = getDBUtils().getConnection();
            StringBuilder query = new StringBuilder("SELECT TX_MOD FROM TR_LTM_SLS_RTN_TX WHERE ID_STR_RT=?");
            query.append(" AND ID_WS = ? AND DC_DY_BSN = ? AND AI_TRN = ? AND AI_LN_ITM = ?");
            pStatement = connection.prepareStatement(query.toString());
            int n = 1;
            pStatement.setString(n++, storeID);
            pStatement.setString(n++, workstationID);
            pStatement.setString(n++, businessDate);
            pStatement.setInt(n++, sequenceNumber);
            pStatement.setInt(n++, itemSequenceNumber);
            resultSet = pStatement.executeQuery();
            if (resultSet.next())
            {
                taxMode = resultSet.getInt(1);
            }

        }
        catch (SQLException sqle)
        {
            getLogger().debug("Exception during reading getSellingTaxMode", sqle);
            throw new EJBException("Exception during reading getSellingTaxMode");
        }

    finally
        {
      getDBUtils().close(connection, pStatement, resultSet);
        }

        return getTaxCategory(taxMode);
    }

    /**
     * @param taxMode is the code of the transaction mode in form of integer
     * @return category of transaction in form of a character.
     * All the available transaction categories are specified in TaxConstantsIfc interface
     *
     */
    private String getTaxCategory(int taxMode)
    {
        return TaxConstantsIfc.TAX_MODE_CHAR[taxMode];
    }

    /**
     * @param srli
     * @return
     * @throws FinderException
     */
    private Collection<PriceModifierDTO> getPriceModifiers(SaleReturnLineItemLocal srli) throws FinderException
    {
        Collection<PriceModifierDTO> priceModifierDTOs = new LinkedList<PriceModifierDTO>();
        Collection<PriceModifierLocal> priceModifiers = getPriceModifierLocalHome().findBySaleReturnLineItemKey(
                srli.getKey());

        for (PriceModifierLocal priceModifier : priceModifiers)
        {
            priceModifierDTOs.add(priceModifier.toDTO());
        }
        return priceModifierDTOs;
    }

    /**
     * Gets the Shipping Records for the transaction.
     *
     * @param key
     * @return a list of SaleReturnShippingRecordDTO objects.
     * @throws FinderException
     */
    private List<SaleReturnShippingRecordDTO> getShippingRecordsForTransaction(TransactionKey key)
            throws FinderException
    {
        getLogger().debug("Getting shipping records for transaction key " + key);

        Collection<SaleReturnShippingRecordLocal> records = getSaleReturnShippingRecordLocalHome().findByTransaction(
                key);
        List<SaleReturnShippingRecordDTO> list = new ArrayList<SaleReturnShippingRecordDTO>(records.size());
        for (SaleReturnShippingRecordLocal shippingRecordLocal : records)
        {
            SaleReturnShippingRecordDTO srsrDTO = shippingRecordLocal.toDTO();

            // To retrive all tax records for each Shipping Record
            Collection<SaleReturnShippingRecordTaxLocal> taxItems = getSaleReturnShippingRecordTaxLocalHome()
                    .findBySendLabelCount(key, srsrDTO.getSendLabelCount());
            SaleReturnShippingRecordTaxDTO[] srsrTaxDTO = new SaleReturnShippingRecordTaxDTO[taxItems.size()];
            int i = 0;
            for (SaleReturnShippingRecordTaxLocal SaleReturnShippingRecordTaxLocal : taxItems)
            {
                srsrTaxDTO[i++] = SaleReturnShippingRecordTaxLocal.toDTO();
            }
            srsrDTO.setTaxInformation(srsrTaxDTO);
            // End of tax information retrieval

            list.add(srsrDTO);
        }

        return list;
    }

    /**
     * Get tax exemption info for a transaction
     *
     *  @param key Transaction key
     *  @return TaxExemptionDTO
     *  @throws FinderException
     */
    private TaxExemptionDTO getTaxExemptionRecordForTransaction(TransactionKey key, Locale locale)
            throws FinderException
    {
        getLogger().debug("Getting Tax Exemption records for transaction key " + key);

        TaxExemptionDTO taxExemptionDTO = null;
        try
        {
            TaxExemptionLocal localTax = getTaxExemptionHome().findByPrimaryKey(key);
            taxExemptionDTO = new TaxExemptionDTO(localTax.getCertificateNumber(), localTax.getReasonCode());
            if (localTax.getReasonCode() != null && localTax.getReasonCode().length() > 0)
            {
                try
                {

                    Collection<ReasonCode> reasonCodes = getCodeListService().getReasonCodeByStoreAndDescription(
                            key.getStoreID(), CodeListConstantsIfc.TAX_EXEMPT, locale);
                    for (ReasonCode reasonCode : reasonCodes)
                    {
                        if (reasonCode.getEntryCode().equals(localTax.getReasonCode()))
                        {
                            taxExemptionDTO.setReasonCodeDescription(reasonCode.getCodeEntryDescription());
                            break;
                        }
                    }
                }
                catch (RemoteException remoteException)
                {
                    getLogger().error(
                            getClass().getName() + "getTaxExemptionRecordForTransaction:Remote Exception"
                                    + remoteException);
                }
            }
        }
        // Not an error condition, perfectly valid and expected
        catch (ObjectNotFoundException onfe)
        {
            taxExemptionDTO = null;
        }
        return taxExemptionDTO;
    }

    /**
     * Calculates the number of units sold from a collection of SaleReturnLineItemDTOs
     *
     * @param saleReturnLineItems
     * @return BigDecimal
     */
    private BigDecimal calculateUnitsSold(Collection<PLULineItemDTO> saleReturnLineItems)
    {
        BigDecimal unitsSold = BigDecimal.ZERO;
        for (PLULineItemDTO lineItem : saleReturnLineItems)
        {
            unitsSold = unitsSold.add(lineItem.getItemQuantity());
        }
        return unitsSold;
    }

    public TransactionKey createRetailTransaction(String storeID, String workstationID, int sequenceNumber,
            String businessDay, TransactionType type, String operatorId, Date transactionEndDateTime,
            String customerId, BigDecimal salesTotal, BigDecimal discountTotal, BigDecimal taxTotal,
            BigDecimal netTotal, BigDecimal inclusiveTax, BigDecimal tenderTotal, String shippingType,
            BigDecimal shippingCharge) throws TransactionCreationException
    {
        try
        {
            TransactionLocal local = getTransactionHome().create(storeID, workstationID, businessDay, sequenceNumber,
                    operatorId, type.toString(), transactionEndDateTime, transactionEndDateTime);
            RetailTransactionLocal retailTransactionLocal = getRetailTransactionHome().create(storeID, workstationID,
                    businessDay, sequenceNumber, customerId, "", "", "", salesTotal, discountTotal, taxTotal, netTotal,
                    inclusiveTax, tenderTotal, "", "", "", "", "", "", "", new Date(), new Date());
            return (TransactionKey)local.getPrimaryKey();
        }
        catch (CreateException ce)
        {
            throw new TransactionCreationException("failed to create transaction", ce,
                    TransactionCreationException.ERROR_KEY);
        }
    }

    public TransactionKey createStoreOpenCloseTransaction(String storeID, String workstationID, Date businessDay,
            String operatorID, TransactionType type) throws TransactionCreationException,
            NextTransactionSeqNumNotFoundException
    {
        try
        {
            int nextSequenceNumber = getNextSequenceNumber(storeID, workstationID);

            //Update the business day
            if (type == TransactionType.OPEN_STORE)
            {
                updateBusinessDay(storeID, workstationID, businessDay);
            }

            StoreTransactionLocal store = getStoreTransactionHome().create(storeID, workstationID, businessDay,
                    nextSequenceNumber, operatorID, type);
            Date date = new Date();
            getControlTransactionHome().create(storeID, workstationID, store.getBusinessDay(),
                    store.getSequenceNumber(), type.toString());
            getTransactionHome().create(storeID, workstationID, store.getBusinessDay(), nextSequenceNumber, operatorID,
                    type.toString(), date, date);
            return (TransactionKey)store.getPrimaryKey();
        }
        catch (CreateException ce)
        {
            throw new TransactionCreationException("failed to create transaction", ce,
                    TransactionCreationException.ERROR_KEY);
        }
    }

    public TransactionKey createRegisterTransaction(String storeID, String workstationID, Date businessDay,
            String operatorID, TransactionType type, WorkstationIfc workstation, boolean countFloatAtOpen,
            boolean countFloatAtClose) throws TransactionCreationException, NextTransactionSeqNumNotFoundException
    {
        try
        {
            int nextSequenceNumber = getNextSequenceNumber(storeID, workstationID);
            int terminalStatusCode = getStatusFromTransactionType(type);
            Date date = new Date();
            RegisterTransactionLocal register = getRegisterTransactionHome().create(storeID, workstationID,
                    DateTimeUtils.getInstance().formatBusinessDate(businessDay), nextSequenceNumber, operatorID,
                    type.toString(), terminalStatusCode, workstation.getWorkstationID(), workstation.getTransactionSequenceNumber(),
                    date, mapFlagToBoolean(workstation.getTrainingModeFlag()), null,
                    mapFlagToBoolean(workstation.getAccountabilityCode()), workstation.getTillFloatAmount(), false,
                    countFloatAtOpen, countFloatAtClose, false, false, false, workstation.getTillReconcileFlag(),
                    mapFlagToBoolean(workstation.getWorkstationActiveStatusFlag()), date, date);
            getControlTransactionHome().create(storeID, workstationID, register.getBusinessDay(),
                    register.getSequenceNumber(), type.toString());
            getTransactionHome().create(storeID, workstationID, register.getBusinessDay(), nextSequenceNumber,
                    operatorID, type.toString(), date, date);
            return (TransactionKey)register.getPrimaryKey();
        }
        catch (CreateException ce)
        {
            throw new TransactionCreationException("failed to create transaction", ce,
                    TransactionCreationException.ERROR_KEY);
        }
    }

    /**
     * @deprecated As of 13.3 use {@link #createTillTransaction(String, String, String, String, String, Date, TransactionType, int)
     */
    public TransactionKey createTillTransaction(String storeID, String workstationID, String tenderRepositoryID,
            String operatorID, Date businessDay, TransactionType type, int tillStatusCode)
            throws TransactionCreationException, NextTransactionSeqNumNotFoundException
    {
        return createTillTransaction(storeID, workstationID,
                                              WorkstationDrawerIfc.DRAWER_PRIMARY,
                                              tenderRepositoryID, operatorID, businessDay, type, tillStatusCode);
    }

    public TransactionKey createTillTransaction(String storeID, String workstationID, String recordedWorkstationID, String tenderRepositoryID,
            String operatorID, Date businessDay, TransactionType type, int tillStatusCode)
            throws TransactionCreationException, NextTransactionSeqNumNotFoundException
    {
        try
        {
            int nextSequenceNumber = getNextSequenceNumber(storeID, workstationID);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 0);
            Date date = cal.getTime();

            TillTransactionLocal tillTransactionLocal = getTillTransactionLocalHome().create(storeID, workstationID,
                    DateTimeUtils.getInstance().formatBusinessDate(businessDay), nextSequenceNumber, operatorID,
                    tenderRepositoryID, type.toString(), date, recordedWorkstationID, date, date, tillStatusCode);

            getControlTransactionHome().create(storeID, workstationID,
                    DateTimeUtils.getInstance().formatBusinessDate(businessDay), nextSequenceNumber, type.toString());

            getTransactionHome().create(storeID, workstationID,
                    DateTimeUtils.getInstance().formatBusinessDate(businessDay), nextSequenceNumber, operatorID,
                    type.toString(), date, date, date, false, false, 2, operatorID, "", 0, tenderRepositoryID, new Date(), new Date(),-1,
                    "-1", 0, false);

            TransactionKey key = (TransactionKey)tillTransactionLocal.getPrimaryKey();
            return key;
        }
        catch (CreateException ce)
        {
            throw new TransactionCreationException("failed to create transaction", ce,
                    TransactionCreationException.ERROR_KEY);
        }
    }

    /**
     * Maps transaction type to a status
     *
     * @param type
     * @return the status for the transaction type.
     */
    private int getStatusFromTransactionType(TransactionType type)
    {
        int retval = -1;

        if (type.equals(TransactionType.CLOSE_REGISTER))
        {
            retval = WorkstationIfc.STATUS_CLOSED;
        }
        else if (type.equals(TransactionType.OPEN_REGISTER))
        {
            retval = WorkstationIfc.STATUS_OPEN;
        }
        else if (type.equals(TransactionType.CLOSE_TILL))
        {
            retval = TillIfc.STATUS_CLOSED;
        }
        else if (type.equals(TransactionType.OPEN_TILL))
        {
            retval = TillIfc.STATUS_OPEN;
        }
        else if (type.equals(TransactionType.SUSPEND_TILL))
        {
            retval = TillIfc.STATUS_CLOSED;
        }
        else if (type.equals(TransactionType.RESUME_TILL))
        {
            retval = TillIfc.STATUS_OPEN;
        }

        return retval;
    }

    /**
     * Maps a flag value to a boolean.
     * @param flag
     * @return <ocde>true<code> if the flag value is 0 otherwise false.
     */
    private boolean mapFlagToBoolean(String flag)
    {
        return !flag.equals("0");
    }

    /**
     * Create a Store transaction
     *
     * @param storeID
     * @param workstationID
     * @param businessDay
     * @param operatorID
     * @param type
     * @return @throws
     *         NextTransactionSeqNumNotFoundException
     */
    public TransactionKey createBankDepositTransaction(String storeID, String workstationID, Date businessDay,
            String operatorID, TransactionType type) throws TransactionCreationException,
            NextTransactionSeqNumNotFoundException
    {
        Date sysDate = new Date();
        TransactionLocal local;
        try
        {
            int nextSequenceNumber = getNextSequenceNumber(storeID, workstationID);
            String businessDateStr = DateTimeUtils.getInstance().formatBusinessDate(businessDay);
            local = getTransactionHome().create(storeID, workstationID, businessDateStr, nextSequenceNumber,
                    operatorID, type.toString(), sysDate, sysDate);
            getControlTransactionHome().create(storeID, workstationID, businessDateStr, nextSequenceNumber,
                    type.toString());
        }
        catch (CreateException ce)
        {
            throw new TransactionCreationException("Failed to create bank deposit transaction", ce,
                    TransactionCreationException.ERROR_KEY);
        }
        return (TransactionKey)local.getPrimaryKey();
    }

    /**
     * Builds a path to store String based on storeID, workstationID from TransactionDTO.
     * Sets the pathToStore value in the dto with the created String.
     *
     * @param hierarchyFunction
     * @param dto
     * @throws CreateException
     * @throws NamingException
     * @throws RemoteException
     * @throws FinderException
     */
    void setPathToStore(int hierarchyFunction, TransactionDTO dto, Locale locale) throws CreateException,
            NamingException, RemoteException, FinderException
    {
        String storeID = dto.getStoreID();
        StoreDirectoryRemote storeHierarchy = getStoreDirectoryService();
        String delim = " >> ";
        StringBuilder pathToStore = new StringBuilder(storeHierarchy.getPathToStore(hierarchyFunction, storeID, delim,
                locale));
        pathToStore.append(delim).append(storeID).append("-").append(getStoreName(storeID, locale)).append(delim)
                .append(
                        LanguageResourceBundleUtil.getString(ConstantsIfc.BASE_BUNDLE_NAME, locale,
                                ConstantsIfc.PATH_TO_STORE_REGISTER_KEY)).append(dto.getRegisterId());
        dto.setPathToStore(pathToStore.toString());
    }

    /**
     * Retrieves a set of transaction information given search criteria as input.
     *
     * @param storeSelectionCriteria
     * @param storeSelectionCriteria
     * @param startIndex
     * @return returnLimit
     */
    public EJournalSearchResultDTO getEJournals(StoreSelectionCriteria storeSelectionCriteria,
            EJournalCriteria ejournalCriteria, int startIndex, int pageSize) throws SearchResultSizeExceededException
    {
        EJournalSearchResultDTO results = new EJournalSearchResultDTO();

        // determine size of results list
        if (pageSize <= 0)
        {
            // return empty list if endIndex is < startIndex
            return results;
        }
        try
        {
            Collection<EJournalSearchDTO> list = new ArrayList<EJournalSearchDTO>(pageSize);
            StoreSelectionCriteria storeSearchCriteria = getStoreSearchCriteria(storeSelectionCriteria, TransactionServiceIfc.REQUEST_TYPE.USER_REQUEST);
            int totalCount = findEJournalByCriteria(list, storeSearchCriteria, ejournalCriteria, startIndex, pageSize);
            results.setEJournals(list.toArray(new EJournalSearchDTO[list.size()]));
            results.setTotalResultCount(totalCount);
            results.setStartRowIndex(startIndex);
            results.setPageSize(pageSize);
        }
        catch (RemoteException e)
        {
            throw new EJBException(e);
        }
        return results;
    }

    /**
     * Finds a Collection of Transaction entities based on SearchCriteria. Use
     * {@link #findEJournalByCriteria(Collection, String, EJournalCriteria, int, int)}
     * instead to obtain the size of the result set found.
     *
     * @param storeJoinKey     - the set of storeIDs to include in the query
     * @param ejournalCriteria - an object containing transaction search criteria
     * @param startIndex       - the starting index of the result set to return
     * @param pageSize         - the number of entities to return
     * @throws SearchResultSizeExceededException
     */
    public Collection<EJournalSearchDTO> findEJournalByCriteria(String storeJoinKey,
            EJournalCriteria ejournalCriteria, int startIndex, int pageSize)
            throws SearchResultSizeExceededException
    {
        Collection<EJournalSearchDTO> results = new ArrayList<EJournalSearchDTO>(pageSize);
        findEJournalByCriteria(results, storeJoinKey, ejournalCriteria, startIndex, pageSize);
        return results;
    }


    /**
     * @deprecated as of 13.3. use {@link #findEJournalByCriteria(Collection, StoreSelectionCriteria, EJournalCriteria, int, int)} instead.
     */
    public int findEJournalByCriteria(Collection<EJournalSearchDTO> dtos, String storeJoinKey,
            EJournalCriteria ejournalCriteria, int startIndex, int pageSize)
            throws SearchResultSizeExceededException
    {
        return findEJournalByCriteria(dtos, (StoreSelectionCriteria)null, ejournalCriteria, startIndex, pageSize);
    }

    /**
     * Query for EJournals the match the specified criteria. Returns the total number
     * of journals that match the queury. The results are added to the
     * <code>dtos</code> parameter.
     *
     * @param dtos
     * @param storeSearchCriteria
     * @param ejournalCriteria
     * @param startIndex
     * @param pageSize
     * @return total number of journals that match the queury
     * @throws SearchResultSizeExceededException if total num is larger than "MaximumSearchResults" parameter
     */
    public int findEJournalByCriteria(Collection<EJournalSearchDTO> dtos,
            StoreSelectionCriteria storeSearchCriteria, EJournalCriteria ejournalCriteria,
            int startIndex, int pageSize)
            throws SearchResultSizeExceededException
    {
        // determine size of results list
        if (pageSize <= 0)
        {
            // return empty list if endIndex is < startIndex
            return 0;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int endIndex = startIndex + pageSize - 1;
        try
        {
            conn = getDBUtils().getConnection();
            StringBuilder query = new StringBuilder(
                    "SELECT DISTINCT JL_ENR.ID_STR_RT AS ID_STR_RT, JL_ENR.ID_WS AS ID_WS, JL_ENR.DC_DY_BSN AS DC_DY_BSN, JL_ENR.AI_TRN AS AI_TRN, JL_ENR.TS_JRL_BGN AS TS_JRL_BGN ");
            String orderBy = " ORDER BY ID_STR_RT, ID_WS, DC_DY_BSN DESC, AI_TRN DESC, TS_JRL_BGN ";
            ps = createQuery(conn, query, storeSearchCriteria, ejournalCriteria, orderBy);
            rs = ps.executeQuery();

            // spin past everything before startIndex
            int i = 1;
            while (i < startIndex)
            {
                if (rs.next())
                {
                    i++;
                }
                else
                {
                    break;
                }
            }

            // only process result set until endIndex is hit
            int j=i;
            while (rs.next())
            {
                if(j <= endIndex)
                {
                    dtos.add(new EJournalSearchDTO(new TransactionKey(
                            rs.getString("ID_STR_RT"),
                            rs.getString("ID_WS"),
                            rs.getInt("AI_TRN"),
                            rs.getString("DC_DY_BSN")),
                            getDBUtils().getDateFromResultSet(rs, "TS_JRL_BGN"),
                            getStoreName(rs.getString("ID_STR_RT"),
                            ejournalCriteria.getLocale())));
                }
                j++;
            }

            // if the total results are larger than the "MaximumSearchResults" parameter
            // then throw the SearchResultSizeExceededException.
            if (j > getMaximumResults())
            {
                throw new SearchResultSizeExceededException(j);
            }

            // return total size of query results, which is one less than the index.
            return (j - 1);
        }
        catch (SQLException e)
        {
            getLogger().error(e);
            throw new EJBException(e);
        }
        catch (ParseException e)
        {
            getLogger().error(e);
            throw new EJBException(e);
        }
        catch (FinderException e)
        {
            getLogger().error(e);
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }
    }

    /**
     * @see oracle.retail.stores.commerceservices.transaction.TransactionServiceIfc#getTransactionHeader(String)
     */
    public String getTransactionHeader(String searchCriteria) throws TransactionRetrievalException
    {
        return getTransactionHeader(searchCriteria, TransactionServiceIfc.REQUEST_TYPE.USER_REQUEST);
    }

    /**
     * @see oracle.retail.stores.commerceservices.transaction.TransactionServiceIfc#getTransactionHeader(String, boolean)
     */
    public String getTransactionHeader(String searchCriteria, TransactionServiceIfc.REQUEST_TYPE requestType) throws TransactionRetrievalException
    {
        try
        {
            return getTransactionHeaderUtility().getTransactionHeader(searchCriteria, this, requestType);
        }
        catch (RemoteException e)
        {
            getLogger().error("Error getting the transaction header via transHeaderUtility", e);
            throw new EJBException(e);
        }
    }

    /**
     * @see oracle.retail.stores.commerceservices.transaction.TransactionServiceIfc#getTransactionPOSLog(String)
     */
    public String getTransactionPOSLog(String searchCriteriaString) throws TransactionRetrievalException,
            SearchResultSizeExceededException
    {
        // todo: change this to use an instance identified by the EJB parameter using the method getPOSLogTransactionUtility()
        return getPOSLogTransactionUtility().getTransactionPOSLog(searchCriteriaString, getLogger(),
                getSchemaTypeFactoryName());
    }

    /*
     *   The purpose of the following methods is to provide transaction related information to store systems,
     *   i.e. the POS
     */
    /**
     * This method determines if a transaction has already been voided
     * @param searchCriteriaString
     * @return Boolean indicating if transaction has already been voided
     * @throws TransactionRetrievalException
     */
    public Boolean isVoidedTransaction(String searchCriteriaString) throws RemoteException,
            TransactionRetrievalException
    {
        getLogger().debug("Start isVoidedTransaction");

        QueryTransactionRequest request = null;
        try
        {
            request = AbstractTransactionUtility.makeSearchRequest(searchCriteriaString, getJAXBContext());
        }
        catch (JAXBException e)
        {
            getLogger().error("Invalid Search Criteria: caught JAXBException: ", e);
            throw new TransactionRetrievalException("Invalid Search Criteria: caught JAXBException: ", e.getMessage());
        }

        Boolean voided = new Boolean(false);
        Connection conn = null;
        try
        {
            // Get connection and statement
            conn = getDBUtils().getConnection();
            String sql = "SELECT AI_TRN FROM TR_VD_PST WHERE ID_STR_RT = ? AND ID_WS = ? AND DC_DY_BSN = ? AND AI_TRN_VD = ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            // Add column values to the statement.
            statement.setObject(1, request.getTransactionID().getStoreID());
            statement.setObject(2, request.getTransactionID().getWorkstationID());
            Calendar cal = request.getTransactionID().getBusinessDate().toGregorianCalendar();
            statement.setObject(3, JdbcUtilities.YYYYMMDD_DATE_FORMAT.format(cal.getTime()));
            statement.setObject(4, request.getTransactionID().getSequenceNumber().toString());

            // Get the result set
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                // get the sequenceNumber to verify the read did not fail;
                // increment it to remomve the warning.
                long sequenceNumber = rs.getLong(1);
                sequenceNumber++;
                voided = new Boolean(true);
            }
        }
        catch (Exception e)
        {
            getLogger().error("Failed to read voided transanction: ", e);
            throw new TransactionRetrievalException("Failed to read voided transanction: ", e.getMessage());
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (Exception e)
            {
                getLogger().warn("Exception closing the connection: " + e.getMessage());
            }
        }

        return voided;
    }

    /**
     * Gets a list of orders and emails in Replication XML format
     * @param xmlRequest XML request string
     * @return String DataReplication XML
     * @exception RemoteException
     */
    public String getAlertListReplication(String xmlRequest) throws RemoteException
    {
        Connection conn = null;
        EntityBatchIfc batch = null;

        try
        {
            OrderSearchCriteria criteria = getOrderSearchCriteria(xmlRequest);
            EntitySearchIfc search = getOrderSearchEntity(criteria.getOrderSearchInfo());
            EntityIfc entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                    .getEntityInstance("OrderTable");

            conn = getDBUtils().getConnection();
            entityReaderCatalog.readEntity(entity, search, conn);

            batch = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                    .getEntityBatchInstance();
            batch.addEntity(entity);

            entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory().getEntityInstance(
                    "EMessage");
            search = getEMessageSearchEntity(criteria.getOrderSearchInfo());
            entityReaderCatalog.readEntity(entity, search, conn);

            batch.addEntity(entity);
        }
        catch (Exception e)
        {
            getLogger().error("Failed to read order using DataReplication: ", e);
            throw new RemoteException("Failed to read order using DataReplication: ", e);
        }
        finally
        {
            try
            {
                // Close the db connection.
                conn.close();
            }
            catch (Exception e)
            {
                getLogger().warn("Exception closing the connection: " + e.getMessage());
            }
        }

        return buildReplicationXMLString(batch);
    }

    /**
     * Converts the XML order request into a JAXB object
     * @param xmlRequest
     * @return OrderSearchCriteria
     * @throws RemoteException
     */
    protected OrderSearchCriteria getOrderSearchCriteria(String xmlRequest) throws RemoteException
    {
        OrderSearchCriteria criteria = null;
        try
        {
            JAXBContext ctx = getJAXBContext();
            javax.xml.bind.Unmarshaller um = ctx.createUnmarshaller();
            Object resp = um.unmarshal(new StreamSource(new StringReader(xmlRequest)));
            if (!(resp instanceof OrderSearchCriteria))
            {
                getLogger().error("Expecting an OrderSearchCriteria object; received: " + resp.getClass().getName());
                throw new RemoteException("Invalid OrderSearchCriteria.");
            }
            criteria = (OrderSearchCriteria)resp;
        }
        catch (JAXBException e)
        {
            getLogger().error("Exception unmarshalling a order search criteria.", e);
            throw new RemoteException("Exception unmarshalling an OrderSearchCriteria.", e);
        }

        return criteria;
    }

    /**
     * Gets EMessages in Replication XML format
     * @param xmlRequest XML request string
     * @return String DataReplication XML
     * @exception RemoteException
     */
    public String getEMessageReplication(String xmlRequest) throws RemoteException
    {
        Connection conn = null;
        EntityBatchIfc batch = null;

        try
        {
            conn = getDBUtils().getConnection();
            EntityIfc entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                    .getEntityInstance("EMessage");
            OrderSearchCriteria criteria = getOrderSearchCriteria(xmlRequest);
            EntitySearchIfc search = getEMessageSearchEntity(criteria.getOrderSearchInfo());
            entityReaderCatalog.readEntity(entity, search, conn);
            batch = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                    .getEntityBatchInstance();
            batch.addEntity(entity);
        }
        catch (Exception e)
        {
            getLogger().error("Failed to read order using DataReplication: ", e);
            throw new RemoteException("Failed to read order using DataReplication: ", e);
        }
        finally
        {
            try
            {
                // Close the db connection.
                conn.close();
            }
            catch (Exception e)
            {
                getLogger().warn("Exception closing the connection: " + e.getMessage());
            }
        }

        return buildReplicationXMLString(batch);
    }

    /**
     * Convert OrderSearchInfo criteria into EntitySearchIfc
     * @param info
     * @return EntitySearchIfc
     */
    protected EntitySearchIfc getOrderSearchEntity(OrderSearchInfo info)
    {
        // EMessage and Order have some criteria in common; get the
        // EMessage critia first.
        EntitySearchIfc search = getEMessageSearchEntity(info);

        if (info.getTrainingMode() != null)
        {
            boolean mode = Boolean.getBoolean(info.getTrainingMode());
            if (mode)
            {
                search.addSearchField(new EntitySearchField("FL_TRG_TRN", "1"));
            }
            else
            {
                search.addSearchField(new EntitySearchField("FL_TRG_TRN", "0"));
            }
        }
        if (info.getIntiatingChannel() != null)
        {
            int chnl = Integer.parseInt(info.getIntiatingChannel());
            if (chnl != -1)
            {
                search.addSearchField(new EntitySearchField("CHL_ORD_CRT", info.getIntiatingChannel()));
            }
        }
        return search;
    }

    /**
     * Convert OrderSearchInfo into EntitySearchIfc
     * @param info
     * @return EntitySearchIfc
     */
    protected EntitySearchIfc getEMessageSearchEntity(OrderSearchInfo info)
    {
        EntitySearchIfc search = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                .getEntitySearchInstance();

        if (info.getOrderID() != null)
        {
            search.addSearchField(new EntitySearchField("ID_ORD", info.getOrderID()));
        }
        if (info.getStoreNumber() != null)
        {
            search.addSearchField(new EntitySearchField("ID_STR_RT", info.getStoreNumber()));
        }
        if (info.getEmessageID() != null)
        {
            search.addSearchField(new EntitySearchField("ID_EMSG", info.getEmessageID()));
        }
        if (info.getCustomerID() != null)
        {
            search.addSearchField(new EntitySearchField("ID_CT", info.getCustomerID()));
        }
        if (info.getStatus() != null)
        {
            int status = -1;
            for (int i = 0; i < OrderConstantsIfc.ORDER_STATUS_DESCRIPTORS.length; i++)
            {
                if (info.getStatus().equals(OrderConstantsIfc.ORDER_STATUS_DESCRIPTORS[i]))
                {
                    status = i;
                    i = OrderConstantsIfc.ORDER_STATUS_DESCRIPTORS.length;
                }
            }

            if (status != -1)
            {
                search.addSearchField(new EntitySearchField("ST_ORD", Integer.toString(status)));
            }
        }

        return search;
    }

    /**
     * Gets item description associated with the first line item associated
     * with one or more transactions.
     * @param xmlRequest XML request string
     * @return XML containing one or more item descriptions
     * @exception RemoteException
     */
    public String getTransactionFirstItemDescription(String xmlRequest) throws RemoteException
    {
        getLogger().debug("Start First Item Description retrieval.");

        // Get the object factory and response object; the reponse is a list of descriptions.
        // The create method generates the list which will contain the descriptions
        ObjectFactory factory = new ObjectFactory();
        TransactionFirstItemDescriptionSearchResponse response = null;
        List<LocalizedTextElement> descriptions = null;
        try
        {
            response = factory.createTransactionFirstItemDescriptionSearchResponse();
            descriptions = response.getFirstItemDescription();

            // Get the list of transactions IDs; these IDs indicate the transactions
            // for which the item descriptions should be retrieved.
            TransactionFirstItemDescriptionSearchCriteria criteria = getTransactionFirstItemDescriptionSearchCriteria(xmlRequest);

            // Iterate through the list of IDs and get the description for each.
            List<TransactionID> transIDs = criteria.getTransactionIDs();
            LocaleRequestor localeRequestor = XSDElementConversionUtil
                    .getLocaleRequestor(criteria.getLocaleRequestor());
            for (TransactionID tID : transIDs)
            {
                TransactionKey transactionKey = new TransactionKey(tID.getStoreID(), tID.getWorkstationID(),
                        tID.getSequenceNumber().intValue(), tID.getBusinessDate().toGregorianCalendar().getTime());
                LocalizedTextIfc localizedDescriptions = readFirstItemDescription(transactionKey, localeRequestor);
                descriptions.add(XSDElementConversionUtil.getLocalizedTextElement(localizedDescriptions));
            }
        }
        catch (JAXBException e)
        {
            getLogger().error("Error generating TransactionFirstItemDescriptionSearchResponse.", e);
            throw new RemoteException("Error generating TransactionFirstItemDescriptionSearchResponse.", e);
        }

        // Convert the response object to xml
        String xmlResponse = null;
        try
        {
            JAXBContext jaxbContext = getJAXBContext();
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter strWriter = new StringWriter();
            marshaller.marshal(response, strWriter);
            xmlResponse = strWriter.toString();
        }
        catch (JAXBException e)
        {
            getLogger().error("Error generating JAXBContext for " + JAXB_CONTEXT + ".", e);
            throw new RemoteException("Error generating JAXBContext for " + JAXB_CONTEXT + ".", e);
        }
        catch (Exception e)
        {
            getLogger().error("Error generating XML response.", e);
            throw new RemoteException("Error generating XML response.", e);
        }

        return xmlResponse;
    }

    /**
     * Convert the TransactionFirstItemDescriptionSearchCriteria object to a JAXB object
     * @param xmlRequest
     * @return TransactionFirstItemDescriptionSearchCriteria
     * @throws RemoteException
     */
    protected TransactionFirstItemDescriptionSearchCriteria getTransactionFirstItemDescriptionSearchCriteria(
            String xmlRequest) throws RemoteException
    {
        TransactionFirstItemDescriptionSearchCriteria criteria = null;
        try
        {
            JAXBContext ctx = getJAXBContext();
            javax.xml.bind.Unmarshaller um = ctx.createUnmarshaller();
            Object resp = um.unmarshal(new StreamSource(new StringReader(xmlRequest)));
            if (!(resp instanceof TransactionFirstItemDescriptionSearchCriteria))
            {
                getLogger().error("Expecting an LayawaySearchCriteria object; received: " + resp.getClass().getName());
                throw new RemoteException("Invalid LayawaySearchCriteria.");
            }
            criteria = (TransactionFirstItemDescriptionSearchCriteria)resp;
        }
        catch (JAXBException e)
        {
            getLogger().error("Exception unmarshalling a order search criteria.", e);
            throw new RemoteException("Exception unmarshalling an LayawaySearchCriteria.", e);
        }

        return criteria;
    }

    /**
     * Reads the description for the first line item in the transaction.
     * @param tid
     * @return String description; if this method fails to retrieve a description
     * for any reason, it returns an empty string.
     */
    protected LocalizedTextIfc readFirstItemDescription(TransactionKey transactionKey, LocaleRequestor localeRequestor)
    {
        LocalizedTextIfc localizedDesc = new LocalizedText();
        localizedDesc.initialize(LocaleMap.getSupportedLocales(), "");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getDBUtils().getConnection();
            ps = conn.prepareStatement(READ_TRAN_FIRST_DESC);
            int index = 1;
            ps.setString(index++, transactionKey.getStoreID());
            ps.setString(index++, transactionKey.getWorkstationID());
            ps.setInt(index++, transactionKey.getSequenceNumber());
            ps.setString(index++, JdbcUtilities.YYYYMMDD_DATE_FORMAT.format(transactionKey.getDate()));
            rs = ps.executeQuery();
            while (rs.next())
            {
                Locale locale = LocaleUtilities.getLocaleFromString(rs.getString(1));
                String desc = rs.getString(2);
                localizedDesc.putText(locale, desc);
            }
        }
        catch (SQLException sqe)
        {
            if (getLogger().isDebugEnabled())
            {
                getLogger().warn("Error retriving first line item description.", sqe);
            }
            else
            {
                getLogger().warn("Error retriving first line item description.");
            }
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }

        return localizedDesc;
    }

    /**
     * Gets a layway or layaway transactions in the form of Repliaion XML.
     * If the search is by customer, then retrieve all the associated Layaway
     * rows.  If the Search is by Layaway ID, get all the transactions
     * associated with the Layaway.
     * @param xmlRequest XML request string
     * @return String DataReplication XML
     * @exception RemoteException
     * @exception TransactionRetrievalException
     */
    public String getLayawayReplication(String xmlRequest) throws RemoteException, TransactionRetrievalException
    {
        getLogger().debug("Start layaway replication retrieval.");
        EntityBatchIfc batch = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                .getEntityBatchInstance();

        Connection conn = null;
        try
        {
            conn = getDBUtils().getConnection();
            // Build the search fields
            LayawaySearchCriteria criteria = getLayawaySearchCriteria(xmlRequest);
            if (criteria.getLayawaySearchInfo().getLayawayID() != null)
            {
                // Get the Transaciton IDs associated with this Layaway
                List<EntitySearchIfc> entitySearch = getLayawayTransactionsIDs(criteria.getLayawaySearchInfo()
                        .getLayawayID());
                // Read each transaction into XML Replication objects.
                for (EntitySearchIfc search : entitySearch)
                {
                    EntityIfc entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                            .getEntityInstance("Transaction");
                    entityReaderCatalog.readEntity(entity, search, conn);
                    batch.addEntity(entity);
                }
            }
            if (criteria.getLayawaySearchInfo().getCustomerID() != null)
            {
                // Read all the Layaway rows associated with the customer
                EntityIfc entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                        .getEntityInstance("LayawayTable");
                EntitySearchIfc search = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                        .getEntitySearchInstance();
                search.addSearchField(new EntitySearchField("ID_CT", criteria.getLayawaySearchInfo().getCustomerID()));
                entityReaderCatalog.readEntity(entity, search, conn);
                batch.addEntity(entity);
            }
            batch.setBatchId("EnterpriseLayaway");
        }
        catch (Exception e)
        {
            getLogger().error("Failed to read layaway using DataReplication: ", e);
            throw new RemoteException("Failed to read layaway using DataReplication: ", e);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (Exception e)
            {
                getLogger().warn("Exception closing the connection: " + e.getMessage());
            }
        }

        // Generate an XML document from the batch object,
        // and a XML string from the document.
        return buildReplicationXMLString(batch);
    }

    /**
     * Get all the transaction IDs associated with a layaway
     * @param layawayID
     * @return a list of SerachEntitys containing transaction IDs
     * @throws TransactionRetrievalException
     */
    private List<EntitySearchIfc> getLayawayTransactionsIDs(String layawayID) throws TransactionRetrievalException
    {
        List<EntitySearchIfc> list = new ArrayList<EntitySearchIfc>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getDBUtils().getConnection();
            ps = conn.prepareStatement(READ_LAYAWAY_TRAN_IDS);
            ps.setString(1, layawayID);
            rs = ps.executeQuery();
            while (rs.next())
            {
                String storeID = rs.getString(1);
                String wsID = rs.getString(2);
                String tranID = rs.getString(3);
                String bd = rs.getString(4);
                EntitySearchIfc search = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                        .getEntitySearchInstance();
                search.addSearchField(new EntitySearchField("ID_STR_RT", storeID));
                search.addSearchField(new EntitySearchField("ID_WS", wsID));
                search.addSearchField(new EntitySearchField("AI_TRN", tranID));
                search.addSearchField(new EntitySearchField("DC_DY_BSN", bd));
                list.add(search);
            }
            return list;
        }
        catch (SQLException sqe)
        {
            getLogger().error("", sqe);
            throw new TransactionRetrievalException(sqe.getMessage(), sqe, layawayID);
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }
    }

    /**
     * Converts the Layaway XML request into a JAXB object
     * @param xmlRequest
     * @return LayawaySearchCriteria
     * @throws RemoteException
     */
    protected LayawaySearchCriteria getLayawaySearchCriteria(String xmlRequest) throws RemoteException
    {
        LayawaySearchCriteria criteria = null;
        try
        {
            JAXBContext ctx = getJAXBContext();
            javax.xml.bind.Unmarshaller um = ctx.createUnmarshaller();
            Object resp = um.unmarshal(new StreamSource(new StringReader(xmlRequest)));
            if (!(resp instanceof LayawaySearchCriteria))
            {
                getLogger().error("Expecting an LayawaySearchCriteria object; received: " + resp.getClass().getName());
                throw new RemoteException("Invalid LayawaySearchCriteria.");
            }
            criteria = (LayawaySearchCriteria)resp;
        }
        catch (JAXBException e)
        {
            getLogger().error("Exception unmarshalling a order search criteria.", e);
            throw new RemoteException("Exception unmarshalling an LayawaySearchCriteria.", e);
        }

        return criteria;
    }

    /**
     * Gets an order or orders in the form of Repliaion XML.
     * @param xmlRequest XML request string
     * @return String DataReplication XML
     * @exception RemoteException
     * @exception TransactionRetrievalException
     */
    public String getOrderReplication(String xmlRequest) throws RemoteException, TransactionRetrievalException
    {
        getLogger().debug("Start order replication retrieval.");
        EntityBatchIfc batch = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                .getEntityBatchInstance();

        Connection conn = null;
        try
        {
            conn = getDBUtils().getConnection();
            // Build the search fields
            OrderSearchCriteria criteria = getOrderSearchCriteria(xmlRequest);
            EntityIfc entity = null;
            if (criteria.getOrderSearchInfo().isSummary())
            {
                entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory().getEntityInstance(
                        "OrderTable");
            }
            else
            {
                entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory().getEntityInstance(
                        "StandAloneOrder");
            }
            EntitySearchIfc search = getOrderSearchEntity(criteria.getOrderSearchInfo());
            entityReaderCatalog.readEntity(entity, search, conn);
            batch.setBatchId("EnterpriseOrder");
            batch.addEntity(entity);
        }
        catch (Exception e)
        {
            getLogger().error("Failed to read order using DataReplication: ", e);
            throw new RemoteException("Failed to read order using DataReplication: ", e);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (Exception e)
            {
                getLogger().warn("Exception closing the connection: " + e.getMessage());
            }
        }

        // Generate an XML document from the batch object,
        // and a XML string from the document.
        return buildReplicationXMLString(batch);
    }

    /**
     * This method retrieves transactions based on the values in the XML Search Criteria
     * @param searchCriteriaString
     * @return Transactions in DataReplication XML format.
     * @throws TransactionRetrievalException
     * @throws SearchResultSizeExceededException
     */
    public String getTransactionReplication(String searchCriteriaString) throws RemoteException,
            TransactionRetrievalException
    {
        getLogger().debug("Start transaction replication retrieval.");
        EntityBatchIfc batch = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                .getEntityBatchInstance();

        Connection conn = null;
        int numberOfTransactions = 0;
        int maxTransactions = 0;
        try
        {
            conn = getDBUtils().getConnection();
            // Build the transaction search fields
            EntitySearchIfc transSearchFields = ReplicationObjectFactoryContainer.getInstance()
                    .getExtractorObjectFactory().getEntitySearchInstance();
            maxTransactions = convertTransactionRequestCriteria(searchCriteriaString, transSearchFields);
            EntityIfc entity = ReplicationObjectFactoryContainer.getInstance().getExtractorObjectFactory()
                    .getEntityInstance("Transaction");
            entityReaderCatalog.readEntity(entity, transSearchFields, conn);
            numberOfTransactions = entity.getTable(ARTSDatabaseIfc.TABLE_TRANSACTION).getNumberOfRowsRead();
            batch.setBatchId("EnterpriseTransaction");
            batch.addEntity(entity);
        }
        catch (Exception e)
        {
            getLogger().error("Failed to read transanction using DataReplication: ", e);
            throw new TransactionRetrievalException("Failed to read transanction using DataReplication: ", e
                    .getMessage());
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (Exception e)
            {
                getLogger().warn("Exception closing the connection: " + e.getMessage());
            }
        }

        String batchXML = null;
        if (numberOfTransactions > maxTransactions)
        {
            try
            {
                JAXBContext jc = getJAXBContext();
                ObjectFactory objectFactory = new ObjectFactory();
                SearchResultsExceedElement searchResultsExceed = objectFactory.createSearchResultsExceedElement();
                searchResultsExceed.setMessage("Search results set was too large");
                searchResultsExceed.setTotalCount(String.valueOf(numberOfTransactions));

                StringWriter writer = new StringWriter();
                Marshaller marshaller = jc.createMarshaller();
                marshaller.marshal(searchResultsExceed, writer);
                batchXML = writer.toString().trim();
                return batchXML;
            }
            catch (JAXBException e)
            {
                getLogger().error("Invalid Search Criteria: " + searchCriteriaString, e);
                throw new TransactionRetrievalException("Invalid Search Criteria", e, searchCriteriaString);
            }
        }
        else if (numberOfTransactions > 0)
        {
            batchXML = buildReplicationXMLString(batch);
        }

        getLogger().debug("Replication retrieval complete.");
        return batchXML;
    }

    /**
     * There are two different transaction request formats depending on whether or
     * not the business date is included in the request.  This method manages
     * processing based on the type of reqest format.
     * @param searchCriteriaString
     * @param searchFields
     * @return
     * @throws TransactionRetrievalException
     */
    protected int convertTransactionRequestCriteria(String searchCriteriaString, EntitySearchIfc searchFields)
            throws TransactionRetrievalException
    {
        // Transform the XML using JAXB
        Object request = null;
        try
        {
            request = AbstractTransactionUtility.makeSearchObject(searchCriteriaString, getJAXBContext());
        }
        catch (JAXBException e)
        {
            getLogger().error("Invalid Search Criteria: caught JAXBException: ", e);
            throw new TransactionRetrievalException("Invalid Search Criteria: caught JAXBException: ", e.getMessage());
        }

        int maxTrans = 0;

        // If query transaction request does not include the business date, but does include
        // The maximum number of transactions to return.
        if (request instanceof QueryTransactionRequest)
        {
            maxTrans = convertTransactionRequestCriteria((QueryTransactionRequest)request, searchFields);
        }
        else
        // The transaction key element includes the business date; it can only get a single transaction
        // the max transaction is 1.
        if (request instanceof TransactionKeyElement)
        {
            maxTrans = 1;
            convertTransactionRequestCriteria((TransactionKeyElement)request, searchFields);
        }
        else
        {
            throw new TransactionRetrievalException("Invalid Search Criteria: Wrong message type", null);
        }

        return maxTrans;
    }

    /**
     * Convert the transaction search criteria xml into a QueryTransactionRequest
     * @param searchCriteriaString
     * @return QueryTransactionRequest
     * @throws TransactionRetrievalException
     */
    protected QueryTransactionRequest getQueryTransactionRequest(String searchCriteriaString)
            throws TransactionRetrievalException
    {
        QueryTransactionRequest request = null;
        try
        {
            request = AbstractTransactionUtility.makeSearchRequest(searchCriteriaString, getJAXBContext());
        }
        catch (JAXBException e)
        {
            getLogger().error("Invalid Search Criteria: caught JAXBException: ", e);
            throw new TransactionRetrievalException("Invalid Search Criteria: caught JAXBException: ", e.getMessage());
        }
        return request;
    }

    /**
     * This method converts the search criteria XML into an EntitySearch object
     * @param searchCriteriaString
     * @return The maximum number of transactions that are allowed to
     * be returned to requesting application.
     * @throws TransactionRetrievalException
     */
    protected int convertTransactionRequestCriteria(QueryTransactionRequest request, EntitySearchIfc searchFields)
    {
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_RETAIL_STORE_ID, request
                .getTransactionID().getStoreID()));
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_WORKSTATION_ID, request
                .getTransactionID().getWorkstationID()));
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_TRANSACTION_SEQUENCE_NUMBER, request
                .getTransactionID().getSequenceNumber().toString()));

        if (getLogger().isDebugEnabled())
        {
            getLogger().debug(
                    "Replication transaction lookup key values -- " + "StoreID: "
                            + request.getTransactionID().getStoreID() + "; WorkstationID: "
                            + request.getTransactionID().getWorkstationID() + "; SequenceNumber: "
                            + request.getTransactionID().getSequenceNumber().toString());
        }

        return request.getMaxResults().intValue();
    }

    private void convertTransactionRequestCriteria(TransactionKeyElement element, EntitySearchIfc searchFields)
    {
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_RETAIL_STORE_ID, element.getStoreID()));
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_WORKSTATION_ID, element
                .getWorkstationID()));
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_TRANSACTION_SEQUENCE_NUMBER, element
                .getSequenceNumber()));
        searchFields.addSearchField(new EntitySearchField(ARTSDatabaseIfc.FIELD_BUSINESS_DAY_DATE, element
                .getTransactionDate()));

        getLogger().debug(
                "Replication transaction lookup key values -- " + "StoreID: " + element.getStoreID()
                        + "; WorkstationID: " + element.getWorkstationID() + "; SequenceNumber: "
                        + element.getSequenceNumber() + "; BusinessDate: " + element.getTransactionDate());
    }

    private List<EntitySearchIfc> getTaxRuleSearchEntities(List<Integer> taxGroups, List<String> taxAths)
    {
        List<EntitySearchIfc> searchEntities = new ArrayList<EntitySearchIfc>();

        for (String auth : taxAths)
        {
            EntitySearchField authField = new EntitySearchField("ID_ATHY_TX", auth);

            for (Integer group : taxGroups)
            {
                EntitySearchIfc searchFields = ReplicationObjectFactoryContainer.getInstance()
                        .getExtractorObjectFactory().getEntitySearchInstance();
                searchFields.addSearchField(authField);
                searchFields.addSearchField(new EntitySearchField("ID_GP_TX", group.toString()));
                searchEntities.add(searchFields);
            }
        }

        return searchEntities;
    }

    /**
     * Reads the description for the first line item in the transaction.
     * @param tid
     * @return String description; if this method fails to retrieve a description
     * for any reason, it returns an empty string.
     */
    private List<Integer> readTransactionTaxGroups(EntitySearchIfc searchFields)
    {
        List<Integer> groups = new ArrayList<Integer>();
        Integer group = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String storeID = searchFields.getSearchField(ARTSDatabaseIfc.FIELD_RETAIL_STORE_ID).getFieldValue().toString();
        String workstationID = searchFields.getSearchField(ARTSDatabaseIfc.FIELD_WORKSTATION_ID).getFieldValue()
                .toString();
        String seqNumber = searchFields.getSearchField(ARTSDatabaseIfc.FIELD_TRANSACTION_SEQUENCE_NUMBER)
                .getFieldValue().toString();
        EntitySearchFieldIfc dbField = searchFields.getSearchField(ARTSDatabaseIfc.FIELD_BUSINESS_DAY_DATE);

        // Determine if the business date should be use in lookup.
        boolean useBDate = false;
        if (dbField != null)
        {
            useBDate = true;
            getLogger().debug(
                    "Replication tax rule lookup not using a date; Date = " + dbField.getFieldValue().toString());
        }

        try
        {
            conn = getDBUtils().getConnection();
            if (useBDate)
            {
                ps = conn.prepareStatement(READ_TRAN_TAX_GROUPS_BDATE);
            }
            else
            {
                ps = conn.prepareStatement(READ_TRAN_TAX_GROUPS);
            }
            ps.setString(1, storeID);
            ps.setString(2, workstationID);
            ps.setInt(3, Integer.parseInt(seqNumber));
            if (useBDate)
            {
                ps.setString(4, dbField.getFieldValue().toString());
            }
            rs = ps.executeQuery();
            while (rs.next())
            {
                group = new Integer(rs.getInt(1));
                if (!groups.contains(group))
                {
                    groups.add(group);
                }
            }
        }
        catch (SQLException sqe)
        {
            if (getLogger().isDebugEnabled())
            {
                getLogger().warn("Error retriving first line item description.", sqe);
            }
            else
            {
                getLogger().warn("Error retriving first line item description.");
            }
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }

        return groups;
    }

    private List<String> readTransactionTaxAuthorities(EntitySearchIfc searchFields)
    {
        List<String> auths = new ArrayList<String>();
        String auth = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getDBUtils().getConnection();
            ps = conn.prepareStatement(READ_TRAN_TAX_AUTHORITIES);
            ps.setString(1, searchFields.getSearchField(ARTSDatabaseIfc.FIELD_RETAIL_STORE_ID).getFieldValue()
                    .toString());
            rs = ps.executeQuery();
            while (rs.next())
            {
                auth = rs.getString(1);
                if (!auths.contains(auth))
                {
                    auths.add(auth);
                }
            }
        }
        catch (SQLException sqe)
        {
            if (getLogger().isDebugEnabled())
            {
                getLogger().warn("Error retriving first line item description.", sqe);
            }
            else
            {
                getLogger().warn("Error retriving first line item description.");
            }
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }

        return auths;
    }

    /**
     * Create a formatted XML string from the document.
     *
     * @param docElement name of the topmost element in the document.
     * @return formatted xml string
     * @exception RemoteException thrown on error
     */
    protected String buildReplicationXMLString(EntityBatchIfc batch) throws RemoteException
    {
        Document document;
        try
        {
            document = batch.generateDocument();
        }
        catch (ReplicationExportException e)
        {
            throw new RemoteException("Exception converting a Replication batch to an XML Document", e);
        }
        StringWriter outText = new StringWriter();
        StreamResult sr = new StreamResult(outText);
        Properties oprops = new Properties();
        oprops.put(OutputKeys.METHOD, "xml");
        oprops.put(OutputKeys.OMIT_XML_DECLARATION, "no");
        if (document.getDoctype() != null)
        {
            if (document.getDoctype().getPublicId() != null)
            {
                oprops.put(OutputKeys.DOCTYPE_PUBLIC, document.getDoctype().getPublicId());
            }
            if (document.getDoctype().getSystemId() != null)
            {
                oprops.put(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId());
            }
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = null;
        try
        {
            t = tf.newTransformer();
            if (entityReaderCatalog.isFormatExport())
            {
                oprops.put(OutputKeys.INDENT, "yes");
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            }
            t.setOutputProperties(oprops);
            t.transform(new DOMSource(document), sr);
        }
        catch (TransformerException ex)
        {
            throw new RemoteException("Error converting XML document to string.", ex);
        }
        return outText.toString();
    }

    /**
     *  This method reads the file reads the configuration file and returns an object
     *  containing the configuration.
     *  @param configName the configuration file name
     *  @return EntityReaderCatalog database configuration information
     */
    protected void buildEntityReaderCatalog(String extractorConfigurationFileName)
    {
        try
        {
            if (entityReaderCatalog == null)
            {
                // I have tried several ways to get a stream from this file including:
                //      stream = getClass().getResourceAsStream(configName);
                // However, the kluge that follows is the only one that I could get work.
                InputStream stream = null;
                ClassLoader loader = getClass().getClassLoader();
                Enumeration<URL> rs = loader.getResources(extractorConfigurationFileName);
                if (rs.hasMoreElements())
                {
                    URL url = rs.nextElement();
                    stream = url.openStream();
                }
                ReaderCatalogConfiguratorIfc config = ReplicationObjectFactoryContainer.getInstance()
                        .getExtractorObjectFactory().getReaderCatalogConfiguratorInstance();
                entityReaderCatalog = config.configureReaderCatalog(stream);
            }
        }
        catch (Exception e)
        {
            getLogger().error("An error occurred reading the Data Replication configuration XML file.", e);
        }
    }

    /**
     *  This method gets the name of the Replication Import Configuration File;
     *  if the value is not available from the descriptor file it uses the default.
     *  @return String file name
     */
    protected String getReplicationExportConfigFileName()
    {
        String fileName = "ReplicationExportConfig.xml";

        try
        {
            fileName = (String)getContext().lookup("java:comp/env/ReplicationExportConfigFile");
            getLogger().debug("ReplicationExportConfigFile is " + fileName);
        }
        catch (NamingException e)
        {
            getLogger().error("Cannot find Replication Export config file.  Using default " + fileName, e);
        }

        return fileName;
    }

    // The purpose of the preceeding methods is to provide transaction related
    // information to store systems, i.e. the POS

    /**
     * Lazily create and return the {@link JAXBContext} used by this bean to
     * marshal XML and objects.
     *
     * @return
     */
    protected JAXBContext getJAXBContext() throws JAXBException
    {
        if (jaxbContext == null)
        {
            jaxbContext = getServiceLocator().getJAXBContext(getJAXBContextPath());
        }
        return jaxbContext;
    }

    /**
     * Return the default JAXBContext path, {@link #JAXB_CONTEXT}.
     *
     * @return
     */
    protected String getJAXBContextPath()
    {
        return JAXB_CONTEXT;
    }

    /**
     * Get the factory class name used for schema types.
     *
     * @return
     */
    private String getSchemaTypeFactoryName()
    {
        String factoryName = "oracle.retail.stores.commerceservices.ixretail.schematypes.v21.SchemaTypesFactory";

        if (schemaTypeFactoryName == null)
        {
            try
            {
                factoryName = (String)getContext().lookup("java:comp/env/SchemaTypesFactory");
                schemaTypeFactoryName = factoryName;

                getLogger().debug("schemaTypeFactoryName is " + factoryName);
            }
            catch (NamingException e)
            {
                getLogger().error("Cannot find SchemaTypesFactoryName.  Using default " + factoryName, e);
            }
        }
        else
        {
            factoryName = schemaTypeFactoryName;
        }

        return factoryName;
    }

    public boolean isVoided(TransactionKey key)
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            connection = getDBUtils().getConnection();
            ps = connection
                    .prepareStatement("SELECT COUNT(*) FROM TR_VD_PST WHERE ID_STR_RT = ? AND ID_WS = ? AND AI_TRN_VD = ? AND DC_DY_BSN = ?");

            int i = 1;
            int count = 0;
            ps.setString(i++, key.getStoreID());
            ps.setString(i++, key.getWorkstationID());
            ps.setInt(i++, key.getSequenceNumber());
            ps.setString(i++, key.getFormattedDateString());
            rs = ps.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }
            return (count == 1);
        }
        catch (SQLException e)
        {
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(connection, ps, rs);
        }
    }

    /**
     * Get the class name of the transaction header utility.
     *
     * @return
     */
    private String getTransactionHeaderUtilityName()
    {
        String theName = "oracle.retail.stores.commerceservices.transaction.ejb.TransactionsHeaderUtility";

        if (transactionHeaderUtilityName == null)
        {
            try
            {
                theName = (String)getContext().lookup("java:comp/env/TransactionHeaderUtility");
                transactionHeaderUtilityName = theName;

                getLogger().debug("TransactionHeaderUtilityName is " + theName);
            }
            catch (NamingException e)
            {
                getLogger().error("Cannot find transactionHeaderUtilityName.  Using default " + theName, e);
            }
        }
        else
        {
            theName = transactionHeaderUtilityName;
        }

        return theName;
    }

    private TransactionsHeaderUtility getTransactionHeaderUtility()
    {
        String className = getTransactionHeaderUtilityName();
        TransactionsHeaderUtility transactionHeaderUtility = null;
        try
        {
            Class<?> clazz = Class.forName(className);
            transactionHeaderUtility = (TransactionsHeaderUtility)clazz.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            getLogger().error("Failed to find class for TransactionHeaderUtility: " + className, e);
            throw new EJBException(e);
        }
        catch (InstantiationException e)
        {
            getLogger().error("Failed to Instantiate class for TransactionHeaderUtility: " + className, e);
            throw new EJBException(e);
        }
        catch (IllegalAccessException e)
        {
            getLogger().error(
                    "Failed to Instantiate class (illegal access) for TransactionHeaderUtility: " + className, e);
            throw new EJBException(e);
        }

        if (transactionHeaderUtility == null)
        {
            transactionHeaderUtility = new TransactionsHeaderUtility();
        }

        return transactionHeaderUtility;
    }

    /**
     * Get the POSLog utility class name.
     *
     * @return
     */
    private String getPOSLogTransactionUtilityName()
    {
        String theName = "oracle.retail.stores.commerceservices.transaction.ejb.POSLogTransactionUtility";

        if (posLogTransactionUtilityName == null)
        {
            try
            {
                theName = (String)getContext().lookup("java:comp/env/POSLogTransactionUtility");
                posLogTransactionUtilityName = theName;

                getLogger().debug("POSLogTransactionUtility Name is " + theName);
            }
            catch (NamingException e)
            {
                getLogger().error("Cannot find POSLogTransactionUtility Name.  Using default " + theName, e);
            }
        }
        else
        {
            theName = posLogTransactionUtilityName;
        }

        return theName;
    }

    /**
     * Instantiates, and returns ref to, POSLogTransactionUtility
     * @return POSLogTransactionUtility
     */
    private POSLogTransactionUtility getPOSLogTransactionUtility()
    {
        String className = getPOSLogTransactionUtilityName();
        POSLogTransactionUtility posLogTransactionUtility = null;
        try
        {
            Class<?> clazz = Class.forName(className);
            posLogTransactionUtility = (POSLogTransactionUtility)clazz.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            getLogger().error("Failed to find class for TransactionHeaderUtility: " + className, e);
            throw new EJBException(e);
        }
        catch (InstantiationException e)
        {
            getLogger().error("Failed to Instantiate class for TransactionHeaderUtility: " + className, e);
            throw new EJBException(e);
        }
        catch (IllegalAccessException e)
        {
            getLogger().error(
                    "Failed to Instantiate class (illegal access) for TransactionHeaderUtility: " + className, e);
            throw new EJBException(e);
        }

        if (posLogTransactionUtility == null)
        {
            posLogTransactionUtility = new POSLogTransactionUtility();
        }

        try
        {
            POSLogTransactionUtility.context = getContext();
        }
        catch (NamingException e)
        {
            getLogger().error("", e);
        }
        return posLogTransactionUtility;
    }

    /**
     * Retrieves a set of transaction information given search criteria as input.
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @return returnLimit
     * @throws DataException
     * @deprecated Please use overloaded method
     *     {@link #getTransactions(StoreSelectionCriteria, SearchCriteria, int, int, String[])}
     */
    public TransactionSearchResultDTO getTransactions(StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int pageSize) throws SearchResultSizeExceededException
    {
        return getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize, null, TransactionServiceIfc.REQUEST_TYPE.USER_REQUEST);
    }

    /**
     * Retrieves a set of transaction information given search criteria as input.
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param requestType - is the request from a user or from POS returns
     * @return returnLimit
     * @throws DataException
     * @deprecated Please use overloaded method
     *     {@link #getTransactions(StoreSelectionCriteria, SearchCriteria, int, int, String[])}
     */
    public TransactionSearchResultDTO getTransactions(StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int pageSize, TransactionServiceIfc.REQUEST_TYPE requestType) throws SearchResultSizeExceededException
    {
        return getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize, null, requestType);
    }

    public String getRetailTransactionCustomerId(TransactionKey key)
    {
        String customerId = null;
        try
        {
            RetailTransactionLocal local = getRetailTransactionHome().findByPrimaryKey(key);
            customerId = local.getCustomerId();

        }
        catch (FinderException e)
        {
            getLogger().error("", e);
        }
        return customerId;
    }

    private Collection<CustomerSearchResult> getCustomers(CustomerCriteria criteria) throws SearchException,
            RemoteException
    {
        CustomerSearchCriteria scs = new CustomerSearchCriteria();
        Collection<CustomerSearchResult> c = null;
        if (criteria.isSearchByCustomerID())
        {
            CustomerSearchResult csr = new CustomerSearchResult(criteria.getCustomerID());
            c = new ArrayList<CustomerSearchResult>();
            c.add(csr);
        }
        else
        {
            if (criteria.isSearchByAddressInfo())
            {
                if (criteria.isSearchByCustomerAddressLine1())
                {
                    scs.setAddress1(criteria.getCustomerAddressLine1());
                }
                if (criteria.isSearchByCustomerAddressLine2())
                {
                    scs.setAddress2(criteria.getCustomerAddressLine2());
                }
                if (criteria.isSearchByCustomerAddressCity())
                {
                    scs.setCity(criteria.getCustomerAddressCity());
                }
                if (criteria.isSearchByCustomerAddressState())
                {
                    scs.setState(criteria.getCustomerAddressState());
                }
                if (criteria.isSearchByCustomerAddressCountry())
                {
                    scs.setCountry(criteria.getCustomerAddressCountry());
                }
                if (criteria.isSearchByCustomerAddressZipCode())
                {
                    scs.setPostal(criteria.getCustomerAddressZipCode());
                }
                if (criteria.isSearchByCustomerAddressZipCodeExt())
                {
                    scs.setPostalExt(criteria.getCustomerAddressZipCodeExt());
                }
                if (criteria.isSearchByCustomerTelephoneNumber())
                {
                    scs.setTelephone(criteria.getCustomerTelephoneNumber());
                }
            }
            if (criteria.isSearchByContactInfo())
            {
                if (criteria.isSearchByCustomerFirstName())
                {
                    scs.setFirstName(criteria.getCustomerFirstName());
                }
                if (criteria.isSearchByCustomerLastName())
                {
                    scs.setLastName(criteria.getCustomerLastName());
                }
            }
            c = getCustomerService().searchCustomers(scs);
        }
        return c;
    }

    /**
     * Builds a PreparedStatement using SearchCriteria as input.
     *
     * @param conn             - the jdbc connection to use
     * @param query            - contains the query text
     * @param storeSearchCriteria
     * @param ejournalCriteria - the criteria to search by
     * @param orderBy          - contains the value(s) to order the result set by
     */
    private PreparedStatement createQuery(Connection conn, StringBuilder query,
            StoreSelectionCriteria storeSearchCriteria, EJournalCriteria ejournalCriteria, String orderBy)
        throws SQLException
    {
        query.append(" FROM JL_ENR ");
        // store ID list
        query.append(addToFromClause(storeSearchCriteria, ARTSDatabaseIfc.TABLE_EJOURNAL));
        query.append(" WHERE 1=1 ");
        query.append(addToWhereClause(storeSearchCriteria, ARTSDatabaseIfc.TABLE_EJOURNAL));
        query.append(addToWhereClause(ejournalCriteria));
        query.append(" AND JL_ENR.FL_NOR_COM = 1 ");
        query.append(orderBy);
        if (getLogger().isDebugEnabled())
            getLogger().debug(".createQuery() :\n" + query);
        PreparedStatement ps = conn.prepareStatement(query.toString());

        //save off bind variables for debugging SQL - temporary?
        if (getLogger().isDebugEnabled())
            bindVariables = new ArrayList<Object>(20);
        int n = 1;
        n = setBindVariables(ps, n, storeSearchCriteria);
        n = setBindVariables(ps, n, ejournalCriteria);
        if (getLogger().isDebugEnabled())
        {
            getLogger().debug("\nQuery bind variables :\n");
            for (int i = 0; i < bindVariables.size(); i++)
            {
                getLogger().debug((i + 1) + " = " + bindVariables.get(i));
            }
        }
        return ps;
    }

    /**
     * @deprecated as of 13.3. Use {@link #getSearchCriteriaCount(EJournalCriteria)} instead.
     */
    public int getSearchCriteriaCount(String storeJoinKey, EJournalCriteria ejournalCriteria)
    {
        return getSearchCriteriaCount(ejournalCriteria);
    }

    /**
     * Determines the size of the result set that will be returned, based on the
     * storeID and searchCriteria input.
     * <p>
     * For performance reasons, it's probably a faster end-user experience to skip this check.
     *
     * @return int
     * @deprecated as of 13.3 no replacement.
     */
    public int getSearchCriteriaCount(EJournalCriteria ejournalCriteria)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getDBUtils().getConnection();
            List<String> list = new ArrayList<String>();
            list.add("JL_ENR.ID_STR_RT");
            list.add("JL_ENR.ID_WS");
            list.add("JL_ENR.DC_DY_BSN");
            list.add("JL_ENR.AI_TRN");
            list.add("JL_ENR.TS_JRL_BGN");
            String ids = getDBUtils().safeConcat(list);
            StringBuilder query = new StringBuilder("SELECT COUNT(DISTINCT " + ids + ") ");
            ps = createQuery(conn, query, null, ejournalCriteria, " ");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }
    }


    /**
     * @deprecated as of 13.3. Use {@link #getSearchCriteriaCount(SearchCriteria, Collection)} instead.
     */
    public int getSearchCriteriaCount(String storeJoinKey, SearchCriteria searchCriteria,
            Collection<CustomerSearchResult> customers)
    {
        return getSearchCriteriaCount(searchCriteria, customers);
    }

    /**
     * Determines the size of the result set that will be returned,
     * based on the storeID and searchCriteria input.
     * <p>
     * For performance reasons, it's probably a faster end-user experience to skip this check.
     *
     * @return int
     * @deprecated as of 13.3 no replacement.
     */
    public int getSearchCriteriaCount(SearchCriteria searchCriteria,
            Collection<CustomerSearchResult> customers)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            conn = getDBUtils().getConnection();
            List<String> list = new ArrayList<String>(4);
            list.add("TR_TRN.ID_STR_RT");
            list.add("TR_TRN.ID_WS");
            list.add("TR_TRN.DC_DY_BSN");
            list.add("TR_TRN.AI_TRN");
            String ids = getDBUtils().safeConcat(list);
            StringBuilder query = new StringBuilder("SELECT COUNT(DISTINCT " + ids + ") ");
            ps = createQuery(conn, query, null, searchCriteria, customers, " ", null);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (DataException e)
        {
            getLogger().error("Unable to convert account number to hash value", e);
            throw new EJBException(e);
        }
        catch (SQLException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }
    }

    /**
     * @param storeSearchCriteria
     * @param tableName
     * @return
     */
    private String addToFromClause(StoreSelectionCriteria storeSearchCriteria, String tableName)
    {
        StringBuilder query = new StringBuilder();
        if (storeSearchCriteria != null)
        {
            if (storeSearchCriteria.isHierarchyNodeSearch() && !storeSearchCriteria.isHierarchyNodeRetailStore())
            {
                query.append(" join ").append(ARTSDatabaseIfc.TABLE_STORE_HIERARCHY).append(" on ");
                query.append("  ").append(tableName).append(".ID_STR_RT = ");
                query.append(ARTSDatabaseIfc.TABLE_STORE_HIERARCHY).append(".ID_STR_RT ");
            }
        }
        return query.toString();
    }

    /**
     * @param storeSearchCriteria
     * @return
     */
    private String addToWhereClause(StoreSelectionCriteria storeSearchCriteria, String tableName)
    {
        StringBuilder query = new StringBuilder();
        if (storeSearchCriteria != null)
        {
            if (storeSearchCriteria.isHierarchyNodeSearch() && !storeSearchCriteria.isHierarchyNodeRetailStore())
            {
                query.append(" AND ").append(ARTSDatabaseIfc.TABLE_STORE_HIERARCHY)
                            .append(".").append(ARTSDatabaseIfc.FIELD_STORE_GROUP_ID).append(" = ? ");
            }
            else if (storeSearchCriteria.isHierarchyNodeRetailStore())
            {
                query.append(" AND ").append(tableName).append(".")
                    .append(ARTSDatabaseIfc.FIELD_RETAIL_STORE_ID).append(" = ? ");
            }
            else if (storeSearchCriteria.getStoreIds() != null)
            {
                // this is a regular list of stores ids to search with.
                int size = storeSearchCriteria.getStoreIds().size();
                if (size > 0)
                {
                    query.append(" AND ").append(tableName).append(".")
                        .append(ARTSDatabaseIfc.FIELD_RETAIL_STORE_ID).append(" in (");
                    for (int i = 0; i < size; i++)
                    {
                        query.append("?");
                        if (i < size - 1)
                        {
                            query.append(",");
                        }
                    }
                    query.append(") ");
                }
            }
        }
        return query.toString();
    }

    /**
     * @param preparedStatement
     * @param index
     * @param storeSearchCriteria
     * @return
     * @throws SQLException
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index, StoreSelectionCriteria storeSearchCriteria)
            throws SQLException
    {
        if (storeSearchCriteria != null)
        {
            if (storeSearchCriteria.isHierarchyNodeSearch())
            {
                String nodeId = storeSearchCriteria.getHierarchyNodeKey().getId();
                if (getLogger().isDebugEnabled())
                    bindVariables.add(nodeId);
                preparedStatement.setString(index++, nodeId);
            }
            else
            {
                Set<String> storeIds = storeSearchCriteria.getStoreIds();
                if (storeIds != null)
                {
                    int size = storeIds.size();
                    if (size > 0)
                    {
                        for (String storeId : storeIds)
                        {
                            if (getLogger().isDebugEnabled())
                                bindVariables.add(storeId);
                            preparedStatement.setString(index++, storeId);
                        }
                    }
                }
            }
        }
        return index;
    }

    /**
     * Transaction Criteria
     */
    private String addToFromClause(TransactionCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            if (criteria.isSearchByTransactionTotal() || criteria.isSearchByRetailTransactionsOnly()
                    || criteria.isSearchByExternalOrderNumber())
            {
                query.append(" left join TR_RTL on ");
                query.append("  TR_RTL.ID_STR_RT = TR_TRN.ID_STR_RT ");
                query.append(" AND TR_RTL.ID_WS = TR_TRN.ID_WS ");
                query.append(" AND TR_RTL.DC_DY_BSN = TR_TRN.DC_DY_BSN ");
                query.append(" AND TR_RTL.AI_TRN = TR_TRN.AI_TRN ");
            }

            if (criteria.isSearchByVoidedTransaction())
            {
                query.append(" left outer join TR_VD_PST on ");
                query.append("     TR_TRN.ID_STR_RT = TR_VD_PST.ID_STR_RT ");
                query.append(" AND TR_TRN.ID_WS     = TR_VD_PST.ID_WS_VD ");
                query.append(" AND TR_TRN.DC_DY_BSN = TR_VD_PST.DC_DY_BSN ");
                query.append(" AND TR_TRN.AI_TRN    = TR_VD_PST.AI_TRN_VD ");
            }
        }
        return query.toString();
    }

    /**
     *
     */
    private String addToWhereClause(EJournalCriteria ejournalCriteria)
    {
        StringBuilder query = new StringBuilder();
        if (ejournalCriteria != null && ejournalCriteria.use())
        {
            if (ejournalCriteria.isSearchByTransactionNumber())
            {
                query.append("AND JL_ENR.AI_TRN = ? ");
                query.append("AND JL_ENR.ID_WS = ? ");
                query.append("AND JL_ENR.ID_STR_RT = ? ");
            }
            else
            {
                if (ejournalCriteria.isSearchByFromWorkstationID())
                {
                    query.append("AND JL_ENR.ID_WS >= ? ");
                }
                if (ejournalCriteria.isSearchByToWorkstationID())
                {
                    query.append("AND JL_ENR.ID_WS <= ? ");
                }
                if (ejournalCriteria.isSearchByFromSequenceNumber())
                {
                    query.append("AND JL_ENR.AI_TRN >= ? ");
                }
                if (ejournalCriteria.isSearchByToSequenceNumber())
                {
                    query.append("AND JL_ENR.AI_TRN <= ? ");
                }
            }
            if (ejournalCriteria.isSearchByFromEndDateTime())
            {
                query.append("AND JL_ENR.TS_JRL_END >= ? ");
            }
            if (ejournalCriteria.isSearchByToEndDateTime())
            {
                query.append("AND JL_ENR.TS_JRL_END <= ? ");
            }
            if (ejournalCriteria.isSearchByBusinessDate())
            {
                query.append("AND JL_ENR.DC_DY_BSN = ? ");
            }

        }
        return query.toString();
    }

    /**
     *
     */
    private String addToWhereClause(TransactionCriteria transactionCriteria)
    {
        StringBuilder query = new StringBuilder();
        if (transactionCriteria != null && transactionCriteria.use())
        {
            if (transactionCriteria.isSearchByTransactionTotal())
            {
                query.append(" AND TR_RTL.MO_NT_TOT = ? ");
            }
            if (transactionCriteria.isSearchByTransactionNumber())
            {
                query.append("AND TR_TRN.AI_TRN = ? ");
                query.append("AND TR_TRN.ID_WS = ? ");
                query.append("AND TR_TRN.ID_STR_RT = ? ");
            }
            else if (transactionCriteria.isSearchByExternalOrderNumber())
            {
                query.append("AND TR_RTL.ID_ORD_EXT_NMB = ? ");
            }
            else
            {
                if (transactionCriteria.isSearchByFromWorkstationID())
                {
                    query.append("AND TR_TRN.ID_WS >= ? ");
                }
                if (transactionCriteria.isSearchByToWorkstationID())
                {
                    query.append("AND TR_TRN.ID_WS <= ? ");
                }
                if (transactionCriteria.isSearchByFromSequenceNumber())
                {
                    query.append("AND TR_TRN.AI_TRN >= ? ");
                }
                if (transactionCriteria.isSearchByToSequenceNumber())
                {
                    query.append("AND TR_TRN.AI_TRN <= ? ");
                }
            }
            if (transactionCriteria.isSearchByFromEndDateTime())
            {
                query.append("AND TR_TRN.TS_TRN_BGN >= ? ");
            }
            if (transactionCriteria.isSearchByToEndDateTime())
            {
                query.append("AND TR_TRN.TS_TRN_END <= ? ");
            }
            if (transactionCriteria.isSearchBybusnessDateEnd())
            {
                query.append("AND TR_TRN.DC_DY_BSN <= ? ");
            }
            if (transactionCriteria.isSearchBybusnessDateStart())
            {
                query.append("AND TR_TRN.DC_DY_BSN >= ? ");
            }
            if (transactionCriteria.isSearchByTransactionType())
            {
                query.append("AND TR_TRN.TY_TRN IN (");
                int[] types = transactionCriteria.getTransactionTypes();
                for (int i = 0; i < types.length; i++)
                {
                    query.append("?,");
                }
                query.setCharAt(query.length() - 1, ')');
            }
            if (transactionCriteria.isSearchByBusinessDate())
            {
                query.append("AND TR_TRN.DC_DY_BSN = ? ");
            }
            if (StringUtils.isNotEmpty(transactionCriteria.getTrainingMode())
                    && !transactionCriteria.getTrainingMode().equalsIgnoreCase("both"))
            {
                query.append("AND TR_TRN.FL_TRG_TRN = ? ");
            }
            if (StringUtils.isNotEmpty(transactionCriteria.getReentryMode())
                    && !transactionCriteria.getReentryMode().equalsIgnoreCase("both"))
            {
                query.append("AND TR_TRN.FL_TRE_TRN = ? ");
            }
            if (transactionCriteria.isSearchByTransactionStatus())
            {
                query.append("AND TR_TRN.SC_TRN = ? ");
            }
            if (transactionCriteria.isSearchByVoidedTransaction())
            {
                if (transactionCriteria.getExcludeVoidedTransactions())
                {
                    query.append("AND TR_VD_PST.AI_TRN_VD is null ");
                }
                else
                {
                    query.append("AND TR_VD_PST.AI_TRN_VD is not null ");
                }
            }
        }
        return query.toString();
    }

    /**
     *
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index, EJournalCriteria ejournalCriteria)
            throws SQLException
    {
        if (ejournalCriteria != null && ejournalCriteria.use())
        {
            java.sql.Date date;
            if (ejournalCriteria.isSearchByTransactionNumber())
            {
                TransactionKey key = new TransactionKey();
                try
                {
                    key = getTransactionKeyFormatter().parse(ejournalCriteria.getTransactionNumber());
                }
                catch (ParseException e)
                {

                }
                if (getLogger().isDebugEnabled())
                    bindVariables.add(new Integer(key.getSequenceNumber()));
                preparedStatement.setInt(index++, key.getSequenceNumber());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(key.getWorkstationID());
                preparedStatement.setString(index++, key.getWorkstationID());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(key.getStoreID());
                preparedStatement.setString(index++, key.getStoreID());
            }
            else
            {
                if (ejournalCriteria.isSearchByFromWorkstationID())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(ejournalCriteria.getFromWorkstationID());
                    preparedStatement.setString(index++, ejournalCriteria.getFromWorkstationID());
                }
                if (ejournalCriteria.isSearchByToWorkstationID())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(ejournalCriteria.getToWorkstationID());
                    preparedStatement.setString(index++, ejournalCriteria.getToWorkstationID());
                }
                if (ejournalCriteria.isSearchByFromSequenceNumber())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(new Integer(ejournalCriteria.getFromSequenceNumber()));
                    preparedStatement.setInt(index++, ejournalCriteria.getFromSequenceNumber());
                }
                if (ejournalCriteria.isSearchByToSequenceNumber())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(new Integer(ejournalCriteria.getToSequenceNumber()));
                    preparedStatement.setInt(index++, ejournalCriteria.getToSequenceNumber());
                }
            }
            if (ejournalCriteria.isSearchByFromEndDateTime())
            {
                date = new java.sql.Date(ejournalCriteria.getFromEndDateTime().getTime());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(date);
                getDBUtils().preparedStatementSetDate(preparedStatement, index++, date);
            }
            if (ejournalCriteria.isSearchByToEndDateTime())
            {
                date = new java.sql.Date(ejournalCriteria.getToEndDateTime().getTime());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(date);
                getDBUtils().preparedStatementSetDate(preparedStatement, index++, date);
            }
            if (ejournalCriteria.isSearchByBusinessDate())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(ejournalCriteria.getBusinessDate());
                preparedStatement.setString(index++, ejournalCriteria.getBusinessDate());
            }
        }
        return index;
    }

    /**
     *
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index, TransactionCriteria transactionCriteria)
            throws SQLException
    {
        if (transactionCriteria != null && transactionCriteria.use())
        {
            java.sql.Date date;
            if (transactionCriteria.isSearchByTransactionTotal())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(transactionCriteria.getTransactionTotal());
                preparedStatement.setBigDecimal(index++, transactionCriteria.getTransactionTotal());
            }
            if (transactionCriteria.isSearchByTransactionNumber())
            {

                TransactionKey key = new TransactionKey();
                try
                {
                    key = getTransactionKeyFormatter().parse(transactionCriteria.getTransactionNumber());
                }
                catch (ParseException e)
                {

                }
                if (getLogger().isDebugEnabled())
                    bindVariables.add(new Integer(key.getSequenceNumber()));
                preparedStatement.setInt(index++, key.getSequenceNumber());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(key.getWorkstationID());
                preparedStatement.setString(index++, key.getWorkstationID());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(key.getStoreID());
                preparedStatement.setString(index++, key.getStoreID());
            }
            if (transactionCriteria.isSearchByExternalOrderNumber())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(transactionCriteria.getExternalOrderNumber());
                preparedStatement.setString(index++, transactionCriteria.getExternalOrderNumber());
            }
            else
            {
                if (transactionCriteria.isSearchByFromWorkstationID())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(transactionCriteria.getFromWorkstationID());
                    preparedStatement.setString(index++, transactionCriteria.getFromWorkstationID());
                }
                if (transactionCriteria.isSearchByToWorkstationID())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(transactionCriteria.getToWorkstationID());
                    preparedStatement.setString(index++, transactionCriteria.getToWorkstationID());
                }
                if (transactionCriteria.isSearchByFromSequenceNumber())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(new Integer(transactionCriteria.getFromSequenceNumber()));
                    preparedStatement.setInt(index++, transactionCriteria.getFromSequenceNumber());
                }
                if (transactionCriteria.isSearchByToSequenceNumber())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(new Integer(transactionCriteria.getToSequenceNumber()));
                    preparedStatement.setInt(index++, transactionCriteria.getToSequenceNumber());
                }
            }
            if (transactionCriteria.isSearchByFromEndDateTime())
            {
                date = new java.sql.Date(transactionCriteria.getFromEndDateTime().getTime());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(date);
                getDBUtils().preparedStatementSetDate(preparedStatement, index++, date);
            }
            if (transactionCriteria.isSearchByToEndDateTime())
            {
                date = new java.sql.Date(transactionCriteria.getToEndDateTime().getTime());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(date);
                getDBUtils().preparedStatementSetDate(preparedStatement, index++, date);
            }
            if (transactionCriteria.isSearchBybusnessDateEnd())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(transactionCriteria.getBusnessDateEnd());
                preparedStatement.setString(index++, transactionCriteria.getBusnessDateEnd());
            }
            if (transactionCriteria.isSearchBybusnessDateStart())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(transactionCriteria.getBusnessDateStart());
                preparedStatement.setString(index++, transactionCriteria.getBusnessDateStart());
            }
            if (transactionCriteria.isSearchByTransactionType())
            {
                int[] types = transactionCriteria.getTransactionTypes();
                for (int i = 0; i < types.length; i++)
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(String.valueOf(types[i]));
                    preparedStatement.setString(index++, String.valueOf(types[i]));
                }
            }
            if (transactionCriteria.isSearchByBusinessDate())
            {
                preparedStatement.setString(index++, transactionCriteria.getBusinessDate());
            }
            if (StringUtils.isNotEmpty(transactionCriteria.getTrainingMode())
                    && transactionCriteria.getTrainingMode().equalsIgnoreCase("on"))
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add("1");
                preparedStatement.setString(index++, "1");
            }
            if (StringUtils.isNotEmpty(transactionCriteria.getTrainingMode())
                    && transactionCriteria.getTrainingMode().equalsIgnoreCase("off"))
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add("0");
                preparedStatement.setString(index++, "0");
            }
            if (StringUtils.isNotEmpty(transactionCriteria.getReentryMode())
                    && transactionCriteria.getReentryMode().equalsIgnoreCase("on"))
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add("1");
                preparedStatement.setString(index++, "1");
            }
            if (StringUtils.isNotEmpty(transactionCriteria.getReentryMode())
                    && transactionCriteria.getReentryMode().equalsIgnoreCase("off"))
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add("0");
                preparedStatement.setString(index++, "0");
            }
            if (transactionCriteria.isSearchByTransactionStatus())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(transactionCriteria.getTransactionStatus());
                preparedStatement.setInt(index++, transactionCriteria.getTransactionStatus().intValue());
            }
        }
        return index;
    }

    /**
     * Adds SQL FROM clause to a Transaction search query based on tender criteria
     * @param criteria TenderCriteria
     * @return String SQL clause to be added
     */
    private String addToFromClause(TenderCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            //cash tenders don't have a child table
            query.append(" join TR_LTM_TND on ");
            query.append("  TR_LTM_TND.ID_STR_RT = TR_TRN.ID_STR_RT");
            query.append(" AND TR_LTM_TND.ID_WS = TR_TRN.ID_WS");
            query.append(" AND TR_LTM_TND.DC_DY_BSN = TR_TRN.DC_DY_BSN");
            query.append(" AND TR_LTM_TND.AI_TRN = TR_TRN.AI_TRN");
            if (criteria.isSearchByTenderID()) // place this sanity check here to avoid SQL syntax errors
            {
                if (criteria.isSearchByType(TenderType.CREDIT) || criteria.isSearchByType(TenderType.DEBIT))
                {
                    query.append(" left join TR_LTM_CRDB_CRD_TN on ");
                    query.append("  TR_LTM_TND.ID_STR_RT = TR_LTM_CRDB_CRD_TN.ID_STR_RT ");
                    query.append(" AND TR_LTM_TND.ID_WS = TR_LTM_CRDB_CRD_TN.ID_WS ");
                    query.append(" AND TR_LTM_TND.DC_DY_BSN = TR_LTM_CRDB_CRD_TN.DC_DY_BSN ");
                    query.append(" AND TR_LTM_TND.AI_TRN = TR_LTM_CRDB_CRD_TN.AI_TRN ");
                }
                if (criteria.isSearchByType(TenderType.CHECK) || criteria.isSearchByType(TenderType.ECHECK))
                {
                    query.append(" left join TR_LTM_CHK_TND on ");
                    query.append("  TR_LTM_TND.ID_STR_RT = TR_LTM_CHK_TND.ID_STR_RT ");
                    query.append(" AND TR_LTM_TND.ID_WS = TR_LTM_CHK_TND.ID_WS ");
                    query.append(" AND TR_LTM_TND.DC_DY_BSN = TR_LTM_CHK_TND.DC_DY_BSN ");
                    query.append(" AND TR_LTM_TND.AI_TRN = TR_LTM_CHK_TND.AI_TRN ");
                }
                if (criteria.isSearchByType(TenderType.GIFTCARD))
                {
                    query.append(" left join TR_LTM_GF_CRD_TND on ");
                    query.append("  TR_LTM_TND.ID_STR_RT = TR_LTM_GF_CRD_TND.ID_STR_RT ");
                    query.append(" AND TR_LTM_TND.ID_WS = TR_LTM_GF_CRD_TND.ID_WS ");
                    query.append(" AND TR_LTM_TND.DC_DY_BSN = TR_LTM_GF_CRD_TND.DC_DY_BSN ");
                    query.append(" AND TR_LTM_TND.AI_TRN = TR_LTM_GF_CRD_TND.AI_TRN ");
                }
                if (criteria.isSearchByType(TenderType.STORECREDIT))
                {
                    query.append(" left join TR_LTM_CR_STR_TND on ");
                    query.append("  TR_LTM_TND.ID_STR_RT = TR_LTM_CR_STR_TND.ID_STR_RT ");
                    query.append(" AND TR_LTM_TND.ID_WS = TR_LTM_CR_STR_TND.ID_WS ");
                    query.append(" AND TR_LTM_TND.DC_DY_BSN = TR_LTM_CR_STR_TND.DC_DY_BSN ");
                    query.append(" AND TR_LTM_TND.AI_TRN = TR_LTM_CR_STR_TND.AI_TRN ");
                }
            }
        }
        getLogger().debug("Tender from clause = " + query.toString());

        return query.toString();
    }

    /**
     * Adds SQL WHERE clause to a Transaction search query based on tender criteria
     * @param criteria TenderCriteria
     * @return String SQL clause to be added
     */
    private String addToWhereClause(TenderCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            Collection searchTypes = criteria.getSearchTypes();
            if (!searchTypes.isEmpty())
            {
                query.append(" AND TR_LTM_TND.TY_TND IN (");
                int s = searchTypes.size();
                for (int i = 0; i < s; i++)
                {
                    query.append("?");
                    if (i < (s - 1))
                    {
                        query.append(", ");
                    }
                }
                query.append(") ");
            }
            if (criteria.isSearchByTenderID())
            {
                query.append(" AND (");
                boolean or = false;
                if (criteria.isSearchByType(TenderType.CREDIT) || criteria.isSearchByType(TenderType.DEBIT))
                {
                    if (criteria.getAccountNumberToken() != null)
                    {
                        query.append(" TR_LTM_CRDB_CRD_TN.LU_ACNT_CRD_TKN = ?");
                    }
                    else
                    {
                        query.append(" TR_LTM_CRDB_CRD_TN.ID_MSK_ACNT_CRD LIKE (?)");
                    }
                    or = true;
                }
                if (criteria.isSearchByType(TenderType.CHECK)|| criteria.isSearchByType(TenderType.ECHECK))
                {
                    if (or)
                    {
                        query.append(" OR");
                    }
                    query.append(" TR_LTM_CHK_TND.NR_MSK_MICR like (?)");
                    or = true;
                }
                if (criteria.isSearchByType(TenderType.GIFTCARD))
                {
                    if (or)
                    {
                        query.append(" OR");
                    }
                    query.append(" TR_LTM_GF_CRD_TND.ID_MSK_ACNT_CRD like (?)");
                    or = true;
                }
                if (criteria.isSearchByType(TenderType.STORECREDIT))
                {
                    if (or)
                    {
                        query.append(" OR");
                    }
                    query.append(" TR_LTM_CR_STR_TND.ID_CR_STR = ?");
                    or = true;
                }

                // Add other tender types here. But first modify addToFromClause(TenderCriteria)

                query.append(" )");
            }
            if (criteria.isSearchByMaximumTenderAmount())
            {
                query.append(" AND TR_LTM_TND.MO_ITM_LN_TND <= ? ");
            }
            if (criteria.isSearchByMinimumTenderAmount())
            {
                query.append(" AND TR_LTM_TND.MO_ITM_LN_TND >= ? ");
            }
        }
        getLogger().debug("Tender where clause = " + query.toString());
        return query.toString();
    }

    /**
     * @throws DataException
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index, TenderCriteria criteria)
            throws SQLException, DataException
    {
        if (criteria != null)
        {
            for (TenderType tenderType : criteria.getSearchTypes())
            {
                preparedStatement.setString(index++, tenderType.toString());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(tenderType);
            }
            if (criteria.isSearchByTenderID())
            {
                if (criteria.isSearchByType(TenderType.CREDIT) || criteria.isSearchByType(TenderType.DEBIT))
                {
                    String tenderID = criteria.getAccountNumberToken();
                    if (tenderID == null)
                    {
                        //replace pan masking character with SQL wildcard character used in "LIKE" expressions
                        tenderID = StringUtils.replace(criteria.getMaskedAccountNumber(), getPanMask(), "%");
                    }
                    preparedStatement.setString(index++, tenderID);
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(tenderID);
                }

                if (criteria.isSearchByType(TenderType.CHECK) || criteria.isSearchByType(TenderType.ECHECK))
                {
                    //micrNumber: replace pan masking character with SQL wildcard character used in "LIKE" expressions
                    String micrNumber = StringUtils.replace(criteria.getMaskedMicrNumber(), getPanMask(), "%");
                    preparedStatement.setString(index++, micrNumber);
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(micrNumber);
                }
                if (criteria.isSearchByType(TenderType.GIFTCARD))
                {
                   //replace pan masking character with SQL wildcard character used in "LIKE" expressions
                    String giftCardNumber = StringUtils.replace(criteria.getMaskedGiftCardNumber(), getPanMask(), "%");
                    preparedStatement.setString(index++, giftCardNumber);
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(giftCardNumber);
                }
                if (criteria.isSearchByType(TenderType.STORECREDIT))
                {
                    String storeCreditNumber = criteria.getStoreCreditNumber();
                    preparedStatement.setString(index++, storeCreditNumber);
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(storeCreditNumber); // it's okay to debug store credit numbers
                }
            }
            if (criteria.isSearchByMaximumTenderAmount())
            {
                preparedStatement.setBigDecimal(index++, criteria.getMaximumTenderAmount());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getMaximumTenderAmount());
            }
            if (criteria.isSearchByMinimumTenderAmount())
            {
                preparedStatement.setBigDecimal(index++, criteria.getMinimumTenderAmount());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getMinimumTenderAmount());
            }
        }
        return index;
    }

    /**
     * Tender Criteria
     */
    private String addToFromClause(SignatureCaptureCriteria criteria, boolean alreadyJoined)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null && !alreadyJoined)
        {
            query.append(" left join TR_LTM_CRDB_CRD_TN on ");
            query.append("  TR_LTM_CRDB_CRD_TN.ID_STR_RT = TR_TRN.ID_STR_RT");
            query.append(" AND TR_LTM_CRDB_CRD_TN.ID_WS = TR_TRN.ID_WS");
            query.append(" AND TR_LTM_CRDB_CRD_TN.DC_DY_BSN = TR_TRN.DC_DY_BSN");
            query.append(" AND TR_LTM_CRDB_CRD_TN.AI_TRN = TR_TRN.AI_TRN");
        }
        return query.toString();
    }

    /**
     * Binds values in the Prepared Statement
     *
     * @param PreparedStatement Prepared Statement
     * @param index Index
     * @param SignatureCaptureCriteria Signature Capture Criteria
     * @return int
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index, SignatureCaptureCriteria criteria)
            throws SQLException
    {
        if (criteria != null && !criteria.getTenderTypeAccepted().equals("All"))
        {
            preparedStatement.setString(index++, criteria.getTenderTypeAccepted());
            if (getLogger().isDebugEnabled())
                bindVariables.add(criteria.getTenderTypeAccepted());
        }
        return index;
    }

    /**
     * LineItemCriteria Criteria
     */
    private String addToFromClause(LineItemCriteria criteria)
    {
        StringBuilder buffer = new StringBuilder();
        if (criteria != null && criteria.use())
        {
            buffer.append(" JOIN TR_LTM_RTL_TRN ON TR_LTM_RTL_TRN.ID_STR_RT = TR_TRN.ID_STR_RT AND TR_LTM_RTL_TRN.ID_WS = TR_TRN.ID_WS AND TR_LTM_RTL_TRN.DC_DY_BSN = TR_TRN.DC_DY_BSN AND TR_LTM_RTL_TRN.AI_TRN = TR_TRN.AI_TRN ");
            buffer.append(" JOIN TR_LTM_SLS_RTN ON TR_TRN.ID_STR_RT = TR_LTM_SLS_RTN.ID_STR_RT AND TR_TRN.ID_WS = TR_LTM_SLS_RTN.ID_WS AND TR_TRN.DC_DY_BSN = TR_LTM_SLS_RTN.DC_DY_BSN AND TR_TRN.AI_TRN = TR_LTM_SLS_RTN.AI_TRN ");
            if (criteria.isSearchByPriceOverride())
            {
                buffer.append(" JOIN CO_MDFR_RTL_PRC");
                buffer.append(" ON CO_MDFR_RTL_PRC.ID_STR_RT = TR_TRN.ID_STR_RT");
                buffer.append(" AND CO_MDFR_RTL_PRC.ID_WS = TR_TRN.ID_WS");
                buffer.append(" AND CO_MDFR_RTL_PRC.DC_DY_BSN = TR_TRN.DC_DY_BSN");
                buffer.append(" AND CO_MDFR_RTL_PRC.AI_TRN = TR_TRN.AI_TRN ");
            }
        }
        return buffer.toString();
    }

    /**
     *
     */
    private String addToWhereClause(LineItemCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null && criteria.use())
        {
            if (criteria.isSearchByItemNumber())
            {
                query.append(" AND ( TR_LTM_SLS_RTN.ID_ITM=?");
                query.append("    OR TR_LTM_SLS_RTN.ID_ITM in (select distinct ID_ITM from TR_LTM_SLS_RTN where ID_ITM_POS=?) )");
            }
            if (criteria.isSearchByItemID())
            {
                query.append(" AND TR_LTM_SLS_RTN.ID_ITM=?");
            }
            if (criteria.isSearchByUPC())
            {
                query.append(" AND TR_LTM_SLS_RTN.ID_ITM_POS=?");
            }
            if (criteria.isSearchByPriceOverride())
            {
                query.append(" AND CO_MDFR_RTL_PRC.CD_MTH_PRDV = 0 AND CO_MDFR_RTL_PRC.CD_TY_PRDV = 0");
            }
            if (criteria.isSearchByItemCleared())
            {
                query.append(" AND TR_LTM_RTL_TRN.FL_VD_LN_ITM =?");
            }
            if (criteria.isSearchBySerialNumber())
            {
                query.append(" AND TR_LTM_SLS_RTN.ID_NMB_SRZ =?");
            }
            if (criteria.isSearchByLineItemType())
            {
                if (criteria.getLineItemType() == LineItemCriteria.LINEITEM_SALE_TYPE)
                {
                    query.append(" AND TR_LTM_SLS_RTN.QU_ITM_LM_RTN_SLS > 0");
                    query.append(" AND TR_LTM_SLS_RTN.FL_ITM_PRC_ADJ = '0'");
                }
                else if (criteria.getLineItemType() == LineItemCriteria.LINEITEM_RETURN_TYPE)
                {
                    query.append(" AND TR_LTM_SLS_RTN.QU_ITM_LM_RTN_SLS < 0");
                    query.append(" AND TR_LTM_SLS_RTN.FL_ITM_PRC_ADJ = '0'");
                }
                else if (criteria.getLineItemType() == LineItemCriteria.LINEITEM_PRICEADJUSTMENT_TYPE)
                {
                    query.append(" AND TR_LTM_SLS_RTN.FL_ITM_PRC_ADJ = '1'");
                }
            }
        }
        return query.toString();
    }

    /**
     *
     */
    private int setBindVariables(PreparedStatement statement, int index, LineItemCriteria criteria) throws SQLException
    {
        if (criteria != null && criteria.use())
        {
            if (criteria.isSearchByItemNumber())
            {
                if (getLogger().isDebugEnabled())
                {
                    bindVariables.add(criteria.getItemNumber());
                    bindVariables.add(criteria.getItemNumber());
                }
                statement.setString(index++, criteria.getItemNumber());
                statement.setString(index++, criteria.getItemNumber());
            }
            if (criteria.isSearchByItemID())
            {
                if (getLogger().isDebugEnabled())
                {
                    bindVariables.add(criteria.getItemID());
                }
                statement.setString(index++, criteria.getItemID());
            }
            if (criteria.isSearchByUPC())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getUPC());
                statement.setString(index++, criteria.getUPC());
            }
            if (criteria.isSearchByItemCleared())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(getDBUtils().toString(criteria.isItemCleared()));
                statement.setString(index++, getDBUtils().toString(criteria.isItemCleared()));
            }
            if (criteria.isSearchBySerialNumber())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getSerialNumber());
                statement.setString(index++, criteria.getSerialNumber());
            }
        }
        return index;
    }

    /**
     * SalesAssociateCriteria
     */
    private String addToFromClause(SalesAssociateCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            if (criteria.isSalesAssociateSearch())
            {
                query.append(" LEFT OUTER JOIN CO_MDFR_CMN on ");
                query.append(" TR_TRN.ID_STR_RT = CO_MDFR_CMN.ID_STR_RT ");
                query.append(" AND TR_TRN.ID_WS = CO_MDFR_CMN.ID_WS ");
                query.append(" AND TR_TRN.DC_DY_BSN = CO_MDFR_CMN.DC_DY_BSN ");
                query.append(" AND TR_TRN.AI_TRN = CO_MDFR_CMN.AI_TRN");

            }
        }
        return query.toString();
    }

    /**
     * TaxLineItemCriteria
     */
    private String addToFromClause(TaxLineItemCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            query.append(" join TR_LTM_TX on ");
            query.append(" TR_TRN.ID_STR_RT = TR_LTM_TX.ID_STR_RT ");
            query.append(" AND TR_TRN.ID_WS = TR_LTM_TX.ID_WS ");
            query.append(" AND TR_TRN.DC_DY_BSN = TR_LTM_TX.DC_DY_BSN ");
            query.append(" AND TR_TRN.AI_TRN = TR_LTM_TX.AI_TRN");
        }
        return query.toString();
    }

    /**
     *
     */
    private String addToWhereClause(SalesAssociateCriteria criteria)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            if (criteria.isSalesAssociateSearch())
            {
                query.append(" AND ( CO_MDFR_CMN.ID_EM = ? ");
                query.append("  OR TR_TRN.ID_EM = ? )");
            }
            if (criteria.isCashierSearch())
            {
                query.append(" AND TR_TRN.ID_OPR = ? ");
            }
        }
        return query.toString();
    }

    /**
     *
     * @param preparedStatement
     * @param index
     * @param criteria
     * @param setJoinClause apply the SalesAssociateNumber to the JOIN clause for CO_MDFR_CMN
     * @return
     * @throws SQLException
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index,
            SalesAssociateCriteria criteria, boolean setJoinClause)
            throws SQLException
    {
        if (criteria != null)
        {
            if (criteria.isSalesAssociateSearch())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getSalesAssociateNumber());
                preparedStatement.setString(index++, criteria.getSalesAssociateNumber());
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getSalesAssociateNumber());
                preparedStatement.setString(index++, criteria.getSalesAssociateNumber());
            }
            if (!setJoinClause && criteria.isCashierSearch())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getCashierNumber());
                preparedStatement.setString(index++, criteria.getCashierNumber());
            }
        }
        return index;
    }

    // Customer search criteria
    /**
     */
    private String addToFromClause(CustomerCriteria criteria, boolean alreadyJoinedTR_RTL)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null && criteria.use())
        {
            if (getLogger().isDebugEnabled())
                getLogger().debug("using customer info to search adding to from");
            if (!alreadyJoinedTR_RTL)
            {
                query.append(" join TR_RTL on ");
                query.append("  TR_RTL.ID_STR_RT = TR_TRN.ID_STR_RT ");
                query.append(" AND TR_RTL.ID_WS = TR_TRN.ID_WS ");
                query.append(" AND TR_RTL.DC_DY_BSN = TR_TRN.DC_DY_BSN ");
                query.append(" AND TR_RTL.AI_TRN = TR_TRN.AI_TRN ");
            }
        }
        return query.toString();
    }

    /**
     *
     */
    private String addToWhereClause(CustomerCriteria criteria, Collection<CustomerSearchResult> customerIds)
    {
        StringBuilder query = new StringBuilder();
        if (criteria != null && criteria.use())
        {
            if (getLogger().isDebugEnabled())
                getLogger().debug("using customer info to search adding to where ");
            query.append(" AND TR_RTL.ID_CT in (");

            for (Iterator<CustomerSearchResult> i = customerIds.iterator(); i.hasNext();)
            {
                CustomerSearchResult cust = i.next();
                query.append("'").append(cust.getCustomerID()).append("'");
                if (i.hasNext())
                {
                    query.append(",");
                }
            }
            query.append(") ");
        }
        return query.toString();
    }

    /**
     *
     *
     */
    private int setBindVariables(PreparedStatement preparedStatement, int index, CustomerCriteria criteria)
            throws SQLException
    {
        if (criteria != null && criteria.use())
        {
            if (getLogger().isDebugEnabled())
                getLogger().debug("using customer info to search setting bv");
            if (criteria.isSearchByCustomerID())
            {
                if (getLogger().isDebugEnabled())
                    bindVariables.add(criteria.getCustomerID());
                preparedStatement.setString(index++, criteria.getCustomerID());
            }
            if (criteria.isSearchByAddressInfo())
            {
                if (criteria.isSearchByCustomerAddressLine1())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressLine1().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerAddressLine1().toUpperCase());
                }
                if (criteria.isSearchByCustomerAddressLine2())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressLine2().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerAddressLine2().toUpperCase());
                }
                if (criteria.isSearchByCustomerAddressCity())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressCity().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerAddressCity().toUpperCase());
                }
                if (criteria.isSearchByCustomerAddressState())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressState().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerAddressState().toUpperCase());
                }
                if (criteria.isSearchByCustomerAddressCountry())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressCountry().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerAddressCountry().toUpperCase());
                }
                if (criteria.isSearchByCustomerAddressZipCode() && criteria.isSearchByCustomerAddressZipCodeExt())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressZipCode().toUpperCase() + "-"
                                + criteria.getCustomerAddressZipCodeExt().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerAddressZipCode().toUpperCase() + "-"
                            + criteria.getCustomerAddressZipCodeExt().toUpperCase());
                }
                else if (criteria.isSearchByCustomerAddressZipCode())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerAddressZipCode().toUpperCase() + "%");
                    preparedStatement.setString(index++, criteria.getCustomerAddressZipCode().toUpperCase() + "%");
                }
                else if (criteria.isSearchByCustomerAddressZipCodeExt())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add("%" + criteria.getCustomerAddressZipCodeExt().toUpperCase());
                    preparedStatement.setString(index++, "%" + criteria.getCustomerAddressZipCodeExt().toUpperCase());
                }
                if (criteria.isSearchByCustomerTelephoneNumber())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerTelephoneNumber().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerTelephoneNumber());
                }
            }
            if (criteria.isSearchByContactInfo())
            {
                if (criteria.isSearchByCustomerFirstName())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerFirstName().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerFirstName().toUpperCase());
                }
                if (criteria.isSearchByCustomerLastName())
                {
                    if (getLogger().isDebugEnabled())
                        bindVariables.add(criteria.getCustomerLastName().toUpperCase());
                    preparedStatement.setString(index++, criteria.getCustomerLastName().toUpperCase());
                }
            }
        }
        return index;
    }

    /**
     * A message driven bean calls this method when it recieves a message
     * on the the transactin queue.  It then determines if it has a Replication
     * message or a POSLog message and call the appropriate method.
     *
     * @param xml String POSLog (XML) to be imported
     * @return int number of imported transactions. <= 0 if none found.
     */
    public int importTransactions(String xml)
    {
        if (isDocumentDataReplication(xml))
        {
            return importReplicationXml(xml);
        }
        else
        {
            return importPOSLogXml(xml);
        }
    }

    /**
     * This method determines if the XML string is an XML
     * Replication document.
     *
     * @param xml String POSLog (XML) to be imported
     * @return true if it is an XML Replication document.
     */
    protected boolean isDocumentDataReplication(String xml)
    {
        String segment = xml.substring(0, 75);
        if (segment.length() > 75)
        {
            segment = segment.substring(0, 75);
        }
        boolean ret = false;
        boolean found = false;

        int index = segment.indexOf('>') + 1;

        for (int i = index; i < segment.length(); i++)
        {
            if (segment.charAt(i) == '<')
            {
                index = i;
                found = true;
                i = segment.length();
            }
        }

        if (found && segment.startsWith("<BATCH ", index))
        {
            ret = true;
        }

        return ret;
    }

    /**
     * This method imports an XML Replication document.
     *
     * @param xml String POSLog (XML) to be imported
     * @return true if it is an XML Replication document.
     */
    protected int importReplicationXml(String xml)
    {
        if (replicationHandler != null)
        {
            Connection conn = null;

            try
            {
                conn = getDBUtils().getConnection();
                replicationHandler.setConnection(conn);

                // Create an input source that describes the file to parse.
                // Then tell the parser to parse input from that source
                org.xml.sax.InputSource input = new InputSource(new StringReader(xml));
                replicationParser.parse(input);
            }
            catch (SAXException se)
            {
                // Just rethrow. All exeptions have already been logged.
                Exception e = se;
                if (se.getCause() instanceof ReplicationImportException)
                {
                    e = (ReplicationImportException)se.getCause();
                }
                throw new EJBException("Error parsing XML Data Replication import XML document.", e);
            }
            catch (Exception e)
            {
                // An unexpected error log and throw EJBException
                getLogger().error("Error parsing XML Data Replication import XML document.", e);
                throw new EJBException("Error parsing XML Data Replication import XML document.", e);
            }
            finally
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (Throwable eth)
                    {
                        // do nothing
                    }
                }
            }

            return replicationHandler.getTransactionCount();
        }
        else
        {
            getLogger().error("Configuration setup failed; transactions cannot be imported.");
            throw new EJBException("Configuration setup failed; transactions cannot be imported.");
        }
    }

    /**
     * This method gets the import factory object.
     *
     * @return ImporterObjectFactoryIfc
     */
    protected ImporterObjectFactoryIfc getReplicationImportFactory()
    {
        String className = getReplicationImportFactoryName();
        ImporterObjectFactoryIfc factory = null;
        try
        {
            Class<?> clazz = Class.forName(className);
            factory = (ImporterObjectFactoryIfc)clazz.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            getLogger().error("Failed to find class for ImporterObjectFactoryIfc: " + className, e);
            throw new EJBException(e);
        }
        catch (InstantiationException e)
        {
            getLogger().error("Failed to Instantiate class for ImporterObjectFactoryIfc: " + className, e);
            throw new EJBException(e);
        }
        catch (IllegalAccessException e)
        {
            getLogger().error(
                    "Failed to Instantiate class (illegal access) for ImporterObjectFactoryIfc: " + className, e);
            throw new EJBException(e);
        }

        return factory;
    }

    protected String getReplicationImportFactoryName()
    {
        String className = "oracle.retail.stores.xmlreplication.ImporterObjectFactory";

        try
        {
            className = (String)getContext().lookup("java:comp/env/ReplicationImportObjectFactory");
            getLogger().debug("ReplicationImportObjectFactory is " + className);
        }
        catch (NamingException e)
        {
            getLogger().error("Cannot find ReplicationImportObjectFactory.  Using default " + className, e);
        }

        return className;
    }

    /**
     * This method gets the import factory object.
     *
     * @return ImporterObjectFactoryIfc
     */
    protected ExtractorObjectFactoryIfc getReplicationExtractFactory()
    {
        String className = getReplicationExtractFactoryName();
        ExtractorObjectFactoryIfc factory = null;
        try
        {
            Class<?> clazz = Class.forName(className);
            factory = (ExtractorObjectFactoryIfc)clazz.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            getLogger().error("Failed to find class for ExtractorObjectFactoryIfc: " + className, e);
            throw new EJBException(e);
        }
        catch (InstantiationException e)
        {
            getLogger().error("Failed to Instantiate class for ExtractorObjectFactoryIfc: " + className, e);
            throw new EJBException(e);
        }
        catch (IllegalAccessException e)
        {
            getLogger().error(
                    "Failed to Instantiate class (illegal access) for ExtractorObjectFactoryIfc: " + className, e);
            throw new EJBException(e);
        }

        return factory;
    }

    protected String getReplicationExtractFactoryName()
    {
        String className = "oracle.retail.stores.xmlreplication.ExtractorObjectFactory";

        try
        {
            className = (String)getContext().lookup("java:comp/env/ReplicationExtractObjectFactory");
            getLogger().debug("ReplicationImportObjectFactory is " + className);
        }
        catch (NamingException e)
        {
            getLogger().error("Cannot find ReplicationImportObjectFactory.  Using default " + className, e);
        }

        return className;
    }

    /**
     *  This method gets the name of the Replication Import Configuration File;
     *  if the value is not available from the descriptor file it uses the default.
     *  @return String file name
     */
    protected String getReplicationImportConfigFileName()
    {
        String fileName = "oracle/retail/stores/commerceservices/transaction/ejb/ReplicationImportConfig.xml";

        try
        {
            fileName = (String)getContext().lookup("java:comp/env/ReplicationImportConfigFile");
            getLogger().debug("ReplicationImportConfigFile is " + fileName);
        }
        catch (NamingException e)
        {
            getLogger().error("Cannot find ReplicationImportObjectFactory.  Using default " + fileName, e);
        }

        return fileName;
    }

    /**
     *  This method reads the file reads the configuration file and returns an object
     *  containing the configuration.
     *  @param configName the configuration file name
     *  @return EntityReaderCatalog database configuration information
     */
    protected TablePersistanceConfigurationCatalogIfc configureCatalog(String configName)
            throws ReplicationImportException
    {
        TablePersistanceConfiguratorIfc config = null;
        TablePersistanceConfigurationCatalogIfc catalog = null;
        try
        {
            // I have tried several ways to get a stream from this file including:
            //      stream = getClass().getResourceAsStream(configName);
            // which I found in several other EJBs in the application.  However,
            // the kluge that follows is the only one that I could get work.
            InputStream stream = null;
            ClassLoader loader = getClass().getClassLoader();
            Enumeration<URL> rs = loader.getResources(configName);
            if (rs.hasMoreElements())
            {
                URL url = rs.nextElement();
                stream = url.openStream();
            }
            config = ReplicationObjectFactoryContainer.getInstance().getImporterObjectFactory()
                    .getTablePersistanceConfiguratorInstance();
            catalog = config.configureWriterCatalog(stream);
        }
        catch (IOException e)
        {
            getLogger().error("IO error reading " + configName + "for Data Replicaiton import configuration", e);
            throw new ReplicationImportException("IO error reading " + configName
                    + "for Data Replicaiton import configuration", e);
        }

        return catalog;
    }

    /**
     * Using XML parser, converts POSLog XML to DOM, and kicks-off
     * POSLog import processing.
     *
     * @param xml String POSLog (XML) to be imported
     * @return int number of imported transactions. <= 0 if none found.
     */
    protected int importPOSLogXml(String xml)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        //factory.setValidating(true);
        //factory.setNamespaceAware(true);

        try
        {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            throw new EJBException("Cannot create XML document builder.", e);
        }
        Document document = null;
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes(EncodingIfc.UTF8));
            document = builder.parse(bis);
            bis.close();
        }
        catch (SAXException e)
        {
            throw new EJBException("Error parsing XML document.", e);
        }
        catch (IOException e)
        {
            throw new EJBException("Error parsing XML document.", e);
        }

        return importPOSLogXml(document);
    }

    /**
     * Handles import of POSLog transactions into Central Office database.
     *
     * Throws EJBException on error. If these reach the EJB container,
     * a container-managed transaction rollback happens.
     * Note: One or more POSlog XML <Transaction> blocks may comprise a
     * database or EJB-container "transaction"
     *
     * @param document org.w3c.dom.Document created from POSLog XML
     * @return int number of imported transactions. <= 0 if none found.
     */
    public int importPOSLogXml(Document document)
    {
        int importCount = -1;
        Connection conn = null;
        try
        {
            if (!CommerceservicesGateway.isBaseCurrencyCountryCodeSet())
            {
                // Must call currency service here. It will initialize currency types in
                // CommerceservicesGateway that will be used in POSLog import.
                getCurrencyService().getCurrencyTypes();
            }
            conn = getDBUtils().getConnection();
            String databaseProduct = conn.getMetaData().getDatabaseProductName().toUpperCase();
            Element root = document.getDocumentElement();
            String tagName = root.getTagName();
            // check if it is a v1.0 XML document:
            if (tagName.equals("POS360LogContainer"))
            {
                JdbcSaveIXRetailTransactionCSIfc jdbcTrans = getJdbcSaveIXRetailTransaction(databaseProduct, "1.0");
                NodeList startNodeList = document.getElementsByTagName("POS360Transaction");
                for (importCount = 0; importCount < startNodeList.getLength(); importCount++)
                {
                    Node attr = startNodeList.item(importCount);
                    jdbcTrans.insertTransaction(conn, (Element)attr);
                    saveSignatureFromPOSLog((Element)attr);
                    //if (getLogger().isDebugEnabled())
                    // getLogger().debug(trans.buildBaseSQLStatement((Element)attr,null).getSQLString());
                }
            }
            // check if it is a v2.1 XML document:
            else if (tagName.equals("POSLog"))
            {
                // v2.1 does not have a version number attribute in the root
                // element....I am assuming v2.2 will have one.
                // check attribute for v2.2 or later version, and act accordingly
                // otherwise, drop through to v2.1 processing:
                JdbcSaveIXRetailTransactionCSIfc jdbcTrans = getJdbcSaveIXRetailTransaction(databaseProduct, "2.1");
                jdbcTrans.setCustomerExtensionNamespace(getCustomerExtensionNamespace(root));
                NodeList startNodeList = document.getElementsByTagName("Transaction");
                TransactionImportRemote transactionImport = getTransactionImport();
                for (importCount = 0; importCount < startNodeList.getLength(); importCount++)
                {
                    // For POSlog v1.x we abort processing of entire log when an
                    // exception is thrown....
                    // Here we continue processing remaining POSlog transactions.
                    Node attr = null;
                    try
                    {
                        attr = startNodeList.item(importCount);
                        transactionImport.importTransaction(jdbcTrans, (Element)attr);
                        //jdbcTrans.insertTransaction(conn, (Element) attr);
                    }
                    catch (Exception e)
                    {
                        getLogger().error(attr, e);

                        // log transaction XML to special POSlog category
                        // so custom appender can handle errors
                        Logger poslogLogger = Logger.getLogger("POSlog");
                        XMLSerializer xmlSerializer = new XMLSerializer();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        xmlSerializer.setOutputByteStream(bos);
                        xmlSerializer.serialize((Element)attr);
                        bos.close();
                        poslogLogger.error(bos.toString());
                    }
                }
            }
            else
            {
                throw new DataException("Root element not recognised:" + root.getLocalName());
            }
        }
        catch (DataException e)
        {
            getLogger().error("Transaction " + String.valueOf(importCount) + " in POSlog Document", e);
            throw new EJBException(e);
        }
        catch (SQLException e)
        {
            getLogger().error("Transaction " + String.valueOf(importCount) + " in POSlog Document", e);
            throw new EJBException(e);
        }
        catch (Exception e)
        {
            getLogger().error("Transaction " + String.valueOf(importCount) + " in POSlog Document", e);
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(conn, null, null);
        }
        return importCount;
    }

    /**
     * Gets customer-extension XML namespace, from POSlog.
     *
     * @param root
     *           org.w3c.dom.Element - root XML element of POSlog. The namespace
     *           is an attribute of it. The attribute's name is got from EJB
     *           descriptor
     * @return value of the namespace (XML) attribute
     */
    private String getCustomerExtensionNamespace(Element root)
    {
        try
        {
            String custExtKey = (String)getContext().lookup("java:comp/env/customerNamespaceKey");
            return root.getAttribute(custExtKey);
        }
        catch (NamingException e)
        {
            return null;
        }
    }

    /**
     * Save signature from POSlog
     *
     * @param pos org.w3c.dom.Element
     */
    private void saveSignatureFromPOSLog(Element pos)
    {
        String signatureFormat = getSignatureFormat();
        NodeList lineItems = pos.getElementsByTagName("LineItem");
        for (int lineItemCount = 0; lineItemCount < lineItems.getLength(); lineItemCount++)
        {
            Element lineItem = (Element)lineItems.item(lineItemCount);
            if ("Tender".equals(lineItem.getAttribute("LineItemType")))
            {
                NodeList tenders = lineItem.getElementsByTagName("Tender");
                if (tenders.getLength() > 0)
                {
                    Element tender = (Element)tenders.item(0);
                    if ("Credit".equals(getValueFromParent(tender, "TenderID")))
                    {
                        NodeList authorizations = tender.getElementsByTagName("Authorization");
                        if (authorizations.getLength() > 0)
                        {
                            Element authorization = (Element)authorizations.item(0);
                            String signature = getValueFromParent(authorization, "ElectronicSignatureImage");
                            if (StringUtils.isNotEmpty(signature))
                            {
                                try
                                {
                                    byte[] sigbytes = null;
                                    if ("image/png".equals(signatureFormat))
                                    {
                                        sigbytes = ImageUtils.getInstance().decode(signature);
                                    }
                                    else if ("image/svg+xml".equals(signatureFormat))
                                    {
                                        sigbytes = signature.getBytes();
                                    }
                                    String store = getValueFromParent(pos, "RetailStoreID");
                                    String workstation = getValueFromParent(pos, "WorkstationID");
                                    Date date = DateTimeUtils.getInstance().parseBusinessDate(
                                            getValueFromParent(pos, "BusinessDayDate"));
                                    int sequence = Integer.parseInt(getValueFromParent(pos, "SequenceNumber"));
                                    int lineItemSeq = Integer.parseInt(getValueFromParent(lineItem, "SequenceNumber"));
                                    CreditDebitLineItemLocal local = getDebitCreditHome().findByPrimaryKey(
                                            new TenderLineItemKey(store, workstation, sequence, date, lineItemSeq));
                                    local.setCustomerSignatureImage(sigbytes);
                                }
                                catch (ParseException e)
                                {
                                    getLogger().error("", e);
                                }
                                catch (NumberFormatException e)
                                {
                                    getLogger().error("", e);
                                }
                                catch (FinderException e)
                                {
                                    getLogger().error("", e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private String getValueFromParent(Element parent, String tag)
    {
        String value = "";
        try
        {
            Element child = null;
            if (parent != null && tag != null)
            {
                NodeList list = parent.getElementsByTagName(tag);
                child = (Element)list.item(0);
            }
            if (child != null && child.getFirstChild() != null)
            {
                Node node = child.getFirstChild();
                value = node.getNodeValue();
            }
        }
        catch (Exception e)
        {
        }
        return value;
    }

    private String getSignatureFormat()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter("SignatureFormat", "image/png");
            List<String> values = param.getValues();
            return values.get(0);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * Returns the object responsible for saving POSLog transaction to database
     *
     * @param databaseProduct String description of current database
     * @param schemaVersion   String symbol for POSLog version ("1.0" or "2.1")
     * @return oracle.retail.stores.commerceservices.xmltosql.JdbcSaveIXRetailTransactionCSIfc
     */
    private JdbcSaveIXRetailTransactionCSIfc getJdbcSaveIXRetailTransaction(String databaseProduct, String schemaVersion)
    {
        try
        {
            if (jdbcSaveIXRetailTransaction == null)
            {
                if (schemaVersion.equals("1.0"))
                {
                    jdbcSaveIXRetailTransaction = new JdbcSaveIXRetailTransactionCS();
                }
                else if (schemaVersion.equals("2.1"))
                {
                    jdbcSaveIXRetailTransaction = (JdbcSaveIXRetailTransactionCS)Class.forName(
                            getJDBCTransactionClassName()).newInstance();
                }

                // Set the JDBC helper class appropriate to the database
                jdbcSaveIXRetailTransaction.setHelper(databaseProduct);
            }
            return jdbcSaveIXRetailTransaction;
        }
        catch (ClassNotFoundException e)
        {
            getLogger().debug("", e);
            throw new EJBException(e);
        }
        catch (InstantiationException e)
        {
            getLogger().debug("", e);
            throw new EJBException(e);
        }
        catch (IllegalAccessException e)
        {
            getLogger().debug("", e);
            throw new EJBException(e);
        }
    }

    private String getJDBCTransactionClassName()
    {
        try
        {
            return (String)getContext().lookup("java:comp/env/jdbcTransactionClassName");
        }
        catch (NamingException e)
        {
            return "oracle.retail.stores.commerceservices.xmltosql.v21.JdbcSaveIXRetailTransactionCS";
        }
    }

    /**
     * Imports signature capture information and creates entries in necessary tables for search/display.
     *
     * @param xml
     */
    public void importSignatureCapture(String xml)
    {
        Connection conn = null;
        PreparedStatement ps = null;
        try
        {
            JAXBContext jc = getJAXBContext();
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Signature signature = (Signature)unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
            TransactionHeader transactionHeader = signature.getTransactionHeader();
            String storeId = transactionHeader.getRetailStoreID();
            String workstationId = transactionHeader.getWorkstationID();
            int sequence = transactionHeader.getTransactionSequenceNumber().intValue();
            String businessDate = transactionHeader.getBusinessDayDate();
            BigDecimal grandTotal = new BigDecimal(transactionHeader.getTransactionTotal().toString());
            String transactionType = transactionHeader.getTransactionTypeCode();
            TransactionKey transactionKey = new TransactionKey(storeId, workstationId, sequence, businessDate);
            getTransactionHome().create(transactionKey.getStoreID(), transactionKey.getWorkstationID(),
                    transactionKey.getFormattedDateString(), transactionKey.getSequenceNumber(),
                    transactionHeader.getOperatorId(), TransactionType.getType(transactionType).toString(),
                    transactionHeader.getStartTime().toGregorianCalendar().getTime(),
                    transactionHeader.getEndTime().toGregorianCalendar().getTime());
            getRetailTransactionHome().create(transactionKey.getStoreID(), transactionKey.getWorkstationID(),
                    transactionKey.getFormattedDateString(), transactionKey.getSequenceNumber(), "", "", "", "",
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, grandTotal,
                    BigDecimal.ZERO, BigDecimal.ZERO, "", "", "", "", "", "", "", new Date(), new Date());
            List<SignatureEntry> signatures = signature.getSignatureEntry();
            for (SignatureEntry signatureEntryType : signatures)
            {
                String cardType = signatureEntryType.getCardType();
                //String authMethod = "N/A";
                String dataEntryMethod = "MSR";

                getDebitCreditHome().create(storeId, workstationId, sequence, transactionKey.getDate(),
                        signatureEntryType.getTenderLineItemSequenceNumber().intValue(), null,
                        signatureEntryType.getCreditDebitCardAuthorizationMethod(), null,
                        signatureEntryType.getCreditDebitCardAdjudicationCode(), null,
                        signatureEntryType.getSignatureImage(),dataEntryMethod,
                        signatureEntryType.getCardType(), TenderType.CREDIT.toString(),
                        signatureEntryType.getAccountNumberToken(),
                        signatureEntryType.getMaskedCreditDebitCardAccountNumber());
            }
        }
        catch (ParseException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        catch (CreateException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        catch (JAXBException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(conn, ps, null);
        }
    }

    /**
     * Import the XML string containing the EJ and persist it to the DB.
     */
    public void importEJournal(String xml)
    {
        try
        {
            JAXBContext jc = getJAXBContext();

            //Monica
                       if (xml.contains("&"))
                       {
                         xml = xml.replaceAll("&", "&amp");
                       }
                       
                       //End Monica
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Ejournal ejournal = (Ejournal)unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
            List<EjournalEntry> ejournals = ejournal.getEjournalEntry();
            for (EjournalEntry ejournalEntryType : ejournals)
            {
            	
                if (getLogger().isDebugEnabled())
                    getLogger().debug("importing journal of type " + ejournalEntryType);
                EJournalDTO dto = new EJournalDTO(ejournalEntryType.getRetailStoreID(), ejournalEntryType
                        .getWorkstationID(), ejournalEntryType.getBusinessDayDate().toGregorianCalendar().getTime(), ejournalEntryType
                        .getSequenceNumber().intValue(), ejournalEntryType.getStartTime().toGregorianCalendar().getTime(), ejournalEntryType
                        .getEndTime().toGregorianCalendar().getTime(), ejournalEntryType.getType(), ejournalEntryType.isNormalCompletion(),
                        ejournalEntryType.getTape().getContent().size() > 0 ?(String)ejournalEntryType.getTape().getContent().get(0): "");
                getEJHome().create(dto);
            }
        }
        catch (JAXBException e)
        {
            throw new EJBException(e);
        }
        catch (CreateException e)
        {
            throw new EJBException(e);
        }
    }

    /**
     * retrieves an ejournal dto for the specified transaction id
     *
     * a TransactionKey is adequate to identify an EJournal entry generated for a transaction
     */
    public EJournalDTO retrieveEJournalEntry(TransactionKey key)
    {
        try
        {
            EJournalLocal local = getEJHome().findTransactional(key);
            return local.toDTO();
        }
        catch (FinderException e)
        {
            throw new EJBException(e.getMessage());
        }
    }

    /**
     * retrieves an ejournal dto for the specified ejournal id
     *
     * For non-transactional EJournals, you must use an EJournalKey to uniquely identify the entry
     *
     * because non-transactional EJournals entries have "0000" as their sequence number
     *
     * the EJournalKey also includes the transaction begin time (in the PK)
     *
     */
    public EJournalDTO retrieveEJournalEntry(EJournalKey key)
    {
        try
        {
            EJournalLocal local = getEJHome().findByPrimaryKey(key);
            return local.toDTO();
        }
        catch (FinderException e)
        {
            throw new EJBException(e.getMessage());
        }
    }

    /**
     * If dto.operatorID is not empty, attempts to sets dto.operatorName
     * with value from EmployeeIfc.
     *
     * @param transactionDTO
     */
    protected void setEmployeeName(TransactionDTO transactionDTO)
    {
        String id = transactionDTO.getOperatorID();
        if (StringUtils.isNotEmpty(id))
        {
            try
            {
                EmployeeServiceRemote employeeService = getEmployeeService();
                EmployeeDTO employeeDTO = employeeService.getEmployee(id);
                transactionDTO.setOperatorName(employeeDTO.getEmployeeName());
            }
            catch (EmployeeNotFoundException e)
            {
                getLogger().error("could not find Employee for id: " + id + "\n" + e.getMessage());
            }
            catch (RemoteException e)
            {
                getLogger().error("unable to access EmployeeService", e);
            }
        }
    }

    /**
     * Gets the "MaximumSearchResults" parameter from the parameter service. If
     * the service doesn't exist it is an error, if the parameter itself doesn't
     * exist the default value of 100 is used.
     */
    private int getMaximumResults()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter("MaximumSearchResults", "100");
            List<String> values = param.getValues();
            String paramValue = values.get(0);
            return Integer.parseInt(paramValue);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * Gets the parameter from the parameter service.  if the service doesn't exist it is an error, if the parameter
     * itself doesn't exist the default value is used
     */
    private int getMaximumTransactionSequenceNumber()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter(
                    "TransactionIDSequenceNumberMaximum", "9999");
            List values = param.getValues();
            String paramValue = (String) values.get(0);
            return Integer.parseInt(paramValue);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * Gets the parameter from the parameter service.  if the service doesn't exist it is an error, if the parameter
     * itself doesn't exist the default value is used
     */
    private boolean isSequenceNumberSkipZeroFlag()
    {
        try
        {
            return getParameterService().getBooleanParameter(
                    "TransactionIDSequenceNumberSkipZero", false)
                    .booleanValue();
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * Gets the StoreIDLength parameter from the parameter service.  if the service doesn't exist it is an error, if the parameter
     * itself doesn't exist the default value is used
     *
     * This value is used to parse the POS transaction id
     */
    private int getStoreIDLength()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter(
                    "StoreIDLength", "5");
            List values = param.getValues();
            String paramValue = (String) values.get(0);
            return Integer.parseInt(paramValue);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * Gets the WorkstationIDLength parameter from the parameter service.  if the service doesn't exist it is an error, if the parameter
     * itself doesn't exist the default value is used
     *
     * This value is used to parse the POS transaction id
     */
    private int getWorkstationIDLength()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter(
                    "WorkstationIDLength", "3");
            List values = param.getValues();
            String paramValue = (String) values.get(0);
            return Integer.parseInt(paramValue);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * Gets the SequenceNumberLength parameter from the parameter service.  if the service doesn't exist it is an error, if the parameter
     * itself doesn't exist the default value is used
     *
     * This value is used to parse the POS transaction id
     */
    private int getSequenceNumberLength()
    {
        try
        {
            ParameterIfc param = getParameterService().getApplicationParameter(
                    "SequenceNumberLength", "4");
            List values = param.getValues();
            String paramValue = (String) values.get(0);
            return Integer.parseInt(paramValue);
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new EJBException(e);
        }
    }

    /**
     * @param storeId       the store id
     * @param workstationId the workstation id
     * @return the next transaction sequence number
     * @throws NextTransactionSeqNumNotFoundException
     *
     */
    private int getNextSequenceNumber(String storeId, String workstationId)
            throws NextTransactionSeqNumNotFoundException
    {
        WorkstationPK workstationPK = new WorkstationPK(storeId, workstationId);
        WorkstationLocal workstation;
        try
        {
            workstation = getWorkstationLocalHome().findByPrimaryKey(workstationPK);
        }
        catch (FinderException e)
        {
            throw new NextTransactionSeqNumNotFoundException("Failed to get next transaction sequence number", e,
                    NextTransactionSeqNumNotFoundException.ERROR_KEY);
        }

        // increment the current transaction sequence number
        int transactionSequenceNumber = workstation.incrementTransactionSequenceNumber(
                getMaximumTransactionSequenceNumber(), isSequenceNumberSkipZeroFlag());
        return transactionSequenceNumber;
    }

    /**
     * @param storeId       the store id
     * @param workstationId the workstation id
     * @param businessDay   the businessDay
     * @throws NextTransactionSeqNumNotFoundException
     *
     */
    private void updateBusinessDay(String storeId, String workstationId, Date businessDay)
            throws NextTransactionSeqNumNotFoundException
    {
        WorkstationPK workstationPK = new WorkstationPK(storeId, workstationId);
        WorkstationLocal workstation;
        try
        {
            workstation = getWorkstationLocalHome().findByPrimaryKey(workstationPK);
        }
        catch (FinderException e)
        {
            throw new NextTransactionSeqNumNotFoundException("Failed to update the business day", e,
                    NextTransactionSeqNumNotFoundException.ERROR_KEY);
        }
        // update businessDayDate
        workstation.setBusinessDayDate(DateTimeUtils.getInstance().formatBusinessDate(businessDay));
    }

    /**
     * Lookup the EmployeeService
     */
    private EmployeeServiceRemote getEmployeeService()
    {
        return (EmployeeServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/EmployeeService",
                EmployeeServiceHome.class);
    }

    private String getStoreName(String storeID, Locale locale) throws FinderException
    {
        try
        {
            RetailStore store = getStoreDirectoryService().getStore(storeID, locale);
            return store.getName();
        }
        catch (RemoteException e)
        {
            this.getLogger().error("Failed to get store name, StoreId=" + storeID + e);
            throw new EJBException(e);
        }
    }

    private EJournalLocalHome getEJHome()
    {
        return (EJournalLocalHome)getServiceLocator().getLocalHome("java:comp/env/ejb/EJournal");
    }

    private CreditDebitLineItemLocalHome getDebitCreditHome()
    {
        return (CreditDebitLineItemLocalHome)getServiceLocator().getLocalHome("java:comp/env/ejb/CreditDebitLineItem");
    }

    private ParameterServiceRemote getParameterService()
    {
        return (ParameterServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/ParameterService",
                ParameterServiceHome.class);
    }

    private StoreDirectoryRemote getStoreDirectoryService()
    {
        return (StoreDirectoryRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/StoreDirectory",
                StoreDirectoryHome.class);
    }

    private CodeListServiceRemote getCodeListService()
    {
        return (CodeListServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/CodeListService",
                CodeListServiceHome.class);
    }


    private NoSaleTransactionLocalHome getNoSaleTransactionHome()
    {
        return (NoSaleTransactionLocalHome)getServiceLocator().getLocalHome(NoSaleTransactionLocalHome.EJB_REF);
    }

    private VoidTransactionLocalHome getVoidTransactionLocalHome()
    {
        return (VoidTransactionLocalHome)getServiceLocator().getLocalHome(VoidTransactionLocalHome.EJB_REF);
    }

    private TillTransactionLocalHome getTillTransactionLocalHome()
    {
        return (TillTransactionLocalHome)getServiceLocator().getLocalHome(TillTransactionLocalHome.EJB_REF);
    }

    private TillLoanTransactionLocalHome getTillLoanTransactionLocalHome()
    {
        return (TillLoanTransactionLocalHome)getServiceLocator().getLocalHome(TillLoanTransactionLocalHome.EJB_REF);
    }

    private TillPickupTransactionLocalHome getTillPickupTransactionLocalHome()
    {
        return (TillPickupTransactionLocalHome)getServiceLocator().getLocalHome(TillPickupTransactionLocalHome.EJB_REF);
    }

    private RegisterTransactionLocalHome getRegisterTransactionHome()
    {
        return (RegisterTransactionLocalHome)getServiceLocator().getLocalHome(RegisterTransactionLocalHome.EJB_REF);
    }

    private RetailTransactionLocalHome getRetailTransactionHome()
    {
        return (RetailTransactionLocalHome)getServiceLocator().getLocalHome(RetailTransactionLocalHome.EJB_REF);
    }

    private TaxExemptionLocalHome getTaxExemptionHome()
    {
        return (TaxExemptionLocalHome)getServiceLocator().getLocalHome(TaxExemptionLocalHome.EJB_REF);
    }

    private StoreTransactionLocalHome getStoreTransactionHome()
    {
        return (StoreTransactionLocalHome)getServiceLocator().getLocalHome(StoreTransactionLocalHome.EJB_REF);
    }

    private ControlTransactionLocalHome getControlTransactionHome()
    {
        return (ControlTransactionLocalHome)getServiceLocator().getLocalHome(ControlTransactionLocalHome.EJB_REF);
    }

    private TransactionLocalHome getTransactionHome()
    {
        return (TransactionLocalHome)getServiceLocator().getLocalHome(TransactionLocalHome.EJB_REF);
    }

    private RedeemTransactionLocalHome getRedeemTransactionHome()
    {
        return (RedeemTransactionLocalHome)getServiceLocator().getLocalHome(RedeemTransactionLocalHome.EJB_REF);
    }

    private InstantCreditTransactionLocalHome getInstantCreditTransactionHome()
    {
        return (InstantCreditTransactionLocalHome)getServiceLocator().getLocalHome(
                InstantCreditTransactionLocalHome.EJB_REF);
    }

    private TenderServiceRemote getTenderService()
    {
        return (TenderServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/TenderService",
                TenderServiceHome.class);
    }

    /**
     * @return currency service bean remote interface
     */
    private CurrencyServiceRemote getCurrencyService()
    {
        return (CurrencyServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/CurrencyService",
                CurrencyServiceHome.class);
    }

    private PaymentLocalHome getPaymentHome()
    {
        return (PaymentLocalHome)getServiceLocator().getLocalHome(PaymentLocalHome.EJB_REF);
    }

    private LayawayLocalHome getLayawayHome()
    {
        return (LayawayLocalHome)getServiceLocator().getLocalHome(LayawayLocalHome.EJB_REF);
    }

    private OrderLocalHome getOrderHome()
    {
        return (OrderLocalHome)getServiceLocator().getLocalHome(OrderLocalHome.EJB_REF);
    }

    private OrderLineItemLocalHome getOrderLineItemLocalHome()
    {
        return (OrderLineItemLocalHome)getServiceLocator().getLocalHome(OrderLineItemLocalHome.EJB_REF);
    }

    private WorkstationLocalHome getWorkstationLocalHome()
    {
        return (WorkstationLocalHome)getServiceLocator().getLocalHome("java:comp/env/ejb/Workstation");
    }

    /**
     * Retrieves the local home object for the SaleReturnLineItem entity
     *
     * @return a SaleReturnLineItemLocalHome
     */
    private SaleReturnLineItemLocalHome getSaleReturnLineItemLocalHome()
    {
        return (SaleReturnLineItemLocalHome)getServiceLocator().getLocalHome("java:comp/env/ejb/SaleReturnLineItem");
    }

    /**
     * Retrieves the local home object for the SaleReturnLineItemTax entity
     *
     * @return a SaleReturnLineItemTaxLocalHome
     */
    private SaleReturnLineItemTaxLocalHome getSaleReturnLineItemTaxLocalHome()
    {
        return (SaleReturnLineItemTaxLocalHome)getServiceLocator().getLocalHome(SaleReturnLineItemTaxLocalHome.EJB_REF);
    }

    private DiscountLineItemLocalHome getDiscountLineItemLocalHome()
    {
        return (DiscountLineItemLocalHome)getServiceLocator().getLocalHome("java:comp/env/ejb/DiscountLineItem");
    }

    private OrderRecipientDetailLocalHome getOrderRecipientDetailLocalHome()
    {
        return (OrderRecipientDetailLocalHome)getServiceLocator()
                .getLocalHome("java:comp/env/ejb/OrderRecipientDetail");
    }

    private PriceModifierLocalHome getPriceModifierLocalHome()
    {
        return (PriceModifierLocalHome)getServiceLocator().getLocalHome("java:comp/env/ejb/PriceModifier");
    }

    private SaleReturnShippingRecordLocalHome getSaleReturnShippingRecordLocalHome()
    {
        return (SaleReturnShippingRecordLocalHome)getServiceLocator().getLocalHome(
                SaleReturnShippingRecordLocalHome.EJB_REF);
    }

    private SaleReturnShippingRecordTaxLocalHome getSaleReturnShippingRecordTaxLocalHome()
    {
        return (SaleReturnShippingRecordTaxLocalHome)getServiceLocator().getLocalHome(
                SaleReturnShippingRecordTaxLocalHome.EJB_REF);
    }

    private FinancialAccountingTransactionLocalHome getFinancialAccountingTransactionLocalHome()
    {
        return (FinancialAccountingTransactionLocalHome)getServiceLocator().getLocalHome(
                FinancialAccountingTransactionLocalHome.EJB_REF);
    }

    private FundReceiptTransactionLocalHome getFundReceiptTransactionLocalHome()
    {
        return (FundReceiptTransactionLocalHome)getServiceLocator().getLocalHome(
                FundReceiptTransactionLocalHome.EJB_REF);
    }

    private GiftCardLocalHome getGiftCardLocalHome()
    {
        return (GiftCardLocalHome)getServiceLocator().getLocalHome(GiftCardLocalHome.EJB_REF);
    }

    /**
     * @return CustomerServiceRemote
     */
    private CustomerServiceRemote getCustomerService()
    {
        return (CustomerServiceRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/CustomerService",
                CustomerServiceHome.class);
    }

    /**
     * @return TransactionImportRemote
     */
    private TransactionImportRemote getTransactionImport()
    {
        return (TransactionImportRemote)getServiceLocator().getRemoteService("java:comp/env/ejb/TransactionImport",
                TransactionImportHome.class);
    }

    public void ejbCreate() throws CreateException
    {
        super.ejbCreate();
        getLogger().debug("[Entry]");

        // Initialize the importer and extractor object factories and set it on the
        // ReplicationOjectFactoryContainer;  The container is singleton
        // which gives all classes access to the factories.
        if (ReplicationObjectFactoryContainer.getInstance().getImporterObjectFactory() == null)
        {
            ImporterObjectFactoryIfc iFactory = getReplicationImportFactory();
            ExtractorObjectFactoryIfc eFactory = getReplicationExtractFactory();
            ReplicationObjectFactoryContainer.getInstance().setImporterObjectFactory(iFactory);
            ReplicationObjectFactoryContainer.getInstance().setExtractorObjectFactory(eFactory);
        }

        // Initialize the the transaction import ob
        try
        {
            // Read the XML configuration file and store the contents
            // in an EntityReaderCatalog.
            String configFileName = getReplicationImportConfigFileName();
            getLogger().debug("File format name: " + configFileName);
            TablePersistanceConfigurationCatalogIfc catalog = configureCatalog(configFileName);

            // Create the parser we'll use.  The parser implementation is a
            // Xerces class, but we use it only through the SAX XMLReader API
            replicationParser = new org.apache.xerces.parsers.SAXParser();

            // Specify that we don't want validation.  This is the SAX2
            // API for requesting parser features.  Note the use of a
            // globally unique URL as the feature name.  Non-validation is
            // actually the default, so this line isn't really necessary.
            replicationParser.setFeature("http://xml.org/sax/features/validation", false);

            // Instantiate this class to provide handlers for the parser and
            // tell the parser about the handlers
            replicationHandler = new XMLReplicationImporterHandler();
            replicationHandler.setConfigurationCatalog(catalog);
            replicationParser.setContentHandler(replicationHandler);
            replicationParser.setErrorHandler(replicationHandler);

        }
        catch (ReplicationImportException rie)
        {
            // Errors have already been logged
        }
        catch (Exception e)
        {
            getLogger().error("Error configuring XML Data Replication import XML document.", e);
        }

        // Configure Returns Export Catalog
        buildEntityReaderCatalog(getReplicationExportConfigFileName());

        getLogger().debug("[Exit]");
    }

    /**
     * Retrieves a set of transaction information given search criteria as input.
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @return returnLimit
     * @param optionVal Tender Types Values
     * @throws DataException
     */
    public TransactionSearchResultDTO getTransactions(StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int pageSize, String[] optionVal)
            throws SearchResultSizeExceededException
    {
        return getTransactions(storeSelectionCriteria, searchCriteria, startIndex, pageSize, optionVal, TransactionServiceIfc.REQUEST_TYPE.USER_REQUEST);
    }

    /**
     * Retrieves a set of transaction information given search criteria as input.
     *
     * @param storeSelectionCriteria
     * @param searchCriteria
     * @param startIndex
     * @param pageSize
     * @param optionVal Tender Types Values
     * @param requestType - is the request from a user or from POS returns
     * @return returnLimit
     * @throws DataException
     */
    public TransactionSearchResultDTO getTransactions(StoreSelectionCriteria storeSelectionCriteria,
            SearchCriteria searchCriteria, int startIndex, int pageSize, String[] optionVal,
            TransactionServiceIfc.REQUEST_TYPE requestType)
            throws SearchResultSizeExceededException
    {
        TransactionSearchResultDTO results = new TransactionSearchResultDTO();

        // determine size of results list
        if (pageSize <= 0)
        {
            // return empty list if endIndex is < startIndex
            return results;
        }

        try
        {
            Collection<CustomerSearchResult> customers = null;
            if (searchCriteria.getCustomerCriteria() != null && searchCriteria.getCustomerCriteria().use())
            {
                customers = getCustomers(searchCriteria.getCustomerCriteria());
            }

            if (customers == null || customers.size() > 0)
            {
                // perform query
                Collection<TransactionSearchDTO> list = new ArrayList<TransactionSearchDTO>(pageSize);
                StoreSelectionCriteria storeSearchCriteria = getStoreSearchCriteria(storeSelectionCriteria, requestType);
                int totalCount = findBySearchCriteria(list, storeSearchCriteria, searchCriteria,
                        customers, startIndex, pageSize, optionVal);

                results.setTransactions(list.toArray(new TransactionSearchDTO[list.size()]));
                results.setTotalResultCount(totalCount);
                results.setStartRowIndex(startIndex);
                results.setPageSize(pageSize);
            }
        }
        catch (DataException e)
        {
            throw new EJBException(e);
        }
        catch (SearchResultSizeExceededException e)
        {
            throw e;
        }
        catch (SearchException e)
        {
            throw new EJBException(e);
        }
        catch (RemoteException e)
        {
            throw new EJBException(e);
        }
        return results;
    }

    /**
     * Query for Transactions the match the specified criteria. Returns the total number
     * of transactions that match the queury. The results are added to the
     * <code>dtos</code> parameter.
     *
     * @param dtos
     * @param storeSearchCriteria
     * @param searchCriteria
     * @param startIndex
     * @param pageSize
     * @return total number of journals that match the queury
     * @throws SearchResultSizeExceededException if total num is larger than "MaximumSearchResults" parameter
     */
    private int findBySearchCriteria(Collection<TransactionSearchDTO> dtos, StoreSelectionCriteria storeSearchCriteria,
            SearchCriteria searchCriteria, Collection<CustomerSearchResult> customers,
            int startIndex, int pageSize, String[] optionVal)
            throws DataException, SearchResultSizeExceededException
    {
    	

    	String loyalityId = "";
    	if(searchCriteria!=null && searchCriteria.getTransactionCriteria()!=null &&searchCriteria.getTransactionCriteria().getLoyalityId()!=null&&
    			searchCriteria.getTransactionCriteria().getLoyalityId().length()!=0){
    		loyalityId = searchCriteria.getTransactionCriteria().getLoyalityId();
    	
    	/*code changes added by Dharmendra on 28/11/2016 to validate whether to search by loyality id
    	 and accordingly it sets the COLoyalitConstants.isSearchByLoyalityId constant*/
    	
    	searchCriteria.getTransactionCriteria().setSearchByLoyalityId(true);
    	GDYNCOLoyalitConstants.isSearchByLoyalityId = Boolean.TRUE;
    	}else{
			if (searchCriteria != null
					&& searchCriteria.getTransactionCriteria() != null) {
				searchCriteria.getTransactionCriteria().setSearchByLoyalityId(
						false);
			}
			GDYNCOLoyalitConstants.isSearchByLoyalityId = Boolean.FALSE;
    	}
    	logger.debug("COLoyalitConstants.isSearchByLoyalityId "+GDYNCOLoyalitConstants.isSearchByLoyalityId+" loyalityId "+loyalityId);
        // determine size of results list
        if (pageSize <= 0)
        {
            // return empty list if endIndex is < startIndex
            return 0;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int endIndex = startIndex + pageSize - 1;
        try
        {
            conn = getDBUtils().getConnection();
            StringBuilder query = new StringBuilder(
                    "SELECT DISTINCT TR_TRN.ID_STR_RT AS ID_STR_RT, TR_TRN.ID_WS AS ID_WS, TR_TRN.DC_DY_BSN AS DC_DY_BSN, TR_TRN.AI_TRN AS AI_TRN, TR_TRN.TY_TRN AS TY_TRN, TR_TRN.TS_TRN_END AS TS_TRN_END ");
            String orderBy;
            if (searchCriteria.getOrderByCriteria() != null)
            {
                orderBy = searchCriteria.getOrderByCriteria().getOrderByCriteria();
            }
            else
            {
                orderBy = " ORDER BY ID_STR_RT, ID_WS, DC_DY_BSN DESC, AI_TRN DESC";
            }
            ps = createQuery(conn, query, storeSearchCriteria, searchCriteria, customers, orderBy, optionVal);
            
            /*code changes added by Dharmendra on 28/11/2016 to perform a search by loyality id
          	 and accordingly it sets the COLoyalitConstants.isSearchByLoyalityId constant*/

			if (GDYNCOLoyalitConstants.isSearchByLoyalityId) {
				logger.debug("searching by loyality id "+loyalityId);
				String querystr = "SELECT distinct A.ID_STR_RT ID_STR_RT ,A.ID_WS AS ID_WS, A.AI_TRN AS AI_TRN, A.DC_DY_BSN AS DC_DY_BSN, A.TY_TRN AS TY_TRN, A.TS_TRN_END  AS TS_TRN_END FROM(SELECT DISTINCT A.ID_STR_RT AS ID_STR_RT, A.ID_WS AS ID_WS, A.AI_TRN AS AI_TRN, A.DC_DY_BSN AS DC_DY_BSN, A.TY_TRN AS TY_TRN, A.TS_TRN_END AS TS_TRN_END FROM TR_TRN  A, ct_trn_lylt B where A.ID_STR_RT=B.ID_STR_RT  AND  A.ID_WS=B.ID_WS AND  A.AI_TRN=B.AI_TRN AND  A.DC_DY_BSN=B.DC_DY_BSN AND  A.ID_STR_RT=B.ID_STR_RT AND  B.LYLT_ID= ?) A INNER JOIN (SELECT * FROM TR_RTL ) B ON A.ID_STR_RT=B.ID_STR_RT ";

				ps = conn.prepareStatement(querystr);
				ps.setLong(1, new Long(loyalityId));
			}
			
			
          
            rs = ps.executeQuery();
            
            // spin past everything before startIndex
            int i = 1;
            while (i < startIndex)
            {
                if (rs.next())
                {
                    i++;
                }
                else
                {
                    break;
                }
            }

            // only process result set until endIndex is hit
            int j = i;
            while (rs.next())
            {
                if(j <= endIndex)
                {
                    TransactionKey transKey = new TransactionKey(rs.getString("ID_STR_RT"),
                            rs.getString("ID_WS"),
                            rs.getInt("AI_TRN"),
                            rs.getString("DC_DY_BSN"));

                    dtos.add(new TransactionSearchDTO(transKey,
                            getDBUtils().getDateFromResultSet(rs, "TS_TRN_END"),
                            TransactionType.getType(rs.getString("TY_TRN")).toDisplayString(),
                            getStoreName(rs.getString("ID_STR_RT"),
                            searchCriteria.getLocale())));
                }
                j++;
            }

            // if the total results are larger than the "MaximumSearchResults" parameter
            // then throw the SearchResultSizeExceededException.
            if (j > getMaximumResults())
            {
                throw new SearchResultSizeExceededException(j);
            }

            // return total size of query results, which is one less than the index.
            return (j - 1);
        }
        catch (SQLException e)
        {
            getLogger().error(e);
            throw new EJBException(e);
        }
        catch (ParseException e)
        {
            getLogger().error(e);
            throw new EJBException(e);
        }
        catch (FinderException e)
        {
            getLogger().error(e);
            throw new EJBException(e);
        }
        finally
        {
            getDBUtils().close(conn, ps, rs);
        }
    }

    /**
     * Builds a PreparedStatement using SearchCriteria as input.
     *
     * @param conn           - the jdbc connection to use
     * @param query          - contains the query text
     * @param storeSearchCriteria
     * @param searchCriteria - the criteria to search by
     * @param orderBy        - contains the value(s) to order the result set by
     * @param optionVal        Tender Types Values
     * @throws DataException
     */
    private PreparedStatement createQuery(Connection conn, StringBuilder query,
            StoreSelectionCriteria storeSearchCriteria, SearchCriteria searchCriteria,
            Collection<CustomerSearchResult> customers, String orderBy, String[] optionVal)
        throws SQLException, DataException
    {
        query.append(" FROM TR_TRN  ");
        query.append(addToFromClause(storeSearchCriteria, ARTSDatabaseIfc.TABLE_TRANSACTION));
        // TransactionCriteria may require TR_RTL
        query.append(addToFromClause(searchCriteria.getTransactionCriteria()));
        query.append(addToFromClause(searchCriteria.getTenderCriteria()));
        query.append(addToFromClause(searchCriteria.getLineItemCriteria()));
        query.append(addToFromClause(searchCriteria.getSalesAssociateCriteria()));
        query.append(addToFromClause(searchCriteria.getTaxLineItemCriteria()));

        boolean alreadyJoinedTR_RTL = false;
        if (searchCriteria.getTransactionCriteria() != null)
        {
            alreadyJoinedTR_RTL = searchCriteria.getTransactionCriteria().isSearchByTransactionTotal()
                    || searchCriteria.getTransactionCriteria().isSearchByRetailTransactionsOnly()
                    || searchCriteria.getTransactionCriteria().isSearchByExternalOrderNumber();
        }
        boolean alreadyJoinedCredit = false;
        query.append(addToFromClause(searchCriteria.getCustomerCriteria(), alreadyJoinedTR_RTL));
        if (searchCriteria.getTenderCriteria() != null)
        {
            TenderCriteria criteria = searchCriteria.getTenderCriteria();
            if (criteria.isSearchByType(TenderType.CREDIT) &&
                (criteria.getAccountNumberToken() != null || criteria.getMaskedAccountNumber() != null))
            {
                alreadyJoinedCredit = true;
            }
            else
            {
                alreadyJoinedCredit = false;
            }
        }
        query.append(addToFromClause(searchCriteria.getSignatureCaptureCriteria(), alreadyJoinedCredit));

        // dwight jennings : I assume this is to avoid having to conditionally add a where clause into the statement.
        query.append(" WHERE 1=1 ");

        //save off bind variables for debugging SQL
        if (getLogger().isDebugEnabled())
            bindVariables = new ArrayList<Object>(20);

        // where clause
        query.append(addToWhereClause(storeSearchCriteria, ARTSDatabaseIfc.TABLE_TRANSACTION));
        query.append(addToWhereClause(searchCriteria.getTransactionCriteria()));
        query.append(addToWhereClause(searchCriteria.getTenderCriteria()));
        query.append(addToWhereClause(searchCriteria.getLineItemCriteria()));
        query.append(addToWhereClause(searchCriteria.getSalesAssociateCriteria()));
        query.append(addToWhereClause(searchCriteria.getCustomerCriteria(), customers));
        query.append(addToWhereClause(searchCriteria.getSignatureCaptureCriteria(), optionVal));
        query.append(orderBy);

        PreparedStatement ps = conn.prepareStatement(query.toString());

        int n = 1;
        n = setBindVariables(ps, n, storeSearchCriteria);
        n = setBindVariables(ps, n, searchCriteria.getTransactionCriteria());
        n = setBindVariables(ps, n, searchCriteria.getTenderCriteria());
        n = setBindVariables(ps, n, searchCriteria.getLineItemCriteria());
        n = setBindVariables(ps, n, searchCriteria.getSalesAssociateCriteria(), false);
        n = setBindVariables(ps, n, searchCriteria.getSignatureCaptureCriteria());

        if (getLogger().isDebugEnabled())
        {
            getLogger().debug(query);
            getLogger().debug("\nQuery bind variables :\n");
            for (int i = 0; i < bindVariables.size(); i++)
            {
                getLogger().debug((i + 1) + " = " + bindVariables.get(i));
            }
        }
        return ps;
    }

    /**
     * Adds Where clause in the SQL query
     * @param SignatureCaptureCriteria Signature Capture Criteria
     * @param optionVal Tender Type values
     * @return String
     */
    private String addToWhereClause(SignatureCaptureCriteria criteria, String[] optionVal)
    {
        // POS puts card type into issuer...
        StringBuilder query = new StringBuilder();
        if (criteria != null)
        {
            if (optionVal != null && criteria.getTenderTypeAccepted().equals("All"))
            {
                StringBuilder optionValStr = new StringBuilder();
                boolean clauseRequired = false;
                for (int i = 0; i < optionVal.length; i++)
                {
                    if (optionVal[i].equals("All"))
                    {
                        if (optionVal.length > 1)
                        {
                            clauseRequired = true;
                            continue;
                        }

                        break;
                    }
                    optionValStr.append("'").append(optionVal[i]).append("'");
                    if (i < optionVal.length - 1)
                    {
                        optionValStr.append(',');
                    }
                } // end for

                if (clauseRequired)
                {
                    query.append(" AND TR_LTM_CRDB_CRD_TN.ID_ISSR_TND_MD in (").append(optionValStr).append(')');
                }
            }
            else if (!criteria.getTenderTypeAccepted().equals("All"))
            {
                query.append(" AND TR_LTM_CRDB_CRD_TN.ID_ISSR_TND_MD = ?");
            }
        }
        return query.toString();
    }

    /**
     * Get the order receipient Detail order Id
     * @param TransactionKey
     * @return String
     */
    private String getOrderRecipientDetailOrderId(TransactionKey key)
    {
        String orderId = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        try
        {
            connection = getDBUtils().getConnection();
            statement = connection.prepareStatement(GET_ORDER_ID_SQL);

            int index = 1;
            statement.setString(index++, key.getStoreID());
            statement.setString(index++, key.getWorkstationID());
            statement.setInt(index++, key.getSequenceNumber());
            statement.setString(index++, key.getFormattedDateString());

            resultset = statement.executeQuery();

            if (resultset.next())
            {
                orderId = resultset.getString("ID_ORD");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            getLogger().error(ex);
        }
        finally
        {
            getDBUtils().close(connection, statement, resultset);
        }

        return orderId;
    }

    /**
     * Returns billpay transaction dto
     */
    private BillpayTransactionDTO createBillPayTransactionDTO(RetailTransactionLocal transaction, Locale locale)
            throws FinderException
    {
        try
        {
            if (getLogger().isDebugEnabled())
                getLogger().debug("Creating bill pay transaction dto " + transaction);

            TransactionKey key = (TransactionKey) transaction.getPrimaryKey();

            Collection<TenderLineItemDTO> tenderItems = getTenderService().getTenderItemsForTransaction(key);
            Collection<DiscountLineItemDTO> discountLineItems = getDiscountLineItemDTOs(key);
            TaxExemptionDTO taxExemptionDTO = getTaxExemptionRecordForTransaction(key, locale);

            BillpayTransactionDTO dto = new BillpayTransactionDTO(key, null, "", transaction.getCustomerId(),
                    discountLineItems, tenderItems, "", transaction.getSalesTotal(), transaction.getDiscountTotal(),
                    transaction.getTaxTotal(), transaction.getNetTotal(), transaction.getTenderTotal());

            dto.setBills(getBillPayments(key));

            dto.setTaxExemptionDTO(taxExemptionDTO);

            dto.setVoided(isVoided(key));
            if (transaction.getCustomerId() != null)
                dto.setCustomerLinkedToTransaction(true);

            TransactionLocal transactionBase = getTransactionHome().findByPrimaryKey(key);
            dto.setReentryMode(transactionBase.getTransactionReentryFlag());

            return dto;
        }
        catch (RemoteException e)
        {
            getLogger().error("", e);
            throw new FinderException(e.toString());
        }
    }


    /**
     * Get the bill payment details
     * @param TransactionKey
     * @return String
     */
    private ArrayList<BillDTO> getBillPayments(TransactionKey key)
    {
        ArrayList<BillDTO> bills = new ArrayList<BillDTO>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        try
        {
            StringBuilder query = new StringBuilder();
            query.append("SELECT BPAY.ID_ACNT, BPAY.NM_CT, LTM.ID_ACNT_CHLD, LTM.NM_CT_CHLD, LTM.BL_NMB, LTM.MO_PYM_CLT")
                 .append(" FROM TR_RTL_BPAY BPAY, TR_LTM_RTL_BPAY LTM")
                 .append(" WHERE BPAY.AI_TRN = LTM.AI_TRN AND BPAY.ID_STR_RT = LTM.ID_STR_RT")
                 .append(" AND BPAY.ID_WS = LTM.ID_WS AND BPAY.DC_DY_BSN = LTM.DC_DY_BSN")
                 .append(" AND BPAY.ID_STR_RT = ? AND BPAY.ID_WS = ? AND BPAY.AI_TRN = ? AND BPAY.DC_DY_BSN = ?");

            connection = getDBUtils().getConnection();
            statement = connection.prepareStatement(query.toString());

            int index = 1;
            statement.setString(index++, key.getStoreID());
            statement.setString(index++, key.getWorkstationID());
            statement.setInt(index++, key.getSequenceNumber());
            statement.setString(index++, key.getFormattedDateString());

            resultset = statement.executeQuery();

            while(resultset.next())
            {
                BillDTO bill = new BillDTO();
                String accountNumber = resultset.getString(ARTSDatabaseIfc.FIELD_BILLPAY_ACCOUNT_NUMBER);
                String childaccountNumber = resultset.getString(ARTSDatabaseIfc.FIELD_BILLPAY_CHILD_ACCOUNT_NUMBER);
                String customerName = resultset.getString(ARTSDatabaseIfc.FIELD_BILLPAY_CUSTOMER_NAME);
                String childCustomerName = resultset.getString(ARTSDatabaseIfc.FIELD_BILLPAY_CHILD_CUSTOMER_NAME);

                if(childaccountNumber == null || Util.isEmpty(childaccountNumber))
                {
                    bill.setAccountNumber(accountNumber);
                    bill.setCustomerName(customerName);
                }
                else
                {
                    bill.setAccountNumber(childaccountNumber);
                    bill.setCustomerName(childCustomerName);
                }

                bill.setBillNumber(resultset.getString(ARTSDatabaseIfc.FIELD_BILLPAY_BILL_NUMBER));
                bill.setAmountCollected(resultset.getBigDecimal(ARTSDatabaseIfc.FIELD_BILLPAY_PAYMENT_COLLECTED));
                bills.add(bill);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            getLogger().error(ex);
        }
        finally
        {
            getDBUtils().close(connection, statement, resultset);
        }

        return bills;
    }

    /**
     * Return a new search criteria that is the node that the user has access to
     * or contains a list of specific stores.
     *
     * @param storeSelectionCriteria
     * @param requestType
     * @return
     * @throws RemoveException
     * @throws RemoteException
     */
    private StoreSelectionCriteria getStoreSearchCriteria(StoreSelectionCriteria storeSelectionCriteria,
            TransactionServiceIfc.REQUEST_TYPE requestType) throws RemoteException
    {
        StoreDirectoryRemote storeDirectoryService = getStoreDirectoryService();
        boolean isPosReturnsRequest = TransactionServiceIfc.REQUEST_TYPE.POS_RETURN_REQUEST
                .workaroundEquals(requestType);

        // Filter stores by user's hierarchy, only if the request is a user
        // request. Requests from POS returns may not have a valid user.
        HierarchyNodeKey userNode = null;
        if (!isPosReturnsRequest)
        {
            try
            {
                userNode = storeDirectoryService.getHierarchyNodeIDForEmployeeByLoginID(getCallerPrincipalName());
            }
            catch (EmployeeHierarchyNotFoundException e)
            {
                throw new RemoteException("Failed to get store hierarchy for logged in employee.", e);
            }
        }

        StoreSelectionCriteria returnCriteria = null;

        // Find the hierarchy nodes that match the given search criteria
        if (storeSelectionCriteria != null) // uses this as a criteria for searching
        {
            if (storeSelectionCriteria.isHierarchyNodeSearch() && !storeSelectionCriteria.isHierarchyNodeRetailStore())
            {
                // determine whether the selected node or the user's node is deeper.
                HierarchyNodeKey selectedNode = storeSelectionCriteria.getHierarchyNodeKey();
                HierarchyNodeKey deeperNode = storeDirectoryService.getDeeperNode(selectedNode, userNode);
                returnCriteria = new StoreSelectionCriteria(deeperNode);
            }
            else
            {
                // get the numerical list between two stores.
                Set<String> storeIds = storeDirectoryService.getStores(storeSelectionCriteria);
                returnCriteria = new StoreSelectionCriteria(storeIds);
            }
        }
        // If no store criteria was specified in a request coming from POS
        // returns, then use the default hierarchy's stores
        else if (isPosReturnsRequest)
        {

            // look up the default root node.
            try
            {
                returnCriteria = new StoreSelectionCriteria(storeDirectoryService.getDefaultHierarchyNodeID());
            }
            catch (EmployeeHierarchyNotFoundException e)
            {
                throw new RemoteException("Failed to find default root hierarchy node.", e);
            }
        }
        else
        {
            // No search was criteria given. Limit results to only those stores
            // visible to this user
            returnCriteria = new StoreSelectionCriteria(userNode);
        }

        return returnCriteria;
    }

    /**
     * Returns the line item description of the given locale
     * @param lineItem the sale return line item
     * @param locale the locale
     * @return the line item description
     */
    protected String getSaleReturnLineItemDescription(SaleReturnLineItemDTO lineItem, Locale locale)
    {
        String itemDescription = lineItem.getReceiptItemDescription();
        Locale receiptLocale = lineItem.getReceiptItemDescriptionLocale();
        if(!LocaleMap.getBestMatch(locale).equals(receiptLocale))
        {
            try
            {
                Item item = getItemFacade().getById(lineItem.getItemID(), locale);
                if (item != null && !StringUtils.isEmpty(item.getDescription()))
                {
                    itemDescription = item.getDescription();
                }
            }
            catch (DataException e)
            {
                getLogger().error("failed to get item description for locale " + locale, e);
            }
        }

        return itemDescription;
    }


    /**
     * Returns the Item Access Object loaded with an open connection.
     * @return the ItemFacade with an open Connection
     */
    protected ItemFacadeIfc getItemFacade()
    {
        if (itemFacadeFactory == null)
        {
            itemFacadeFactory = (DAOFactoryIfc) BeanLocator.getPersistenceBean(ItemFacadeIfc.DAO_BEAN_KEY);
        }

        ItemFacadeIfc facade = null;
        try
        {
            facade = (ItemFacadeIfc) itemFacadeFactory.createDAO(new ContextDBConnectionManager(new DBUtils(getContext())), new BasicDBUtils());
        }
        catch (NamingException e)
        {
            getLogger().error("Error getting connection.", e);
        }
        return facade;
    }

    /**
     * Returns PAN mask used to mask unknown digits
     *
     * @return the PAN mask
     */
    public String getPanMask()
    {
        ApplicationConfigurationBean configBean = (ApplicationConfigurationBean)BeanLocator
                .getApplicationBean(ApplicationConfigurationBean.APPLICATION_CONFIGURATION_BEAN);
        String panMask = configBean.getPanMask();
        return panMask;
    }
 
    //Added by Monica 
    public void setLoyaltyDetails(TransactionDTO dto)
    {
        //int loyaltyID = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try
        {
            connection = getDBUtils().getConnection();
            StringBuilder query = new StringBuilder("SELECT LYLT_ID,LYLT_EML FROM CT_TRN_LYLT WHERE ID_STR_RT=?");
            query.append(" AND ID_WS = ? AND DC_DY_BSN = ? AND AI_TRN = ?");
            pStatement = connection.prepareStatement(query.toString());
           
            pStatement.setString(1, dto.getStoreID());
            pStatement.setString(2, dto.getWorkstationID());
            pStatement.setString(3, dto.getBusinessDateString());
            pStatement.setInt(4, dto.getSequenceNumber());          
            resultSet = pStatement.executeQuery();
            if (resultSet.next())
            {            	
            	dto.setLoyaltyID(resultSet.getLong(1));
            	dto.setLoyaltyEmail(resultSet.getString(2));
            }

        }
        catch (SQLException sqle)
        {
            getLogger().debug("Exception during reading getLoyaltyID", sqle);
            throw new EJBException("Exception during reading getLoyaltyID");
        }

    finally
        {
      getDBUtils().close(connection, pStatement, resultSet);
        }

       
    }
    
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
    public GDYNLoyalityEJournalViewBean getLoyalityDtls(String filteredTransactionNumber,String loyalityId,String businessDate) throws java.rmi.RemoteException{
    	
    	// String query = "SELECT A.ID_STR_RT ID_STR_RT ,A.ID_WS AS ID_WS, A.AI_TRN AS AI_TRN, A.DC_DY_BSN AS DC_DY_BSN, A.TY_TRN AS TY_TRN, A.TS_TRN_END  AS TS_TRN_END, B.CD_CNY_ISO AS CO_TRN_CNY  FROM(SELECT DISTINCT A.ID_STR_RT AS ID_STR_RT, A.ID_WS AS ID_WS, A.AI_TRN AS AI_TRN, A.DC_DY_BSN AS DC_DY_BSN, A.TY_TRN AS TY_TRN, A.TS_TRN_END AS TS_TRN_END FROM TR_TRN  A, ct_trn_lylt B where A.ID_STR_RT=B.ID_STR_RT  AND  A.ID_WS=B.ID_WS AND  A.AI_TRN=B.AI_TRN AND  A.DC_DY_BSN=B.DC_DY_BSN AND  A.ID_STR_RT=B.ID_STR_RT AND  B.LYLT_ID= '6666666666') A INNER JOIN (SELECT * FROM TR_RTL ) B ON A.ID_STR_RT=B.ID_STR_RT AND  A.ID_WS=B.ID_WS AND  A.AI_TRN=B.AI_TRN AND  A.DC_DY_BSN=B.DC_DY_BSN AND  A.ID_STR_RT=B.ID_STR_RT";
    	logger.info("inside getLoyalityDtls method of TransactionServiceBean" +filteredTransactionNumber +" : loyalityId "+loyalityId+" : businessDate "+businessDate);
    	logger.debug("inside getLoyalityDtls method of TransactionServiceBean " +filteredTransactionNumber +" : loyalityId "+loyalityId+" : businessDate "+businessDate);
		String storeId = filteredTransactionNumber.substring(0, 5);
		String workStationId = filteredTransactionNumber.substring(5, 8);
		String transSeqNo = filteredTransactionNumber.substring(8,
				filteredTransactionNumber.length());
		logger.info("storeId "+storeId+" workStationId "+workStationId +" transSeqNo "+transSeqNo);
		logger.debug("storeId "+storeId+" workStationId "+workStationId +" transSeqNo "+transSeqNo);
		logger.warn("storeId "+storeId+" workStationId "+workStationId +" transSeqNo "+transSeqNo);

		String query = "select ai_trn,id_str_rt,id_ws,dc_dy_bsn,id_chnl_sls,lylt_id,lylt_eml  from ct_trn_lylt where id_str_rt = ? and id_ws = ? and DC_DY_BSN = ? and ai_trn = ? ";
		GDYNLoyalityEJournalViewBean loyalityEjournalViewBean = new GDYNLoyalityEJournalViewBean();
		int count = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		try{
			
			connection = getDBUtils().getConnection();
			connection.prepareStatement(query);
			pstmt = connection.prepareStatement(query);
			//pstmt.setString(1,loyalityId );
			pstmt.setString(1, storeId);
			pstmt.setString(2, workStationId);
			pstmt.setString(3, businessDate);
			pstmt.setString(4, transSeqNo);
			resultset = pstmt.executeQuery();
			
			while(resultset.next()){
				count=  count+1;
			logger.debug("records exist in ct_trn_lylt table for transaction number "+transSeqNo);
			logger.info("records exist in ct_trn_lylt table for transaction number "+transSeqNo);
			String storeNumber = resultset.getString("ID_STR_RT");
			if(!GDYNCOLoyalitConstants.isEmptryString(storeNumber)){
			loyalityEjournalViewBean.setStoreNumber(storeNumber);
			}
			String workstationId = resultset.getString("ID_WS");
			if(!GDYNCOLoyalitConstants.isEmptryString(workstationId)){
			loyalityEjournalViewBean.setWorkstationId(workstationId);
			}
			String businessDayString = resultset.getString("DC_DY_BSN");
			if(!GDYNCOLoyalitConstants.isEmptryString(businessDayString)){
			loyalityEjournalViewBean.setBusinessDayString(businessDayString);
			}
			
			String sequenceNumber = resultset.getString("AI_TRN");
			if(!GDYNCOLoyalitConstants.isEmptryString(sequenceNumber)){
			loyalityEjournalViewBean.setSequenceNumber(sequenceNumber);
			}
			
			loyalityId = resultset.getString("LYLT_ID");
			if(!GDYNCOLoyalitConstants.isEmptryString(loyalityId)){
			loyalityEjournalViewBean.setLoyalityId(loyalityId);
			}else{
				loyalityEjournalViewBean.setLoyalityId("");
			}
			
			String loyalityEmail = resultset.getString("LYLT_EML");
			if(!GDYNCOLoyalitConstants.isEmptryString(loyalityEmail)){
			loyalityEjournalViewBean.setLoyalityEmail(loyalityEmail);
			}else{
				loyalityEjournalViewBean.setLoyalityEmail("");
			}
			if(!GDYNCOLoyalitConstants.isEmptryString(filteredTransactionNumber)){
			loyalityEjournalViewBean.setFullTransactionNumber(filteredTransactionNumber);
			}
			
			String salesChannelId = resultset.getString("ID_CHNL_SLS");
			if(!GDYNCOLoyalitConstants.isEmptryString(salesChannelId)){
				loyalityEjournalViewBean.setSalesChannel(salesChannelId);
				}else{
					loyalityEjournalViewBean.setSalesChannel("");
			}
			
					
			}
		/*	rs.close();
			pstmt.close();*/
			 }catch(SQLException e ){
			logger.debug(e.getMessage());	
			e.printStackTrace();
			} finally
	        {
				logger.info(loyalityEjournalViewBean);
	            getDBUtils().close(connection, pstmt, resultset);
	        }
		logger.info("loyalityEjournalViewBean.toString "+loyalityEjournalViewBean.toString());
		logger.debug("loyalityEjournalViewBean.toString "+loyalityEjournalViewBean.toString());
		if(count==0){
			return null;
		}else{
		return loyalityEjournalViewBean ;
		}
		}

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
    public void updateLoyalityDtls(String seqNumber,String loyalityId,String loyalityEmail,String workstationId,String storeNumber) throws java.rmi.RemoteException{
    	  
    	logger.debug( "seqNumber "+seqNumber+" loyalityId "+loyalityId+" loyalityEmail "+loyalityEmail+" workstationId "+workstationId+" storeNumber "+storeNumber);
    	logger.info( "seqNumber "+seqNumber+" loyalityId "+loyalityId+" loyalityEmail "+loyalityEmail+" workstationId "+workstationId+" storeNumber "+storeNumber);
		String query = "update ct_trn_lylt set  lylt_id = ? , lylt_eml = ? ,FL_lylt_export =0 , TS_EXpt_rcrd = null where ai_trn= ? and id_ws = ? and id_str_rt = ? ";

		int noOfRowsUpdated = 0;
		PreparedStatement pstmt = null;
		Connection connection = null;
		try{
			
			connection = getDBUtils().getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, loyalityId);
			pstmt.setString(2, loyalityEmail);
			pstmt.setInt(3, Integer.parseInt(seqNumber));
			pstmt.setString(4, workstationId);
			pstmt.setString(5, storeNumber);
			noOfRowsUpdated = pstmt.executeUpdate();
			pstmt.close();
			}catch(SQLException e ){
				
			 logger.debug(e.getMessage());	
			}finally
		      {
				 getDBUtils().closeStatement(pstmt);
		         getDBUtils().closeConnection(connection);;
		      }
		logger.info("noOfRowsUpdated "+noOfRowsUpdated);
		logger.debug("noOfRowsUpdated "+noOfRowsUpdated);
				
		}
    
}
