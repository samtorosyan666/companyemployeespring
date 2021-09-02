package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void deleteAllByCompanyId(int id) {
        employeeRepository.deleteAllByCompanyId(id);
    }

    public List<Employee> findEmployeeByCompanyId(int companyId) {
        return employeeRepository.findEmployeeByCompanyId(companyId);
    }

    public Optional<Employee> findEmployeeById(int id) {
        return employeeRepository.findById(id);

    }
}
