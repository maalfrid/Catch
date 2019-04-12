package com.tdt4240.catchgame;

public class ScaledObject {
    private ObjectType object;
    private double scale;

    public ScaledObject(ObjectType object, double scale){
        this.object = object;
        this.scale = scale;
    }

    public ObjectType getObject() {
        return object;
    }

    public double getScale(){
        return scale;
    }
}
