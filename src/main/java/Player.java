/**
 * Creates the overall player object that holds all individual statistics
 */
public class Player {

    /**
     * All necessary object variables
     */

    private String name;
    private Integer receptions;
    private Integer receiving_yards;
    private Integer touchdowns;
    private Double yards_per_catch;
    private Double yards_after_catch;

    //Player object constructor

    /**
     * The player object constructor that takes the 6 following items as parameters
     * @param name
     * @param receptions
     * @param receiving_yards
     * @param touchdowns
     * @param yards_per_catch
     * @param yards_after_catch
     */
    public Player(String name, Integer receptions, Integer receiving_yards, Integer touchdowns, Double yards_per_catch, Double yards_after_catch) {
        this.name = name;
        this.receptions = receptions;
        this.receiving_yards = receiving_yards;
        this.touchdowns = touchdowns;
        this.yards_per_catch = yards_per_catch;
        this.yards_after_catch = yards_after_catch;
    }
    public Player() {
    }

    //Getters

    /**
     * Gets player name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets player receptions
     * @return
     */
    public Integer getReceptions() {
        return receptions;
    }

    /**
     * Gets player yards
     * @return
     */
    public Integer getReceiving_yards() {
        return receiving_yards;
    }

    /**
     * gets player touchdowns
     * @return
     */
    public Integer getTouchdowns() {
        return touchdowns;
    }

    /**
     * Gets player yards per catch
     * @return
     */
    public Double getYards_per_catch() {
        return yards_per_catch;
    }

    /**
     * Gets player yards after catch
     * @return
     */
    public Double getYards_after_catch() {
        return yards_after_catch;
    }


    /**
     * Sets player name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets player receptions
     * @param receptions
     */
    public void setReceptions(Integer receptions) {
        this.receptions = receptions;
    }

    /**
     * Sets player receiving yards
     * @param receiving_yards
     */
    public void setReceiving_yards(Integer receiving_yards) {
        this.receiving_yards = receiving_yards;
    }

    /**
     * Sets player touchdowns
     * @param touchdowns
     */
    public void setTouchdowns(Integer touchdowns) {
        this.touchdowns = touchdowns;
    }

    /**
     * Sets player yards per catch
     * @param yards_per_catch
     */
    public void setYards_per_catch(Double yards_per_catch) {
        this.yards_per_catch = yards_per_catch;
    }

    /**
     * Sets player yards after catch
     * @param yards_after_catch
     */
    public void setYards_after_catch(Double yards_after_catch) {
        this.yards_after_catch = yards_after_catch;
    }

    /**
     * toString override to allow us flexibility in how information is displayed
     * @return
     */
    @Override
    public String toString() {
        return String.format("Player: %s | Receptions: %d | Receiving Yards: %d | Touchdowns: %d | Yards Per Catch: %.1f | Yards After Catch: %.1f", name, receptions, receiving_yards, touchdowns, yards_per_catch, yards_after_catch);
    }
}
