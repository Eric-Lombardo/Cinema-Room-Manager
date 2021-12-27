package cinema;
import java.util.*;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int columns = scanner.nextInt();

        // Create default 2D seating grid
        char[][] seatData = new char[rows][columns];
        for (char[] row : seatData) {
            Arrays.fill(row, 'S');
        }

        int purchasedTickets = 0;
        int totalTickets = rows * columns;
        int currentIncome = 0;
        int totalIncome = getTotalPossibleIncome(rows, columns);
        int choice = displayOptions(scanner);

        while (choice != 0) {
            switch (choice) {
                case 1:
                    displayCinema(seatData);
                    choice = displayOptions(scanner);
                    break;
                case 2:
                    currentIncome += buyTicket(rows, columns, scanner, seatData);
                    purchasedTickets++;
                    choice = displayOptions(scanner);
                    break;
                case 3:
                    displayStats(purchasedTickets, totalTickets, currentIncome, totalIncome);
                    choice = displayOptions(scanner);
                    break;
            }
        }
    }

    private static void displayStats(int purchasedTickets, int totalTickets, int currentIncome, int totalIncome) {
        System.out.println("\nNumber of purchased tickets: " + purchasedTickets);
        double percentage = (double) purchasedTickets / totalTickets * 100;
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    private static int getTotalPossibleIncome(int rows, int columns) {
        if (rows * columns <= 60) {
            return rows * columns * 10;
        } else {
            int firstHalf = rows / 2;
            int frontTotal = firstHalf * columns * 10;
            int backTotal = (rows - firstHalf) * columns * 8;
            return frontTotal + backTotal;
        }
    }

    private static void displayCinema(char[][] data) {
        System.out.println("Cinema:");

        StringBuilder displayColumns = new StringBuilder(" ");
        for (int i = 0; i < data[0].length; i++) {
            displayColumns.append(" ").append(i + 1);
        }
        System.out.println(displayColumns);

        for (int i = 0; i < data.length; i++) {
            StringBuilder displayRows = new StringBuilder(Integer.toString(i + 1));
            for (int j = 0; j < data[i].length; j++) {
                displayRows.append(" ").append(data[i][j]);
            }
            System.out.println(displayRows);
        }
    }

    private static int displayOptions(Scanner scanner) {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scanner.nextInt();
    }

    private static int buyTicket(int rows, int columns, Scanner scanner, char[][] data) {
        System.out.println("Enter a row number:");
        int buyRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int buyColumn = scanner.nextInt();

        boolean isValidPurchase = false;
        while (!isValidPurchase) {
            if (buyRow > data.length || buyRow < 1 || buyColumn > data[0].length || buyColumn < 1) {
                System.out.println("Wrong input!");
                System.out.println("Enter a row number:");
                buyRow = scanner.nextInt();
                System.out.println("Enter a seat number in that row:");
                buyColumn = scanner.nextInt();
            } else if (data[buyRow - 1][buyColumn - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
                System.out.println("Enter a row number:");
                buyRow = scanner.nextInt();
                System.out.println("Enter a seat number in that row:");
                buyColumn = scanner.nextInt();
            } else {
                isValidPurchase = true;
            }
        }

        updateCinema(data, new int[] {buyRow, buyColumn});

        if (rows * columns <= 60) {
            System.out.println("Ticket price: $10");
            return 10;
        } else {
            int result = buyRow <= rows / 2 ? 10 : 8;
            System.out.println("Ticket price: $" + result);
            return result;
        }
    }

    private static void updateCinema(char[][] data, int[] buyTicket) {
        data[buyTicket[0] - 1][buyTicket[1] - 1] = 'B';
    }
}