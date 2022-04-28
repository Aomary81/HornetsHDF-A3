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
    private final Button RESTART = new Button("Restart");
    private final Button EXIT = new Button("Exit");
    private Helipad helipad;
    private River river;
    private Helicopter helicopter;
    private int water, fuel, ticks, speed;
    private Random r;
    private ArrayList<GameObject> gameObjects;
    private Building building1, building2, building3;
    private Dimension worldSize;
    private BuildingCollection classroom;
    private FireCollection FireCollection1;
    private FireCollection FireCollection2;
    private FireCollection FireCollection3;
    private FireCollection deadFires;
    private Fire fire;
    private double damage1, damage2, damage3, value;

    public GameWorld() {
        worldSize = new Dimension();
        r = new Random();
        fuel = 25000;
        water = 0;
        damage1 = 0;
        damage2 = 0;
        damage3 = 0;
        init();
    }

    public void init() {
        helipad = new Helipad(worldSize);
        river = new River(worldSize);
        FireCollection1 = new FireCollection();
        FireCollection2 = new FireCollection();
        FireCollection3 = new FireCollection();
        classroom = new BuildingCollection();
        fire = new Fire(worldSize);
        building1 = new Building(worldSize);
        building2 = new Building(worldSize);
        building2.setBuildingLocationX(worldSize.getWidth()/8);
        building2.setBuildingLocationY(worldSize.getHeight()/2);
        building2.setBuildingDimension(worldSize.getWidth()/10,
                worldSize.getHeight()/10 * 3);
        building3 = new Building(worldSize);
        building3.setBuildingLocationX(worldSize.getWidth()/8 * 6);
        building3.setBuildingLocationY(worldSize.getHeight()/2);
        building3.setBuildingDimension(worldSize.getWidth()/10,
                worldSize.getHeight()/10 * 3);
        classroom.add(building1);
        classroom.add(building2);
        classroom.add(building3);
        helicopter = new Helicopter(worldSize);
        FireCollection1 = new FireCollection();
        while(FireCollection1.size() < 2){
            FireCollection1.add(new Fire(worldSize));
        }
        FireCollection2 = new FireCollection();
        while(FireCollection2.size() < 2){
            FireCollection2.add(new Fire(worldSize));
        }
        FireCollection3 = new FireCollection();
        while(FireCollection3.size() < 2){
            FireCollection3.add(new Fire(worldSize));
        }
        deadFires = new FireCollection();
        gameObjects = new ArrayList<>();

        ticks = 0;
        gameObjects.add(river);
        gameObjects.add(helipad);
        gameObjects.add(classroom);
        gameObjects.add(FireCollection1);
        gameObjects.add(FireCollection2);
        gameObjects.add(FireCollection3);
        gameObjects.add(helicopter);
    }
    void tick() {

        ticks++;
        if(ticks < 2) {
            for (Fire spot : FireCollection1) {
                building1.setFireInBuilding(spot);
            }
            for (Fire spot : FireCollection2) {
                building2.setFireInBuilding(spot);
            }
            for (Fire spot : FireCollection3) {
                building3.setFireInBuilding(spot);
            }
        }
        if (ticks % 5 == 0) {
            fuel = helicopter.fuelConsumption();

            for(Fire spot: FireCollection1){
                spot.grow();
            }
            for(Fire spot: FireCollection2){
                spot.grow();
            }
            for(Fire spot: FireCollection3){
                spot.grow();
            }
        }
        damage1 = building1.getDamage(FireCollection1);
        building1.setDamage(damage1);
        damage2 = building1.getDamage(FireCollection2);
        building2.setDamage(damage2);
        damage3 = building1.getDamage(FireCollection3);
        building3.setDamage(damage3);
        helicopter.move();
        water = helicopter.getWaterLevel();
        if ((fuel <= 0) ||
                (getFires()==0 && helicopter.isOverHelipad(helipad))) {
            gameOver();
        }
    }
    public void turnLeft() {
        helicopter.turningL();
    }

    // method to call helicopter command turn right
    public void turnRight() {
        helicopter.turningR();
    }

    // Method for filling the water tank on helicopter
    public void drink() {
        if (helicopter.overRiver(river)) {
            helicopter.drinkWater();
        }
    }
    public void fight() {
        for(Fire spot: FireCollection1) {
            if (helicopter.overFire(spot)) {
                if ((water/4) > spot.getFireSize()) {
                    FireCollection1.remove(spot);
                } else
                    spot.shrink(water);
            }
        }
        for(Fire spot: FireCollection2) {
            if (helicopter.overFire(spot)) {
                if ((water) > spot.getFireSize()) {
                    FireCollection2.remove(spot);
                } else
                    spot.shrink(water);
            }
        }
        for(Fire spot: FireCollection3) {
            if (helicopter.overFire(spot)) {
                if ((water) > spot.getFireSize()) {
                    FireCollection3.remove(spot);
                } else
                    spot.shrink(water);
            }
        }

        helicopter.fightFire();

    }
    void gameOver() {
        RESTART.addActionListener(actionEvent -> {
            Game game = new Game();
            game.show();
        });
        EXIT.addActionListener(actionEvent -> quit());
        Dialog dlg = new Dialog("GAME OVER!");
        dlg.setLayout(new BorderLayout());
        if (fuel <= 0) {
            dlg.add(BorderLayout.CENTER, "YOU LOSE!!! TRY AGAIN!!!");
        } else {
            int lose = (building1.getValue()+ building2.getValue()
                    + building3.getValue() - getLoss());
            int highscore =
                    fuel + lose;
            dlg.add(BorderLayout.CENTER,
                    "Your High Score is: " + highscore );
        }
        dlg.add(BorderLayout.EAST, RESTART);
        dlg.add(BorderLayout.WEST, EXIT);
        dlg.show(worldSize.getHeight() / 24 * 10, worldSize.getHeight() / 24 * 10,
                worldSize.getWidth() / 24 * 9,
                worldSize.getHeight() / 24 * 9);
    }
    public void quit() {
        Display.getInstance().exitApplication();
    }

    public ArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public String getHeading() {
        return String.valueOf(helicopter.heading());
    }

    public String getSpeed() {
        return String.valueOf(Helicopter.getSpeed());
    }
    public String getFuel(){
        return String.valueOf(fuel);
    }
    public int getFires(){
        return (FireCollection1.size() + FireCollection2.size()
                + FireCollection3.size());
    }
    public int getFireSize() {
        double flame = 0;
        for(Fire spot: FireCollection1){
            flame = flame + spot.getFireSize();
        }
        for(Fire spot: FireCollection2){
            flame = flame + spot.getFireSize();
        }
        for(Fire spot: FireCollection3){
            flame = flame + spot.getFireSize();
        }
        return (int) flame;
    }
    public String getDamage(){
        return String.valueOf(damage1+damage2+damage3);
    }
    public int getLoss(){
        double totDamage = damage1* building1.getValue()
                + damage2 * building2.getValue()
                + damage3 * building3.getValue();
        return (int)totDamage;
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }
    public void accelerate() {
        helicopter.accelerate();
    }

    public void brake() {
        helicopter.decelerate();
    }
}
