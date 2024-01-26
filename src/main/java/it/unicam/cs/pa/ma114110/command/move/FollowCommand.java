package it.unicam.cs.pa.ma114110.command.move;

import it.unicam.cs.pa.ma114110.command.SampleCommand;

public class FollowCommand extends SampleCommand {
    private final String label;
    private final double distance;
    private final double s;

    public FollowCommand(String label, double distance, double s) {
        if (!label.matches("[a-zA-Z_0-9]+")) {
            throw new IllegalArgumentException("Label can contains only alphanumeric character and '_'");
        }

        if (distance < 0) {
            throw new IllegalArgumentException("distance must be max to 0");
        }

        if (s < 0) {
            throw new IllegalArgumentException("s must be max to 0");
        }

        this.label = label;
        this.distance = distance;
        this.s = s;
    }


    public String getLabel() {
        return label;
    }

    public double getDistance() {
        return distance;
    }

    public double getS() {
        return s;
    }
}
