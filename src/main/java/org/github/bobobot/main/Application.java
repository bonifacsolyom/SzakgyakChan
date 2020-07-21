package org.github.bobobot.main;

import org.github.bobobot.config.ApplicationConfig;
import org.github.bobobot.services.impl.BoardService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		BoardService boardService = ctx.getBean(BoardService.class);
		boardService.create("b", "random");
		System.out.println(boardService.findByShortName("b").getLongName());
	}
}