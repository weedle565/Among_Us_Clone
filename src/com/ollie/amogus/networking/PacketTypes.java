package com.ollie.amogus.networking;

public enum PacketTypes {

    INVALID(-1),
    LOGIN(0x00),
    DISCONNECT(0x01),
    MOVE(0x02),
    REPLY(0x03);

    private int packetId;

    PacketTypes(int packetId){
        this.packetId = packetId;
    }

    public int getPacketId() {
            return packetId;
        }

    public void setPacketId(int packetId) {
            this.packetId = packetId;
        }

}
