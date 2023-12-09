package com.ollie.amogus.rooms;

import com.ollie.amogus.helper.Graph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Rooms {

    private final BufferedImage backImage;
    private final String name;

    private ArrayList<Rooms> adjacentRooms;

    private final ArrayList<Rectangle> hitboxes;

    private final ArrayList<Rectangle> moveHitbox;

    private int fromX1;
    private int fromY1;

    private int fromX2;
    private int fromY2;

    private int fromX3;
    private int fromY3;

    private int fromX4;
    private int fromY4;


    public Rooms(BufferedImage backImage, String name) {
        this.backImage = backImage;
        this.name = name;

        adjacentRooms = new ArrayList<>();
        hitboxes = new ArrayList<>();
        moveHitbox = new ArrayList<>();
    }

    protected void addNewHitbox(Rectangle newBox) {
        hitboxes.add(newBox);
    }

    public ArrayList<Rooms> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setAdjacentRooms(ArrayList<Rooms> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    protected void addAdjacentRoom(Rooms room) {
        adjacentRooms.add(room);
    }

    protected void addNewMoveHitbox(Rectangle newBox) {
        moveHitbox.add(newBox);
    }

    public ArrayList<Rectangle> getMoveHitbox() {
        return moveHitbox;
    }

    public void displayMoveHitboxes(Graphics g) {

        for (Rooms r : adjacentRooms) {
            for (Rectangle move : r.getMoveHitbox()) {
                g.setColor(Color.RED);
                g.drawRect(move.x, move.y, move.width, move.height);
            }
        }

    }

    public void displayHitboxes(Graphics g) {

        for (Rectangle r : hitboxes) {
            g.setColor(Color.RED);
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }

    public abstract void drawBackground(Graphics g);

    public BufferedImage getBackImage() {
        return backImage;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Rectangle> getHitboxes() {
        return hitboxes;
    }

    public int getFromX1() {
        return fromX1;
    }

    public void setFromX1(int fromX1) {
        this.fromX1 = fromX1;
    }

    public int getFromY1() {
        return fromY1;
    }

    public void setFromY1(int fromY1) {
        this.fromY1 = fromY1;
    }

    public int getFromX2() {
        return fromX2;
    }

    public void setFromX2(int fromX2) {
        this.fromX2 = fromX2;
    }

    public int getFromY2() {
        return fromY2;
    }

    public void setFromY2(int fromY2) {
        this.fromY2 = fromY2;
    }

    public int getFromX3() {
        return fromX3;
    }

    public void setFromX3(int fromX3) {
        this.fromX3 = fromX3;
    }

    public int getFromY3() {
        return fromY3;
    }

    public void setFromY3(int fromY3) {
        this.fromY3 = fromY3;
    }

    public int getFromX4() {
        return fromX4;
    }

    public void setFromX4(int fromX4) {
        this.fromX4 = fromX4;
    }

    public int getFromY4() {
        return fromY4;
    }

    public void setFromY4(int fromY4) {
        this.fromY4 = fromY4;
    }
}
