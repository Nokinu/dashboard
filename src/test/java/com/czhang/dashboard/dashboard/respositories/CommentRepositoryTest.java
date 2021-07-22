package com.czhang.dashboard.dashboard.respositories;

import com.czhang.dashboard.dashboard.model.Comment;
import com.czhang.dashboard.dashboard.repositories.CommentRepository;
import com.czhang.dashboard.dashboard.utils.UtilsFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@ActiveProfiles("integratedTest")
@DataJpaTest
public class CommentRepositoryTest {
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	@DisplayName("Find Comments by created date")
	@Order(1)
	public void findByCreatedYearAndMonthAndDay_ShouldReturnComment() {
		//Given
		Comment comment = UtilsFactory.createComment(null);
		comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		testEntityManager.persistAndFlush(comment);

		//When
		LocalDate localDate = LocalDate.now();
		List<Comment> commentList = commentRepository.findByCreatedYearAndMonthAndDay(localDate.getYear(),
				localDate.getMonthValue(), localDate.getDayOfMonth());

		//Then
		assertThat(commentList).hasSize(1);
		assertThat(commentList.get(0)).hasFieldOrPropertyWithValue("comments", "Test");
	}
	
	@Test
	@DisplayName("Save One Comment")
	@Order(2)
	public void save_Positive_ShouldSaveOneComment() {
		//Given
		Comment comment = UtilsFactory.createComment(null);
		comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		
		//When
		Comment saved = commentRepository.save(comment);
		
		//Then
		assertThat(testEntityManager.find(Comment.class, saved.getId())).isEqualTo(saved);
	}

}
