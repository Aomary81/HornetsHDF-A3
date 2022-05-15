package org.csc133.a3.commands;

import com.codename1.ui.Command;
import org.csc133.a3.GameWorld;

import com.codename1.ui.events.ActionEvent;

public class Drink extends Command {
    private GameWorld gw;

    public Drink(GameWorld gw) {
        super("Drink");
        this.gw = gw;

    }
    @Override
    public void actionPerformed(ActionEvent event){
        gw.drink();
    }

}
