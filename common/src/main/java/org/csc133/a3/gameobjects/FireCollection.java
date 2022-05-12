package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class FireCollection extends GameObjectCollection<Fire> {

    public FireCollection(){
        super();
        this.color = ColorUtil.MAGENTA;
    }

    @Override
    public void updateLocalTransforms() {}

    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                             Point screenOrigin) {}

    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {
        for(Fire fire : getGameObjects()){
            fire.draw(g, originParent, originScreen);
        }
    }
}
