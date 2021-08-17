package com.test02;

//순서있는 목록을 List 객체로 확인 해보자
import java.util.*;
import com.mycom.*;

public class MyList {

	public static void View() {
		Vector v = new Vector(10, 2);
		System.out.println(v.capacity() + ":" + v.size());

		for (int i = 0; i <= 10; i++) {
			v.add(i);
		}
		v.add("abc");
		v.add(92.8);

		System.out.println(v);
		System.out.println(v.capacity() + ":" + v.size());
	}

	public static void View01() {

		Set<Address> s1 = new HashSet<>();
		Address a1 = new Address("1111", "1111", "1111");
		s1.add(new Address("111", "111", "111"));
		s1.add(new Address("222", "222", "222"));
		s1.add(new Address("333", "333", "333"));
		s1.add(new Address("444", "444", "444"));
		
		Address ar01=new Address("333", "333", "333");
		Address ar02=new Address("222", "333", "333");
		Address ar03=new Address("111", "333", "333");
		Address ar04=new Address("444", "333", "333");
		Address ar05=new Address("555", "333", "333");
		
		
		System.out.println(s1 +  "\n, sise=" +s1.size());
		
		System.out.println();
		for (Address ar : s1) {
			if (ar.getName().equals("222")) {
				ar.setName("정길동");
			}
			System.out.println(ar);
		}
		s1.remove(ar01);
		
		System.out.println(s1+"\n, size =" +s1.size());
		s1.clear();
		System.out.println(s1 +" \n, size =" +s1.size());
	}

	public static void View02() {
		Vector<Integer> v = new Vector<Integer>();

		for (int i = 0; i <= 10; i++) {
			v.add(i); // new Integer(i);
		}

		System.out.println(v);
		System.out.println(v.capacity() + ":" + v.size());
	}

	public static void View03() {
		// Address를 관리하는 ArrayList객체를 생성하자.

		// Vector<Integer> v=new Vector<Integer>(); 와 결과는 같다
		// 그러나 capacity() 는 사용안한다.

		ArrayList<Integer> v = new ArrayList<Integer>();

		for (int i = 0; i <= 10; i++) {
			v.add(i); // new Integer(i);
		}

		System.out.println(v);
		// System.out.println(v.capacity() + ":" + v.size());
	}

	public static void View04() {
		List<Address> list = new ArrayList<>();

		Address a1 = new Address("1111", "2222", "3333");
		list.add(a1);
		list.add(new Address("4444", "5555", "6666"));
		list.add(new Address("7777", "8888", "9999"));

		for (Address ar : list) {
			System.out.println(ar);
		}

		System.out.println();

		// 1111의 이름을 홍길동 변환하자.
		list.get(0).setName("홍길동");
		for (Address ar : list) {
			System.out.println(ar);
		}
		System.out.println("\n 4444의 주소를 부산으로 변경하자.");

		for (Address ar : list) {
			if (ar.getName().equals("4444")) {
				ar.setAddr("부산");
			}
			System.out.println(ar);
		}
	}

	public static void main(String[] args) {
		View01();
	}
}
