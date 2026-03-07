public class Plant {

    private String name;
    private int price;
    private int yield;
    private int max_growth;
    private String preferred_soil;
    private int crop_price;

    public Plant(String name, int price, int yield, int max_growth, String preferred_soil, int crop_price) {
        this.name = name;
        this.price = price;
        this.yield = yield;
        this.max_growth = max_growth;
        this.preferred_soil = preferred_soil;
        this.crop_price = crop_price;
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

    public String getPreferredSoil() {
        return preferred_soil;
    }

    public int getCropPrice() {
        return crop_price;
    }
}
