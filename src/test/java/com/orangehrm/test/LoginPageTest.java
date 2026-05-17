package com.orangehrm.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;

import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass{

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() throws IOException {
		loginPage= new LoginPage(getDriver());
		homePage= new HomePage(getDriver());
	}
	
	@Test
	public void verifyValidLoginTest() {
//		ExtentManager.startTest("verify valid login test");
		ExtentManager.logStep("Navigating to the login page entering user name and password");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("Verifying admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successful login");
		ExtentManager.logStep("Validation successfull");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticWait(2);
	}
	
	@Test
	public void invalidLoginTest() {
		ExtentManager.startTest("Validating invalid test");
		ExtentManager.logStep("Navigating to the login page entering user name and password");
		loginPage.login("admin", "1234");
		String expectedErrorMessage= "Invalid credentials";
		
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test failed: Invalid error message");
		ExtentManager.logStep("Validation successfull");
	}
}
