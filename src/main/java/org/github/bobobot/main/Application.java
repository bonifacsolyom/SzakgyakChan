package org.github.bobobot.main;

import org.github.bobobot.config.ApplicationConfig;
import org.github.bobobot.services.impl.BoardService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ApplicationConfig.class);
		ctx.refresh();

		BoardService boardService = ctx.getBean(BoardService.class);
		boardService.create("b", "random");
		System.out.println(boardService.findByShortName("b").getLongName());
	}
}