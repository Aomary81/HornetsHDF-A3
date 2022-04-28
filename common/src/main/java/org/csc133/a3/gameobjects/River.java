package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class River extends Fixed{


    public River(Dimension worldSize) {
        this.worldSize = worldSize;
        this.color = ColorUtil.BLUE;
        this.location = new Point2D(0, 0);
        this.dimension = new Dimension(worldSize.getWidth(),
                worldSize.getHeight());

    }


    int getRiverNorth() {
        return (int)location.getY() + (worldSize.getHeight()/9 * 2);
    }

    int getRiverSouth() {
        return (getRiverNorth() + dimension.getHeight()/9 * 2);
    }
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.clearRect(containerOrigin.getX(), containerOrigin.getY(),
                worldSize.getWidth(), worldSize.getHeight());
        g.setColor(color);
        g.drawRect(containerOrigin.getX() + (int)location.getX(),
                (containerOrigin.getY() + (worldSize.getHeight()/9 *2)),
                dimension.getWidth(),
                dimension.getHeight()/9 * 2);
    }
}