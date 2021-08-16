package minesweeper;

import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String[] command;
        Minefield minefield = new Minefield(9, getMines());
        do {
            System.out.println(minefield);
            command = getCoordinates();
            int col = Integer.parseInt(command[0]) - 1;
            int row = Integer.parseInt(command[1]) - 1;
            switch (command[2]) {
                case "mine":
                    minefield.markCell(row, col);
                    break;
                case "free":
                    minefield.clearCell(row, col);
                    break;
                default:
                    break;
            }
        } while (!minefield.allMinesFound() && !minefield.isSteppedOnMine());
        System.out.println(minefield);
        if (minefield.allMinesFound()) {
            System.out.println("Congratulations! You found all mines!");
        }
        if (minefield.isSteppedOnMine()) {
            System.out.println("You stepped on a mine and failed!");
        }
    }

    /**
     * Asks the user for the number of mines to place on the minefield and validates it by the size of the field
     * @return number of mines
     */
    private static int getMines() {
        int numOfMines = 0;
        do {
            System.out.print("How many mines do you want on the field? > ");
            String mines = sc.nextLine();
            if (!mines.matches("\\b([1-9]|[1-7][0-9]|8[0-1])\\b")) {
                System.out.println("Invalid value. Please enter a number from 1 to 81.");
            } else {
                numOfMines = Integer.parseInt(mines);
            }
        } while (numOfMines == 0);

        return numOfMines;
    }

    private static String[] getCoordinates() {
        String[] params;
        do {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            params = sc.nextLine().split(" ");
            if (params.length != 3) {
                System.out.println("Invalid number of arguments!");
                continue;
            }
            if (!params[0].matches("\\b([1-9])\\b") && !params[1].matches("\\b([1-9])\\b")) {
                System.out.println("You need to enter two numbers between 1 and 9.");
                continue;
            }
            if (!params[2].equals("mine") && !params[2].equals("free")) {
                System.out.println("Invalid operation - you must choose either 'mine' or 'free'.");
                continue;
            }
            return params;
        } while (true);
    }

}