package com.ollie.amogus.gameobjects.entities;

import com.ollie.amogus.gameobjects.GameObject;
import com.ollie.amogus.gameobjects.Wall;
import com.ollie.amogus.imagehandling.MirrorHandler;
import com.ollie.amogus.imagehandling.SpriteSheetLoader;
import com.ollie.amogus.main.Frame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crewmate extends GameObject {

    private boolean isImposter;
    private boolean moving;

    private String userName;

    private int displayNum;
    private int modifier;

    private Directions directions;

    public Crewmate(int x, int y, String userName) {
        super(x, y, 4, new Rectangle(x, y, 48, 48));

        this.userName = userName;

        displayNum = 0;
        modifier = 0;

        isImposter = false;
        moving = false;

        directions = Directions.RIGHT;

        BufferedImage[] sprites = new BufferedImage[8];
        sprites[0] = SpriteSheetLoader.getSprite(0, 0);
        sprites[1] = SpriteSheetLoader.getSprite(1, 0);
        sprites[2] = SpriteSheetLoader.getSprite(2, 0);
        sprites[3] = SpriteSheetLoader.getSprite(3, 0);

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

         g.drawImage(super.getSprites()[displayNum + modifier], getX(), getY(), super.getSprites()[displayNum + modifier].getWidth()*3,super.getSprites()[displayNum + modifier].getHeight()*3, null);
         g.drawRect(getCollisionDetector().getBounds().x, getCollisionDetector().getBounds().y, getCollisionDetector().getBounds().width, getCollisionDetector().getBounds().height);
    }

    @Override
    public synchronized void updatePos(int x, int y){

    }

    public synchronized boolean checkCollisions(int x, int y){

        for(Wall w : Frame.getG().getWalls()){

            Rectangle newDetector = new Rectangle(getX(), getY(), (int) getCollisionDetector().getBounds().getWidth(), (int) getCollisionDetector().getBounds().getHeight());
            System.out.println(newDetector.getX() + " " + newDetector.getY() + " " + ((Rectangle) w.getCollisionDetector()).getX() + ((Rectangle) w.getCollisionDetector()).getY());
            if(newDetector.intersects((Rectangle) w.getCollisionDetector())){
                return true;
            }

        }

        return false;
    }

    public synchronized void changeDirection(Directions d){

        directions = d;

    }

    public synchronized void addSpriteNum(){

        if(moving) {
            if ((displayNum + 1) < 4) displayNum++;
            else displayNum = 0;
        }
    }

    public void setMoving(boolean moving){

        if(!moving)
            displayNum = 0;

        this.moving = moving;

    }

}
