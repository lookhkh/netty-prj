package util;

import java.util.List;
import java.util.Map;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.FcmOptions;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;

import datavo.MetaData;
import datavo.msg.MultiMessageWrapper;

public class MultiMessageWrapperBuilders {

		public static MultiMessageWrapper createMultiMessageWrapper(MetaData meta, MulticastMessage msg) {
			
			return MultiMessageWrapper.builder()
					.setData(meta)
					.setMultiMsg(msg)
					.build();
	
	}
	
	
	
	public static MulticastMessage createMultiMessageBuilder(Map<String,String> data, 
															AndroidConfig andConfig, 
															ApnsConfig apns, 
															FcmOptions fcmOptions, 
															WebpushConfig web,
															String condition,
															Notification noti,
															String token,
															List<String> tokens) {
	
						return MulticastMessage.builder()
						.putAllData(data)
						.setAndroidConfig(andConfig)
						.setApnsConfig(apns)
						.setFcmOptions(fcmOptions)
						.setWebpushConfig(web)
						.setNotification(noti)
						.addToken(token)
						.addAllTokens(tokens)
						.build();
	}
	
	public static MulticastMessage createDefaultMultiMessageBuilder(Notification noti, List<String> tokens) {
	
		return createMultiMessageBuilder(null, null, null, null, null, null, noti, null, tokens);
}
	
}
