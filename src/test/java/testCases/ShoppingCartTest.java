package testCases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ShoppingCartTest extends Setup {

	WebDriverWait wait;
	HomePage homePage;
	ShoppingCart shoppingCart;
	int numOfItemsPerPage=12;
	int numOfItemsToAdd=5;
	int numOfItemToEdit=2;
	Actions action;
	Random random;
	String searchKeyWords="hoodie";
	int currentPage;
	
	@Test(priority=1)
	public void testDeleteItems() {
		addToCartSetUp();
		addItemsToCart();
		ArrayList<Item> unEditItemInfo=shoppingCart.getAllItemsInfoInCart(wait);
		int count=0;
		while(count<numOfItemToEdit){
			List<WebElement> itemInCart=shoppingCart.getItemsInCart(wait);
			int randomNumber=random.nextInt(itemInCart.size());
			shoppingCart.deleteItem(itemInCart.get(randomNumber));
			unEditItemInfo.remove(randomNumber);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		ArrayList<Item> editItemInfo=shoppingCart.getAllItemsInfoInCart(wait);
		Assert.assertTrue(unEditItemInfo.equals(editItemInfo));
	}
	
	@Test(priority=2, dataProvider="EditItemQuantity", dataProviderClass=RegisterDataProviders.class)
	public void editQuantityTest(String[] qty) {
		addToCartSetUp();
		addItemsToCart();
		ArrayList<Item> unEditItemInfo=shoppingCart.getAllItemsInfoInCart(wait);
		for(String quantity:qty) {
			List<WebElement> itemInCart=shoppingCart.getItemsInCart(wait);
			int randomNumber=random.nextInt(itemInCart.size());
			WebElement item=itemInCart.get(randomNumber);
			shoppingCart.changeQty(item, quantity, wait);
			shoppingCart.clickUpdateBtn(wait);
			if(canParseToInt(quantity)&&Integer.parseInt(quantity)<=0||containsMinusSignAndNumber(quantity)) {
				String message=shoppingCart.getMessage(item, wait);
				String expectedMessage="Please enter a number greater than 0 in this field.";
				Assert.assertEquals(message, expectedMessage);
				int originalQty=unEditItemInfo.get(randomNumber).getQuantity();
				shoppingCart.changeQty(item,String.valueOf(originalQty) , wait);
				continue;
			}
			else if(containsOnlyLettersAndSymbols(quantity)) {
				String message=shoppingCart.getMessage(item, wait);
				String expectedMessage="This is a required field.";
				Assert.assertEquals(message, expectedMessage);
				int originalQty=unEditItemInfo.get(randomNumber).getQuantity();
				shoppingCart.changeQty(item,String.valueOf(originalQty) , wait);
				continue;
			}
			else if(!canParseToInt(quantity)) {
				quantity=extractNumberFromString(quantity);
			}
			unEditItemInfo.get(randomNumber).setQuantity(Integer.parseInt(quantity));
			unEditItemInfo.get(randomNumber).setTotalPrice();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<Item> editItemInfo=shoppingCart.getAllItemsInfoInCart(wait);
			Assert.assertEquals(unEditItemInfo, editItemInfo);
		}
	}
	
	@Test(priority=3, dataProvider="EditItemQuantityFromProductPage", dataProviderClass=RegisterDataProviders.class)
	public void editItemTest(String[] qty) {
		addToCartSetUp();
		addItemsToCart();
		ArrayList<Item> unEditItemInfo=shoppingCart.getAllItemsInfoInCart(wait);
		for(String quantity:qty) {
			List<WebElement> itemInCart=shoppingCart.getItemsInCart(wait);
			int randomNumber=random.nextInt(itemInCart.size());
			WebElement item=itemInCart.get(randomNumber);
			shoppingCart.editItem(item);
			ProductDetailPage pdpage=new ProductDetailPage(driver);
			String size=pdpage.pickSize(wait, random);
			String color=pdpage.pickColor(wait, random);
			pdpage.chooseQty(quantity);
			pdpage.updateCart(wait);
			unEditItemInfo.get(randomNumber).setSize(size);
			unEditItemInfo.get(randomNumber).setColor(color);
			unEditItemInfo.get(randomNumber).setQuantity(Integer.parseInt(quantity));
			unEditItemInfo.get(randomNumber).setTotalPrice();
			ArrayList<Item> editItemInfo=shoppingCart.getAllItemsInfoInCart(wait);
			boolean checkEqual=true;
			for(Item unEditItem:unEditItemInfo) {
				for(Item editItem:editItemInfo) {
					if(!unEditItem.equals(editItem)) {
						checkEqual=false;
					}
					else {
						checkEqual=true;
						break;
					}
				}
			}
			Assert.assertTrue(checkEqual);
		}
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
	
	public void addToCartSetUp() {
		currentPage=1;
		wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		action=new Actions(driver);
		random=new Random();
		homePage=new HomePage(driver);
		shoppingCart=new ShoppingCart(driver);
		homePage.searchItem(searchKeyWords);
		wait.until(ExpectedConditions.visibilityOf(homePage.itemTable));
	}
	
	public void addItemToCart(WebElement element) {
		action.moveToElement(element).build().perform();
		String color=homePage.pickColor(element, random);
		String size=homePage.pickSize(element, random);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		homePage.addToCart(element, wait);
	}
	
	public void addItemsToCart() {
		int count=0;
		int totalItem=homePage.numOfAllItem();
		boolean pageIsDisplayed=homePage.pageIsDisplayed();
			while(count<numOfItemsToAdd) {
				int itemIndex=pageIsDisplayed?toNextPage(totalItem):random.nextInt(totalItem)+1;
				WebElement e=homePage.getElement(itemIndex);
				addItemToCart(e);
				count++;
			}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		homePage.clickCart(wait);
		homePage.clickViewCart(wait);
	}
	
	public static boolean canParseToInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    
	}
	 public static boolean containsMinusSignAndNumber(String str) {
	        return str.matches(".*-.*\\d.*");
	    }
	 
	 public static boolean containsOnlyLettersAndSymbols(String str) {
	        return str.matches("^[^0-9]*$");
	    }
	 
	 public String  extractNumberFromString(String str) {
	        Pattern pattern = Pattern.compile("\\d+");
	        Matcher matcher = pattern.matcher(str);
	        String quantity="";

	        while (matcher.find()) {
	            quantity+=matcher.group();
	        }
	        return quantity;
	    }
}
