public class Game {
    private Player player;
    private Field field;
    private int currentDay;
    private Leaderboard highScores;

    public Game(Player player, Field field, Leaderboard highScores) {
        this.player = player;
        this.field = field;
        this.highScores = highScores;
        this.currentDay = 1;
    }

    public void start() {
        System.out.println("Game started");
        field.setAllTiles();
    }

    public void showMenu() {
        System.out.println("1. Plant Seed");
        System.out.println("2. Water");
        System.out.println("3. Apply Fertilizer");
        System.out.println("4. Remove or Harvest");
        System.out.println("5. Excavate");
        System.out.println("6. Next Day");
        System.out.println("7. End Game");
    }

    public void plantSeed(int row, int column) {
        System.out.println("Plant seed at: " + row + ", " + column);
    }

    public void water(int row, int column) {
        if (field.WaterTile(row, column)) {
            System.out.println("Tile watered.");
        } else {
            System.out.println("Cannot water tile.");
        }
    }

    public void applyFertilizer(int row, int column) {
        if (field.FertilizeTile(row, column)) {
            System.out.println("Tile fertilized.");
        } else {
            System.out.println("Cannot fertilize tile.");
        }
    }

    public void removeOrHarvest(int row, int column) {
        System.out.println("Remove or harvest at: " + row + ", " + column);
    }

    public void excavate(int row, int column) {
        System.out.println("Excavate tile at: " + row + ", " + column);
    }

    public void nextDay() {
        currentDay++;
        System.out.println("Day " + currentDay);
    }

    public void endGame() {
        System.out.println("Game ended.");
    }
}
