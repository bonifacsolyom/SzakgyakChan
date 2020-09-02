package org.github.bobobot.ui.views.misc;

import com.vaadin.ui.Upload;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageReceiver implements Upload.Receiver {

	@Getter
	private static final String folderPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images";
	String fileName;

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
		if (fileName == null) return null;
		return folderPath + File.separator + fileName;
	}

}
