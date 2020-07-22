package org.github.bobobot;

import org.github.bobobot.services.impl.BoardService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(Application.class, args);

		BoardService boardService = appContext.getBean(BoardService.class);
		boardService.create("b", "random");
		System.out.println(boardService.findByShortName("b").getLongName());
	}
}