package org.github.bobobot.controllers;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ReplyController {

	@Autowired
	IReplyService service;

	@GetMapping("/replies")
	ResponseEntity<List<Reply>> all() {
		try {
			ResponseEntity<List<Reply>> list = ResponseEntity.ok(service.list());
			log.info("Generated a list of all replies.");
			return list;
		} catch (IllegalArgumentException e) {
			log.error("Could not list replies: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/replies")
	ResponseEntity<Reply> newReply(@RequestBody Reply reply) {
		try {
			ResponseEntity<Reply> createdReply = ResponseEntity.ok(service.post(reply));
			log.info("Created new reply with info: " + reply);
			return createdReply;
		} catch (IllegalArgumentException e) {
			log.error("Could not create new reply with info: " + reply, e);
			return ResponseEntity.badRequest().body(reply);
		}
	}

	@GetMapping("/reply/{id}")
	ResponseEntity<Reply> get(@PathVariable Long id) {
		try {
			ResponseEntity<Reply> foundReply = ResponseEntity.ok(service.findById(id));
			log.info("Returned reply with id: " + id);
			return foundReply;
		} catch (Exception e) {
			log.error("Could not get reply with id: " + id, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/reply/{id}")
	ResponseEntity<Reply> update(@RequestBody Reply reply, @PathVariable Long id) {
		reply.setID(id);
		try {
			ResponseEntity<Reply> updatedReply = ResponseEntity.ok(service.update(reply));
			log.info("Updated reply with info: " + reply);
			return updatedReply;
		} catch (Exception e) {
			log.error("Could not update reply with info: " + reply, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping("/reply/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			log.info("Deleted reply with id: " + id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			log.error("Could not delete reply: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}
}
