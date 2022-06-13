package com.kafka.kafkanetty.kafka.model;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * 
 * **/
@Data
public class MsgFromKafkaVo {

	
	private final KafkaKeyEnum key;
    
	private final TypeOfSending type;
	
	private final List<String> tokens;
    
	private List<DataBody> payload;
	
	private final String id;
	
	private final boolean isScheduled;
	
	private final String timeOfDelievery;
	
	private final String actionUrl;
	
	
	
	@Builder
	public MsgFromKafkaVo(KafkaKeyEnum key, TypeOfSending type, List<String> tokens, List<DataBody> payload, String id,
			boolean isScheduled, String timeOfDelievery, String actionUrl) {
		this.key = key;
		this.type = type;
		this.tokens = tokens;
		this.payload = payload;
		this.id = id;
		this.isScheduled = isScheduled;
		this.timeOfDelievery = timeOfDelievery;
		this.actionUrl = actionUrl;
	}

	/**
	 * 
	 * @return 0 : SINGLE / 1 : MULTIPLE
	 * 
	 * **/
	@JsonIgnore
	public int getCodeOfType() {
		return this.type.getCode();	
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
			
			return lengthOfTitle <= 30 && lengthOfBody<=200 && tokens.size() <= 500; 
			
		}
	}
	
	public Map<Boolean, List<DataBody>> validateDataBodys() {
		return this.payload.stream()
				.collect(Collectors.groupingBy(item -> this.validateDataBody(item)));
	}

}
