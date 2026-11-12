import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:jets.db";

    public DatabaseManager() {System.out.println(">>> SQLite DB Path: " + new java.io.File("jets.db").getAbsolutePath());
        createTable();

    }
    private Connection connect() throws SQLException {
        try{
            Class.forName("org.sqlite.JDBC");
        }
        catch(ClassNotFoundException e){
            System.out.println("Sqlite JDBC Driver not found!");
        }
        return DriverManager.getConnection(URL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "name TEXT PRIMARY KEY, " +
                "receptions INTEGER, " +
                "receiving_yards INTEGER, " +
                "touchdowns INTEGER, " +
                "ypc REAL, " +
                "yac REAL" + ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        }
        catch (SQLException e){
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM players;";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Player p = new Player(
                        rs.getString("name"),
                        rs.getInt("receptions"),
                        rs.getInt("receiving_yards"),
                        rs.getInt("touchdowns"),
                        rs.getDouble("ypc"),
                        rs.getDouble("yac")
                );
                list.add(p);
            }
        }
        catch (SQLException e){
            System.out.println("Loading error: " + e.getMessage());
        }
        return list;
    }
    //check semicolon after values question marks
    public void insertPlayer(Player p){
        String sql = "INSERT OR REPLACE INTO players(name, receptions, receiving_yards, touchdowns, ypc, yac) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getReceptions());
            pstmt.setInt(3,p.getReceiving_yards());
            pstmt.setInt(4, p.getTouchdowns());
            pstmt.setDouble(5, p.getYards_per_catch());
            pstmt.setDouble(6, p.getYards_after_catch());
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Insert error: " + e.getMessage());
        }
    }
    public void updatePlayer(Player p){
        String sql = "UPDATE players SET receptions = ?, receiving_yards = ?, touchdowns = ?, ypc = ?, yac = ? WHERE name = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, p.getReceptions());
            pstmt.setInt(2, p.getReceiving_yards());
            pstmt.setInt(3, p.getTouchdowns());
            pstmt.setDouble(4, p.getYards_per_catch());
            pstmt.setDouble(5, p.getYards_after_catch());
            pstmt.setString(6, p.getName());
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Update error: " + e.getMessage());
        }
    }
    public void deletePlayer(Player p){
        String sql = "DELETE FROM players WHERE name = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, p.getName());
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Delete error: " + e.getMessage());
        }
    }
}
