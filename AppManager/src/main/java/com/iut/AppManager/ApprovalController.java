package com.iut.AppManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    @ResponseBody EntityModel<Approval> deleteApproval(@PathVariable long id){
        Approval out = null;
        for(Approval app : reposiroty.findAll()){
            if(app.getId() == id){
                out = app;
                reposiroty.delete(app);
            }
        }
        return EntityModel.of(out,
                linkTo(methodOn(ApprovalController.class).approvals()).withRel("approvals"));
    }

    @PostMapping("/approvals")
    @ResponseBody EntityModel<Approval> addApproval(@RequestBody Approval app){
        reposiroty.save(app);
        return EntityModel.of(app,
                linkTo(methodOn(ApprovalController.class).getApproval(app.getId())).withSelfRel(),
                linkTo(methodOn(ApprovalController.class).approvals()).withRel("approvals"));
    }

    @GetMapping("/approvals/approval/{id}")
    @ResponseBody EntityModel<Approval> getApproval(@PathVariable long id){
       for(Approval app : reposiroty.findAll()) {
           if(app.getId() == id){
               return EntityModel.of(app,
                       linkTo(methodOn(ApprovalController.class).getApproval(app.getId())).withSelfRel(),
                       linkTo(methodOn(ApprovalController.class).deleteApproval(app.getId())).withRel("drop"),
                       linkTo(methodOn(ApprovalController.class).approvals()).withRel("approvals"));
           }
       }
       return null;
    }
}
