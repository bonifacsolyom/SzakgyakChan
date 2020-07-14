package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.ReplyDAO;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.Reply;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class InMemoryReplyDAO implements ReplyDAO {
	ArrayList<Reply> memory = new ArrayList<>();

	@Override
	public Reply create(String content, LocalDateTime date, int votes, Thread thread) {
		Reply reply = new Reply(memory.size(), content, date, votes, thread);
		memory.add(reply);
		return reply;
	}

	@Override
	public Optional<Reply> update(int ID, String content, LocalDateTime date, int votes, Thread thread) {
		Optional<Reply> reply = memory.stream()
				.filter(r -> r.getID() == ID)
				.findFirst();

		if (reply.isPresent()) {
			reply.get().setContent(content);
			reply.get().setDate(date);
			reply.get().setVotes(votes);
			reply.get().setThread(thread);
		}

		return reply;
	}

	@Override
	public Optional<Reply> select(int ID) {
		Optional<Reply> reply = memory.stream()
				.filter(r -> r.getID() == ID)
				.findFirst();
		return reply;
	}

	@Override
	public ArrayList<Reply> list() {
		return memory;
	}

	@Override
	public Optional<Reply> delete(int ID) {
		Optional<Reply> reply = memory.stream()
				.filter(r -> r.getID() == ID)
				.findFirst();
		if (reply.isPresent()) {
			memory.remove(reply.get());
		}

		return reply;
	}
}
