package com.test;

import java.io.*;

public class Test_WR {

	public static void MyWriter(File f) {
		try (FileWriter fw = new FileWriter(f)) {
			fw.write("�����̶��");
		} catch (IOException e) {
			System.out.println(e);
		} // fw.close()�� �ڵ�ȣ���ϸ鼭 fw�� ��ü�� �Ҹ��Ų��.
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

		// fr�� ��ü�� close()�ϱ� ���� �Ǵ� close()�� ��
		// ������ �ۼ��� ���� �ݵ�� �Ʒ� �ڵ带 ����ؼ� ����Ѵ�.
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
