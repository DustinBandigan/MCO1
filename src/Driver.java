import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Driver {
    static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int input;
        Field DisplayMap = new Field();
        DisplayMap.setAllTiles();
        String Holder[];
        //scanner.nextInt()
        BufferedReader reader = new BufferedReader(new FileReader("Plants.json"));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        reader.close();
        line=line.replaceAll("[{}]", "");
        line=line.replaceAll("\"", "");
        line=line.replaceAll(" ", "_");
        line=line.replaceAll(",", ":");
        System.out.println(line);
        Holder=line.split(":");

    }

}
/*

//You can remove the thing below to stop errors. Other things commented out are being tested rn. Recycle for reading
        BufferedReader reader = new BufferedReader(new FileReader("Fertilizers.json"));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        reader.close();
        System.out.println(line);
        line=line.replaceAll("[{}]", "");
        line=line.replaceAll("\"", "");
        line=line.replaceAll(" ", "_");
        line=line.replaceAll(",", ":");
        System.out.println(line);
        Holder=line.split(":");

 System.out.println(DisplayMap.getDisplayTile(0,0) +" || "+ DisplayMap.getDisplayTile(0,1));
        System.out.println(DisplayMap.getDisplayTile(1,0));
        //Write an instruction that says "Water this tile" also Test if Water stops the thing or not.
        if(!DisplayMap.WaterTile(scanner.nextInt(),scanner.nextInt())){System.out.println("Alr Watered");}
        System.out.println(DisplayMap.getDisplayTile(0,0) +" || "+ DisplayMap.getDisplayTile(0,1));
        System.out.println(DisplayMap.getDisplayTile(1,0));
        //Write an instruction about Fertilizing Soil
        if(!DisplayMap.FertilizeTile(scanner.nextInt(),scanner.nextInt())){System.out.println("Alr Fertilized");}
        System.out.println(DisplayMap.getDisplayTile(0,0) +" || "+ DisplayMap.getDisplayTile(0,1));
        System.out.println(DisplayMap.getDisplayTile(1,0));
        DisplayMap.Meteor(scanner.nextInt(),scanner.nextInt());
        System.out.println(DisplayMap.getDisplayTile(0,0) +" || "+ DisplayMap.getDisplayTile(0,1));
        System.out.println(DisplayMap.getDisplayTile(1,0));
        //Write an instruction about checking for Meteor
        DisplayMap.checkMeteor(scanner.nextInt(), scanner.nextInt());
        System.out.println(DisplayMap.getDisplayTile(0,0) +" || "+ DisplayMap.getDisplayTile(0,1));
        System.out.println(DisplayMap.getDisplayTile(1,0));
        //Write an instruction about removing the Meteor.
        DisplayMap.removeMeteor(scanner.nextInt(), scanner.nextInt());
        System.out.println(DisplayMap.getDisplayTile(0,0) +" || "+ DisplayMap.getDisplayTile(0,1));
        System.out.println(DisplayMap.getDisplayTile(1,0));

        Block of code that contains all our texts
 */