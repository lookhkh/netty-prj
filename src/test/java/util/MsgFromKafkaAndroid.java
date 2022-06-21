package util;

import datavo.msg.MultiMessageWrapper;
import datavo.msg.SingleMessageWrapper;

public class MsgFromKafkaAndroid {

	public static MultiMessageWrapper voForMultiplePush = MultiMessageWrapperBuilders.createMultiMessageWrapper(MetaDataBuilders.buildAndroidMultiMetaData(false), MultiMessageWrapperBuilders.createDefaultMultiMessageBuilder(Notifications.noti, Options.targets));
	
	public static SingleMessageWrapper voForSinglePushWithValidDataBody = SingleMessageWrapperBuildes.createSingleMessageWrapper(MetaDataBuilders.buildAndroidSingleMetaData(false), SingleMessageWrapperBuildes.createDefaultSingleMessageBuilder(Notifications.noti, Options.token, Options.topic));
}
