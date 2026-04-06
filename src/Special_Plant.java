public class Special_Plant extends Plant{
    private Boolean Energizing;
    private Boolean Dormant;
    private Boolean Root_Crop;

    Special_Plant(String name, int price, int yield, int max_growth, String preferred_soil, int crop_price, int key, Boolean Energizing, Boolean Dormant){
        super(name, price, yield, max_growth, preferred_soil, crop_price, key);
        this.Energizing=Energizing;
        this.Dormant=Dormant;
    }

    Special_Plant(String name, int price, int yield, int max_growth, String preferred_soil, int crop_price, int key, Boolean Energizing, Boolean Dormant, Boolean Root_crop){
        this(name, price, yield, max_growth, preferred_soil, crop_price, key, Energizing, Dormant);
        this.Root_Crop = true;
    }


    public Boolean IsEnergizing(){
        return this.Energizing;
    }

    public Boolean IsDormant(){
        return this.Dormant;
    }

    public void setDormant(Boolean Dormant){
        this.Dormant=Dormant;
    }

    public void setEnergizing(Boolean Energizing){
        this.Energizing=Energizing;
    }

    public Boolean IsRoot_Crop(){
        return this.Root_Crop;
    }

    public void Special_UpdateStage(){
        int value;
        switch(getName()){
            case "Tomato":
                value=getCurrent_growth();
                if(value > 0 && value <= 12){
                    setDormant(true);
                    setEnergizing(false);
                }
                else if(value > 12 && value <= 22){
                    setEnergizing(true);
                    setDormant(false);
                }
                else{
                    setEnergizing(false);
                    setDormant(false);
                }
                break;
            case "Wheat":
                value=getCurrent_growth()-7;
                if(value >= 0 && value <= 2) {
                setEnergizing(true);
                }
                else if (value >= 5 && value <= 7){
                    setEnergizing(true);
                }
                else if (value >= 10 && value <= 12) {
                    setEnergizing(true);
                }
                else {
                    setEnergizing(false);
                }
                break;

            /*
            The ones below are the Root Crops by the way, I'm just making the split because I feel like I'm going to get this stuff mixed up, also might help for navigating.
             */

            case "Potato":
                value=getCurrent_growth()-5;
                if(value<0 || value%3==2){
                    setEnergizing(false);
                    setDormant(false);
                }

                else if(value%3==0){
                setEnergizing(true);
                setDormant(false);
                }

                else{
                    setEnergizing(false);
                    setDormant(true);
                }

                break;

            case "Turnip":
                if(getCurrent_growth()==3){
                    setDormant(true);
                }
                else{
                    setDormant(false);
                }
                break;

        }
    }




}
