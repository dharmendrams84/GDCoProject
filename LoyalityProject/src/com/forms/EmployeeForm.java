package com.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class EmployeeForm extends ActionForm{
	
	private String firstName ;
	
	private String lastName;
	
	private String emplDiscGrpCode;
	
	private List<String> emplDiscGrpCodes;
	
	private String emplStatus;
	
	public List<String> getEmplDiscGrpCodes() {
		return emplDiscGrpCodes;
	}

	public void setEmplDiscGrpCodes(List<String> emplDiscGrpCodes) {
		this.emplDiscGrpCodes = emplDiscGrpCodes;
	}

	private int emplId;
	
	private String jobName;
	
	public int getEmplNumber() {
		return emplNumber;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setEmplNumber(int emplNumber) {
		this.emplNumber = emplNumber;
	}

	private String emplIdSrc;
	
	private int emplNumber;
	
	public String getEmplIdSrc() {
		return emplIdSrc;
	}

	public void setEmplIdSrc(String emplIdSrc) {
		this.emplIdSrc = emplIdSrc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmplDiscGrpCode() {
		return emplDiscGrpCode;
	}

	public void setEmplDiscGrpCode(String emplDiscGrpCode) {
		this.emplDiscGrpCode = emplDiscGrpCode;
	}

	public String getEmplStatus() {
		return emplStatus;
	}

	public void setEmplStatus(String emplStatus) {
		this.emplStatus = emplStatus;
	}

	public int getEmplId() {
		return emplId;
	}

	public void setEmplId(int emplId) {
		this.emplId = emplId;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String positionCode ;
	
	private String email;
	
}
