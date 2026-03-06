import java.util.Scanner;

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
        DisplayMap.Meteor(scanner.nextInt(),scanner.nextInt());
        System.out.println(DisplayMap.getTile(0,0) +" || "+ DisplayMap.getTile(0,1));
        System.out.println(DisplayMap.getTile(1,0));
        //Write an instruction about
    }

}
