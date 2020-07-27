package org.github.bobobot.controllers;

import lombok.extern.java.Log;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
public class ReplyController {

	@Autowired
	IReplyService service;

	@GetMapping("/replies")
	List<Reply> all() {
		return service.list();
	}

	@PostMapping("/replies")
	Reply newReply(@RequestBody Reply reply) {
		log.info("Created new reply with info: " + reply);
		return service.post(reply);
	}

	@GetMapping("/reply/{id}")
	Reply get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/reply/{id}")
	Reply update(@RequestBody Reply reply, @PathVariable Long id) {
		reply.setID(id);
		log.info("Updated reply with info: " + reply);
		return service.update(reply);
	}

	@DeleteMapping("/reply/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
