package acceptance;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openchat.web.OpenChat;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegisterTestUsers.class,
        HelloWorldAPI_AcceptanceTest.class,
        RegistrationAPI_AcceptanceTest.class,
        LoginAPI_AcceptanceTest.class,
        RetrieveTimelineAPI_AcceptanceTest.class,
        RetrieveWallAPI_AcceptanceTest.class,
        RetrieveUsersAPI_AcceptanceTest.class
})
public class APITestSuite {

    static final String BASE_URL = "http://localhost:4321";
    
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
