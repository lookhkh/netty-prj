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
package com.kt.onnuripay.kafka.client.handler.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Profile;

import com.kt.onnuripay.kafka.model.ResultOfPush;


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

@Mapper
@Profile(value={"default","local","prod"})
public interface SmsPushMapper {

    /**
     * 
     * @param result Message Push 결과 입력 메소드.
     * TODO 데이터 모델링이 결정되면 그에 맞게 XML 작성 필요 220715 조현일
     */
    void insertDbHistory(ResultOfPush result);

	/**
	 * @param userToken 전송 대상 유저의 정보를 불러오기 위한 데이터 객체
	 * @return 대상이 되는 유저가 APP 수신을 설정했는지 확인하기 위한 VO 객체
	 * 
	 * **/

}
