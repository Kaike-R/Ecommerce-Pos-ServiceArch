package br.com.kaikedev.userservice.Controller;

import br.com.kaikedev.userservice.Entity.UserEntity;
import br.com.kaikedev.userservice.Service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Collection<UserEntity>> getAllUsers() {
        Collection<UserEntity> users = userService.getAllUsers();
        log.info("Get all users" + users);
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<?> getUserById(Long id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserEntity> getUserByUsername(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity userList) {

        Boolean x = userService.insertUser(userList);
        return ResponseEntity.ok(x);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user) {
        log.info("Update user" + user);
        Integer modUser = userService.updateUser(user);
        return ResponseEntity.ok(modUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@RequestParam Integer id) {

        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
