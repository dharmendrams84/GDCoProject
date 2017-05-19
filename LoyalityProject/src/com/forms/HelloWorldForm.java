package com.forms;

import org.apache.struts.action.ActionForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;


public class HelloWorldForm extends ActionForm  {

    private static final long serialVersionUID = -473562596852452021L;

    private String emplNumber;
    
    private String emplFirstName;

	public String getEmplFirstName() {
		return emplFirstName;
	}

	public void setEmplFirstName(String emplFirstName) {
		this.emplFirstName = emplFirstName;
	}

	public String getEmplNumber() {
		return emplNumber;
	}

	public void setEmplNumber(String emplNumber) {
		this.emplNumber = emplNumber;
	}

   public static void main(String[] args) {
	   try{
		   File file = new File("test.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);

			}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	
	   }

}
