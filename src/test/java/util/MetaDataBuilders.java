package util;

import java.time.LocalDateTime;

import datavo.MetaData;
import datavo.enums.KafkaKeyEnum;
import datavo.enums.MsgType;
import datavo.enums.TypeOfSending;

public class MetaDataBuilders {

	public static MetaData buildMetaData(KafkaKeyEnum key, TypeOfSending sending, MsgType type, boolean isScheduled) {
		// TODO Auto-generated method stub
		return 	MetaData.builder()	
				.setKey(key)
				.setKind(sending)
				.setMsgType(type)
				.setScheduled(isScheduled)
				.setSender(Options.sender)
				.setTimeOfDelievery(Options.time)
				.build();
	}
	
	public static MetaData buildAndroidSingleMetaData(boolean isScheduled) {
		// TODO Auto-generated method stub
		return buildMetaData(KafkaKeyEnum.ANDROID, TypeOfSending.SINGLE, MsgType.APP_PUSH, isScheduled);
	}
	
	public static MetaData buildAndroidMultiMetaData(boolean isScheduled) {
		// TODO Auto-generated method stub
		return buildMetaData(KafkaKeyEnum.ANDROID, TypeOfSending.MULTIPLE, MsgType.APP_PUSH, isScheduled);
	}
	
	public static MetaData buildIOSSingleMetaData(boolean isScheduled) {
		return buildMetaData(KafkaKeyEnum.IOS, TypeOfSending.SINGLE, MsgType.APP_PUSH, isScheduled);
	}
	
	public static MetaData buildIOSMultiMetaData(boolean isScheduled) {
		return buildMetaData(KafkaKeyEnum.IOS, TypeOfSending.MULTIPLE, MsgType.APP_PUSH, isScheduled);
	}
	
	public static MetaData buildSMSSingleMetaData(boolean isScheduled, MsgType type) {
		return buildMetaData(KafkaKeyEnum.SMS, TypeOfSending.SINGLE, type, isScheduled);

	}
	
	public static MetaData buildSMSMultiMetaData(boolean isScheduled, MsgType type) {
		return buildMetaData(KafkaKeyEnum.SMS, TypeOfSending.MULTIPLE, type, isScheduled);

	}


}
