/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/commerceservices/transaction/src/oracle/retail/stores/commerceservices/transaction/TransactionCriteria.java /rgbustores_13.4x_generic_branch/1 2011/05/04 14:50:48 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 * $Log:
 *   6    360Commerce 1.5         11/9/2006 7:16:43 PM   Jack G. Swan
 *        C:\dev\projects\360Commerce\build
 *   5    360Commerce 1.4         3/23/2006 12:33:37 PM  Brett J. Larsen CR
 *        16522 - merge returns management build 1.0.0.6 into trunk
 *   4    360Commerce 1.3         12/13/2005 4:43:38 PM  Barry A. Pape
 *        Base-lining of 7.1_LA
 *   3    360Commerce 1.2         3/31/2005 6:36:39 PM   Robert Pearse   
 *   2    360Commerce 1.1         3/10/2005 10:26:20 AM  Robert Pearse   
 *   1    360Commerce 1.0         2/14/2005 2:06:56 PM   Robert Pearse   
 *  $
 *  Revision 1.7.6.1  2004/12/21 23:06:13  djennings
 *  @scr 1. optionally filter out voided txns from getTransaction
 *           2. when searching for an item, optionally specify if we only want to search salelineitems, or returnlineitems, etc
 *           3. making transactiondto extend from extensibledto
 *           4. making some methods protected s.t. the can be customized.
 *           5. adding some methods explicitly for overriding
 *           6. stop passing transactionservicebean explicity to poslogutility and transactionheaderutility. make them do a jndi lookup ( so that the deployment descriptor changes will work properly ).
 *           7. parameterizing the instantiation of poslogutility and transactionheaderutility so they can be overridden
 *           8. allow getTransaction() result set to be sorted by transaction id or transaction timestamp

 *   4    360Commerce1.3         12/13/2005 4:43:38 PM  Barry A. Pape  
 *        Base-lining of 7.1_LA
 *   3    360Commerce1.2         3/31/2005 5:36:39 PM   Robert Pearse   
 *   2    360Commerce1.1         3/10/2005 10:26:20 AM  Robert Pearse   
 *   1    360Commerce1.0         2/14/2005 2:06:56 PM   Robert Pearse   
 *  $
 *  Revision 1.7  2004/11/17 22:36:55  sgu
 *  @scr 3521. Rewrote the way how we retrieve suspended
 *  transaction during store close.
 *
 *  Revision 1.6  2004/10/07 22:38:02  ksharma
 *  Updated with "Reentry Mode"
 *
 *  Revision 1.5  2004/10/06 22:14:19  ksharma
 *  Updated with "Training Mode" search criteria
 *
 *  Revision 1.4  2004/06/11 22:59:01  jlee
 *  Split up the transaction id in each of the message schema
 *
 *  Revision 1.3  2004/05/19 08:59:22  slloyd
 *  Modify Transaction Entities and access strategy.
 *
 *  Revision 1.2  2004/05/05 16:17:40  cmitchell
 *  Merge from Branch.  This should be fun.
 *
 * ===========================================================================
 */
package oracle.retail.stores.commerceservices.transaction;

import oracle.retail.stores.commerceservices.transaction.EJournalCriteria;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class TransactionCriteria extends EJournalCriteria
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6906512840385601123L;
	private BigDecimal transactionTotal;
    private int[] transactionTypes;
    private String trainingMode;
    private String reentryMode;
    private Integer transactionStatus;
    private boolean retailTransactionsOnly;
    
    private boolean includeVoidedTransactionCriteria;
    private boolean excludeVoidedTransactions;
    private String busnessDateStart;
    private String busnessDateEnd;
    private boolean searchBybusnessDateStart = false;
    private boolean searchBybusnessDateEnd = false;
    /*new parameter and corresponding getter and setter added by dharmendra on 23/11/2016 to hold loyality id value entered by the user*/
    private String loyalityId;
    /*new parameter and corresponding getter and setter added by dharmendra on 23/11/2016 to determine whether to serach by loyality id*/
    private boolean searchByLoyalityId = false;
    
    public TransactionCriteria()
    {
        super();
        setRetailTransactionsOnly(false);
        includeVoidedTransactionCriteria=false;
    }

    public TransactionCriteria(int[] transactionTypes,
                               Date fromEndDateTime,
                               Date toEndDateTime,
                               String transactionNumber)
    {
        super(fromEndDateTime,
              toEndDateTime,
              transactionNumber);
        setTransactionTypes(transactionTypes);
        setRetailTransactionsOnly(false);
        includeVoidedTransactionCriteria=false;
    }

    public TransactionCriteria(int[] transactionTypes,
                               Date fromEndDateTime,
                               Date toEndDateTime,
                               int fromSequenceNumber,
                               int toSequenceNumber,
                               String fromWorkstationID,
                               String toWorkstationID)
    {
        super(fromEndDateTime, toEndDateTime, fromSequenceNumber, toSequenceNumber, fromWorkstationID, toWorkstationID);
        setTransactionTypes(transactionTypes);
        setRetailTransactionsOnly(false);
        includeVoidedTransactionCriteria=false;
    }

    public void clearSearchByVoidedTransaction()
    {
        includeVoidedTransactionCriteria=false;
        excludeVoidedTransactions=false;
    }
    
    public boolean isSearchByVoidedTransaction()
    {
        return includeVoidedTransactionCriteria;
    }
    
    public void setExcludeVoidedTransactions(boolean bExclude)
    {
        includeVoidedTransactionCriteria=true;
        excludeVoidedTransactions=bExclude;
    }
    
    public boolean getExcludeVoidedTransactions()
    {
        return excludeVoidedTransactions;
    }
    
    public BigDecimal getTransactionTotal()
    {
        return this.transactionTotal;
    }

    public void setTransactionTotal(BigDecimal transactionTotal)
    {
        this.transactionTotal = transactionTotal;
    }

    public int[] getTransactionTypes()
    {
        return transactionTypes;
    }

    public void setTransactionTypes(int[] types)
    {
        transactionTypes = types;
    }
    
    /**
     * @return Returns the trainingMode.
     */
    public String getTrainingMode()
    {
        return trainingMode;
    }
    /**
     * @param trainingMode The trainingMode to set.
     */
    public void setTrainingMode(String trainingMode)
    {
        this.trainingMode = trainingMode;
    }
    
    /**
     * @return Returns the reentryMode.
     */
    public String getReentryMode()
    {
        return reentryMode;
    }
    /**
     * @param reentryMode The reentryMode to set.
     */
    public void setReentryMode(String reentryMode)
    {
        this.reentryMode = reentryMode;
    }
    public boolean use()
    {
        return (super.use()
                || isSearchByTransactionTotal()
                || isSearchByTransactionType()
				|| isSearchByTransactionStatus()
				|| isSearchByRetailTransactionsOnly());
    }

    public boolean isSearchByTransactionType()
    {
        return transactionTypes != null && transactionTypes.length > 0 && transactionTypes[0] != 0;
    }

    public boolean isSearchByTransactionTotal()
    {
        return (transactionTotal != null);
    }   
  
	/**
	 * @return Returns the status.
	 */
	public Integer getTransactionStatus() {
		return transactionStatus;
	}
	/**
	 * @param status The status to set.
	 */
	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public boolean isSearchByTransactionStatus()
	{
		return (transactionStatus != null);
	}
	
	/**
	 * @return Returns the retailTransactionsOnly.
	 */
	public boolean isSearchByRetailTransactionsOnly() {
		return retailTransactionsOnly;
	}
	/**
	 * @param retailTransactionsOnly The retailTransactionsOnly to set.
	 */
	public void setRetailTransactionsOnly(boolean retailTransactionsOnly) {
		this.retailTransactionsOnly = retailTransactionsOnly;
	}

	public String getBusnessDateEnd() {
		return busnessDateEnd;
	}

	public void setBusnessDateEnd(String busnessDateEnd) {
		this.busnessDateEnd = busnessDateEnd;
	}

	public String getBusnessDateStart() {
		return busnessDateStart;
	}

	public void setBusnessDateStart(String busnessDateStart) {
		this.busnessDateStart = busnessDateStart;
	}

	public boolean isSearchBybusnessDateEnd() {
		return searchBybusnessDateEnd;
	}

	public void setSearchBybusnessDateEnd(boolean searchBybusnessDateEnd) {
		this.searchBybusnessDateEnd = searchBybusnessDateEnd;
	}

	public boolean isSearchBybusnessDateStart() {
		return searchBybusnessDateStart;
	}

	public void setSearchBybusnessDateStart(boolean searchBybusnessDateStart) {
		this.searchBybusnessDateStart = searchBybusnessDateStart;
	}

	/**
	 * @return the loyalityId
	 */
	public String getLoyalityId() {
		return loyalityId;
	}

	/**
	 * @param loyalityId the loyalityId to set
	 */
	public void setLoyalityId(String loyalityId) {
		this.loyalityId = loyalityId;
	}

	/**
	 * @return the searchByLoyalityId
	 */
	public boolean isSearchByLoyalityId() {
		return searchByLoyalityId;
	}

	/**
	 * @param searchByLoyalityId the searchByLoyalityId to set
	 */
	public void setSearchByLoyalityId(boolean searchByLoyalityId) {
		this.searchByLoyalityId = searchByLoyalityId;
	}
	
	
}
