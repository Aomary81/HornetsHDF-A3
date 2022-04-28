package org.csc133.a3;

import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a3.commands.*;
import org.csc133.a3.views.ControlCluster;
import org.csc133.a3.views.GlassCockpit;
import org.csc133.a3.views.MapView;

public class Game extends Form implements Runnable {
    private GameWorld gw;
    private MapView mv;
    private ControlCluster cc;
    private GlassCockpit gc;
    public Game() {
        gw = new GameWorld();
        mv = new MapView(gw);
        cc = new ControlCluster(gw);
        gc = new GlassCockpit(gw);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, gc);
        this.add(BorderLayout.CENTER, mv);
        this.add(BorderLayout.SOUTH, cc);

        UITimer timer = new UITimer(this);
        timer.schedule(100, true, this);
        // Exit Key
        addKeyListener('Q', new Exit(gw));
        //Left Arrow used to turn the helicopter left
        addKeyListener(-93, new TurnLeft(gw));
        // Right Arrow used to turn the helicopter right
        addKeyListener(-94, new TurnRight(gw));
        // Up Arrow to speed up the helicopter
        addKeyListener(-91, new Accelerate(gw));
        // Down Arrow to slow down and stop the helicopter
        addKeyListener(-92, new Brake(gw));
        // dump water
        addKeyListener('f', new Fight(gw));
        // drink water
        addKeyListener('d', new Drink(gw));
    }

    @Override
    public void run() {
        gw.tick();
        gc.update();
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        //gw.draw(g);
    }
}
