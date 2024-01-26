package it.unicam.cs.pa.ma114110.command;

public class MoveCommand extends SampleCommand {
    Integer x;
    Integer y;

    public MoveCommand(String command) {
        super(command);
    }

    @Override
    public boolean verifyCommand(String command) {
        return super.verifyCommand(command);
    }
}
