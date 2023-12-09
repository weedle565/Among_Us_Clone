package com.ollie.amogus.helper;

import com.ollie.amogus.rooms.Rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph{

    private final Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    public void addVertex(Rooms label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void addEdge(Rooms label1, Rooms label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    public List<Vertex> getAdjVertices(Rooms label) {
        return adjVertices.get(new Vertex(label));
    }

}
