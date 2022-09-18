package com.ollie.amogus.gameobjects;

import com.ollie.amogus.gameobjects.entities.Crewmate;
import com.ollie.amogus.gameobjects.entities.Directions;
import com.ollie.amogus.gameobjects.entities.MPCrewMate;
import com.ollie.amogus.main.Frame;
import com.ollie.amogus.networking.MovePacket;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

public class Map {

    private ArrayList<Crewmate> crewmates = new ArrayList<>();
    private BufferedImage backImage;
    int x, y;

    public Map(int x, int y) throws IOException {

        this.x = x;
        this.y = y;
        backImage = ImageIO.read(Objects.requireNonNull(Map.class.getResource("/resoures/map.png")));
    }

    public void drawImage(Graphics g) {

        g.drawImage(backImage, getX(), getY(), null);

    }

    public synchronized void updatePos(int x, int y) {

        if(com.ollie.amogus.main.Frame.getG().getCrew().checkCollisions(x, y)) return;

        setX(x);
        setY(y);

        Frame.getG().getWalls().iterator().forEachRemaining(w -> w.updatePos(x, y));

    }

    public synchronized void addCrewMate(Crewmate c){
        crewmates.add(c);
    }

    public void render(Graphics g){
        for(Crewmate c : crewmates){
            System.out.println(c.getUserName() + " " + c.getX() + " " + c.getY());
            c.drawImage(g);
        }
    }

    private int getCrewmateIndex(String username){

        int i = 0;
        for(Crewmate c : crewmates){
            if(c instanceof MPCrewMate && c.getUserName().equals(username))
                break;

            i++;
        }

        return i;

    }

    public synchronized void moveCrewmates(String username, int x, int y, Directions movingDir){

        int i = getCrewmateIndex(username);
        MPCrewMate crewmate = (MPCrewMate) crewmates.get(i);

        crewmate.setNewX(x);
        crewmate.setNewY(y);
        crewmate.changeDirection(movingDir);

    }

    public void setX(int x) {
        this.x += x;
    }

    public void setY(int y) {
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Crewmate> getCrewmates() {
        return crewmates;
    }
}
