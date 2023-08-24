package testCases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import dataProviders.RegisterDataProviders;
import junit.framework.Assert;
import pageObject.HomePage;
import pageObject.Item;
import pageObject.ProductDetailPage;
import pageObject.ShoppingCart;
import testSetup.Setup;

public class AddToCartTest extends Setup {
	WebDriverWait wait;
	ArrayList<Item> addedItems;
	HomePage homePage;
	ProductDetailPage productPage;
	int numOfItemsPerPage=12;
	int numOfItemsToAdd=5;
	Actions action;
	Random random;
	String searchKeyWords="hoodie";
	int currentPage;
	@Test(priority=1)
	public void testAddToCartFromHomePage(){
		addToCartSetUp();
		int count=0;
		int totalItem=homePage.numOfAllItem();
		boolean pageIsDisplayed=homePage.pageIsDisplayed();
			while(count<numOfItemsToAdd) {
				int itemIndex=pageIsDisplayed?toNextPage(totalItem):random.nextInt(totalItem)+1;
				WebElement e=homePage.getElement(itemIndex);
				Item item=addToCartFromHomePage(e);
				addedItems=Item.addItem(item, addedItems);
				count++;
			}
		homePage.clickCart(wait);
		homePage.clickViewCart(wait);
		ShoppingCart shoppingCart=new ShoppingCart(driver);
		ArrayList<Item> itemsInCartInfo=shoppingCart.getAllItemsInfoInCart(wait);
		boolean itemInCartCheck=itemsInCartInfo.equals(addedItems);
	}
	
	@Test(priority=2)
	public void testAddToCartFromPoductPage() {
		addToCartSetUp();
		int count=0;
		int totalItem=homePage.numOfAllItem();
		boolean pageIsDisplayed=homePage.pageIsDisplayed();
		while(count<numOfItemsToAdd) {
			int itemIndex=pageIsDisplayed?toNextPage(totalItem):random.nextInt(totalItem)+1;
			WebElement e=homePage.getElement(itemIndex);
			e.click();
			Item item=addToCartFromProductPage();
			addedItems=Item.addItem(item, addedItems);
			driver.navigate().back();
			count++;
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		homePage.clickCart(wait);
		homePage.clickViewCart(wait);
		ShoppingCart shoppingCart=new ShoppingCart(driver);
		ArrayList<Item> itemsInCartInfo=shoppingCart.getAllItemsInfoInCart(wait);
		boolean itemInCartCheck=itemsInCartInfo.equals(addedItems);
	}
	
	@Test(priority=3)
	public void testColorNotSelectedFromHomePage() {
		selectOptionFromHomePage("color");
	}
	
	@Test(priority=4)
	public void testSizeNotSelectedFromHomePage() {
		selectOptionFromHomePage("size");
	}

	@Test(priority=5)
	public void testNoOptionSelectedFromHomePage() {
		selectOptionFromHomePage("");
	}	
	
	@Test(priority=6)
	public void testColorNotSelectedFromProductPage() {
		String displayedText=selectOptionFromProductPage("color")[0];
		Assert.assertEquals(displayedText, "This is a required field.");
	}
	
	@Test(priority=7)
	public void testSizeNotSelectedFromProductPage() {
		String displayedText=selectOptionFromProductPage("size")[0];
		Assert.assertEquals(displayedText, "This is a required field.");
	}

	@Test(priority=8)
	public void testNoOptionSelectedFromProductPage() {
		String[] displayedText=selectOptionFromProductPage("noColorNSize");
		Assert.assertEquals(displayedText[0], "This is a required field.");
		Assert.assertEquals(displayedText[1], "This is a required field.");
	}
	
	@Test(priority=9, dataProvider="ItemQuantity", dataProviderClass=RegisterDataProviders.class)
	public void testQuantityFromProductPage(String quatity) {
		selectOptionFromProductPage(quatity);
		String displayedText=selectOptionFromProductPage("noColorNSize")[0];
		Assert.assertEquals(displayedText, "Please enter a quantity greater than 0.");
	}	
	
	@Test(priority=10)
	public void testDefaultQuantity() {
		addToCartSetUp();
		int count=0;
		int totalItem=homePage.numOfAllItem();
		boolean pageIsDisplayed=homePage.pageIsDisplayed();
		while(count<numOfItemsToAdd) {
			int itemIndex=pageIsDisplayed?toNextPage(totalItem):random.nextInt(totalItem)+1;
			WebElement e=homePage.getElement(itemIndex);
			e.click();
			Assert.assertEquals(productPage.getQuantity(), "1");
			driver.navigate().back();
			count++;
			}
	}	
	
	public void addToCartSetUp() {
		wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		action=new Actions(driver);
		random=new Random();
		homePage=new HomePage(driver);
		productPage=new ProductDetailPage(driver);
		addedItems=new ArrayList<Item>();
		currentPage=1;
		homePage.searchItem(searchKeyWords);
		wait.until(ExpectedConditions.visibilityOf(homePage.itemTable));
	}
	
	public Item addToCartFromHomePage(WebElement e) {
		action.moveToElement(e).build().perform();
		String itemName=homePage.getItemName(e);
		Item item=new Item();
		
		String color=homePage.pickColor(e, random);
		String size=homePage.pickSize(e, random);
		float price=homePage.getPrice(e);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		homePage.addToCart(e, wait);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		item.setColor(color);
		item.setName(itemName);
		item.setSize(size);
		item.setPrice(price);
		item.setTotalPrice();
		return item;
		
	}
	
	public Item addToCartFromProductPage() {
		String itemName=productPage.getProductName();
		Item item=new Item();
		String size=productPage.pickSize(wait, random);
		String color=productPage.pickColor(wait, random);
		float price=productPage.getPrice();
		
		productPage.addToCart(wait);
	
		item.setColor(color);
		item.setName(itemName);
		item.setSize(size);
		item.setPrice(price);
		item.setTotalPrice();
		return item;
	}
	
	public int toNextPage(int totalItem) {
		int next=random.nextInt(totalItem)+1;
		int pickedPage=(int) Math.ceil((double)next/(double)numOfItemsPerPage);
		if(currentPage!=pickedPage) {
			homePage.goToPage(currentPage==1?pickedPage:pickedPage+1);
			currentPage=pickedPage;
		}
		int remainder=next%numOfItemsPerPage;
		int itemInPage=remainder>0 ? remainder:numOfItemsPerPage;
		return itemInPage;
	}
	
	public void selectOptionFromHomePage(String option) {
		addToCartSetUp();
		int count=0;
		int totalItem=homePage.numOfAllItem();
		String expectedMessage="You need to choose options for your item.";
		boolean pageIsDisplayed=homePage.pageIsDisplayed();
		while(count<numOfItemsToAdd) {
			int itemIndex=pageIsDisplayed?toNextPage(totalItem):random.nextInt(totalItem)+1;
			WebElement e=homePage.getElement(itemIndex);
			action.moveToElement(e).build().perform();
			switch(option) {
			case "color":
				homePage.pickSize(e, random);
				break;
			case "size":
				homePage.pickColor(e, random);
				break;
				}
			homePage.addToCart(e, wait);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String actualMessage=productPage.getAddToCartMessage(wait);
			Assert.assertEquals(actualMessage, expectedMessage);
			driver.navigate().back();
			count++;
			}
		}
	
	public String[] selectOptionFromProductPage(String option) {
		addToCartSetUp();
		int count=0;
		int totalItem=homePage.numOfAllItem();
		String[] displayedMessages=new String[2];
		boolean pageIsDisplayed=homePage.pageIsDisplayed();
		while(count<numOfItemsToAdd) {
			int itemIndex=pageIsDisplayed?toNextPage(totalItem):random.nextInt(totalItem)+1;
			WebElement e=homePage.getElement(itemIndex);
			e.click();
			switch(option) {
			case "color":
				productPage.pickSize(wait, random);
				productPage.addToCart(wait);
				displayedMessages[0]=productPage.colorWarning(wait);
				break;
			case "size":
				productPage.pickColor(wait, random);
				productPage.addToCart(wait);
				displayedMessages[0]=productPage.sizeWarning(wait);
				break;
			case "noColorNSize":
				productPage.addToCart(wait);
				displayedMessages[0]=productPage.colorWarning(wait);
				displayedMessages[1]=productPage.sizeWarning(wait);
				break;
			default:
				productPage.pickSize(wait, random);
				productPage.pickColor(wait, random);
				productPage.chooseQty(option);
				productPage.addToCart(wait);
				displayedMessages[0]=productPage.qtyWarning(wait);
				}

			driver.navigate().back();
			count++;
			}
		return displayedMessages;
		}
}

