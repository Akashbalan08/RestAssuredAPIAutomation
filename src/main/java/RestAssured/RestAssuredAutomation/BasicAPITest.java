package RestAssured.RestAssuredAutomation;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicAPITest {
    @Test
    public void testGetUser() {
        Response response = RestAssured.get("https://reqres.in/api/users/2");

        // Print the response
        System.out.println(response.getBody().asString());

        // Assertions
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.asString().contains("Janet"));
    }
        
       @Test
        public void testPostUser() {
            String requestBody = "{ \"name\": \"John\", \"job\": \"QA Engineer\" }";

            RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                .when()
                    .post("https://reqres.in/api/users")
                .then()
                    .statusCode(201);
    }
       @Test
       public void testJsonResponse() {
           RestAssured
               .given()
               .when()
                   .get("https://reqres.in/api/users/2")
               .then()
                   .statusCode(200)
                   .body("data.first_name", org.hamcrest.Matchers.equalTo("Janet"));
       }
       
       @Test
       public void testExtractResponse() {
           Response response = RestAssured.get("https://reqres.in/api/users/2");
           String firstName = response.jsonPath().getString("data.first_name");

           Assert.assertEquals(firstName, "Janet");
       }
       @Test
       public void testWithQueryParams() {
           RestAssured
               .given()
                   .queryParam("page", 2)
               .when()
                   .get("https://reqres.in/api/users")
               .then()
                   .statusCode(200);
       }

}
