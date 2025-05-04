package com.smart_order_system.orders_system_be.Inventory;

import com.smart_order_system.orders_system_be.Parts.Part;
import com.smart_order_system.orders_system_be.Parts.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PartRepository partRepository;

    @PostMapping("/init")
    public Inventory createInventoryIfNotExists() {
        return inventoryRepository.findById(1L).orElseGet(() -> inventoryRepository.save(new Inventory()));
    }

    @PostMapping("/add-part")
    public ResponseEntity<Inventory> addPartToInventory(@RequestBody Part part) {
        Inventory inventory = inventoryRepository.findById(1L).orElse(null);
        if (inventory == null) {
            return ResponseEntity.notFound().build();
        }

        Part existingPart = partRepository.findByPartName(part.getPartName());
        if (existingPart != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        part.setInventory(inventory);
        partRepository.save(part);

        inventory.getInventory().add(part);
        inventoryRepository.save(inventory);

        return ResponseEntity.ok(inventory);
    }


    @GetMapping("/parts")
    public ResponseEntity<List<Part>> getAllInventoryParts() {
        Inventory inventory = inventoryRepository.findById(1L).orElse(null);
        if (inventory == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(inventory.getInventory());
    }

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetInventory() {
        Inventory inventory = inventoryRepository.findById(1L).orElse(null);
        if (inventory == null) {
            return new ResponseEntity<>("Inventory not found", HttpStatus.NOT_FOUND);
        }

        inventory.getInventory().clear();

        inventoryRepository.save(inventory);

        return new ResponseEntity<>("Inventory has been reset", HttpStatus.OK);
    }

}