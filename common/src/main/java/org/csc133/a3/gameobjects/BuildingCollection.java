package org.csc133.a3.gameobjects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a3.gameobjects.Building;

public class BuildingCollection extends GameObjectCollection<Building>{
    @Override
    public void draw(Graphics g, Point containerOrigin) {

        for(Building classroom: getGameObjects()){
            classroom.draw(g, containerOrigin);

        }
    }
}
