package com.tdt4240.catchgame.Model;

import java.util.Objects;

/*
Class to generate a key for resized images that should be stored for future use so the scaling only has to happen once.
 */

public class ScaledObject {
    private ObjectType object;
    private double scale;

    public ScaledObject(ObjectType object, double scale) {
        this.object = object;
        this.scale = scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScaledObject)) return false;
        ScaledObject that = (ScaledObject) o;
        return Double.compare(that.scale, scale) == 0 &&
                object == that.object;
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, scale);
    }
}
