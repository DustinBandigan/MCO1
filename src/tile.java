public class tile {
    private String SoilType;
    private boolean Usable;
    private boolean Fertilized;
    private boolean Watered;
    private int FertileTime;

    public tile(){
        this.SoilType="Placeholder"; //Replace with the new overloaded kind of Constructor later.
        this.Fertilized =false;
        this.FertileTime=0;
        this.Usable =true;
        this.Watered=false;
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

}
