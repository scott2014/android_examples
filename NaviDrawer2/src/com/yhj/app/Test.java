package com.yhj.app;

public class Test {
	
	public static void main(String[] args) {
		String s = "a#";
		String[] ss = s.split("#");
		for (String s1 : ss) {
			System.out.println(s1);
		}
	}
}
