package com.bankmgmt.app.rest;

import com.bankmgmt.app.entity.Account;
import com.bankmgmt.app.service.BankService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// TODO: Make this class a Rest Controller
@RestController
@RequestMapping("/accounts")
public class BankController {
	@Autowired
    private BankService bankAccountService;
	
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account request){
    	return bankAccountService.createAccount(request);
    }
    // TODO: API to Get all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
    	return bankAccountService.getAllAccounts();
    }

    // TODO: API to Get an account by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@RequestParam Integer id) {
    	return bankAccountService.getAccountById(id);
    }

    // TODO: API to Deposit money
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Integer id,@RequestBody Account  request){
    	return bankAccountService.deposit(id,request);
    }

    // TODO: API to Withdraw money
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Integer id,@RequestBody Account request){
    	return bankAccountService.withdraw(id,request);
    }

    // TODO: API to Transfer money between accounts
    @PostMapping("/accounts/transfer?fromId={fromId}&toId={toId}&amount={amount}")
    public ResponseEntity<?> transfer(@RequestParam Integer fromId, @RequestParam Integer toId, @RequestParam double amount) {
        return bankAccountService.transfer(fromId, toId, amount);
    }

    // TODO: API to Delete an account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
    	return bankAccountService.deleteAccount(id);
    }
    
    
    
}
