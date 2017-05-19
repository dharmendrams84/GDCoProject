package com.employee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import com.forms.*;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

public class EmployeeDAO {

	static String driverUrl = "oracle.jdbc.driver.OracleDriver";

	static String url = "jdbc:oracle:thin:@localhost:1521:orcl";

	static String userName = "system";
	static String password = "system";

	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("oracle.jdbc.OracleDriver");  

			/*connection = DriverManager
					.getConnection(
							"stotst-scan.corp.gdglobal.ca:1521/stotst.corp.gdglobal.ca",
							"RCO_SCHEMA", "Rco_1120#");*/
			/*connection = DriverManager
					.getConnection(
							"172.31.16.239:1521/xe",
							"ORBO_OWNER", "password");*/
			
			connection = DriverManager
				     .getConnection(
				       "jdbc:oracle:thin:@stotst-scan.corp.gdglobal.ca:1521/stotst.corp.gdglobal.ca",
				       "RCO_SCHEMA", "Rco_1120#");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("connection established"+connection==null);
		return connection;
	}
	
	static Connection connection = null;
	static{
		System.out.println("inside static bolck");
		 connection = getConnection();
	}
	
	public List<EmployeeForm> getEmployeeDetailsOnFirstName(String firstName) {
		List<EmployeeForm> forms = new ArrayList<EmployeeForm>();
		try {
			
			String queryString = "select firstname,lastname,empl_disc_group_code,empl_status_code,empl_id,position_code,email,EMPL_NUMBER from ct_eep_empl_master where firstname like ?";

			PreparedStatement preparedStatement = connection
					.prepareStatement(queryString);
			preparedStatement.setString(1, "%"+firstName+"%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " : "
						+ resultSet.getString(2) + " : "
						+ resultSet.getString(3) + " : "
						+ resultSet.getString(4) + " : "
						+ resultSet.getString(5) + " : "
						+ resultSet.getString(6) + " : "
						+ resultSet.getString(7));
				EmployeeForm employeeForm = new EmployeeForm();
				employeeForm.setFirstName(resultSet.getString(1));
				employeeForm.setLastName(resultSet.getString(2));
				employeeForm.setEmplDiscGrpCode(resultSet.getString(3));
				employeeForm.setEmplStatus(resultSet.getString(4));
				employeeForm.setEmplId(resultSet.getInt(5));
				employeeForm.setPositionCode(resultSet.getString(6));
				employeeForm.setEmail(resultSet.getString(7));
				employeeForm.setEmplNumber(Integer.parseInt(resultSet.getString(8)));
				employeeForm.setEmplDiscGrpCodes(getEmployeeDiscGrpCodes());

				forms.add(employeeForm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return forms;
	}

	
	public List<EmployeeForm> getEmployeeDetails(int emplNumber) {
		List<EmployeeForm> forms = new ArrayList<EmployeeForm>();
		try {
			
			String queryString = "select firstname,lastname,empl_disc_group_code,empl_status_code,empl_id,position_code,email from ct_eep_empl_master where empl_number = ?";

			PreparedStatement preparedStatement = connection
					.prepareStatement(queryString);
			preparedStatement.setInt(1, emplNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + " : "
						+ resultSet.getString(2) + " : "
						+ resultSet.getString(3) + " : "
						+ resultSet.getString(4) + " : "
						+ resultSet.getString(5) + " : "
						+ resultSet.getString(6) + " : "
						+ resultSet.getString(7));
				EmployeeForm employeeForm = new EmployeeForm();
				employeeForm.setFirstName(resultSet.getString(1));
				employeeForm.setLastName(resultSet.getString(2));
				employeeForm.setEmplDiscGrpCode(resultSet.getString(3));
				employeeForm.setEmplStatus(resultSet.getString(4));
				employeeForm.setEmplId(resultSet.getInt(5));
				employeeForm.setPositionCode(resultSet.getString(6));
				employeeForm.setEmail(resultSet.getString(7));
				employeeForm.setEmplNumber(emplNumber);
				employeeForm.setEmplDiscGrpCodes(getEmployeeDiscGrpCodes());

				forms.add(employeeForm);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}/* catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/

		return forms;
	}

	public List<String> getEmployeeDiscGrpCodes() {
		List<String> discGrpcodes = new ArrayList<String>();
		try {

			//Class.forName(driverUrl);

			/*Connection connection = getConnection();*/

			String queryString = "select empl_disc_group_code from ct_eep_group";

			PreparedStatement preparedStatement = connection
					.prepareStatement(queryString);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				discGrpcodes.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} /*catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		// System.out.println("discount group codes "+discGrpcodes);
		return discGrpcodes;
	}

	public static void updateEmployeeDtls(EmployeeForm employeeForm) {
		try {

			String updateQuery = "update ct_eep_empl_master set firstname = ?,lastname = ?,empl_disc_group_code = ?,empl_status_code = ? ,"
					+ "position_code = ? ,email = ? where empl_number = ?";

			/*Connection connection = getConnection();*/

			PreparedStatement pstmt = connection.prepareStatement(updateQuery);
			pstmt.setString(1, employeeForm.getFirstName());
			pstmt.setString(2, employeeForm.getLastName());
			pstmt.setString(3, employeeForm.getEmplDiscGrpCode());
			pstmt.setString(4, employeeForm.getEmplStatus());
			pstmt.setString(5, employeeForm.getPositionCode());
			pstmt.setString(6, employeeForm.getEmail());
			pstmt.setInt(7, employeeForm.getEmplNumber());

			int rowsUpdated = pstmt.executeUpdate();
			pstmt.close();
			System.out.println("rowsUpdated " + rowsUpdated);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//getConnection();
			new EmployeeDAO().getEmployeeDetails(26481);

	}

}
