package com.example.evies_car_dealership.controllers;

import com.example.evies_car_dealership.dao.LeaseDao;
import com.example.evies_car_dealership.model.LeaseContract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final LeaseDao leaseDao;

    public LeaseController(LeaseDao leaseDao) {
        this.leaseDao = leaseDao;
    }

    @PostMapping
    public ResponseEntity<String> createLease(@RequestBody LeaseContract contract) {
        try {
            leaseDao.save(contract);
            return new ResponseEntity<>("Lease recorded", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to record lease", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<LeaseContract>> getAllLeases() {
        return new ResponseEntity<>(leaseDao.getAll(), HttpStatus.OK);
    }
}
