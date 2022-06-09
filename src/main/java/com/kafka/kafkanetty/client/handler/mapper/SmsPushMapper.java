package com.kafka.kafkanetty.client.handler.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsPushMapper {

	boolean getIfSendYnByUserNo(String userNo);

}
