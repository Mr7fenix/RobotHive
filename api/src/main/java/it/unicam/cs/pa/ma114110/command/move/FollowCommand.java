package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.CommandInterface;

public record FollowCommand(String label, double distance, double s) implements CommandInterface {
    public FollowCommand {
        if (!label.matches("[a-zA-Z_0-9]+")) {
            throw new IllegalArgumentException("Label can contains only alphanumeric character and '_'");
        }

        if (distance < 0) {
            throw new IllegalArgumentException("distance must be max to 0");
        }

        if (s < 0) {
            throw new IllegalArgumentException("s must be max to 0");
        }

    }
}
