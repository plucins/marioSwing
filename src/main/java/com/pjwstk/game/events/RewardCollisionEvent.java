package com.pjwstk.game.events;

import com.pjwstk.game.interfaces.IRewardListener;
import com.pjwstk.game.models.AbstractPaintable;

import java.util.List;

public class RewardCollisionEvent implements IEvent {
    private AbstractPaintable nagroda;
    public RewardCollisionEvent(AbstractPaintable paintable) {
        this.nagroda = paintable;
    }

    @Override
    public void execute() {
        List<IRewardListener> list = Dispatcher.instance.getAllObjectsImplementingInterface(IRewardListener.class);
        for (IRewardListener listener : list) {
            listener.reward(nagroda);
        }
    }
}
