package org.github.bobobot.controllers;

import lombok.extern.java.Log;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;
import org.github.bobobot.services.impl.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Log
@RestController
public class BoardController {

	@Autowired
	IBoardService service;

	@GetMapping("/boards")
	List<Board> all() {
		return service.list();
	}

	@PostMapping("/boards")
	Board newBoard(@RequestBody Board board) {
		log.info("Created new board with info: " + board);
		return service.create(board);
	}

	@GetMapping("/board/{id}")
	Board get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/board/{id}")
	Board update(@RequestBody Board board, @PathVariable Long id) {
		board.setID(id);
		log.info("Updated board with info: " + board);
		return service.update(board);
	}

	@DeleteMapping("/board/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
