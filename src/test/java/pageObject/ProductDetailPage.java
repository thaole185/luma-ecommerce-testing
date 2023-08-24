package pageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

	public WebDriver driver;
	public ProductDetailPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="a.action.showcart")
	WebElement cart;
	@FindBy(css="a.action.viewcart")
	WebElement viewCart;
	@FindBy(xpath="//div[@class='messages']")
	WebElement addToCartMessage;
	@FindBy(xpath="//span[@data-ui-id='page-title-wrapper']")
	WebElement productName;
	@FindBy(xpath="//div[@class='swatch-attribute size']/div")
	WebElement sizes;
	@FindBy(xpath="//div[@class='swatch-attribute color']/div")
	WebElement colors;
	@FindBy(id="qty")
	WebElement quantity;
	@FindBy(id="product-addtocart-button")
	WebElement addToCartButton;
	@FindBy(id="product-updatecart-button")
	WebElement updateButton;
	@FindBy(id="super_attribute[93]-error")
	WebElement colorWarning;
	@FindBy(id="super_attribute[143]-error")
	WebElement sizeWarning;
	@FindBy(id="qty-error")
	WebElement qtyWarning;
	@FindBy(xpath="//span[@class='counter-number']")
	WebElement counterQty;
	@FindBy(css="a.action.viewcart")
	WebElement viewCartLink;
	@FindBy(xpath="//span[@class='price-wrapper ']")
	WebElement price;
	
	public String getAddToCartMessage(WebDriverWait wait) {
		try {
			wait.until(ExpectedConditions.visibilityOf(addToCartMessage));
			return addToCartMessage.getText();
		}catch(TimeoutException e) {
			return "";
		}
	}
	
	public String getQuantity() {
		String qty=quantity.getAttribute("value");
		return qty;
	}
	
	public float getPrice() {
		String itemPrice=price.getAttribute("data-price-amount");
		return Float.parseFloat(itemPrice);
	}
	
	public String pickSize(WebDriverWait wait, Random random) {
		String pickedSize="none";
		try{
			wait.until(ExpectedConditions.elementToBeClickable(sizes));
			List<WebElement> allSizes=sizes.findElements(By.tagName("div"));
			WebElement randomSize=allSizes.get(random.nextInt(allSizes.size()));
			if(randomSize.getAttribute("aria-checked").equals("false")) {
				randomSize.click();
			}
			pickedSize=randomSize.getAttribute("option-label");
			return pickedSize;
		}
		catch(TimeoutException e) {
			return pickedSize;
		}
	}
	
	public String pickColor(WebDriverWait wait, Random random) {
		String pickedColor="none";
		try{
			wait.until(ExpectedConditions.elementToBeClickable(colors));
			List<WebElement> allColors=colors.findElements(By.tagName("div"));
			WebElement randomColor=allColors.get(random.nextInt(allColors.size()));
			if(randomColor.getAttribute("aria-checked").equals("false")) {
				randomColor.click();
			}
			pickedColor=randomColor.getAttribute("option-label");
			return pickedColor;
		}
		catch(TimeoutException e) {
			return pickedColor;
		}
	}
	
	public String getProductName() {
		return productName.getText();
	}
	
	public void chooseQty(String qty) {
		quantity.clear();
		quantity.sendKeys(qty);
	}
	
	public void addToCart(WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
		Actions action=new Actions(driver);
		action.moveToElement(addToCartButton).click().perform();
	}
	
	public void updateCart(WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(updateButton));
		Actions action=new Actions(driver);
		action.moveToElement(updateButton).click().perform();
	}
	
	public String colorWarning(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(colorWarning));
		return colorWarning.getText();
	}
	
	public String sizeWarning(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(sizeWarning));
		return sizeWarning.getText();
	}
	
	public String qtyWarning(WebDriverWait wait) {
		wait.until(ExpectedConditions.visibilityOf(qtyWarning));
		return qtyWarning.getText();
	}
	
	public int getcounterQty() {
		String qty=counterQty.getText();
		if (qty.isEmpty()) {
	        return 0;
	    }
	    
	    return Integer.parseInt(qty);

	}
	
	public void clickCart(WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(cart));
		cart.click();
	}
	
	public void clickViewCart(WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(viewCart));
		viewCart.click();
	}
}
