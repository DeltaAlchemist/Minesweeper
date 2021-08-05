package minesweeper;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int fieldColumns = 9;
        int fieldRows = 9;
        final int maxNumberMines = fieldColumns * fieldRows;
        int numberOfMines;

        do {
            System.out.print("How many mines do you want on the field?\n> ");
            numberOfMines = sc.nextInt();
            if (validNumberOfMines(numberOfMines, 0, maxNumberMines)) {
                System.out.printf("Error: There can be a minimum of 1 and a maximum of %d mines on the field.%n", maxNumberMines);
            }
        } while (validNumberOfMines(numberOfMines, 0, maxNumberMines));
        Minefield field = new Minefield(fieldColumns, fieldRows, numberOfMines);
        field.print();

        // Game Loop
        int columnNumber, rowNumber;
        boolean canBeFlagged;
        boolean coordinateInField;

        do {
            do {
                System.out.println("Set/delete mines marks (x and y coordinates): ");
                columnNumber = sc.nextInt();
                rowNumber = sc.nextInt();
                coordinateInField = field.coordinateInField(rowNumber - 1, columnNumber - 1);
                canBeFlagged = field.coordinateCanBeFlagged(rowNumber - 1, columnNumber - 1);
                if (!coordinateInField) {
                    System.out.println("This coordinate doesn't exist on the minefield.");
                } else {
                    if (!canBeFlagged) System.out.println("There is a number here!");
                }
            } while (!canBeFlagged || !coordinateInField);
            field.flagCoordinate(rowNumber - 1, columnNumber - 1);
            field.print();
        } while (!field.gameOver());
        System.out.println("Congratulations! You found all the mines!");

    }

    private static boolean validNumberOfMines(int number, int min, int max) {
        return number < min && number > max;
    }
}