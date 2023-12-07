package com.ollie.amogus.networking;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReplyLoginPacket extends Packet {

    private final String username;
    private final float x;
    private final float y;
    private final String ip;
    private final int port;

    public ReplyLoginPacket(byte[] data){
        super(0x03);

        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Float.parseFloat(dataArray[1]);
        this.y = Float.parseFloat(dataArray[2]);
        this.ip = dataArray[3];

        this.port = Integer.parseInt(dataArray[4]);


    }

    public ReplyLoginPacket(float x, float y, String username, String ip, int port){
        super(0x03);

        this.x = x;
        this.y = y;
        this.username = username;
        this.ip = ip;

        this.port = port;

    }

    @Override
    public void writeData(GameClient c ) {

        c.sendData(getData());
    }

    @Override
    public void writeData(GameServer e) {
        e.sendToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("03" + this.username + "," + getX() + "," + getY() + "," + getIp() + "," + getPort()).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
