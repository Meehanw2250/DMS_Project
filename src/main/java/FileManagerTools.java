import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class fileMangerTools {

    public boolean saveToFile(ArrayList<Player> players, String filename){
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(filename))){
            for (Player p : players){
                printWriter.println(p.getName() + "," +
                        p.getReceptions() + "," +
                        p.getReceiving_yards() + "," +
                        p.getTouchdowns() + "," +
                        p.getYards_per_catch() + "," +
                        p.getYards_after_catch());
            }
            System.out.println("Saved to " + filename);
            return true;
        }
        catch(IOException e){
            System.out.println("We ran into an issue saving the file: " + e.getMessage());
            return false;
        }

    }
}
