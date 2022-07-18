package fcm;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class FcmTest {

    @Test
    public void t() {
        Message m1 = Message.builder().setNotification(Notification.builder().setTitle("hi").setBody("body").build()).setToken("toasdasdsa").build();
        Message m2 = Message.builder().setNotification(Notification.builder().setTitle("hi").setBody("body").build()).setToken("toasdasdsa").build();
        Message m3 = Message.builder().setNotification(Notification.builder().setTitle("hi").setBody("body").build()).setToken("toasdasdsa").build();
        Message m4 = Message.builder().setNotification(Notification.builder().setTitle("hi").setBody("body").build()).setToken("toasdasdsa").build();
        Message m5 = Message.builder().setNotification(Notification.builder().setTitle("hi").setBody("body").build()).setToken("toasdasdsa").build();

        List<Message> messages = Arrays.asList(m1,m2,m3,m4,m5);
        
        System.out.println(messages);
        
        FirebaseMessaging.getInstance().sendAsync(m1).addListener(null, null);;

    }
}
