package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.interfaces.Steerable;

import java.util.ArrayList;

import static com.codename1.ui.CN.*;

public class Helicopter extends GameObject implements Steerable {
    private Point2D turning;
    private Point2D forwardPoints;
    private int fuel, currentIndex;
    private static int speed;
    private int water;
    private ArrayList<Point2D> turn, forward;

    public Helicopter(Dimension worldSize) {
        this.worldSize = worldSize;
        this.color = ColorUtil.YELLOW;
        this.dimension = new Dimension(50, 50);
        this.location = new Point2D(worldSize.getWidth()/2,
                worldSize.getHeight());
        turn = new ArrayList<>();
        currentIndex = 0;
        fuel = 25000;
        for (int i = 1; i < 25; i++) {
            double angle = Math.toRadians(360 / 24 * i - 105);
            turning = new Point2D(Math.cos(angle), Math.sin(angle));
            turn.add(turning);
        }

    }

    // Changes the direction the helicopter is pointing
    public void move() {
        location.setX(location.getX() + (turn.get(currentIndex).getX()
                * speed));
        location.setY(location.getY() +
                (turn.get(currentIndex).getY() * speed));

    }

    // Uses the turn ArrayList to change direction
    public void turningR() {
        currentIndex++;
        if (currentIndex > turn.size() - 1) {
            currentIndex = 0;
        }
    }

    // Uses the turn ArrayList to change direction
    public void turningL() {
        if (currentIndex == 0) {
            currentIndex = turn.size() - 1;
        }
        currentIndex--;
    }

    // changes the speed of the helicopter
    public static void accelerate() {
        if (speed >= 0 && speed < 10) {
            speed++;
        } else {
            speed = 10;
        }
    }

    // changes the speed of the helicopter
    public static void decelerate() {
        if (speed > 0) {
            speed--;
        } else {
            speed = 0;
        }
    }
    public static int getSpeed() {
        return speed;
    }
    // Changes the fuel level
    public int fuelConsumption() {
        fuel = fuel - ((speed * speed) + 5);

        if (fuel <= 0) {
            fuel = 0;
        }
        return fuel;
    }

    // Command method to pick up Water
    public void drinkWater() {
        if (speed < 3) {
            water = water + 100;
        }
        if (water >= 1000) {
            water = 1000;
        }
    }
    public int heading(){
        return currentIndex * 15;
    }

    // Checks to see if center of helicopter is over the river
    public boolean overRiver(River r) {
        if ((r.getRiverNorth() < getHelicopterLocationY()) &&
                (r.getRiverSouth() - dimension.getWidth() / 2
                        > getHelicopterLocationY())) {
            return true;
        } else {
            return false;
        }
    }

    // Check is helicopter is over the fire
    public boolean overFire(Fire f) {
        if ((f.getFireX() < (getHelicopterLocationX() + dimension.getWidth()/2) &&
                ((f.getFireX() + f.getFireSize()) > (getHelicopterLocationX() + dimension.getWidth()/2))
                && (f.getFireY() < (getHelicopterLocationY() + dimension.getHeight()/2))
                && (((f.getFireY() + f.getFireSize())
                > (getHelicopterLocationY() + dimension.getHeight()/2))))) {
            return true;
        } else {
            return false;
        }
    }

    // Check to see if Helicopter is over the Helipad
    public boolean isOverHelipad(Helipad pad) {
        return (pad.getHelipadX() < getHelicopterLocationX()) &&
                ((pad.getHelipadX() + pad.getHelipadSize())
                        > getHelicopterLocationX()) &&
                (pad.getHelipadY() < getHelicopterLocationY()) &&
                ((pad.getHelipadY() + pad.getHelipadSize())
                        > getHelicopterLocationY()) &&
                (speed == 0);
    }

    // Current Water Level
    public int getWaterLevel() {
        return water;
    }

    // Drops all water of fire
    public void fightFire() {
        water = 0;
    }

    public int getHelicopterLocationX(){
        return (int)location.getX() - dimension.getWidth() / 2;
    }
    public int getHelicopterLocationY(){
        return (int)location.getY() - dimension.getWidth()/2 -
                (dimension.getWidth()*4);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(color);
        g.fillArc(containerOrigin.getX() + (int)location.getX() -
                        dimension.getWidth() / 2,
                containerOrigin.getY() +
                        (int)location.getY() - dimension.getWidth()/2 -
                        (dimension.getWidth()*4),
                (dimension.getWidth()),
                (dimension.getWidth()), 0,360);
        g.drawLine(containerOrigin.getX() + (int)location.getX(),
                containerOrigin.getY() + (int)location.getY() -
                        (dimension.getWidth()*4),
                (int)(containerOrigin.getX() + (int)location.getX() +
                        (turn.get(currentIndex).getX()
                                * (dimension.getWidth()*2))),
                (int)(containerOrigin.getY() + (int)location.getY() -
                        (dimension.getWidth()*4) +
                        (turn.get(currentIndex).getY()
                                * (dimension.getWidth()*2))));
        g.setFont(Font.createSystemFont(FACE_MONOSPACE, STYLE_BOLD,
                SIZE_MEDIUM));
        g.drawString("f: " + fuel, containerOrigin.getX()
                        + (int)location.getX() -
                        dimension.getWidth() / 2,
                containerOrigin.getY() +
                        (int)location.getY() - dimension.getWidth()/2 -
                        (dimension.getWidth()*4) + dimension.getWidth());
        g.drawString("w: " + water, containerOrigin.getX()
                        + (int)location.getX() -
                        dimension.getWidth() / 2,
                containerOrigin.getY() +
                        (int)location.getY() - dimension.getWidth()/2 -
                        (dimension.getWidth()*4) + dimension.getWidth() + 20);
    }

    @Override
    public void steerLeft() {
        if (currentIndex == 0) {
            currentIndex = turn.size() - 1;
        }
        currentIndex--;
    }


    @Override
    public void steerRight() {
        currentIndex++;
        if (currentIndex > turn.size() - 1) {
            currentIndex = 0;
        }
    }
}
