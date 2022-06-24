package onnuripay.properties;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

import onnuripay.consts.DefaultConfigValue;

public class DefaultPropertiesWrapperForSingleProducer extends DefaultPropertiesWrapper{

	private final Properties singleProp;

	public DefaultPropertiesWrapperForSingleProducer(Properties prop) {
		super(prop);
		this.singleProp = new Properties(this.getDefaultSuperTypeProp());
		
		if(this.singleProp.get(ProducerConfig.CLIENT_ID_CONFIG) == null) this.singleProp.put(ProducerConfig.CLIENT_ID_CONFIG, DefaultConfigValue.DEFAULT_PRODUCER_ID+this.getClass().getPackageName());
		
		this.setProp(singleProp);
	}
	
	public Properties getSingleProp() {
		return singleProp;
	}
	
	
}
