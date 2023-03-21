package com.iut.AccManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccControler {
    @Autowired
    private AccountRepository repository;

    public AccControler(AccountRepository repository){
        this.repository = repository;
        repository.save(new Account(2000, "Low"));
    }

    @GetMapping("/")
    public @ResponseBody String HelloWorld(){
        return "Hello World !";
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Account> accounts(){
        return repository.findAll();
    }

    @PostMapping(value = "/accounts", headers = "Accept=application/json")
    public @ResponseBody Account addAccounts(@RequestBody Account acc){
        repository.save(acc);
        return acc;
    }

    @GetMapping(value = "/accounts/account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Account account(@PathVariable int id){
        for(Account acc : repository.findAll()){
            if(acc.getId() == id){
                return acc;
            }
        }
        return null;
    }
}
