package com.czhang.dashboard.dashboard.service;

import com.czhang.dashboard.dashboard.model.Comment;
import com.czhang.dashboard.dashboard.repositories.CommentRepository;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


//@Transactional(readOnly = true)
@Log
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> saveAllComment(@NonNull List<Comment> commentList) {
        log.info("Comment Servcie - save all comments size : " + commentList.size());
       return commentRepository.saveAllAndFlush(commentList);
    }

    public List<Comment> findAllCommentsForToday() {
        LocalDate localDate = LocalDate.now();
        log.info("Comment Servcie - find all comments for today : " + localDate.toString());
        return commentRepository.findByCreatedYearAndMonthAndDay(localDate.getYear(), localDate.getMonthValue(),
                localDate.getDayOfMonth());
    }
}
