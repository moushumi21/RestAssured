package TestCases;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Create_A_Product {
	
	@Test
	public void create_A_Product() {
		
		String payloadPath = "src/main/java/data/payload.json";
		
//		HashMap<String,String> payload = new HashMap<String,String>();
//		payload.put("name", "new product");
//		payload.put("price", "100");
//		payload.put("description", "it's a new product");
//		payload.put("category_id", "6");
		
		Response response=
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json; charset=UTF-8")
					.body(new File (payloadPath))
				.when()
					.log().all()
					.post("/create.php")
				.then()
					.log().all()
					.extract().response();
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);

		System.out.println("Response Time: " + responseTime);
		if(responseTime<=200) {
			System.out.println("Response time is within range");
		}else {
			System.out.println("out of range");
		}
		
		String responseBody = response.getBody().asString();
		System.out.println("Response body: " + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String successMessage = jp.getString("message");
		Assert.assertEquals(successMessage,"Product was created.");
			
	}
	
}
