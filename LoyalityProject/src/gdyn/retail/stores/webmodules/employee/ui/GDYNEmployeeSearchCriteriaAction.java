/* ===========================================================================
* Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/webapp/customer-webapp/src/ui/oracle/retail/stores/webmodules/customer/ui/CustomerSearchCriteriaAction.java /rgbustores_13.4x_generic_branch/1 2011/05/05 16:16:55 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update ade header date
 *    acadar    12/04/08 - added formatting on search
 *    mahising  11/21/08 - Updated for comments
 *    mahising  11/13/08 - Added for Customer module
 *
 * ===========================================================================
 */

package gdyn.retail.stores.webmodules.employee.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Customer search criteria action.
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 * @since 13.1
 */
public class GDYNEmployeeSearchCriteriaAction extends Action
{
    final public String SUCCESS = "success";

    private static final Logger logger = Logger.getLogger(GDYNEmployeeSearchCriteriaAction.class);


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
     * @exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
    	logger.debug("Enters GDYNEmployeeSearchCriteriaAction");
        return mapping.findForward(SUCCESS);
    }

    
}
