package com.smart_order_system.orders_system_be.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailRepository emailRepository;

    @PostMapping
    public ResponseEntity<Email> createEmail(@RequestBody Email email){
        Email createdEmail = emailRepository.save(email);
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