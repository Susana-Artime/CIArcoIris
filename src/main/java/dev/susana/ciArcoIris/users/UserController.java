package dev.susana.ciArcoIris.users;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

     @PostMapping (path = "admin/register")
     public ResponseEntity<User> createUser(@RequestBody User user) {
         User newUser = userService.createUser(user);
         return ResponseEntity.ok(newUser);
     }

     @GetMapping (path = "admin/users")
     public ResponseEntity<List<User>> getAllUsers() {
         List<User> users = userService.getUsers();
         return ResponseEntity.ok(users);
     }

     @GetMapping (path = "admin/users/{id}")
     public ResponseEntity<User> getUserById(@PathVariable Long id) {
         return userService.getUserById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
     }
 
     @PutMapping (path = "admin/users/{id}")
     public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
        
     }

     
    @DeleteMapping (path = "admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    boolean deleted = userService.deleteUser(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    public Optional<User> validateUser(String email, String password) {
        return userService.validateUser(email,password);
    }

}

    



