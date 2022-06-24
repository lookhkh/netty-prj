package onnuripay.validation;

import datavo.msg.MessageWrapper;

public interface ValidatorForVo {

	/**
	 * 
	 * @apiNote ������ ��ü�� validation�Ѵ�. 
	 * @return byte[] validation �� �޽��� to json �Ľ��� ������ ���, �ش� �����͸� default charset���� ���ڵ��� byte[]�� ��ȯ�Ѵ�.
	 * **/
	public byte[] validate(MessageWrapper data) throws IllegalArgumentException;
	
}
