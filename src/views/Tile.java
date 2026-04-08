package views;

public class Tile {
    private String SoilType;
    private boolean Usable;
    private boolean Fertilized;
    private boolean Watered;
    private int FertileTime;
    private Plant current_crop;
    private Plant[] thePlant;

    public Tile() throws Exception{
        this.SoilType="Placeholder"; //Replace with the new overloaded kind of Constructor later.
        this.Fertilized =false;
        this.FertileTime=0;
        this.Usable =true;
        this.Watered=false;
        //Initializes the plant choices.

        this.thePlant = new Plant[]{
            //This is the Turnip.
            new Special_Plant("Turnip",5,6,2,9,"l",10,false, false, true),
            //This is Wheat.
            new Special_Plant("Wheat", 4, 4, 4, 20, "l", 15, false, false),
            //This is Thyme.
            new Plant("Thyme", 0, 7, 6,12,"g",20),
            //This is Potato.
            new Special_Plant("Potato", 4,8,3,23,"s",20,false,false,true),
            //This is Tomato.
            new Special_Plant("Tomato",0, 8,9,23,"s",10,false,false)
        };




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

    public Plant getCurrent_crop(){
        return this.current_crop;
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

    //I'm just keeping this around for testing, because when we implement the UI, it might not be necessary.
    public String getPlantDisplay(){
        return this.current_crop.getName().substring(0,2).toLowerCase();
        //Submits the views.Plant Name of the views.Plant occupying it.
    }

    public void plantCrop(int i){
        this.current_crop=this.thePlant[i];
    }

    public void removeCrop(int i){
        this.current_crop = null; //Without this, removing a plant can permanently ruin that tile’s plant object for future planting.
    }

    //Call this method after a day to check for the stage of the plant and how to update it.
    public void growPlant(){
        int value=current_crop.getCurrent_growth();

        if(current_crop instanceof Special_Plant){
            if(((Special_Plant) current_crop).IsDormant()){
                current_crop.setCurrent_growth(value+1);
            }

            else if(((Special_Plant) current_crop).IsEnergizing()){
                if(!this.isWatered()){
                    current_crop.setCurrent_growth(value+1);
                }
            }


        }

    }

    //Call this method after a day to Update the Fertilizers Fertility time.
    public void updateFertility(){
        int value=getFertileTime();
        if(current_crop instanceof Special_Plant){
            if(((Special_Plant) current_crop).IsEnergizing()){
                setFertileTime(value-2);
            }
        }
        else{setFertileTime(value-1);}
    }

    //Call this method to update the state of plants; please note, that special plants have their own special updateStage
    public void updateStage(){
        int value=current_crop.getCurrent_growth();
        String name=current_crop.getName();
        //Entire logic for finding out what state the plant is in, these states aren't those of special plants.
        switch (name){
            case "Thyme":
                if(value==12){current_crop.setState("Mature");}
                else{current_crop.setState("Seedling");}
            break;

            case "Turnip":
                if(value<=2){
                    current_crop.setState("Seedling");
                }
                else if(value<=7 && value>=4){
                    current_crop.setState("Low");
                }
                else if(value==8){
                    current_crop.setState("High");
                }

                else{
                    current_crop.setState("Special");
                }
            break;

            case "Wheat":
                if(value==0){
                    current_crop.setState("Seedling");
                }

                else if(value<=6){
                    current_crop.setState("Low");
                }

                else if(value==10||value==11||value==15||value==16){
                    current_crop.setState("High");
                }

                else{
                    current_crop.setState("Special");
                }
            break;

            case "Tomato":
                if(value==0){
                    current_crop.setState("Seedling");
                }

                else{current_crop.setState("Special");}
            break;

            case "Potato":
                if(value<=4){
                    current_crop.setState("Seedling");
                }

                else if(value%3==2 && value<=11){
                    current_crop.setState("Low");
                }

                else if(value%3==2){
                    current_crop.setState("High");
                }


        }

        //No matter what happens, if the crop is at max growth, it will be mature, so it either overrides or just has its own special check.
        if(value>=current_crop.getMaxGrowth()){
            current_crop.setState("Mature");
        }
    }







}
