package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class Helipad extends Fixed{
    private int helipadId;

    public Helipad(Point lZ) {
        setColor(ColorUtil.GRAY);
        this.dimension = new Dimension(400,400);
        translate(lZ.getX(), lZ.getY());
        scale(0.2,0.2);
    }

    // Get Helipads X and Y coordinates
    public int getHelipadX() {
        return (int)location.getX() - dimension.getWidth()/2;
    }

    public int getHelipadY() {
        return (int)location.getY() - dimension.getWidth()/2 -
                dimension.getWidth();
    }

    public int getHelipadSize() {
        return dimension.getWidth();
    }

    @Override
    public void updateLocalTransforms() {
    }
    @Override
    public void localDraw(Graphics g, Point parentOrigin, Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawArc((int)(screenOrigin.getX() - ((getDimension().getWidth() * 0.90)) + (getDimension().getWidth() * 0.90)/2),
                (int)(screenOrigin.getY()-((getDimension().getHeight()*0.90))),
                (int)(getDimension().getWidth()*0.90),
                (int)(getDimension().getHeight()*0.90),
                0,360);
        g.drawRect(screenOrigin.getX()-(getDimension().getWidth()/2),
                screenOrigin.getY()-(getDimension().getHeight()),
                getDimension().getWidth(), getDimension().getHeight(),
                5);
    }
}
