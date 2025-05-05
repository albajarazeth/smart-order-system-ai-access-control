package com.smart_order_system.orders_system_be.User;

import org.springframework.beans.factory.annotation.Autowired;
import com.smart_order_system.orders_system_be.Email.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository UserRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User User){
        User createdUser = UserRepository.save(User);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        String email = loginRequest.getEmailAddress();
        String password = loginRequest.getPassword();
    
        Optional<User> user = UserRepository.findByEmailAddressAndPassword(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("User exists. ID: " + user.get().getId());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }
    
    @GetMapping
    public List<User> getAllUser() {
        return UserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return UserRepository.findById(id)
                .map(User -> new ResponseEntity<>(User, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/emails")
    public ResponseEntity<List<Email>> getUserEmailsByUserId(@PathVariable Long id) {
        Optional<User> userOptional = UserRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Email> emails = user.getEmails();
            return new ResponseEntity<>(emails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
