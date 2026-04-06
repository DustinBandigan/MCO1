public class Plant {

    private String name;
    private int price;
    private int yield;
    private int current_growth;
    private int max_growth;
    private String State;
    private String preferred_soil;
    private int crop_price;
    private int key;

    public Plant(String name, int price, int yield, int max_growth, String preferred_soil, int crop_price, int key) {
        this.name = name;
        this.price = price;
        this.yield = yield;
        this.max_growth = max_growth;
        this.preferred_soil = preferred_soil;
        this.crop_price = crop_price;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

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

    public int getKey(){
        return this.key;
    }

    public void setCurrent_growth(int current_growth){
        this.current_growth=current_growth;
    }

    public void setState(String State){
        this.State=State;
    }

    public void resetCrop(){
        this.name = "";
        this.price = 0;
        this.yield = 0;
        this.max_growth = 0;
        this.preferred_soil = "";
        this.crop_price =0;
        this.current_growth=0;
    }

}
