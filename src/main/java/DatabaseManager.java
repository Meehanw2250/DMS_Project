import java.sql.*;
import java.util.ArrayList;

/**
 * Create the overall manager class
 */
public class DatabaseManager {
    private String dbName;

    /**
     * Takes user input, if not empty it uses the given database name, if empty it autofills with "jets"
     * It then prints the filepath and creates a table
     * @param dbName
     */
    public DatabaseManager(String dbName) {
        if (dbName == null || dbName.trim().isEmpty()) {
            this.dbName = "jets";
        } else {
            this.dbName = dbName.trim();
        }
        System.out.println(">>> SQLite DB Path: " + new java.io.File(this.dbName + ".db").getAbsolutePath());
        createTable();
    }

    public DatabaseManager() {
        this("jets");
    }

    /**
     * Creates the file path for the SQLite to run
     * It then makes an active connection to the database
     * @return
     * @throws SQLException
     */
    private Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Sqlite JDBC Driver not found!");
        }

        // Safety check to ensure dbName is never null
        String activeDb = (this.dbName == null || this.dbName.trim().isEmpty()) ? "jets" : this.dbName;
        String url = "jdbc:sqlite:" + activeDb + ".db";
        return DriverManager.getConnection(url);
    }

    /**
     * Creates the query to make the table inside of SQLite
     */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "name TEXT PRIMARY KEY, " +
                "receptions INTEGER, " +
                "receiving_yards INTEGER, " +
                "touchdowns INTEGER, " +
                "ypc REAL, " +
                "yac REAL" + ");";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    /**
     * Creates the array list by querying the database and then printing out the player along with their individual stats from the named columns
     * @return
     */
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
        } catch (SQLException e) {
            System.out.println("Loading error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Assigns all player attributes a number and then pulls them one at a time 1-6 until all question marks are filled to load the table with the player
     * @param p
     */
    public void insertPlayer(Player p) {
        String sql = "INSERT OR REPLACE INTO players(name, receptions, receiving_yards, touchdowns, ypc, yac) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getReceptions());
            pstmt.setInt(3, p.getReceiving_yards());
            pstmt.setInt(4, p.getTouchdowns());
            pstmt.setDouble(5, p.getYards_per_catch());
            pstmt.setDouble(6, p.getYards_after_catch());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }

    /**
     * Queries the database and tells it to update the stats based on what statistic is assigned to the number and then overwrites the entire object to complete the update
     * @param p
     */
    public void updatePlayer(Player p) {
        String sql = "UPDATE players SET receptions = ?, receiving_yards = ?, touchdowns = ?, ypc = ?, yac = ? WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getReceptions());
            pstmt.setInt(2, p.getReceiving_yards());
            pstmt.setInt(3, p.getTouchdowns());
            pstmt.setDouble(4, p.getYards_per_catch());
            pstmt.setDouble(5, p.getYards_after_catch());
            pstmt.setString(6, p.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    /**
     * Simple delete query where if the name matches the user input, the object is removed from the database
     * @param p
     */
    public void deletePlayer(Player p) {
        String sql = "DELETE FROM players WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }
}