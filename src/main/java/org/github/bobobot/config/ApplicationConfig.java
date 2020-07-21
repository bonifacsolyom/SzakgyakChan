package org.github.bobobot.config;

import org.github.bobobot.dao.*;
import org.github.bobobot.dao.impl.*;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.services.*;
import org.github.bobobot.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

	@Bean
	public IBoardDAO boardDAO() {
		return new InMemoryBoardDAO();
	}

	@Bean
	public INotificationDAO commentDAO() {
		return new InMemoryCommentNotificationDAO();
	}

	@Bean
	public INotificationDAO voteDAO() {
		return new InMemoryVoteNotificationDAO();
	}

	@Bean
	public IReplyDAO replyDAO() {
		return new InMemoryReplyDAO();
	}

	@Bean
	public IThreadDAO threadDAO() {
		return new InMemoryThreadDAO();
	}

	@Bean
	public IUserDAO userDAO() {
		return new InMemoryUserDAO();
	}

	@Bean
	public IBoardService boardService(IBoardDAO boardDAO) {
		return new BoardService(boardDAO);
	}

	@Bean
	public INotificationService notificationService(INotificationDAO<CommentNotification> commentDAO, INotificationDAO<VoteNotification> voteDAO, IUserDAO userDAO) {
		return new NotificationService(commentDAO, voteDAO, userDAO);
	}

	@Bean
	public IReplyService replyService(IReplyDAO replyDAO, INotificationDAO<CommentNotification> commentDAO, INotificationDAO<VoteNotification> voteDAO) {
		return new ReplyService(replyDAO, commentDAO, voteDAO);
	}

	@Bean
	public IThreadService threadService(IThreadDAO threadDAO) {
		return new ThreadService(threadDAO);
	}

	@Bean
	public IUserService userService(IUserDAO userDAO, PasswordEncoder passwordEncoder) {
		return new UserService(userDAO, passwordEncoder);
	}

	//Miscellaneous

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}