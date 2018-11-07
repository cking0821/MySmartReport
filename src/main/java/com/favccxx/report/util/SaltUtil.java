package com.favccxx.report.util;

import org.apache.commons.text.RandomStringGenerator;

import com.favccxx.report.constants.SysConstants;


public class SaltUtil {
	
	public static String getSalt() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
			     .withinRange('a', 'z').build();
		String randomLetters = generator.generate(20);
		SessionUtil.getSession().setAttribute(SysConstants.USER_SALT_KEY, randomLetters);
		return randomLetters;
	}

}
