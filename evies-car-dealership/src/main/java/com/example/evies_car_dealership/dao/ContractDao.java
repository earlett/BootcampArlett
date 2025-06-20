package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.LeaseContract;
import com.example.evies_car_dealership.model.SalesContract;

import java.util.List;

public interface ContractDao {

    void saveSalesContract(SalesContract contract);
    void saveLeaseContract(LeaseContract contract);

    List<SalesContract> getSalesContractsByDealershipId(int dealershipId);
    List<LeaseContract> getLeaseContractsByDealershipId(int dealershipId);
}
