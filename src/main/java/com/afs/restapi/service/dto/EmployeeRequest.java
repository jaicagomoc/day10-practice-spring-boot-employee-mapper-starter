package com.afs.restapi.service.dto;

public class EmployeeRequest {
    private String name;
    private int age;
    private String gender;


    public EmployeeRequest( String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

}


