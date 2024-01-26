package com.mr7fenix;

import java.util.Objects;

public class Coords implements CoordsInterface {
    private final Integer x;
    private final Integer y;

    /**
     * @param x the x coordinate for the specifico cell
     * @param y the y coordinate for the specifico cell
     */
    public Coords(Integer x, Integer y) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }


    @Override
    public Integer getX() {
        return x;
    }

    @Override
    public Integer getY() {
        return y;
    }



}
