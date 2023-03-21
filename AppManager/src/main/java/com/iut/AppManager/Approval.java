package com.iut.AppManager;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Approval {
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="response")
    private String response;

    public Approval() {}

    public Approval(Long id, String response) {
        this.id = id;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
