package com.afs.restapi.service.mapper;

import com.afs.restapi.entity.Company;
import com.afs.restapi.service.dto.CompanyRequest;
import com.afs.restapi.service.dto.CompanyResponse;
import org.springframework.beans.BeanUtils;

public class CompanyMapper {

    public static Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest,company);
        return company;
    }

    //TODO: no need to check if employees.isEmpty() as it is redundant with employees.size()
    public static CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company,companyResponse);
        var employees = company.getEmployees();
        companyResponse.setEmployeesCount((employees == null || employees.isEmpty() )? 0: employees.size());
        return companyResponse;
    }
}
