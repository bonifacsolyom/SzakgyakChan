package org.github.bobobot.repositories;

import org.github.bobobot.entities.VoteNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoteNotificationRepository extends JpaRepository<VoteNotification, Long>  {

	/**
	 * Gathers all vote notifications that belong to a user.
	 * @param id The ID of the user.
	 * @return A list of all vote notifications that belong to the given user.
	 */
	@Query(value =
			"SELECT * " +
			"FROM " +
			"  User_vote_notifications " +
			"  INNER JOIN User ON User_vote_notifications.USER_ID = Ruser.ID " +
			"  INNER JOIN Vote_notification ON User_vote_notifications.VOTE_NOTIFICATIONS_ID = Vote_notification.ID " +
			"WHERE " +
			"  ORIGINAL_REPLY_ID = ?1", nativeQuery = true)
	List<VoteNotification> getByUserId(Long id);
}
