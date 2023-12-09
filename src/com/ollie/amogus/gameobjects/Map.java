package com.ollie.amogus.gameobjects;

import com.ollie.amogus.gameobjects.entities.Crewmate;
import com.ollie.amogus.gameobjects.entities.Directions;
import com.ollie.amogus.gameobjects.entities.MPCrewMate;
import com.ollie.amogus.helper.Graph;
import com.ollie.amogus.main.Frame;
import com.ollie.amogus.main.Game;
import com.ollie.amogus.networking.MovePacket;
import com.ollie.amogus.rooms.AdminWalkway;
import com.ollie.amogus.rooms.Cafeteria;
import com.ollie.amogus.rooms.Rooms;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Map {

    private final ArrayList<Crewmate> crewmates = new ArrayList<>();
    int x, y;
    private final Crewmate localCrewmate;

    private final Game g;
    private int counter;

    public Map(int x, int y, Game g, Crewmate localCrewmate) throws IOException {

        this.g = g;
        this.localCrewmate = localCrewmate;

        this.x = x;
        this.y = y;
        this.counter = 1;

    }

    public synchronized void updatePos(int x, int y) {

//        if(com.ollie.amogus.main.Frame.getG().getCrew().checkCollisions(x, y)) return;

        setX(x);
        setY(y);

        Frame.getG().getWalls().iterator().forEachRemaining(w -> w.updatePos(x, y));

    }

    public synchronized void addCrewMate(Crewmate c){

        if (getCrewmateIndex(c.getUserName()) >= 0) return;

        c.setNewX(512 - g.getCrew().getRx());
        c.setNewY(600 - g.getCrew().getRy());

        crewmates.add(c);

    }

    public synchronized void checkIfInGame(Crewmate crew) {

        if (getCrewmateIndex(crew.getUserName()) == -1) {
            System.out.println(crew.getUserName() + " Has been added to the game!");
            addCrewMate(crew);
        }

    }

    public synchronized void removeCrewmate(String username) {

        crewmates.get(getCrewmateIndex(username)).updatePos(10000, 10000);

        crewmates.remove(getCrewmateIndex(username));
        g.repaint();
        System.gc();

    }

    public synchronized void render(Graphics g){
//        System.out.println(crewmates.size());
        for(Crewmate c : crewmates){
            c.drawImage(g);
        }
    }

    private int getCrewmateIndex(String username){

        int i = 0;
        boolean found = false;
        for(Crewmate c : crewmates){
            if(c instanceof MPCrewMate && c.getUserName().equals(username)) {
                found = true;
                break;
            }

            i++;
        }

        return found ? i : -1;

    }

    public synchronized void moveCrewmates(String username, float x, float y, Directions movingDir){

        if (Objects.equals(username, localCrewmate.getUserName())) return;

        for(Crewmate m : crewmates) {

            MPCrewMate crewmate = (MPCrewMate) m;

            if (!g.getCrew().getUserName().equals(username) && Objects.equals(m.getUserName(), username)) {

                if (counter % 10 == 0) {
                    crewmate.addSpriteNum(true);
                }

                crewmate.changeDirection(movingDir);
                crewmate.setNewX(x);
                crewmate.setNewY(y);
                crewmate.getCollisionDetector().getBounds().setLocation(getX(), getY());

                counter++;
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
