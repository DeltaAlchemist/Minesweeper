package minesweeper;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("How many mines do you want on the field?");
        int mines = sc.nextInt();

        char[][] mineField = new char[9][9];

        for (char[] chars : mineField) {
            Arrays.fill(chars, '.');
        }

        placeMines(mineField, mines);
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

        for (char[] chars : field) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }
}
