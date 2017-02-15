/* ===========================================================================
* Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved. 
 * ===========================================================================
 * $Header: rgbustores/commerceservices/transaction/src/oracle/retail/stores/commerceservices/transaction/TransactionDTO.java /rgbustores_13.4x_generic_branch/1 2011/05/04 14:50:47 mszekely Exp $
 * ===========================================================================
 * NOTES
 * <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *    cgreene   11/12/10 - added method isCanceled
 *    cgreene   05/26/10 - convert to oracle packaging
 *    abondala  05/26/10 - Siebel - CO integration
 *    abondala  01/03/10 - update header date
 *
 * ===========================================================================
 */

package oracle.retail.stores.commerceservices.transaction;
import java.util.Date;

import oracle.retail.stores.commerceservices.common.ExtensibleDTO;
import oracle.retail.stores.domain.transaction.TransactionConstantsIfc;
import oracle.retail.stores.foundation.util.DateTimeUtils;
import oracle.retail.stores.foundation.util.DateTimeUtilsIfc;

/**
 * Base class for DTOs that represent ARTS transactional information.
 * Attributes in this class are common among all 360Commerce transaction types.
 */
public class TransactionDTO extends ExtensibleDTO
{
    private static final long serialVersionUID = -5612005142770575038L;

    private TransactionKey key = new TransactionKey();
    private String type = "";
    private String operatorID = "";
    private String salesAssociateID = "";
    private String tillID = "";
    private String operatorName = "";
    private Date endDateTime = new Date();
    private Date beginDateTime = new Date();
    private boolean trainingMode = false;
    private boolean keyedOffline = false;
    private String pathToStore = "";
    private int status = 0;
    private DateTimeUtilsIfc dtUtil = DateTimeUtils.getInstance();
    private int customerInfoType = 0;
    private String customerInfo = null;
    private String transactionNumber;
    private boolean reentryMode = false;
    private boolean voided = false;
    private boolean customerLinkedToTransaction = false;
    private String registerId = "";
    private long loyaltyID ;
    private String loyaltyEmail;    

    public String getRegisterId()
    {
        return registerId;
    }

    public void setRegisterId(String registerId)
    {
        this.registerId = registerId;
    }

    public TransactionDTO()
    {
        this(new TransactionKey());
    }

    public TransactionDTO(TransactionKey key)
    {
        this(key, TransactionType.UNKNOWN.toString(), "", new Date(), "");
    }

    public TransactionDTO(TransactionKey key, String type, String operatorID, Date endDateTime, String pathToStore)
    {
        this.key = key;
        this.type = type;
        this.operatorID = operatorID;
        this.endDateTime = endDateTime;
        this.pathToStore = pathToStore;
    }

    public TransactionKey getKey()
    {
        return key;
    }

    public String getStoreID()
    {
        return key.getStoreID();
    }

    public String getWorkstationID()
    {
        return key.getWorkstationID();
    }

    public int getSequenceNumber()
    {
        return key.getSequenceNumber();
    }

    public Date getBusinessDate()
    {
        return key.getDate();
    }

    public String getBusinessDateString()
    {
        return key.getFormattedDateString();
    }

    public String getType()
    {
        return type;
    }

    public boolean isRetailTransaction()
    {
        return false;
    }

    public Date getTransactionEndDateTime()
    {
        return endDateTime;
    }

    /**
     * @return
     */
    public Date getTransactionBeginDateTime()
    {
        return beginDateTime;
    }

    /**
     * @return
     */
    public String getOperatorID()
    {
        return operatorID;
    }

    /**
     * @return
     */
    public String getTillID()
    {
        return tillID;
    }

    public String getSalesAssociateID()
    {
        return salesAssociateID;
    }

    public void setSalesAssociateID(String string)
    {
        salesAssociateID = string;
    }

    /**
     * @return
     */
    public String getOperatorName()
    {
        return operatorName;
    }

    /**
     * @return
     */
    public boolean isTrainingMode()
    {
        return trainingMode;
    }

    /**
     * @return
     */
    public boolean isKeyedOffline()
    {
        return keyedOffline;
    }

    /**
     * @param date
     */
    public void setTransactionBeginDateTime(Date date)
    {
        beginDateTime = date;
    }

    /**
     * @param date
     */
    public void setTransactionEndDateTime(Date date)
    {
        endDateTime = date;
    }

    /**
     * @param string
     */
    public void setOperatorID(String string)
    {
        operatorID = string;
    }

    /**
     * @param string
     */
    public void setTillID(String string)
    {
        tillID = string;
    }

    /**
     * @param string
     */
    public void setOperatorName(String string)
    {
        operatorName = string;
    }

    /**
     * @param string
     */
    public void setType(String string)
    {
        type = string;
    }

    /**
     * @param b
     */
    public void setTrainingMode(boolean b)
    {
        trainingMode = b;
    }

    /**
     * @param b
     */
    public void setKeyedOffline(boolean b)
    {
        keyedOffline = b;
    }

    //change these to Date and move into client, formatting should be at ui layer
    public String getFormattedTimeString()
    {
        return dtUtil.formatDate(endDateTime, "hh:mm z");
    }

    public String getFormattedDateString()
    {
        return dtUtil.formatDate(endDateTime, "MM/dd/yyyy");
    }

    //this is Central Office specific, should be in a StoreDTO
    public String getPathToStore()
    {
        return pathToStore;
    }

    public void setPathToStore(String pathToStore)
    {
        this.pathToStore = pathToStore;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getCustomerInfo()
    {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo)
    {
        this.customerInfo = customerInfo;
    }

    public boolean isCustomerLinkedToTransaction()
    {
        return customerLinkedToTransaction;
    }

    public void setCustomerLinkedToTransaction(boolean customerLinkedToTransaction)
    {
        this.customerLinkedToTransaction = customerLinkedToTransaction;
    }

    public int getCustomerInfoType()
    {
        return customerInfoType;
    }

    public void setCustomerInfoType(int customerInfoType)
    {
        this.customerInfoType = customerInfoType;
    }

    public String getTransactionNumber()
    {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber)
    {
        this.transactionNumber = transactionNumber;
    }

    /**
     * Compare the {@link #transactionStatus} with
     * {@link TransactionConstantsIfc#STATUS_CANCELED}.
     *
     * @see oracle.retail.stores.domain.transaction.TransactionIfc#isCanceled()
     * @see oracle.retail.stores.domain.transaction.TransactionConstantsIfc#STATUS_CANCELED
     */
    public boolean isCanceled()
    {
        return getStatus() == TransactionConstantsIfc.STATUS_CANCELED;
    }

    /**
     * @return Returns the voided property.
     */
    public boolean isVoided()
    {
        return voided;
    }

    /**
     * @param voided.
     */
    public void setVoided(boolean v)
    {
        this.voided = v;
    }

    /**
     * @return Returns the reentryMode.
     */
    public boolean isReentryMode()
    {
        return reentryMode;
    }

    /**
     * @param reentryMode The reentryMode to set.
     */
    public void setReentryMode(boolean reentryMode)
    {
        this.reentryMode = reentryMode;
    }

    /**
     * 
     * @return
     */
    public String getStatusDescriptor()
    {
        if (getStatus() < 0 || getStatus() > TransactionConstantsIfc.STATUS_DESCRIPTORS.length - 1)
            return TransactionConstantsIfc.STATUS_DESCRIPTORS[0];

        return TransactionConstantsIfc.STATUS_DESCRIPTORS[getStatus()];
    }

	/**
	 * @return the loyaltyID
	 */
	public long getLoyaltyID() {
		return loyaltyID;
	}

	/**
	 * @param loyaltyID the loyaltyID to set
	 */
	public void setLoyaltyID(long loyaltyID) {
		this.loyaltyID = loyaltyID;
	}

	/**
	 * @return the loyaltyEmail
	 */
	public String getLoyaltyEmail() {
		return loyaltyEmail;
	}

	/**
	 * @param loyaltyEmail the loyaltyEmail to set
	 */
	public void setLoyaltyEmail(String loyaltyEmail) {
		this.loyaltyEmail = loyaltyEmail;
	}
    
    
}