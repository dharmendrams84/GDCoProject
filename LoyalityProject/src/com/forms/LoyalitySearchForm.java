package com.forms;

import org.apache.struts.action.ActionForm;

public class LoyalitySearchForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loyalityId ;
	
	private String loyalityEmailId;

	public String getLoyalityId() {
		return loyalityId;
	}

	public void setLoyalityId(String loyalityId) {
		this.loyalityId = loyalityId;
	}

	public String getLoyalityEmailId() {
		return loyalityEmailId;
	}

	public void setLoyalityEmailId(String loyalityEmailId) {
		this.loyalityEmailId = loyalityEmailId;
	}
}
