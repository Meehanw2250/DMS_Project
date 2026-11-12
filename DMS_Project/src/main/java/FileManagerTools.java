import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManagerTools {
//Creates a new printwriter to a specific file name and then goes through each player block by block
    //grabbing each variable as seperated by a comma and writing it into said file.
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
    //creates a new file instance based on the user provided file name, reads each player and adds it back into the file piewce by piece as seperated by parts of the index. It then takes the parts and puts them back together into
    //individual players in the list again.
    public ArrayList<Player> loadFromFile(String filename){
        ArrayList<Player> reloadedPlayers = new ArrayList<>();
        File file = new File(filename);

        if(!file.exists()){
            System.out.println("File name " + filename + "does not exist. Here is a clean slate");
            return reloadedPlayers;

        }
        try(Scanner fileScanner = new Scanner(file)){
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if(parts.length >= 6){
                    String name = parts[0];
                    int receptions = Integer.parseInt(parts[1]);
                    int receivingYards = Integer.parseInt(parts[2]);
                    int touchDowns = Integer.parseInt(parts[3]);
                    double yardsPerReception = Double.parseDouble(parts[4]);
                    double yardsAfterCatch = Double.parseDouble(parts[5]);

                    Player p = new Player(name, receptions, receivingYards, touchDowns, yardsPerReception, yardsAfterCatch);
                    reloadedPlayers.add(p);
                }
            }
            System.out.println("File full of Jets players loaded up!");
        }
        catch (IOException e){
            System.out.println("Error reading the Jets file" + e.getMessage());
        }
        catch (NumberFormatException e){
            System.out.println("Error sorting through numbers in file" + e.getMessage());
        }
        return reloadedPlayers;
    }

}
