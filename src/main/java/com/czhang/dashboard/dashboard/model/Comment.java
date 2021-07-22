package com.czhang.dashboard.dashboard.model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "db_comment")
@EntityListeners(AuditingEntityListener.class)
@Data
@Setter
@Getter
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "comments")
	private String comments;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "comment_type")
	private CommentType commentType;
	
	@CreatedDate
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

}