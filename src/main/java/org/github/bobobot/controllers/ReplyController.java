package org.github.bobobot.controllers;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		return service.post(reply);
	}

	@GetMapping("/reply/{id}")
	Reply get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/reply/{id}")
	Reply update(@RequestBody Reply reply, @PathVariable Long id) {
		reply.setID(id);
		return service.update(reply);
	}

	@DeleteMapping("/replies/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
