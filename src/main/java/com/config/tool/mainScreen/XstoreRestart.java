package com.config.tool.mainScreen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.config.tool.constent.IConfigToolConstents;

public class XstoreRestart {

	void handleXstoreAnchorFile() {

		String filePath = IConfigToolConstents.XSTORE_ANCHOR_FILE_PATH;
		deleteXstoreAnchorFile(filePath);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	private void deleteXstoreAnchorFile(String filePath) {
		
		try {
			// Use the delete method from Files class to delete the file
			Files.delete(Paths.get(filePath));
			System.err.println("Xstore anchor file is deleted");
		} catch (NoSuchFileException e) {
			System.err.println("Didn't find the Xstore anchor file");
		} catch (IOException e) {
			System.err.println("Error occurred while deleting the file: " + e.getMessage());
		}
	}

}
