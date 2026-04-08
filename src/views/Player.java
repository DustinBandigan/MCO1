package views;

import java.util.Date;

public class Player {

    private String name;
    private int money;
    private WateringCan wateringCan;
    
    public Player(String name, int money, WateringCan wateringCan) {
        this.name = name;
        this.money = money;
        this.wateringCan = wateringCan;
    }
    
    public boolean canAfford(int cost) {
        return money >= cost;
    }
    
    public void addSavings(int amount) {
        money += amount;
    }
    
    public boolean deductSavings(int cost) {
        if (money >= cost) {
            money -= cost;
            return true;
        }
        return false;
    }
    
    public void nextDay(Date today) {
        System.out.println("Day advanced: " + today);
    }
    
    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public WateringCan getWateringCan() {
        return wateringCan;
    }
}
