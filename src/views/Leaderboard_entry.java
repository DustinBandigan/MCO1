package views;

public class Leaderboard_entry {
    private int final_score;
    private String name;

    public Leaderboard_entry(String name, int score){
        this.final_score = score;
        this.name = name;
    }

    public int getFinalScore(){
        return this.final_score;
    }

    public String getName(){
        return this.name;
    }

}
