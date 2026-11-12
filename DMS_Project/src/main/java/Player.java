public class Player {

    //All necessary object variables

    private String name;
    private Integer receptions;
    private Integer receiving_yards;
    private Integer touchdowns;
    private Double yards_per_catch;
    private Double yards_after_catch;

    //Player object constructor

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

    public String getName() {
        return name;
    }
    public Integer getReceptions() {
        return receptions;
    }
    public Integer getReceiving_yards() {
        return receiving_yards;
    }
    public Integer getTouchdowns() {
        return touchdowns;
    }
    public Double getYards_per_catch() {
        return yards_per_catch;
    }
    public Double getYards_after_catch() {
        return yards_after_catch;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }
    public void setReceptions(Integer receptions) {
        this.receptions = receptions;
    }
    public void setReceiving_yards(Integer receiving_yards) {
        this.receiving_yards = receiving_yards;
    }
    public void setTouchdowns(Integer touchdowns) {
        this.touchdowns = touchdowns;
    }
    public void setYards_per_catch(Double yards_per_catch) {
        this.yards_per_catch = yards_per_catch;
    }
    public void setYards_after_catch(Double yards_after_catch) {
        this.yards_after_catch = yards_after_catch;
    }

    //toString override to allow us flexibility in how information is displayed

    @Override
    public String toString() {
        return String.format("Player: %s | Receptions: %d | Receiving Yards: %d | Touchdowns: %d | Yards Per Catch: %.1f | Yards After Catch: %.1f", name, receptions, receiving_yards, touchdowns, yards_per_catch, yards_after_catch);
    }
}
