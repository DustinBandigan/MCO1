import java.io.BufferedReader;
import java.io.FileReader;

public class tile {
    private String SoilType;
    private boolean Usable;
    private boolean Fertilized;
    private boolean Watered;
    private int FertileTime;
    private Plant current_crop;
    private Plant[] thePlant;

    public tile() throws Exception{
        this.SoilType="Placeholder"; //Replace with the new overloaded kind of Constructor later.
        this.Fertilized =false;
        this.FertileTime=0;
        this.Usable =true;
        this.Watered=false;
        this.thePlant = new Plant[5];

        BufferedReader reader = new BufferedReader(new FileReader("Plants.json"));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        reader.close();
        line=line.replaceAll("[{}]", "");
        line=line.replaceAll("\"", "");
        line=line.replaceAll(" ", "_");
        line=line.replaceAll(",", ":");
        String Holder[]=line.split(":");
        for(int x=0; x<5; x++){
            thePlant[x]=new Plant(Holder[2+(13*x)], Integer.parseInt(Holder[4+(13*x)]), Integer.parseInt(Holder[6+(13*x)]), Integer.parseInt(Holder[8+(13*x)]), (Holder[10+(13*x)]), Integer.parseInt(Holder[12+(13*x)]), x);
        }

    }

    public boolean isUsable(){
        return this.Usable;
    }

    public boolean isFertilized(){
        return this.Fertilized;
    }

    public int getFertileTime(){
        return this.FertileTime;
    }

    public String getSoilType(){
        return this.SoilType;
    }

    public boolean isWatered(){
        return this.Watered;
    }

    public void setWatered(boolean Watered){
        this.Watered=Watered;
    }

    public void setFertilized(boolean Fertilized){
        this.Fertilized=Fertilized;
    }

    public void setUsable(boolean Usable){
        this.Usable=Usable;
    }

    public void setSoilType(String SoilType){
        this.SoilType=SoilType;
    }


    public void setFertileTime(int FertileTime){
        this.FertileTime=FertileTime;
    }

    public boolean isSoilOptimal() {
        return (this.SoilType.equals(this.current_crop.getPreferredSoil()));
    }

    public int getIsGrown(){
        if (this.current_crop.getCurrent_growth()==this.thePlant[this.current_crop.getKey()].getMaxGrowth())
        {return -1;} //return -1 if yes it's grown
        else {return this.current_crop.getCurrent_growth();} //return the stage if it is.
    }

    public String getPlantDisplay(){
        return this.current_crop.getName().substring(0,2).toLowerCase();
        //Submits the Plant Name of the Plant occupying it.
    }

    public void plantCrop(int i){
        this.current_crop=this.thePlant[i];
    }

    public void removeCrop(int i){

        this.current_crop.resetCrop();
    }

}
