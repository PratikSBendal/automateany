package automateany.automateany;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Button.Button;
import io.github.bonigarcia.wdm.WebDriverManager;
import InputBox.Input;
import Link.Link;
import Div.Div;
import DomtoJson.ConvertHtmltoJson;
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
	            ConvertHtmltoJson.convertDomToJson(url);
	            driver.get(url);
//	            Div.processDiv(driver, "☰");
//	            Link.clickElementByText(driver, "Tutorials");
//	            Link.clickElementByText(driver, "Tutorial");
//	            driver.navigate().back();
//	            JavascriptExecutor js = (JavascriptExecutor) driver;
//	            js.executeScript("window.scrollBy(0,10000)"); // Scroll down by 500 pixels
	            Input.processInput(driver, "Enter your Mobile number","6565645656");
	            Button.processButton(driver, "Send");
//	            Link.processLink(driver, "Tutorials");	
//	            Link.processLink(driver, "Tutorial");
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            driver.quit();
	        }
	        }
}
