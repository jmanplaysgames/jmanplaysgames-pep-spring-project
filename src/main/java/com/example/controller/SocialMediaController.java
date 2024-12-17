package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.*;



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
        if(obtainedAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }else{
            return ResponseEntity.status(200).body(obtainedAccount);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity postMessage(@RequestBody Message message){
        try{
            Account obtainedAccount = accountService.getAccountById(message.getPostedBy());
            System.out.println(obtainedAccount);
            if(obtainedAccount != null){
                Message postedMessage = messageService.addMessage(message);
                if(postedMessage != null){
                    return ResponseEntity.status(200).body(postedMessage);
                }
            }
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(400).body("Client error"); 
        }

        return ResponseEntity.status(400).body("Client error"); 
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageById(@PathVariable Integer messageId){
        try {
            Message obtainedMessage = messageService.getMessageById(messageId);
            if(obtainedMessage == null){
                return ResponseEntity.status(200).body("");
            }
            return ResponseEntity.status(200).body(obtainedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
        
    }
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable Integer messageId){
        try {
            messageService.deleteMessageById(messageId);
            return ResponseEntity.status(200).body(1);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
        
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMessageById(@PathVariable Integer messageId, @RequestBody Message message){
        try {
            if(message.getMessageText().length() <= 255 && !message.getMessageText().equals("")){
                messageService.updateMessageById(messageId,message);
                return ResponseEntity.status(200).body(1);
            }
            return ResponseEntity.status(400).body("");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("");
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getAllMessagesByAccount(@PathVariable Integer accountId){
        try {
            List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
            return ResponseEntity.status(200).body(messages);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
    }

}

