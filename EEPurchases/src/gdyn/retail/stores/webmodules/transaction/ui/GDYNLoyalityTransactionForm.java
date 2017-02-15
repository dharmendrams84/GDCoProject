package gdyn.retail.stores.webmodules.transaction.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

public class GDYNLoyalityTransactionForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(GDYNLoyalityTransactionForm.class); //constant

    private String storeNumber;
    
    private String workstationId;
    
    private String businessDayString;
    
    private String sequenceNumber;
    
    private String typeTransaction;
    
    private String loyalityId ;
    
    private String loyalityEmail;
        
    private String loyalityIdOrg;
    
    public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getWorkstationId() {
		return workstationId;
	}

	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}

	public String getBusinessDayString() {
		return businessDayString;
	}

	public void setBusinessDayString(String businessDayString) {
		this.businessDayString = businessDayString;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public String getLoyalityId() {
		return loyalityId;
	}

	public void setLoyalityId(String loyalityId) {
		this.loyalityId = loyalityId;
	}

	public String getLoyalityEmail() {
		return loyalityEmail;
	}

	public void setLoyalityEmail(String loyalityEmail) {
		this.loyalityEmail = loyalityEmail;
	}

	public String getLoyalityIdOrg() {
		return loyalityIdOrg;
	}

	public void setLoyalityIdOrg(String loyalityIdOrg) {
		this.loyalityIdOrg = loyalityIdOrg;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	private String salesChannel;
    
}
