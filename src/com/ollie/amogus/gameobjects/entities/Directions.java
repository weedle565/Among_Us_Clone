package com.ollie.amogus.gameobjects.entities;

public enum Directions {


    FORWARD(1),
    BACK(2),
    LEFT(3),
    RIGHT(4);

    private int num;

    Directions(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
