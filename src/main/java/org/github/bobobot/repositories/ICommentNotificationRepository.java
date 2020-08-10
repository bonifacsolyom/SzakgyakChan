package org.github.bobobot.repositories;

import org.github.bobobot.entities.CommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentNotificationRepository extends JpaRepository<CommentNotification, Long> {

	/**
	 * Gathers all comment notifications that belong to a user.
	 *
	 * @param id The ID of the user.
	 * @return A list of all comment notifications that belong to the given user.
	 */
	@Query(value =
			"SELECT * " +
					"FROM " +
					"  User_comment_notifications " +
					"  INNER JOIN User ON User_comment_notifications.USER_ID = Ruser.ID " +
					"  INNER JOIN Comment_notification ON User_comment_notifications.COMMENT_NOTIFICATIONS_ID = Comment_notification.ID " +
					"WHERE " +
					"  ORIGINAL_REPLY_ID = ?1", nativeQuery = true)
	List<CommentNotification> getByUserId(Long id);

}
