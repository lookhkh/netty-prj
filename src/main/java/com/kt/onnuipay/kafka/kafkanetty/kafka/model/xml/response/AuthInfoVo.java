package com.kt.onnuipay.kafka.kafkanetty.kafka.model.xml.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.kt.onnuipay.kafka.kafkanetty.exception.XroshotRuntimeException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "MAS")
@ToString(callSuper = true)
@Getter
public class AuthInfoVo extends BaseXMLResponse {

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

	@Builder
	@JsonCreator(mode = Mode.PROPERTIES)
	public AuthInfoVo(@JsonProperty("method") String methodName, 
					  @JsonProperty("Result")String result, 
					  @JsonProperty("SessionID") String sessionId ) {
//					  @JsonProperty("SendLimitPerSecond") List<LimitedMsgPerSecond> list,
//					  @JsonProperty("ProductStatus") List<ProductStatus> productList, 
//					  @JsonProperty("SendLimitPerMonth") List<LimitedMsgPerMonth> monthList) {
		super(methodName,result);
		this.sessionId = sessionId;
//		this.list = list;
//		this.productList = productList;
//		this.monthList = monthList;
	}
	


	@Override
	public boolean valid() throws XroshotRuntimeException {
		return !sessionId.isBlank();
	}
	
	
	
}
