package testSetup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Setup {

	public WebDriver driver;
	public ResourceBundle rb;
	public Logger logger;
	
	@BeforeClass
	@Parameters({"browser"})
	public void setup(@Optional("Chrome")String browser) {
		rb=ResourceBundle.getBundle("config");
		logger=LogManager.getLogger(getClass());
		
		if(browser.equals("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver(options);
		}
		else {
			driver=new FirefoxDriver();
		}
		driver.get(rb.getString("url"));
		driver.manage().window().maximize();
	}
	
	public String takeScreenshot(String testName) {
		//String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takess=(TakesScreenshot) driver;
		File screenshot=takess.getScreenshotAs(OutputType.FILE);
		String imagePath=System.getProperty("user.dir")+File.separator+"screenshots"+
						File.separator+ testName+"_"+".png";
		try {
			FileUtils.copyFile(screenshot, new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return imagePath;
	}
}
