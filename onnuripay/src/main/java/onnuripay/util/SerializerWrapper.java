/**
 * 
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */

package onnuripay.util;

import java.nio.charset.Charset;

import datavo.msg.MessageWrapper;
import datavo.msg.util.MessageUtils;


/**
 * @apiNote MessageWrapper 직렬화 클래스. 직렬화 및 역직렬화 수행
 * @author cho hyun il 
 * @date 2022. 6. 24.
 *
 */
public class SerializerWrapper {
	
	public static String DEFAULT_CHARSET = "utf-8";

	public byte[] toJson(MessageWrapper data) {
		return MessageUtils.toJson(data, MessageWrapper.class).getBytes(Charset.forName(DEFAULT_CHARSET));
	}

}
