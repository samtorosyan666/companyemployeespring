package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.model.Comment;
import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.security.CurrentUser;
import com.example.companyemployeespring.service.CommentService;
import com.example.companyemployeespring.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TopicController {
    private final TopicService topicService;
    private final CommentService commentService;

    @GetMapping("/addTopic")
    public String addTopic(@AuthenticationPrincipal CurrentUser currentUser) {
        log.info("User with {} name opened the Add topic page", currentUser.getEmployee().getEmail());
        return "addTopic";
    }

    @GetMapping("/topics")
    public String viewTopics(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<Topic> all = topicService.findAllByCompanyId(currentUser.getEmployee().getCompany().getId());
        modelMap.addAttribute("topics", all);
        log.info("User with {} name opened the All topics page", currentUser.getEmployee().getEmail());
        return "topics";
    }

    @PostMapping("/addTopic")
    public String addTopic(@AuthenticationPrincipal CurrentUser currentUser, @ModelAttribute Topic topic) {
        topic.setCreatedDate(new Date());
        topic.setEmployee(currentUser.getEmployee());
        topicService.saveTopic(topic);
        log.info("User with {} name added a topic with << {} >> name ", currentUser.getEmployee().getEmail(),topic.getName());
        return "redirect:/topics";

    }

    @GetMapping("/topics/{id}")
    public String showMore(@PathVariable("id") int id, ModelMap modelMap,@AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Topic> topic = topicService.findTopicById(id);
        if (topic.isEmpty()) {
            return "redirect:/topics";
        }
        List<Comment> comments = commentService.getAllCommentsByTopicId(id);
        modelMap.addAttribute("comments", comments);
        modelMap.addAttribute("topic", topic.get());
        log.info("User with {} name opened the Single topic page", currentUser.getEmployee().getEmail());
        return "singleTopic";
    }

}
