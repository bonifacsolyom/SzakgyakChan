package org.github.bobobot.config;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.services.*;
import org.github.bobobot.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//TODO: Mivel a fő osztályon (Application) már van @SpringBootApplication annotáció, és az önmagában már tartalmaz @ComponentScan annotációt, és az a
// legkülső package-en van, így nincs szükség több ComponentScan-re.
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
	//TODO: +1, ennél már csak ez lett volna szebb "PasswordEncoderFactories.createDelegatingPasswordEncoder()"
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public EmailValidator emailValidator() {
		return EmailValidator.getInstance();
	}
}