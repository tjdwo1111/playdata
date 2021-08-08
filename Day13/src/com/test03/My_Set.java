package com.test03;

import java.util.*;
import com.mycom.*;

public class My_Set {
	public static void View() {

		Set<String> my_set = new HashSet<>();
		my_set.add("abc");
		my_set.add("abc1");			//중복 데이터는 허용 안해서 덮어써진다.
		my_set.add("abc2");			
		my_set.add("1111");
		my_set.add("2222");
		System.out.println(my_set);
		
		for(String r : my_set) {
			System.out.println(r);
		}
	}

	public static void main(String[] args) {
		View();
	}
}
