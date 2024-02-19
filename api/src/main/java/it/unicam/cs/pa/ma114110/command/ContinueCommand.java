package it.unicam.cs.pa.ma114110.command;

public class ContinueCommand implements Command {
    private final int s;

    public ContinueCommand(int s) {
        if (s <= 0) {
            throw new IllegalArgumentException("s must be greater than 0");
        }

        this.s = s;
    }

    public int getS() {
        return s;
    }
}
