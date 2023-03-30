package com.iut.AppManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApprovalController {
    @Autowired
    private ApprovalRepository reposiroty;

    public ApprovalController(ApprovalRepository repository){
        this.reposiroty = repository;
        this.reposiroty.save(new Approval(1L, "approved"));
        this.reposiroty.save(new Approval(2L, "refused"));
        this.reposiroty.save(new Approval(3L, "approved"));
        this.reposiroty.save(new Approval(4L, "refused"));
    }

    @GetMapping("/")
    @ResponseBody String helloWorld(){
        return "Hello World !";
    }

    @GetMapping("/approvals")
    @ResponseBody List<Approval> approvals(){
        return reposiroty.findAll();
    }

    @DeleteMapping("/approvals/approval/{id}")
    @ResponseBody Approval deleteApproval(@PathVariable int id){
        Approval out = null;
        for(Approval app : reposiroty.findAll()){
            if(app.getId() == id){
                out = app;
                reposiroty.delete(app);
            }
        }
        return out;
    }

    @PostMapping("/approvals")
    @ResponseBody Approval addApproval(@RequestBody Approval app){
        reposiroty.save(app);
        return app;
    }

    @GetMapping("/approvals/approval/{id}")
    @ResponseBody Approval getApproval(@PathVariable int id){
       for(Approval app : reposiroty.findAll()) {
           if(app.getId() == id){
               return app;
           }
       }
       return null;
    }
}
