package coding.gabs.todolist.controller;

import coding.gabs.todolist.entity.User;
import coding.gabs.todolist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userCreated = userService.createUser(user);
        URI uriOfTheNewUserCreated = URI.create("/user/" + userCreated.getId());
        return ResponseEntity.created(uriOfTheNewUserCreated).body(userCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByUsername(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
