package com.minis;

public class BaseService {
	private AServiceImpl as;
	
	public AService getAs() {
		return as;
	}
	public void setAs(AServiceImpl as) {
		this.as = as;
	}
	public BaseService() {
	}
	public void sayHi() {
		System.out.print("Base Service says hello");
		as.sayHi();
	}
}
