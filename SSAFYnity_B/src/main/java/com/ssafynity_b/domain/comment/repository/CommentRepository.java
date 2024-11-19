package com.ssafynity_b.domain.comment.repository;

import com.ssafynity_b.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
