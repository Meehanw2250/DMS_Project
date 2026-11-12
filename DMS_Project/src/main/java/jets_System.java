import java.util.ArrayList;
import java.util.Scanner;

public class jets_System {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Scanner sc = new Scanner(System.in);
    private String filename;
    private FileManagerTools fileManagerTools;
//Creating a fileManagerTools object
    public jets_System(boolean isTest){
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

    public jets_System(){
        this.fileManagerTools = new FileManagerTools();
        this.filename = "Jets.txt";
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
            sc.nextLine();
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
    public boolean addPlayer(Player p){
        if (p==null) return false;
        players.add(p);
        return true;
    }
    //Takes in user input of player name and then compares it to the list. If the name is found all
    //player attributes are listed
    public Player displayPlayer() {

        if(players == null || players.isEmpty()){
            System.out.println("The list is currently empty");
            return null;
        }
        System.out.println("Jets Players In Database");
        for(Player p : players){
            if(p != null && p.getName() != null){
                System.out.println("------" + p.getName() + "------");
            }
        }
        System.out.println("______________________________________________________");

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
    public Player displayPlayer(String searchName){

        if(players == null || players.isEmpty() || searchName == null || searchName.trim().isEmpty() ){
            return null;
        }
        for (Player p : players){
            if(p != null && p.getName() != null && p.getName().equalsIgnoreCase(searchName.trim())){
                return p;
            }
        }
        return null;
    }

//User enters player name, if match is found through the loop then one by one the players stat lines are shown and
    //the user is asked one by one if they would like to change the individual statistic.
    public boolean updatePlayer(){
        if (players == null || players.isEmpty()){
            System.out.println("List is empty, cannot update player");
            return false;
        }
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

    public boolean updatePlayer(Player targetPlayer, int newReceptions, int newYards, int newTDs, double newYPC, double newYAC){
    if (players == null || players.isEmpty() || targetPlayer == null) return false;
    for(int i = 0; i < players.size(); i++){
        Player p = players.get(i);
        if(p!=null && p.getName()!=null && p.getName().equalsIgnoreCase(targetPlayer.getName())){
            Player updated = new Player(p.getName(), newReceptions, newYards, newTDs, newYPC, newYAC);
            players.set(i, updated);
            return true;
        }
    }
    return false;
    }

    //Takes in user input of player name, then loops through list until a match is found, confirms that user
    //wants to actually delete player and then appends the list accordingly
    public boolean deletePlayer(){
        if (players == null || players.isEmpty()){
            System.out.println("List is empty, cannot delete players");
            return false;
        }
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
    public boolean deletePlayer(Player p){
        if (players == null || players.isEmpty() || p == null) return  false;
        return players.removeIf(existing ->existing.getName().equalsIgnoreCase(p.getName()));
    }
    //User enters the names of two players, after confirming they are in the list the two players are listed one after another with their stats shown for the user to compare
    public boolean comparePlayers(){
        if (players == null || players.isEmpty()){
            System.out.println("List is empty, cannot compare players");
            return false;
        }
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
        System.out.println("<><><><><><><><><><><><><>");
        System.out.println("      Stat Comparison       ");
        System.out.println("<><><><><><><><><><><><><>");
        System.out.printf("%s VS. %s\n", p1.getName(), p2.getName());

        int recYard = Math.abs(p1.getReceiving_yards() - p2.getReceiving_yards());
        if (p1.getReceiving_yards() > p2.getReceiving_yards()){
            System.out.printf("%-20s: %s leads %d to %d | %+d yards\n", "Receiving Yards", p1.getName(),p1.getReceiving_yards(), p2.getReceiving_yards(), recYard);
        } else if (p2.getReceiving_yards() > p1.getReceiving_yards()) {
            System.out.printf("%-20s: %s leads %d to %d | %+d yards\n", "Receiving Yards", p2.getName(), p2.getReceiving_yards(), p1.getReceiving_yards(), recYard);
        }
        else{
            System.out.printf("%-20s: %s and %s are tied | %d to %d\n", "Receiving Yards", p1.getName(), p2.getName(), p1.getReceiving_yards(), p2.getReceiving_yards());
        }

        //----------------------------------------------------------------------------------------------------

        int recComp = Math.abs(p1.getReceptions() - p2.getReceptions());
        if (p1.getReceptions() > p2.getReceptions()){
            System.out.printf("%-20s: %s leads %d to %d | %+d receptions\n", "Receptions", p1.getName(), p1.getReceptions(), p2.getReceptions(), recComp);
        } else if (p2.getReceptions() > p1.getReceptions()) {
            System.out.printf("%-20s: %s leads %d to %d | %+d receptions\n", "Receptions", p2.getName(), p2.getReceptions(), p1.getReceptions(), recComp);
        }
        else{
            System.out.printf("%-20s: %s and %s are tied | %d to %d\n", "Receptions", p1.getName(), p2.getName(), p1.getReceptions(), p2.getReceptions());
        }

        //----------------------------------------------------------------------------------------------------

        int tdComp = Math.abs(p1.getTouchdowns() - p2.getTouchdowns());
        if (p1.getTouchdowns() > p2.getTouchdowns()){
            System.out.printf("%-20s: %s leads %d to %d | %+d Touchdowns\n", "Touchdowns", p1.getName(), p1.getTouchdowns(), p2.getTouchdowns(), tdComp);
        } else if (p2.getTouchdowns() > p1.getTouchdowns()) {
            System.out.printf("%-20s: %s leads %d to %d | %+d Touchdowns\n", "Touchdowns", p2.getName(), p2.getTouchdowns(), p1.getTouchdowns(), tdComp);
        }
        else{
            System.out.printf("%-20s: %s and %s are tied | %d to %d\n", "Touchdowns", p1.getName(), p2.getName(), p1.getTouchdowns(), p2.getTouchdowns());
        }

        //----------------------------------------------------------------------------------------------------

        double ypcComp = Math.abs(p1.getYards_per_catch() - p2.getYards_per_catch());
        if (p1.getYards_per_catch() > p2.getYards_per_catch()){
            System.out.printf("%-20s: %s leads %.2f to %.2f | %+.2f Yards Per Catch\n", "Yards Per Catch", p1.getName(), p1.getYards_per_catch(), p2.getYards_per_catch(), ypcComp);
        } else if (p2.getYards_per_catch() > p1.getYards_per_catch()) {
            System.out.printf("%-20s: %s leads %.2f to %.2f | %+.2f Yards Per Catch\n", "Yards Per Catch", p2.getName(), p2.getYards_per_catch(), p1.getYards_per_catch(), ypcComp);
        }
        else{
            System.out.printf("%-20s: %s and %s are tied | %.2f to %.2f\n", "Yards Per Catch", p1.getName(), p2.getName(), p1.getYards_per_catch(), p2.getYards_per_catch());
        }

        //----------------------------------------------------------------------------------------------------
        String yac = "Yards After Catch";
        double yacComp = Math.abs(p1.getYards_after_catch() - p2.getYards_after_catch());
        if (p1.getYards_after_catch() > p2.getYards_after_catch()){
            System.out.printf("%-20s: %s lead %.2f to %.2f | %+.2f Yards After Catch\n", "Yards After Catch", p1.getName(), p1.getYards_after_catch(), p2.getYards_after_catch(), yacComp);
        } else if (p2.getYards_after_catch() > p1.getYards_after_catch()) {
            System.out.printf("%-20s: %s leads %.2f to %.2f | %+.2f Yards After Catch\n", "Yards After Catch", p2.getName(), p2.getYards_after_catch(), p1.getYards_after_catch(), yacComp);
        }
        else{
            System.out.printf("%-20s: %s and %s are tied | %.2f to %.2f\n", yac, p1.getName(), p2.getName(), p1.getYards_after_catch(), p2.getYards_after_catch());
        }
        System.out.println("Press enter to return to the menu\n");
        sc.nextLine();


        return false;
    }

    public String comparePlayers(Player p1, Player p2){
        if(p1 == null || p2 == null) return null;
        int yardDifference = Math.abs(p1.getReceiving_yards() - p2.getReceiving_yards());
        return p1.getName() + "VS" + p2.getName() + "With a difference of: " + yardDifference;
    }
}
