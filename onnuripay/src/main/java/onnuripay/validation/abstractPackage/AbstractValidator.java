package onnuripay.validation.abstractPackage;

import datavo.msg.MessageWrapper;
import onnuripay.consts.DefaultConfigValue;
import onnuripay.util.SerializerWrapper;
import onnuripay.validation.ValidatorForVo;


/**
 * @apiNote validator, 사용자는 해당 클래스를 재정의 이후 벨리데이션을 반드시 진행해야 한다.
 * @author cho hyun il lookhkh37@gmail.com
 * 
 * **/
public abstract class AbstractValidator implements ValidatorForVo {

	private final SerializerWrapper serializer;
	
	

	
	public AbstractValidator(SerializerWrapper serializer) {
		super();
		this.serializer = serializer;
	}


	@Override
	public byte[] validate(MessageWrapper data) {
		
		if(isAllowdToGetNotified(data) && isDataVoValid(data)) {
			 try {
				return serializer.toJson(data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new IllegalArgumentException(data+" 파싱 중 문제가 발생하였습니다. 메시지 포맷을 다시 확인해주세요 [charset =>"+DefaultConfigValue.DEFAULT_CHARSET);
			}
		}
		
		throw new IllegalArgumentException("validation이 실패하였습니다");
	}


	/**
	 * 
	 * @apiNote 송신하고자 하는 유저의 알림 수락 여부를 검사한다. 알림을 수신하지 않을 시, false를 반환한다.
	 * 
	 * **/
	protected abstract boolean isAllowdToGetNotified(MessageWrapper data);
	

	/**
	 * 
	 * @apiNote 보내고자 하는 Data 객체의 validation을 수행한다. 밸리데이션에 실패할 시 false를 반환한다. 
	 * 
	 * **/
	protected abstract boolean isDataVoValid(MessageWrapper data);

}
