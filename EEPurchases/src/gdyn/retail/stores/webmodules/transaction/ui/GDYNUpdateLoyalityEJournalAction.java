/*new action class created by Dharmendra on 28/11/2016 to update loyality details in CT_TRN_LYLT table*/ 

package gdyn.retail.stores.webmodules.transaction.ui;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.retail.stores.foundation.common.ServiceLocator;
import oracle.retail.stores.webmodules.transaction.app.ejb.EJournalManagerHome;
import oracle.retail.stores.webmodules.transaction.app.ejb.EJournalManagerRemote;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class GDYNUpdateLoyalityEJournalAction extends Action
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
    	
        String storeNumber = ((GDYNLoyalityTransactionForm) form).getStoreNumber();
        String businessDayString = ((GDYNLoyalityTransactionForm) form).getBusinessDayString();
        String loyalityId = ((GDYNLoyalityTransactionForm) form).getLoyalityId();
        String loyalityEmail = ((GDYNLoyalityTransactionForm) form).getLoyalityEmail();
        String sequenceNumber =  ((GDYNLoyalityTransactionForm) form).getSequenceNumber();
        String  workstationId =    ((GDYNLoyalityTransactionForm) form).getWorkstationId();
        String fullTransactionNumber = request.getParameter("fullTransactionNumber");
        request.setAttribute("transactionId",fullTransactionNumber);
        request.setAttribute("result","success");
        logger.info("storeNumber "+storeNumber+ " businessDayString "+businessDayString+" loyalityId "+loyalityId+" loyalityEmail "+loyalityEmail);
        logger.info("sequenceNumber "+sequenceNumber+ " workstationId "+workstationId+"fullTransactionNumber");
        
         EJournalManagerRemote eJournalManager = (EJournalManagerRemote) ServiceLocator.getInstance().getRemoteService(
                 "java:comp/env/ejb/EJournalManager", EJournalManagerHome.class);
       
       boolean transactionUpdateStatus = eJournalManager.updateLoyalityDetails(sequenceNumber, loyalityId, loyalityEmail,workstationId,storeNumber);
       request.setAttribute("transactionUpdateStatus",transactionUpdateStatus);
        return (mapping.findForward("success"));
      //  return mapping.getInputForward();
    }

   
    private final Logger logger = Logger.getLogger(getClass());
}
