package com.afs.restapi.service.dto;

public class EmployeeUpdateRequest {



    private final int age;
    private final int salary;
    public EmployeeUpdateRequest(  int age, int salary) {


        this.age = age;

        this.salary = salary;
    }


    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }
}


