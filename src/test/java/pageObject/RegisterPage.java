package pageObject;



import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class RegisterPage {
	
	WebDriver driver;
	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="firstname")
	WebElement fname;
	@FindBy(id="lastname")
	WebElement lname;
	@FindBy(name="email")
	WebElement email;
	@FindBy(id="password")
	WebElement pwd;
	@FindBy(name="password_confirmation")
	WebElement conpwd;
	@FindBy(name="is_subscribed")
	WebElement subScribedBox;
	@FindBy(xpath="//*[@id=\"form-validate\"]/div/div[1]/button")
	WebElement submitButton;
	@FindBy(id="firstname-error")
	WebElement fnameErr;
	@FindBy(id="lastname-error")
	WebElement lnameErr;
	@FindBy(id="email_address-error")
	WebElement emailErr;
	@FindBy(id="password-error")
	WebElement passErr;
	@FindBy(id="password-confirmation-error")
	WebElement conpwdErr;
	@FindBy(xpath="//*[@id=\"maincontent\"]/div[2]/div[2]/div/div/div")
	WebElement pwdTrailingErr;
	
	public void enterFName(String firstname) {
		fname.sendKeys(firstname);
	}
	
	public void enterLName(String lastname) {
		lname.sendKeys(lastname);
	}
	
	public void enterEmail(String userEmail) {
		email.sendKeys(userEmail);
	}
	
	public void enterPwd(String pass) {
		pwd.sendKeys(pass);
	}
	
	public void enterConPwd(String conpass) {
		conpwd.sendKeys(conpass);
	}
	
	public void clickCreateAcct() {
		submitButton.click();
	}
	
	public void clickSubscribe() {
		subScribedBox.click();
	}
	
	public boolean subscribeIsSelected() {
		return subScribedBox.isSelected();
	}
	
	public String fnameErrText(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(fnameErr));
		return fnameErr.getText();
	}
	
	public String lnameErrText(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(lnameErr));
		return lnameErr.getText();
	}
	
	public String emailErrText(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(emailErr));
		return emailErr.getText();
	}
	
	public String passErrText(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(passErr));
		return passErr.getText();
	}
	
	public String conpwdErrText(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(conpwdErr));
		return conpwdErr.getText();
	}
	
	public String pwdTrailingErrText(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(pwdTrailingErr));
		return pwdTrailingErr.getText();
	}
	
}
