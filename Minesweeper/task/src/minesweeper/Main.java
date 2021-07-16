package minesweeper;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many mines do you want on the field?\n> ");
        int mines = sc.nextInt();

        char[][] field = new char[9][9];

        for (char[] chars : field) {
            Arrays.fill(chars, '.');
        }

        printMineField(field, mines);
    }

    public static void placeMines(char[][] field, int mines) {
        Random random = new Random();

        int counter = 0;

        while (counter < mines) {
            int row = random.nextInt(9);
            int column = random.nextInt(9);

            if (field[row][column] == '.') {
                field[row][column] = 'X';
                counter++;
            }
        }
    }

    public static String[][] findMines(char[][] field, int mines) {

        placeMines(field, mines);

        char[][] helpGrid = new char[11][11];
        for (int i = 1; i < 10; i++) {
            System.arraycopy(field[i - 1], 0, helpGrid[i], 1, 9);
        }

        int[][] countBombs = new int[11][11];
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                // if cells doesn't contain a bomb, check all nearby cells
                if (helpGrid[i][j] == '.') {
                    if (helpGrid[i - 1][j - 1] == 'X') countBombs[i][j]++;
                    if (helpGrid[i - 1][j] == 'X') countBombs[i][j]++;
                    if (helpGrid[i - 1][j + 1] == 'X') countBombs[i][j]++;
                    if (helpGrid[i][j + 1] == 'X') countBombs[i][j]++;
                    if (helpGrid[i + 1][j + 1] == 'X') countBombs[i][j]++;
                    if (helpGrid[i + 1][j] == 'X') countBombs[i][j]++;
                    if (helpGrid[i + 1][j - 1] == 'X') countBombs[i][j]++;
                    if (helpGrid[i][j - 1] == 'X') countBombs[i][j]++;
                }
            }
        }
        String[][] finalGrid = new String[9][9];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (countBombs[i + 1][j + 1] == 0 && helpGrid[i + 1][j + 1] == 'X') {
                    finalGrid[i][j] = "X";
                } else if (countBombs[i + 1][j + 1] == 0 && helpGrid[i + 1][j + 1] == '.') {
                    finalGrid[i][j] = ".";
                } else {
                    finalGrid[i][j] = Integer.toString(countBombs[i + 1][j + 1]);
                }
            }
        }
        return finalGrid;
    }

    public static void printMineField(char[][] field, int mines) {
        String[][] mineField = findMines(field, mines);

        for (String[] mm : mineField) {
            for (String m : mm) {
                System.out.print(m);
            }
            System.out.println();
        }
    }
}
