package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;


public class FireCollection extends GameObjectCollection<Fire>{

    public FireCollection(){
        super();
        this.color = ColorUtil.rgb(255,0,0);
    }
    public void draw(Graphics g, Point containerOrigin) {
        for(Fire spot: getGameObjects()){
            spot.draw(g, containerOrigin);
        }
    }
}
