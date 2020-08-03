package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
public class NotificationController {

	@Autowired
	private INotificationService service;

	@ExceptionHandler
	ModelAndView handleErrors(Exception e) {
		log.error("Error in notification controller: ", e);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}

	@GetMapping("/commentNotifications")
	ResponseEntity<List<CommentNotification>> allCommentNotifications() {
		log.info("Generating a list of all comment notifications");
		return ResponseEntity.ok(service.listCommentNotifications());
	}

	@GetMapping("/voteNotifications")
	ResponseEntity<List<VoteNotification>> allVoteNotifications() {
		log.info("Generating a list of all vote notifications.");
		return ResponseEntity.ok(service.listVoteNotifications());
	}

	@PostMapping("/commentNotifications")
	ResponseEntity<Void> newCommentNotification(@RequestBody CommentNotification notification) {
		log.info("Creating new comment notification with info: " + notification);
		service.create(notification);
		return ResponseEntity.ok().body(null);
	}

	@PostMapping("/voteNotifications")
	ResponseEntity<Void> newVoteNotification(@RequestBody VoteNotification notification) {
			log.info("Creating new vote notification with info: " + notification);
			service.create(notification);
			return ResponseEntity.ok().body(null);
	}

	@GetMapping("/commentNotification/{id}")
	ResponseEntity<CommentNotification> getCommentNotification(@PathVariable Long id) {
		log.info("Returning comment notification with id: " + id);
		return ResponseEntity.ok(service.findCommentNotificationByID(id));
	}

	@GetMapping("/voteNotification/{id}")
	ResponseEntity<VoteNotification> getVoteNotification(@PathVariable Long id) {
			log.info("Returning vote notification with id: " + id);
			return ResponseEntity.ok(service.findVoteNotificationByID(id));
	}

	@PutMapping("/commentNotification/{id}")
	ResponseEntity<CommentNotification> update(@RequestBody CommentNotification notification, @PathVariable Long id) {
		notification.setId(id);
		log.info("Updating comment notification with info: " + notification);
		return ResponseEntity.ok(service.update(notification));
	}

	@PutMapping("/voteNotification/{id}")
	ResponseEntity<VoteNotification> update(@RequestBody VoteNotification notification, @PathVariable Long id) {
		notification.setId(id);
		log.info("Updating vote notification with info: " + notification);
		return ResponseEntity.ok(service.update(notification));
	}

	@DeleteMapping("/commentNotification/{id}")
	ResponseEntity<Void> deleteCommentNotification(@PathVariable Long id) {
		log.info("Deleting notification with id: " + id);
		service.deleteCommentNotification(id);
		return ResponseEntity.ok().body(null);
	}

	@DeleteMapping("/voteNotification/{id}")
	ResponseEntity<Void> deleteVoteNotification(@PathVariable Long id) {
			log.info("Deleting notification with id: " + id);
			service.deleteVoteNotification(id);
			return ResponseEntity.ok().body(null);
	}

	@GetMapping("/users/{id}/commentNotifications")
	ResponseEntity<List<CommentNotification>> getCommentNotificationsByUserId(@PathVariable Long id) {
			log.info("Gathering comment notifications of user " + id);
			return ResponseEntity.ok(service.getCommentNotificationsByUserId(id));
	}

	@GetMapping("/users/{id}/voteNotifications")
	ResponseEntity<List<VoteNotification>> getVoteNotificationsByUserId(@PathVariable Long id) {
			log.info("Gathering vote notifications of user " + id);
			return ResponseEntity.ok(service.getVoteNotificationsByUserId(id));
	}
}
