package com.example.companyemployeespring.controller;
import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @GetMapping("/employees")
    public String getEmployees(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Employee> all = employeeService.findEmployeeByCompanyId(currentUser.getEmployee().getCompany().getId());
        modelMap.addAttribute("employees", all);
        log.info("User with {} name opened the employees page,", currentUser.getEmployee().getEmail());

        return "employees";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(ModelMap modelMap) {
        List<Company> all = companyService.findAll();
        modelMap.addAttribute("companies", all);
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee, @AuthenticationPrincipal CurrentUser currentUser, Locale locale) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeService.save(employee,locale);
        Company company = companyService.getById(employee.getCompany().getId());
        company.setSize(company.getSize() + 1);
        companyService.save(company);
        log.info("Admin with {} name added a employee with << {} >> name", currentUser.getEmployee().getEmail(), employee.getEmail());
        mailService.send(employee.getEmail(),"Welcome","Dear "+ employee.getName() + ",you have succesfully registered to our site");
        return "redirect:/employees";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("email") String email, @RequestParam("token") String token){
        employeeService.verifyUser(email,token);
        return "emailVerifySuccess";
    }

}
