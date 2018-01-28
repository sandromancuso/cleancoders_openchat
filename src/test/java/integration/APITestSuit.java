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
        IT_WallAPI.class,
        IT_UsersAPI.class,
        IT_FolloweesAPI.class
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