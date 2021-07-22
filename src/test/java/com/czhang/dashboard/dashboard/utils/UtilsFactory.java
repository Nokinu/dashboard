package com.czhang.dashboard.dashboard.utils;

import com.czhang.dashboard.dashboard.model.Comment;
import com.czhang.dashboard.dashboard.model.CommentType;
import com.czhang.dashboard.dashboard.model.User;
import com.czhang.dashboard.dashboard.model.UserRoleType;

public class UtilsFactory {

    public static final Comment createComment(CommentType commentType) {
        Comment comment = new Comment();
        comment.setComments("Test");
        comment.setCommentType(commentType == null ? CommentType.DELTA : commentType);
        return comment;
    }

    public static final User createUser(UserRoleType userRoleType) {
        User user = new User();
        user.setUsername("Admin");
        user.setPassword("Admin");
        user.setRole(userRoleType == null ? UserRoleType.SUPER : userRoleType);
        return user;
    }
}
