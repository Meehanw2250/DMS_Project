import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

//All backend processes and the arraylist to store everything
public class jetsGUI extends JFrame {
    private jets_System jetsSystem;
    private FileManagerTools fileManagerTools;

    private JTable jetsTable;
    private DefaultTableModel jetsTableModel;

    private JTextField nameField, tdsField, yardsField, recField, ypcField, yacField;
    private JLabel statusLabel;

    public jetsGUI() {
        jetsSystem = new jets_System();
        fileManagerTools = new FileManagerTools();
        setTitle("New York Jets Receiver Stats");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(18, 87, 64));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("New York Jets Receiver Stats");
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        leftPanel.setPreferredSize(new Dimension(300, 0));

        leftPanel.add(createFieldRow("Player Name: ", nameField = new JTextField(15)));
        leftPanel.add(createFieldRow("Touchdowns (TD):", tdsField = new JTextField(15)));
        leftPanel.add(createFieldRow("Receiving Yards:", yardsField = new JTextField(15)));
        leftPanel.add(createFieldRow("Receptions:", recField = new JTextField(15)));
        leftPanel.add(createFieldRow("Yards/Catch (YPC):", ypcField = new JTextField(15)));
        leftPanel.add(createFieldRow("Yards After Catch (YAC):", yacField = new JTextField(15)));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton clearButton = new JButton("Clear");
        JButton removeButton = new JButton("Remove");

        JButton [] actionButtons =  {addButton, updateButton, clearButton, removeButton};
        for (JButton button : actionButtons) {
            button.setBackground(new Color (18, 87, 64));
            button.setForeground(Color.white);
            button.setOpaque(true);
            button.setBorderPainted(false);
        }


        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(removeButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(buttonPanel);

        JButton compareButton = new JButton("Compare Selected Players");
        compareButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(compareButton);

        compareButton.setBackground(Color.black); // Dark Navy
        compareButton.setForeground(Color.WHITE);
        compareButton.setOpaque(true);
        compareButton.setBorderPainted(false);

        add(leftPanel, BorderLayout.WEST);

        String[] columns = {"Name", "TDs", "Yards", "Receptions", "YPC", "YAC"};
        jetsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jetsTable = new JTable(jetsTableModel);
        jetsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTableHeader header = jetsTable.getTableHeader();
        header.setBorder(BorderFactory.createLineBorder(Color.black));
        header.setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(jetsTable);
        add(scrollPane, BorderLayout.CENTER);

        //Bottom Bar

        statusLabel = new JLabel("Select Player in row to edit or remove.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);

        //Listeners

        jetsTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = jetsTable.getSelectedRow();
            if (selectedRow >= 0 && !e.getValueIsAdjusting()) {
                nameField.setText(jetsTableModel.getValueAt(selectedRow, 0).toString());
                tdsField.setText(jetsTableModel.getValueAt(selectedRow, 1).toString());
                yardsField.setText(jetsTableModel.getValueAt(selectedRow, 2).toString());
                recField.setText(jetsTableModel.getValueAt(selectedRow, 3).toString());
                ypcField.setText(jetsTableModel.getValueAt(selectedRow, 4).toString());
                yacField.setText(jetsTableModel.getValueAt(selectedRow, 5).toString());
                statusLabel.setText(" Row " + (selectedRow + 1) + " selected. Make your changes and click Update.");
            }
        });

        //Clear Button

        clearButton.addActionListener(e -> clearForm());

        //Add Button
        addButton.addActionListener(e -> {
            try{
                String name = nameField.getText().trim();
                int tds = Integer.parseInt(tdsField.getText().trim());
                int yards = Integer.parseInt(yardsField.getText().trim());
                int rec = Integer.parseInt(recField.getText().trim());
                double ypc = Double.parseDouble(ypcField.getText().trim());
                double yac = Double.parseDouble(yacField.getText().trim());

                if (name.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a name, it cannot be empty!");
                    return;
                }
                Player newPlayer = new Player(name,rec, yards, tds, ypc, yac);
                jetsSystem.addPlayer(newPlayer);
                fileManagerTools.saveToFile(jetsSystem.getPlayers(), "Jets.txt");

                loadTableData();
                clearForm();
                statusLabel.setText(" Added " + name + " successfully. ");
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = jetsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select a player from the table to update.");
                return;
            }
            try {
                String name = nameField.getText().trim();
                int tds = Integer.parseInt(tdsField.getText().trim());
                int yards = Integer.parseInt(yardsField.getText().trim());
                int rec = Integer.parseInt(recField.getText().trim());
                double ypc = Double.parseDouble(ypcField.getText().trim());
                double yac = Double.parseDouble(yacField.getText().trim());

                Player target = jetsSystem.displayPlayer(jetsTableModel.getValueAt(selectedRow, 0).toString());
                if(target != null){
                    jetsSystem.updatePlayer(target, rec, yards, tds, ypc, yac);
                    fileManagerTools.saveToFile(jetsSystem.getPlayers(), "Jets.txt");
                    loadTableData();
                    clearForm();
                    statusLabel.setText(" Updated " + name + " successfully. ");
                }
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            }
        });

        //Remove Button
        removeButton.addActionListener(e -> {
            int selectedRow = jetsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select a player from the table to remove.");
                return;
            }
            String playerName = jetsTableModel.getValueAt(selectedRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove " + playerName + "?", "Confirm to delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Player target = jetsSystem.displayPlayer(playerName);
                if (target != null) {
                    jetsSystem.deletePlayer(target);
                    fileManagerTools.saveToFile(jetsSystem.getPlayers(), "Jets.txt");
                    loadTableData();
                    clearForm();
                    statusLabel.setText(" Removed " + playerName + " successfully. ");
                }
            }
        });

        //Compare Button
        compareButton.addActionListener(e -> {
            ArrayList<Player> players = jetsSystem.getPlayers();
            if(players.size()<2){
                JOptionPane.showMessageDialog(this, "Please select at least two players to compare!");
                return;
            }
            String[] names = players.stream().map(Player::getName).toArray(String[]::new);
            String p1Name = (String) JOptionPane.showInputDialog(this, "Select First Player: ", "Compare Players", JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
            if(p1Name==null) return;

            String p2Name = (String) JOptionPane.showInputDialog(this, "Select Second Player: ", "Compare Players", JOptionPane.QUESTION_MESSAGE, null, names, names[1]);
            if(p2Name==null) return;

            Player p1 = null;
            Player p2 = null;

            for (Player p : players) {
                if(p.getName().equals(p1Name)){
                    p1 = p;
                }
                if(p.getName().equals(p2Name)){
                    p2 = p;
                }
            }
            if(p1 != null && p2 != null){
                String comparisonResult = jetsSystem.comparePlayers(p1, p2);
                JOptionPane.showMessageDialog(this, comparisonResult, "Player Comparison", JOptionPane.INFORMATION_MESSAGE );
            }
        });

        loadTableData();

    }

    private JPanel createFieldRow(String labelText,JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private void loadTableData(){
        jetsTableModel.setRowCount(0);
        ArrayList<Player> players = jetsSystem.getPlayers();
        if(players!= null){
            for(Player p : players){
                String formattedYpc = String.format("%.1f", p.getYards_per_catch());
                String formattedYac = String.format("%.1f", p.getYards_after_catch());
                Object[] row = {
                        p.getName(),
                        p.getTouchdowns(),
                        p.getReceiving_yards(),
                        p.getReceptions(),
                        formattedYpc,
                        formattedYac
                };
                jetsTableModel.addRow(row);
            }
        }
    }

    private void clearForm(){
        nameField.setText("");
        tdsField.setText("");
        yardsField.setText("");
        recField.setText("");
        ypcField.setText("");
        yacField.setText("");
        jetsTable.clearSelection();
        statusLabel.setText("Cleared, time to add a new player");

    }

}
