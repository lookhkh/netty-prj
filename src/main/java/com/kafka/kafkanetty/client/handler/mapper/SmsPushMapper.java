package com.kafka.kafkanetty.client.handler.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;

@Mapper
public interface SmsPushMapper {

	UserInfoOnPush getIfSendYnByUserNo(MsgFromKafkaVo vo);

}
