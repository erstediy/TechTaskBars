package com.krizhanovsky.Server.dao;


import com.krizhanovsky.Server.entity.Contract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractDAO {

    private final SessionFactory sessionFactory;

    public ContractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contract> getAllContracts(){
        Session session = sessionFactory.getCurrentSession();
        List<Contract> contracts = session.createQuery("from Contract", Contract.class).getResultList();
        System.out.println(contracts);
        return contracts;
    }

    public void addContract(Contract contract){
        Contract ifExist = null;
        Session session = sessionFactory.getCurrentSession();
        ifExist = (Contract) session.createQuery("from Contract where number=:number").setParameter("number",contract.getNumber()).uniqueResult();
        if(ifExist!=null){
            ifExist.setUpDate(contract.getUpDate());
        }else {
            session.saveOrUpdate(contract);
        }
    }

}
