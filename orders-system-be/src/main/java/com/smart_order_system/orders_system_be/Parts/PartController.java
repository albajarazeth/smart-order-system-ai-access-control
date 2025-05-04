package com.smart_order_system.orders_system_be.Parts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/part")
@CrossOrigin(origins = "*")
public class PartController {

    @Autowired
    private PartRepository partRepository;

    @PostMapping
    public ResponseEntity<Part> createPart(@RequestBody Part part){
        Part createdPart = partRepository.save(part);
        return new ResponseEntity<>(createdPart, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Part> getAllParts(){
        return partRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id){
        return partRepository.findById(id)
                .map(part -> new ResponseEntity<>(part, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{partName}")
    public ResponseEntity<Part> getPartByName(@PathVariable String partName){
        Part part = partRepository.findByPartName(partName);  // Use findByPartName() here
        return part != null ?
                new ResponseEntity<>(part, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
