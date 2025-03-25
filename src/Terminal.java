import java.util.Scanner;

public class Terminal {
    public static void clearScreen() {
        String os = System.getProperty("os.name");

        if (os.contains("Windows"))
        {
            for (int i = 0; i < 100; i++) {
                // Clear Windows terminal via many println calls since escape codes don't work
                System.out.println();
            }
        }
        else
        {
            // Assume clear terminal escape codes work on non-windows terminals
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static int getInt() {
        Scanner scanner = new Scanner(System.in);
        int userInput = -1;
        do {
            String input = scanner.nextLine();
            if (input.length() == 1) {
                if (Character.isDigit(input.charAt(0))) {
                    userInput = Integer.parseInt(input);
                } else {
                    System.out.println("Error: Must input an integer between 0 and 10");
                }
            } else {
                System.out.println("Error: Must input an integer between 0 and 10");
            }
        } while (userInput == -1);

        return userInput;
    }

    public static char getColour() {
        Scanner scanner = new Scanner(System.in);
        char userInput = '0';
        do {
            String input = scanner.nextLine();
            if (input.length() == 1) {
                if (input.equals("W") || input.equals("B")) {
                    userInput = input.charAt(0);
                } else {
                    System.out.println("Error: Must input either W or B");
                }
            } else {
                System.out.println("Error: Must input either W or B");
            }
        } while (userInput == '0');

        return userInput;
    }
}