package com.smart_order_system.orders_system_be.Parts;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String partName;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String partType;
    /*Examples of part type would be "motor", "propeller", "controller". 
    AI agent will search by this description to find substitutions*/

    public Part() {}

    public Part(String partName, Double price, String imageUrl, String partType ) {
        this.partName = partName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.partType = partType;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpartName() {
        return partName;
    }

    public void setpartName(String partName) {
        this.partName = partName;
    }

    public Double getprice() {
        return price;
    }

    public void setprice(Double price) {
        this.price = price;
    }

    public void setpartType(String partType) {
        this.partType = partType;
    }

    public String getpartType() {
        return partType;
    }

    public String getimageUrl() {
        return imageUrl;
    }

    public void setimageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

