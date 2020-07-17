package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.IImageDAO;
import org.github.bobobot.entities.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryImageDAO implements IImageDAO {
	List<Image> memory = new ArrayList<>();

	@Override
	public Image create(Image image) {
		image.setID(memory.size());
		memory.add(image);
		return image;
	}

	@Override
	public Optional<Image> update(Image image) {
		Optional<Image> memoryImage = memory.stream()
				.filter(i -> i.getID() == image.getID())
				.findFirst();

		if (memoryImage.isPresent()) {
			memoryImage.get().setExists(image.isExists());
			memoryImage.get().setPath(image.getPath());
		}

		return memoryImage;
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