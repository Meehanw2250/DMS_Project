import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//All backend processes and the arraylist to store everything
public class jetsGUI extends JFrame {
    private jets_System jetsSystem;
    private FileManagerTools fileManagerTools;
    private ArrayList<Player> players;
    private String fileName = "Jets.txt";

    private static final  Color JETS_GREEN = new Color(12, 55, 29);

    private JTextArea displayArea;
    private JTextField nameInput, recInput, ydsInput, tdInput, ypcInput, yacInput;

public jetsGUI(){
    this.fileManagerTools = new FileManagerTools();
    this.fileName = "Jets.txt";
    this.players = fileManagerTools.loadFromFile(fileName);
    this.jetsSystem = new jets_System();

    setTitle("The New York Jets Stats Vault");
    setValueFrame();
}
//Formatting entire GUI, adding text display area, adding the banner, adds all input areas, adds buttons
public void setValueFrame(){
    setSize(700,550);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));

    //Top green jets banner
    JPanel topPanel = new JPanel();
    topPanel.setBackground(JETS_GREEN);
    JLabel titleLabel = new JLabel("NEW YORK JETS RECEIVERS");
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    topPanel.add(titleLabel);
    add(topPanel,BorderLayout.NORTH);

    //Making the bottom panel for the user input sections and the buttons
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));

    //Making the center display area
    displayArea = new JTextArea();
    displayArea.setEditable(false);
    displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    add(new JScrollPane(displayArea),BorderLayout.CENTER);

    //Input areas for adding individuals statistics
    JPanel inputGrid = new JPanel(new GridLayout(3,4,5,5));
    inputGrid.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

    inputGrid.add(new JLabel("Name:"));
    nameInput = new JTextField();
    inputGrid.add(nameInput);

    inputGrid.add(new JLabel("Receptions:"));
    recInput = new JTextField();
    inputGrid.add(recInput);

    inputGrid.add(new JLabel("Receiving Yards:"));
    ydsInput = new JTextField();
    inputGrid.add(ydsInput);

    inputGrid.add(new JLabel("Touchdowns:"));
    tdInput = new JTextField();
    inputGrid.add(tdInput);

    inputGrid.add(new JLabel("Yards Per Catch:"));
    ypcInput = new JTextField();
    inputGrid.add(ypcInput);

    inputGrid.add(new JLabel("Yards After Catch:"));
    yacInput = new JTextField();
    inputGrid.add(yacInput);

    bottomPanel.add(inputGrid);

    //Adding the interactive buttons below the user input section
    JPanel buttonRow = new JPanel(new FlowLayout());
    JButton addPlayerButton = new JButton("Add Player");
    JButton searchButton = new JButton("Search Player");
    JButton compareButton = new JButton("Compare Players");
    JButton updateButton = new JButton("Update Player");
    JButton removePlayerButton = new JButton("Remove Player");
    JButton refreshButton = new JButton("Refresh Players");

    //Add the buttons we made to the row
    buttonRow.add(addPlayerButton);
    buttonRow.add(searchButton);
    buttonRow.add(compareButton);
    buttonRow.add(updateButton);
    buttonRow.add(removePlayerButton);
    buttonRow.add(refreshButton);

    //Add the button row to the bottom panel
    bottomPanel.add(buttonRow);
    add(bottomPanel,BorderLayout.SOUTH);

    //connecting the actual methods to the buttons
    setupButtons(addPlayerButton, searchButton, compareButton, updateButton, removePlayerButton, refreshButton);

    //Everytime the program starts, show the base roster
    refreshDisplayList();
}
private void setupButtons(JButton addPlayerButton, JButton searchButton, JButton compareButton, JButton updateButton, JButton removePlayerButton, JButton refreshButton){
//Adding all buttons along with the backend functions of them
  addPlayerButton.addActionListener(e -> {
      try {
          String name = nameInput.getText().trim();
          if(name.isEmpty()){
              displayArea.setText("Whoops, player name cannot be empty");
              return;
          }
          int rec = Integer.parseInt(recInput.getText().trim());
          int yds = Integer.parseInt(ydsInput.getText().trim());
          int td = Integer.parseInt(tdInput.getText().trim());
          double ypc = Double.parseDouble(ypcInput.getText().trim());
          double yac = Double.parseDouble(yacInput.getText().trim());

          Player newPlayer = new Player(name, rec, yds, td, ypc, yac);
          players.add(newPlayer);
          fileManagerTools.saveToFile(players, fileName);

          displayArea.setText("New Player Added: " + name);
          clearInputs();
          refreshDisplayList();

      }
      catch (NumberFormatException ex){
          displayArea.setText("Please enter a number");
      }
  });

  searchButton.addActionListener(e -> {
      String searchName = nameInput.getText().trim();
      if(searchName.isEmpty()){
          displayArea.setText("Please enter a name in the search bar");
          return;
      }
      Player foundPlayer = null;
      for(Player p : players){
          if(p.getName() != null && p.getName().equalsIgnoreCase(searchName)){
              foundPlayer = p;
              break;
          }
      }
      if(foundPlayer != null){
          String info = "=== Player Info ===\n\n" +
                  "Name:               " + foundPlayer.getName() + " \n" +
                  "Receptions:         " + foundPlayer.getReceptions() + " \n" +
                  "Receiving Yards:    " + foundPlayer.getReceiving_yards() + " \n" +
                  "Touchdowns:         " + foundPlayer.getTouchdowns() + " \n" +
                  "Yards Per Catch:    " +foundPlayer.getYards_per_catch() +  " \n" +
                  "Yards After Catch:  " + foundPlayer.getYards_after_catch() + " \n";
          displayArea.setText(info);


      }
      else{
          displayArea.setText("Player not found");
      }
  });

  compareButton.addActionListener(e -> {
      String player1Name = nameInput.getText().trim();
      if(player1Name.isEmpty()){
          displayArea.setText("Please enter the first name in the search bar");
          return;
      }
      Player p1 = findPlayerByName(player1Name);
      if(p1 == null){
          displayArea.setText(player1Name + " not found in roster");
          return;
      }
      String player2Name = JOptionPane.showInputDialog(this, "enter the second name in the search bar" + ":", "Compare Receiver Stats", JOptionPane.QUESTION_MESSAGE);

      if(player2Name == null || player2Name.trim().isEmpty()){
          return;
      }
      Player p2 = findPlayerByName(player2Name.trim());
      if(p2 == null){
          displayArea.setText(player2Name + " not found in roster");
          return;
      }
      StringBuilder sb = new StringBuilder();
      sb.append("=== Player Stat Comparison ===\n");
      sb.append(String.format("%-15s |     %-25s\n", p1.getName(), p2.getName()));
      sb.append("----------------------------------------------------------------------\n");
      sb.append(String.format("Receptions:      %-13d | Receptions: %-13d\n", p1.getReceptions(), p2.getReceptions()));
      sb.append(String.format("Receiving Yards: %-13d | Receiving Yards: %-13d\n", p1.getReceiving_yards(), p2.getReceiving_yards()));
      sb.append(String.format("Touchdowns:      %-13d | Touchdowns: %-13d\n", p1.getTouchdowns(), p2.getTouchdowns()));
      sb.append(String.format("Yards Per Catch:   %-13.1f | Yards Per Catch: %-13.1f\n", p1.getYards_per_catch(), p2.getYards_per_catch()));
      sb.append(String.format("Yards After Catch: %-13.1f | Yards After Catch: %-13.1f\n", p1.getYards_after_catch(), p2.getYards_after_catch()));

      displayArea.setText(sb.toString());


  });

  updateButton.addActionListener(e -> {
      String searchName = nameInput.getText().trim();
      if(searchName.isEmpty()){
          displayArea.setText("Please enter the name in the search bar");
          return;
      }
      Player targetPlayer = findPlayerByName(searchName);
      if(targetPlayer == null) {
          displayArea.setText("Player not found");
          return;
      }
      try{
          if(!recInput.getText().trim().isEmpty()){targetPlayer.setReceptions(Integer.parseInt(recInput.getText().trim()));}

          if(!ydsInput.getText().trim().isEmpty()){targetPlayer.setReceiving_yards(Integer.parseInt(ydsInput.getText().trim()));}

          if(!tdInput.getText().trim().isEmpty()){targetPlayer.setTouchdowns(Integer.parseInt(tdInput.getText().trim()));}

          if(!ypcInput.getText().trim().isEmpty()){targetPlayer.setYards_per_catch(Double.parseDouble(ypcInput.getText().trim()));}

          if(!yacInput.getText().trim().isEmpty()){targetPlayer.setYards_after_catch(Double.parseDouble(yacInput.getText().trim()));}

          fileManagerTools.saveToFile(players, fileName);
          displayArea.setText("Player Updated");
          clearInputs();
          refreshDisplayList();
      }
      catch(NumberFormatException nfe){
          displayArea.setText("Please enter valid stats into the stat bars");
      }

  });

  removePlayerButton.addActionListener(e -> {
      String searchName = nameInput.getText().trim();
      if(searchName.isEmpty()){
          displayArea.setText("Please enter a name in the name bar");
          return;
      }
      boolean removed = players.removeIf(p -> p.getName() != null && p.getName().equalsIgnoreCase(searchName));
      if(removed){
          fileManagerTools.saveToFile(players, fileName);
          displayArea.setText(searchName + " has been removed");
          clearInputs();
          refreshDisplayList();
      }
      else {
          displayArea.setText(searchName + " not found on the roster");
      }

  });
  refreshButton.addActionListener(e -> refreshDisplayList());
}
//Sorts through the list of players and finds the player that matches the user input
private Player findPlayerByName(String name){
    for(Player p : players){
        if(p.getName() != null && p.getName().equalsIgnoreCase(name)){
            return p;
        }
    }
    return null;
}
//Clears all inputs in the input boxes
private void clearInputs(){
    recInput.setText("");
    ydsInput.setText("");
    ypcInput.setText("");
    yacInput.setText("");
    nameInput.setText("");
    tdInput.setText("");
}
//Redisplays full list of players and their stats
private void refreshDisplayList(){
    StringBuilder sb = new StringBuilder("=== Current Player List (" + fileName + ") ===\n\n");
    if(players.isEmpty()){
        sb.append("No players in roster");
    }
    else{
        for(Player p : players){
            sb.append("• ").append(p.toString()).append("\n");
        }
    }
    displayArea.setText(sb.toString());
}


}
