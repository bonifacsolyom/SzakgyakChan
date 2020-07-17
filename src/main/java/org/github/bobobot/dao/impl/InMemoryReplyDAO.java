package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.IReplyDAO;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryReplyDAO implements IReplyDAO {
	List<Reply> memory = new ArrayList<>();

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
	public List<Reply> selectByThread(Thread thread) {
		List<Reply> replies = memory.stream()
				.filter(r -> r.getThread().equals(thread))
				.collect(Collectors.toList());
		return new ArrayList<Reply>(replies);
	}

	@Override
	public List<Reply> list() {
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
