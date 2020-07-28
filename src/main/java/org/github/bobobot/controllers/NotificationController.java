package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NotificationController {

	@Autowired
	INotificationService service;

	@GetMapping("/commentNotifications")
	ResponseEntity<List<CommentNotification>> allCommentNotifications() {
		try {
			ResponseEntity<List<CommentNotification>> list = ResponseEntity.ok(service.listCommentNotifications());
			log.info("Generated a list of all comment notifications.");
			return list;
		} catch (IllegalArgumentException e) {
			log.error("Could not list comment notifications: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/voteNotifications")
	ResponseEntity<List<VoteNotification>> allVoteNotifications() {
		try {
			ResponseEntity<List<VoteNotification>> list = ResponseEntity.ok(service.listVoteNotifications());
			log.info("Generated a list of all vote notifications.");
			return list;
		} catch (IllegalArgumentException e) {
			log.error("Could not list vote notifications: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/commentNotifications")
	ResponseEntity<CommentNotification> newCommentNotification(@RequestBody CommentNotification notification) {
		try {
			ResponseEntity<CommentNotification> createdNotification = ResponseEntity.ok(service.create(notification));
			log.info("Created new comment notification with info: " + notification);
			return createdNotification;
		} catch (IllegalArgumentException e) {
			log.error("Could not create new comment notification with info: " + notification, e);
			return ResponseEntity.badRequest().body(notification);
		}
	}

	@PostMapping("/voteNotifications")
	ResponseEntity<VoteNotification> newVoteNotification(@RequestBody VoteNotification notification) {
		try {
			ResponseEntity<VoteNotification> createdNotification = ResponseEntity.ok(service.create(notification));
			log.info("Created new vote notification with info: " + notification);
			return createdNotification;
		} catch (IllegalArgumentException e) {
			log.error("Could not create new vote notification with info: " + notification, e);
			return ResponseEntity.badRequest().body(notification);
		}
	}

	@GetMapping("/commentNotification/{id}")
	ResponseEntity<CommentNotification> getCommentNotification(@PathVariable Long id) {
		try {
			ResponseEntity<CommentNotification> foundNotification = ResponseEntity.ok(service.findCommentNotificationByID(id));
			log.info("Returned comment notification with id: " + id);
			return foundNotification;
		} catch (Exception e) {
			log.error("Could not get comment notification with id: " + id, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/voteNotification/{id}")
	ResponseEntity<VoteNotification> getVoteNotification(@PathVariable Long id) {
		try {
			ResponseEntity<VoteNotification> foundNotification = ResponseEntity.ok(service.findVoteNotificationByID(id));
			log.info("Returned vote notification with id: " + id);
			return foundNotification;
		} catch (Exception e) {
			log.error("Could not get vote notification with id: " + id, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/commentNotification/{id}")
	ResponseEntity<CommentNotification> update(@RequestBody CommentNotification notification, @PathVariable Long id) {
		notification.setId(id);
		try {
			ResponseEntity<CommentNotification> updatedNotification = ResponseEntity.ok(service.update(notification));
			log.info("Updated comment notification with info: " + notification);
			return updatedNotification;
		} catch (Exception e) {
			log.error("Could not update comment notification with info: " + notification, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/voteNotification/{id}")
	ResponseEntity<VoteNotification> update(@RequestBody VoteNotification notification, @PathVariable Long id) {
		notification.setId(id);
		try {
			ResponseEntity<VoteNotification> updatedNotification = ResponseEntity.ok(service.update(notification));
			log.info("Updated vote notification with info: " + notification);
			return updatedNotification;
		} catch (Exception e) {
			log.error("Could not update vote notification with info: " + notification, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping("/commentNotification/{id}")
	ResponseEntity<Void> deleteCommentNotification(@PathVariable Long id) {
		try {
			service.deleteCommentNotification(id);
			log.info("Deleted notification with id: " + id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			log.error("Could not delete notification: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping("/voteNotification/{id}")
	ResponseEntity<Void> deleteVoteNotification(@PathVariable Long id) {
		try {
			service.deleteVoteNotification(id);
			log.info("Deleted notification with id: " + id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			log.error("Could not delete notification: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}
}
