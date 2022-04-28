package org.csc133.a3.interfaces;

import org.csc133.a3.gameobjects.Fire;

public class UnStarted implements FireState{

    @Override
    public void nextState(Fire fire) {
        fire.setState(new Burning());
    }
}
