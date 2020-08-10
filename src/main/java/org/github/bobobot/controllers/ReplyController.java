package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
public class ReplyController {

	@Autowired
	private IReplyService service;

	@ExceptionHandler
	ModelAndView handleErrors(Exception e) {
		log.error("Error in reply controller: ", e);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}

	@GetMapping("/replies")
	ResponseEntity<List<Reply>> all() {
		log.info("Generating a list of all replies.");
		return ResponseEntity.ok(service.list());
	}

	@PostMapping("/replies")
	ResponseEntity<Reply> newReply(@RequestBody Reply reply) {
		log.info("Creating new reply with info: " + reply);
		return ResponseEntity.ok(service.post(reply));
	}

	@GetMapping("/reply/{id}")
	ResponseEntity<Reply> get(@PathVariable Long id) {
		log.info("Returning reply with id: " + id);
		return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/reply/{id}")
	ResponseEntity<Reply> update(@RequestBody Reply reply, @PathVariable Long id) {
		reply.setId(id);
		log.info("Updating reply with info: " + reply);
		return ResponseEntity.ok(service.update(reply));
	}

	@DeleteMapping("/reply/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		log.info("Deleting reply with id: " + id);
		service.delete(id);
		return ResponseEntity.ok().body(null);
	}
}
