package com.pjwstk.game.map;

import com.pjwstk.game.models.Gate;
import com.pjwstk.game.models.ObstacleGround;
import com.pjwstk.game.models.AbstractPaintable;
import com.pjwstk.game.models.Coin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapReader {
    private Gate gate;
    private String mapName;
    private BufferedReader reader;
    private int mapSizeHeight;

    private List<AbstractPaintable> elementyPrzeszkodyMapy = new ArrayList<>();
    private List<AbstractPaintable> elementyNagrodyMapy = new ArrayList<>();

    public MapReader(String mapName, int mapSizeHeight) {
        this.mapName = mapName;
        this.mapSizeHeight = mapSizeHeight;
    }

    public Gate getGate() {
        return gate;
    }

    private void openFile() {
        try {
            reader = new BufferedReader(new FileReader(mapName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<AbstractPaintable> getElementyPrzeszkodyMapy() {
        return elementyPrzeszkodyMapy;
    }

    public List<AbstractPaintable> getElementyNagrodyMapy() {
        return elementyNagrodyMapy;
    }

    public void loadMap() throws IOException {
        openFile();

        String linia = null;
        int licznikLinii = 0;
        while ((linia = reader.readLine()) != null) {
                for (int i = 0; i < linia.length(); i++) {

                    if (linia.charAt(i) == '1') {
                        elementyPrzeszkodyMapy.add(new ObstacleGround(mapSizeHeight - 50, i, licznikLinii));
                    } else if (linia.charAt(i) == '2') {
                        elementyNagrodyMapy.add(new Coin(mapSizeHeight - 50, i, licznikLinii));
                    } else if (linia.charAt(i) == '3') {
                        gate = new Gate(mapSizeHeight - 50, i, licznikLinii);
                    }
                }

            licznikLinii++;
        }

    }

}
