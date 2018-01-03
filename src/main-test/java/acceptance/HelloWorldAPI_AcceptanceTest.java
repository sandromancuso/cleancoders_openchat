package acceptance;

import org.junit.Test;

import static acceptance.APITestSuite.BASE_URL;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

public class HelloWorldAPI_AcceptanceTest {

    @Test public void
    check_if_the_application_can_respond_hello_world() {
        when()
                .get(BASE_URL + "/helloworld")
        .then()
                .statusCode(200)
                .body(containsString("Hello World!"));
    }
}
