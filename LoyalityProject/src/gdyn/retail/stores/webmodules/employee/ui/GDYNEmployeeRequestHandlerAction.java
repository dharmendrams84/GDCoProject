/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/webapp/customer-webapp/src/ui/oracle/retail/stores/webmodules/customer/ui/CustomerRequestHandlerAction.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:16:55 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update ade header date
 *    tzgarba   03/26/09 - Added support for session timeout tracking to
 *                         centralized customer screens.
 *    mahising  02/28/09 - Change salutation dropdown to free form text field
 *    mahising  02/25/09 - Fixed issue of salutation and gender field for
 *                         customer
 *    mahising  12/23/08 - fix base issue
 *    acadar    12/01/08 - address localization
 *    mahising  11/21/08 - Updated for comments
 *    mahising  11/20/08 - Update for customer
 *    mahising  11/13/08 - Added for Customer module
 *
 * ===========================================================================
 */

package gdyn.retail.stores.webmodules.employee.ui;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Customer request Action Handler.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @since 13.1
 */
public class GDYNEmployeeRequestHandlerAction extends Action
{

    /**
     * This method get data to populate list box field and also redirect request
     * to proper resource.
     *
     * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
     * @since 13.1
     */
	
	 private static final Logger logger = Logger.getLogger(GDYNEmployeeRequestHandlerAction.class);
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        String forwardString = null;

        //Set a token for time out checking
        request.getSession().setAttribute("CustomerTimeoutToken", "true");

        if ((request.isUserInRole("gdemployee")))
        {
            forwardString = "searchgdemployee";
        }
        else if (request.isUserInRole("gdemployee_add"))
        {
            forwardString = "addgdemployee";
        }
        
        
        logger.debug("In GDYNEmployeeRequestHandlerAction file" + forwardString);
        logger.error("In GDYNEmployeeRequestHandlerAction file" + forwardString);

        return mapping.findForward(forwardString);
    }


}
