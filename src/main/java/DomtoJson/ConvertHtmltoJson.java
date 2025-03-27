package DomtoJson;

import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ConvertHtmltoJson {
	public static void convertDomToJson(String url) {
		 try {
	            Document doc = Jsoup.connect(url).get(); // Fetch the page source

	            // Convert DOM to JSON
	            String jsonOutput = convertDomToJson(doc);
	            System.out.println("Extracted JSON Succesfully");
	            saveJsonToFile(jsonOutput, "htmljson.json");
	            // Parse JSON and perform click actions
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode rootNode = objectMapper.readTree(jsonOutput);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Converts HTML DOM to JSON.
	     */
	    private static String convertDomToJson(Document doc) throws Exception {
	        ObjectMapper objectMapper = new ObjectMapper();
	        ObjectNode rootNode = objectMapper.createObjectNode();

	        // Extract links
	        ArrayNode linksArray = objectMapper.createArrayNode();
	        doc.select("a").forEach(link -> {
	            ObjectNode linkNode = objectMapper.createObjectNode();
	            linkNode.put("tag", "a");
	            linkNode.put("text", link.text().trim());
	            linkNode.put("id", link.attr("id"));
	            linkNode.put("class", link.attr("class"));
	            linkNode.put("href", link.attr("href"));
	            linkNode.put("clickable", !link.attr("href").isEmpty()); // Clickable if href exists
	            linksArray.add(linkNode);
	        });
	        rootNode.set("links", linksArray);

	        // Extract buttons
	        ArrayNode buttonArray = objectMapper.createArrayNode();
	        doc.select("button").forEach(button -> {
	            ObjectNode buttonNode = objectMapper.createObjectNode();
	            buttonNode.put("tag", "button");
	            buttonNode.put("class", button.attr("class"));
	            buttonNode.put("name", button.text().trim());
	            buttonNode.put("id", button.attr("id"));
	            buttonNode.put("clickable", !button.text().isEmpty()); // Clickable if text exists
	            buttonArray.add(buttonNode);
	        });
	        rootNode.set("buttons", buttonArray);

	        // Extract inputs
	        ArrayNode inputArray = objectMapper.createArrayNode();
	        doc.select("input").forEach(input -> {
	            ObjectNode inputNode = objectMapper.createObjectNode();
	            inputNode.put("tag", "input");
	            inputNode.put("type", input.attr("type"));
	            inputNode.put("name", input.attr("name"));
	            inputNode.put("class", input.attr("class"));
	            inputNode.put("value", input.attr("value"));
	            inputNode.put("id", input.attr("id"));
	            inputNode.put("placeholder", input.attr("placeholder"));
	            inputNode.put("passvalue", !input.attr("name").isEmpty()); // Clickable if name exists
	            inputArray.add(inputNode);
	        });
	        rootNode.set("inputs", inputArray);
	        
	        ArrayNode divArray = objectMapper.createArrayNode();
	        doc.select("div").forEach(div -> {
				ObjectNode divNode = objectMapper.createObjectNode();
				divNode.put("tag", "div");
				divNode.put("class", div.attr("class"));
				divNode.put("id", div.attr("id"));
				divNode.put("name", div.attr("name"));
				divNode.put("text", div.text().trim());
				divNode.put("clickable", !div.text().isEmpty()); // Clickable if text exists
				divArray.add(divNode);
			});
			rootNode.set("div", divArray);
			
			 // Extract spans <span>
	        ArrayNode spanArray = objectMapper.createArrayNode();
	        doc.select("span").forEach(span -> {
	            ObjectNode spanNode = objectMapper.createObjectNode();
	            spanNode.put("tag", "span");
	            spanNode.put("class", span.attr("class"));
	            spanNode.put("id", span.attr("id"));
	            spanNode.put("text", span.text().trim());
	            spanNode.put("clickable", !span.text().isEmpty());
	            spanArray.add(spanNode);
	        });
	        rootNode.set("spans", spanArray);

	        // Extract paragraphs <p>
	        ArrayNode paragraphArray = objectMapper.createArrayNode();
	        doc.select("p").forEach(paragraph -> {
	            ObjectNode paragraphNode = objectMapper.createObjectNode();
	            paragraphNode.put("tag", "p");
	            paragraphNode.put("text", paragraph.text().trim());
	            paragraphNode.put("clickable", !paragraph.text().isEmpty());
	            paragraphArray.add(paragraphNode);
	        });
	        rootNode.set("paragraphs", paragraphArray);

	        // Extract images <img>
	        ArrayNode imageArray = objectMapper.createArrayNode();
	        doc.select("img").forEach(image -> {
	            ObjectNode imageNode = objectMapper.createObjectNode();
	            imageNode.put("tag", "img");
	            imageNode.put("src", image.attr("src"));
	            imageNode.put("alt", image.attr("alt"));
	            imageArray.add(imageNode);
	        });
	        rootNode.set("images", imageArray);
	        
	        // Extract forms <form>
	        ArrayNode formArray = objectMapper.createArrayNode();
	        doc.select("form").forEach(form -> {
	            ObjectNode formNode = objectMapper.createObjectNode();
	            formNode.put("tag", "form");
	            formNode.put("action", form.attr("action"));
	            formNode.put("method", form.attr("method"));
	            formArray.add(formNode);
	        });
	        rootNode.set("forms", formArray);

	        // Extract labels <label>
	        ArrayNode labelArray = objectMapper.createArrayNode();
	        doc.select("label").forEach(label -> {
	            ObjectNode labelNode = objectMapper.createObjectNode();
	            labelNode.put("tag", "label");
	            labelNode.put("text", label.text().trim());
	            labelNode.put("for", label.attr("for"));
	            labelArray.add(labelNode);
	        });
	        rootNode.set("labels", labelArray);

	        // Extract textareas <textarea>
	        ArrayNode textareaArray = objectMapper.createArrayNode();
	        doc.select("textarea").forEach(textarea -> {
	            ObjectNode textareaNode = objectMapper.createObjectNode();
	            textareaNode.put("tag", "textarea");
	            textareaNode.put("name", textarea.attr("name"));
	            textareaNode.put("value", textarea.text().trim());
	            textareaArray.add(textareaNode);
	        });
	        rootNode.set("textareas", textareaArray);

	        // Extract select <select> and option <option>
	        ArrayNode selectArray = objectMapper.createArrayNode();
	        doc.select("select").forEach(select -> {
	            ObjectNode selectNode = objectMapper.createObjectNode();
	            selectNode.put("tag", "select");
	            selectNode.put("name", select.attr("name"));

	            ArrayNode optionArray = objectMapper.createArrayNode();
	            select.select("option").forEach(option -> {
	                ObjectNode optionNode = objectMapper.createObjectNode();
	                optionNode.put("tag", "option");
	                optionNode.put("value", option.attr("value"));
	                optionNode.put("text", option.text().trim());
	                optionArray.add(optionNode);
	            });
	            selectNode.set("options", optionArray);
	            selectArray.add(selectNode);
	        });
	        rootNode.set("selects", selectArray);

	        // Extract tables <table>, rows <tr>, and columns <td> and <th>
	        ArrayNode tableArray = objectMapper.createArrayNode();
	        doc.select("table").forEach(table -> {
	            ObjectNode tableNode = objectMapper.createObjectNode();
	            tableNode.put("tag", "table");

	            ArrayNode rowArray = objectMapper.createArrayNode();
	            table.select("tr").forEach(row -> {
	                ObjectNode rowNode = objectMapper.createObjectNode();
	                rowNode.put("tag", "tr");

	                ArrayNode cellArray = objectMapper.createArrayNode();
	                row.select("td, th").forEach(cell -> {
	                    ObjectNode cellNode = objectMapper.createObjectNode();
	                    cellNode.put("tag", cell.tagName());
	                    cellNode.put("text", cell.text().trim());
	                    cellNode.put("clickable", !cell.text().isEmpty());
	                    cellArray.add(cellNode);
	                });
	                rowNode.set("cells", cellArray);
	                rowArray.add(rowNode);
	            });
	            tableNode.set("rows", rowArray);
	            tableArray.add(tableNode);
	        });
	        rootNode.set("tables", tableArray);

	        // Extract lists <ul>, <ol> and list items <li>
	        ArrayNode listArray = objectMapper.createArrayNode();
	        doc.select("ul, ol").forEach(list -> {
	            ObjectNode listNode = objectMapper.createObjectNode();
	            listNode.put("tag", list.tagName());

	            ArrayNode listItemArray = objectMapper.createArrayNode();
	            list.select("li").forEach(li -> {
	                ObjectNode liNode = objectMapper.createObjectNode();
	                liNode.put("tag", "li");
	                liNode.put("text", li.text().trim());
	                listItemArray.add(liNode);
	            });
	            listNode.set("items", listItemArray);
	            listArray.add(listNode);
	        });
	        rootNode.set("lists", listArray);

	        // Extract iframes <iframe>
	        ArrayNode iframeArray = objectMapper.createArrayNode();
	        doc.select("iframe").forEach(iframe -> {
	            ObjectNode iframeNode = objectMapper.createObjectNode();
	            iframeNode.put("tag", "iframe");
	            iframeNode.put("src", iframe.attr("src"));
	            iframeArray.add(iframeNode);
	        });
	        rootNode.set("iframes", iframeArray);

	        // Extract scripts <script>
	        ArrayNode scriptArray = objectMapper.createArrayNode();
	        doc.select("script").forEach(script -> {
	            ObjectNode scriptNode = objectMapper.createObjectNode();
	            scriptNode.put("tag", "script");
	            scriptNode.put("src", script.attr("src"));
	            scriptArray.add(scriptNode);
	        });
	        rootNode.set("scripts", scriptArray);

	        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
	   
	}
	   public static void main(String[] args) {
	        try {
	            String url = "https://test1.creditfair.in/";
	            Document doc = Jsoup.connect(url).get(); // Fetch the page source

	            // Convert DOM to JSON
	            String jsonOutput = convertDomToJson(doc);
	            System.out.println("Extracted JSON: \n" + jsonOutput);
	            saveJsonToFile(jsonOutput, "htmljson.json");

	            // Parse JSON and perform click actions
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode rootNode = objectMapper.readTree(jsonOutput);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Converts HTML DOM to JSON.
	     */
	    private static void saveJsonToFile(String jsonOutput, String filePath) {
	        try (FileWriter file = new FileWriter(new File(filePath))) {
	            file.write(jsonOutput);
	            System.out.println("JSON saved to " + filePath);
	        } catch (Exception e) {
	            System.out.println("Error saving JSON to file: " + e.getMessage());
	        }
	    }
	}
