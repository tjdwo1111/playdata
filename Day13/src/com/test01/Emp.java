package com.test01;

public class Emp<T> {// ������� Ŭ����
	
	private T empno;//����� ��ȣ
	private T ename; //����� �̸�	
	public Emp(T empno, T ename) {
		super();
		this.empno = empno;
		this.ename = ename;
	}
	public T getEmpno() {
		return empno;
	}
	public void setEmpno(T empno) {
		this.empno = empno;
	}
	public T getEname() {
		return ename;
	}
	public void setEname(T ename) {
		this.ename = ename;
	}
	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + "]";
	}
	
	
	
}
