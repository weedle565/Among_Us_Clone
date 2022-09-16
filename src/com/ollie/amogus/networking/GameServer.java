package com.ollie.amogus.networking;

import com.ollie.amogus.gameobjects.entities.MPCrewMate;
import com.ollie.amogus.main.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private Game g;
    private List<MPCrewMate> players;

    public GameServer(Game g){
        this.g = g;

        try{
            socket = new DatagramSocket(1331);
        } catch (SocketException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];

            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port){

        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));

        Packet p = null;
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:

        }

    }

    public void sendData(byte[] data, InetAddress address, int port){

        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try{
            this.socket.send(packet);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendToAllClients(byte[] data){
        for(MPCrewMate crewMate : players){
            sendData(data, crewMate.getIp(), crewMate.getPort());
        }
    }

}
