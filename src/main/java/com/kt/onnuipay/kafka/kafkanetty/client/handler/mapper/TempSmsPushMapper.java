package com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;

@Profile("test")
@Repository
public class TempSmsPushMapper implements SmsPushMapper {

	@Override
	public UserInfoOnPush getIfSendYnByUserNo(String userToken) {
		// TODO Auto-generated method stub
		if(userToken.hashCode()%2==0) {
			return new UserInfoOnPush(true);
		}
		return new UserInfoOnPush(false);
	}
}
