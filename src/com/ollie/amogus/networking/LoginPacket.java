package com.ollie.amogus.networking;

public class LoginPacket extends Packet {

    private String username;
    private int x, y;

    public LoginPacket(byte[] data){
        super(00);

        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);

    }

    public LoginPacket(String username, int x, int y){
        super(00);

        this.username = username;
        this.x = x;
        this.y = y;
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
        return ("00" + this.username + "," + getX() + "," + getY()).getBytes();
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
}