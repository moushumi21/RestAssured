package TestCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Put_or_Update_a_Product {

	@Test
	public void update_a_product() {
		
		String payloadPath = "src/main/java/data/updatePayload.json";
		
		Response response=
				given()
					.log().all()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type","application/json;")
					.body(new File (payloadPath))
				.when()
					.log().all()
					.put("/update.php")
				.then()
					.log().all()
					.extract().response();
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
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
		Assert.assertEquals(successMessage,"Product was updated.");
			
	}
	
}

