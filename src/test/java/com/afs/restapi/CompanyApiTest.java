package com.afs.restapi;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import com.afs.restapi.service.dto.CompanyRequest;
import com.afs.restapi.service.dto.CompanyUpdateRequest;
import com.afs.restapi.service.mapper.CompanyMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_find_companies() throws Exception {
        Company company = companyRepository.save(getCompanyOOCL());

        mockMvc.perform(get("/companies"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(company.getName()));
    }

    @Test
    void should_find_company_by_id() throws Exception {
        CompanyRequest companyRequest = new CompanyRequest("OOCL");
        Company company = companyRepository.save(CompanyMapper.toEntity(companyRequest));
        ObjectMapper objectMapper = new ObjectMapper();
        String updateCompanyJSON = objectMapper.writeValueAsString(company);

        mockMvc.perform(get("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateCompanyJSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(company.getName()));
    }

    @Test
    void should_update_company_name() throws Exception {
        CompanyRequest companyRequest = new CompanyRequest("Facebook");
        Company toBeUpdateCompany = companyRepository.save(CompanyMapper.toEntity(companyRequest));
        CompanyUpdateRequest companyUpdateRequest = new CompanyUpdateRequest("Meta");
        ObjectMapper objectMapper = new ObjectMapper();
        String updateCompanyJSON = objectMapper.writeValueAsString(companyUpdateRequest);
        mockMvc.perform(put("/companies/{id}", toBeUpdateCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateCompanyJSON))
                .andExpect(MockMvcResultMatchers.status().is(204));

        Optional<Company> optionalCompany = companyRepository.findById(toBeUpdateCompany.getId());
        assertTrue(optionalCompany.isPresent());
        Company updatedCompanyById = optionalCompany.get();
        Assertions.assertEquals(toBeUpdateCompany.getId(), updatedCompanyById.getId());
        Assertions.assertEquals(companyUpdateRequest.getName(), updatedCompanyById.getName());
    }

    @Test
    void should_delete_company_name() throws Exception {
        Company company = companyRepository.save(getCompanyGoogle());
        mockMvc.perform(delete("/companies/{id}", company.getId()))
                .andExpect(MockMvcResultMatchers.status().is(204));

        assertTrue(companyRepository.findById(company.getId()).isEmpty());
    }

    @Test
    void should_create_company() throws Exception {
        CompanyRequest companyRequest = new CompanyRequest("OOCL");
        ObjectMapper objectMapper = new ObjectMapper();
        String companyRequestJSON = objectMapper.writeValueAsString(companyRequest);
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyRequestJSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(companyRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeesCount").exists());

    }

    @Test
    void should_find_companies_by_page() throws Exception {
        Company oocl = companyRepository.save(getCompanyOOCL());
        Company thoughtworks = companyRepository.save(getCompanyThoughtWorks());
        Company google = companyRepository.save(getCompanyGoogle());

        mockMvc.perform(get("/companies")
                        .param("pageNumber", "1")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(oocl.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(oocl.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(thoughtworks.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(thoughtworks.getName()));
    }

    @Test
    void should_find_employees_by_companies() throws Exception {
        Company oocl = companyRepository.save(getCompanyOOCL());
        Employee employee = employeeRepository.save(getEmployee(oocl));

        mockMvc.perform(get("/companies/{companyId}/employees", oocl.getId()))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee.getSalary()));
    }

    private static Employee getEmployee(Company company) {
        Employee employee = new Employee();
        employee.setName("Bob");
        employee.setAge(22);
        employee.setGender("Male");
        employee.setSalary(10000);
        employee.setCompanyId(company.getId());
        return employee;
    }


    private static Company getCompanyOOCL() {
        Company company = new Company();
        company.setName("OOCL");
        return company;
    }

    private static Company getCompanyThoughtWorks() {
        Company company = new Company();
        company.setName("Thoughtworks");
        return company;
    }

    private static Company getCompanyGoogle() {
        Company company = new Company();
        company.setName("Google");
        return company;
    }
}