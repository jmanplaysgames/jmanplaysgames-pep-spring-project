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

    public Account getAccountById(Integer accountId){
        return accountRepository.getById(accountId);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account addAccount(Account currentAccount){
        if(accountRepository.countByusername(currentAccount.getUsername()) != 0){
            return null;
        }else{
            return accountRepository.save(currentAccount);
        }
    }
    
    public Account canLogin(Account currentAccount){
        try{
            Account account = accountRepository.findByUsernameAndPassword(currentAccount.getUsername(), currentAccount.getPassword());
            if(account.getUsername().equals(currentAccount.getUsername()) && account != null && account.getPassword().equals(currentAccount.getPassword())){
                return account;
            }
        }catch(NullPointerException e){
            return null;
        }
        return null;
        
    }
}
