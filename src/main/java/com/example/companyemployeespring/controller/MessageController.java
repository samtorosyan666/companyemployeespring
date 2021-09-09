package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.EmployeeService;
import com.example.companyemployeespring.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final EmployeeService employeeService;
    private final MessageService messageService;

    @GetMapping("/sendMessage")
    public String getAllEmployees(ModelMap modelMap,@AuthenticationPrincipal CurrentUser currentUser) {
        List<Employee> all = employeeService.findAll();
        modelMap.addAttribute("employees", all);
        log.info("User with {} name opened the Send messages page", currentUser.getEmployee().getEmail());
        return "messages";
    }

    @GetMapping("/allMessages")
    public String getAllMessages(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Message> all = messageService.findAllMessagesByToId(currentUser.getEmployee().getId());
        modelMap.addAttribute("messages", all);
        log.info("User with {} name opened the All messages page", currentUser.getEmployee().getEmail());
        return "showMessages";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@ModelAttribute Message message, @AuthenticationPrincipal CurrentUser currentUser) {
        message.setFromEmployee(currentUser.getEmployee());
        messageService.saveMessage(message);
        log.info("User with {} name sent a message", currentUser.getEmployee().getEmail());
        return "redirect:/employees";
    }

}
