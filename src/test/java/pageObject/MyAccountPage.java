package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage {

	public WebDriver driver;
	public MyAccountPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@id=\"maincontent\"]/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/p")
	WebElement nameText;
	@FindBy(xpath="//*[@id=\"maincontent\"]/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/p")
	WebElement newsLetter; 
	@FindBy(xpath="/html/body/div[1]/header/div[1]/div/ul/li[2]/span/button")
	WebElement menuDropDown;
	@FindBy(xpath="/html/body/div[1]/header/div[1]/div/ul/li[2]/div/ul/li[3]/a")
	WebElement signOut;
	
	public String getNameText() {
		String[] texts=nameText.getText().split("\n");
		return texts[0];
	}
	
	public String getNewsLetterText() {
		String text=newsLetter.getText();
		return text;
	}
	
	public void clickDropDownMenu(WebDriverWait wait, Actions actions) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(menuDropDown));
			actions.moveToElement(menuDropDown).click().perform();

		}
		catch(TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void clickSignOut(WebDriverWait wait, Actions actions) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(signOut));
			actions.moveToElement(signOut).click().perform();
			
		}
		catch(TimeoutException e) {
			e.printStackTrace();
		}
	}
}
