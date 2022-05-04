package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public class River extends Fixed {

    public River(Dimension worldSize) {
        setColor(ColorUtil.BLUE);
        setDimension(new Dimension(worldSize.getWidth(),
                worldSize.getHeight()/6));
        translate(0, getDimension().getHeight()*2.5);
    }

  @Override
    public void updateLocalTransforms() {

    }

    @Override
    public void localDraw(Graphics g,
                          Point parentOrigin, Point screenOrigin) {
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.drawRect(screenOrigin.getX(),
                screenOrigin.getY(),
                getDimension().getWidth(), getDimension().getHeight());
    }
}

