package com.czhang.dashboard.dashboard.service;

import com.czhang.dashboard.dashboard.model.CommentType;
import com.czhang.dashboard.dashboard.repositories.CommentRepository;
import com.czhang.dashboard.dashboard.utils.UtilsFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentServiceMVCTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("MVC Test to save all comments")
    @Order(1)
    public void testSaveAllComments_positive() {
        Mockito.when(commentRepository.saveAll(new ArrayList<>())).
                thenReturn(List.of(UtilsFactory.createComment(null)));
        assertThat(commentService.saveAllComment(new ArrayList<>())).hasSize(1);
        assertThat(commentService.saveAllComment(new ArrayList<>()).get(0).getCommentType()).isEqualTo(CommentType.DELTA);
    }

    @Test
    @DisplayName("MVC Test to find all today's comments")
    @Order(2)
    public void testGetAllCommentsForToday() {
        LocalDate locateDate = LocalDate.now();
        Mockito.when(commentRepository.findByCreatedYearAndMonthAndDay(locateDate.getYear(),locateDate.getMonthValue(),locateDate.getDayOfMonth())).
                thenReturn(List.of(UtilsFactory.createComment(null)));
        assertThat(commentService.findAllCommentsForToday()).hasSize(1);
    }
}
