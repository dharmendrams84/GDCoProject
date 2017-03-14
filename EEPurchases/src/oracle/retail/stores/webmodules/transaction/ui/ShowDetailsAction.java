/* ===========================================================================
* Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/webapp/transaction-webapp/src/ui/oracle/retail/stores/webmodules/transaction/ui/ShowDetailsAction.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:16:44 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    blarsen   08/23/10 - Replaced string literal with ifc constant.
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update ade header date
 *
 * ===========================================================================
 * $Log:
 *   8    360Commerce 1.7         4/8/2008 5:15:37 PM    Edward B. Thorne
 *        Updating file headers and copyright dates.  Reviewed by Owen.
 *   7    360Commerce 1.6         2/25/2008 3:30:19 PM   Rabindra P. Kar
 *        Updgrade from struts v1.1 to v1.2.9
 *   6    360Commerce 1.5         1/10/2008 12:02:41 PM  Anda D. Cadar   FR 40:
 *         Remediation for Klocwork issues: SV.DATA_BOUND and SV.LOG_FORGING
 *   5    360Commerce 1.4         12/27/2007 1:41:51 PM  Tim Solley      Fixed
 *        transaction tracker to only allow access to transactions in a user's
 *         store hierarchy
 *   4    360Commerce 1.3         8/2/2007 4:07:02 PM    Brett J. Larsen CR
 *        20512 - in a sig-cap search, the link provided did not bring up sig
 *        cap view - it brought up the normal trans details view
 *
 *        the code that determined the forward action did not consider the
 *        type of search being performed - it simply used the 1st valid role
 *        it checked
 *
 *        sig cap is a special case - it has a special view to display the
 *        image
 *
 *        added code to check for the search type and the role for this
 *        special case
 *
 *        
 *   3    360Commerce 1.2         3/31/2005 4:29:59 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/2/2005 10:30:19 AM   Robert Pearse   
 *   1    360Commerce 1.0         2/24/2005 6:06:26 PM   Robert Pearse   
 *  $
 *
 * Revision 1.1  2004/03/17 23:28:48  jlee
 * @scr move transaction tracker entire out and deleted the last of the commerceservices  stuff from co
 *
 * Revision 1.5  2004/02/04 21:49:34  mspeich
 * Organized imports.
 *
 * Revision 1.4  2004/01/12 22:43:58  slloyd
 * Import fixes.
 *
 * Revision 1.3  2004/01/07 22:57:22  jlee
 * moved some common ui classes and hopefully fixed dependency problem for war
 *
 * Revision 1.2  2003/12/02 18:55:17  build
 * conversion of ctrl-m's
 *
 * Revision 1.1.1.1  2003/12/01 04:37:08  build
 * Import from PVCS ${date}
 *
 * 
 *    Rev 1.4   Oct 27 2003 13:29:38   jlee
 * redid ui for transaction tracker to make customer info access independent of transaction details access
 * 
 *    Rev 1.3   Jul 21 2003 23:37:24   slloyd
 * Websphere and security role updates.
 * 
 *    Rev 1.2   Jun 25 2003 08:08:48   bar
 * logger deprecation fix and removing unused imports
 * Resolution for 1576: General development
 *
 *    Rev 1.1   Jun 21 2003 15:04:08   pjf
 * Make single search result to txn detail work.
 * Resolution for 1618: Transaction search resulting in one match not functioning
 *
 *    Rev 1.0   Jun 17 2003 16:09:02   jlee
 * Initial revision.
 *
 *    Rev 1.4   May 05 2003 15:31:24   slloyd
 * refactor transaction bean into RetailTransactionBean
 * Resolution for 1582: CentralOfficeUI
 *
 *    Rev 1.3   Apr 16 2003 17:39:34   jlee
 * refactored exception handling to struts 1.1 way
 * Resolution for 1599: No matches found
 *
 *    Rev 1.2   Apr 16 2003 11:34:30   jlee
 * only one result go to details
 * Resolution for 1598: Search with only one match results in the one match listed
 *
 *    Rev 1.1   Apr 16 2003 10:15:14   jlee
 * refactor some ui stuff to fix a defect (of course I broke it more)
 * Resolution for 1598: Search with only one match results in the one match listed
 *
 *    Rev 1.0   Apr 16 2003 10:11:14   jlee
 * Initial revision.
 * Resolution for 1598: Search with only one match results in the one match listed
 * ===========================================================================
 */
package oracle.retail.stores.webmodules.transaction.ui;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

import oracle.retail.stores.commerceservices.storedirectory.HierarchyNodeKey;
import oracle.retail.stores.commerceservices.storedirectory.StoreSelectionCriteria;
import oracle.retail.stores.commerceservices.storedirectory.ejb.StoreDirectoryHome;
import oracle.retail.stores.commerceservices.storedirectory.ejb.StoreDirectoryRemote;
import oracle.retail.stores.foundation.common.ServiceLocator;

/**
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class ShowDetailsAction extends Action
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
     *
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {
        String              forwardString      = null;
        TransactionKeyForm  transactionKeyForm = (TransactionKeyForm) form;
        String              transactionNumber  = transactionKeyForm.getTransactionNumber();
        String              businessDate       = transactionKeyForm.getBusinessDate();
        String              currentSearch       = (String)request.getSession().getAttribute("currentSearch");

        //get the transaction number from the request if it is not set on the key form
        //needed to bypass form validation errors when search result size is 1 (forward="oneResult")
        if (StringUtils.isEmpty(transactionNumber))
        {
            transactionNumber = (String)request.getAttribute("transactionNumber");
            //PCI Compliance FR40 - Remediation for SV.LOG_FORGING, filter the data before displaying it into the log file
            logger.debug("form number empty, using transactionNumber from request: " + TagUtils.getInstance().filter(transactionNumber));
        }
        
        // Validate that the user has permission to view transactions for this store
        String storeNumber = transactionNumber.substring(0, 5);
        String loginID = request.getUserPrincipal().getName();
        StoreDirectoryRemote userStoreDirectoryRemote = getStoreDirectoryService();
        HierarchyNodeKey userStoreHierarchy = userStoreDirectoryRemote.getHierarchyNodeIDForEmployeeByLoginID(loginID);
        StoreSelectionCriteria userStoreCriteria = new StoreSelectionCriteria(userStoreHierarchy);
        Collection userStores = userStoreDirectoryRemote.getStores(userStoreCriteria);
        
        if(!userStores.contains(storeNumber)) {
        	ActionMessages errors = new ActionMessages();
        	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("transaction.centej.trandet.error.notallowed"));
			saveErrors(request, errors);
			return mapping.findForward("notAllowed");
        }
        
        logger.debug("form class: " + form.getClass().getName());

        if (TransactionConstants.SEARCH_BY_SIG_CAP.equals(currentSearch) &&  request.isUserInRole("view_sigcap"))
        {
            forwardString = "signatureCaptureView";
        }
        else if (request.isUserInRole("view_trandetail"))
        {
            forwardString = "transactionDetailView";
        }
        else if (request.isUserInRole("view_journalview"))
        {
            forwardString = "eJournalView";
        }
        else if (request.isUserInRole("view_sigcap"))
        {
            forwardString = "signatureCaptureView";
        }
        else if (request.isUserInRole("view_customerinfo"))
        {
            forwardString = "customerView";
        }

        if (StringUtils.isNotEmpty(transactionNumber))
        {
        	//PCI Compliance FR40 - Remediation for SV.DATA_BOUND
        	request.setAttribute("transactionNumber", TagUtils.getInstance().filter(transactionNumber));
        }
        else
        {
            logger.debug("**** ERROR - transactionNumber is empty ***");
        }

        if (StringUtils.isNotEmpty(businessDate))
        {
        	//PCI Compliance FR40 - Remediation for SV.DATA_BOUND
            request.setAttribute("businessDate", TagUtils.getInstance().filter(businessDate));
        }
        else
        {
            logger.debug("**** ERROR - businessDate is empty ***");
        }

        logger.debug("forwardString: " + forwardString);

        return (mapping.findForward(forwardString));
    }
    
    private StoreDirectoryRemote getStoreDirectoryService() {
    	return (StoreDirectoryRemote) ServiceLocator.getInstance().
    		getRemoteService("java:comp/env/ejb/StoreDirectorySB", StoreDirectoryHome.class);
    }

    private static final Logger logger = Logger.getLogger(ShowDetailsAction.class);
}


