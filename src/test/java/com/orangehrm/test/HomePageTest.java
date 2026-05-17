package com.orangehrm.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() throws IOException {
		this.loginPage= new LoginPage(getDriver());
		this.homePage= new HomePage(getDriver());
	}
	
	@Test
	public void verifyOrangeHRMLogo() {
//		ExtentManager.startTest("Home page logo");
		ExtentManager.logStep("Navigating to the login page entering user name and password");
		
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(),"Test failed: OrangeHRM Logo is not visible");
		ExtentManager.logStep("Validation successfull");
	}
	
	

}
