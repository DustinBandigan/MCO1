package views;

public class WateringCan {

    private int maxLevel;
    private int currentLevel;

    public WateringCan(int maxLevel) {
        this.maxLevel = maxLevel;
        this.currentLevel = maxLevel;
    }

    public boolean water() {
        if (currentLevel > 0) {
            currentLevel--;
            return true;
        }
        return false;
    }

    public void refill() {
        currentLevel = maxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
