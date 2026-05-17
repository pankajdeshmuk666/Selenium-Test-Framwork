package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

	private ActionDriver actionDriver;

	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

	/*
	 * @FindBy(xpath = "//a[text()='Logout']") private WebElement logoutButton;
	 */

	// Initialize the ActionDriver object by passing WebDriver instance
	/*
	 * public HomePage(WebDriver driver) throws IOException { this.actionDriver= new
	 * ActionDriver(driver); }
	 */

	public HomePage(WebDriver driver) {
		/*
		 * This initializes all @FindBy annotated WebElements in your Page Object class.
		 * It’s a standard Selenium Page Object Model (POM) practice. It keeps your page
		 * classes clean and avoids repetitive driver.findElement() calls. PageFactory
		 * is good for element initialization. It’s lightweight, standard, and
		 * interview-friendly.
		 */
//		PageFactory.initElements(driver, this); 
		this.actionDriver = BaseClass.getActionDriver();
	}

	// Method to verify admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	// Method to verify orangeHRM log is visible
	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}

	// Method to perform logout action
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}
}
