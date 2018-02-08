package integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openchat.OpenChat;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IT_RegistrationAPI.class,
        IT_LoginAPI.class,
        IT_TimelineAPI.class,
        IT_UsersAPI.class,
        IT_FolloweesAPI.class,
        IT_WallAPI.class
})
public class APITestSuit {

    static String BASE_URL = "http://localhost:4321";

    public static final String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
    public static final String DATE_PATTERN = "\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d([+-][0-2]\\d:[0-5]\\d|Z)";

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