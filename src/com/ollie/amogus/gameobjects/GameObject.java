package com.ollie.amogus.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    private float x, y;
    private final Shape collisionDetector;

    private BufferedImage[] sprites;

    public GameObject(float x, float y, int sprites, Rectangle collisionDetector) {
        this.x = x;
        this.y = y;
        this.sprites = new BufferedImage[sprites];
        this.collisionDetector = collisionDetector;
    }

    public void setX(float x) {
        this.x += x;
    }

    public void setNewX(float x){
        this.x = x;
    }

    public void setNewY(float y){
        this.y = y;
    }

    public void setY(float y) {
        this.y += y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
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

    public abstract void updatePos(float x, float y);
}
