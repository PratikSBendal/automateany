package Button;

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

public class Button {
	public static void clickElementByTextButton(WebDriver driver, String buttonText) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = null;

		try {
			// Prioritize efficient lookups
			String[] xpaths = { 
					"//button[@id='" + buttonText + "']", // ID match
					"//button[normalize-space()='" + buttonText + "']", // Exact match (best)
					"//button[contains(text(),'" + buttonText + "')]", // Partial text match
					"//button[contains(@class,'" + buttonText + "')]" // Class match (last resort)
			};

			for (String xpath : xpaths) {
				element = findClickableElement(driver, wait, xpath);
				if (element != null) {
					System.out.println("Clicked on: " + buttonText + " (Matched XPath: " + xpath + ")");
					element.click();
					return;
				}
			}

			System.out.println("Could not find or click: " + buttonText);
		} catch (Exception e) {
			System.out.println("Error while clicking on: " + buttonText + " | " + e.getMessage());
		}
	}

// Utility function to find a clickable button
	private static WebElement findClickableElement(WebDriver driver, WebDriverWait wait, String xpath) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		} catch (Exception e) {
			return null;
		}
	}

	public static void processButton(WebDriver driver, String buttonTextToClick) throws IOException {
		String jsonFilePath = "htmljson.json"; 
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(Paths.get(jsonFilePath).toFile());
		try {
			ArrayNode buttonArray = (ArrayNode) rootNode.get("buttons");
		    boolean isClicked = false;
			for (JsonNode button : buttonArray) {
				if (button.get("clickable").asBoolean() && (button.path("id").asText().equals(buttonTextToClick)
						|| button.path("name").asText().equals(buttonTextToClick)
						|| button.path("class").asText().equals(buttonTextToClick)
						|| button.path("id").asText().equals(buttonTextToClick))) {
					clickElementByTextButton(driver, buttonTextToClick);
		            isClicked = true;
					break;
				}
			}
			if (!isClicked) {
			        System.out.println("No matching button found for: " + buttonTextToClick);
			}
		} catch (Exception e) {
			System.out.println("Error processing buttons: " + e.getMessage());
		}
	}

}
