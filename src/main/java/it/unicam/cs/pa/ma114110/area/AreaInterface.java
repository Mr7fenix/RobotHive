package it.unicam.cs.pa.ma114110.area;

import it.unicam.cs.pa.ma114110.space.Coords;

/**
 * This interface is used to represents an area in space.
 * Area can be square or circular. Every area represents a condition when is associated a label.
 *
 * @param <T> tipo di dato contenuto nell'area di gioco
 */
public interface AreaInterface {
    /**
     * This method returns the label associated to the area.
     *
     * @return the label associated to the area
     */
    String getLabel();

    /**
     * This method returns true if the given coordinates are inside the area.
     *
     * @param coords the coordinates to check
     * @return true if the given coordinates are inside the area
     */
    boolean contains(Coords coords);
}
