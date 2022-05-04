package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.parts.Arc;
import org.csc133.a3.gameobjects.parts.Lines;
import org.csc133.a3.gameobjects.parts.Rectangle;
import org.csc133.a3.interfaces.Steerable;

import java.util.ArrayList;

public class Helicopter extends GameObject implements Steerable {
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

    //````````````````````````````````````````````````````````````````````````
    private static class HeloBubble extends Arc {
        public HeloBubble(){
            super(ColorUtil.YELLOW,
                    2*Helicopter.BUBBLE_RADIUS,
                    2*Helicopter.BUBBLE_RADIUS,
                    0,(float) (2*Helicopter.BUBBLE_RADIUS*0.20),
                    1,1,
                    0,
                    135,270);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloEngineBlock extends Rectangle {
        public HeloEngineBlock(){
            super(ColorUtil.YELLOW,
                    Helicopter.ENGINE_BLOCK_WIDTH,
                    Helicopter.ENGINE_BLOCK_HEIGHT,
                    (float)(-Helicopter.ENGINE_BLOCK_WIDTH/2),
                    (float)(-Helicopter.ENGINE_BLOCK_HEIGHT*1.5),
                    1,1, 0);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloBlade extends Rectangle{
        public HeloBlade(){
            super(ColorUtil.GRAY,
                    BLADE_LENGTH, BLADE_WIDTH,
                    -BLADE_LENGTH/2, -ENGINE_BLOCK_HEIGHT-(BLADE_WIDTH/2),
                    1,1,0);
        }

        public void updateLocalTransforms(double rotationSpeed){
            this.rotate(rotationSpeed);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloBladeShaft extends Arc{
        public HeloBladeShaft(){
            super(ColorUtil.YELLOW,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    2 * Helicopter.BLADE_WIDTH / 3,
                    0, -Helicopter.ENGINE_BLOCK_HEIGHT,
                    1, 1,
                    0,
                    0,
                    360);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloRail extends Rectangle{
        public HeloRail(float side){
            super(ColorUtil.YELLOW,
                    Helicopter.RAIL_WIDTH, Helicopter.RAIL_LENGTH,
                    side*Helicopter.RAIL_LENGTH/2, -Helicopter.RAIL_LENGTH/2,
                    1,1,0);
        }
    }
    //````````````````````````````````````````````````````````````````````````
    private static class HeloUpperLeg extends Rectangle{
        public HeloUpperLeg(float side){
            super(ColorUtil.GRAY,
                    Helicopter.LEG_WIDTH, Helicopter.LEG_HEIGHT,
                    (float) (side*Helicopter.ENGINE_BLOCK_WIDTH/2
                            + side*Helicopter.LEG_WIDTH/2),
                    Helicopter.BUBBLE_RADIUS - Helicopter.LEG_HEIGHT,
                    1,1,0);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloLowerLeg extends Rectangle{
        public HeloLowerLeg(float side){
            super(ColorUtil.GRAY,
                    Helicopter.LEG_WIDTH, Helicopter.LEG_HEIGHT,
                    side*Helicopter.ENGINE_BLOCK_WIDTH/2
                            + side*Helicopter.LEG_WIDTH/2,
                    -Helicopter.ENGINE_BLOCK_HEIGHT,
                    1,1,0);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloTailTop extends Rectangle{
        public HeloTailTop(){
            super(ColorUtil.GRAY,
                    Helicopter.TAIL_WIDTH, Helicopter.TAIL_HEIGHT,
                    (float)(-Helicopter.TAIL_WIDTH/2),
                    (float)((-Helicopter.TAIL_HEIGHT
                            - (Helicopter.ENGINE_BLOCK_WIDTH/2))),
                    1,1,
                    0);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloTailSide extends Lines {
        public HeloTailSide(float side){
            super(ColorUtil.YELLOW,
                    0,0,
                    (int)side*Helicopter.ENGINE_BLOCK_WIDTH/8,
                    -Helicopter.TAIL_HEIGHT,
                    (float)-side*(Helicopter.ENGINE_BLOCK_WIDTH/4),
                    (float)-(Helicopter.ENGINE_BLOCK_HEIGHT*1.5),
                    1,1,
                    0);
        }
    }

    //````````````````````````````````````````````````````````````````````````

    private static class HeloRearRotorBox extends Rectangle{
        public HeloRearRotorBox(){
            super(ColorUtil.GRAY,
                    Helicopter.ENGINE_BLOCK_WIDTH/4,
                    Helicopter.ENGINE_BLOCK_WIDTH/4,
                    (float)-((Helicopter.ENGINE_BLOCK_WIDTH/4)/2),
                    (float)((-Helicopter.TAIL_HEIGHT
                            - (Helicopter.ENGINE_BLOCK_WIDTH/2)-(Helicopter.ENGINE_BLOCK_WIDTH/4))),
                    1,1,0);
        }
    }
    //````````````````````````````````````````````````````````````````````````

    private static class HeloRearRotorConnector extends Rectangle{
        public HeloRearRotorConnector(){
            super(ColorUtil.YELLOW,
                    Helicopter.TAIL_WIDTH*2,
                    (int) (Helicopter.TAIL_WIDTH*1.5),
                    Helicopter.ENGINE_BLOCK_WIDTH/8,
                    (float)((-Helicopter.TAIL_HEIGHT
                            - (Helicopter.ENGINE_BLOCK_WIDTH/2)
                            -(Helicopter.ENGINE_BLOCK_WIDTH/8)
                            - Helicopter.TAIL_WIDTH)),
                    1,1,0);
        }
    }


    //````````````````````````````````````````````````````````````````````````

    private static class HeloRearRotor extends Rectangle{
        public HeloRearRotor(){
            super(ColorUtil.GRAY,
                    Helicopter.TAIL_WIDTH*2,
                    Helicopter.TAIL_WIDTH*10,
                    Helicopter.ENGINE_BLOCK_WIDTH/8
                            + Helicopter.TAIL_WIDTH*2,
                    (float)((-Helicopter.TAIL_HEIGHT
                            - (Helicopter.ENGINE_BLOCK_WIDTH/2)
                            -(Helicopter.ENGINE_BLOCK_WIDTH/8)
                            - Helicopter.TAIL_WIDTH)
                            - (Helicopter.TAIL_WIDTH*5)
                    + (Helicopter.TAIL_WIDTH*0.75)),
                    1,1,0);
        }
    }

    //````````````````````````````````````````````````````````````````````````
    private static class HeloRearBlade extends Rectangle{
        public HeloRearBlade(){
            super(ColorUtil.YELLOW,
                    Helicopter.ENGINE_BLOCK_WIDTH/2,
                    Helicopter.ENGINE_BLOCK_WIDTH/4,
                    (float)-((Helicopter.ENGINE_BLOCK_WIDTH*5/8)),
                    (float)((-Helicopter.TAIL_HEIGHT
                            - (Helicopter.ENGINE_BLOCK_WIDTH/2)-(Helicopter.ENGINE_BLOCK_WIDTH/4))),
                    1,1,0);
        }
    }

    private class HeloText extends Rectangle{
        public HeloText(){
            super(ColorUtil.YELLOW,
                    Helicopter.RAIL_WIDTH, Helicopter.RAIL_LENGTH,
                    Helicopter.RAIL_LENGTH/2, 0,
                    5,-5,0);
        }
    }
    private static class HeloFirstRearBrace extends Lines {

        public HeloFirstRearBrace(float side){
            super(ColorUtil.YELLOW,
                    0,0,
                    (int)(side*(Helicopter.BUBBLE_RADIUS*0.70)),
                    -(int)(Helicopter.TAIL_HEIGHT*.60),
                    (float)-side*(Helicopter.ENGINE_BLOCK_WIDTH/4),
                    (float)-(Helicopter.ENGINE_BLOCK_HEIGHT) - (Helicopter.ENGINE_BLOCK_HEIGHT/2),
                    1,1,
                    0);
        }
    }

    private static class HeloSecondRearBrace extends Lines {

        public HeloSecondRearBrace(float side){
            super(ColorUtil.YELLOW,
                    0,0,
                    (int)(side*(Helicopter.ENGINE_BLOCK_WIDTH*0.35)),
                    (int)(Helicopter.TAIL_HEIGHT*0.40),
                    (float)-(side*Helicopter.ENGINE_BLOCK_WIDTH*0.15),
                    (float)-((Helicopter.TAIL_HEIGHT) + (Helicopter.ENGINE_BLOCK_HEIGHT*1.5)),
                    1,1,
                    0);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private ArrayList<GameObject> heloParts;

    private HeloBlade heloBlade;

    public Helicopter(Point lZ, int color){
        setColor(color);
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
        scale(0.2,0.2);

    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                          Point screenOrigin){
        for(GameObject go : heloParts){
            go.draw(g, parentOrigin, screenOrigin);
        }
    }

    public void heloBladeUpdate(double currentSpeed){
        heloBlade.updateLocalTransforms(currentSpeed);
    }

    public void addHeloText(){
        heloParts.add(new HeloText());
    }
    @Override
    public void steerLeft() {

    }

    @Override
    public void steerRight() {

    }
    public void accelerate(){
//        heloState.accelerate();
    }
    public void startOrStopEngine(){
//        heloState.startOrStopEngine();
    }
    public void move(){
        //super.move();
    }
    public void adjustSpeed(int speedChange){
        //super.adjustSpeed(speedChange);
    }
    public void setDimension(Dimension dimension){
        super.setDimension(dimension);
    }
    public int getColor(){
        return ColorUtil.YELLOW;
    }
    @Override
    public int getWidth(){
        //int width = 250;
        return 250;
    }
    @Override
    public int getHeight(){
        //int height = 250;
        return 250;
    }

    @Override
    public void updateLocalTransforms() {
        heloBlade.updateLocalTransforms();

    }

    @Override
    public void steer(int steer) {
        if (steer < 0){
            //this.setHeading(steer);
        }
    }
}

