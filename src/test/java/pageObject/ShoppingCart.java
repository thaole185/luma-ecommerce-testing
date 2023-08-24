package pageObject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCart {

	WebDriver driver;
	public ShoppingCart(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//table[@id='shopping-cart-table']")
	WebElement table;
	@FindBy(css="button.action.update")
	WebElement updateBtn;
	
	public List<WebElement> getItemsInCart(WebDriverWait wait){
		wait.until(ExpectedConditions.visibilityOf(table));
		return table.findElements(By.tagName("tbody"));
	}
	
	public Item getItemInfo(int itemIndex, WebDriverWait wait) {
		String itemXpath=String.format("//table[@id='shopping-cart-table']/tbody[%d]", itemIndex);
		WebElement row=driver.findElement(By.xpath(itemXpath));
		String size="none";
		String color="none";
		try {
			WebElement textDisplayed =wait.until(ExpectedConditions.visibilityOf(row.findElement(By.xpath(".//dl[@class='item-options']"))));
			String[] texts= textDisplayed.getText().split("\n");
			if(texts.length<4) {
				String info=texts[0];
				if(info.equals("size")) {
					size=texts[1];
				}
				else {
					color=texts[1];
				}
			}
			else {
				size=texts[1];
				color=texts[3];
			}
		}
		catch(NoSuchElementException noElement) {
		}
		String name=row.findElement(By.xpath(".//strong[@class='product-item-name']")).getText();
		String qty=row.findElement(By.xpath(".//input[@title='Qty']")).getAttribute("value");
		String price=row.findElement(By.xpath(".//td[@class='col price']")).getText();
		String totalPrice=row.findElement(By.xpath(".//td[@class='col subtotal']")).getText();
		
		Item item=new Item();
		item.setColor(color);
		item.setSize(size);
		item.setName(name);
		item.setQuantity(Integer.parseInt(qty));
		item.setPrice(Float.parseFloat(price.replace("$", "")));
		item.setTotalPrice();
		
		return item;
	}
	
	public ArrayList<Item> getAllItemsInfoInCart(WebDriverWait wait) {
		List<WebElement> cartList=getItemsInCart(wait);
		ArrayList<Item> itemsInCart = new ArrayList<Item>();
		for(int index=1; index<=cartList.size();index++) {
			Item item=getItemInfo(index, wait);
			itemsInCart.add(item);
		}
		return itemsInCart;
	
	}
	
	public void changeQty(WebElement e, String qty, WebDriverWait wait) {
		WebElement row= wait.until(ExpectedConditions.visibilityOf(e));
		WebElement qtyBox=row.findElement(By.xpath(".//input[@title='Qty']"));
		qtyBox.clear();
		qtyBox.sendKeys(qty);
	}
	
	public String getMessage(WebElement e, WebDriverWait wait) {
		String message=wait.until(ExpectedConditions.visibilityOf( e.findElement(By.xpath(".//div[@class='mage-error']")))).getText();
		return message;
	}
	
	public void editItem(WebElement e) {
		WebElement row=e.findElement(By.cssSelector("tr.item-actions"));
		WebElement editPen=row.findElement(By.cssSelector("a.action.action-edit"));
		editPen.click();
	}
	
	public void deleteItem(WebElement e) {
		WebElement row=e.findElement(By.cssSelector("tr.item-actions"));
		WebElement deleteSymbol=row.findElement(By.cssSelector("a.action.action-delete"));
		deleteSymbol.click();
	}
	
	public void clickUpdateBtn(WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(updateBtn));
		updateBtn.click();
	}
}
