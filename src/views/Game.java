package views;

import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
    private static final int SIZE = 10;
    private static final int PLANT_COUNT = 5;
    private static final int FERTILIZER_COUNT = 3;
    private static final int SEASON_DAYS = 20;
    private static final int METEOR_DAY = 15;

    private Player player;
    private Field field;
    private Leaderboard highScores;

    private int currentDay;
    private boolean meteorTriggered;
    private boolean gameOver;
    private int excavationsToday;

    private String[] plantNames = new String[PLANT_COUNT];
    private int[] plantPrices = new int[PLANT_COUNT];

    private String[] fertilizerNames = new String[FERTILIZER_COUNT];
    private int[] fertilizerPrices = new int[FERTILIZER_COUNT];
    private int[] fertilizerDays = new int[FERTILIZER_COUNT];

    private String[][] baseSoil = new String[SIZE][SIZE];
    private boolean[][] meteorHit = new boolean[SIZE][SIZE];
    private boolean[][] permanentFertilizer = new boolean[SIZE][SIZE];

    public Game(Player player, Field field, Leaderboard highScores) throws Exception {
        this.player = player;
        this.field = field;
        this.highScores = highScores;
        this.currentDay = 1;
        this.meteorTriggered = false;
        this.gameOver = false;
        this.excavationsToday = 0;

        loadPlants();
        loadFertilizers();
        loadMap();
        initializeTiles();
    }

    private void initializeTiles() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                meteorHit[r][c] = false;
                permanentFertilizer[r][c] = false;

                Tile tile = field.getGameTile(r, c);
                tile.setSoilType(baseSoil[r][c]);
                tile.setUsable(true);
                tile.setWatered(false);
                tile.setFertilized(false);
                tile.setFertileTime(0);
            }
        }
    }

    private String[] cleanAndSplit(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("MCO1-main/Plants.json"));
        String line = reader.readLine();
        reader.close();

        line = line.replaceAll("[{}\"]", "").replace(",", ":");
        return line.split(":");
    }

    private void loadPlants() {
        plantNames[0] = "Turnip";
        plantPrices[0] = 10;

        plantNames[1] = "Wheat";
        plantPrices[1] = 15;

        plantNames[2] = "Thyme";
        plantPrices[2] = 25;

        plantNames[3] = "Potato";
        plantPrices[3] = 20;

        plantNames[4] = "Tomato";
        plantPrices[4] = 10;
    }

    private void loadFertilizers() {
        fertilizerNames[0] = "Quick views.Fertilizer";
        fertilizerPrices[0] = 100;
        fertilizerDays[0] = 2;

        fertilizerNames[1] = "Lasting views.Fertilizer";
        fertilizerPrices[1] = 150;
        fertilizerDays[1] = 3;

        fertilizerNames[2] = "Premium views.Fertilizer";
        fertilizerPrices[2] = 200;
        fertilizerDays[2] = 6;
    }

    private void loadMap() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("../Map.json"));
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

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasMeteorOccurred() {
        return meteorTriggered;
    }

    public void printHeader() {
        System.out.println("\n====================================");
        System.out.println("DAY " + currentDay + " / " + SEASON_DAYS);
        System.out.println("views.Player: " + player.getName());
        System.out.println("Savings: " + player.getMoney());
        System.out.println("Water: " + player.getWateringCan().getCurrentLevel() + "/" +
                player.getWateringCan().getMaxLevel());
        System.out.println("====================================");
    }

    public void showLegend() {
        System.out.println("\nLegend:");
        System.out.println("l = loam, s = sand, g = gravel");
        System.out.println("xx = crop initials");
        System.out.println("[ ] = watered");
        System.out.println("( ) = fertilized");
        System.out.println("* = meteor tile");
        System.out.println("! = harvestable");
    }

    public void showMenu() {
        System.out.println("\nActions:");
        System.out.println("1. views.Plant Seed");
        System.out.println("2. Remove or Harvest");
        System.out.println("3. Water");
        System.out.println("4. Refill Watering Can (100)");
        System.out.println("5. Apply views.Fertilizer");

        if (meteorTriggered) {
            System.out.println("6. Excavate Meteorite");
            System.out.println("7. Next Day");
        } else {
            System.out.println("6. Next Day");
        }
    }

    public void showPlantMenu() {
        System.out.println("\nChoose plant:");
        for (int i = 0; i < PLANT_COUNT; i++) {
            System.out.println((i + 1) + ". " + plantNames[i] + " - " + plantPrices[i]);
        }
    }

    public void showFertilizerMenu() {
        System.out.println("\nChoose fertilizer:");
        for (int i = 0; i < FERTILIZER_COUNT; i++) {
            System.out.println((i + 1) + ". " + fertilizerNames[i] + " - " + fertilizerPrices[i]
                    + " (" + fertilizerDays[i] + " days)");
        }
    }

    public void displayField() {
        System.out.print("    ");
        for (int c = 0; c < SIZE; c++) {
            System.out.printf("%-10d", c + 1);
        }
        System.out.println();

        for (int r = 0; r < SIZE; r++) {
            System.out.printf("%-4d", r + 1);
            for (int c = 0; c < SIZE; c++) {
                System.out.printf("%-10s", getTileDisplay(r, c));
            }
            System.out.println();
        }
    }

    private String getTileDisplay(int row, int col) {
        Tile tile = field.getGameTile(row, col);
        String symbol;

        if (meteorHit[row][col]) {
            symbol = "*";
        } else if (hasPlant(row, col)) {
            Plant crop = tile.getCurrent_crop();
            symbol = crop.getName().substring(0, 2).toLowerCase();

            refreshCropState(crop, tile);

            if (isHarvestable(crop)) {
                symbol += "!";
            }
        } else {
            symbol = baseSoil[row][col];
        }

        if (tile.isFertilized() || permanentFertilizer[row][col]) {
            symbol = "(" + symbol + ")";
        }

        if (tile.isWatered()) {
            symbol = "[" + symbol + "]";
        }

        return symbol;
    }

    private boolean validTile(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private boolean hasPlant(int row, int col) {
        Tile tile = field.getGameTile(row, col);
        return tile.getCurrent_crop() != null &&
                tile.getCurrent_crop().getName() != null &&
                !tile.getCurrent_crop().getName().equals("");
    }

    private void refreshCropState(Plant crop, Tile tile) {
        if (crop instanceof Special_Plant) {
            ((Special_Plant) crop).Special_UpdateStage();
        }
        tile.updateStage();
    }

    private boolean isHarvestable(Plant crop) {
        String state = crop.getState();
        return state.equals("Low") || state.equals("High") || state.equals("Mature");
    }

    public void plantSeed(int row, int col, int plantChoice) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }

        if (plantChoice < 0 || plantChoice >= PLANT_COUNT) {
            System.out.println("Invalid plant choice.");
            return;
        }

        Tile tile = field.getGameTile(row, col);

        if (meteorHit[row][col] || !tile.isUsable()) {
            System.out.println("Cannot plant on a meteor tile.");
            return;
        }

        if (hasPlant(row, col)) {
            System.out.println("There is already a plant there.");
            return;
        }

        if (!player.deductSavings(plantPrices[plantChoice])) {
            System.out.println("Not enough savings.");
            return;
        }

        tile.plantCrop(plantChoice);
        tile.getCurrent_crop().setCurrent_growth(0);
        tile.getCurrent_crop().setState("Seedling");
        tile.setWatered(false);

        System.out.println(plantNames[plantChoice] + " planted.");
    }

    public void removeOrHarvest(int row, int col) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }

        if (!hasPlant(row, col)) {
            System.out.println("No plant on that tile.");
            return;
        }

        Tile tile = field.getGameTile(row, col);
        Plant crop = tile.getCurrent_crop();

        refreshCropState(crop, tile);

        if (isHarvestable(crop)) {
            int earnings = field.sellCrop(row, col);
            player.addSavings(earnings);
            System.out.println("Harvested " + crop.getName() + " for " + earnings + ".");
        } else {
            System.out.println(crop.getName() + " removed.");
        }

        clearPlant(row, col);
    }

    public void water(int row, int col) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }

        if (!hasPlant(row, col)) {
            System.out.println("No plant on that tile.");
            return;
        }

        Tile tile = field.getGameTile(row, col);

        if (tile.isWatered()) {
            System.out.println("views.Tile already watered.");
            return;
        }

        if (!player.getWateringCan().water()) {
            System.out.println("Watering can is empty.");
            return;
        }

        tile.setWatered(true);
        System.out.println("views.Tile watered.");
    }

    public void refillWateringCan() {
        if (!player.deductSavings(100)) {
            System.out.println("Not enough savings.");
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

        if (fertilizerChoice < 0 || fertilizerChoice >= FERTILIZER_COUNT) {
            System.out.println("Invalid fertilizer choice.");
            return;
        }

        Tile tile = field.getGameTile(row, col);

        if (meteorHit[row][col]) {
            System.out.println("Cannot fertilize a meteor tile.");
            return;
        }

        if (tile.isFertilized() || permanentFertilizer[row][col]) {
            System.out.println("views.Tile already has fertilizer.");
            return;
        }

        if (!player.deductSavings(fertilizerPrices[fertilizerChoice])) {
            System.out.println("Not enough savings.");
            return;
        }

        tile.setFertilized(true);
        tile.setFertileTime(fertilizerDays[fertilizerChoice]);
        System.out.println("views.Fertilizer applied.");
    }

    public void excavate(int row, int col) {
        if (!validTile(row, col)) {
            System.out.println("Invalid tile.");
            return;
        }

        if (!meteorTriggered) {
            System.out.println("Meteor excavation is not available yet.");
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
            System.out.println("Not enough savings.");
            return;
        }

        Tile tile = field.getGameTile(row, col);
        meteorHit[row][col] = false;
        tile.setUsable(true);
        tile.setSoilType(baseSoil[row][col]);
        tile.setFertilized(true);
        tile.setFertileTime(999999);
        permanentFertilizer[row][col] = true;
        excavationsToday++;

        System.out.println("Meteor excavated.");
    }

    public void nextDay() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                processNextDay(r, c);
            }
        }

        player.addSavings(50);
        excavationsToday = 0;

        if (currentDay == METEOR_DAY && !meteorTriggered) {
            meteorEvent();
            meteorTriggered = true;
        }

        if (currentDay >= SEASON_DAYS) {
            System.out.println("\nPlanting season is over!");
            endGame();
            gameOver = true;
            return;
        }

        currentDay++;
    }

    private void processNextDay(int row, int col) {
        Tile tile = field.getGameTile(row, col);

        if (!hasPlant(row, col)) {
            tile.setWatered(false);
            return;
        }

        Plant crop = tile.getCurrent_crop();
        refreshCropState(crop, tile);

        int growthGain = computeGrowthGain(tile, crop);
        crop.setCurrent_growth(crop.getCurrent_growth() + growthGain);

        refreshCropState(crop, tile);
        updateFertilizerDuration(tile, crop, row, col);

        tile.setWatered(false);

        if (!permanentFertilizer[row][col] && tile.getFertileTime() <= 0) {
            tile.setFertilized(false);
            tile.setFertileTime(0);
        }
    }

    private int computeGrowthGain(Tile tile, Plant crop) {
        String state = crop.getState();
        boolean preferredSoil = tile.isSoilOptimal();
        boolean fertilized = tile.isFertilized() || hasPermanentFertilizer(tile);

        if (state.equals("Mature")) {
            return 0;
        }

        if (state.equals("Seedling")) {
            if (!tile.isWatered()) {
                return 0;
            }

            int gain = 1;
            if (preferredSoil) gain += 2;
            if (fertilized) gain += 2;
            return gain;
        }

        if (state.equals("Low") || state.equals("High")) {
            if (!tile.isWatered()) {
                return 0;
            }

            int gain = 1;
            if (preferredSoil) gain += 1;
            if (fertilized) gain += 1;
            return gain;
        }

        if (crop instanceof Special_Plant) {
            Special_Plant sp = (Special_Plant) crop;

            if (sp.IsDormant() != null && sp.IsDormant()) {
                return 1;
            }

            if (sp.IsEnergizing() != null && sp.IsEnergizing()) {
                if (tile.isWatered()) {
                    return 0;
                }
                return 1;
            }
        }

        return 0;
    }

    private void updateFertilizerDuration(Tile tile, Plant crop, int row, int col) {
        if (!tile.isFertilized() || permanentFertilizer[row][col]) {
            return;
        }

        int deduction = 1;

        if (crop instanceof Special_Plant) {
            Special_Plant sp = (Special_Plant) crop;
            if (sp.IsEnergizing() != null && sp.IsEnergizing()) {
                deduction = 2;
            }
        }

        tile.setFertileTime(tile.getFertileTime() - deduction);

    }

    private boolean hasPermanentFertilizer(Tile tile) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (field.getGameTile(r, c) == tile) {
                    return permanentFertilizer[r][c];
                }
            }
        }
        return false;
    }

    private void clearPlant(int row, int col) {
        Tile tile = field.getGameTile(row, col);
        if (hasPlant(row, col)) {
            tile.removeCrop(0);
        }
        tile.setWatered(false);
    }

    private void meteorEvent() {
        System.out.println("\nA meteor has struck the field!");

        int[][] affectedTiles = {
                {1,1},{1,4},{1,5},{1,8},
                {3,3},{3,4},{3,5},{3,6},
                {4,1},{4,3},{4,4},{4,5},{4,6},{4,8},
                {5,1},{5,3},{5,4},{5,5},{5,6},{5,8},
                {6,3},{6,4},{6,5},{6,6},
                {8,1},{8,4},{8,5},{8,8}
        };

        for (int i = 0; i < affectedTiles.length; i++) {
            int r = affectedTiles[i][0];
            int c = affectedTiles[i][1];
            Tile tile = field.getGameTile(r, c);

            if (hasPlant(r, c)) {
                refreshCropState(tile.getCurrent_crop(), tile);

                if (isHarvestable(tile.getCurrent_crop())) {
                    int earnings = field.sellCrop(r, c);
                    player.addSavings(earnings);
                }

                clearPlant(r, c);
            }

            meteorHit[r][c] = true;
            tile.setUsable(false);
            tile.setWatered(false);
            tile.setFertilized(false);
            tile.setFertileTime(0);
        }

        System.out.println("Meteor tiles are now blocked.");
    }

    public void endGame() {
        System.out.println("\nviews.Game ended.");
        System.out.println("Final savings: " + player.getMoney());
        highScores.register_score(player.getName(), player.getMoney());
        highScores.display_score();
    }
}
