package org.csc133.a3.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.codename1.util.MathUtil;

import java.util.ArrayList;

public abstract class FlightPath extends GameObject {

    public abstract void updateLocalTransforms();

    public abstract void localDraw(Graphics g, Point parentOrigin,
                                      Point screenOrigin);

    public class Traversal extends BezierCurve{
        private double t;
        private boolean active = false;
        private NonPlayerHelicopter nonPlayerHelicopter;

        public Traversal(NonPlayerHelicopter nonPlayerHelicopter){
            super(nonPlayerHelicopter.worldSize, 4);
            this.nonPlayerHelicopter = nonPlayerHelicopter;
        }

        public void activate(){
            active = true;
        }

        public void deactivate(){
            active = false;
        }

        public boolean isActive(){
            return active;
        }

        @Override
        public void setEndControlPoint(Point2D lastControlPoint){
            super.setEndControlPoint(lastControlPoint);
        }

        public void moveAlongAPath(Point2D c){
            Point2D p = evaluateCurve(t);
            double tx = p.getX() - c.getX();
            double ty = p.getY() - c.getY();
            double theta = 360 - Math.toDegrees(MathUtil.atan2(ty,tx));

            nonPlayerHelicopter.translate(tx,ty);

            if(t<=1){
                t += 0.001*nonPlayerHelicopter.getSpeed();
                nonPlayerHelicopter.rotate(
                        nonPlayerHelicopter.getHeading() - theta);
                nonPlayerHelicopter.setHeading((int) theta);
            }
            else{
                t=0;
            }
        }

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            if(active){
                super.localDraw(g,parentOrigin,screenOrigin);
            }
        }
    }

    public class FlightControl{
        private Traversal primary;
        private Traversal correction;

        public FlightControl(NonPlayerHelicopter nonPlayerHelicopter) {
            primary = new Traversal(nonPlayerHelicopter);
            correction = new Traversal(nonPlayerHelicopter);
            primary.activate();
            correction.deactivate();
        }

        public void moveAlongAPath(Point2D c){
            primary.moveAlongAPath(c);
        }

        public Traversal getPrimary(){
            return primary;
        }

        public Traversal getCorrection(){
            return correction;
        }
    }

    public static class BezierCurve extends GameObject {
        private ArrayList<Point2D> controlPoints;
        private int curveID;
        private boolean activePath = false;

        public BezierCurve(Dimension worldSize, int currentCurveID) {
            controlPoints = new ArrayList<>();
            curveID = currentCurveID;

            if(currentCurveID == 0){
                controlPoints.add(new Point2D(
                        worldSize.getWidth()/2,
                        worldSize.getHeight()/2 - 300));
                controlPoints.add(new Point2D(
                        worldSize.getWidth()/2,
                        worldSize.getHeight()/2 - 100));
                controlPoints.add(new Point2D(
                        10,
                        worldSize.getHeight()/2 + 175));
                controlPoints.add(new Point2D(
                        worldSize.getWidth()/2,
                        worldSize.getHeight()/2 + 175));
                activePath = true;
            }
            else if(currentCurveID == 1){
                controlPoints.add(new Point2D(
                        worldSize.getWidth()/2,
                        worldSize.getHeight()/2 + 175));
                controlPoints.add(new Point2D(
                        worldSize.getWidth()-50,
                        worldSize.getHeight()/2 + 175));
                controlPoints.add(new Point2D(
                        worldSize.getWidth()*3/4 + 100,
                        worldSize.getHeight()/2-200));
            }
            else if(currentCurveID == 2){
                controlPoints.add(new Point2D(
                        worldSize.getWidth()*3/4 + 100,
                        worldSize.getHeight()/2-200));
                controlPoints.add(new Point2D(600,
                        worldSize.getHeight()/2 - 810));
                controlPoints.add(new Point2D(-200,
                        worldSize.getHeight()/2 + 175));
                controlPoints.add(new Point2D(
                        worldSize.getWidth()/2,
                        worldSize.getHeight()/2 + 175));
            }
        }

        public Point2D getEndControlPoint(){
            return controlPoints.get(controlPoints.size()-1);
        }

        public void setStartControlPoint(Point2D pointerPress){
            controlPoints.set(0,pointerPress);
        }

        public void setEndControlPoint(Point2D pointerPress){
            controlPoints.set(controlPoints.size()-1,pointerPress);
        }

        public int getCurveID(){
            return curveID;
        }

        Point2D evaluateCurve(double t) {
            Point2D p = new Point2D(0, 0);
            int d = controlPoints.size() - 1;
            for (int i = 0; i < controlPoints.size(); i++) {
                p.setX(p.getX()
                        + bernsteinD(d, i, t) * controlPoints.get(i).getX());
                p.setY(p.getY()
                        + bernsteinD(d, i, t) * controlPoints.get(i).getY());
            }
            return p;
        }

        private void drawBezierCurve(Graphics g,
                                     ArrayList<Point2D> controlPoints) {
            final double smallFloatIncrement = 0.001;
            Point2D currentPoint = controlPoints.get(0);
            Point2D nextPoint;
            double t = 0;
            g.setColor(ColorUtil.GRAY);

            for (Point2D p : controlPoints) {
                g.fillArc((int) p.getX() - 15, (int) p.getY() - 15,
                        30, 30, 0, 360);
            }

            if(activePath){
                g.setColor(ColorUtil.CYAN);
            }
            else{
                g.setColor(ColorUtil.WHITE);
            }

            while (t < 1){
                nextPoint = evaluateCurve(t);
                g.drawLine((int) currentPoint.getX(),
                        (int) currentPoint.getY(),
                        (int) nextPoint.getX(),
                        (int) nextPoint.getY());
                currentPoint = nextPoint;
                t = t + smallFloatIncrement;
            }

            nextPoint = controlPoints.get(controlPoints.size() - 1);
            g.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(),
                    (int) nextPoint.getX(), (int) nextPoint.getY());
        }

        private double bernsteinD(int d, int i, double t) {
            return choose(d, i) * MathUtil.pow(t, i)
                    * MathUtil.pow(1 - t, d - i);
        }

        private double choose(int n, int k) {
            if (k <= 0 || k >= n) {
                return 1;
            }
            return choose(n - 1, k - 1) + choose(n - 1, k);
        }

        public void newActivePath(){
            activePath = true;
        }

        public void oldPath() {
            activePath = false;
        }

        @Override
        public void updateLocalTransforms() {}

        @Override
        public void localDraw(Graphics g, Point parentOrigin,
                                 Point screenOrigin) {
            containerTranslate(g, parentOrigin);
            drawBezierCurve(g, controlPoints);
        }
    }
}