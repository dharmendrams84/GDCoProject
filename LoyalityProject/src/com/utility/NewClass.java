package com.utility;

public class NewClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mystring = "161814041";
		Long l1= new Long("0161814041");
		Long l2= new Long("00000161814041");
		//System.out.println(l1.equals(l2));
		String leftPaddedString = String.format("%015d", Integer.parseInt(mystring));
		System.out.println("leftPaddedString "+leftPaddedString);
	}

}
