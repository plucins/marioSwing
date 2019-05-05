package com.pjwstk.game.views;

import com.pjwstk.game.events.Dispatcher;
import com.pjwstk.game.events.GateReachedEvent;
import com.pjwstk.game.events.RewardCollisionEvent;
import com.pjwstk.game.events.VerticalCollisionEvent;
import com.pjwstk.game.interfaces.IFinishGateReachedListener;
import com.pjwstk.game.map.MapReader;
import com.pjwstk.game.models.AbstractPaintable;
import com.pjwstk.game.models.GameHero;
import com.pjwstk.game.models.Gate;
import com.pjwstk.game.models.Score;
import com.pjwstk.game.models.movement.MovementHorizontal;
import com.pjwstk.game.models.movement.MovementVertical;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements IFinishGateReachedListener {
    private Dimension wymiar;
    private long lastMove;
    private static final double PIXELS_PER_SECOND = 200;
    private int drawOffset = 0;
    private int level = 1;

    private GameHero hero = new GameHero(400, 300);
    private Score score = new Score();
    private MovementVertical vertical;
    private MovementHorizontal horizontal;

    private List<AbstractPaintable> przeszkody;
    private List<AbstractPaintable> nagrody;

    private Gate finishGate;

    public MainPanel(Dimension wymiar) {
        super();

        setSize(wymiar);
        setPreferredSize(wymiar);
        setMinimumSize(wymiar);
        setMaximumSize(wymiar);

        this.wymiar = wymiar;

        loadLevel();

        lastMove = System.currentTimeMillis();
        Dispatcher.instance.registerObject(this);
    }

    private void loadLevel() {
        MapReader reader = new MapReader("mapy/level" + level + ".map", wymiar.height);
        try {
            reader.loadMap();
            przeszkody = reader.getElementyPrzeszkodyMapy();
            nagrody = reader.getElementyNagrodyMapy();
            finishGate = reader.getGate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D pen = (Graphics2D) g;
        pen.setColor(Color.CYAN);
        pen.fillRect(0, 0, wymiar.width, wymiar.height);

        finishGate.paint(pen, drawOffset);

        hero.paint(pen, drawOffset);

        for (AbstractPaintable paintable : przeszkody) {
            paintable.paint(pen, drawOffset);
        }

        for (AbstractPaintable paintable : nagrody) {
            paintable.paint(pen, drawOffset);
        }

        score.paint(pen, drawOffset);
    }


    public void moveObjectsOnScene() {
        long difference = System.currentTimeMillis() - lastMove;
        lastMove = System.currentTimeMillis();

        double moveStep = (PIXELS_PER_SECOND * difference) / 1000.0;

        boolean haveSomethingUnder = false;
        hero.animateHero();

        for (AbstractPaintable paintable : przeszkody) {
            if (hero.checkCollision(paintable)) {
                Dispatcher.instance.dispatch(new VerticalCollisionEvent(paintable));
            }
            if (!hero.canMoveDown(paintable)) {
                haveSomethingUnder = true;
            }
        }
        if (!haveSomethingUnder) {
            hero.fall();
            if(hero.getPositionY() > wymiar.height){
                score.setLives(score.getLives() - 1);
                hero.setPositionY(wymiar.height - 60);
                hero.setPositionX(hero.getPositionX() - 100);

                if(score.getLives() <= 0) {
                    hero = new GameHero(300, 300);
                    loadLevel();
                    drawOffset = 0;
                    score.setLives(3);
                    score.setCoinsCount(0);
                }
            }
        }

        hero.moveVertical(moveStep);

        List<AbstractPaintable> kopia = new ArrayList<>(nagrody);

        for (AbstractPaintable paintable : kopia) {
            if (hero.checkCollision(paintable)) {
                Dispatcher.instance.dispatch(new RewardCollisionEvent(paintable));
                nagrody.remove(paintable);
            }
        }


        boolean canMoveHorizontal = true;
        if (horizontal == MovementHorizontal.LEFT) {
            for (AbstractPaintable przeszkoda : przeszkody) {
                if (!hero.canMoveLeft(przeszkoda)) {
                    canMoveHorizontal = false;
                    break;
                }
            }
            if (canMoveHorizontal) {
                hero.moveLeft(moveStep);
            }
        } else if (horizontal == MovementHorizontal.RIGHT) {
            for (AbstractPaintable przeszkoda : przeszkody) {
                if (!hero.canMoveRight(przeszkoda)) {
                    canMoveHorizontal = false;
                    break;
                }
            }
            if (canMoveHorizontal) {
                hero.moveRight(moveStep);
            }
        }

        if (hero.checkCollision(finishGate)) {
            Dispatcher.instance.dispatch(new GateReachedEvent());
        }

        int pozycjaNaEkranie = hero.getPositionX() - drawOffset;
        if (pozycjaNaEkranie >= (wymiar.width / 2)) {
            int roznica = pozycjaNaEkranie - (wymiar.width / 2);
            drawOffset += roznica;
        }
    }

    public void verticalMovement(MovementVertical direction) {
        if (direction == MovementVertical.UP) {
            hero.jump();
        }
    }

    public void horizontalMovement(MovementHorizontal direction) {
        if(direction == MovementHorizontal.RIGHT ) {
            hero.setMovingLeft(false);
            hero.setMoving(true);

        } else if ( direction == MovementHorizontal.LEFT){
            hero.setMovingLeft(true);
            hero.setMoving(true);
        }
        else {
            hero.setMoving(false);
        }

        horizontal = direction;
    }

    @Override
    public void playerReachedGate() {
        level++;
        hero = new GameHero(300, 300);
        loadLevel();
        drawOffset = 0;
    }
}
