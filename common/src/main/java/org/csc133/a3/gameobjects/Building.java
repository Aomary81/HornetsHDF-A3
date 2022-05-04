package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;


import static com.codename1.ui.CN.*;

public class Building extends GameObject{
    private int value;
    private int damage;
    private int currentValue;
    private int buildingID;

    public Building(int buildingObject, Dimension worldSize){
        setColor(ColorUtil.rgb(255,0,0));
        if(buildingObject == 0) {
            this.dimension = new Dimension(1200, 200);
            this.worldSize = worldSize;
            value = 854;
            currentValue = value;
            this.buildingID=buildingObject;
            translate(0, this.getDimension().getHeight()*2.2);

        }else if(buildingObject == 1){
            this.dimension = new Dimension(200, 500);
            value = 603;
            currentValue = value;
            this.buildingID=buildingObject;
            translate(-getDimension().getWidth()*3,
                    -getDimension().getHeight()/2);

        }else{
            this.dimension = new Dimension(200, 400);
            value = 429;
            currentValue = value;
            this.buildingID=buildingObject;
            translate(getDimension().getWidth()*3,
                    -getDimension().getHeight()/2);
        }
        translate(worldSize.getWidth()/2,worldSize.getHeight()/2);
    }

    public int getCurrentValue(){
        return this.currentValue;
    }

    public int getCurrentValue(int loss){
        return currentValue = currentValue - loss;
    }

    public void adjustValue(int loss){
        this.value = loss;
    }

    public int getBuildingArea() {
        return (dimension.getWidth() * dimension.getHeight());
    }


    @Override
    public void updateLocalTransforms() {

    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                          Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawRect(-getWidth()/2,-getHeight()/2, getWidth(), getHeight());

        g.setFont(Font.createSystemFont
                (FACE_MONOSPACE, STYLE_BOLD, SIZE_MEDIUM));
        g.scale(1,-1);
        g.drawString("V  : $" + this.getCurrentValue(),
                getWidth()/2 + 25, getHeight()/2 - getHeight());
        g.drawString("D  : " + this.damage + "%",
                getWidth()/2 + 25, getHeight()/2 + 25 - getHeight());
    }
}
