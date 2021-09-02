package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CommentService;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;
    private final CommentService commentService;

    @GetMapping("/addTopic")
    public String addTopic() {
        return "addTopic";
    }

    @GetMapping("/topics")
    public String viewTopics(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Topic> all = topicService.findAllByCompanyId(currentUser.getEmployee().getCompany().getId());
        modelMap.addAttribute("topics", all);
        return "topics";
    }

    @PostMapping("/addTopic")
    public String addTopic(@AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute Topic topic) {
        topic.setCreatedDate(new Date());
        topic.setEmployee(currentUser.getEmployee());
        topicService.saveTopic(topic);
        return "redirect:/topics";

    }

    @GetMapping("/topics/{id}")
    public String showMore(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Topic> topic = topicService.findTopicById(id);
        if (topic.isEmpty()) {
            return "redirect:/topics";
        }
        List<Comment> comments = commentService.getAllCommentsByTopicId(id);
        modelMap.addAttribute("comments", comments);
        modelMap.addAttribute("topic", topic.get());
        return "singleTopic";
    }

}
