package automateany.automateany;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Button.Button;
import io.github.bonigarcia.wdm.WebDriverManager;
import InputBox.Input;
import Link.Link;

public class TestClass {
	 public static WebDriver driver;

	    public static void main(String[] args) {
	        WebDriverManager.chromedriver().setup();
	        String inputBoxinput = "6546767676";
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--window-size=1920,1080");
	        options.addArguments("--start-maximized");
	        options.addArguments("--headless");  // Remove this if debugging visually
	        options.addArguments("--no-sandbox");
	        options.addArguments("--disable-dev-shm-usage");
	        try {
	            driver = new ChromeDriver();
	            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	            driver.manage().window().maximize();
	            String url =  "https://test1.creditfair.in/";// Read URL from JSON
	            driver.get(url);
//	            Divop.processDivByText(driver, "☰");
//	            LinkClicker.linkClick(driver, "Apply Now");
//	            LinkClicker.linkClick(driver, "Tutorials");
//	            LinkClicker.linkClick(driver, "Tutorial");

//	            driver.navigate().back();
//	            JavascriptExecutor js = (JavascriptExecutor) driver;
//	            js.executeScript("window.scrollBy(0,10000)"); // Scroll down by 500 pixels
	            Input.processInput(driver, "Enter your Mobile number","6565645656");
	            Button.processButtonByText(driver, "Send");
//	            Link.linkClick(driver, "Tutorials");
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            driver.quit();
	        }
	        }
}
