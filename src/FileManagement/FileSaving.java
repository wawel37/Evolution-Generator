package FileManagement;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileSaving {

    public static File selectFile(){
        JFileChooser fileChooser = new JFileChooser("/");
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static void appendToFile(File file, String data){
        try{
            FileWriter fileWriter = new FileWriter(file.getPath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            System.out.println("Saved data to " + file.getAbsolutePath());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
