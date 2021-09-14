package com.example.companyemployeespring;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.EmployeeType;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class CompanyEmployeeSpringApplication implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CompanyEmployeeSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!employeeRepository.findByEmail("admin").isPresent()) {

            Company company = companyRepository.save(Company.builder()
                    .name("Default Company")
                    .address("Default Address")
                    .build());

            employeeRepository.save(Employee.builder()
                    .email("admin")
                    .phoneNumber("+37441824704")
                    .surname("admin")
                    .name("admin")
                    .password(passwordEncoder.encode("admin"))
                    .position("admin")
                    .employeeType(EmployeeType.ADMIN)
                    .company(company)
                    .build());
        }
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}