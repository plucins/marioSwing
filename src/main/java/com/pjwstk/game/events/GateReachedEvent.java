package com.pjwstk.game.events;

import com.pjwstk.game.interfaces.IFinishGateReachedListener;

import java.util.List;

public class GateReachedEvent implements IEvent {
    @Override
    public void execute() {
        List<IFinishGateReachedListener> listeners = Dispatcher.instance.getAllObjectsImplementingInterface(IFinishGateReachedListener.class);
        for (IFinishGateReachedListener listener: listeners) {
            listener.playerReachedGate();
        }
    }
}
