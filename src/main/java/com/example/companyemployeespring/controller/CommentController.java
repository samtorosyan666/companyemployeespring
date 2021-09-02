package com.example.companyemployeespring.controller;
import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Employee;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CommentService;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final TopicService topicService;

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute Comment comment, @AuthenticationPrincipal CurrentUser currentUser) throws ParseException {
        comment.setEmployee(currentUser.getEmployee());
        comment.setCreatedDate(new Date());
        commentService.saveComment(comment);
        return "redirect:/topics/" + comment.getTopic().getId();
    }

    @GetMapping("/deleteComment/{id}")
    @Transactional
    public String deleteComment(@PathVariable("id") int id,@ModelAttribute Comment comment,@AuthenticationPrincipal CurrentUser currentUser) {
        Employee employee = comment.getEmployee();

        if (currentUser.getEmployee().getId() == employee.getId()){
            commentService.deleteCommentById(id);

        }
        return "redirect:/topics/" + comment.getTopic().getId();
}
}
