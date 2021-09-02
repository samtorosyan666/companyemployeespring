package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    void deleteAllByCompanyId(int id);

    Optional<Employee> findByEmail(String email);

    List<Employee> findEmployeeByCompanyId(int companyId);
}
