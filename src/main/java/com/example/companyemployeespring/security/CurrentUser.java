package com.example.companyemployeespring.security;

import com.example.companyemployeespring.model.Employee;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {


    private final Employee employee;

    public CurrentUser(Employee employee) {
        super(employee.getEmail(), employee.getPassword(), AuthorityUtils.createAuthorityList(employee.getEmployeeType().name()));
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
