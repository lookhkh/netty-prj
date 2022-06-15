package util;

import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;

public class UserInfos {

	public static UserInfoOnPush userInfoOnPushWithYes = new UserInfoOnPush(true);
	
	public static UserInfoOnPush userInfoOnPushWithNo = new UserInfoOnPush(false);

}
