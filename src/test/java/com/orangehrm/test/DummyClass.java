package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;


public class DummyClass extends BaseClass{

	@Test
	public void dummyTest() {
//		ExtentManager.startTest("Dummy test");
		String title= getDriver().getTitle();
//		ExtentManager.logStep("verifying the title");
		assert title.equals("OrangeHRM"):"Test failed - Title is not matching";
		
		System.out.println("Test Passed - Title is matching	");
	}
}
