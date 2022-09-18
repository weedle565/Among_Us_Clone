package com.ollie.amogus.networking;

import com.ollie.amogus.gameobjects.entities.Directions;

public class MovePacket extends Packet {

    private String username;
    private int x, y;

    private boolean moving;
    private int directions;

    public MovePacket(byte[] data) {
        super(02);

        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.moving = Integer.parseInt(dataArray[3]) == 1;
        this.directions = Integer.parseInt(dataArray[4]);
    }

    public MovePacket(String username, int x, int y, boolean moving, int directions) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.directions = directions;
    }

    @Override
    public void writeData(GameClient c) {
        c.sendData(getData());
    }

    @Override
    public void writeData(GameServer e) {

        e.sendToAllClients(getData());

    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y + "," + (moving ? 1 : 0)
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

    public boolean isMoving() {
        return moving;
    }

    public Directions getDirections() {
        return switch (directions) {
            case 1 -> Directions.LEFT;
            case 2 -> Directions.BACK;
            case 3 -> Directions.RIGHT;
            default -> Directions.FORWARD;
        };
    }
}
