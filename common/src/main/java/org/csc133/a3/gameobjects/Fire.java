package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.util.MathUtil;
import org.csc133.a3.interfaces.FireDispatch;
import org.csc133.a3.views.MapView;
import static com.codename1.ui.CN.*;

import java.util.Random;

public class Fire extends Fixed implements FireDispatch{
    private int greatestSize;
    private int size;
    private boolean fireStart;
    private int buildingID;
    private boolean selected;

    public Fire(Dimension worldSize, int buildingID){
        this.worldSize = worldSize;
        this.color = ColorUtil.MAGENTA;
        this.buildingID = buildingID;
        size = new Random().nextInt(4) + 9;
        greatestSize = size;
        fireStart = false;
        selected = false;
        setDimension(new Dimension(size,size));
        translate(-getDimension().getWidth(),-getDimension().getHeight());
    }

    public boolean isCollidingWith(Helicopter helicopter){
        return super.isCollidingWith(helicopter);
    }

    public void grow(){
        if (dimension.getWidth() < 25) {
            dimension.setWidth(dimension.getWidth() + 4);
            dimension.setHeight(dimension.getHeight() + 4);
            size += 4;
            myTranslation.translate(- 2, - 2);
        }

        if(size > greatestSize){
            greatestSize = size;
        }
    }

    public void shrink(int water){
        dimension.setWidth(dimension.getWidth() -8);
        dimension.setHeight(dimension.getHeight() -8);
        this.myTranslation.translate(4,4);
    }

    public void biggerShrink(){
        dimension.setWidth(5);
        dimension.setHeight(5);
    }

    public void start(){
        fireStart = true;
    }

    public boolean begin(){
        return fireStart;
    }

    public int getSize(){
        return this.dimension.getWidth();
    }

    public int getMaxSize(){
        return 30;
    }

    public int getGreatestSize(){
        return greatestSize;
    }

    public int getBuildingID(){
        return this.buildingID;
    }

    public double currentSize() {
        double x = (MathUtil.pow(dimension.getWidth()/2, 2) * Math.PI);
        return x;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public void select(boolean selected) {
        selected = true;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void update(Object o) {
        if(o instanceof MapView){
            MapView f = (MapView) o;
        }
    }

    @Override
    public void updateLocalTransforms() {}

    @Override
    public void localDraw(Graphics g, Point parentOrigin, Point screenOrigin){
        g.setColor(getColor());
        containerTranslate(g,parentOrigin);
        cn1ForwardPrimitiveTranslate(g,getDimension());
        g.fillArc(getDimension().getWidth()/2, parentOrigin.getY()/2,
                getWidth(), getHeight(),
                0, 360);
        g.setFont(Font.createSystemFont
                (FACE_MONOSPACE, STYLE_BOLD, SIZE_MEDIUM));
        g.scale(1,-1);
        g.drawString("" + getSize(), getMaxSize()+ getSize(),
                getMaxSize() - worldSize.getHeight()/4);
    }
}