package util;

import java.util.Map;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.FcmOptions;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;

import datavo.MetaData;
import datavo.msg.SingleMessageWrapper;

public class SingleMessageWrapperBuildes {

	public static SingleMessageWrapper createSingleMessageWrapper(MetaData meta, Message msg) {
		
		
		return SingleMessageWrapper.builder()
				.setData(meta)
				.setMsg(msg)
				.build();
	}
	
	
	public static Message createMessageBuilder(Map<String,String> data, 
													AndroidConfig andConfig, 
													ApnsConfig apns, 
													FcmOptions fcmOptions, 
													WebpushConfig web,
													String condition,
													Notification noti,
													String token,
													String topic) {
		// TODO Auto-generated method stub
		return Message.builder()
						.putAllData(data)
						.setAndroidConfig(andConfig)
						.setApnsConfig(apns)
						.setFcmOptions(fcmOptions)
						.setWebpushConfig(web)
						.setCondition(condition)
						.setNotification(noti)
						.setToken(token)
						.setTopic(topic)
						.build();
	}
	
	public static Message createDefaultSingleMessageBuilder(
														Notification noti,
														String token,
														String topic) {
						return createMessageBuilder(null,null,null,null,null,null,noti,Options.target.get(0),topic);
}
}
