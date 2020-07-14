package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.ImageDAO;
import org.github.bobobot.entities.Image;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryImageDAO implements ImageDAO {
	ArrayList<Image> memory = new ArrayList<>();

	@Override
	public Image create(boolean exists, String path) {
		Image image = new Image(memory.size(), exists, path);
		memory.add(image);
		return image;
	}

	@Override
	public Optional<Image> update(int ID, boolean exists, String path) {
		Optional<Image> image = memory.stream()
				.filter(i -> i.getID() == ID)
				.findFirst();

		if (image.isPresent()) {
			image.get().setExists(exists);
			image.get().setPath(path);
		}

		return image;
	}

	@Override
	public Optional<Image> select(int ID) {
		Optional<Image> image = memory.stream()
				.filter(i -> i.getID() == ID)
				.findFirst();

		return image;
	}

	@Override
	public Optional<Image> delete(int ID) {
		Optional<Image> image = memory.stream()
				.filter(i -> i.getID() == ID)
				.findFirst();

		if (image.isPresent()) {
			memory.remove(image.get());
		}

		return image;
	}
}