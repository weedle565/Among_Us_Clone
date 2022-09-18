package com.ollie.amogus.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    private int x, y, fakeX, fakeY;
    private final Shape collisionDetector;

    private BufferedImage[] sprites;

    public GameObject(int x, int y, int sprites, Rectangle collisionDetector) {
        this.x = x;
        this.y = y;
        this.fakeX = x;
        this.fakeY = y;
        this.sprites = new BufferedImage[sprites];
        this.collisionDetector = collisionDetector;
    }

    public void setX(int x) {
        this.x += x;
    }

    public void setNewX(int x){
        this.x = x;
    }

    public void setNewY(int y){
        this.y = y;
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

    public int getFakeX() {
        return fakeX;
    }

    public int getFakeY() {
        return fakeY;
    }

    public void setFakeX(int fakeX) {
        this.fakeX += fakeX;
    }

    public void setFakeY(int fakeY) {
        this.fakeY += fakeY;
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
