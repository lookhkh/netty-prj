package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResourceInfo{
	
	@JacksonXmlProperty(localName = "Category")
	private final String category;
	@JacksonXmlProperty(localName = "ResourceID")
	private final String resourceId;
	@JacksonXmlProperty(localName = "Address")
	private final String address;
	@JacksonXmlProperty(localName = "Port")
	private final int port;
	
	
	
	
	public boolean valid() {
		return 
				 StringUtils.hasLength(this.address) && this.port>0;
	}



	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ResourceInfo(@JsonProperty("Category") String category, 
						@JsonProperty("ResourceID")String resourceId, 
						@JsonProperty("Address") String address, 
						@JsonProperty("Port") int port) {
		this.category = category;
		this.resourceId = resourceId;
		this.address = address;
		this.port = port;
	}

	


}
