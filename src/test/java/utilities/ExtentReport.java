package utilities;

import java.io.File;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testSetup.Setup;



public class ExtentReport implements ITestListener{

	public ExtentSparkReporter sparkReporter; 
	public static ExtentReports extent; 
	public static ExtentTest test; 
	public String testName;
	
	public void onStart(ITestContext context) {
	
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+File.separator+"reports"+File.separator+"myReport");
		
		sparkReporter.config().setDocumentTitle("Automation Test"); 
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter); 
		
		extent.setSystemInfo("Computer Name", "localhost");
		extent.setSystemInfo("Tester name", "THao");
		extent.setSystemInfo("os","apple");
		extent.setSystemInfo("Browser name","Chrome,Firefox,Edge");
	}
	
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getName()); // create a new entry in the report
		test.log(Status.PASS, "Testcase passed is:"+result.getName());
	}
	
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getName());
		test.log(Status.FAIL, "Test case that fail is:"+result.getName());
		test.log(Status.FAIL, "Result of failure is:"+result.getThrowable());
		Setup bc=new Setup();
		try {
			String imagePath=bc.takeScreenshot(result.getName());
			test.addScreenCaptureFromPath(imagePath);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
		
	}
	
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
}
