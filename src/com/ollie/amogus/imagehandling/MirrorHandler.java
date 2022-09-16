package com.ollie.amogus.imagehandling;

import java.awt.image.BufferedImage;

public class MirrorHandler {

    public static BufferedImage mirrorer(BufferedImage mirror){
        int width = mirror.getWidth();
        int height = mirror.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < height; y++){
            for(int lx = 0, rx = width - 1; lx < width; lx++, rx--) {

                int p = mirror.getRGB(lx, y);

                mirroredImage.setRGB(rx, y, p);

            }
        }

        return mirroredImage;
    }

}
