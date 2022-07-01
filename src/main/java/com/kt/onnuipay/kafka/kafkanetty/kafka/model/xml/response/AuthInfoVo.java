package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
@ToString
@Builder
public class AuthInfoVo {

	@JacksonXmlProperty(isAttribute = true, localName = "method")
	private final String methodName;
	@JacksonXmlProperty(localName = "Result")
	private final String result;
	@JacksonXmlProperty(localName = "SessionID")
	private final String sessionId;
	
//	@JacksonXmlProperty(localName = "SendLimitPerSecond")
//    @JacksonXmlElementWrapper(useWrapping = false)
//	private final List<LimitedMsgPerSecond> list;
//
//	@JacksonXmlProperty(localName = "ProductStatus")
//    @JacksonXmlElementWrapper(useWrapping = false)
//	private final List<ProductStatus> productList;
//	
//	@JacksonXmlProperty(localName = "SendLimitPerMonth")
//    @JacksonXmlElementWrapper(useWrapping = false)
//	private final List<LimitedMsgPerMonth> monthList;

	@JsonCreator(mode = Mode.PROPERTIES)
	public AuthInfoVo(@JsonProperty("method") String methodName, 
					  @JsonProperty("Result")String result, 
					  @JsonProperty("SessionID") String sessionId ) {
//					  @JsonProperty("SendLimitPerSecond") List<LimitedMsgPerSecond> list,
//					  @JsonProperty("ProductStatus") List<ProductStatus> productList, 
//					  @JsonProperty("SendLimitPerMonth") List<LimitedMsgPerMonth> monthList) {
		this.methodName = methodName;
		this.result = result;
		this.sessionId = sessionId;
//		this.list = list;
//		this.productList = productList;
//		this.monthList = monthList;
	}
	
	



	
	
	
}
