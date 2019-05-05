package com.pjwstk.game.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ObstacleGround extends AbstractPaintable {
    private final static int sizeX = 30;
    private final static int sizeY = 30;

    private BufferedImage ground;

    public ObstacleGround(int positionYOffset, int positionX, int positionY) {
        super(positionX * sizeX, positionYOffset - positionY * sizeY);

        try {
            ground = ImageIO.read(new File("images/ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D pen, int offset) {
        pen.drawImage(ground, positionX - offset, positionY, null);
    }

    @Override
    public int getWidth() {
        return ground.getWidth();
    }

    @Override
    public int getHeight() {
        return ground.getHeight();
    }
}
