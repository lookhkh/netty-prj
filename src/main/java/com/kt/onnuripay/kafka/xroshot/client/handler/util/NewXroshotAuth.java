/* 
* Copyright ⓒ2019 kt corp. All rights reserved.
*
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.kt.onnuripay.kafka.xroshot.client.handler.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewXroshotAuth {
	private static final Logger log = LoggerFactory.getLogger(NewXroshotAuth.class);

	/**
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypto1(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(bytes);

		byte[] hashBytes = md.digest();

		return cryptDeriveKey(hashBytes, "SHA1", 24);
	}

	/**
	 * 
	 * @param hBaseData
	 * @param hashAlgorithm
	 * @param requiredLength
	 * @return
	 * @throws Exception
	 */
	public static byte[] cryptDeriveKey(byte[] hBaseData, String hashAlgorithm, int requiredLength) throws Exception {
		int keyLength = hBaseData.length;
		byte[] derivedKey = new byte[requiredLength];
		if (keyLength >= requiredLength) {
			System.arraycopy(hBaseData, 0, derivedKey, 0, requiredLength);
			return derivedKey;
		}

		byte[] buff1 = new byte[64];
		byte[] buff2 = new byte[64];

		Arrays.fill(buff1, (byte) 0x36);
		Arrays.fill(buff2, (byte) 0x5C);

		for (int i = 0; i < keyLength; i++) {
			buff1[i] ^= hBaseData[i];
			buff2[i] ^= hBaseData[i];
		}

		try {
			MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
			md.reset();
			// use the named algorithm to hash those buffers

			byte[] result1 = md.digest(buff1);
			md.reset();

			byte[] result2 = md.digest(buff2);
			for (int i = 0; i < requiredLength; i++) {
				if (i < result1.length) {
					derivedKey[i] = result1[i];
				} else {
					derivedKey[i] = result2[i - result1.length];
				}
			}
		} catch (NoSuchAlgorithmException e) {
			log.debug(e.getMessage());
		}

		return derivedKey;
	}

	/**
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 * @throws Exception
	 */
	public static String encrypto2(byte[] data1, byte[] data2) throws Exception {
		byte[] enc1 = encrypto1(data2);
		SecretKeySpec sks = new SecretKeySpec(enc1, 0, 16, "AES");

		byte[] iv = new byte[16];
		IvParameterSpec ivps = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sks, ivps);
		byte[] enc2 = cipher.doFinal(data1);

		return new String(Base64.encodeBase64(enc2));
	}

	/**
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypto2(String data, byte[] key) throws Exception {
		byte[] decData = Base64.decodeBase64(data);

		byte[] encKey = encrypto1(key);
		SecretKeySpec sks = new SecretKeySpec(encKey, 0, 16, "AES");

		byte[] iv = new byte[16];
		IvParameterSpec ivps = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, sks, ivps);
		byte[] dec1 = cipher.doFinal(decData);

		return new String(dec1);
	}

}
