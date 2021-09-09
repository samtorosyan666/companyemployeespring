package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Company;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CompanyService;
import com.example.companyemployeespring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;

    @GetMapping("/companies")
    public String getCompanies(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Company> all = companyService.findAll();
        modelMap.addAttribute("companies", all);
        log.info("Employee with {} name opened companies page, company.size = {}", currentUser.getEmployee().getEmail(), all.size());
        return "companies";
    }

    @GetMapping("/addCompany")
    public String addCompany() {

        return "addCompany";
    }

    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute Company company,@AuthenticationPrincipal CurrentUser currentUser) {
        companyService.save(company);
        log.info("Admin with {} name added the << {} >> company", currentUser.getEmployee().getEmail(), company.getName());
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany/{id}")
    @Transactional
    public String deleteCompany(@PathVariable("id") int id,@ModelAttribute Company company,@AuthenticationPrincipal CurrentUser currentUser) {
        employeeService.deleteAllByCompanyId(id);
        companyService.deleteById(id);
        log.info("Admin with {} name deleted the << {} >> company", currentUser.getEmployee().getEmail(), company.getName());
        return "redirect:/companies";
    }
}
