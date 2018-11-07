package com.favccxx.report.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


public class EncryptUtil {
	
	public static final String HASH_METHOD_SHA = "sha";
	public static final String HASH_METHOD_SSHA = "ssha";
	public static final String HASH_METHOD_SHA_256 = "SHA-256";
	public static final String HASH_METHOD_MD5 = "md5";
	public static final String HASH_METHOD_SMD5 = "smd5";
	public static final String HASH_METHOD_CRYPT = "crypt";

	public static String _Base64Hash(String alg, byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance(alg);
		md.reset();
		md.update(data);
		byte[] buff = md.digest();
		return Base64.encodeBase64String(buff);
	}

	/**
	 * 
	 * @param alg 算法
	 * @param key 待加密字符串
	 * @param salt 加密盐
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String alg, String key, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		String base64_hash_pass = _Base64Hash(alg, key.getBytes("utf-8"));
		String data = base64_hash_pass + salt;
		return _Base64Hash(alg, data.getBytes("utf-8"));
	}

	/**
	 * 算法加密
	 * @param alg 算法
	 * @param key 待加密字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String alg, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String _alg = alg.toUpperCase();
		String base64_hash_pass = _Base64Hash(_alg, key.getBytes("utf-8"));
		return "{" + _alg + "}" + base64_hash_pass;
	}

}
