/*new action class created by Dharmendra on 28/11/2016 to retrive loyality details from CT_TRN_LYLT table*/ 
package gdyn.retail.stores.webmodules.transaction.ui;



import gdyn.retail.stores.webmodules.ejournal.GDYNLoyalityEJournalViewBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.retail.stores.commerceservices.transaction.SearchCriteria;
import oracle.retail.stores.commerceservices.transaction.TransactionCriteria;
import oracle.retail.stores.foundation.common.ServiceLocator;

import oracle.retail.stores.webmodules.transaction.app.ejb.EJournalManagerHome;
import oracle.retail.stores.webmodules.transaction.app.ejb.EJournalManagerRemote;
import oracle.retail.stores.webmodules.transaction.ui.EJournalKeyForm;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

public final class GDYNShowLoyalityEJournalAction extends Action
{
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @throws
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception
    {
    	
    	logger.debug("inside execute method of ShowLoyalityEJournalAction");
        String transactionNumber = ((EJournalKeyForm) form).getTransactionNumber();
        String businessDate = ((EJournalKeyForm) form).getBusinessDate();
       // String transactionBeginDate = ((EJournalKeyForm) form).getTransactionBeginDate();
//      PCI Compliance FR40 - Remediation for SV.LOG_FORGING, filter the data before displaying it into the log file
        String filteredTransactionNumber = TagUtils.getInstance().filter(transactionNumber);
       /* String filteredBusinessDate = TagUtils.getInstance().filter(businessDate);
        String filteredBeginDate = TagUtils.getInstance().filter(transactionBeginDate);*/
        
      
        
      
       
        String transactionNumberParam = request.getParameter("transactionNumber");
        
        logger.debug("transactionNumber "+transactionNumber+" : businessDate "+businessDate+" : filteredTransactionNumber "+filteredTransactionNumber+" transactionNumberParam "+transactionNumberParam);
        String loyalityId = ""; 
        SearchCriteria searchCriteria = (SearchCriteria)request.getSession().getAttribute("searchCriteria");
        if(searchCriteria!=null){
        TransactionCriteria transactionCriteria =	searchCriteria.getTransactionCriteria();
        if(transactionCriteria!=null){
        //	EJournalKeyForm eJournalKeyForm =  (EJournalKeyForm)form;
        	
        	
        	loyalityId = transactionCriteria.getLoyalityId();
         }
        }
        
        
       logger.debug("Loyality id obained from  TransactionCriteria "+loyalityId+" : filteredTransactionNumber "+filteredTransactionNumber);
        
      //  logger.debug("tranNumber: " + filteredTransactionNumber + ", date: " + filteredBusinessDate);
        EJournalManagerRemote eJournalManager = (EJournalManagerRemote) ServiceLocator.getInstance().getRemoteService(
                "java:comp/env/ejb/EJournalManager", EJournalManagerHome.class);
        GDYNLoyalityEJournalViewBean loyalityEJournalViewBean = new GDYNLoyalityEJournalViewBean();
        try
        {
           
            
            // non-transactional ejournals all have 0000 for their sequence #
            // so, they require the ejournal begin date to be uniquely identified
            // thus, non-transaction ejournal requests include a transaction begin date
           /* if (filteredBeginDate != null && !filteredBeginDate.equals(""))
            {
                result = eJournalManager.retrieveEJournalEntry(filteredTransactionNumber, filteredBusinessDate, filteredBeginDate);
            }
            else
            {
                result = eJournalManager.retrieveEJournalEntry(filteredTransactionNumber, filteredBusinessDate);
                
               
                
            }*/
         
	        
	        loyalityEJournalViewBean =     eJournalManager.retrieveLoyalityEJournal(filteredTransactionNumber,loyalityId,businessDate);
            
	        logger.debug("loyalityEJournalViewBean :- "+loyalityEJournalViewBean);
	        
            request.setAttribute("eJournalViewBean", loyalityEJournalViewBean);
        }
        catch (Exception e)
        {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE,
                       new ActionMessage("error.ejournal.entry.not.found"));

            saveErrors(request, errors);

            request.setAttribute("EJNotFound", new Boolean(true));

            return mapping.getInputForward();
        }
        if(loyalityEJournalViewBean==null){
        ActionMessages errors = new ActionMessages();
        errors.add(ActionMessages.GLOBAL_MESSAGE,
                   new ActionMessage("error.loyality.entry.not.found"));
        

        saveErrors(request, errors);

        request.setAttribute("EJNotFound", new Boolean(true));

        return mapping.getInputForward();
        }else{
        return (mapping.findForward("success"));
        }
    }


    private final Logger logger = Logger.getLogger(getClass());
}
