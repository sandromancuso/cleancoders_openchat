package org.openchat.domain.user;

public class UserService {

    private final IdGenerator idGenerator;
    private final UserRepository userRepository;

    public UserService(IdGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public User create(RegistrationData registrationData) {
        validateUsername(registrationData.username());
        User user = userFrom(registrationData);
        userRepository.add(user);
        return user;
    }

    private User userFrom(RegistrationData registrationData) {
        return new User(idGenerator.nextId(),
                                    registrationData.username(),
                                    registrationData.password(),
                                    registrationData.about());
    }

    private void validateUsername(String username) {
        if (userRepository.isUsernameInUse(username)) {
            throw new UsernameAlreadyInUseException();
        }
    }
}
