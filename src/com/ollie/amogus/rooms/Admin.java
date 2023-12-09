package com.ollie.amogus.rooms;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Admin extends Rooms {

    public Admin() throws IOException {
        super(ImageIO.read(new File("resoures/admin.png")), "cafeteria");

        setFromX1(500);
        setFromX2(200);
        setFromX3(300);
        setFromX4(400);

        setFromY1(900);
        setFromY2(200);
        setFromY3(300);
        setFromY4(400);

        addNewMoveHitbox(new Rectangle(975, 450, 30, 250));
    }

    @Override
    public void drawBackground(Graphics g) {

    }
}
