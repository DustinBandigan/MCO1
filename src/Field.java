 public class Field {
    private tile[][] Tiles;
    private String[][] DisplayTiles;

    public Field(){
        Tiles=new tile[2][2];
        DisplayTiles=new String[2][2];
        for(int x=0; x<2; x++){
            for(int y=0; y<2; y++) {Tiles[x][y] = new tile();}
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
        this.DisplayTiles[x][y]="*";
        this.Tiles[x][y].setUsable(false);
    }

}
