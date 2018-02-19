package org.openchat.domain.users;

public class UserService {
    private final IdGenerator idGenerator;
    private final UserRepository userRepository;

    public UserService(IdGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public User createUser(RegistrationData registrationData)
                        throws UsernameAlreadyInUseException{
        throw new UnsupportedOperationException();
    }
}
