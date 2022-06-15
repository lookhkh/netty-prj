package util;

import java.util.Arrays;
import java.util.List;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;

public class DataBodys {

	public static String validHeader = "Test Header";
	public static String invalidHeaderWithLengthOver2000 = validHeader.repeat(55);
	
	public static String validBody = "Test Body";
	public static String invalidBodyWithLengthOver2000 = validBody.repeat(500);
	
	public static List<DataBody> bodyOfWithValidHeaderAndBody = Arrays.asList(DataBody.builder()
			.title(validHeader)
			.body(validBody)
			.build());
	
	public static List<DataBody> bodyOfSmsWithValidHeaderAndInvalidBody = Arrays.asList(DataBody.builder()
			.title(validHeader)
			.body(invalidBodyWithLengthOver2000)
			.build());
	
	public static  List<DataBody> bodyOfInvalidHeaderAndValidBody = Arrays.asList(DataBody.builder()
			.title(invalidHeaderWithLengthOver2000)
			.body(validBody)
			.build());
	
	public static List<DataBody> bodyOfInvalidHeaderAndInValidBody = Arrays.asList(DataBody.builder()
			.title(invalidHeaderWithLengthOver2000)
			.body(invalidBodyWithLengthOver2000)
			.build());
}
