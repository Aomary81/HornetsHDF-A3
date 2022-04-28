package org.csc133.a3.commands;

import com.codename1.ui.Command;
import org.csc133.a3.GameWorld;

import com.codename1.ui.events.ActionEvent;

public class TurnLeft extends Command {
    private GameWorld gw;

    public TurnLeft(GameWorld gw) {
        super("Left");
        this.gw = gw;
    }
    @Override
    public void actionPerformed(ActionEvent event){
        gw.turnLeft();
    }

}
