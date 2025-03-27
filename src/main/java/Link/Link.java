package Link;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class Link {
public static void clickElementByText(WebDriver driver, String linkText) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	WebElement element = null;

	try {
		// Optimized element search order
		String[] xpaths = { "//a[@id='" + linkText + "']", // ID match (fastest)
				"//a[normalize-space()='" + linkText + "']", // Exact text match
				"//a[contains(@class,'" + linkText + "')]", // Class match
				"//a[contains(text(),'" + linkText + "')]" // Partial text match (slowest)
		};

		for (String xpath : xpaths) {
			element = findElementByXPath(driver, wait, xpath);
			if (element != null) {
				System.out.println("Clicked on: " + linkText + " (Matched XPath: " + xpath + ")");
				element.click();
				return;
			}
		}

		System.out.println("Could not find or click: " + linkText);
	} catch (Exception e) {
		System.out.println("Error while clicking on: " + linkText + " | " + e.getMessage());
	}
}

//Utility function for finding clickable elements
private static  WebElement findElementByXPath(WebDriver driver, WebDriverWait wait, String xpath) {
	try {
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	} catch (Exception e) {
		return null; // Avoid throwing an exception; return null instead
	}
}
public static void processLink(WebDriver driver, String linkTextToClick) throws IOException {
	 String jsonFilePath = "htmljson.json"; // Path to external JSON file
     ObjectMapper objectMapper = new ObjectMapper();
     JsonNode rootNode = objectMapper.readTree(Paths.get(jsonFilePath).toFile());
   try {
       ArrayNode linkArray = (ArrayNode) rootNode.get("links");
       for (JsonNode link : linkArray) {
           if (link.get("clickable").asBoolean() && 
         		  (link.path("text").asText().equals(linkTextToClick) || 
         		  link.path("class").asText().equals(linkTextToClick) || 
         		  link.path("id").asText().equals(linkTextToClick))) { 
            clickElementByText(driver, linkTextToClick);
            break;
           }
       }
   } catch (Exception e) {
       System.out.println("Error processing buttons: " + e.getMessage());
   }
}
}
