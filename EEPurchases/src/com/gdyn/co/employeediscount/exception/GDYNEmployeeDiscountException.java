/**
 * 
 */
package com.gdyn.co.employeediscount.exception;

/**
 * @author Monica
 *
 */
public class GDYNEmployeeDiscountException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8772668263073947510L;
	
	public GDYNEmployeeDiscountException(String message)
	{
		super(message);
	}
	
	public GDYNEmployeeDiscountException(Throwable throwable)
	{
		super(throwable);
	}
	
	public GDYNEmployeeDiscountException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

}
