package com.apple.shop.comment;

import com.apple.shop.CustomUser;
import com.apple.shop.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Integer parent,
                       Authentication authentication){
        CustomUser CustomUser = (CustomUser) authentication.getPrincipal();

        var data = new Comment();
        data.setComment(content);
        data.setUsername(CustomUser.getUsername());
        data.setParentId(parent);
        commentRepository.save(data);
        return "";
        }
}
