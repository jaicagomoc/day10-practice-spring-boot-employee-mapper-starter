package com.afs.restapi.service;

import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.EmployeeNotFoundException;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.service.dto.EmployeeRequest;
import com.afs.restapi.service.dto.EmployeeResponse;
import com.afs.restapi.service.mapper.EmployeeMapper;
import com.afs.restapi.service.mapper.EmployeeUpdateMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public EmployeeResponse update(Long id, EmployeeRequest employeeRequest) {
        Employee toBeUpdatedEmployee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employeeRequest.getSalary() != 0) {
            toBeUpdatedEmployee.setSalary(employeeRequest.getSalary());
        }
        if (employeeRequest.getAge() != 0) {
            toBeUpdatedEmployee.setAge(employeeRequest.getAge());
        }
        Employee updatedEmployee = EmployeeMapper.toEntity((employeeRequest));
        return EmployeeUpdateMapper.toResponse( employeeRepository.save(updatedEmployee));
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Employee employee = EmployeeMapper.toEntity(employeeRequest);
        return EmployeeMapper.toReponse(employeeRepository.save(employee));
    }

    public List<Employee> findByPage(Integer pageNumber, Integer pageSize) {
        Page<Employee> employeesInThePage = employeeRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
        return employeesInThePage.stream().collect(Collectors.toList());
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
