package com.ollie.amogus.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    private int x;
    private int y;
    private Shape collisionDetector;

    private BufferedImage[] sprites;

    public GameObject(int x, int y, int sprites, Rectangle collisionDetector) {
        this.x = x;
        this.y = y;
        this.sprites = new BufferedImage[sprites];
        this.collisionDetector = collisionDetector;
    }

    public void setX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addSprites(BufferedImage[] sprites){
        this.sprites = sprites;
    }

    public BufferedImage[] getSprites() {
        return sprites;
    }

    public Shape getCollisionDetector() {
        return collisionDetector;
    }

    public abstract void drawImage(Graphics g);

    public abstract void updatePos(int x, int y);
}
