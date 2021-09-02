package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void save(Company company) {
        companyRepository.save(company);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company getById(int id) {
        return companyRepository.getById(id);
    }


    public void deleteById(int id) {
        companyRepository.deleteById(id);
    }

    public Company findCompanyById(int companyId) {
        return companyRepository.getById(companyId);
    }
}

