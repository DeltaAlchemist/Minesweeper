package minesweeper;

public class Coordinates {
    boolean empty, flagged;
    char symbol;

    Coordinates() {
        this.empty = true;
        this.flagged = false;
        this.symbol = '.';
    }

    public void toggleFlag() {
        setFlagged(!flagged);
        setSymbol(isFlagged() ? '*' : '.');
    }

    public void placeMine() {
        setEmpty(false);
        setSymbol('.');
    }
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
