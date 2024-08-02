package com.config.tool.mainScreen;
import com.config.tool.constent.IConfigToolConstents;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
public class XstoreMobileRestart {
    void handleXstoreMobileAnchorFile() {
        String filePath = IConfigToolConstents.XSTORE_MOB_ANCHOR_FILE_PATH;
        deleteXstoreMobileAnchorFile(filePath);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    private void deleteXstoreMobileAnchorFile(String filePath) {
        try {
            // Use the delete method from Files class to delete the file
            Files.delete(Paths.get(filePath));
        } catch (NoSuchFileException e) {
            System.err.println("Didn't find the Xstore mobile anchore file");
        } catch (IOException e) {
            System.err.println("Error occurred while deleting the file: " + e.getMessage());
        }
    }
}