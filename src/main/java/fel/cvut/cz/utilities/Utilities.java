package fel.cvut.cz.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Utilities used in other class. */
public class Utilities {
    public static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null){
                builder.append(line + "\n");
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static int parseInt(String number){
        try{
            return Integer.parseInt(number);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static float parseFloat(String number){
        try{
            return Float.parseFloat(number);
        } catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void writeToBottomOfFile(String pathToFile, String text){
        try {
            String content = new String(Files.readAllBytes(Paths.get(pathToFile)));
            content = content + text + "\n";
            FileWriter writer = new FileWriter(pathToFile, false);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
}
