import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
    private static final int SIZE = 10;
    private static final int PLANT_COUNT = 5;
    private static final int FERTILIZER_COUNT = 3;

    private Player player;
    private Field field;
    private int currentDay;
    private Leaderboard highScores;

    private boolean meteorTriggered;
    private int excavationsToday;

    private String[] plantNames = new String[PLANT_COUNT];
    private int[] plantPrices = new int[PLANT_COUNT];
    private int[] plantYield = new int[PLANT_COUNT];
    private int[] plantMaxGrowth = new int[PLANT_COUNT];
    private String[] plantPreferredSoil = new String[PLANT_COUNT];
    private int[] cropPrice = new int[PLANT_COUNT];

    private String[] fertilizerNames = new String[FERTILIZER_COUNT];
    private int[] fertilizerPrices = new int[FERTILIZER_COUNT];
    private int[] fertilizerDays = new int[FERTILIZER_COUNT];

    private String[][] baseSoil = new String[SIZE][SIZE];
    private int[][] plantedType = new int[SIZE][SIZE];
    private int[][] growthStage = new int[SIZE][SIZE];
    private boolean[][] watered = new boolean[SIZE][SIZE];
    private int[][] fertilizerType = new int[SIZE][SIZE];
    private int[][] fertilizerRemaining = new int[SIZE][SIZE];
    private boolean[][] meteorHit = new boolean[SIZE][SIZE];
    private boolean[][] permanentFertilizer = new boolean[SIZE][SIZE];

    public Game(Player player, Field field, Leaderboard highScores) throws Exception {
        this.player = player;
        this.field = field;
        this.highScores = highScores;
        currentDay = 1;
        meteorTriggered = false;
        excavationsToday = 0;

        loadPlants();
        loadFertilizers();
        loadMap();
        initializeTiles();
    }

    private void initializeTiles() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                plantedType[r][c] = -1;
                fertilizerType[r][c] = -1;
            }
        }
    }

    private String[] cleanAndSplit(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        reader.close();

        line = line.replaceAll("[{}\"]", "").replace(",", ":");
        return line.split(":");
    }

    private void loadPlants() throws Exception {
        String[] h = cleanAndSplit("Plants.json");
        for (int i = 0; i < PLANT_COUNT; i++) {
            int x = 13 * i;
            plantNames[i] = h[2 + x];
            plantPrices[i] = Integer.parseInt(h[4 + x]);
            plantYield[i] = Integer.parseInt(h[6 + x]);
            plantMaxGrowth[i] = Integer.parseInt(h[8 + x]);
            plantPreferredSoil[i] = h[10 + x];
            cropPrice[i] = Integer.parseInt(h[12 + x]);
        }
    }

    private void loadFertilizers() throws Exception {
        String[] h = cleanAndSplit("Fertilizers.json");
        for (int i = 0; i < FERTILIZER_COUNT; i++) {
            int x = 7 * i;
            fertilizerNames[i] = h[2 + x];
            fertilizerPrices[i] = Integer.parseInt(h[4 + x]);
            fertilizerDays[i] = Integer.parseInt(h[6 + x]);
        }
    }

    private void loadMap() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("Map.json"));
        String line = reader.readLine();
        reader.close();

        line = line.replaceAll("[{}\"]", "");
        line = line.replaceAll(" ", "_");
        line = line.replaceAll(",", ":");
        line = line.replaceAll("[\\[\\]]", ":");
        line = line.replaceAll(":::", ":").replaceAll("::", ":");
        line = line.substring(34);

        String[] h = line.split(":");
        int index = 0;

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                baseSoil[r][c] = h[index++];
            }
        }
    }

    public int getDay() {
        return currentDay;
    }

    public void showMenu() {
        System.out.println("1. Plant Seed");
        System.out.println("2. Remove or Harvest");
        System.out.println("3. Water");
        System.out.println("4. Refill Watering Can (100)");
        System.out.println("5. Apply Fertilizer");
        System.out.println("6. Next Day");
    }

    public void showPlantMenu() {
        System.out.println("Choose plant:");
        for (int i = 0; i < plantNames.length; i++) {
            System.out.println((i + 1) + ". " + plantNames[i] + " - " + plantPrices[i]);
        }
    }

    public void showFertilizerMenu() {
        System.out.println("Choose fertilizer:");
        for (int i = 0; i < fertilizerNames.length; i++) {
            System.out.println((i + 1) + ". " + fertilizerNames[i] + " - " + fertilizerPrices[i]);
        }
    }

    public void displayField() {
        System.out.print("    ");
        for (int c = 0; c < SIZE; c++) {
            System.out.printf("%-6d", c + 1);
        }
        System.out.println();

        for (int r = 0; r < SIZE; r++) {
            System.out.printf("%-4d", r + 1);
            for (int c = 0; c < SIZE; c++) {
                System.out.printf("%-6s", getTileDisplay(r, c));
            }
            System.out.println();
        }
    }

    private String getTileDisplay(int row, int col) {
        String symbol;

        if (meteorHit[row][col]) {
            symbol = "*";
        } else if (plantedType[row][col] != -1) {
            symbol = plantNames[plantedType[row][col]].substring(0, 2).toLowerCase();
            if (growthStage[row][col] >= plantMaxGrowth[plantedType[row][col]]) {
                symbol += "!";
            }
        } else {
            symbol = baseSoil[row][col];
        }

        if (fertilizerType[row][col] != -1 || permanentFertilizer[row][col]) {
            symbol = "(" + symbol + ")";
        }
        if (watered[row][col]) {
            symbol = "[" + symbol + "]";
        }

        return symbol;
    }

    private boolean validTile(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private String soilName(int row, int col) {
        if (baseSoil[row][col].equals("l")) return "loam";
        if (baseSoil[row][col].equals("s")) return "sand";
        return "gravel";
    }

    private void clearPlant(int row, int col) {
        plantedType[row][col] = -1;
        growthStage[row][col] = 0;
        watered[row][col] = false;
        field.getGameTile(row, col).setWatered(false);
        field.getGameTile(row, col).removeCrop(0);
    }

    public void plantSeed(int row, int col, int plantChoice) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }
        if (plantChoice < 0 || plantChoice >= plantNames.length) {
            System.out.println("Invalid plant choice.");
            return;
        }
        if (meteorHit[row][col]) {
            System.out.println("Cannot plant on a meteor tile.");
            return;
        }
        if (plantedType[row][col] != -1) {
            System.out.println("There is already a plant there.");
            return;
        }
        if (!player.deductSavings(plantPrices[plantChoice])) {
            System.out.println("Not enough savings.");
            return;
        }

        plantedType[row][col] = plantChoice;
        growthStage[row][col] = 0;
        watered[row][col] = false;
        field.getGameTile(row, col).plantCrop(plantChoice);
        field.getGameTile(row, col).setWatered(false);

        System.out.println(plantNames[plantChoice] + " planted.");
    }

    public void removeOrHarvest(int row, int col) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }
        if (plantedType[row][col] == -1) {
            System.out.println("No plant on that tile.");
            return;
        }

        int type = plantedType[row][col];
        if (growthStage[row][col] >= plantMaxGrowth[type]) {
            int earnings = plantYield[type] * cropPrice[type];
            player.addSavings(earnings);
            System.out.println("Harvested " + plantNames[type] + " for " + earnings + ".");
        } else {
            System.out.println(plantNames[type] + " removed.");
        }

        clearPlant(row, col);
    }

    public void water(int row, int col) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }
        if (plantedType[row][col] == -1) {
            System.out.println("No plant on that tile.");
            return;
        }
        if (watered[row][col]) {
            System.out.println("Plant already watered.");
            return;
        }
        if (!player.getWateringCan().water()) {
            System.out.println("Watering can is empty.");
            return;
        }

        watered[row][col] = true;
        field.getGameTile(row, col).setWatered(true);
        System.out.println("Tile watered.");
    }

    public void refillWateringCan() {
        if (!player.deductSavings(100)) {
            System.out.println("Not enough savings to refill watering can.");
            return;
        }
        player.getWateringCan().refill();
        System.out.println("Watering can refilled.");
    }

    public void applyFertilizer(int row, int col, int fertilizerChoice) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }
        if (fertilizerChoice < 0 || fertilizerChoice >= fertilizerNames.length) {
            System.out.println("Invalid fertilizer choice.");
            return;
        }
        if (meteorHit[row][col]) {
            System.out.println("Cannot fertilize a meteor tile.");
            return;
        }
        if (fertilizerType[row][col] != -1 || permanentFertilizer[row][col]) {
            System.out.println("Tile already has fertilizer.");
            return;
        }
        if (!player.deductSavings(fertilizerPrices[fertilizerChoice])) {
            System.out.println("Not enough savings.");
            return;
        }

        fertilizerType[row][col] = fertilizerChoice;
        fertilizerRemaining[row][col] = fertilizerDays[fertilizerChoice];
        System.out.println("Fertilizer applied.");
    }

    public void excavate(int row, int col) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }
        if (!meteorHit[row][col]) {
            System.out.println("No meteor on this tile.");
            return;
        }
        if (excavationsToday >= 5) {
            System.out.println("Only 5 excavations are allowed per day.");
            return;
        }
        if (!player.deductSavings(500)) {
            System.out.println("Not enough savings to excavate.");
            return;
        }

        meteorHit[row][col] = false;
        permanentFertilizer[row][col] = true;
        excavationsToday++;
        System.out.println("Meteor excavated.");
    }

    public void nextDay() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (plantedType[r][c] != -1 && watered[r][c]) {
                    int add = 1;

                    if (plantPreferredSoil[plantedType[r][c]].equals(soilName(r, c))) {
                        add++;
                    }
                    if (fertilizerType[r][c] != -1 || permanentFertilizer[r][c]) {
                        add++;
                    }

                    growthStage[r][c] += add;
                    if (growthStage[r][c] > plantMaxGrowth[plantedType[r][c]]) {
                        growthStage[r][c] = plantMaxGrowth[plantedType[r][c]];
                    }

                    watered[r][c] = false;
                    field.getGameTile(r, c).setWatered(false);

                    if (fertilizerType[r][c] != -1 && --fertilizerRemaining[r][c] <= 0) {
                        fertilizerType[r][c] = -1;
                        fertilizerRemaining[r][c] = 0;
                    }
                }
            }
        }

        player.addSavings(50);
        excavationsToday = 0;

        if (currentDay == 7 && !meteorTriggered) {
            meteorEvent();
            meteorTriggered = true;
        }

        if (currentDay == 15) {
            System.out.println("\nPlanting season is over!");
            endGame();
            System.exit(0);
        }

        currentDay++;
    }

    private void meteorEvent() {
        System.out.println("\nA meteor has struck the field!");

        int[][] meteorTiles = {
                {1,1},{1,4},{1,5},{1,8},
                {3,3},{3,4},{3,5},{3,6},
                {4,1},{4,3},{4,4},{4,5},{4,6},{4,8},
                {5,1},{5,3},{5,4},{5,5},{5,6},{5,8},
                {6,3},{6,4},{6,5},{6,6},
                {8,1},{8,4},{8,5},{8,8}
        };

        for (int i = 0; i < meteorTiles.length; i++) {
            int r = meteorTiles[i][0];
            int c = meteorTiles[i][1];

            if (plantedType[r][c] != -1) {
                int type = plantedType[r][c];
                if (growthStage[r][c] >= plantMaxGrowth[type]) {
                    player.addSavings(plantYield[type] * cropPrice[type]);
                }
                clearPlant(r, c);
            }

            meteorHit[r][c] = true;
            fertilizerType[r][c] = -1;
            fertilizerRemaining[r][c] = 0;
        }

        System.out.println("Meteor tiles are marked with *");
    }

    public void endGame() {
        System.out.println("\nGame ended.");
        System.out.println("Final savings: " + player.getMoney());
        highScores.register_score(player.getName(), player.getMoney());
        highScores.display_score();
    }
}
