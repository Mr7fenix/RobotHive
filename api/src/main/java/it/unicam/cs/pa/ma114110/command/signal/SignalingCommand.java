package it.unicam.cs.pa.ma114110.command.signal;

import it.unicam.cs.pa.ma114110.command.Command;

public record SignalingCommand(String label, boolean signal) implements Command {
    public SignalingCommand {
        if (!label.matches("[a-zA-Z_0-9]+")) {
            throw new IllegalArgumentException("Label can contains only alphanumeric character and '_'");
        }
    }
}
