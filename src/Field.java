 public class Field {
    private tile[][] Tiles;
    private String[][] DisplayTiles;
    private Fertilizer[] theFertilizer;

    public Field(){
        Tiles=new tile[2][2];
        DisplayTiles=new String[2][2];
        theFertilizer=new Fertilizer[3];
        for(int x=0; x<2; x++){
            for(int y=0; y<2; y++) {Tiles[x][y] = new tile();}
        }

        for(int x=0; x<3; x++){
            //theFertilizer[x]= new Fertilizer();
        }
    }

    public void setAllTiles(){
        DisplayTiles[0][0]="l";
        DisplayTiles[0][1]="s";
        DisplayTiles[1][0]="g";
    }

    public String getTile(int x, int y){
        return this.DisplayTiles[x][y];
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

    public void updateGrowth(int x, int y){
        int i=this.Tiles[x][y].getIsGrown();
        if (i==-1){this.DisplayTiles[x][y]=this.DisplayTiles[x][y]+"!";}

        else {this.DisplayTiles[x][y]=this.DisplayTiles[x][y].replaceAll("[0-9]",Integer.toString(i));}
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

    public void applyFertilizer(int x, int y){

    }
}
