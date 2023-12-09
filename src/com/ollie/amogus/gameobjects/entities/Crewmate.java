package com.ollie.amogus.gameobjects.entities;

import com.ollie.amogus.gameobjects.GameObject;
import com.ollie.amogus.gameobjects.Wall;
import com.ollie.amogus.imagehandling.MirrorHandler;
import com.ollie.amogus.imagehandling.SpriteSheetLoader;
import com.ollie.amogus.main.Frame;
import com.ollie.amogus.networking.DisconnectPacket;
import com.ollie.amogus.networking.MovePacket;
import com.ollie.amogus.rooms.Rooms;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crewmate extends GameObject {

    private boolean isImposter;
    private boolean moving;

    private float rx, ry;

    private final String userName;

    private int displayNum;
    private int modifier;

    private Directions directions;

    private Rooms room;

    public Crewmate(float x, float y, String userName) {
        super(x, y, 4, new Rectangle((int) x, (int) y, 48, 48));

        rx = 0;
        ry = 0;

        this.userName = userName;

        displayNum = 0;
        modifier = 0;

        isImposter = false;
        moving = false;

        directions = Directions.RIGHT;

        BufferedImage[] sprites = new BufferedImage[8];
        //Load the sprites from the sprite sheet, (x, y);
        sprites[0] = SpriteSheetLoader.getSprite(0, 0);
        sprites[1] = SpriteSheetLoader.getSprite(1, 0);
        sprites[2] = SpriteSheetLoader.getSprite(2, 0);
        sprites[3] = SpriteSheetLoader.getSprite(3, 0);

        //Mirror the sprites to be able to get the correct direction for going the opposite direction
        sprites[4] = MirrorHandler.mirrorer(sprites[0]);
        sprites[5] = MirrorHandler.mirrorer(sprites[1]);
        sprites[6] = MirrorHandler.mirrorer(sprites[2]);
        sprites[7] = MirrorHandler.mirrorer(sprites[3]);

        super.addSprites(sprites);

    }

    @Override
    public synchronized void drawImage(Graphics g) {


        if(directions == Directions.LEFT) modifier = 4;
        else if (directions == Directions.RIGHT) modifier = 0;

         g.drawImage(super.getSprites()[displayNum + modifier], (int) getX(), (int) getY(), super.getSprites()[displayNum + modifier].getWidth()*3,super.getSprites()[displayNum + modifier].getHeight()*3, null);
         g.drawRect(getCollisionDetector().getBounds().x, getCollisionDetector().getBounds().y, getCollisionDetector().getBounds().width, getCollisionDetector().getBounds().height);
    }

    /*
        Update the position of the crewmate on anothers screen, as the crewmates real x and y never changes we need
        to work out where the crewmate should be on anothers screen, thus we use fake x and fake y.
        The movement is halfed as for some reason during testing the movement would be twice as fast as actuality over
        the network. On move, send a new move packet to the network with the fake x and y to update the position for other
        players
        @TODO Change directions.ordinal to getting a direction

     */
    @Override
    public synchronized void updatePos(float x, float y){
        setX(x);
        setY(y);

        getCollisionDetector().setLocation(new Point((int) getX(), (int) getY()));

        this.getCollisionDetector().getBounds().setLocation((int) getX(), (int) getY());


        MovePacket mp = new MovePacket(getUserName(), getX(), getY(), moving, directions.getNum());
        mp.writeData(Frame.getG().getClient());
    }

    public synchronized boolean checkCollisions(int x, int y){

        for(Wall wall : Frame.getG().getWalls()){

            Rectangle newDetector = new Rectangle((int) getX(), (int) getY(), (int) getCollisionDetector().getBounds().getWidth(), (int) getCollisionDetector().getBounds().getHeight());
            //System.out.println(newDetector.getX() + " " + newDetector.getY() + " " + ((Rectangle) w.getCollisionDetector()).getX() + ((Rectangle) w.getCollisionDetector()).getY());
            if(newDetector.intersects((Rectangle) wall.getCollisionDetector())){
                return true;
            }

        }

        return false;
    }

    public synchronized void changeDirection(Directions direction){

        directions = direction;
//        System.out.println(directions);

    }

    //Change which part of the animation the sprite is in
    public synchronized void addSpriteNum(boolean newClient){

        if(moving || newClient) {
            if ((displayNum + 1) < 4) displayNum++;
            else displayNum = 0;
        }
    }

    public boolean getMoving() {
        return moving;
    }

    public void setMoving(boolean moving){

        if(!moving)
            displayNum = 0;

        this.moving = moving;

    }

    public void changeRooms() {
        int test = 1;

        for (Rooms room : room.getAdjacentRooms()) {
            System.out.println(room);
            for (Rectangle boxes : room.getMoveHitbox()) {
                if (boxes.intersects(getCollisionDetector())) {
                    setRoom(room);
                    System.out.println(getRoom().getName());

                    switch (test) {
                        case 1 -> {
                            setNewX(room.getFromX1());
                            setNewY(room.getFromY1());
                            MovePacket mp = new MovePacket(getUserName(), getX(), getY(), moving, directions.getNum());
                            mp.writeData(Frame.getG().getClient());
                        }
                        case 2 -> {
                            setNewX(room.getFromX2());
                            setNewY(room.getFromY2());
                            MovePacket mp = new MovePacket(getUserName(), getX(), getY(), moving, directions.getNum());
                            mp.writeData(Frame.getG().getClient());
                        }
                        case 3 -> {
                            setNewX(room.getFromX3());
                            setNewY(room.getFromY3());
                            MovePacket mp = new MovePacket(getUserName(), getX(), getY(), moving, directions.getNum());
                            mp.writeData(Frame.getG().getClient());
                        }
                        case 4 -> {
                            setNewX(room.getFromX4());
                            setNewY(room.getFromY4());
                            MovePacket mp = new MovePacket(getUserName(), getX(), getY(), moving, directions.getNum());
                            mp.writeData(Frame.getG().getClient());
                        }
                    }

                }
            }
            test++;
        }
    }

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    public String getUserName() {
        return userName;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }
}
