package org.openchat.domain.user;

public class UserService {

    private final IdGenerator idGenerator;
    private final UserRepository userRepository;

    public UserService(IdGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public User create(RegistrationData registrationData) {
        User user = new User(idGenerator.nextId(),
                                registrationData.username(),
                                registrationData.password(),
                                registrationData.about());
        userRepository.add(user);
        return user;
    }
}
