import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class Driver {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;
        Field DisplayMap = new Field();
        DisplayMap.setAllTiles();
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        //Write an instruction that says "Water this tile" also Test if Water stops the thing or not.
        if(!DisplayMap.WaterTile(scanner.nextInt(),scanner.nextInt())){System.out.println("Alr Watered");}
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        //Write an instruction about Fertilizing Soil
        if(!DisplayMap.FertilizeTile(scanner.nextInt(),scanner.nextInt())){System.out.println("Alr Fertilized");}
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        DisplayMap.Meteor(scanner.nextInt(),scanner.nextInt());
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        //Write an instruction about checking for Meteor
        DisplayMap.checkMeteor(scanner.nextInt(), scanner.nextInt());
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        //Write an instruction about removing the Meteor.
        DisplayMap.removeMeteor(scanner.nextInt(), scanner.nextInt());
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        //You can remove the thing below to stop errors. Other things commented out are being tested rn.
        BufferedReader reader = new BufferedReader(new FileReader("fertilizers.json"));

    }

}
