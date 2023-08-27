package com.afs.restapi.service.mapper;

import com.afs.restapi.entity.Company;
import com.afs.restapi.service.dto.CompanyResponse;
import com.afs.restapi.service.dto.CompanyUpdateRequest;
import org.springframework.beans.BeanUtils;

public class CompanyUpdateMapper {
    public static Company toEntity(CompanyUpdateRequest previousCompany) {
        Company company = new Company();
        BeanUtils.copyProperties(previousCompany, company);
        return company;
    }

    public static void toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
    }
}
