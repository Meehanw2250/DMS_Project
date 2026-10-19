import java.util.ArrayList;
import java.util.Scanner;

public class jets_System {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Scanner sc = new Scanner(System.in);
    private String filename;
    private FileManagerTools fileManagerTools;
//Creating a fileManagerTools object
    public jets_System(){
        this.fileManagerTools = new FileManagerTools();
        System.out.println("Enter the file name you would like to open: ->");
        String inputName = sc.nextLine().trim();

        if (inputName.isEmpty()){
            System.out.println("No file exists. Now Loading Jets.txt");
            this.filename = "Jets.txt";
        }
        else{
            this.filename = inputName;
        }
        this.players = this.fileManagerTools.loadFromFile(this.filename);
    }

//Printing out menu options and taking in user input to save as choice
    public int displayMenu() {
        System.out.println("Welcome to Jets System");
        System.out.println("1. Add Player");
        System.out.println("2. Display Player");
        System.out.println("3. Update Player");
        System.out.println("4. Remove Player");
        System.out.println("5. Compare Players");
        System.out.println("6. Exit");

        int choice = -1;

        while(true){
            System.out.println("Select an option 1-6");

            if(!sc.hasNextInt()){
                System.out.println("Seriously, enter a number 1-6");
                sc.next();
                continue;
            }
            choice = sc.nextInt();
            if(choice >= 1 && choice <=6){
                break;
            }
            else{
                System.out.println("Invalid number, enter a number between 1 and 6");
            }
        }

        return choice;
    }
// Uses the choice from displayMenu() then selects the matching case and performs said method. Then loops
    // back through until the user exits
    public int runMenu() {
        int choice;
        do {
            choice = displayMenu();
            boolean dataChanged = false;

            switch (choice) {
                case 1:
                    dataChanged = addPlayer();
                    break;

                case 2:
                    displayPlayer();
                    break;

                case 3:
                    dataChanged = updatePlayer();
                    break;

                case 4:
                    dataChanged = deletePlayer();
                    break;

                case 5:
                    comparePlayers();
                    break;

                case 6:
                    System.out.println("Saving file changed to " + this.filename + " Leaving Application now");
                    this.fileManagerTools.saveToFile(players, this.filename);
                    break;

                default:
                    System.out.println("Invalid choice try again");
            }
            if (dataChanged){
                this.fileManagerTools.saveToFile(players, this.filename);
                System.out.println(this.filename + " updated successfully!");
            }

        }
        while (choice != 6);
        return 0;
    }
//takes in all player attributes and creates a player object then adds it to the players arraylist
    public boolean addPlayer() {
        sc.nextLine();
        System.out.println("Enter Player Name: ");
        String playerName = sc.nextLine();

        int receptions = -1;
        while (true) {
            System.out.println("Enter Receptions: ");
            if (sc.hasNextInt()) {
                receptions = sc.nextInt();
                if (receptions >= 0) {
                    break;
                }
                System.out.println("This Cannot Be A Negative");
            } else {
                System.out.println("Please enter a whole number");
                sc.next();
            }
        }
        int receivingYards = -1;
        while (true) {
            System.out.println("Enter Receiving Yards: ");
            if (sc.hasNextInt()) {
                receivingYards = sc.nextInt();
                if (receivingYards >= 0) {
                    break;
                }
                System.out.println("This Cannot Be A Negative");
            } else {
                System.out.println("Please enter a whole number");
                sc.next();
            }

        }
        int touchDowns = -1;
        while (true) {
            System.out.println("Enter Total Touchdowns; ");
            if (sc.hasNextInt()) {
                touchDowns = sc.nextInt();
                if (touchDowns >= 0) {
                    break;
                }
                System.out.println("This Cannot Be A Negative");
            } else {
                System.out.println("Please enter a whole number");
                sc.next();
            }
        }
        double yardsPerCatch = 0.0;
        if(receptions > 0){
            yardsPerCatch = (double) receivingYards / receptions;
        }
        else{
            System.out.println("Receptions cannot be empty!");
        }
        double yardsAfterCatch = -1.0;
        while (true){
            System.out.println("Enter Yards After Catch");
            if(sc.hasNextInt()) {
                yardsAfterCatch = sc.nextInt();
                if(yardsAfterCatch >= 0){
                    break;
                }
                System.out.println("Number cannot be negative!");
            }
            else{
                System.out.println("Enter a real number if you're a real jets fan!");
                sc.next();
            }
        }
        players.add(new Player(playerName, receptions, receivingYards, touchDowns, yardsPerCatch, yardsAfterCatch));
        System.out.println("<Player Added To List>");

       return true;
    }
    //Takes in user input of player name and then compares it to the list. If the name is found all
    //player attributes are listed
    public Player displayPlayer() {

        if(players == null || players.isEmpty()){
            System.out.println("The list is currently empty");
            return null;
        }

        sc.nextLine();
        System.out.println("Enter a Jets players name to search: ");
        String searchName = sc.nextLine();

        if(searchName == null || searchName.trim().isEmpty()){
            System.out.println("Please enter a name ");
            return null;
        }

        for (Player p : players){
            if (p != null && p.getName() != null && p.getName().equalsIgnoreCase(searchName.trim())){
                System.out.println("Player Name: " + p.getName());
                System.out.println("Receptions: " + p.getReceptions());
                System.out.println("Receiving Yards: " + p.getReceiving_yards());
                System.out.println("Touchdowns: " + p.getTouchdowns());
                System.out.println("Yards Per Catch: " + p.getYards_per_catch());
                System.out.println("Yards After Catch: " + p.getYards_after_catch());

                return p;
            }


        }
        System.out.println("Player named, " + searchName.trim() + " not found in file");
        return null;

    }
//User enters player name, if match is found through the loop then one by one the players stat lines are shown and
    //the user is asked one by one if they would like to change the individual statistic.
    public boolean updatePlayer(){
        if (players == null || players.isEmpty()){
            System.out.println("List is empty, cannot update player");
            return false;
        }
        sc.nextLine();
        System.out.println("Please enter the Player you wish to update: ");
        String searchName = sc.nextLine();

        if (searchName == null || searchName.trim().isEmpty()){
            System.out.println("Please enter a valid name ");
            return false;
        }
        for (int i = 0; i < players.size(); i++){
            Player p = players.get(i);

            if(p != null && p.getName() != null && p.getName().equalsIgnoreCase(searchName.trim())){
                System.out.println("PLAYER FOUND: " + p.getName());
                System.out.println("Type what you would like to change or press ENTER to continue ");

                try{
                    System.out.print("Receptions (" + p.getReceptions() + ") -> ");
                    String recInput = sc.nextLine().trim();
                    int newReceptions = recInput.isEmpty() ? p.getReceptions() : Integer.parseInt(recInput);

                    System.out.print("Receiving Yards (" + p.getReceiving_yards() + ") -> ");
                    String ydsInput = sc.nextLine().trim();
                    int newRecYards = ydsInput.isEmpty() ? p.getReceiving_yards() : Integer.parseInt(ydsInput);

                    System.out.print("Touchdowns (" + p.getTouchdowns() + ") -> ");
                    String tdInput = sc.nextLine().trim();
                    int newTD = tdInput.isEmpty() ? p.getTouchdowns() : Integer.parseInt(tdInput);

                    System.out.print("Yards Per Catch (" + p.getYards_per_catch() + ") -> ");
                    String ypcInput = sc.nextLine().trim();
                    double newYPC = ypcInput.isEmpty() ? p.getYards_per_catch() : Double.parseDouble(ypcInput);

                    System.out.print("Yards After Catch (" + p.getYards_after_catch() + ") -> ");
                    String yacInput = sc.nextLine().trim();
                    double newYAC = yacInput.isEmpty() ? p.getYards_after_catch() : Double.parseDouble(yacInput);

                    Player updatedPlayer = new Player(p.getName(), newReceptions, newRecYards, newTD, newYPC, newYAC);
                    players.set(i, updatedPlayer);

                    System.out.println("You have succesfully updated " + p.getName() + "'s stat profile");
                    return true;
                }
                catch (NumberFormatException e){
                    System.out.println("Number input was invalid, returning to main menu" + e.getMessage());
                }
            }
        }
        System.out.println("Player " + searchName.trim() + " was not found");
        return false;
    }
    //Takes in user input of player name, then loops through list until a match is found, confirms that user
    //wants to actually delete player and then appends the list accordingly
    public boolean deletePlayer(){
        if (players == null || players.isEmpty()){
            System.out.println("List is empty, cannot delete players");
            return false;
        }
        sc.nextLine();
        System.out.println("Enter the player you wish to remove: ");
        String searchName = sc.nextLine().trim();

        if (searchName == null || searchName.isEmpty()){
            System.out.println("Invalid or missing name");
            return false;
        }
        for (int i = 0; i < players.size(); i++){
            Player p = players.get(i);

            if (p != null && p.getName() != null && p.getName().equalsIgnoreCase(searchName.trim())){
                System.out.println("Player found: " + p.getName());
                System.out.println("Are you sure you want to delete this player? Y/N");
                String confirmation = sc.nextLine().trim();

                if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")){
                    players.remove(i);
                    System.out.println("Player successfully removed");
                    return true;
                }
                else {
                    System.out.println("Player " + searchName.trim() + " was not removed");
                    return false;
                }
            }
        }
        System.out.println("Player " + searchName.trim() + " was not found in the file");
        return false;
    }
    //User enters the names of two players, after confirming they are in the list the two players are listed one after another with their stats shown for the user to compare
    public boolean comparePlayers(){
        if (players == null || players.isEmpty()){
            System.out.println("List is empty, cannot compare players");
            return false;
        }
        sc.nextLine();
        System.out.print("Please enter the name of the first player: ->");
        String playerOne = sc.nextLine().trim();
        if (playerOne.isEmpty()){
            System.out.println("Error no name entered");
            return false;
        }

        System.out.println("Please enter the name of the second player: ->");
        String playerTwo = sc.nextLine().trim();
        if(playerTwo.isEmpty()){
            System.out.println("Error no name entered");
            return false;
        }
       Player p1 = null;
       Player p2 = null;

        for(Player p : players) {
            if(p != null && p.getName() != null){
                if(p.getName().equalsIgnoreCase(playerOne) && p1 == null){
                    p1 = p;
                } else if (p.getName().equalsIgnoreCase(playerTwo) && p2 == null) {
                    p2 = p;
                }
            }
        }
        if (p1 == null){
            System.out.println("Player " + playerOne + " was not found");
        }
        if (p2 == null){
            System.out.println("Player " + playerTwo + " was not found");
        }
        if (p1 == null || p2 == null){
            return false;
        }
        System.out.println("< Player Comparison Below >");
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println("You decide who's better");

        return false;
    }
}
