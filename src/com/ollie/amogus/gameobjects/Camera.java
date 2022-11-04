package com.ollie.amogus.gameobjects;

import com.ollie.amogus.main.Frame;

import java.awt.*;

public class Camera extends GameObject {

    public Camera(float x, float y, int sprites, Rectangle collisionDetector) {
        super(x, y, sprites, collisionDetector);
    }

    @Override
    public void drawImage(Graphics g) {

    }

    @Override
    public void updatePos(float x, float y) {

        setX(x);
        setY(y);

    }
}
