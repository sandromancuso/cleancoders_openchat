package acceptance;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openchat.OpenChat;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegistrationAPI_AcceptanceTest.class,
        LoginAPI_AcceptanceTest.class,
        TimelineAPI_AcceptanceTest.class,
        WallAPI_AcceptanceTest.class,
        UsersAPI_AcceptanceTest.class,
        FolloweesAPI_AcceptanceTest.class
})
public class APITestSuit {

    static String BASE_URL = "http://localhost:4321";

    private static OpenChat openChat;

    @BeforeClass
    public static void setUp() {
        openChat = new OpenChat();
        openChat.start();
        openChat.awaitInitialization();
    }

    @AfterClass
    public static void tearDown() {
        openChat.stop();
    }

}