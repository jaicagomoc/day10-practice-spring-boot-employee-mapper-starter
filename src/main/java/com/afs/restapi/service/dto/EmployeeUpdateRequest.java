package com.afs.restapi.service.dto;

public class EmployeeUpdateRequest {

    private String name;

    private int age;
    private String gender;
    private int salary;
    public EmployeeUpdateRequest( String name, int age, String gender, int salary) {

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }


    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }
}


