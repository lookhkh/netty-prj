package util;

import com.google.firebase.messaging.Notification;

public class Notifications {

	public static Notification noti = Notification.builder().setBody("test Body").setTitle("test Header").setImage("www.test.temp.com").build();
}
