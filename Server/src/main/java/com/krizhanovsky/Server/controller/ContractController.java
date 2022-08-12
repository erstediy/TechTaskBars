package com.krizhanovsky.Server.controller;

import com.krizhanovsky.Server.entity.Contract;
import com.krizhanovsky.Server.service.ContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public List<Contract> getAllContracts(){
        return contractService.getAllContracts();
    }

    @PostMapping
    public void addContract(@RequestBody Contract contract){
        System.out.println("Controller" + contract);
        contractService.addContract(contract);
    }

}
