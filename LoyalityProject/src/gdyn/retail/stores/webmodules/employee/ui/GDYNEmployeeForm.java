package gdyn.retail.stores.webmodules.employee.ui;

import org.apache.struts.action.ActionForm;

public class GDYNEmployeeForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName ;
	
	private String lastName;
	
	private String emplDiscGrpCode;
	
	private String emplStatus;
	
	private int emplId;
	
	private String emplIdSrc;
	
	private String emplNumber;	
	
	private String positionCode ;
	
	private String email;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emplDiscGrpCode
	 */
	public String getEmplDiscGrpCode() {
		return emplDiscGrpCode;
	}

	/**
	 * @param emplDiscGrpCode the emplDiscGrpCode to set
	 */
	public void setEmplDiscGrpCode(String emplDiscGrpCode) {
		this.emplDiscGrpCode = emplDiscGrpCode;
	}

	/**
	 * @return the emplStatus
	 */
	public String getEmplStatus() {
		return emplStatus;
	}

	/**
	 * @param emplStatus the emplStatus to set
	 */
	public void setEmplStatus(String emplStatus) {
		this.emplStatus = emplStatus;
	}

	/**
	 * @return the emplId
	 */
	public int getEmplId() {
		return emplId;
	}

	/**
	 * @param emplId the emplId to set
	 */
	public void setEmplId(int emplId) {
		this.emplId = emplId;
	}

	/**
	 * @return the emplIdSrc
	 */
	public String getEmplIdSrc() {
		return emplIdSrc;
	}

	/**
	 * @param emplIdSrc the emplIdSrc to set
	 */
	public void setEmplIdSrc(String emplIdSrc) {
		this.emplIdSrc = emplIdSrc;
	}

	/**
	 * @return the emplNumber
	 */
	public String getEmplNumber() {
		return emplNumber;
	}

	/**
	 * @param emplNumber the emplNumber to set
	 */
	public void setEmplNumber(String emplNumber) {
		this.emplNumber = emplNumber;
	}

	/**
	 * @return the positionCode
	 */
	public String getPositionCode() {
		return positionCode;
	}

	/**
	 * @param positionCode the positionCode to set
	 */
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
