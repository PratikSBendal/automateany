package TextArea;

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

public class TextArea {
	public static void textElmentProcess(WebDriver driver, String textareaFieldName, String textareaValue)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
  	    WebElement element = null;
  	  try {
          // Prioritize fast searches first
          String[] xpaths = {
              "//textarea[@name='" + textareaFieldName + "']",       // Name match (fastest)
              "//textarea[@id='" + textareaFieldName + "']",         // ID match
              "//textarea[@placeholder='" + textareaFieldName + "']", // Placeholder match
              "//textarea[contains(@class,'" + textareaFieldName + "')]", // Class name match
              "//textarea[contains(text(),'" + textareaFieldName + "')]"  // Partial text match (slowest)
          };

          for (String xpath : xpaths) {
              element = findElementByXPath(driver, wait, xpath);
              if (element != null) {
                  System.out.println("Entered: " + textareaFieldName + " (Matched XPath: " + xpath + ")");
                  element.sendKeys(textareaValue);
                  return;
              }
          }

          System.out.println("Could not find textarea field: " + textareaFieldName);
      } catch (Exception e) {
          System.out.println("Error while entering: " + textareaFieldName + " | " + e.getMessage());
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
	  
	  public static void processTextArea(WebDriver driver, String textareaname, String textareavalue) throws IOException {
			 String jsonFilePath = "htmljson.json"; // Path to external JSON file
	         ObjectMapper objectMapper = new ObjectMapper();
	         JsonNode rootNode = objectMapper.readTree(Paths.get(jsonFilePath).toFile());
	       try {
	           ArrayNode textareaArray = (ArrayNode) rootNode.get("textareas");
			    boolean isClicked = false;
	           for (JsonNode textarea : textareaArray) {

	               if (textarea.get("passvalue").asBoolean() && 
	            	        (textarea.path("name").asText().equals(textareaname) || 
	            	         textarea.path("placeholder").asText().equals(textareaname) || 
	            	         textarea.path("id").asText().equals(textareaname) ||
	            	         textarea.path("type").asText().equals(textareaname))) {
	            	   textElmentProcess(driver, textareaname, textareavalue);
	    		            isClicked = true;
	            	        break;
	            	    }
	           }
	           if (!isClicked) {
				   System.out.println("No matching textarea found for: " + textareaname);
			   }
	           
	       } catch (Exception e) {
	           System.out.println("Error processing buttons: " + e.getMessage());
	       }
	   }
}
