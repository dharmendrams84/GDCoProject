package com.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class HelloWorld{
    public static void main(String[] args) {

        try{
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();

            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");


            byte[] text = "No body can see me.".getBytes("UTF8");


            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);

            String s = new String(textEncrypted);
            System.out.println(s);

            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            byte[] textDecrypted = desCipher.doFinal(textEncrypted);

            s = new String(textDecrypted);
            
			File file = new File(
					"C:\\OracleRetailStore\\Client\\pos\\config\\test11.properties");
			FileWriter fw = new FileWriter(file);
			BufferedWriter output = new BufferedWriter(
					new FileWriter(
							"C:\\OracleRetailStore\\Client\\pos\\config\\test11.properties",
							true));
			output.write(textEncrypted.toString());
			output.flush();
			output.close();
			System.out.println(s);
        }catch(Exception e)
        {
            System.out.println("Exception");
        }
    }
}
