package com.kafka.kafkanetty.client.test.manager;

public class Simple {

	public String name;
	public int age;
	public Simple(String name, int age) {
		this.name = name;
		this.age =age;
	}
	public Simple() {
	}
	@Override
	public String toString() {
		return "Simple [name=" + name + ", age=" + age + "]";
	}
	
	
	
	
}
