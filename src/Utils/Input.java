package Utils;

import java.util.Scanner;

public class Input {
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

    public static String[] makeOptionsFromChars(char[] chars) {
        String[] options = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            options[i] = String.valueOf(chars[i]);
        }
        return options;
    }
}
