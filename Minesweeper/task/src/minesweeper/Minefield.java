package minesweeper;

import java.util.*;

public class Minefield {
    private final char[][] field;
    private final boolean[][] mines;
    private final int size;
    private int minesMarked, minesCorrect, minesPlaced, cellsToClear;
    private boolean steppedOnMine, firstMove;
    private Random rand = null;

    public Minefield(int fieldSize) {
        size = fieldSize;
        field = new char[fieldSize][fieldSize];
        for (char[] row : field) {
            Arrays.fill(row, '.');
        }
        mines = new boolean[fieldSize][fieldSize];
        for (boolean[] row : mines) {
            Arrays.fill(row, false);
        }
    }

    public Minefield(int fieldSize, int numOfMines) {
        this(fieldSize);
        rand = new Random();
        minesPlaced = numOfMines;
        minesCorrect = 0;
        minesMarked = 0;
        cellsToClear = (fieldSize * fieldSize) - numOfMines;
        steppedOnMine = false;
        firstMove = true;
        placeMinesOnField(numOfMines);

    }

    private void placeMinesOnField(int numOfMines) {
        for (int i = 0; i < numOfMines; ) {
            int spot = rand.nextInt(81);
            int row = spot / size;
            int col = spot % size;
            if (!mines[row][col]) {
                mines[row][col] = true;
                i++;
            }
        }
    }

    /**
     * Iterate through the field to get the number of mines that surrounds an empty cell.
     */
    private void determineNeighborCells() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) { // It shouldn't count the num of mines around a mine.
                if (mines[row][col]) {
                    continue;
                }
                int numOfMines = countMines(row, col);
                if (numOfMines > 0) {
                    field[row][col] = String.valueOf(numOfMines).charAt(0);
                }
            }
        }
    }

    /**
     * Determine how many mines are surrounding a specified cell. Ensures we don't go out of bounds whilst checking.
     * @param cellRow row of the field
     * @param cellCol collum of the field
     * @return number of mines around a specified cell
     */
    private char countMines(int cellRow, int cellCol) {
        int numOfMines = 0;
        for (int row = Math.max(0, cellRow - 1); row <= Math.min(size - 1, cellRow + 1); row++) {
            for (int col = Math.max(0, cellCol - 1); col <= Math.min(size - 1, cellCol + 1); col++) {
                if (row == cellRow && col == cellCol) { // Don't check the cell it's already checking
                    continue;
                }
                if (mines[row][col]) {
                    numOfMines++;
                }
            }
        }
        return numOfMines == 0 ? '/' : String.valueOf(numOfMines).charAt(0);
    }

    private void revealMines() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (mines[row][col]) {
                    field[row][col] = 'X';
                }
            }
        }
    }

    protected void markCell(int row, int col) {
        if (field[row][col] == '.') {
            field[row][col] = '*';
            minesMarked++;
            if (mines[row][col]) minesCorrect++;
            return;
        }
        if (field[row][col] == '*') {
            field[row][col] = '.';
            minesMarked--;
            if (mines[row][col]) minesCorrect--;
            return;
        }
        System.out.println("Cell already cleared. Try again.");
    }

    protected void clearCell(int row, int col) {
        if (firstMove) {
            while (mines[row][col]) {
                mines[row][col] = false;
                placeMinesOnField(1);
            }
            firstMove = false;
        }
        if (mines[row][col]) {
            steppedOnMine = true;
            revealMines();
            return;
        }
        if (!(field[row][col] == '.' || field[row][col] == '*')) {
            System.out.println("Cell already cleared.");
            return;
        }
        if (field[row][col] == '*') {
            minesMarked--;
        }
        field[row][col] = countMines(row, col);
        cellsToClear--;
        if (field[row][col] == '/') {
            clearZeroCell(row, col);
        }
    }

    private void clearZeroCell(int cellRow, int cellCol) {
        for (int row = Math.max(0, cellRow - 1); row <= Math.min(size - 1, cellRow + 1); row++) {
            for (int col = Math.max(0, cellCol - 1); col <= Math.min(size - 1, cellCol + 1); col++) {
                if (row == cellRow && col == cellCol) {
                    continue;
                }
                if (field[row][col] != '.' && field[row][col] != '*') {
                    continue;
                }
                if (field[row][col] == '*') {
                    minesMarked--;
                }
                field[row][col] = countMines(row, col);
                cellsToClear--;
                if (field[row][col] == '/') {
                    clearZeroCell(row, col);
                }
            }
        }
    }

    protected boolean allMinesFound() {
        return (minesMarked == minesCorrect && minesCorrect == minesPlaced) || cellsToClear == 0;
    }

    protected boolean isSteppedOnMine() {
        return steppedOnMine;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n |123456789|\n-|---------|\n");
        for (int row = 0; row < size; row++) {
            result.append(row + 1);
            result.append("|");
            for (int col = 0; col < size; col++) {
                result.append(field[row][col]);
            }
            result.append("|\n");
        }
        result.append("-|---------|");

        return String.valueOf(result);
    }
}

