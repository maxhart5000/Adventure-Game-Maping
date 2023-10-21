package dev.hartcode;

import dev.hartcode.game.AdventureGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the AdventureGame class.
        AdventureGame adventureGame = new AdventureGame();

        // Start the game at the specified location ("Road").
        adventureGame.play("Road");

        // Create a Scanner object for user input.
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read user input, trim leading/trailing whitespace, and convert to uppercase.
            String direction = scanner.nextLine().trim().toUpperCase().substring(0, 1);

            // Check if the user wants to quit the game.
            if (direction.equals("Q")) {
                System.out.println("The game is shutting down...");
                break; // Exit the game loop.
            } else {
                // Move the player in the specified direction.
                adventureGame.move(direction);
            }
        }

        // Close the Scanner and exit the program.
        scanner.close();
    }
}
