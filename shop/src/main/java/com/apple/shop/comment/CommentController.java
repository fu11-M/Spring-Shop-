package com.apple.shop.comment;

import com.apple.shop.CustomUser;
import com.apple.shop.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Integer parent,
                       Authentication authentication){
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        var data = new Comment();
        data.setComment(content);
        data.setUsername(customUser.getUsername());
        System.out.println(customUser.getUsername());
        data.setParentId(parent);
        commentRepository.save(data);
        return "redirect:/list";
        }


    @GetMapping("/comments")
    String getComments(@RequestParam Integer parentId, Model model) {
        List<Comment> comments = commentRepository.findAllByParentId(parentId);
        model.addAttribute("comments", comments);
        return "detail.html";
    }
}
