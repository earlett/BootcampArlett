package com.example.evies_car_dealership.controllers;

import com.example.evies_car_dealership.dao.CustomerDao;
import com.example.evies_car_dealership.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /* ── GET ALL ── */
    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerDao.getAll());
    }

    /* ── GET BY EMAIL ── */
    @GetMapping("/{email}")
    public ResponseEntity<Customer> getByEmail(@PathVariable String email) {
        return customerDao.getByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /* ── CREATE ── */
    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer body) {
        int id = customerDao.getOrCreate(body);
        if (id > 0) {
            body = new Customer(id, body.getFullName(), body.getEmail(),
                    body.getPhone(), body.getAddress());
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /* ── UPDATE ── */
    @PutMapping("/{email}")
    public ResponseEntity<Void> update(@PathVariable String email,
                                       @RequestBody Customer body) {
        if (customerDao.getByEmail(email).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Customer upd = new Customer(0, body.getFullName(), email,
                body.getPhone(), body.getAddress());

        return customerDao.update(upd)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /* ── DELETE ── */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        return customerDao.deleteByEmail(email)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
