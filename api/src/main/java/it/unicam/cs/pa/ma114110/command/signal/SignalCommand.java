package it.unicam.cs.pa.ma114110.command.signal;

import it.unicam.cs.pa.ma114110.command.SampleCommand;

import java.awt.*;

public class SignalCommand extends SampleCommand {

    private final String label;


    public SignalCommand (String label){
        if (!label.matches("[a-zA-Z_0-9]+")){
            throw new IllegalArgumentException("Label can contains only alphanumeric character and '_'");
        }

        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
