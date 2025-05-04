package com.smart_order_system.orders_system_be.Email;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.smart_order_system.orders_system_be.User.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "emails")
 @JsonIdentityInfo(
     generator = ObjectIdGenerators.PropertyGenerator.class,
     property = "id")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String emailAddress;  

    @Column(nullable = false)
    private String body;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Boolean isSentFromAgent;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

    public Email() {}

    public Email(String subject, String emailAddress, String body, LocalDateTime date, User user, Boolean isSentFromAgent) {
        this.subject = subject;
        this.emailAddress = emailAddress;
        this.body = body;
        this.date = date;
        this.user = user;
        this.isSentFromAgent=isSentFromAgent;
    }

    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean isIsSentFromAgent() {
        return this.isSentFromAgent;
    }

    public void setIsSentFromAgent(Boolean isSentFromAgent) {
        this.isSentFromAgent = isSentFromAgent;
    }
}