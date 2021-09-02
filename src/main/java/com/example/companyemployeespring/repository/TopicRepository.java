package com.example.companyemployeespring.repository;

import com.example.companyemployeespring.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    List<Topic> findAllByEmployee_Company_Id(int id);
}
