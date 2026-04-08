import java.io.BufferedReader;
import java.io.FileReader;

public class Field {
    private Tile[][] Tiles;
    private String[][] DisplayTiles;
    private Fertilizer[] theFertilizer;


    public Field() throws Exception{
        Tiles=new Tile[10][10];
        DisplayTiles=new String[10][10];
        theFertilizer=new Fertilizer[3];

        BufferedReader reader = new BufferedReader(new FileReader("MCO1-main/Map.json"));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        reader.close();
        line=line.replaceAll("[{}]", "");
        line=line.replaceAll("\"", "");
        line=line.replaceAll(" ", "_");
        line=line.replaceAll(",", ":");
        line=line.replaceAll(",", ":");
        line=line.replaceAll("[\\[\\]]", ":");
        line=line.replaceAll(":::", ":");
        line=line.replaceAll("::", ":");
        line=line.substring(34);
        String Holder2[]=line.split(":");


        BufferedReader reader2 = new BufferedReader(new FileReader("MCO1-main/Fertilizers.json"));
        StringBuilder sb2 = new StringBuilder();
        String line2 = reader2.readLine();
        reader2.close();
        line2=line2.replaceAll("[{}]", "");
        line2=line2.replaceAll("\"", "");
        line2=line2.replaceAll(" ", "_");
        line2=line2.replaceAll(",", ":");
        String Holder[]=line2.split(":");

        int index=0;
        for(int x=0; x<10; x++){
            for(int y=0; y<10; y++) {
                Tiles[x][y] = new Tile();
                DisplayTiles[x][y]=Holder2[index];
                index++;
            }
        }

        for(int x=0; x<3; x++){
            theFertilizer[x]= new Fertilizer(Holder[2+(7*x)], Integer.parseInt(Holder[4+(7*x)]), Integer.parseInt(Holder[6+(7*x)]));
        }
        //Note, index 0 is quick, 1 is lasting, 2 is quality for the Fertilizer index.
    }

    public String getDisplayTile(int x, int y){
        return this.DisplayTiles[x][y];
    }

    public Tile getGameTile(int x, int y){
        return this.Tiles[x][y];
    }

    public boolean WaterTile(int x, int y){
        if (this.Tiles[x][y].isUsable()) {
            if (this.Tiles[x][y].isWatered())
            {return false;}

            else {
                this.Tiles[x][y].setWatered(true);
                this.DisplayTiles[x][y] = "." + this.DisplayTiles[x][y] + ".";
                return true;
            }
        }
        else {return false;}
    }

    public boolean FertilizeTile(int x, int y){
        if (this.Tiles[x][y].isUsable()){
            if (this.Tiles[x][y].isFertilized())
            {return false;}

            else {
                this.Tiles[x][y].setFertilized(true);
                this.DisplayTiles[x][y] = "(" + this.DisplayTiles[x][y] + ")";
                return true;
            }
        }
        else {return false;}
    }

    public void Meteor(int x, int y){
        this.DisplayTiles[x][y]="*"+this.DisplayTiles[x][y]+"*";
        this.Tiles[x][y].setUsable(false);
    }

    public boolean checkMeteor(int x, int y) {
        if (this.DisplayTiles[x][y].contains("*") && !(this.Tiles[x][y].isUsable()))
        {System.out.println("Yeah there's a meteor there."); return true; }

        else {return false;}
    }

    public void removeMeteor(int x, int y){
        this.DisplayTiles[x][y]=this.DisplayTiles[x][y].replace("*","");
        this.Tiles[x][y].setUsable(true);
        this.Tiles[x][y].setFertilized(true);
        this.Tiles[x][y].setFertileTime(67);
    }

    public void checkSoilType(int x, int y){
        if(this.Tiles[x][y].isSoilOptimal())
        {this.DisplayTiles[x][y]=this.DisplayTiles[x][y].toUpperCase();}
    }

    public void updateGrowth(int x, int y, int i){
        int z=this.Tiles[x][y].getCurrent_crop().getCurrent_growth();
        if (z==-1){this.DisplayTiles[x][y]=this.DisplayTiles[x][y]+"!";}

        else {
            this.DisplayTiles[x][y]=this.DisplayTiles[x][y].replaceAll("0","");
            this.DisplayTiles[x][y]=this.DisplayTiles[x][y].replaceAll("[1-9]",Integer.toString(i));
        }
    }

    public void updatePlant(int x, int y){
        this.DisplayTiles[x][y]=this.DisplayTiles[x][y].replaceAll("[a-zA-z]",this.Tiles[x][y].getPlantDisplay());
    }

    public void expireFertility(int x, int y) {
        if (this.Tiles[x][y].isFertilized()) {
            if (this.Tiles[x][y].getFertileTime() == 0) {
                this.Tiles[x][y].setFertilized(false);
                this.DisplayTiles[x][y]=this.DisplayTiles[x][y].replaceAll("[()]", "");
            }
        }
    }

    public void applyFertilizer(int x, int y, int days){
        if(!this.Tiles[x][y].isFertilized()) {
            this.Tiles[x][y].setFertilized(true);
            this.Tiles[x][y].setFertileTime(days);
        }
    }

    //This function will return the amount of crop yielded
    public int yieldCrop(int x, int y){
        String holder = this.Tiles[x][y].getCurrent_crop().getState();
        Special_Plant plant_holder = (Special_Plant) this.Tiles[x][y].getCurrent_crop();

        //If a plant is harvested when it's in a Seedling, Dormant, or Energized State it doesn't yield anything.
        if(holder.equals("Special") || holder.equals("Seedling")){
            return 0;
        }

        //If a plant is harvested in its lose, it yields normal.
        else if(holder.equals("Low")){
            return this.Tiles[x][y].getCurrent_crop().getYield();
        }

        //The last two states, Mature and High yield the same amount of crop, which is *2
        else {
            return this.Tiles[x][y].getCurrent_crop().getYield()*2;
        }

    }

    //This gets the final price of the crop if you're going to sell / harvest it, you can use this to make sure it returns 0.
    public int sellCrop(int x, int y){
        int yield=yieldCrop(x,y);
        String holder = this.Tiles[x][y].getCurrent_crop().getState();
        Special_Plant plant_holder = (Special_Plant) this.Tiles[x][y].getCurrent_crop();

        //It's either special root crop case + high productive, or root crop + Mature, non_root + Mature/High, or just Low / 0 if no yield.

        if(this.Tiles[x][y].getCurrent_crop() instanceof Special_Plant){
            //If a Root Crop is in high productive it yields 2x as much AND has 50% bonus, hence the calculation
            if(Boolean.TRUE.equals(plant_holder.IsRoot_Crop()) && holder.equals("High")){
                    return (int) Math.round(plant_holder.getHigh_price()*1.5) * yield;
            }
        }

        if(holder.equals("Mature") || holder.equals("High")){
            //Sells for high productive price, yield already calculated
            return yield*this.Tiles[x][y].getCurrent_crop().getHigh_price();
        }

        //Sells for either 0 or low productive price.
        return yield*this.Tiles[x][y].getCurrent_crop().getLow_price();


    }

}
