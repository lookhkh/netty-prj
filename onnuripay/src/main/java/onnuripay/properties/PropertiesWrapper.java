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
 * TODO 설정정보 더 조사 후 보완하기 220620 조현일
 * 
 * @see https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html producer 설정 정보 참고
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
