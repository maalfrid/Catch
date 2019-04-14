package com.tdt4240.catchgame;

import java.util.Objects;

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
