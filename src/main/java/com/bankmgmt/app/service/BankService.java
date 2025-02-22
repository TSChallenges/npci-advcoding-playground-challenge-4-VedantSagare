package com.bankmgmt.app.service;

import com.bankmgmt.app.entity.Account;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;


public class BankService {

    private List<Account> accounts = new ArrayList<>();
    private Integer currentId = 1;
    
	

    // TODO: Method to Create a new Account
    public ResponseEntity<Account> createAccount(@Valid Account request) {
		if (accounts.stream().anyMatch(acc -> acc.getEmail().equalsIgnoreCase(request.getEmail()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Account account = new Account(request.getId(), request.getAccountHolderName(), request.getAccountType(), request.getBalance(),request.getEmail());
        accounts.add(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

    // TODO: Method to Get All Accounts
    public ResponseEntity<List<Account>> getAllAccounts() {
		// TODO Auto-generated method stub
    	return ResponseEntity.ok(accounts);
	}

    // TODO: Method to Get Account by ID
    public ResponseEntity<Account> getAccountById(Integer id) {
		// TODO Auto-generated method stub
     return accounts.stream().filter(acc->acc.getId()==id).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	public ResponseEntity<Account> deposit(Integer id, Account request) {
		// TODO Auto-generated method stub
		if(request.getBalance()<=0)return ResponseEntity.badRequest().build();
		Optional<Account> accountOpt=accounts.stream().filter(acc->acc.getId()==id).findFirst();
		if(accountOpt.isPresent()) {
			Account account=accountOpt.get();
			account.setBalance(request.getBalance()+account.getBalance());
            return ResponseEntity.ok(account);		
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}

	public ResponseEntity<Account> withdraw(Integer id, Account request) {
        if (request.getBalance() <= 0) return ResponseEntity.badRequest().build();

		Optional<Account> accountOpt=accounts.stream().filter(acc->acc.getId()==id).findFirst();
		if(accountOpt.isPresent()) {
			Account account=accountOpt.get();
			if(account.getBalance()<request.getBalance()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			account.setBalance(account.getBalance()-request.getBalance());
			return ResponseEntity.ok(account);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	public ResponseEntity<?> transfer(Integer fromId, Integer toId, double amount) {
		// TODO Auto-generated method stub
		if(amount<=0)return ResponseEntity.badRequest().build();
        Account fromAccount = accounts.stream().filter(acc -> acc.getId() == fromId).findFirst().orElse(null);
		Account toAccount=accounts.stream().filter(acc-> acc.getId()==toId).findFirst().orElse(null);
		
		 if (fromAccount == null || toAccount == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        if (fromAccount.getBalance() < amount) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
	        }
	        
	     fromAccount.setBalance(fromAccount.getBalance()-amount);
	     toAccount.setBalance(toAccount.getBalance()+amount);
	    return ResponseEntity.ok(Map.of("fromAccount", fromAccount, "toAccount", toAccount));
	}

    public ResponseEntity<Void> deleteAccount(Integer  id){
    	boolean removed=accounts.removeIf(acc->acc.getId()==id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
}
}
