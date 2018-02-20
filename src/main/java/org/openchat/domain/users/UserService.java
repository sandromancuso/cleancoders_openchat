package org.openchat.domain.users;

import java.util.List;

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

    public List<User> allUsers() {
        return userRepository.all();
    }

    public void addFollowing(Following following) throws FollowingAlreadyExistsException{
        if (userRepository.hasFollowing(following)) {
            throw new FollowingAlreadyExistsException();
        }
        userRepository.add(following);
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
