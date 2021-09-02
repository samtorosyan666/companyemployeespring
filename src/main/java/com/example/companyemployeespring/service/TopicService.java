package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Topic;
import com.example.companyemployeespring.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public List<Topic> findAllByCompanyId(int id){
        return topicRepository.findAllByEmployee_Company_Id(id);
    }
    public Optional<Topic> findTopicById(int id){
        return topicRepository.findById(id);
    }

    public void saveTopic(Topic topic){
        topicRepository.save(topic);
    }
}
