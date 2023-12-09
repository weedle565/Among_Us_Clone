package com.ollie.amogus.helper;

import com.ollie.amogus.rooms.Rooms;

public class Vertex{

    private Rooms adjacentRoom;

    protected Vertex(Rooms adjacentRoom) {
        this.adjacentRoom = adjacentRoom;
    }

    public Rooms getAdjacentRoom() {
        return adjacentRoom;
    }

    public void setAdjacentRoom(Rooms adjacentRoom) {
        this.adjacentRoom = adjacentRoom;
    }
}
