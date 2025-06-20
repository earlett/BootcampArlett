package com.example.evies_car_dealership.controllers;

import com.example.evies_car_dealership.dao.LeaseDao;
import com.example.evies_car_dealership.dao.SalesDao;
import com.example.evies_car_dealership.model.LeaseContract;
import com.example.evies_car_dealership.model.SalesContract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final SalesDao salesDao;
    private final LeaseDao leaseDao;

    @Autowired
    public ContractController(SalesDao salesDao, LeaseDao leaseDao) {
        this.salesDao = salesDao;
        this.leaseDao = leaseDao;
    }

    @PostMapping("/sales")
    public ResponseEntity<String> createSalesContract(@RequestBody SalesContract contract) {
        try {
            salesDao.save(contract);
            return new ResponseEntity<>("Sales contract created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save sales contract", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SalesContract>> getAllSalesContracts() {
        return new ResponseEntity<>(salesDao.getAll(), HttpStatus.OK);
    }

    @PostMapping("/leases")
    public ResponseEntity<String> createLeaseContract(@RequestBody LeaseContract contract) {
        try {
            leaseDao.save(contract);
            return new ResponseEntity<>("Lease contract created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save lease contract", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/leases")
    public ResponseEntity<List<LeaseContract>> getAllLeaseContracts() {
        return new ResponseEntity<>(leaseDao.getAll(), HttpStatus.OK);
    }
}