package com.lms.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public final class ImageUtil {

	private ImageUtil() {}
	
	public static String getBase64ImageFromByte(byte[] image){
		StringBuilder sb = new StringBuilder();
		sb.append("data:image/jpeg;base64,");
		sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(image, false)));
		return sb.toString();
	}

}
