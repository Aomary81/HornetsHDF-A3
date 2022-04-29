package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class FireCollection extends GameObjectCollection<Fire>{

    public FireCollection(){
        super();
        setColor(ColorUtil.rgb(255, 0, 0));
    }

    @Override
    public void updateLocalTransforms() {

    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                          Point screenOrigin) {
        for(Fire spot: getGameObjects()){
            spot.draw(g, parentOrigin, screenOrigin);
        }
    }
}
