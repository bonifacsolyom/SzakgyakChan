package org.github.bobobot.config;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.services.*;
import org.github.bobobot.services.impl.*;
import org.github.bobobot.ui.views.layouts.NewCommentFormLayout;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
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

	@Bean(name = "NewCommentFormLayout")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	NewCommentFormLayout newCommentFormLayout() {
		return new NewCommentFormLayout();
	}

}