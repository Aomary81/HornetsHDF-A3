package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

public class Helipad extends Fixed{
    private int helipadId;

    public Helipad(Dimension worldSize) {
        this.worldSize = worldSize;
        this.color = ColorUtil.GRAY;
        this.dimension = new Dimension(200, 200);
        this.location = new Point2D(worldSize.getWidth()/2,
                worldSize.getHeight());
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
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(color);
        g.drawArc(containerOrigin.getX() + (int)location.getX()
                        - (dimension.getWidth()/8 *3),
                containerOrigin.getY() +
                        (int)location.getY() - dimension.getWidth()/2 -
                        dimension.getWidth() + dimension.getWidth()/8,
                (dimension.getWidth() - 50),
                (dimension.getWidth() - 50), 0,360);
        g.drawRect(containerOrigin.getX() + (int)location.getX() -
                        dimension.getWidth()/2, containerOrigin.getY() +
                        (int)location.getY() - dimension.getWidth()/2 -
                        dimension.getWidth(), dimension.getWidth(),
                dimension.getWidth(), 5);
    }
}
