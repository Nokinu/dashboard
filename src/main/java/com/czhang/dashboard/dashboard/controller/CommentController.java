package com.czhang.dashboard.dashboard.controller;

import com.czhang.dashboard.dashboard.model.Comment;
import com.czhang.dashboard.dashboard.model.CommentType;
import com.czhang.dashboard.dashboard.model.User;
import com.czhang.dashboard.dashboard.service.CommentService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
@Log
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public String getAllCommentsForToday(Model model) {
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> commentList = commentService.findAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComment = commentList.stream().collect(Collectors.groupingBy(Comment::getCommentType));
        model.addAttribute("starComments", groupedComment.get(CommentType.STAR));
        model.addAttribute("deltaComments",groupedComment.get(CommentType.DELTA));
        model.addAttribute("plusComments", groupedComment.get(CommentType.PLUS));
        log.info("Find the comments size : " + commentList.size());
        return "commentList";
    }

    @PostMapping("/add")
    public String addComments(@RequestParam(name = "plusComment", required = false) String plusComment,
                              @RequestParam(name = "deltaComment", required = false) String deltaComment,
                              @RequestParam(name = "starComment", required = false) String starComment) {

        List<Comment> saveComments = new ArrayList<>();
        var currentUser = (String) SecurityUtils.getSubject().getPrincipal();

        if(StringUtils.isNotEmpty(plusComment)) {
            saveComments.add(createComment(plusComment, CommentType.PLUS, currentUser));
        }
        if(StringUtils.isNotEmpty(deltaComment)) {
            saveComments.add(createComment(deltaComment, CommentType.DELTA, currentUser));
        }
        if(StringUtils.isNotEmpty(starComment)) {
            saveComments.add(createComment(starComment, CommentType.STAR, currentUser));
        }
        if(!saveComments.isEmpty()) {
            log.info("saved comments : " +saveComments.size());
            commentService.saveAllComment(saveComments);
        }
        return "redirect:/comments/all";
    }

    private static Comment createComment(final String commentContent, final CommentType commentType, final String userName) {
        Comment comment = new Comment();
        comment.setCommentType(commentType);
        comment.setComments(commentContent);
        comment.setCreatedBy(userName);
        return comment;
    }
}
