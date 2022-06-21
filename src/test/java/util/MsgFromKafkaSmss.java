package util;

import datavo.enums.MsgType;
import datavo.msg.MultiMessageWrapper;
import datavo.msg.SingleMessageWrapper;

public class MsgFromKafkaSmss {

	public static MultiMessageWrapper voForMultipleSMSWithSMS = MultiMessageWrapperBuilders
																			.createMultiMessageWrapper(
																					MetaDataBuilders
																						.buildSMSMultiMetaData(false, datavo.enums.MsgType.SMS), 
																					MultiMessageWrapperBuilders
																					.createDefaultMultiMessageBuilder(Notifications.noti,Options.targets)
																				);
	
	public static MultiMessageWrapper voForMultipleSMSWithLMS = MultiMessageWrapperBuilders
																			.createMultiMessageWrapper(
																					MetaDataBuilders
																						.buildSMSMultiMetaData(false, datavo.enums.MsgType.LMS), 
																					MultiMessageWrapperBuilders
																					.createDefaultMultiMessageBuilder(Notifications.noti,Options.targets)
																				);
	
	

	public static SingleMessageWrapper voForSingleSmsWithSMS = SingleMessageWrapperBuildes.createSingleMessageWrapper(
																	MetaDataBuilders.buildSMSSingleMetaData(false, MsgType.SMS), 
																	SingleMessageWrapperBuildes.createDefaultSingleMessageBuilder(Notifications.noti, Options.token, Options.topic));
	
	
	public static SingleMessageWrapper voForSingleSmsWithLMS = SingleMessageWrapperBuildes.createSingleMessageWrapper(
																		MetaDataBuilders.buildSMSSingleMetaData(false, MsgType.LMS), 
																		SingleMessageWrapperBuildes.createDefaultSingleMessageBuilder(Notifications.noti, Options.token, Options.topic));
}
