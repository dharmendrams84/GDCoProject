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
	
	public static boolean isValidLoyaltyId(String loyaltyId) {
		if(loyaltyId.startsWith("0")||loyaltyId.startsWith("1")){
			return false;
		}
		try {
			Long.parseLong(loyaltyId);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	public static boolean validateLuhnAlgorithm(String ccNumber)
    {
            int sum = 0;
            boolean alternate = false;
            for (int i = ccNumber.length() - 1; i >= 0; i--)
            {
                    int n = Integer.parseInt(ccNumber.substring(i, i + 1));
                    if (alternate)
                    {
                            n *= 2;
                            if (n > 9)
                            {
                                    n = (n % 10) + 1;
                            }
                    }
                    sum += n;
                    alternate = !alternate;
            }
            return (sum % 10 == 0);
    }
}
