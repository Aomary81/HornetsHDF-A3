package org.csc133.a3.views;


import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import org.csc133.a3.GameWorld;
import org.csc133.a3.gameobjects.GameObject;
import org.csc133.a3.gameobjects.Helicopter;
import org.csc133.a3.gameobjects.Helipad;

public class MapView extends Container {
    GameWorld gw;
    private float winLeft, winBottom, winRight, winTop;
    private Helicopter helicopter;
    private Helipad helipad;
    public MapView(GameWorld gw) {
        this.gw = gw;
    }

    @Deprecated
    public void displayTransform(Graphics g){
        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(), getAbsoluteY());

        gXform.translate(0,getHeight());
        gXform.scale(1f,-1f);

        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }
    private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom){
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.scale(1/winWidth, 1/winHeight);
        tmpXform.translate(-winLeft,-winBottom);
        return tmpXform;
    }

    private Transform buildNDtoDisplayXform(float displayWidth, float displayHeight){
        Transform tmpXform = Transform.makeIdentity();
        tmpXform.translate(0,displayHeight);
        tmpXform.scale(displayWidth, -displayHeight);
        return tmpXform;
    }
    private void setupVTM(Graphics g){
        Transform worldToND, ndToDisplay, theVTM;
        winLeft = winBottom = 0;
        winRight = this.getWidth();
        winTop = this.getHeight();
        float winHeight = winTop - winBottom;
        float winWidth= winRight - winLeft;

        worldToND = buildWorldToNDXform(winWidth,winHeight,winLeft,winBottom);
        ndToDisplay = buildNDtoDisplayXform(this.getWidth(),this.getHeight());
        theVTM = ndToDisplay.copy();
        theVTM.concatenate(worldToND);

        Transform gXform = Transform.makeIdentity();
        g.getTransform(gXform);
        gXform.translate(getAbsoluteX(),getAbsoluteY());
        gXform.concatenate(theVTM);
        gXform.translate(-getAbsoluteX(),-getAbsoluteY());
        g.setTransform(gXform);
    }

    @Override
    public void laidOut(){
        gw.setDimension(new Dimension(this.getWidth(),this.getHeight()));
        gw.init();
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

        Point parentOrigin = new Point(this.getX(), this.getY());
        Point screenOrigin = new Point(getAbsoluteX(), getAbsoluteY());

        setupVTM(g);
        helicopter.draw(g, parentOrigin, screenOrigin);// maybe
        helipad.draw(g, parentOrigin, screenOrigin);
        g.resetAffine();
    }

    public void updateLocalTransforms() {
//        for(GameObject go: gw.getGameObjectCollection()){
//            go.updateLocalTransforms();
//        }
    }

    public void init() {
        helipad = new Helipad(new Point(getWidth()/2,getHeight()/8));
        helicopter = new Helicopter(new Point(getWidth()/2,getHeight()/8));
    }
}
