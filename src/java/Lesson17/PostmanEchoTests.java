import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostmanEchoTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testGetRequest() {
        Response response = given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.getStatusCode());
        assertEquals("bar1", response.jsonPath().getString("args.foo1"));
        assertEquals("bar2", response.jsonPath().getString("args.foo2"));
    }

    @Test
    public void testPostRequest() {
        Response response = given()
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.getStatusCode());
        assertEquals("bar1", response.jsonPath().getString("form.foo1"));
        assertEquals("bar2", response.jsonPath().getString("form.foo2"));
    }

    @Test
    public void testPutRequest() {
        Response response = given()
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.getStatusCode());
        assertEquals("bar1", response.jsonPath().getString("form.foo1"));
        assertEquals("bar2", response.jsonPath().getString("form.foo2"));
    }

    @Test
    public void testDeleteRequest() {
        Response response = given()
                .when()
                .delete("/delete")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.getStatusCode());
        assertEquals("https://postman-echo.com/delete", response.jsonPath().getString("url"));
    }

    @Test
    public void testPatchRequest() {
        Response response = given()
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.getStatusCode());
        assertEquals("bar1", response.jsonPath().getString("form.foo1"));
        assertEquals("bar2", response.jsonPath().getString("form.foo2"));
    }
}
