package org.openchat.domain.users;

public class UserService {
    private final IdGenerator idGenerator;
    private final UserRepository userRepository;

    public UserService(IdGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public User createUser(RegistrationData registrationData)
                        throws UsernameAlreadyInUseException {
        validateUsername(registrationData.username());
        User user = createUserFrom(registrationData);
        userRepository.add(user);
        return user;
    }

    private void validateUsername(String username) throws UsernameAlreadyInUseException {
        if (userRepository.isUsernameTaken(username)) {
            throw new UsernameAlreadyInUseException();
        }
    }

    private User createUserFrom(RegistrationData registrationData) {
        String userId = idGenerator.next();
        return new User(userId,
                        registrationData.username(),
                        registrationData.password(),
                        registrationData.about());
    }
}
