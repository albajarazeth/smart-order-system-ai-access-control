package com.smart_order_system.orders_system_be.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddressAndPassword(String emailAddress, String password);
}
