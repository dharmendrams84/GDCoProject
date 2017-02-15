/*This class is created to store constants related to CO Loyality changes*/

package gdyn.retail.stores.commerceservices.transaction;

import java.io.Serializable;

public class GDYNCOLoyalitConstants implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6527922550601136085L;
	public static Boolean isSearchByLoyalityId = false;
	
	public static Boolean isEmptryString(String str){
		if(str==null ||str.length()==0){
			return true;
		}else{
			return false;
		}
	}
}
