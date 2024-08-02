package com.config.tool.mainScreen;
import com.config.tool.constent.IConfigToolConstents;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
public class XenvRestart {
    void handleXenvAnchorFile() {
        String filePath1= IConfigToolConstents.Xenv_ANCHOR_FILE_PATH1;
        String filePath2= IConfigToolConstents.Xenv_ANCHOR_FILE_PATH2;
        deleteXenvAnchorFile(filePath1,filePath2);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    private void deleteXenvAnchorFile(String filePath1,String filePath2) {
        try {
            // Use the delete method from Files class to delete the file
            Files.delete(Paths.get(filePath1));
            Files.delete(Paths.get(filePath2));
        } catch (NoSuchFileException e) {
            System.err.println("Didn't find the Xenv anchor file");
        } catch (IOException e) {
            System.err.println("Error occurred while deleting the file: " + e.getMessage());
        }
    }
}