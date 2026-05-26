package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;
	
	//define locators using By class
	
	private By userNameField= By.name("username"); 
	private By passwordField= By.cssSelector("input[type='password']");
	private By loginButton= By.xpath("//button[text() =' Login ']");
	private By errorMessage= By.xpath("//p[text()='Invalid credentials']");
	
	/*
	 * public LoginPage(WebDriver driver) throws IOException { this.actionDriver=new
	 * ActionDriver(driver); }
	 */
	
	public LoginPage(WebDriver driver) {
		this.actionDriver= BaseClass.getActionDriver();
	}
	
	//Method to perform login
	
	public void login(String userName, String password) {
		actionDriver.enterText(userNameField, userName);
		actionDriver.enterText(passwordField, password);
		actionDriver.waitForElementToBeClickable(loginButton);
		actionDriver.click(loginButton);
	}
	
	//Method to check the error message displayed
	public void isErrorMessageDisplyed() {
		actionDriver.isDisplayed(errorMessage);
	}
	
	//Method to get the text from error message
	public void getErroMessageText() {
		actionDriver.getText(errorMessage);
	}
	
	//Verify if error message is correct or not
	public boolean verifyErrorMessage(String expectedError) {
		return actionDriver.compareText(errorMessage, expectedError);
	}
}
