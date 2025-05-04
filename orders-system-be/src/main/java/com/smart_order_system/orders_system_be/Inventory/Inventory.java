package com.smart_order_system.orders_system_be.Inventory;

import com.smart_order_system.orders_system_be.Parts.Part;

import java.util.List;

import jakarta.persistence.*;


import java.util.ArrayList;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "inventory")
    private List<Part> inventory = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Part> getInventory() {
        return inventory;
    }

    public void setInventory(List<Part> inventory) {
        this.inventory = inventory;
    }

    public void addPart(Part part) {
        part.setInventory(this);
        this.inventory.add(part);
    }
}
