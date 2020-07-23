package org.github.bobobot.config;

import org.github.bobobot.services.*;
import org.github.bobobot.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("org.github.bobobot")
public class ApplicationConfig {

	@Bean
	public IBoardService boardService() {
		return new BoardService();
	}

	@Bean
	public INotificationService notificationService(IUserService userService) {
		return new NotificationService(userService);
	}

	@Bean
	public IReplyService replyService() {
		return new ReplyService();
	}

	@Bean
	public IThreadService threadService() {
		return new ThreadService();
	}

	@Bean
	public IUserService userService(PasswordEncoder passwordEncoder) {
		return new UserService();
	}


	//Miscellaneous
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}