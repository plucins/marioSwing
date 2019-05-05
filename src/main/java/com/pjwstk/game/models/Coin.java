package com.pjwstk.game.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coin extends AbstractPaintable {
    private final static int sizeX = 30;
    private final static int sizeY = 30;

    private BufferedImage bufferedImage;

    public Coin(int positionYOffset, int positionX, int positionY) {
        super(positionX * sizeX, positionYOffset - positionY * sizeY);

        try {
            bufferedImage = ImageIO.read(new File("images/retro_coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics2D pen, int offset) {
        pen.drawImage(bufferedImage, positionX - offset, positionY, null);
    }

    @Override
    public int getWidth() {
        return sizeX;
    }

    @Override
    public int getHeight() {
        return sizeY;
    }
}
