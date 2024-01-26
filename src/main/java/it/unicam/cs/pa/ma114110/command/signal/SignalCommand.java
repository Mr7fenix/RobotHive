package it.unicam.cs.pa.ma114110.command;

import java.awt.*;

public class SignalCommand extends SampleCommand{

    String label;


    public SignalCommand (String label){
        if (!label.matches("[a-zA-Z_0-9]")){
            throw new IllegalArgumentException("Label can contains only alphanumeric character and '_'");
        }

        this.label = label;
    }
}
