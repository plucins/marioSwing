package com.pjwstk.game.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gate extends AbstractPaintable {
    private BufferedImage image;

    public Gate(int positionYOffset, int positionX, int positionY) {
        super(positionX * 30, positionYOffset - positionY * 30);
        try {
            image = ImageIO.read(new File("images/gate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics2D pen, int offset) {
        pen.drawImage(image, positionX - offset, positionY - getHeight() + 30, null);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }
}
