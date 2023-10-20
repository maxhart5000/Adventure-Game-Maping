package dev.hartcode;

import dev.hartcode.game.AdventureGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdventureGame adventureGame = new AdventureGame();
        adventureGame.play("Road");

        Scanner scanner = new Scanner(System.in);

        while(true){
            String direction = scanner.nextLine().trim().toUpperCase().substring(0,1);
            if(direction.equals("Q")){
                System.out.println("The game is shutting down...");
                break;
            }else{
                adventureGame.move(direction);
            }
        }

    }
}
