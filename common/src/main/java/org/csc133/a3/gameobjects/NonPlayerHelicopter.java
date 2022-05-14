package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.util.MathUtil;
import org.csc133.a3.gameobjects.FlightPath.*;

public class NonPlayerHelicopter extends Helicopter{
    private BezierCurve bezierCurve;
    private double t = 0;
    private double heading = 0;
    private int speed = 0;

    public NonPlayerHelicopter(Point lZ, BezierCurve bezierCurve) {
        super(lZ, ColorUtil.rgb(255,204,246));
        addHeloText();
        this.bezierCurve = bezierCurve;
        heading-= 90;
    }

    public void setPath(BezierCurve bezierCurve) {
        this.bezierCurve = bezierCurve;
        t=0;
    }

    public void setSpeed(){
        speed = 20;
    }

    @Override
    public void updateLocalTransforms(){
        heloBladeUpdate();
        Point2D c = new Point2D(myTranslation.getTranslateX(),
                myTranslation.getTranslateY());
        Point2D p = this.bezierCurve.evaluateCurve(t);
        double tx = p.getX() - c.getX();
        double ty = p.getY() - c.getY();
        double theta = 360 - Math.toDegrees(MathUtil.atan2(ty,tx));

        this.translate(tx,ty);

        if(t<=1){
            t += 0.001*speed;
            rotate(heading - theta);
            heading = theta;
        }
        else{
            t=0;
        }
    }
    public boolean collision(Helicopter helicopter){
        return helicopter.myTranslation.getTranslateX()
                + helicopter.getWidth()/8 > myTranslation.getTranslateX()
                - getWidth()/8 &&
                helicopter.myTranslation.getTranslateX()
                        - helicopter.getWidth()/8
                        < myTranslation.getTranslateX()
                        + getWidth()/8 &&
                helicopter.myTranslation.getTranslateY()
                        + helicopter.getHeight()/8
                        > myTranslation.getTranslateY()
                        - getHeight()/8 &&
                helicopter.myTranslation.getTranslateY()
                        - helicopter.getHeight()/8
                        < myTranslation.getTranslateY()
                        + getHeight()/8;
    }

    public boolean collisionAvoidance(Helicopter helicopter) {
        return helicopter.myTranslation.getTranslateX()
                + helicopter.getWidth()/8 > myTranslation.getTranslateX()
                - getWidth()/8 &&
                helicopter.myTranslation.getTranslateX()
                        - helicopter.getWidth()/8
                        < myTranslation.getTranslateX()
                        + getWidth()/8 &&
                helicopter.myTranslation.getTranslateY()
                        + helicopter.getHeight()/8
                        > myTranslation.getTranslateY()
                        - getHeight()/8 &&
                helicopter.myTranslation.getTranslateY()
                        - helicopter.getHeight()/8
                        < myTranslation.getTranslateY()
                        + getHeight()/8;
    }
}
