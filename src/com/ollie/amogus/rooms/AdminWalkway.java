package com.ollie.amogus.rooms;

import com.ollie.amogus.imagehandling.SpriteSheetLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminWalkway extends Rooms {

    public AdminWalkway() throws IOException {
        super(ImageIO.read(new File("resoures/adminwalkway.png")), "adminwalkway");

        addNewMoveHitbox(new Rectangle(455, 940, 125, 30));

        setFromX1(300);
        setFromX2(200);
        setFromX3(300);
        setFromX4(400);

        setFromY1(50);
        setFromY2(200);
        setFromY3(300);
        setFromY4(400);

    }

    @Override
    public void drawBackground(Graphics g) {
        g.drawImage(getBackImage(), 50, 0, 950, 1000, null);

    }
}
