package onnuripay.properties;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

import onnuripay.consts.CommonMsg;
import onnuripay.consts.DefaultConfigValue;


/**
 * 
 */

/**
 * TODO �������� �� ���� �� �����ϱ� 220620 ������
 * 
 * @see https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html producer ���� ���� ����
 * @author cho hyun il 
 * 
 * **/
public class PropertiesWrapper  {

	private Properties prop;
	
	public PropertiesWrapper() {
	}
	
	public Properties getConfigProp() {
		return prop;
	}
	
	public void setProp(Properties prop) {
		this.prop = prop;
	}

	
	

	
}
