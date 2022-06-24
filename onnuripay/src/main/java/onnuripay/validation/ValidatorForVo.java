package onnuripay.validation;

import datavo.msg.MessageWrapper;

public interface ValidatorForVo {

	/**
	 * 
	 * @apiNote 데이터 객체를 validation한다. 
	 * @return byte[] validation 및 메시지 to json 파싱이 성공할 경우, 해당 데이터를 default charset으로 인코딩한 byte[]을 반환한다.
	 * **/
	public byte[] validate(MessageWrapper data) throws IllegalArgumentException;
	
}
