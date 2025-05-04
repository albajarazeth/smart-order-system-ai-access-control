package com.smart_order_system.orders_system_be.Parts;



import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
    Part findByPartName(String partName);
}