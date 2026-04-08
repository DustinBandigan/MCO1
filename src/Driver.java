import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== VERDANT SUN FARMING SIMULATOR ===");
        System.out.print("Enter player name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "Player";
        }

        Field field = new Field();
        Leaderboard leaderboard = new Leaderboard();
        WateringCan can = new WateringCan(10);
        Player player = new Player(name, 1000, can);

        Game game = new Game(player, field, leaderboard);

        while (!game.isGameOver()) {
            game.printHeader();
            game.displayField();
            game.showLegend();
            game.showMenu();

            System.out.print("\nEnter choice: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    game.showPlantMenu();
                    int plantChoice = askChoice(scanner, "Plant choice: ") - 1;

                    int row = askRow(scanner);
                    int col = askCol(scanner);

                    game.plantSeed(row, col, plantChoice);
                    break;

                case 2:
                    game.removeOrHarvest(askRow(scanner), askCol(scanner));
                    break;

                case 3:
                    game.water(askRow(scanner), askCol(scanner));
                    break;

                case 4:
                    game.refillWateringCan();
                    break;

                case 5:
                    game.showFertilizerMenu();
                    int fertilizerChoice = askChoice(scanner, "Fertilizer choice: ") - 1;

                    row = askRow(scanner);
                    col = askCol(scanner);

                    game.applyFertilizer(row, col, fertilizerChoice);
                    break;

                case 6:
                    if (game.hasMeteorOccurred()) {
                        game.excavate(askRow(scanner), askCol(scanner));
                    } else {
                        game.nextDay();
                    }
                    break;

                case 7:
                    if (game.hasMeteorOccurred()) {
                        game.nextDay();
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    private static int askRow(Scanner scanner) {
        System.out.print("Row (1-10): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid row.");
            scanner.nextLine();
            System.out.print("Row (1-10): ");
        }
        return scanner.nextInt() - 1;
    }

    private static int askCol(Scanner scanner) {
        System.out.print("Column (1-10): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid column.");
            scanner.nextLine();
            System.out.print("Column (1-10): ");
        }
        return scanner.nextInt() - 1;
    }

    private static int askChoice(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid choice.");
            scanner.nextLine();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
}
