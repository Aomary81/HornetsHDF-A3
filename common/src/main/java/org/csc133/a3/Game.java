package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a3.views.ControlCluster;
import org.csc133.a3.views.GlassCockpit;
import org.csc133.a3.views.MapView;

public class Game extends Form implements Runnable{
    private GameWorld gw;
    private MapView mapView;
    private GlassCockpit glassCockPit;
    private ControlCluster controlCluster;

    public Game(){
        gw = new GameWorld();
        setTitle("GameWorld");

        mapView = new MapView(gw);
        glassCockPit = new GlassCockpit(gw);
        controlCluster = new ControlCluster(gw);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, glassCockPit);
        this.add(BorderLayout.CENTER, mapView);
        this.add(BorderLayout.SOUTH, controlCluster);

        addKeyListener(-93, (evt) ->
                gw.turn(-15)); // Left Arrow
        addKeyListener(-94, (evt) ->
                gw.turn(15)); // Right Arrow
        addKeyListener(-91, (evt) ->
                gw.adjustSpeed(1)); // Up Arrow
        addKeyListener(-92, (evt) ->
                gw.adjustSpeed(-1)); // Down Arrow
        addKeyListener('Q', (evt) -> gw.quit());
        addKeyListener('d', (evt) -> gw.drink());
        addKeyListener('f', (evt) -> gw.fight());
        addKeyListener('z', (evt) -> gw.zoom());
        addKeyListener('s', (evt) -> gw.startEngine());

        UITimer timer = new UITimer(this);
        timer.schedule(100,true,this);

        this.getAllStyles().setBgColor(ColorUtil.BLACK);
        this.show();
        gw.init();
    }

    public void paint(Graphics g){
        super.paint(g);
    }

    @Override
    public void run(){
        mapView.updateLocalTransforms();
        mapView.repaint();

        gw.tick();
        glassCockPit.update();
        repaint();
    }
}