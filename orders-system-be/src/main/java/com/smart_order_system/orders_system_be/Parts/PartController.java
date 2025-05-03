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
    private PartRepository PartRepository;

    @PostMapping
    public ResponseEntity<Part> createPart(@RequestBody Part part){
        Part createdPart=PartRepository.save(part);
        return new ResponseEntity<>(createdPart,HttpStatus.CREATED);
    }

    @GetMapping
    public List<Part> getAllParts(){
        return PartRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id){
        return PartRepository.findById(id)
                .map(part -> new ResponseEntity<>(part,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{partName}")
    public Part getPartByName(@PathVariable String partName){
        return PartRepository.findByName(partName);
    }

}