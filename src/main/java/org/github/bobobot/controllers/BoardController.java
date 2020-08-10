package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
public class BoardController {

	@Autowired
	private IBoardService service;

	@ExceptionHandler
	ModelAndView handleErrors(Exception e) {
		log.error("Error in board controller: ", e);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}

	@GetMapping("/boards")
	ResponseEntity<List<Board>> all() {
		log.info("Generating a list of all boards");
		return ResponseEntity.ok(service.list());
	}

	@PostMapping("/boards")
	ResponseEntity<Board> newBoard(@RequestBody Board board) {
		log.info("Creating new board with info: " + board);
		return ResponseEntity.ok(service.create(board));
	}

	@GetMapping("/board/{id}")
	ResponseEntity<Board> get(@PathVariable Long id) {
		log.info("Returning board with id: " + id);
		return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/board/{id}")
	ResponseEntity<Board> update(@RequestBody Board board, @PathVariable Long id) {
		board.setId(id);
		log.info("Updating board with info: " + board);
		return ResponseEntity.ok(service.update(board));
	}

	@DeleteMapping("/board/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		log.info("Deleting board with id: " + id);
		service.delete(id);
		return ResponseEntity.ok().body(null);
	}
}
