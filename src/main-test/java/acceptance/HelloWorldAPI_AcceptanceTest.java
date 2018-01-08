package acceptance;

import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

public class HelloWorldAPI_AcceptanceTest {

    @Test public void
    return_hello_world() {
        when()
                .get("http://localhost:4321/helloworld")
        .then()
                .body(containsString("Hello World!"));
    }

}
