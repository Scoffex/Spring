package com.progetto.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.progetto.service_impl.UtenteStandardServiceImpl;

@Component
public class Crypthography {
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final byte[] INIT_VECTOR = new byte[128 / 8];
	private static final String PASSWORD = ParamReader.getParam(Constants.PASSWORD_ENCRYPT);
	private static final Logger logger = LoggerFactory.getLogger(UtenteStandardServiceImpl.class);

	public static String encrypt(String valueToEncrypt) {
		IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR);
		SecretKeySpec key = new SecretKeySpec(PASSWORD.getBytes(), "AES");

		byte[] encryptedBytes = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

			encryptedBytes = cipher.doFinal(valueToEncrypt.getBytes());
		} catch (Exception e) {
			logger.error("ERROR ENCRPYTING: ", e);
		}

		return new String(Base64.encodeBase64(encryptedBytes, false));
	}

	public static String decrypt(String encryptedValue) {
		IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR);
		SecretKeySpec key = new SecretKeySpec(PASSWORD.getBytes(), "AES");

		byte[] decryptedBytes = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);

			byte[] decodedBytes = Base64.decodeBase64(encryptedValue);

			decryptedBytes = cipher.doFinal(decodedBytes);
		} catch (Exception e) {
			logger.error("ERROR DECRYPTING: ", e);
		}

		return new String(decryptedBytes);

	}
	
	
//	public static final void main (String[] args) {
//		Crypthography cry = new Crypthography();
//		String password = cry.decrypt("Md/Kmbet+zW5Ju+7psSVYg==");
//		System.out.println(password);
//				
//	}

}