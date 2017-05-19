package com.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WritePropertiesFile {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		FileInputStream in = new FileInputStream("C:\\OracleRetailStore\\Client\\pos\\config\\test11.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream("C:\\OracleRetailStore\\Client\\pos\\config\\test11.properties");
		props.setProperty("country", "america1");
		props.store(out, null);
		out.close();


	}

}
