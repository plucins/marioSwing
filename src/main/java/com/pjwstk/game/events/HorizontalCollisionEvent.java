package com.pjwstk.game.events;

import com.pjwstk.game.models.AbstractPaintable;
import com.pjwstk.game.interfaces.ICollisionListener;

import java.util.List;

public class HorizontalCollisionEvent implements IEvent {
    private AbstractPaintable collidedWith;

    public HorizontalCollisionEvent(AbstractPaintable collidedWith) {
        this.collidedWith = collidedWith;
    }

    @Override
    public void execute() {
        List<ICollisionListener> list = Dispatcher.instance.getAllObjectsImplementingInterface(ICollisionListener.class);
        for (ICollisionListener listener : list) {
            listener.collisionHorizontal(collidedWith);
        }
    }

}
