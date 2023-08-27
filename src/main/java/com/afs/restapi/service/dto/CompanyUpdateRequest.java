package com.afs.restapi.service.dto;


public class CompanyUpdateRequest {

    private String name;

    public CompanyUpdateRequest( String name) {

        this.name = name;
    }
    public CompanyUpdateRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
