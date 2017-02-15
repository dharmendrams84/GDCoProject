package com.gdyn.co.employeediscount.ejb;

/**
 * SQL Interface for Employee Service
 * @author Monica
 * 
 */
public interface GDYNEmployeeDiscountSQLIfc 
{

	String EMPL_ID_SRC_COL = "empl_id_src";
	String EMPL_DISC_GROUP_CODE_COL = "empl_disc_group_code";
	String EMPL_STATUS_CODE_COL = "empl_status_code";
	String PERIOD_ID_COL = "period_id";
	String ENTITLEMENT_ID_COL = "ENTITLEMENT_ID" ;
	String EMPL_GROUP_ID_COL = "EMPL_GROUP_ID";
	String REMAINING_SPEND_COL = "REMAINING_SPEND";
	String DISCOUNT_DIVISION_COL = "DISCOUNT_DIVISION";
	String DISCOUNT_PERCENT_COL = "DISCOUNT_PERCENT";
	String MAX_SPEND_ENTITLED_COL = "MAX_SPEND_ENTITLED";
	String SPEND_LIMIT_COL = "SPEND_LIMIT";
	String FIRSTNAME_COL = "FIRSTNAME";
	String LASTNAME_COL = "LASTNAME";
	String TOTAL_SPEND_COL = "TOTAL_SPEND";
	
	

	
	
	String GET_RESPONSE_SELECT_SQL =  "SELECT empl_id_src,empl_disc_group_code, empl_status_code FROM ct_eep_empl_master WHERE empl_number=?";
	
	String SELECT_PERIOD_SQL="SELECT period_id FROM ct_eep_period WHERE sysdate BETWEEN period_start_date AND period_end_date";

	/*String ENTITLEMENT_SPEND_SQL="SELECT entl.DISCOUNT_DIVISION,entl.DISCOUNT_PERCENT, mas.empl_id_src,mas.firstname,mas.lastname,prd.period_id,sum.empl_group_id,sum.entitlement_id,mas.empl_number,"+
    "(case when  sum.remaining_spend>0 then sum.remaining_spend else entl.max_spend_entitled END) maxspend FROM ct_eep_empl_master mas,  ct_eep_group grp,  ct_eep_entitlement entl,  CT_VW_EEP_SUMMARY sum, ct_eep_period  prd WHERE mas.empl_number=?"+" AND sum.period_id   = ? "+
    "AND mas.empl_disc_group_code=grp.empl_disc_group_code AND grp.empl_group_id=entl.empl_group_id AND sum.empl_id_src=mas.empl_id_src AND sum.empl_group_id = grp.empl_group_id AND sum.entitlement_id  = entl.entitlement_id AND sum.period_id = prd.period_id";


	
	String EMP_SUMMARY_SPEND_SQL="SELECT entl.DISCOUNT_DIVISION,entl.DISCOUNT_PERCENT, mas.empl_id_src,mas.firstname,mas.lastname,prd.period_id,sum1.empl_group_id,sum1.entitlement_id,mas.empl_number,"+
		    "sum1.remaining_spend FROM ct_eep_empl_master mas,  ct_eep_group grp,  ct_eep_entitlement entl,  CT_VW_EEP_SUMMARY sum1, ct_eep_period  prd WHERE mas.empl_number=?"+" AND sum1.period_id   = ? "+
		    "AND mas.empl_disc_group_code=grp.empl_disc_group_code AND grp.empl_group_id=entl.empl_group_id AND sum1.empl_id_src=mas.empl_id_src AND sum1.empl_group_id = grp.empl_group_id AND sum1.entitlement_id  = entl.entitlement_id AND sum1.period_id = prd.period_id AND sum1.remaining_spend >0";
	*/
	String ENTITLEMENT_SPEND_SQL="SELECT entl.DISCOUNT_DIVISION,entl.DISCOUNT_PERCENT, mas.empl_id_src,mas.firstname,mas.lastname,prd.period_id,entl.empl_group_id,entl.entitlement_id,mas.empl_number,"+
		    "entl.max_spend_entitled FROM ct_eep_empl_master mas,  ct_eep_group grp,  ct_eep_entitlement entl, ct_eep_period  prd WHERE mas.empl_number=?"+" AND prd.period_id   = ? "+
		    "AND mas.empl_disc_group_code=grp.empl_disc_group_code AND grp.empl_group_id=entl.empl_group_id ";
	
	/*String TOTAL_SPEND_SQL="SELECT entl.DISCOUNT_DIVISION,"+
		    "sum1.total_spend FROM ct_eep_empl_master mas,  ct_eep_group grp,  ct_eep_entitlement entl,  CT_VW_EEP_SUMMARY sum1, ct_eep_period  prd WHERE mas.empl_number=?"+" AND sum1.period_id   = ? "+
		    "AND mas.empl_disc_group_code=grp.empl_disc_group_code AND grp.empl_group_id=entl.empl_group_id AND sum1.empl_id_src=mas.empl_id_src AND sum1.empl_group_id = grp.empl_group_id AND sum1.entitlement_id  = entl.entitlement_id AND sum1.period_id = prd.period_id";
	*/
	String TOTAL_SPEND_SQL="SELECT total_spend from CT_VW_EEP_SUMMARY where period_id=? AND entitlement_id=? AND empl_group_id=? AND empl_id_src=?";
		   
	
}
