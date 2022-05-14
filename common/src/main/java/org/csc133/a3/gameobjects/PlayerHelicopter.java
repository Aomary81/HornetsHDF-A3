package org.csc133.a3.gameobjects;

import com.codename1.ui.geom.Point;

public class PlayerHelicopter extends Helicopter{
    public PlayerHelicopter(Point lZ,int color) {
        super(lZ, color);
        translate(lZ.getX(),
                lZ.getY());
        addHeloText();
    }

    @Override
    public void updateLocalTransforms() {
        heloBladeUpdate();
    }
}
