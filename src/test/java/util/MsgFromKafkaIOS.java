package util;

import java.util.Arrays;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

import datavo.msg.MultiMessageWrapper;
import datavo.msg.SingleMessageWrapper;

public class MsgFromKafkaIOS {
	public static MultiMessageWrapper voForMultipleIOSPush = MultiMessageWrapperBuilders.createMultiMessageWrapper(MetaDataBuilders.buildAndroidMultiMetaData(false), MultiMessageWrapperBuilders.createDefaultMultiMessageBuilder(Notifications.noti, Options.targets));
	
	public static SingleMessageWrapper voForSingleIOSPush = SingleMessageWrapperBuildes.createSingleMessageWrapper(MetaDataBuilders.buildIOSSingleMetaData(false), SingleMessageWrapperBuildes.createDefaultSingleMessageBuilder(Notifications.noti, Options.token, Options.topic));
	
}
