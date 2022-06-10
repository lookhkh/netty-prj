package com.kafka.kafkanetty.client.handler.manager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO DB 스키마 활용하여 유저에게 PUSH를 보낼 때 어떤 정보를 활용할지 확인 220610
 * 
 * **/
@Data
@AllArgsConstructor
public class UserInfoOnPush {

	private boolean pushYn;
	
	
	public boolean getPushYn() {

		return this.pushYn;
	}


	public boolean validation() {
		// TODO DB에서 가져온 유저 정보 Validation 로직 추가. 220610 조현일
		return true;
	}

}
