package org.openchat.domain.user;

import org.openchat.infrastructure.IDGenerator;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

public class UserService {

    private final IDGenerator idGenerator;
    private final UserRepository userRepository;

    public UserService(IDGenerator idGenerator, UserRepository userRepository) {
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
    }

    public Optional<User> createUser(RegistrationData registrationData) {
        if (userRepository.isUsernameTaken(registrationData.username())) {
            return empty();
        }

        User user = new User(idGenerator.nextId(),
                registrationData.username(),
                registrationData.password(),
                registrationData.about());
        userRepository.add(user);
        return Optional.of(user);
    }

    public Optional<User> login(LoginData loginData) {
        return userRepository.userWithMatchingCredentials(loginData);
    }

    public List<User> allUsers() {
        return userRepository.allUsers();
    }

    public void create(Following following) throws InvalidUserException {
        Optional<User> follower = userRepository.userFor(following.followerId());
        Optional<User> followee = userRepository.userFor(following.followeeId());
        if (!follower.isPresent() || !followee.isPresent())
            throw new InvalidUserException();
        userRepository.add(following);
    }

    public Optional<User> userFor(String userId) {
        return userRepository.userFor(userId);
    }

    public List<User> followeesFor(String userId) {
        return userRepository.followeesFor(userId);
    }
}
