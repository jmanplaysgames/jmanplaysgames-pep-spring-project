package com.example.service;


import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
public class AccountService {


    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Transactional
    public Account addAccount(Account currentAccount){
        if(accountRepository.getById(currentAccount.getAccountId()) != null){
            return null; // or anything you want to do if record is already exists.
        }else{
            return accountRepository.save(currentAccount);
        }
    }
}
