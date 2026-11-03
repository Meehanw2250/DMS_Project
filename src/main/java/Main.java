import javax.swing.*;

//Created a jets system object and then ran the runMenu() method
public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new jetsGUI().setVisible(true);
        });
    }
}
