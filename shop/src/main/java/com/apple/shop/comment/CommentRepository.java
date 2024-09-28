package com.apple.shop.comment;

import com.apple.shop.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByParentId(Integer parentId);
}
