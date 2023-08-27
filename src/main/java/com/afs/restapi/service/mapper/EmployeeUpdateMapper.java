package com.afs.restapi.service.mapper;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.service.dto.EmployeeUpdateRequest;
import org.springframework.beans.BeanUtils;

public class EmployeeUpdateMapper {
    public static Employee toEntity(EmployeeUpdateRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest,employee);
        return employee;
    }
}
