package org.csc133.a3.gameobjects;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.interfaces.Drawable;

public abstract class GameObject implements Drawable {
    int color;
    Point2D location;
    Dimension dimension, worldSize;

    public String toString(){
        return this.getClass().getSimpleName();
    }


}
