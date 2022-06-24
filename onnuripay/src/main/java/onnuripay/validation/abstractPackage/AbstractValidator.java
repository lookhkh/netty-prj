package onnuripay.validation.abstractPackage;

import datavo.msg.MessageWrapper;
import onnuripay.consts.DefaultConfigValue;
import onnuripay.util.SerializerWrapper;
import onnuripay.validation.ValidatorForVo;


/**
 * @apiNote validator, ����ڴ� �ش� Ŭ������ ������ ���� �������̼��� �ݵ�� �����ؾ� �Ѵ�.
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
				throw new IllegalArgumentException(data+" �Ľ� �� ������ �߻��Ͽ����ϴ�. �޽��� ������ �ٽ� Ȯ�����ּ��� [charset =>"+DefaultConfigValue.DEFAULT_CHARSET);
			}
		}
		
		throw new IllegalArgumentException("validation�� �����Ͽ����ϴ�");
	}


	/**
	 * 
	 * @apiNote �۽��ϰ��� �ϴ� ������ �˸� ���� ���θ� �˻��Ѵ�. �˸��� �������� ���� ��, false�� ��ȯ�Ѵ�.
	 * 
	 * **/
	protected abstract boolean isAllowdToGetNotified(MessageWrapper data);
	

	/**
	 * 
	 * @apiNote �������� �ϴ� Data ��ü�� validation�� �����Ѵ�. �븮���̼ǿ� ������ �� false�� ��ȯ�Ѵ�. 
	 * 
	 * **/
	protected abstract boolean isDataVoValid(MessageWrapper data);

}
