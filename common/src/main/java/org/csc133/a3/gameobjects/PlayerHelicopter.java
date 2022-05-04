package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;

public class PlayerHelicopter extends Helicopter{
    private int padding = -400;
    public PlayerHelicopter(Point lZ,int color) {
        super(lZ, color);
        translate(lZ.getX(),
                lZ.getY() + padding);
        addHeloText();
    }

    @Override
    public void updateLocalTransforms() {
    //    heloBladeUpdate(-10d*getSpeed());
    }
}
