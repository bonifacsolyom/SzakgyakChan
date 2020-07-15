package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.ReplyDAO;
import org.github.bobobot.entities.Reply;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryReplyDAO implements ReplyDAO {
	ArrayList<Reply> memory = new ArrayList<>();

	@Override
	public Reply create(Reply reply) {
		reply.setID(memory.size());
		memory.add(reply);
		return reply;
	}

	@Override
	public Optional<Reply> update(Reply reply) {
		Optional<Reply> memoryReply = memory.stream()
				.filter(r -> r.getID() == reply.getID())
				.findFirst();

		if (memoryReply.isPresent()) {
			memoryReply.get().setContent(reply.getContent());
			memoryReply.get().setDate(reply.getDate());
			memoryReply.get().setVotes(reply.getVotes());
			memoryReply.get().setThread(reply.getThread());
		}

		return memoryReply;
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
