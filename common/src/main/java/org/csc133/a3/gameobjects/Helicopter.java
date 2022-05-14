package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.util.MathUtil;
import static com.codename1.ui.CN.*;
import org.csc133.a3.Game;
import org.csc133.a3.gameobjects.parts.Arc;
import org.csc133.a3.gameobjects.parts.Lines;
import org.csc133.a3.gameobjects.parts.Rectangle;
import org.csc133.a3.interfaces.Steerable;

import java.util.ArrayList;


public class Helicopter extends Movable implements Steerable {
    final static int BUBBLE_RADIUS = 125;
    final static int ENGINE_BLOCK_WIDTH = (int)(BUBBLE_RADIUS*1.8);
    final static int ENGINE_BLOCK_HEIGHT = (ENGINE_BLOCK_WIDTH/3);
    final static int BLADE_WIDTH = 25;
    final static int BLADE_LENGTH = BUBBLE_RADIUS * 5;
    final static int RAIL_WIDTH = 25;
    final static int RAIL_LENGTH = (int) (BLADE_LENGTH/1.8);
    final static int LEG_WIDTH = 50;
    final static int LEG_HEIGHT = 10;
    final static int TAIL_WIDTH = 10;
    final static int TAIL_HEIGHT = (int) (RAIL_LENGTH*0.85);
    private static int color1;
    private boolean canMove;
    private int fuel;
    private int water;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static class HeloBubble extends Arc {
        public HeloBubble(){
            super(color1,
                    2*Helicopter.BUBBLE_RADIUS,
                    2*Helicopter.BUBBLE_RADIUS,
                    0,(float) (2*Helicopter.BUBBLE_RADIUS*0.20),
                    1,1,
                    0,
                    135,270);
        }
    }

    private static class HeloEngineBlock extends Rectangle {
        public HeloEngineBlock(){
            super(color1,
                    Helicopter.ENGINE_BLOCK_WIDTH,
                    Helicopter.ENGINE_BLOCK_HEIGHT,
                    0,(float)(-Helicopter.ENGINE_BLOCK_HEIGHT),
                    1,1, 0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.drawRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloBlade extends Rectangle{
        private double rotationSpeed = 0;

        public HeloBlade(){
            super(color1,
                    Helicopter.BLADE_LENGTH, Helicopter.BLADE_WIDTH,
                    0, -Helicopter.ENGINE_BLOCK_HEIGHT,
                    1,1,42);
        }

        public void updateLocalTransforms(){
            if(rotationSpeed != -100){
                rotationSpeed  = rotationSpeed - 5;
            }
            this.rotate(rotationSpeed);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloBladeShaft extends Arc{
        public HeloBladeShaft(){
            super(ColorUtil.BLACK,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    0, -Helicopter.ENGINE_BLOCK_HEIGHT,
                    1, 1,
                    0,
                    0,
                    360);
        }
    }

    private static class HeloRail extends Rectangle{
        public HeloRail(float side){
            super(color1,
                    Helicopter.RAIL_WIDTH, Helicopter.RAIL_LENGTH,
                    side*Helicopter.RAIL_LENGTH/2, 0,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.drawRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloUpperLeg extends Rectangle{
        public HeloUpperLeg(float side){
            super(color1,
                    Helicopter.LEG_WIDTH, Helicopter.LEG_HEIGHT,
                    (float) (side*Helicopter.ENGINE_BLOCK_WIDTH/2
                            + side*Helicopter.LEG_WIDTH/2),
                    Helicopter.BUBBLE_RADIUS - Helicopter.LEG_HEIGHT,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloLowerLeg extends Rectangle{
        public HeloLowerLeg(float side){
            super(color1,
                    Helicopter.LEG_WIDTH, Helicopter.LEG_HEIGHT,
                    side*Helicopter.ENGINE_BLOCK_WIDTH/2
                            + side*Helicopter.LEG_WIDTH/2,
                    -Helicopter.ENGINE_BLOCK_HEIGHT,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloTailTop extends Rectangle{
        public HeloTailTop(){
            super(color1,
                    Helicopter.TAIL_WIDTH, Helicopter.TAIL_HEIGHT,
                    0, -Helicopter.TAIL_HEIGHT
                            + Helicopter.ENGINE_BLOCK_HEIGHT/2,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloTailSide extends Lines {
        public HeloTailSide(float side){
            super(color1,
                    0,0,
                    (int)side*Helicopter.ENGINE_BLOCK_WIDTH/8,
                    -Helicopter.TAIL_HEIGHT,
                    (float)-side*(Helicopter.ENGINE_BLOCK_WIDTH/4),
                    (float)-(Helicopter.ENGINE_BLOCK_HEIGHT*1.5),
                    1,1,
                    0);
        }
    }

    private static class HeloRearRotorBox extends Rectangle{
        public HeloRearRotorBox(){
            super(color1,
                    Helicopter.TAIL_WIDTH*6,
                    Helicopter.TAIL_WIDTH*6,
                    0, -Helicopter.TAIL_HEIGHT*2
                            + Helicopter.ENGINE_BLOCK_HEIGHT*2
                            + Helicopter.TAIL_WIDTH,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloRearRotorConnector extends Rectangle{
        public HeloRearRotorConnector(){
            super(color1,
                    Helicopter.TAIL_WIDTH*4,
                    (int) (Helicopter.TAIL_WIDTH*1.5),
                    Helicopter.TAIL_WIDTH*3, -Helicopter.TAIL_HEIGHT*2
                            + Helicopter.ENGINE_BLOCK_HEIGHT*2
                            + Helicopter.TAIL_WIDTH*3 - 3,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloRearRotor extends Rectangle{
        public HeloRearRotor(){
            super(color1,
                    Helicopter.TAIL_WIDTH*2,
                    Helicopter.TAIL_WIDTH*10,
                    Helicopter.TAIL_WIDTH*6, -Helicopter.TAIL_HEIGHT*2
                            + Helicopter.ENGINE_BLOCK_HEIGHT*2
                            + Helicopter.TAIL_WIDTH*3,
                    1,1,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private class HeloText extends Rectangle{
        public HeloText(){
            super(color1,
                    Helicopter.RAIL_WIDTH, Helicopter.RAIL_LENGTH,
                    Helicopter.RAIL_LENGTH/2, 0,
                    5,-5,0);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.setFont(Font.createSystemFont
                    (FACE_MONOSPACE, STYLE_BOLD, SIZE_MEDIUM));
            g.drawString("F\t: " + getFuel(),
                    getWidth(), getHeight()/2 - 150);
            g.drawString("W\t: " + getCurrentWaterInTank(),
                    getWidth(), getHeight()/2 - 125);
        }
    }

    private static class HeloRearBlade extends Rectangle{
        public HeloRearBlade(){
            super(color1,
                    Helicopter.TAIL_WIDTH*12,
                    Helicopter.TAIL_WIDTH*3,
                    -Helicopter.ENGINE_BLOCK_WIDTH/6, -Helicopter.TAIL_HEIGHT*2
                            + Helicopter.ENGINE_BLOCK_HEIGHT*2
                            + Helicopter.TAIL_WIDTH/2,
                    1,1,45);
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                              Point screenOrigin) {
            super.localDraw(g,parentOrigin,screenOrigin);
            g.fillRect(-getWidth()/2,-getHeight()/2,
                    getWidth(), getHeight());
        }
    }

    private static class HeloFirstRearBrace extends Lines {

        public HeloFirstRearBrace(float side){
            super(color1,
                    0,0,
                    (int)(side*(Helicopter.BUBBLE_RADIUS*0.70)),
                    -(int)(Helicopter.TAIL_HEIGHT*.60),
                    (float)-side*(Helicopter.ENGINE_BLOCK_WIDTH/4),
                    (float)-(Helicopter.ENGINE_BLOCK_HEIGHT)
                            - (Helicopter.ENGINE_BLOCK_HEIGHT/2),
                    1,1,
                    0);
        }
    }

    private static class HeloSecondRearBrace extends Lines {

        public HeloSecondRearBrace(float side){
            super(color1,
                    0,0,
                    (int)(side*(Helicopter.ENGINE_BLOCK_WIDTH*0.35)),
                    (int)(Helicopter.TAIL_HEIGHT*0.40),
                    (float)-(side*Helicopter.ENGINE_BLOCK_WIDTH*0.15),
                    (float)-((Helicopter.TAIL_HEIGHT)
                            + (Helicopter.ENGINE_BLOCK_HEIGHT*1.5)),
                    1,1,
                    0);
        }
    }

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    HeloState heloState;

    private void changeState(HeloState heloState){
        this.heloState=heloState;
    }

    private abstract class HeloState{
        protected Helicopter getHelo() {
            return Helicopter.this;
        }
        public void accelerate(){}
        public void startOrStopEngine(){}
        public boolean hasLandedAt(){
            return false;
        }
        public void updateLocalTransforms(){}
    }

    private class Off extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Starting());
            canMove = true;
        }

        @Override
        public boolean hasLandedAt() {
            return true;
        }
    }

    private class Starting extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Stopping());
        }
    }

    private class Stopping extends HeloState{

        @Override
        public void startOrStopEngine(){
            getHelo().changeState(new Starting());
        }
    }

    private class Ready extends HeloState{

        @Override
        public void startOrStopEngine(){
            if(1>2){
                getHelo().changeState(new Stopping());
            }
        }

        public void accelerate(){
            heloBladeUpdate();
        }
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private ArrayList<GameObject> heloParts;
    private HeloBlade heloBlade;

    public Helicopter(Point lZ, int color){
        color1 = color;
        heloState = new Off();
        heloParts = new ArrayList<>();

        heloParts.add(new HeloBubble());
        heloParts.add(new HeloEngineBlock());
        heloParts.add(new HeloRail(-1)); // Left
        heloParts.add(new HeloRail(1)); // Right
        heloParts.add(new HeloUpperLeg(-1)); // Left
        heloParts.add(new HeloUpperLeg(1)); // Right
        heloParts.add(new HeloLowerLeg(-1)); // Left
        heloParts.add(new HeloLowerLeg(1)); // Right

        heloParts.add(new HeloTailTop());
        heloParts.add(new HeloTailSide(-1));
        heloParts.add(new HeloTailSide(1));
        heloParts.add(new HeloRearRotorConnector());
        heloParts.add(new HeloRearRotorBox());
        heloParts.add(new HeloRearRotor());
        heloParts.add(new HeloFirstRearBrace(1));
        heloParts.add(new HeloFirstRearBrace(-1));
        heloParts.add(new HeloSecondRearBrace(1));
        heloParts.add(new HeloSecondRearBrace(-1));
        heloParts.add(new HeloRearBlade());
        heloBlade = new HeloBlade();
        heloParts.add(heloBlade);
        heloParts.add(new HeloBladeShaft());

        translate(lZ.getX(),
                lZ.getY());
        super.setHeading(0);
        super.setSpeed(0);
        canMove = false;
        fuel = 25000;
        water = 0;
        scale(0.2,0.2);
    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                          Point screenOrigin){
        for(GameObject go : heloParts){
            go.draw(g, parentOrigin, screenOrigin);
        }
    }

    public void heloBladeUpdate() {
        if (canMove != false) {
            heloBlade.updateLocalTransforms();
        }
    }
    public void addHeloText(){
        heloParts.add(new HeloText());
    }

    public void accelerate(){
        heloState.accelerate();
    }

    public void startOrStopEngine(){
        heloState.startOrStopEngine();
    }

    public void move(){
        super.move();
    }

    public void turn(int turnAmount){
        if(canMove) {
            super.turn(turnAmount);
        }
    }

    public void adjustSpeed(int speedChange){
        super.adjustSpeed(speedChange);
    }

    public void grabWaterFromRiver(){
        if(water<1000 && getSpeed() <=2){
            water += 100;
        }
    }


    public int getCurrentWaterInTank(){
        return water;
    }

    public void useWater(){
        if(water>0){
            water-=100;
        }
    }

    public void useFuel(){
        if(fuel>0){
            fuel -= (int) MathUtil.pow(getSpeed(),2) + 5;
        }
        else{
            fuel = 0;
            if(Dialog.show("Game Over", "You Won!\nScore: "
                            + fuel + "\nPlay Again?", "Heck Yea!",
                    "Some Other Time")) {
                new Game();
            }
            else {
                Display.getInstance().exitApplication();
            }
        }
    }

    public void setDimension(Dimension dimension){
        super.setDimension(dimension);
    }

    public void setWater(int amount){
        water = amount;
    }

    public int getColor(){ return color;}

    public int getFuel(){
        return fuel;
    }

    public int getSpeed(){
        return super.getSpeed();
    }

    public int getHeading(){
        return super.getHeading();
    }

    public boolean isEngineOn(){
        return canMove;
    }

    @Override
    public int getWidth(){
        return 250;
    }

    @Override
    public int getHeight(){
        return 250;
    }

    @Override
    public void updateLocalTransforms() {}

    @Override
    public void steer(int steer) {
        if (steer < 0){
            this.setHeading(steer);
        }
    }
}