package com.kt.onnuipay.kafka.kafkanetty.kafka;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;


/*
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

public interface DispatcherController {

	
	public ResultOfPush route(String msg);

}
