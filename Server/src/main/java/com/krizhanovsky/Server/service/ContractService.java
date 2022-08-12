package com.krizhanovsky.Server.service;

import com.krizhanovsky.Server.dao.ContractDAO;
import com.krizhanovsky.Server.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    final ContractDAO contractDAO;

    public ContractService(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    @Transactional
    public List<Contract> getAllContracts(){
        return contractDAO.getAllContracts();
    }

    @Transactional
    public void addContract(Contract contract){
        System.out.println("SERVICE" + contract);
        contractDAO.addContract(contract);
    }
}
