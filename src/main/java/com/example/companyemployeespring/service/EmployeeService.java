package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MailService mailService;

    @Value("${site.url}")
    private String siteUrl;

    public void save(Employee employee, Locale locale) {
        employeeRepository.save(employee);
        try {
            sendVerificationEmail(employee,locale);
        } catch (MessagingException e) {
    log.error(e.getMessage());
        }
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

    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email).orElse(null);
    }

    public void sendVerificationEmail(Employee employee, Locale locale) throws MessagingException {

        UUID uuid = UUID.randomUUID();
        employee.setToken(uuid);
        employeeRepository.save(employee);
        String verifyUrl = siteUrl + "/verifyEmail?email=" + employee.getEmail() + "&token="+ uuid;
//        mailService.send(employee.getEmail(),"Verify your account","Hi " + employee.getName() + ", \n" +
//                String.format("Please verify your account by clicking on %s ",verifyUrl)
//        );
        mailService.sendHtmlEmail(employee.getEmail(),"Verify your account",employee,verifyUrl,"verifyTemplate", locale);
    }

    public void verifyUser(String email, String token) {
        Employee byEmail = findByEmail(email);
        if (byEmail!=null && byEmail.getToken().equals(UUID.fromString(token))){
            byEmail.setEmailVerified(true);
            byEmail.setToken(null);
            employeeRepository.save(byEmail);
        }
    }
}
