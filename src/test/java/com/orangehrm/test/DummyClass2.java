package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;


public class DummyClass2 extends BaseClass{

	@Test
	public void dummyTest2() {
//		ExtentManager.startTest("Dummy test2");
		String title= getDriver().getTitle();
		assert title.equals("OrangeHRM"):"Test failed - Title is not matching";
		
		System.out.println("Test Passed - Title is matching	");
	}
}
