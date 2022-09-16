package com.ollie.amogus.gameobjects.entities;

import java.net.InetAddress;

public class MPCrewMate extends Crewmate {

    private InetAddress ip;
    private int port;

    public MPCrewMate(int x, int y, String userName, InetAddress ip, int port) {
        super(x, y, userName);

        this.ip = ip;
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
