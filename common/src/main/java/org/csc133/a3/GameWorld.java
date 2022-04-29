package org.csc133.a3;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import org.csc133.a3.gameobjects.*;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private int ticks;
    private Helicopter helicopter;
    private ArrayList<GameObject> gameObjects;
    private Dimension worldSize;

    public GameWorld() {
    }

    public void init() {
        gameObjects = new ArrayList<>();

        ticks = 0;
        gameObjects.add(helicopter);
    }
    void tick() {
        ticks++;
//        helicopter.move();
    }
    public void turnLeft() {
        //helicopter.turningL();
    }

    // method to call helicopter command turn right
    public void turnRight() {
        //helicopter.turningR();
    }

    // Method for filling the water tank on helicopter
    public void drink() {
    }
    public void fight() {
        //helicopter.fightFire();

    }
    void gameOver() {
    }
    public void quit() {
        Display.getInstance().exitApplication();
    }

    public ArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public String getHeading() {
        return String.valueOf(0);
    }

    public String getSpeed() {
        return String.valueOf(0);
    }
    public String getFuel(){
        return String.valueOf(0);
    }
    public int getFires(){
        return 0;
    }
    public int getFireSize() {

        return 0;
    }
    public String getDamage(){
        return String.valueOf(0);
    }
    public int getLoss(){
        return 0;
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }
    public void accelerate() {
        helicopter.accelerate();
    }

    public void brake() {
       // helicopter.decelerate();
    }
}
