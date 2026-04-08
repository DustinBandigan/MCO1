package views;

public class Plant {

    private String name;
    private int Low_price;
    private int High_price;
    private int yield;
    private int current_growth;
    private int max_growth;
    private String State;
    private String preferred_soil;
    private int crop_price;

    public Plant(String name, int price1, int price2, int yield, int max_growth, String preferred_soil, int crop_price) {
        this.name = name;
        this.Low_price = price1;
        this.High_price = price2;
        this.yield = yield;
        this.current_growth=0;
        this.max_growth = max_growth;
        this.State="Seedling";
        this.preferred_soil = preferred_soil;
        this.crop_price = crop_price;
    }

    public String getName() {
        return name;
    }

    public int getLow_price() {
        return Low_price;
    }

    public int getHigh_price(){return High_price;}

    public int getYield() {
        return yield;
    }

    public int getMaxGrowth() {
        return max_growth;
    }

    public String getState(){
        return State;
    }

    public String getPreferredSoil() {
        return preferred_soil;
    }

    public int getCropPrice() {
        return crop_price;
    }

    public int getCurrent_growth(){return current_growth;}

    public void setName(String name) {
        this.name = name;
    }

    public void setLow_price(int low_price) {
        Low_price = low_price;
    }

    public void setHigh_price(int high_price) {
        High_price = high_price;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public void setCurrent_growth(int current_growth){
        this.current_growth=current_growth;
    }

    public void setMax_growth(int max_growth) {
        this.max_growth = max_growth;
    }

    public void setState(String State){
        this.State=State;
    }

    public void setPreferred_soil(String preferred_soil) {
        this.preferred_soil = preferred_soil;
    }

    public void setCrop_price(int crop_price) {
        this.crop_price = crop_price;
    }

    public void resetCrop(){
        this.name = "";
        this.Low_price = 0;
        this.High_price = 0;
        this.yield = 0;
        this.max_growth = 0;
        this.preferred_soil = "";
        this.crop_price =0;
        this.State= "Seedling";
        this.current_growth=0;
    }

}
