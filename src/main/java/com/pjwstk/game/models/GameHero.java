package com.pjwstk.game.models;

import com.pjwstk.game.events.Dispatcher;
import com.pjwstk.game.interfaces.ICollisionListener;
import com.pjwstk.game.interfaces.IJumpListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameHero extends AbstractPaintable implements ICollisionListener, IJumpListener {
    private BufferedImage bufferedImage;
    private double verticalVelocity = 0.0;
    private final double GRAVITY = 0.01;
    private int licznik = 1;
    private Map<String, BufferedImage> imageMap = new HashMap<>();
    private boolean isMoving = false;
    private boolean movingLeft = false;



    public GameHero(int positionX, int positionY) {
        super(positionX, positionY);
        loadHeroImages();

        try {
            bufferedImage = ImageIO.read(new File("images/final_mario.png"));
        } catch (IOException e) {
            System.err.println("Nie ma takiego pliku!");
            e.printStackTrace();
        }
        Dispatcher.instance.registerObject(this);
    }


    public void animateHero() {
        if(isMoving) {
            if(movingLeft) {
                bufferedImage = imageMap.get("left_" + licznik);
            }else {
                bufferedImage = imageMap.get("right_" + licznik );
            }
        } else {
            bufferedImage = imageMap.get("front");
        }


        if(licznik < 3) {
            licznik++;
        } else {
            licznik = 1;
        }
    }

    private void loadHeroImages() {

        try {
            imageMap.put("front", ImageIO.read(new File("images/image_front.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 1; i < 4 ; i++){
            try {
                imageMap.put("left_" +i, ImageIO.read(new File("images/image_left_"+i+".png")));
                imageMap.put("right_" +i, ImageIO.read(new File("images/image_right_"+i+".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
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
