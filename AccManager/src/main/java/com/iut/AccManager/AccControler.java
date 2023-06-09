package com.iut.AccManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

@RestController
public class AccControler {
    @Autowired
    private AccountRepository repository;

    public AccControler(AccountRepository repository){
        this.repository = repository;
        repository.save(new Account(2000, "Low"));
        repository.save(new Account(2000, "High"));
        repository.save(new Account(2000, "High"));
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
    public @ResponseBody EntityModel<Account> account(@PathVariable long id){
        for(Account acc : repository.findAll()){
            if(acc.getId() == id){
                return EntityModel.of(acc,
                        linkTo(methodOn(AccControler.class).accounts()).withRel("See all accounts"));
            }
        }
        return null;
    }

    @PutMapping(value= "/accounts/account/{id}/{somme}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EntityModel<Account> addMoney(@PathVariable long id, @PathVariable int somme){
        for(Account acc : repository.findAll()){
            if(acc.getId() == id){
                acc.setSomme(acc.getSomme()+somme);
                repository.save(acc);
                return EntityModel.of(acc,
                        linkTo(methodOn(AccControler.class).accounts()).withRel("See all accounts"),
                        linkTo(methodOn(AccControler.class).account(id)).withRel("See the changed account"));
            }
        }
        return null;
    }
}
