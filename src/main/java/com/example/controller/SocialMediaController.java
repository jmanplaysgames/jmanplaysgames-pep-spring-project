package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.*;

import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Account account){
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict");
        }else{
            return ResponseEntity.status(200).body(addedAccount);
        }
    }
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Account account){
        Account obtainedAccount = accountService.canLogin(account);
        System.out.println(obtainedAccount);
        if(obtainedAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }else{
            return ResponseEntity.status(200).body(obtainedAccount);
        }
    }
}
