package Div;

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

public class Div {
public static void clickElementByTextDiv(WebDriver driver, String divText) {
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    WebElement element = null;
	    try {
	        // Prioritize efficient lookups
	        String[] xpaths = {
	            "//div[normalize-space()='" + divText + "']",  // Exact match (best)
	            "//div[@id='" + divText + "']",              // ID match
	            "//div[contains(text(),'" + divText + "')]", // Partial text match
	            "//div[contains(@class,'" + divText + "')]"  // Class match (last resort)
	        };

	        for (String xpath : xpaths) {
	            element = findClickableElement(driver, wait, xpath);
	            if (element != null) {
	                System.out.println("Clicked on: " + divText + " (Matched XPath: " + xpath + ")");
	                element.click();
	                return;
	            }
	        }
	        System.out.println("Could not find or click: " + divText);
	    } catch (Exception e) {
	        System.out.println("Error while clicking on: " + divText + " | " + e.getMessage());
	    }
	    }

private  static WebElement findClickableElement(WebDriver driver, WebDriverWait wait, String xpath) {
    try {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    } catch (Exception e) {
        return null;
    }
    }

public static void  processDiv(WebDriver driver, String divTextToClick) throws IOException { 
 	  String jsonFilePath = "htmljson.json"; // Path to external JSON file
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(Paths.get(jsonFilePath).toFile());
    try {
        ArrayNode divArray = (ArrayNode) rootNode.get("div");
        for (JsonNode div : divArray) {
            if (div.get("clickable").asBoolean() && (div.path("name").asText().equals(divTextToClick) || 
         		   div.path("id").asText().equals(divTextToClick) || 
         		   div.path("class").asText().equals(divTextToClick) ||
         		   div.path("text").asText().equals(divTextToClick)
         		   )) { 
                clickElementByTextDiv(driver, divTextToClick);
                break;
            }
        }
    } catch (Exception e) {
        System.out.println("Error processing buttons: " + e.getMessage());
    }
}

	}

