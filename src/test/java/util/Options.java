package util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Options {

	public static String actionUrl = "www.action.url";
	public static LocalDateTime time = LocalDateTime.now();
	public static List<String> targets = Arrays.asList("123","345");
	public static List<String> target = Arrays.asList("123");

	public static String sender = "test1";
	public static String token = "token".hashCode()+"";
	public static String topic = "hello.kafka";

}
