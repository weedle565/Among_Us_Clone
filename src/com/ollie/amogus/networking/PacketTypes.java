package com.ollie.amogus.networking;

public enum PacketTypes {

    INVALID,
    LOGIN,
    DISCONNECT,
    UPDATE;

    private int packetId;
    public int getPacketId() {
            return packetId;
        }

    public void setPacketId(int packetId) {
            this.packetId = packetId;
        }

}
