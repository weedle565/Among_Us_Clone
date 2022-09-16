package com.ollie.amogus.gameobjects;

import com.ollie.amogus.main.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Background extends GameObject {

    public Background(int x, int y) throws IOException {
        super(x, y, 1, new Rectangle());

        super.getSprites()[0] = ImageIO.read(new File("src/resoures/map.png"));
    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(super.getSprites()[0], super.getX(), super.getY(), null);

    }

    @Override
    public void updatePos(int x, int y) {

        if(com.ollie.amogus.main.Frame.getG().getCrew().checkCollisions(x, y)) return;

        setX(x);
        setY(y);

        Frame.getG().getWalls().iterator().forEachRemaining(w -> w.updatePos(x, y));
    }
}
