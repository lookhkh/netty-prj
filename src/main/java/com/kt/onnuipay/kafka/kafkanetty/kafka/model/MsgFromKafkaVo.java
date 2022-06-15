package com.kt.onnuipay.kafka.kafkanetty.kafka.model;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * 
 * **/

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

@Data
public class MsgFromKafkaVo{

	
	private final KafkaKeyEnum key;
    private final MsgType msgType;
	private final boolean isScheduled;
	private final TypeOfSending kind;
	private final String timeOfDelievery;
	private final String sender;
	
    private List<DataBody> payload;    

	private final String actionUrl;
	private List<String> target;

	
	@JsonCreator
	@Builder
	public MsgFromKafkaVo(@JsonProperty("key") KafkaKeyEnum key,
						  @JsonProperty("type")MsgType type, 
						  @JsonProperty("tokens") List<String> target, 
						  @JsonProperty("payload") List<DataBody> payload, 
						  @JsonProperty("sender") String sender,
						  @JsonProperty("scheduled") boolean isScheduled,
						  @JsonProperty("timeOfDelievery")String timeOfDelievery, 
						  @JsonProperty("actionUrl") String actionUrl,
						  @JsonProperty("kind")TypeOfSending kind) {
		this.key = key;
		this.msgType = type;
		this.target = target;
		this.payload = payload;
		this.sender = sender;
		this.isScheduled = isScheduled;
		this.timeOfDelievery = timeOfDelievery;
		this.actionUrl = actionUrl;
		this.kind = kind;
	}

	/**
	 * 
	 * @return 0 : SINGLE / 1 : MULTIPLE
	 * 
	 * **/
	@JsonIgnore
	public int getCodeOfType() {
		return this.kind.getCode();	
	}

	/**
	 * @return 0 : android | 1 : IOS | 2 : SMS 
	 * 
	 * **/
	@JsonIgnore
	public int getTypeValue() {
		// TODO Auto-generated method stub
		return this.key.getTypeCode();
	}
	
	/**
	 * @implSpec <p> 제목 30bytes 제한 <br/> 
	 * 				내용 if sms -> up to 2000 bytes <br/>
	 *                     push -> up to 200 bytes <br/>
	 *                     
	 *              Tokens lists size up to 500 
	 * </p>
	 * 
	 * **/
	@JsonIgnore
	private boolean validateDataBody(DataBody payload) {
		String title = payload.getTitle();
		String body = payload.getBody();
		
		int lengthOfTitle = title.getBytes(Charset.forName("utf-8")).length;
		int lengthOfBody = body.getBytes(Charset.forName("utf-8")).length ;
		
	
		
		if(this.getTypeValue() == 2) {
			
			return lengthOfTitle<=30 
					&& lengthOfBody  <= 2000;	
		}else {
			
			return lengthOfTitle <= 30 && lengthOfBody<=200 && target.size() <= 500; 
			
		}
	}
	
	public Map<Boolean, List<DataBody>> validateDataBodys() {
		return this.payload.stream()
				.collect(Collectors.groupingBy(item -> this.validateDataBody(item)));
	}
	
	

}
