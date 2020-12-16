package FileManagement;

import javax.swing.*;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHandling {

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

    public static String getStringFromJSON(String field) {
        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader("//home//mateusz//IdeaProjects//EvolutionGenerator//assets//JSONInputData.json");
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            if (jsonObject.get(field) == null) return null;
            return String.valueOf(jsonObject.get(field));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
