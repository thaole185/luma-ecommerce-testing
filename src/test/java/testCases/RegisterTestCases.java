package testCases;

import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import dataProviders.RegisterDataProviders;
import pageObject.HomePage;
import pageObject.MyAccountPage;
import pageObject.RegisterPage;
import testSetup.Setup;
import utilities.DataReaders;

public class RegisterTestCases extends Setup {
	
	HomePage hp;
	RegisterPage rg;
	Actions actions;
	WebDriverWait wait;
	String myAcctPageTitle="My Account";
	
	@Test(priority=1,dataProvider="ValidInfos", dataProviderClass=RegisterDataProviders.class)
	public void validAccountTest(String fname, String lname, String email, String pass, String conpass) {
		
		registerSetup(fname, lname, email, pass, conpass); 
		
		AssertJUnit.assertEquals("User with Valid Account did not get Registered",driver.getTitle(), myAcctPageTitle);
		
		MyAccountPage acctPage=new MyAccountPage(driver);
		acctPage.clickDropDownMenu(wait, actions);
		acctPage.clickSignOut(wait, actions);
		
		hp.clickCreateAcctBtn(wait);
	}
	
	
	@Test(priority=2,dataProvider="InvalidConPassInfo", dataProviderClass=RegisterDataProviders.class)
	public void invalidConpassTest(String fname, String lname, String email, String pass, String conpass,ITestResult result) {
		
		registerSetup(fname, lname, email, pass, conpass); 
		String warning="Please enter the same value again.";
		
		Assert.assertEquals("Invalid Confirm Password Message was not Displayed", rg.conpwdErrText(wait), warning);
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("Warning: User with Invalid Confirm Password get registered",true);
		}
	}
	
	@Test(priority=3, dataProvider="TrailingLeadingPassInfo", dataProviderClass=RegisterDataProviders.class)
	public void TrailingLeadingTest(String fname, String lname, String email, String pass, String conpass) {
		
		String warning="The password can't begin or end with a space. Verify the password and try again.";
		
		registerSetup(fname, lname, email, pass, conpass); 
		
		Assert.assertEquals("Warning: Trailing Leading Password Error Message did not displayed", rg.pwdTrailingErrText(wait),warning);
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("Warning: User with Trailing Leading Password get registered",true);
		}
	}
	
	@Test(priority=4, dataProvider="PassMinimumLengthInfo", dataProviderClass=RegisterDataProviders.class)
	public void passMinimumLengthTest(String fname, String lname, String email, String pass, String conpass) {
		
		String warning="Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.";
		
		registerSetup(fname, lname, email, pass, conpass); 
		
		Assert.assertEquals("Warning: Minimum Length Password Error Message did not displayed", rg.passErrText(wait),warning);
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("Warning: User with Password of less then 8 symbols get registered",true);
		}
	}
	
	@Test(priority=5, dataProvider="MinimumPassCharacterInfo", dataProviderClass=RegisterDataProviders.class)
	public void passMinimumCharacterTypeTest(String fname, String lname, String email, String pass, String conpass) {
		
		String warning="Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.";
		
		registerSetup(fname, lname, email, pass, conpass); 
		
		Assert.assertEquals("Warning: Minimum type of Character in Password Error Message did not displayed", rg.passErrText(wait),warning);
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("Warning: User with Password of less then 3 type of characters get registered",true);
		}
	}
	
	@Test(priority=6)
	public void subcribeDefaultTest(){
		wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		actions=new Actions(driver);
		
		hp=new HomePage(driver);
		hp.clickCreateAcctBtn(wait);
		
		rg=new RegisterPage(driver);
		
		Assert.assertFalse("User subscriber to the newsLetter by Default",rg.subscribeIsSelected());
	}
	
	
	@Test(priority=7, dataProvider="NewsLetterSignUpInfo", dataProviderClass=RegisterDataProviders.class)
	public void newsLetterSubscribeTest(String fname, String lname, String email, String pass, String conpass, String subscribe) {
		String subScribeText="You are subscribed to \"General Subscription\".";
		String unsubScribeText="You aren't subscribed to our newsletter.";

		wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		actions=new Actions(driver);
		
		hp=new HomePage(driver);
		hp.clickCreateAcctBtn(wait);
		
		rg=new RegisterPage(driver);
		rg.enterFName(fname);
		rg.enterLName(lname);
		rg.enterEmail(email);
		rg.enterPwd(pass);
		rg.enterConPwd(conpass);
		
		if(subscribe.equals("TRUE")) {
			rg.clickSubscribe();
		}
		
		rg.clickCreateAcct(); 
		
		Assert.assertEquals(driver.getTitle(), myAcctPageTitle);
		
		MyAccountPage acctPage=new MyAccountPage(driver);
		String displayedText=acctPage.getNewsLetterText();
		acctPage.clickDropDownMenu(wait, actions);
		acctPage.clickSignOut(wait, actions);
		
		if(subscribe.equals("TRUE")) {
			Assert.assertEquals(displayedText, subScribeText);
		}
		else{
			Assert.assertEquals(displayedText, unsubScribeText);
		}
	}
	
	@Test(priority=8, dataProvider="InvalidFormatEmailInfo", dataProviderClass=RegisterDataProviders.class)
	public void invalidFormatEmailTest(String fname, String lname, String email, String pass, String conpass) {
		
		String warningText="Please enter a valid email address (Ex: johndoe@domain.com).";
		
		registerSetup(fname, lname, email, pass, conpass); 
		
		Assert.assertEquals("Invalid Warning did not Displayed", warningText, rg.emailErrText(wait));
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("User with Invalid Email got Registered",true);
		}
	}
	
	@Test(dataProvider="ValidInfos", dataProviderClass=RegisterDataProviders.class)
	public void registerWithExistedAccountTest(String fname, String lname, String email, String pass, String conpass) {
		
		String warningText="There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.";
		
		registerSetup(fname, lname, email, pass, conpass); 
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("User with Existed Account got Registered",true);
		}
		Assert.assertEquals("Invalid Warning did not Displayed", warningText, rg.pwdTrailingErrText(wait));
	}
	
	@Test(priority=9, dataProvider="TrailingLeadingNameInfo", dataProviderClass=RegisterDataProviders.class)
	public void TrailingLeadingNameTest(String fname, String lname, String email, String pass, String conpass) {
		
		registerSetup(fname, lname, email, pass, conpass);
		
		Assert.assertEquals("User with valid Info did not get Registered",driver.getTitle(), myAcctPageTitle);
		
		MyAccountPage acctPage=new MyAccountPage(driver);
		String displayedName=acctPage.getNameText();
		String name=fname.trim()+" "+lname.trim();
		acctPage.clickDropDownMenu(wait, actions);
		acctPage.clickSignOut(wait, actions);
		
		Assert.assertEquals("User's Name did not get Trimmed", name, displayedName);
	}
	
	@Test(priority=10, dataProvider="EmptyFieldInfo",dataProviderClass=RegisterDataProviders.class)
	public void emptyFields(String fname, String lname, String email, String pass, String conpass) {
	
		String warning="";
		String[] inputs= {"fname", "lname", "email", "pass", "conpass"};
		for(String input: inputs) {
			switch(input) {
				case "fname":
					registerSetup("", lname, email, pass, conpass);
					warning=rg.fnameErrText(wait);
					break;
				case "lname":
					registerSetup(fname, "", email, pass, conpass);
					warning=rg.lnameErrText(wait);
					break;
				case "email":
					registerSetup(fname, lname, "", pass, conpass);
					warning=rg.emailErrText(wait);
					break;
				case "pass":
					registerSetup(fname, lname, email, "", conpass);
					warning=rg.passErrText(wait);
					break;
				case "conpass":
					registerSetup(fname, lname, email, pass, "");
					warning=rg.conpwdErrText(wait);
					break;
			}
		}
		
		if(driver.getTitle().equals(myAcctPageTitle)) {
			MyAccountPage acctPage=new MyAccountPage(driver);
			acctPage.clickDropDownMenu(wait, actions);
			acctPage.clickSignOut(wait, actions);
			Assert.assertFalse("Warning: User with an empty field get registered",true);
		}
		Assert.assertEquals(warning, "This is a required field.");
	}


	public void registerSetup(String fname, String lname, String email, String pass, String conpass) {
		
		wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		actions=new Actions(driver);
		
		hp=new HomePage(driver);
		hp.clickCreateAcctBtn(wait);
		
		rg=new RegisterPage(driver);
		rg.enterFName(fname);
		rg.enterLName(lname);
		rg.enterEmail(email);
		rg.enterPwd(pass);
		rg.enterConPwd(conpass);
		rg.clickCreateAcct();
	}
	
	
}
