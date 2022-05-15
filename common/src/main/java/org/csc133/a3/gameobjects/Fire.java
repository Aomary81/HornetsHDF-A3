package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.interfaces.FireState;
import org.csc133.a3.interfaces.UnStarted;

import java.util.Random;

import static com.codename1.ui.CN.*;

public class Fire extends Fixed {
    private int fireSize;
    private Random r;
    private FireState state;

    public Fire(Dimension worldSize) {
        this.state = new UnStarted();
        r = new Random();
        fireSize = 3 + r.nextInt(4);
        this.worldSize = worldSize;
        setColor(ColorUtil.MAGENTA);
        this.location = new Point2D(0,0);
        this.dimension = new Dimension(fireSize, fireSize);

    }

    public void grow() {
        fireSize = fireSize + r.nextInt(5);
    }

    public int shrink(int water) {
        fireSize = fireSize - (water / 2);
        return fireSize;
    }

    public double areaOfFire() {
        return Math.PI * fireSize / 2 * fireSize / 2;
    }

    int getFireX() {
        return (int) location.getX();
    }

    int getFireY() {
        return (int) location.getY();
    }

    public int getFireSize() {
        return fireSize;
    }

    public void setLocationX(int locX) {
        location.setX(locX);
    }

    public void setLocationY(int locY) {
        location.setY(locY);
    }

    @Override
    public void localDraw(Graphics g, Point parentOrigin,
                          Point screenOrigin){
        g.setColor(getColor());
        g.fillArc(parentOrigin.getX() + (int) location.getX(),
                parentOrigin.getY() + (int) location.getY(),
                fireSize/2, fireSize/2, 0, 360);
        g.setFont(Font.createSystemFont(FACE_MONOSPACE, STYLE_BOLD,
                SIZE_MEDIUM));
        g.drawString("" + fireSize,
                parentOrigin.getX() + (int) location.getX()
                        + (fireSize/4 * 3),
                parentOrigin.getY() + (int) location.getY()
                        + (fireSize/4 * 3));

    }
    public void setState(FireState state){
        this.state = state;
    }

    public void getState(){
        this.state.nextState(this);
    }

    @Override
    public void updateLocalTransforms() {

    }
}