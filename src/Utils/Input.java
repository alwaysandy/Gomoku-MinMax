package Utils;

import java.util.Scanner;

public class Input {
    /**
     * Displays options and reads user choice.
     * 
     * @param prompt The prompt to display to the user
     * @param options Array of options to display
     * @return The index of the chosen option (1-based)
     */
    public static int readChoice(String prompt, String[] options) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean validInput = false;
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print(prompt);
        while (!validInput) {
            try {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= options.length) {
                    validInput = true;
                } else {
                    System.out.print("Invalid choice. Choose an option: ");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.print("Please enter a number. Choose an option: ");
                scanner.nextLine();
            }
        }
        return choice;
    }

    /**
     * Reads text input from the user.
     * 
     * @param prompt The prompt to display to the user
     * @return The text entered by the user (non-empty)
     */
    public static String readText(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        String text = scanner.nextLine();
        while (text.isEmpty()) {
            System.out.print("Text cannot be empty. \nEnter your text: ");
            text = scanner.nextLine();
        }
        return text;
    }

    /**
     * Converts an array of characters to an array of strings.
     * 
     * @param chars Array of characters to convert
     * @return Array of strings, each containing a single character
     */
    public static String[] makeOptionsFromChars(char[] chars) {
        String[] options = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            options[i] = String.valueOf(chars[i]);
        }
        return options;
    }

    /**
     * Reads a row input (numerical) from the user.
     * 
     * @param size The maximum size of the board
     * @return The row index chosen by the user
     */
    public static int readRow(int size) {
        int value;
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter your row (0-" + (size - 1) + "): ");
                value = input.nextInt();
                return value;
            } catch (Exception e) {
                System.out.println("Please Enter a valid number.");
            }
        }
    }

    /**
     * Reads a column input (alphabetical) from the user.
     * 
     * @param size The maximum size of the board
     * @return The column index chosen by the user
     */
    public static int readCol(int size) {
        int value = -1;
        while(true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter column (a-" + (char)('a' + size) + "): ");
                String colChar = input.next().toLowerCase();
                value = colChar.charAt(0) - 'a';
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
