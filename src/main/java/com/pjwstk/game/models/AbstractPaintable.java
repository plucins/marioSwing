package com.pjwstk.game.models;

import java.awt.*;

public abstract class AbstractPaintable {
    int positionX;
    int positionY;
    private int speed = 1;

    public AbstractPaintable(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public abstract void paint(Graphics2D pen, int offset);

    public void moveLeft(double timeDifference) {
        positionX -= Math.floor(speed * timeDifference);
    }

    public void moveRight(double timeDifference) {
        positionX += Math.floor(speed * timeDifference);
    }

    public void moveUp(double timeDifference) {
        positionY -= Math.floor(speed * timeDifference);
    }

    public void moveDown(double timeDifference) {
        positionY += Math.floor(speed * timeDifference);
    }


    public abstract int getWidth();

    public abstract int getHeight();


    public boolean checkCollision(AbstractPaintable other) {
        if (other == null) {
            return false;
        }

        int posX = positionX;
        int posY = positionY;
        int posXEnd = positionX + getWidth();
        int posYEnd = positionY + getHeight();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();

        if (checkCollision(posX, posY, posXEnd, posYEnd, obstacleX, obstacleY, obstacleXEnd, obstacleYEnd)) return true;

        return false;
    }

    public boolean checkVerticalCollision(AbstractPaintable other) {
        if (other == null) {
            return false;
        }

        int posX = positionX;
        int posY = positionY;
        int posXEnd = positionX + getWidth();
        int posYEnd = positionY + getHeight();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();
        if (checkCollision(other)) {
//            if ((posY < obstacleY && posYEnd > obstacleY) || (obstacleYEnd > posY && obstacleYEnd < posYEnd)) {
                return true;
//            }
        }
        return false;
    }

    public boolean checkHorizontalCollision(AbstractPaintable other) {
        if (other == null) {
            return false;
        }

        int posX = positionX;
        int posY = positionY;
        int posXEnd = positionX + getWidth();
        int posYEnd = positionY + getHeight();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();
        if (checkCollision(other)) {
            if ((posX <= obstacleX && posXEnd >= obstacleX) || (obstacleXEnd >= posX && obstacleXEnd <= posXEnd)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCollision(int posX, int posY, int posXEnd, int posYEnd, int obstacleX, int obstacleY, int obstacleXEnd, int obstacleYEnd) {
        if ((posY < obstacleY && posYEnd > obstacleY) || (obstacleYEnd > posY && obstacleYEnd < posYEnd)) {
            if ((posX <= obstacleX && posXEnd >= obstacleX) || (obstacleXEnd >= posX && obstacleXEnd <= posXEnd)) {
                // kolizja
                return true;
            }
        }
        return false;
    }


    public boolean canMoveLeft(AbstractPaintable other) {
        if (other == null) {
            return true;
        }

        int posX = positionX - speed; // sprawdzam swoją pozycję po przesunięciu
        int posXEnd = positionX + getWidth() - speed;
        int posY = positionY;
        int posYEnd = positionY + getHeight();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();

        if (checkCollision(posX, posY, posXEnd, posYEnd, obstacleX, obstacleY, obstacleXEnd, obstacleYEnd))
            return false;

        return true;
    }


    public boolean canMoveRight(AbstractPaintable other) {
        if (other == null) {
            return true;
        }

        int posX = positionX + speed; // sprawdzam swoją pozycję po przesunięciu
        int posXEnd = positionX + getWidth() + speed;
        int posY = positionY;
        int posYEnd = positionY + getHeight();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();

        if (checkCollision(posX, posY, posXEnd, posYEnd, obstacleX, obstacleY, obstacleXEnd, obstacleYEnd))
            return false;

        return true;
    }

    public boolean canMoveUp(AbstractPaintable other) {
        if (other == null) {
            return true;
        }

        int posY = positionY - speed;
        int posYEnd = positionY + getHeight() - speed;

        int posX = positionX;
        int posXEnd = positionX + getWidth();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();

        if (checkCollision(posX, posY, posXEnd, posYEnd, obstacleX, obstacleY, obstacleXEnd, obstacleYEnd))
            return false;

        return true;
    }

    public boolean canMoveDown(AbstractPaintable other) {
        if (other == null) {
            return true;
        }

        int posY = positionY + speed;
        int posYEnd = positionY + getHeight() + speed;
        int posX = positionX;
        int posXEnd = positionX + getWidth();

        int obstacleX = other.positionX;
        int obstacleY = other.positionY;
        int obstacleXEnd = other.positionX + other.getWidth();
        int obstacleYEnd = other.positionY + other.getHeight();

        if (checkCollision(posX, posY, posXEnd, posYEnd, obstacleX, obstacleY, obstacleXEnd, obstacleYEnd))
            return false;

        return true;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }
}
