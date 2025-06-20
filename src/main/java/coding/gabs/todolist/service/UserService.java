package coding.gabs.todolist.service;

import coding.gabs.todolist.entity.User;
import coding.gabs.todolist.exception.InvalidCredentials;
import coding.gabs.todolist.exception.UserAlreadyExistsInDatabase;
import coding.gabs.todolist.exception.UserNotFound;
import coding.gabs.todolist.repository.UserRepository;
import coding.gabs.todolist.util.PasswordEncryption;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(@NotNull User user) {
        validateIfIsValidNewUser(user);

        String hashedPassword = PasswordEncryption.encryptPassword(user.getPassword());
        user.setPassword(hashedPassword);
        return this.userRepository.save(user);
    }

    private void validateIfIsValidNewUser(User user) {
        Optional<User> userFromDatabase = userRepository.findByUsername(user.getUsername());

        if (userFromDatabase.isPresent()) {
            throw new UserAlreadyExistsInDatabase("The user already exists in the database.");
        }
    }

    public User getUserById(@NotNull UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFound("The user with id '" + id + "' was not found.");
        }

        return user.get();
    }

    public User getUserByUsername(@NotNull String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFound("The user was not found.");
        }

        return user.get();
    }

    public User validateUserCredentials(String username, String password) {
        User user = getUserByUsername(username);

        if (!PasswordEncryption.isPasswordsEquals(password, user.getPassword())) {
            throw new InvalidCredentials("Invalid user password.");
        }

        return user;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFound("Any user was found in the database.");
        }

        return users;
    }
}
