package com.pjwstk.game.interfaces;

import com.pjwstk.game.models.AbstractPaintable;

public interface ICollisionListener {
    public void collisionVertical(AbstractPaintable with);
    public void collisionHorizontal(AbstractPaintable with);
}
