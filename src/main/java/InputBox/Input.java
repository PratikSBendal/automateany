package InputBox;

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


public class Input {
 public static void passElementByInput(WebDriver driver, String inputFieldName, String inputValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  	    WebElement element = null;
  	  try {
          // Prioritize fast searches first
          String[] xpaths = {
              "//input[@name='" + inputFieldName + "']",       // Name match (fastest)
              "//input[@id='" + inputFieldName + "']",         // ID match
              "//input[@placeholder='" + inputFieldName + "']", // Placeholder match
              "//input[contains(@class,'" + inputFieldName + "')]", // Class name match
              "//input[contains(text(),'" + inputFieldName + "')]"  // Partial text match (slowest)
          };

          for (String xpath : xpaths) {
              element = findElementByXPath(driver, wait, xpath);
              if (element != null) {
                  System.out.println("Entered: " + inputValue + " (Matched XPath: " + xpath + ")");
                  element.sendKeys(inputValue);
                  return;
              }
          }

          System.out.println("Could not find input field: " + inputFieldName);
      } catch (Exception e) {
          System.out.println("Error while entering: " + inputFieldName + " | " + e.getMessage());
      }
  }

  // Utility function for finding elements
  private static WebElement findElementByXPath(WebDriver driver, WebDriverWait wait, String xpath) {
      try {
          return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      } catch (Exception e) {
          System.out.println("Element not found for XPath: " + xpath + " | " + e.getMessage());
          return null;
      }
  }
  
  public static void processInput(WebDriver driver, String inputname, String inputvalue) throws IOException {
		 String jsonFilePath = "htmljson.json"; // Path to external JSON file
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode rootNode = objectMapper.readTree(Paths.get(jsonFilePath).toFile());
       try {
           ArrayNode inputArray = (ArrayNode) rootNode.get("inputs");
		    boolean isClicked = false;
           for (JsonNode input : inputArray) {

               if (input.get("passvalue").asBoolean() && 
            	        (input.path("name").asText().equals(inputname) || 
            	         input.path("placeholder").asText().equals(inputname) || 
            	         input.path("id").asText().equals(inputname) ||
            	         input.path("type").asText().equals(inputname))) {
            	        passElementByInput(driver, inputname, inputvalue);
    		            isClicked = true;
            	        break;
            	    }
           }
           if (!isClicked) {
			   System.out.println("No matching input found for: " + inputname);
		   }
           
       } catch (Exception e) {
           System.out.println("Error processing buttons: " + e.getMessage());
       }
   }
}
