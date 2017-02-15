/*new class created by Dharmendra on 28/11/2016 to hold different values fetched for a transaction in CT_TRN_LYLT table*/
package oracle.retail.stores.webmodules.ejournal;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * @version $Revision: /rgbustores_13.4x_generic_branch/1 $
 */
public class LoyalityEJournalViewBean implements Serializable
{
	private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(LoyalityEJournalViewBean.class); //constant

    private String storeNumber;
    
    private String workstationId;
    
    private String businessDayString;
    
    private String sequenceNumber;
    
    private String typeTransaction;
    
    private String loyalityId ;
    
    private String loyalityEmail;
        
    private String loyalityIdOrg;
    
    public String getFullTransactionNumber() {
		return fullTransactionNumber;
	}

	public void setFullTransactionNumber(String fullTransactionNumber) {
		this.fullTransactionNumber = fullTransactionNumber;
	}

	private String fullTransactionNumber;
    
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
    

 /*   public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof LoyalityEJournalViewBean)) return false;

        final LoyalityEJournalViewBean eJournalViewBean = (LoyalityEJournalViewBean) o;

        if (businessDate != null ? !businessDate.equals(eJournalViewBean.businessDate) : eJournalViewBean.businessDate != null) return false;
        if (tape != null ? !tape.equals(eJournalViewBean.tape) : eJournalViewBean.tape != null) return false;
        if (transactionNumber != null ? !transactionNumber.equals(eJournalViewBean.transactionNumber) : eJournalViewBean.transactionNumber != null) return false;

        return true;
    }*/

    /*public int hashCode()
    {
        int result;
        result = (transactionNumber != null ? transactionNumber.hashCode() : 0);
        result = 29 * result + (businessDate != null ? businessDate.hashCode() : 0);
        result = 29 * result + (tape != null ? tape.hashCode() : 0);
        return result;
    }*/

 /*   private String storeNumber;    private String workstationId;    private String businessDayString; private String sequenceNumber;    private String typeTransaction;
    
    private String loyalityId ;  private String loyalityEmail;   private String loyalityIdOrg;
*/	public String toString(){
		return "storeNumber "+storeNumber+" workstationId "+workstationId+ " businessDayString "+businessDayString+" sequenceNumber "+sequenceNumber+ " typeTransaction "+typeTransaction
			+" loyalityId "+loyalityId+ " loyalityEmail "+loyalityEmail	+" loyalityIdOrg "+loyalityIdOrg;
	}

}
