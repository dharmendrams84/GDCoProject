package com.gdyn.co.employeediscount.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Response Object for EmployeeDiscountServiceSB
 * @author Monica
 *
 */

public class GDYNEmployeeDiscResponseObject  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String employeeNumber ;
	private BigDecimal discPercentage ;
	private BigDecimal maxSpendLimit ;	
	private String empDiscGrpCode ;
	private String empStatusCode ;
	private String empIdSrc ;
	private String discDivision ;
	private int periodId ;
	private int empGroupId ;
	private int entitlementId ;
	private String firstName ;
	private String lastName ;
	private String code ;
	private BigDecimal totalSpend ;
	private BigDecimal maxSpendEntitled ;
	/**
	 * @return the maxSpendLimit
	 */
	public BigDecimal getMaxSpendLimit() {
		return maxSpendLimit;
	}
	/**
	 * @param maxSpendLimit the maxSpendLimit to set
	 */
	public void setMaxSpendLimit(BigDecimal maxSpendLimit) {
		this.maxSpendLimit = maxSpendLimit;
	}	
	
	/**
	 * @return the discPercentage
	 */
	public BigDecimal getDiscPercentage() {
		return discPercentage;
	}
	/**
	 * @param discPercentage the discPercentage to set
	 */
	public void setDiscPercentage(BigDecimal discPercentage) {
		this.discPercentage = discPercentage;
	}
	/**
	 * @return the employeeNumber
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * @param employeeNumber the employeeNumber to set
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * @return the empDiscGrpCode
	 */
	public String getEmpDiscGrpCode() {
		return empDiscGrpCode;
	}
	/**
	 * @param empDiscGrpCode the empDiscGrpCode to set
	 */
	public void setEmpDiscGrpCode(String empDiscGrpCode) {
		this.empDiscGrpCode = empDiscGrpCode;
	}
	/**
	 * @return the empStatusCode
	 */
	public String getEmpStatusCode() {
		return empStatusCode;
	}
	/**
	 * @param empStatusCode the empStatusCode to set
	 */
	public void setEmpStatusCode(String empStatusCode) {
		this.empStatusCode = empStatusCode;
	}
	/**
	 * @return the empIdSrc
	 */
	public String getEmpIdSrc() {
		return empIdSrc;
	}
	/**
	 * @param empIdSrc the empIdSrc to set
	 */
	public void setEmpIdSrc(String empIdSrc) {
		this.empIdSrc = empIdSrc;
	}
	/**
	 * @return the periodId
	 */
	public int getPeriodId() {
		return periodId;
	}
	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}
	/**
	 * @return the discDivision
	 */
	public String getDiscDivision() {
		return discDivision;
	}
	/**
	 * @param discDivision the discDivision to set
	 */
	public void setDiscDivision(String discDivision) {
		this.discDivision = discDivision;
	}
	/**
	 * @return the empGroupId
	 */
	public int getEmpGroupId() {
		return empGroupId;
	}
	/**
	 * @param empGroupId the empGroupId to set
	 */
	public void setEmpGroupId(int empGroupId) {
		this.empGroupId = empGroupId;
	}
	/**
	 * @return the entitlementId
	 */
	public int getEntitlementId() {
		return entitlementId;
	}
	/**
	 * @param entitlementId the entitlementId to set
	 */
	public void setEntitlementId(int entitlementId) {
		this.entitlementId = entitlementId;
	}
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the totalSpend
	 */
	public BigDecimal getTotalSpend() {
		return totalSpend;
	}
	/**
	 * @param totalSpend the totalSpend to set
	 */
	public void setTotalSpend(BigDecimal totalSpend) {
		this.totalSpend = totalSpend;
	}
	/**
	 * @return the maxSpendEntitled
	 */
	public BigDecimal getMaxSpendEntitled() {
		return maxSpendEntitled;
	}
	/**
	 * @param maxSpendEntitled the maxSpendEntitled to set
	 */
	public void setMaxSpendEntitled(BigDecimal maxSpendEntitled) {
		this.maxSpendEntitled = maxSpendEntitled;
	}
	

	
}
