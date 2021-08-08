package com.test01;

public class My_T {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Emp e1 = new Emp(7092,"Scott");
		
		Emp e2 = new Emp(new Integer(7093),"9000");
		
		System.out.println(e1);
		System.out.println(e2);
		
		//갑자기 데이터 타입이 변경이 되거나 확장이 되었을때
		Emp e3 = new Emp("7904",9001);	
		System.out.println(e3);
		e3.setEname("abcde");
		System.out.println(e3);
		
		Emp e4=new Emp(new Integer("90"), new java.util.Date());
		System.out.println(e4);
	}
}
