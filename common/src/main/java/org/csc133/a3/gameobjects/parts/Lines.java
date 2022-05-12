package org.csc133.a3.gameobjects.parts;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.GameObject;

public class Lines  extends GameObject {
    public Lines(int color,
                 int topX, int topY,
                 int bottomX, int bottomY,
                 float tx, float ty,
                 float sx, float sy,
                 float degrees){
        setColor(color);
        setDimension(new Dimension(topX,topY));
        setDimension(new Dimension(bottomX, bottomY));
        translate(tx,ty);
        scale(sx,sy);
        rotate(degrees);
    }

    @Override
    public void updateLocalTransforms() {}

    @Override
    public void localDraw(Graphics g, Point parentOrigin, Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawLine(0,0,getWidth(),getHeight());
    }
}