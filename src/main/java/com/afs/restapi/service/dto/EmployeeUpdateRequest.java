package com.afs.restapi.service.dto;

public class EmployeeUpdateRequest {

    private final String name;

    private final int age;
    private final String gender;
    private final int salary;
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


