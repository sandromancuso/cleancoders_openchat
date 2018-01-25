package acceptance;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openchat.OpenChat;
import org.openchat.domain.user.User;
import org.openchat.domain.user.UserRepository;

import java.util.List;

import static java.util.Arrays.asList;

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

//    static User ALICE = aUser().withUsername("Alice").build();
//    static User BOB = aUser().withUsername("Bob").build();
//    static User CHARLIE = aUser().withUsername("Charlie").build();
//    static User JULIE = aUser().withUsername("Julie").build();


//    static List<User> USERS = asList(ALICE, BOB, CHARLIE, JULIE);
    static List<User> USERS = asList();

    static String BASE_URL = "http://localhost:4321";

    private static OpenChat openChat;

    @BeforeClass
    public static void setUp() {
        System.out.println("APITestSuit setUp");
        openChat = openChat();
        openChat.start();
        System.out.println("Before await initialisation");
        openChat.awaitInitialization();
        System.out.println("After await initialisation");
    }

    @AfterClass
    public static void tearDown() {
        openChat.stop();
    }

    private static OpenChat openChat() {
        UserRepository userRepository = new UserRepository();
        USERS.forEach(userRepository::add);

        return new OpenChat() {
            @Override
            protected UserRepository userRepository() {
                return userRepository;
            }
        };
    }

}