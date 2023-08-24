package pageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="/html/body/div[1]/header/div[1]/div/ul/li[3]/a")
	WebElement createAcctBtn;
	@FindBy(id="search")
	WebElement searchBox;
	@FindBy(xpath="//div[@class='products wrapper grid products-grid']")
	public WebElement itemTable;
	@FindBy(css="a.action.next")
	WebElement nextArrow;
	@FindBy(css="a.action.previous")
	WebElement prevArrow;
	@FindBy(xpath="//ul[@class='items pages-items']")
	WebElement pages;
	@FindBy(css="a.action.showcart")
	WebElement cart;
	@FindBy(css="a.action.viewcart")
	WebElement viewCart;
	@FindBy(css="span.counter.qty")
	WebElement counterQty;
	@FindBy(xpath="//div[@class='message-success success message']/div")
	WebElement message;
	@FindBy(xpath="a[text()='shopping cart']")
	WebElement linkToShoppingCart;
	@FindBy(id="toolbar-amount")
	public WebElement toolbar;
	
	public int numOfAllItem() {
		int num;
		List<WebElement> totalItem=toolbar.findElements(By.tagName("span"));
		if(totalItem.size()>1) {
			num=Integer.parseInt(totalItem.get(2).getText());
		}
		else {
			num=Integer.parseInt(totalItem.get(0).getText());
		}
		
		return num;
	}
	
	public WebElement getElement(int index) {
		String xpathExpression=String.format("//div[@class='products wrapper grid products-grid']//li[%d]", index);
		WebElement item=driver.findElement(By.xpath(xpathExpression));
		return item;
	}
	
	public boolean pageIsDisplayed() {
		List<WebElement> page= driver.findElements(By.xpath("//ur[@class='items pages-items']"));
		return page.isEmpty();
	}
	
	public void clickPrevArrow() {
		prevArrow.click();
	}
	
	public void goToPage(int next) {
		String xpath=String.format("//ul[@class='items pages-items']/li[%d]/a", next);
		WebElement page=driver.findElement(By.xpath(xpath));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
	    executor.executeScript("arguments[0].click();", page);
	}
	
	public int getcounterQty() {
		String qty=counterQty.getText();
		if (qty.isEmpty()) {
	        return 0;
	    }
	    return Integer.parseInt(qty);
	}
	
	public void clickCart(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(cart));
		cart.click();
	}
	
	public void clickViewCart(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(viewCart));
		viewCart.click();
	}
	
	public String getMessage(WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.visibilityOf(message));
			return message.getText();
		}catch(TimeoutException e) {
			return "";
		}
	}
	
	public void nextPage() {
		nextArrow.click();
	}
	
	public boolean tableIsDisplayed(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(itemTable));
		return pages.isDisplayed();
	}
	
	public void clickCreateAcctBtn(WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(createAcctBtn));
		createAcctBtn.click();
	}
	
	public float getPrice(WebElement e) {
		String price=e.findElement(By.xpath(".//span[@class='price-wrapper ']")).getAttribute("data-price-amount");
		return Float.parseFloat(price);
	}
	
	public String pickColor(WebElement e, Random random) {
		String pickedColor="none";
		try{
			WebElement colorbar=e.findElement(By.xpath(".//div[@class='swatch-attribute color']"));
			List<WebElement> colors=colorbar.findElements(By.xpath(".//div[@class='swatch-option color']"));
			WebElement randomColor=colors.get(random.nextInt(colors.size()));
			pickedColor=randomColor.getAttribute("option-label");
			randomColor.click();
			return pickedColor;
		}
		catch(NoSuchElementException ex) {
			return pickedColor;
		}
	}
	
	public String pickSize(WebElement e, Random random) {
		String pickedSize="none";
		try{
	        WebElement sizebar = e.findElement(By.xpath(".//div[@class='swatch-attribute size']"));
	        List<WebElement> sizes=sizebar.findElements(By.xpath(".//div[@class='swatch-option text']"));
			WebElement randomSize=sizes.get(random.nextInt(sizes.size()));
			pickedSize=randomSize.getAttribute("option-label");
			randomSize.click();
			return pickedSize;
		}
		catch(NoSuchElementException ex) {
			return pickedSize;
		}
	}
	public String getItemColor(WebElement color) {
		return color.getAttribute("option-label");
	}
	
	public String getItemSize(WebElement size) {
		return size.getAttribute("option-label");
	}
	
	public String getItemName(WebElement e) {
		return e.findElement(By.xpath(".//a[@class='product-item-link']")).getText();
	}
	
	public void addToCart(WebElement e, WebDriverWait wait) {
	    WebElement button = wait.until(ExpectedConditions.elementToBeClickable(e.findElement(By.xpath(".//button[@class='action tocart primary']"))));
	    JavascriptExecutor executor = (JavascriptExecutor) driver;
	    executor.executeScript("arguments[0].click();", button);
	}

	
	public void searchItem(String item) {
		searchBox.clear();
		searchBox.sendKeys(item);
		searchBox.sendKeys(Keys.ENTER);
	}
}
