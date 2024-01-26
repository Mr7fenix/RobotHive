package it.unicam.cs.pa.ma114110.area;

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
}
