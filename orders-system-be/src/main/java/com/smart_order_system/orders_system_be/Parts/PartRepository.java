package com.smart_order_system.orders_system_be.Parts;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartRepository extends JpaRepository<Part, Long> {
    @Query("SELECT i FROM Part i WHERE i.partName LIKE %:partName%")
    Part findByName(@Param("partName") String partName);
}
