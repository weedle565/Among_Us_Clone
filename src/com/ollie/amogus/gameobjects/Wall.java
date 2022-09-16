package com.ollie.amogus.gameobjects;

import com.ollie.amogus.gameobjects.GameObject;

import java.awt.*;

//Move rectangles to CIRCLES;
public class Wall extends GameObject {

    public Wall(int x, int y, int rectWidth, int rectHeight) {
        super(x, y, 0, new Rectangle(x, y, rectWidth, rectHeight));

    }

    @Override
    public void drawImage(Graphics g) {

        g.setColor(Color.RED);
        g.drawRect(getX(), getY(), (int) ((Rectangle)super.getCollisionDetector()).getWidth(), (int) ((Rectangle)super.getCollisionDetector()).getHeight());

    }

    @Override
    public synchronized void updatePos(int x, int y) {

        setX(x);
        setY(y);

    }
}
