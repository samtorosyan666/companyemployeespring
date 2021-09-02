package com.example.companyemployeespring.repository;


import com.example.companyemployeespring.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByTopic_id(int id);
}
