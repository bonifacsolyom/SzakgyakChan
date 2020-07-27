package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BoardController {

	@Autowired
	IBoardService service;

	@GetMapping("/boards")
	ResponseEntity<List<Board>> all() {
		try {
			ResponseEntity<List<Board>> list = ResponseEntity.ok(service.list());
			log.info("Generated a list of all boards.");
			return list;
		} catch (IllegalArgumentException e) {
			log.error("Could not list boards: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/boards")
	ResponseEntity<Board> newBoard(@RequestBody Board board) {
		try {
			ResponseEntity<Board> createdBoard = ResponseEntity.ok(service.create(board));
			log.info("Created new board with info: " + board);
			return createdBoard;
		} catch (IllegalArgumentException e) {
			log.error("Could not create new board with info: " + board, e);
			return ResponseEntity.badRequest().body(board);
		}
	}

	@GetMapping("/board/{id}")
	ResponseEntity<Board> get(@PathVariable Long id) {
		try {
			ResponseEntity<Board> foundBoard = ResponseEntity.ok(service.findById(id));
			log.info("Returned board with id: " + id);
			return foundBoard;
		} catch (Exception e) {
			log.error("Could not get board with id: " + id, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/board/{id}")
	ResponseEntity<Board> update(@RequestBody Board board, @PathVariable Long id) {
		board.setId(id);
		try {
			ResponseEntity<Board> updatedBoard = ResponseEntity.ok(service.update(board));
			log.info("Updated board with info: " + board);
			return updatedBoard;
		} catch (Exception e) {
			log.error("Could not update board with info: " + board, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping("/board/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			log.info("Deleted board with id: " + id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			log.error("Could not delete board: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}
}
