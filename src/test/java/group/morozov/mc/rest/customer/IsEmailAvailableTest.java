package group.morozov.mc.rest.customer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@Tag("integration")
@QuarkusTestResource(MySqlDatabaseTestResource.class)
public class IsEmailAvailableTest {
    @Test
    public void DefaultExists() {
        given()
                .body("{\"customerEmail\": \"good@example.com\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/rest/default/V1/customers/isEmailAvailable")
                .then()
                .statusCode(200)
                .body(equalTo("true"));
    }
    @Test
    public void Exists() {
        given()
                .body("{\"customerEmail\": \"good@example.com\", \"websiteId\": 1}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/rest/default/V1/customers/isEmailAvailable")
                .then()
                .statusCode(200)
                .body(equalTo("true"));
    }
    @Test
    public void NoExistsWithWebsite() {
        given()
                .body("{\"customerEmail\": \"good@example.com\", \"websiteId\": 2}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/rest/default/V1/customers/isEmailAvailable")
                .then()
                .statusCode(200)
                .body(equalTo("false"));
    }
    @Test
    public void NoExists2WithWebsite() {
        given()
                .body("{\"customerEmail\": \"good@example.com\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/rest/base2/V1/customers/isEmailAvailable")
                .then()
                .statusCode(200)
                .body(equalTo("false"));
    }
    @Test
    public void NotExists() {
        given()
                .body("{\"customerEmail\": \"123123@example.com\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/rest/default/V1/customers/isEmailAvailable")
                .then()
                .statusCode(200)
                .body(equalTo("false"));
    }
}