package com.ollie.amogus.rooms;

import com.ollie.amogus.imagehandling.SpriteSheetLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Cafeteria extends Rooms {

    public Cafeteria() throws IOException {
        super(ImageIO.read(new File("resoures/cafeteria.png")), "cafeteria");

        setupRooms();

        setFromX1(500);
        setFromX2(200);
        setFromX3(300);
        setFromX4(400);

        setFromY1(900);
        setFromY2(200);
        setFromY3(300);
        setFromY4(400);

        addNewMoveHitbox(new Rectangle(100, 10, 400, 30));


    }

    public void setupRooms() throws IOException {

        Admin a = new Admin();
        AdminWalkway aw = new AdminWalkway();

        a.addAdjacentRoom(aw);
        aw.addAdjacentRoom(this);
        aw.addAdjacentRoom(a);

        addAdjacentRoom(aw);

    }

    @Override
    public void drawBackground(Graphics g) {

        g.drawImage(getBackImage(), 15, 0, null);

    }
}
