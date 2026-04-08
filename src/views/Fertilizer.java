package views;

public class Fertilizer {
    private String name;
    private int price;
    private int day_length;

    public Fertilizer(String name, int price, int day_length){
        this.name=name;
        this.price=price;
        this.day_length=day_length;
    }

    public String getName(){return this.name;}

    public int getPrice(){return this.price;}

    public int getDay_length(){return this.day_length;}

}
