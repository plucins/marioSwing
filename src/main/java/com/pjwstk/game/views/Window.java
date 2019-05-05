package com.pjwstk.game.views;

import com.pjwstk.game.models.movement.MovementHorizontal;
import com.pjwstk.game.models.movement.MovementVertical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Window extends JFrame {
    // constants
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private static final int FPS = 60;

    // fields
    private MainPanel mainPanel;

    private ExecutorService watekOdrysowujacy = Executors.newSingleThreadExecutor();

    public Window() throws HeadlessException {
        super();

        Dimension wymiar = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainPanel = new MainPanel(wymiar);

        setSize(wymiar);
        setPreferredSize(wymiar);

        setContentPane(mainPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        KeyAdapter nasluchiwaczKlawiszy = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    mainPanel.verticalMovement(MovementVertical.UP);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    mainPanel.verticalMovement(MovementVertical.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mainPanel.horizontalMovement(MovementHorizontal.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    mainPanel.horizontalMovement(MovementHorizontal.RIGHT);
                }

                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    mainPanel.verticalMovement(MovementVertical.NONE);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    mainPanel.verticalMovement(MovementVertical.NONE);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    mainPanel.horizontalMovement(MovementHorizontal.NONE);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    mainPanel.horizontalMovement(MovementHorizontal.NONE);
                }

                super.keyReleased(e);
            }
        };
        addKeyListener(nasluchiwaczKlawiszy);

        watekOdrysowujacy.submit(new Runnable() {
            public void run() {
                long timeStart, timeEnd;
                long ileCzasuMamyNaFPS = (1000 / FPS);


                while (true) {
                    try {
                        timeStart = System.currentTimeMillis();
                        repaint();
                        timeEnd = System.currentTimeMillis();

                        long ileCzasuZajelo = timeEnd - timeStart;
                        if (ileCzasuZajelo < ileCzasuMamyNaFPS) {
                            Thread.sleep(ileCzasuMamyNaFPS - ileCzasuZajelo);
                        }

                        mainPanel.moveObjectsOnScene();
                    } catch (InterruptedException ie) {

                    } catch (Exception e) {
                        System.err.println("Error: " + e);
                    }
                }
            }
        });
    }
}
