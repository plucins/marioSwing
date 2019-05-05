package com.pjwstk.game.models;

import com.pjwstk.game.events.Dispatcher;
import com.pjwstk.game.interfaces.ICollisionListener;
import com.pjwstk.game.interfaces.IJumpListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameHero extends AbstractPaintable implements ICollisionListener, IJumpListener {
    private BufferedImage bufferedImage;
    private double verticalVelocity = 0.0;
    private final double GRAVITY = 0.01;

    public GameHero(int positionX, int positionY) {
        super(positionX, positionY);

        try {
            bufferedImage = ImageIO.read(new File("images/final_mario.png"));
        } catch (IOException e) {
            System.err.println("Nie ma takiego pliku!");
            e.printStackTrace();
        }
        Dispatcher.instance.registerObject(this);
    }



    public void moveVertical(double timeDifference) {
        positionY += Math.floor(verticalVelocity * timeDifference);
        if (verticalVelocity != 0.0 && verticalVelocity < 1.0) {
            verticalVelocity += (timeDifference * GRAVITY);
        }
    }

    public void fall() {
        if (verticalVelocity >= 0.0)
            verticalVelocity = 1.0;
    }

    @Override
    public boolean canMoveDown(AbstractPaintable other) {
        boolean canMove = super.canMoveDown(other);
        if (!canMove && verticalVelocity > 0.0) {
            verticalVelocity = 0.0;
        }

        return canMove;
    }

    @Override
    public void moveUp(double timeDifference) {
    }

    @Override
    public void moveDown(double timeDifference) {
    }

    public void jump() {
        if (verticalVelocity == 0.0) {
            verticalVelocity = -1.0;
        }
    }

    public void paint(Graphics2D pen, int offset) {
        pen.drawImage(bufferedImage, positionX - offset, positionY, null);
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public void collisionVertical(AbstractPaintable with) {
        if (this.positionY <= with.positionY) {
            positionY = with.positionY - getHeight();
            verticalVelocity = 0.0;
            // jestem wyżej od przeszkody
        } else if (this.positionY >= with.positionY) {
            // jestem niżej od przeszkody
            positionY = with.positionY + with.getHeight();
            fall();
        }
    }

    @Override
    public void collisionHorizontal(AbstractPaintable with) {
        if (this.positionX <= with.positionX) {
            // ja jestem z lewej od przeszkody
            this.positionX = with.positionX - getWidth() - 1;
        } else if (this.positionX >= with.positionX) {
            this.positionX = with.positionX + with.getWidth() + 1;
        }
    }
}
