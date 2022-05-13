package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.gameobjects.*;
import org.csc133.a3.gameobjects.FlightPath.*;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private Dimension worldSize;
    private int totalFireDamage;
    private int maxFireDamage;
    private int buildingArea = 0;
    private int damagePercent = 0;
    private int ticks;
    private int fireTotal = 0;
    private boolean zoomed = false;
    private final int NUMBER_OF_BUILDINGS = 3;
    private static PlayerHelicopter playerHelicopter;
    private Helipad helipad;
    private River river;
    private BezierCurve bezierCurve;
    private BezierCurve bezierCurveRightSide;
    private BezierCurve bezierCurveLeftSide;
    private NonPlayerHelicopter nonPlayerHelicopter;
    private BuildingCollection buildings;
    private FireCollection fire;
    private Point2D tempFireLocation;
    private int path = 0;
    private ArrayList<GameObject> gameObjects;
    private int rand;
    private double fireTotalSize;

    public GameWorld(){
    }

    public void init() {
        ticks = 0;
        helipad = new Helipad(new Point(worldSize.getWidth()/2,
                worldSize.getHeight()/8));
        river = new River(worldSize);
        buildings = new BuildingCollection();
        fire = new FireCollection();
        rand = new Random().nextInt(10)+50;
        bezierCurve = new BezierCurve(worldSize, 0);
        bezierCurveRightSide = new BezierCurve(
                worldSize, 1);
        bezierCurveLeftSide = new BezierCurve(
                worldSize, 2);
        nonPlayerHelicopter = new NonPlayerHelicopter(new Point(
                worldSize.getWidth()/2, worldSize.getHeight()/8),
                bezierCurve);

        for (int x = 0; x < NUMBER_OF_BUILDINGS; x++) {
            buildings.add(new Building(x, worldSize));
        }

        for (int i = 0; i < 2; i++) {
            int buildingID = 0;
            for (Building building : buildings) {
                Fire temp = new Fire(worldSize, buildingID);
                building.setFireInBuilding(temp);
                if(buildingID == 2){
                    temp.getLocation();
                }
                temp.getLocation();
                getTotalFireSize();
                fire.add(temp);
                fireTotal++;
                buildingID++;
            }
        }

        while (this.fireTotalSize < 950) {
            int buildingID = 0;
            for (Building building : buildings) {
                if (ticks % rand == 0) {
                    Fire temp = new Fire(worldSize, buildingID);
                    building.setFireInBuilding(temp);
                    if(buildingID == 2){
                        tempFireLocation = temp.getLocation();
                    }
                    fire.add(temp);
                    fireTotal++;
                    buildingID++;
                    getTotalFireSize();
                }

                if (this.fireTotalSize > 950) {
                    break;
                }
            }
        }

        for (Building building : buildings){
            buildingArea += building.getBuildingArea();
        }

        playerHelicopter =
                new PlayerHelicopter(new Point(
                        worldSize.getWidth()/4, worldSize.getHeight()/16),
                        ColorUtil.YELLOW);
        nonPlayerHelicopter =
                new NonPlayerHelicopter(new Point(
                        worldSize.getWidth()/2, worldSize.getHeight()/8),
                        bezierCurve);

        nonPlayerHelicopter.setPath(bezierCurve);

        gameObjects = new ArrayList<>();
        gameObjects.add(helipad);
        gameObjects.add(river);
        gameObjects.add(buildings);
        gameObjects.add(fire);
        gameObjects.add(bezierCurve);
        gameObjects.add(bezierCurveRightSide);
        gameObjects.add(bezierCurveLeftSide);
        gameObjects.add(nonPlayerHelicopter);
        gameObjects.add(playerHelicopter);
    }

    public void quit(){
        Display.getInstance().exitApplication();
    }

    void tick(){
        ticks++;
        getTotalFireSize();
        getTotalDMG();

        for(Fire spot: fire){
            spot.start();
            if(ticks%rand == 0 && spot.begin() == true ) {
                spot.grow();
                getTotalDMG();

                for (Building building : buildings){
                    building.adjustValue(this.damagePercent*1000/3);
                    building.setDMG(this.damagePercent/3);
                    building.getCurrentValue(this.damagePercent/3);
                }
            }
            if(spot.getSize() <= 8){
                fireTotal--;
                fire.remove(spot);
            }
        }

        for (Building building : buildings){
            building.adjustValue(this.damagePercent*1000/3);
            building.setDMG(this.damagePercent/3);
        }

        if(river.isCollidingWith(nonPlayerHelicopter)){
            nonPlayerHelicopter.grabWaterFromRiver();
        }

        for(Fire spot: fire){
            if(spot.isCollidingWith(nonPlayerHelicopter)
                    && nonPlayerHelicopter.getCurrentWaterInTank() > 1){
                spot.biggerShrink();

                if(nonPlayerHelicopter.getCurrentWaterInTank()>=500){
                    nonPlayerHelicopter.setWater(
                            nonPlayerHelicopter.getCurrentWaterInTank()-500);
                }
            }
        }

        if(nonPlayerHelicopter.getLocation().getX() ==
                bezierCurve.getEndControlPoint().getX()
                && nonPlayerHelicopter.getLocation().getY() ==
                bezierCurve.getEndControlPoint().getY()
                && bezierCurve.getCurveID() == 0 && path == 0){
            bezierCurve.oldPath();
            bezierCurveRightSide.newActivePath();
            nonPlayerHelicopter.setPath(bezierCurveRightSide);
            path=1;
        }
        else if(nonPlayerHelicopter.getLocation().getX() ==
                bezierCurveRightSide.getEndControlPoint().getX()
                && nonPlayerHelicopter.getLocation().getY() ==
                bezierCurveRightSide.getEndControlPoint().getY()
                && bezierCurveRightSide.getCurveID() == 1 && path == 1){
            bezierCurveRightSide.oldPath();
            bezierCurveLeftSide.newActivePath();
            nonPlayerHelicopter.setPath(bezierCurveLeftSide);
            path = 2;
        }
        else if(nonPlayerHelicopter.getLocation().getX() ==
                bezierCurveLeftSide.getEndControlPoint().getX()
                && nonPlayerHelicopter.getLocation().getY() ==
                bezierCurveLeftSide.getEndControlPoint().getY()
                && bezierCurveLeftSide.getCurveID() == 2 && path == 2){
            bezierCurveLeftSide.oldPath();
            bezierCurveRightSide.newActivePath();
            nonPlayerHelicopter.setPath(bezierCurveRightSide);
            path = 1;
        }

        if (playerHelicopter.isEngineOn()) {
            playerHelicopter.move();
            playerHelicopter.useFuel();
        }

        if(nonPlayerHelicopter.isEngineOn()){
            nonPlayerHelicopter.setSpeed();
            nonPlayerHelicopter.move();
            nonPlayerHelicopter.useFuel();
        }
        gameOver();
    }

    public ArrayList<GameObject> getGameObjectCollection() {
        return gameObjects;
    }

    public void drink(){
        if(river.isCollidingWith(playerHelicopter)){
            playerHelicopter.grabWaterFromRiver();
        }
    }

    public void fight(){
        for(Fire spot: fire){
            if(spot.isCollidingWith(playerHelicopter)
                    && playerHelicopter.getCurrentWaterInTank() > 1){
                spot.shrink(playerHelicopter.getCurrentWaterInTank());
            }
        }
        playerHelicopter.useWater();
    }

    void gameOver(){
        if (helipad.isCollidingWith(playerHelicopter) && fireTotal == 0
                && playerHelicopter.getSpeed() == 0){
            if(Dialog.show("Game Over", "You Won!\nScore: "
                            + playerHelicopter.getFuel() + "\nPlay Again?",
                    "Heck Yea!", "Some Other Time")) {
                new Game();
            }
            else {
                quit();
            }
        }
    }

    void turn(int turnAmount){
        playerHelicopter.turn(turnAmount);
    }

    public void left() {
        playerHelicopter.turn(-15);
    }

    public void right(){
        playerHelicopter.turn(15);
    }

    public void adjustSpeed(int changeSpeed){
        if (playerHelicopter.isEngineOn()) {
            playerHelicopter.adjustSpeed(changeSpeed);
        }
    }

    public String getHeading() {
        return String.valueOf(playerHelicopter.getHeading());
    }

    private void getTotalFireSize() {
        this.fireTotalSize = 0;

        for (Fire spot : fire) {
            this.fireTotalSize += spot.currentSize();
        }
    }

    private void getTotalDMG() {
        this.totalFireDamage = 0;
        for (Fire spot : fire) {
            this.totalFireDamage += spot.getGreatestSize();
        }

        if(totalFireDamage>maxFireDamage){
            maxFireDamage=totalFireDamage;
        }
        this.damagePercent = (this.maxFireDamage*42000 / this.buildingArea);
    }

    public String getSpeed() {
        return String.valueOf(playerHelicopter.getSpeed());
    }

    public String getFuel() {
        return String.valueOf(playerHelicopter.getFuel());
    }

    public String getFireCount() {
        return String.valueOf(fire.size());
    }

    public String getDamage() {
        return (damagePercent + "%");
    }

    public String getLoss() {
        return ("$" + damagePercent * 1000);
    }

    public String getFireSize() {
        return String.valueOf((int)this.fireTotalSize);
    }

    public void setDimension(Dimension worldSize) {
        this.worldSize = worldSize;
    }

    public void setBezierCurve(Point2D location){
        bezierCurveRightSide.setEndControlPoint(location);
        bezierCurveLeftSide.setStartControlPoint(location);
    }

    public void quitGame() {
        quit();
    }

    public void selectFire(Point2D p){
        for(Fire spot : fire){
            if(spot.contains(p) && !spot.isSelected()){
                spot.select(true);
                setBezierCurve(fire.getLocation());
            }
        }
    }

    public void startEngine(){
        playerHelicopter.startOrStopEngine();
        nonPlayerHelicopter.startOrStopEngine();
    }

    public void zoom() {
        if(zoomed == false){
            zoomed = true;
        }
        else{
            zoomed = false;
        }
    }

    public boolean zoomed(){
        return zoomed;
    }
}