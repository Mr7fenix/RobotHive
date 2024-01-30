package it.unicam.cs.pa.ma114110.space;

/**
 * This interface represents a direction, it can be used to move a robot.
 * direction is composed by two coordinates: x and y.
 * x and y can be between -1 and 1.
 */

public interface DirectionInterface {

        /**
        * @return x coordinate of the direction
        */
        double getX();

        /**
        * @return y coordinate of the direction
        */
        double getY();
}
