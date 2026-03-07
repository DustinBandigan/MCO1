import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Leaderboard {
    private ArrayList<Leaderboard_entry> Highscore_List;

    public Leaderboard() throws Exception{
        Highscore_List = new ArrayList<>();

        String Holder[];
        BufferedReader reader = new BufferedReader(new FileReader("HighScores.json"));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        reader.close();
        line=line.replaceAll("[{}]", "");
        line=line.replaceAll("\"", "");
        line=line.replaceAll(" ", "_");
        line=line.replaceAll(",", ":");
        Holder=line.split(":");
        for(int x=0; x<3; x++) {
            this.Highscore_List.add(new Leaderboard_entry(Holder[2 + (5 * x)], Integer.parseInt(Holder[4 + (5 * x)])));
        }
    }

    public void register_score(String name, int score){
        this.Highscore_List.add(new Leaderboard_entry(name, score)); //Add name
        this.Highscore_List.sort(Comparator.comparingInt(Leaderboard_entry::getFinalScore).reversed()); //Sort
        if(this.Highscore_List.size()>10)
            this.Highscore_List.remove(10); //Remove tenth Index; //Remove tenth Index
    }

    public void display_score(){
        for(Leaderboard_entry entry:Highscore_List){
            System.out.println(entry.getName() + " " + entry.getFinalScore());
        }
    }


}
