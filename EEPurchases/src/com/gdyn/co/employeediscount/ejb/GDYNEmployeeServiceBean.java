/**
 * 
 */
package com.gdyn.co.employeediscount.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import oracle.retail.stores.foundation.common.SessionBeanAdapter;

import org.apache.log4j.Logger;

import com.gdyn.co.employeediscount.exception.GDYNEmployeeDiscountException;
import com.gdyn.co.employeediscount.response.GDYNEmployeeDiscResponseObject;

/**
 * Service bean class for Employee Purchases Service
 * @author Monica
 *
 */
public class GDYNEmployeeServiceBean extends SessionBeanAdapter implements GDYNEmployeeDiscountSQLIfc
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5887100376844253559L;
	  /** Logger for debug logging. */
    private static final Logger logger = Logger.getLogger(com.gdyn.co.employeediscount.ejb.GDYNEmployeeServiceBean.class);
	//private Logger logger = getLogger();


	/**
	 * Gets the GDYNEmployeeDiscResponseObject based on the passed request criteria.
	 * @param employeeNumber
	 * @return GDYNEmployeeDiscResponseObject
	 * @throws Exception
	 */
	public GDYNEmployeeDiscResponseObject[] getEmployeeResponseObject(String employeeNumber,Connection connection) throws Exception
	{

		//Initialisation of variables
		//Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		GDYNEmployeeDiscResponseObject responseObject = null;
		GDYNEmployeeDiscResponseObject[] responseObjectList=null;
		List<GDYNEmployeeDiscResponseObject> list = new ArrayList<GDYNEmployeeDiscResponseObject>();

		if(employeeNumber != null)
		{
			try
			{		
					//Getting DB Connection
					//connection = getDBUtils().getConnection();
					
					if(connection != null)
					{

						preparedStatement = connection.prepareStatement(GET_RESPONSE_SELECT_SQL);

						preparedStatement.setString(1, employeeNumber);						

						logger.debug("Response object SQL: " +GET_RESPONSE_SELECT_SQL);
						resultSet = preparedStatement.executeQuery();
                         int count =0;
                         responseObject = new GDYNEmployeeDiscResponseObject();
						while(resultSet.next())
						{
							//Processing the ResultSet.
							logger.debug("Processing response object");
							
							responseObject.setEmpIdSrc(resultSet.getString(EMPL_ID_SRC_COL));
							responseObject.setEmpDiscGrpCode(resultSet.getString(EMPL_DISC_GROUP_CODE_COL));
							responseObject.setEmpStatusCode(resultSet.getString(EMPL_STATUS_CODE_COL));
							responseObject.setEmployeeNumber(employeeNumber);
							count++;
							break;

						}
						//if there is no employee below status code is returned.
						if(count<=0)
						{
							responseObject.setEmpStatusCode("EMPL_NOT_FOUND");
							logger.debug("Employee" +employeeNumber +"is not found in CT_EEP_EMPL_MASTER");
							list.add(responseObject);
							responseObjectList= list.toArray(new GDYNEmployeeDiscResponseObject[list.size()]);
						}


						preparedStatement.close();
						resultSet.close();
						
						if(responseObject != null && count>0)
						{
							if(responseObject.getEmpStatusCode().equalsIgnoreCase("A")|| responseObject.getEmpStatusCode().equalsIgnoreCase("L"))
							{
								
								responseObjectList = selectEmployeeEntitlement(responseObject,connection);
								if(responseObjectList.length==0)
								{
									responseObject.setCode("NO_DATA_FOUND");
									logger.debug("There is no entitlement data for Employee" +employeeNumber);
									list.add(responseObject);
									responseObjectList= list.toArray(new GDYNEmployeeDiscResponseObject[list.size()]);
								}
								else if(responseObjectList.length!=0)
								{   
									//get total spend and calculate remaining spend for employee
									for(int i=0;i<responseObjectList.length;i++)
									{									
									preparedStatement = connection.prepareStatement(TOTAL_SPEND_SQL);									
									preparedStatement.setInt(1, responseObjectList[i].getPeriodId());
									preparedStatement.setInt(2, responseObjectList[i].getEntitlementId());
									preparedStatement.setInt(3, responseObjectList[i].getEmpGroupId());
									preparedStatement.setString(4, responseObjectList[i].getEmpIdSrc());
									
									logger.debug("Total Spend SQL: " +TOTAL_SPEND_SQL);
									resultSet = preparedStatement.executeQuery();			
									
									BigDecimal totalSpend=null;
									while(resultSet.next())
									{
										
									  totalSpend=resultSet.getBigDecimal(TOTAL_SPEND_COL);	
										if(totalSpend!=null)
										{
										BigDecimal remainingSpend=responseObjectList[i].getMaxSpendEntitled().subtract(totalSpend);
										//Added by Monica if employee gets demoted and the remaining spend is negative is set to '0' 
										if(remainingSpend.compareTo(BigDecimal.ZERO)<0)
										{
										remainingSpend=new BigDecimal(0);
									
										}
										responseObjectList[i].setMaxSpendLimit(remainingSpend);
										}
										else
										{
											BigDecimal remainingSpend=responseObjectList[i].getMaxSpendEntitled().subtract(new BigDecimal(0));
											responseObjectList[i].setMaxSpendLimit(remainingSpend);
										}
										//Processing the ResultSet.
										logger.debug("Processing the total spend response object");						
										
									}
									
									
									//If there is no result for total spend set the default total spend value to '0'
									if(responseObjectList[i].getMaxSpendLimit()==null || totalSpend==null)
									{
										BigDecimal remainingSpend=responseObjectList[i].getMaxSpendEntitled().subtract(new BigDecimal(0));
										responseObjectList[i].setMaxSpendLimit(remainingSpend);
									}
									}
								
									preparedStatement.close();
									resultSet.close();									
								}
							}
							//If the employee is inactive then employee status code is set to "1"
							else
							{
								responseObject.setEmpStatusCode("1");
								list.add(responseObject);
								responseObjectList= list.toArray(new GDYNEmployeeDiscResponseObject[list.size()]);
							}
							
						}

					}
					else
					{
						logger.error("Unable get CO DB connection");
						throw new GDYNEmployeeDiscountException("Unable get CO DB connection");
					}
				}
				

						
			catch(SQLException sqlException)
			{
				logger.error("Database Exception while getting response object from CO: ", sqlException);
				throw new GDYNEmployeeDiscountException("Database Exception while getting response object from CO: ", sqlException);
			}
			catch(Exception exception)
			{
				logger.error("Error in getting response object from CO: ", exception);
				throw new GDYNEmployeeDiscountException("Error in getting response object from CO: ", exception);
			}
			finally
			{
				//Close all the DB connections, statements and result sets.
				getDBUtils().close(connection, preparedStatement, resultSet);
			}
		}
		return responseObjectList;
	}
	
	/**
	 * Selects employee entitlement details within the current period.
	 * @param responseObject
	 * @return GDYNEmployeeDiscResponseObject
	 * @throws Exception
	 */
	public GDYNEmployeeDiscResponseObject[] selectEmployeeEntitlement(GDYNEmployeeDiscResponseObject responseObject,Connection connection) throws Exception 
	{
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer periodId=0;
		//GDYNEmployeeDiscResponseObject responseObject = null;
		List<GDYNEmployeeDiscResponseObject> list = new ArrayList<GDYNEmployeeDiscResponseObject>();

		try
		{
			preparedStatement = connection.prepareStatement(SELECT_PERIOD_SQL);

			logger.debug("Response object SQL: " +SELECT_PERIOD_SQL);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				//Processing the ResultSet.
				logger.debug("Processing response object");
				periodId=resultSet.getInt(PERIOD_ID_COL);
			}
			
			if(periodId!=null)
			{				
				
					preparedStatement = connection.prepareStatement(ENTITLEMENT_SPEND_SQL);				
					preparedStatement.setString(1, responseObject.getEmployeeNumber());
					preparedStatement.setInt(2, periodId);
					logger.debug("Entitlement Spend SQL: " +ENTITLEMENT_SPEND_SQL);
				
					resultSet = preparedStatement.executeQuery();	
					
					while(resultSet.next())
					{
						GDYNEmployeeDiscResponseObject responseObjectList = new GDYNEmployeeDiscResponseObject();
						responseObjectList.setDiscDivision(resultSet.getString(DISCOUNT_DIVISION_COL));
						responseObjectList.setDiscPercentage(resultSet.getBigDecimal(DISCOUNT_PERCENT_COL));
						responseObjectList.setEmpIdSrc(resultSet.getString(EMPL_ID_SRC_COL));
						responseObjectList.setPeriodId(resultSet.getInt(PERIOD_ID_COL));
						responseObjectList.setEmpGroupId(resultSet.getInt(EMPL_GROUP_ID_COL));
						responseObjectList.setEntitlementId(resultSet.getInt(ENTITLEMENT_ID_COL));						
						responseObjectList.setMaxSpendEntitled(resultSet.getBigDecimal(MAX_SPEND_ENTITLED_COL));
						responseObjectList.setEmployeeNumber(responseObject.getEmployeeNumber());
						responseObjectList.setFirstName(resultSet.getString(FIRSTNAME_COL));
						responseObjectList.setLastName(resultSet.getString(LASTNAME_COL));
						responseObjectList.setEmpStatusCode(responseObject.getEmpStatusCode());
						//Processing the ResultSet.
						logger.debug("Processing response object");						
						list.add(responseObjectList);
									
					}					
				
				}
			
			preparedStatement.close();
			resultSet.close();
						
		}
		catch (SQLException sqlException) 
		{
			logger.error("Database Exception while reading the data from entitlement table:", sqlException);			
			throw new GDYNEmployeeDiscountException("Database Exception while reading the data from entitlement table: ", sqlException);
		} 
		catch (Exception exception) 
		{
			logger.error("Database Exception while reading the data from entitlement table:",exception);
			throw new GDYNEmployeeDiscountException("Database Exception while reading the data from entitlement table:", exception);
		} 
		/*finally 
		{
			getDBUtils().close(connection, preparedStatement, resultSet);
		}*/
		return list.toArray(new GDYNEmployeeDiscResponseObject[list.size()]);

		
	}
	
	
}
