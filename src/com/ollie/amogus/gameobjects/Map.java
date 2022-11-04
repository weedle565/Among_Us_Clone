package com.ollie.amogus.gameobjects;

import com.ollie.amogus.gameobjects.entities.Crewmate;
import com.ollie.amogus.gameobjects.entities.Directions;
import com.ollie.amogus.gameobjects.entities.MPCrewMate;
import com.ollie.amogus.main.Frame;
import com.ollie.amogus.main.Game;
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

    private Game g;

    public Map(int x, int y, Game g) throws IOException {

        this.g = g;

        this.x = x;
        this.y = y;
        backImage = ImageIO.read(Objects.requireNonNull(Map.class.getResource("/resoures/map.png")));
    }

    public void drawImage(Graphics g) {

        g.drawImage(backImage, getX(), getY(), null);

    }

    public synchronized void updatePos(int x, int y) {

//        if(com.ollie.amogus.main.Frame.getG().getCrew().checkCollisions(x, y)) return;

        setX(x);
        setY(y);

        Frame.getG().getWalls().iterator().forEachRemaining(w -> w.updatePos(x, y));

    }

    public synchronized void addCrewMate(Crewmate c){
        c.setNewX(512 - g.getCrew().getRx());
        c.setNewY(600 - g.getCrew().getRy());

        System.out.println(c.getX() + " " + c.getY() + " " + g.getCrew().getRx() + " " + g.getCrew().getRy());

        crewmates.add(c);
    }

    public void render(Graphics g, Crewmate crewmate){
        for(Crewmate c : crewmates){
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

    public synchronized void moveCrewmates(String username, float x, float y, Directions movingDir){

        for(Crewmate m : crewmates) {

            MPCrewMate crewmate = (MPCrewMate) m;

            if (g.getCrew().getUserName().equals(username)) {

                crewmate.changeDirection(movingDir);

                System.out.println(x + " " + y + " " + crewmate.getRx() + " " + crewmate.getRy());
            } else {
                crewmate.setNewX(x - 512);
                crewmate.setNewY(y - 600);

                System.out.println(crewmate.getX() + " " + crewmate.getY() + " " + x + " " + y);
            }
        }

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
