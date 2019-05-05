package com.pjwstk.game.models;

import com.pjwstk.game.events.Dispatcher;
import com.pjwstk.game.interfaces.IRewardListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Score extends AbstractPaintable implements IRewardListener {
    private int lives;
    private int coinsCount;

    private BufferedImage heart;

    public Score() {
        super(0, 0);
        this.lives = 3;
        this.coinsCount = 0;

        try {
            heart = ImageIO.read(new File("images/Pixel_heart_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dispatcher.instance.registerObject(this);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public void setCoinsCount(int coinsCount) {
        this.coinsCount = coinsCount;
    }

    @Override
    public void paint(Graphics2D pen, int offset) {
        pen.setColor(Color.black);
        pen.setFont(new Font("Arial", Font.BOLD, 18));
        pen.drawString("Coins: " + coinsCount, 0, 50);

        for (int i = 0; i < lives; i++) {
            pen.drawImage(heart, 0 + i * 30, 0, null);
        }
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void reward(AbstractPaintable paintable) {
        if (paintable instanceof Coin) {
            coinsCount++;
        }
        if (coinsCount >= 100) {
            coinsCount = 0;
            lives++;
        }
    }
}
