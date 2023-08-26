package com.afs.restapi.service.dto;

public class EmployeeRequest {
    private String name;
    private int age;
    private String gender;
    private int salary;
    private Long companyId;

    public EmployeeRequest(String name, int age, String gender, int salary, Long companyId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public int getSalary() {
        return salary;
    }

    public Long getCompanyId() {
        return companyId;
    }
}


