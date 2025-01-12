package com.afs.restapi.service.mapper;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.service.dto.EmployeeRequest;
import com.afs.restapi.service.dto.EmployeeResponse;
import com.afs.restapi.service.dto.EmployeeUpdateRequest;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {
    public static Employee toEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public static EmployeeResponse toReponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
    public static Employee toEntity(EmployeeUpdateRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public static void toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
    }
}
