/*package com.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
public class TestClass {

	*//**
	 * @param args
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 *//*
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
	
		//System.out.println("Decoded value is " + new String(valueDecoded));
		FileInputStream in = new FileInputStream("F:\\logs\\application.properties");
		Properties props = new Properties();
		props.load(in);
		
		//System.out.println("test Key "+props.get("testKey"));
		String str = "Dharmendra 1234"; 
		byte[]   bytesEncoded = Base64.encodeBase64(str .getBytes());
		System.out.println("ecncoded value is " + new String(bytesEncoded ));
		
		// Decode data on other side, by processing encoded data
	
		 File file = new File("C:\\OracleRetailStore\\Client\\pos\\config\\test11.properties");
		 FileWriter fw =  new FileWriter(file);
		 BufferedWriter output = new BufferedWriter(new FileWriter("C:\\OracleRetailStore\\Client\\pos\\config\\test11.properties", true));

		 output.write(new String(bytesEncoded));
		 output.flush();
		 output.close();
		
			//byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
			
		Properties pop = new Properties();
		pop.load(new FileInputStream("F:\\logs\\application.properties"));
		pop.put("java.naming.security.credentials", "testValue1");
		FileOutputStream output = new FileOutputStream("F:\\logs\\application.properties");
		pop.store(output, null);

		//output.flush();
		//output.close();
			FileOutputStream out = new FileOutputStream("F:\\logs\\application.properties");
			props.setProperty("testKey", new String(bytesEncoded));
			props.store(out, null);
			out.close();
			 in.close();

	}

}
*/