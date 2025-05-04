package com.smart_order_system.orders_system_be.Parts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart_order_system.orders_system_be.Inventory.Inventory;
import jakarta.persistence.*;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partName;
    private String partType;
    private String imageUrl;
    private Double price;
    private Integer itemsAvailable;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    @JsonIgnore
    private Inventory inventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getItemsAvailable() {
        return itemsAvailable;
    }

    public void setItemsAvailable(Integer itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
