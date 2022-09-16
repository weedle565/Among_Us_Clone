package com.ollie.amogus.networking;

import com.ollie.amogus.gameobjects.entities.Directions;

public class MovePacket extends Packet {

    private String username;
    private int x, y;

    private int steps;
    private boolean moving;
    private int directions;

    public MovePacket(byte[] data) {
        super(02);

        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.steps = Integer.parseInt(dataArray[3]);
        this.moving = Integer.parseInt(dataArray[4]) == 1;
        this.directions = Integer.parseInt(dataArray[5]);
    }

    public MovePacket(String username, int x, int y, int steps, boolean moving, int directions) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.steps = steps;
        this.moving = moving;
        this.directions = directions;
    }

    @Override
    public void writeData(GameClient c) {

    }

    @Override
    public void writeData(GameServer e) {

        e.sendToAllClients(getData());

    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y + "," + this.steps + "," + (moving ? 1 : 0)
                + "," + this.directions).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getDirections() {
        return directions;
    }
}
