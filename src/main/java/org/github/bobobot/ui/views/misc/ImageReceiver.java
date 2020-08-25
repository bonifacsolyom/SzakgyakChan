package org.github.bobobot.ui.views.misc;

import com.vaadin.ui.Upload;
import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageReceiver implements Upload.Receiver {

	String fileName;

	@Getter
	private static final String folderPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images";

	@Override
	public OutputStream receiveUpload(String fileName, String mimeType) {
		this.fileName = fileName;
		try {
			Files.createDirectories(Paths.get(folderPath));
			return new FileOutputStream(new File(folderPath + File.separator + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getLastFileName() {
		return folderPath + File.separator + fileName;
	}

}
