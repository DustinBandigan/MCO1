import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter player name: ");
        String name = scanner.nextLine();

        Field field = new Field();
        Leaderboard leaderboard = new Leaderboard();
        WateringCan can = new WateringCan(10);
        Player player = new Player(name, 1000, can);

        Game game = new Game(player, field, leaderboard);

        while (true) {
            System.out.println("\n=== DAY " + game.getDay() + " ===");
            System.out.println("Player: " + player.getName());
            System.out.println("Savings: " + player.getMoney());
            System.out.println("Water: " + player.getWateringCan().getCurrentLevel() + "/" + player.getWateringCan().getMaxLevel());
            System.out.println();

            game.displayField();

            System.out.println("\nLegend: l=loam s=sand g=gravel *=meteor [ ]=watered ( )=fertilized !=mature");
            game.showMenu();
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            int row;
            int col;

            switch (choice) {
                case 1:
                    System.out.print("Row: ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Column: ");
                    col = scanner.nextInt() - 1;
                    game.showPlantMenu();
                    System.out.print("Plant choice: ");
                    int plantChoice = scanner.nextInt();
                    game.plantSeed(row, col, plantChoice - 1);
                    break;

                case 2:
                    System.out.print("Row: ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Column: ");
                    col = scanner.nextInt() - 1;
                    game.removeOrHarvest(row, col);
                    break;

                case 3:
                    System.out.print("Row: ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Column: ");
                    col = scanner.nextInt() - 1;
                    game.water(row, col);
                    break;

                case 4:
                    game.refillWateringCan();
                    break;

                case 5:
                    System.out.print("Row: ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Column: ");
                    col = scanner.nextInt() - 1;
                    game.showFertilizerMenu();
                    System.out.print("Fertilizer choice: ");
                    int fertilizerChoice = scanner.nextInt();
                    game.applyFertilizer(row, col, fertilizerChoice - 1);
                    break;

                case 6:
                    game.nextDay();
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
