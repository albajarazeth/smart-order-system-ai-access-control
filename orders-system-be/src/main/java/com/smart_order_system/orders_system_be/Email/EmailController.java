package com.smart_order_system.orders_system_be.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smart_order_system.orders_system_be.EmailAgent.EmailAgent;
import com.smart_order_system.orders_system_be.User.User;
import com.smart_order_system.orders_system_be.User.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailAgent emailAgent;

    @PostMapping
    public ResponseEntity<Email> createEmail(@RequestBody Email email, @RequestParam("userId") Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();
        email.setUser(user);
        Email createdEmail = emailRepository.save(email);
        emailAgent.processEmail(email);
        return new ResponseEntity<>(createdEmail, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Email> getAllEmail() {
        return emailRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Email> getEmailById(@PathVariable Long id){
        return emailRepository.findById(id)
                .map(email -> new ResponseEntity<>(email, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}