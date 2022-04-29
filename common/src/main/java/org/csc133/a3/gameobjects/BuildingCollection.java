package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.Building;

public class BuildingCollection extends GameObjectCollection<Building>{

    @Override
    public void updateLocalTransforms() {

    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin, Point screenOrigin) {
        for(Building classroom: getGameObjects()){
            classroom.draw(g, parentOrigin, screenOrigin);

        }

    }
}
