package com.kafka.kafkanetty.client.test.manager;

import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YesOrNOContainer {

	private final List<YesOrNo> response;
	
	
	
	public static  YesOrNOContainer getListStob(){
		return new YesOrNOContainer(Arrays.asList(
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),	new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f")),
				new YesOrNo(true, "a",null),
				new YesOrNo(true, "b",null),
				new YesOrNo(true, "c",null),
				new YesOrNo(false, "d",new RuntimeException("d")),
				new YesOrNo(false, "f",new IllegalAccessException("f"))));
	}
}
