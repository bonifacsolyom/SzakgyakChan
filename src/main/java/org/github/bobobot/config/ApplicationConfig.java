package org.github.bobobot.config;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.services.*;
import org.github.bobobot.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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

	@Bean
	public IMetaService metaService() {
		return new MetaService();
	}


	//Miscellaneous
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public EmailValidator emailValidator() {
		return EmailValidator.getInstance();
	}
}