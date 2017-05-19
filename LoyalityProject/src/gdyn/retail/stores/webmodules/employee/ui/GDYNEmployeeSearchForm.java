package gdyn.retail.stores.webmodules.employee.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class GDYNEmployeeSearchForm extends ActionForm  {

    private static final long serialVersionUID = -473562596852452021L;

    private String emplNumber;
    
    private String emplFirstName;
    
    private String emplLastName;
    
    private String positionCode;
    
    private String emplEmail;
    
    private List<String> discGrpCodes = new ArrayList<String>();

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
	 * @return the emplFirstName
	 */
	public String getEmplFirstName() {
		return emplFirstName;
	}

	/**
	 * @param emplFirstName the emplFirstName to set
	 */
	public void setEmplFirstName(String emplFirstName) {
		this.emplFirstName = emplFirstName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the emplLastName
	 */
	public String getEmplLastName() {
		return emplLastName;
	}

	/**
	 * @param emplLastName the emplLastName to set
	 */
	public void setEmplLastName(String emplLastName) {
		this.emplLastName = emplLastName;
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
	 * @return the emplEmail
	 */
	public String getEmplEmail() {
		return emplEmail;
	}

	/**
	 * @param emplEmail the emplEmail to set
	 */
	public void setEmplEmail(String emplEmail) {
		this.emplEmail = emplEmail;
	}

	/**
	 * @return the discGrpCodes
	 */
	public List<String> getDiscGrpCodes() {
		return discGrpCodes;
	}

	/**
	 * @param discGrpCodes the discGrpCodes to set
	 */
	public void setDiscGrpCodes(List<String> discGrpCodes) {
		this.discGrpCodes = discGrpCodes;
	}


   

}
