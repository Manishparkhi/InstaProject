package com.example.instagramTest.repository;

import com.example.instagramTest.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
