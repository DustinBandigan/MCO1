public class tile {
    private String SoilType;
    private boolean Usable;
    private boolean Fertilized;
    private boolean Watered;
    private int FertileTime;
    private Plant thePlant;

    public tile(){
        this.SoilType="Placeholder"; //Replace with the new overloaded kind of Constructor later.
        this.Fertilized =false;
        this.FertileTime=0;
        this.Usable =true;
        this.Watered=false;
        //this.thePlant=new Plant(); uncomment when I'm stroking

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

    public void setFertileTime(int FertileTime){
        this.FertileTime=FertileTime;
    }

    public boolean isSoilOptimal() {
        return (this.SoilType.equals(this.thePlant.getPreferredSoil()));
    }

    public int getIsGrown(){
        if (this.thePlant.getMaxGrowth()==this.thePlant.getCurrent_growth())
        {return -1;} //return -1 if yes it's grown
        else {return this.thePlant.getCurrent_growth();} //return the stage if it is.
    }

    public String getPlantDisplay(){
        return this.thePlant.getName().substring(0,2).toLowerCase();
        //Submits the Plant Name of the Plant occupying it.
    }

}
