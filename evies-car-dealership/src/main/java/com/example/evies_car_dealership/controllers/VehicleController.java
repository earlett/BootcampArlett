package com.example.evies_car_dealership.controllers;

import com.example.evies_car_dealership.dao.VehicleDao;
import com.example.evies_car_dealership.model.Vehicle;
import com.example.evies_car_dealership.model.VehicleType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleDao vehicleDao;

    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    /* ─────────── GET all (with filters) ─────────── */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer minMiles,
            @RequestParam(required = false) Integer maxMiles,
            @RequestParam(required = false) VehicleType type) {

        if (minPrice != null && maxPrice != null)
            return ResponseEntity.ok(vehicleDao.searchByPriceRange(minPrice, maxPrice));

        if (make != null && model != null)
            return ResponseEntity.ok(vehicleDao.searchByMakeModel(make, model));

        if (minYear != null && maxYear != null)
            return ResponseEntity.ok(vehicleDao.searchByYearRange(minYear, maxYear));

        if (color != null)
            return ResponseEntity.ok(vehicleDao.searchByColor(color));

        if (minMiles != null && maxMiles != null)
            return ResponseEntity.ok(vehicleDao.searchByMileageRange(minMiles, maxMiles));

        if (type != null)
            return ResponseEntity.ok(vehicleDao.searchByType(type.name()));

        return ResponseEntity.ok(vehicleDao.getAllVehicles());
    }

    /* ─────────── GET by VIN ─────────── */
    @GetMapping("/{vin}")
    public ResponseEntity<Vehicle> getByVin(@PathVariable int vin) {
        Optional<Vehicle> v = vehicleDao.getVehicleByVin(vin);
        return v.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /* ─────────── POST create ─────────── */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Vehicle body) {
        try {
            vehicleDao.addVehicle(body);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle created");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Creation failed");
        }
    }

    /* ─────────── PUT update ─────────── */
    @PutMapping("/{vin}")
    public ResponseEntity<String> update(@PathVariable int vin, @RequestBody Vehicle body) {

        if (vehicleDao.getVehicleByVin(vin).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VIN not found");

        Vehicle updated = new Vehicle(
                String.valueOf(vin),         // force VIN in URL
                body.getYear(),
                body.getMake(),
                body.getModel(),
                body.getColor(),
                body.getType(),              // <- correct accessor
                body.getOdometer(),
                body.getPrice()
        );

        try {
            vehicleDao.update(updated);
            return ResponseEntity.ok("Vehicle updated");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
        }
    }

    /* ─────────── DELETE ─────────── */
    @DeleteMapping("/{vin}")
    public ResponseEntity<Void> delete(@PathVariable int vin) {
        if (vehicleDao.getVehicleByVin(vin).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        vehicleDao.removeVehicle(vin);
        return ResponseEntity.noContent().build();
    }
}
