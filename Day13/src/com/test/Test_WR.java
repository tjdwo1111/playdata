package com.test;

import java.io.*;

public class Test_WR {

	public static void MyWriter(File f) {
		try (FileWriter fw = new FileWriter(f)) {
			fw.write("연습이라네");
		} catch (IOException e) {
			System.out.println(e);
		} // fw.close()를 자동호출하면서 fw의 객체를 소멸시킨다.
	}

	public static void MyReader(File f) {
		// try () ~ catch~ finally : try-with-resources statement
		try (FileReader fr = new FileReader(f)) {
			int res = 0;
			while ((res = fr.read()) != -1) {
				System.out.print((char) res);
			}

		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

	public static void MyReader02(File f) {
		// try ~ catch ~ finally

		// fr의 객체가 close()하기 직전 또는 close()할 때
		// 구문을 작성할 경우는 반드시 아래 코드를 명시해서 사용한다.
		FileReader fr = null;
		int res = 0;
		try {
			fr = new FileReader(f);
			while ((res = fr.read()) != -1) {
				System.out.print((char) res);
			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		File f = new File("text.dat");
		MyWriter(f);
		MyReader02(f);
	}
}
