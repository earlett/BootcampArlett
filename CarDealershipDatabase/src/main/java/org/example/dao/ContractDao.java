package org.example.dao;

import org.example.models.LeaseContract;
import org.example.models.SalesContract;

import java.util.List;

public interface ContractDao {

    //Commands
    void saveSalesContract(SalesContract contract);
    void saveLeaseContract(LeaseContract contract);

    //Queries
    List<SalesContract> getSalesContractsByDealershipId(int dealershipId);
    List<LeaseContract> getLeaseContractsByDealershipId(int dealershipId);
}
