package org.csc133.a3.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;
import org.csc133.a3.GameWorld;
import org.csc133.a3.commands.*;

public class ControlCluster extends Container {
    GameWorld gw;

    public ControlCluster(GameWorld gw) {
        this.gw =gw;
        TableLayout layout = new TableLayout(1,7);
        this.setLayout(layout);
        Button left = new Button("LEFT");
        left.setCommand(new TurnLeft(gw));
        left.getAllStyles().setBgTransparency(255);
        left.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        Button right = new Button("RIGHT");
        right.setCommand(new TurnRight(gw));
        right.getAllStyles().setBgTransparency(255);
        right.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        Button fight = new Button("FIGHT");
        fight.setCommand(new Fight(gw));
        fight.getAllStyles().setBgTransparency(255);
        fight.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        Button exit = new Button("EXIT");
        exit.setCommand(new Exit(gw));
        exit.getAllStyles().setBgTransparency(255);
        exit.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        Button drink = new Button("DRINK");
        drink.setCommand(new Drink(gw));
        drink.getAllStyles().setBgTransparency(255);
        drink.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        Button brake = new Button("BRAKE");
        brake.setCommand(new Brake(gw));
        brake.getAllStyles().setBgTransparency(255);
        brake.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        Button accel = new Button("ACCEL");
        accel.setCommand(new Accelerate(gw));
        accel.getAllStyles().setBgTransparency(255);
        accel.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        this.add(layout.createConstraint().widthPercentage(10)
                .horizontalAlign(Component.LEFT), left);
        this.add(layout.createConstraint().widthPercentage(10)
                .horizontalAlign(Component.LEFT), right);
        this.add(layout.createConstraint().widthPercentage(10)
                .horizontalAlign(Component.LEFT), fight);
        this.add(layout.createConstraint().widthPercentage(40)
                .horizontalAlign(Component.CENTER), exit);
        this.add(layout.createConstraint().widthPercentage(10)
                .horizontalAlign(Component.RIGHT), drink);
        this.add(layout.createConstraint().widthPercentage(10)
                .horizontalAlign(Component.RIGHT), brake);
        this.add(layout.createConstraint().widthPercentage(10)
                .horizontalAlign(Component.RIGHT), accel);
    }
}
