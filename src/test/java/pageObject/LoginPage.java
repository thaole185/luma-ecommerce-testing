package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="email")
	WebElement email;
	@FindBy(id="pass")
	WebElement pass;
	@FindBy(id="send2")
	WebElement signInbtn;
	@FindBy(xpath="//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[2]/div[2]/div/div/a")
	WebElement createAcctBtn;
	By emailErr=By.id("email-error");
	By passErr=By.id("pass-error");
	By incorrectInfo=By.xpath("//*[@id=\\\"maincontent\\\"]/div[2]/div[2]/div/div/div");
	
	public void enterEmail(String emailInfo) {
		email.sendKeys(emailInfo);
	}
	
	public void enterPasword(String password) {
		pass.sendKeys(password);
	}
	
	public void clickSignIn() {
		signInbtn.click();
	}
	
	public void clickCreateAcctbtn() {
		createAcctBtn.click();
	}
	
	public boolean emailErrDisplayed(WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(emailErr));
			return true;
		}
		catch(TimeoutException e) {
			return false;
		}
	}
	
	public boolean passErrDisplayed(WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(passErr));
			return true;
		}
		catch(TimeoutException e) {
			return false;
		}
	}
	
	public boolean invalidAcctErrDisplayed(WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(incorrectInfo));
			return true;
		}
		catch(TimeoutException e) {
			return false;
		}
	}
	
}
