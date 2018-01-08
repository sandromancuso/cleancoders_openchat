package acceptance;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openchat.OpenChat;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HelloWorldAPI_AcceptanceTest.class,
        RegistrationAPI_AcceptanceTest.class
})
public class APITestSuit {

    static String BASE_URL = "http://localhost:4321";

    private static OpenChat openChat;

    @BeforeClass
    public static void setUp() {
        openChat = new OpenChat();
        openChat.start();
    }

    @AfterClass
    public static void tearDown() {
        openChat.stop();
    }
}
