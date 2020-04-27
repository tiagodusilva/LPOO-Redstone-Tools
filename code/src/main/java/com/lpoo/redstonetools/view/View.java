package com.lpoo.redstonetools.view;

import com.lpoo.redstonetools.model.Model;

public abstract class View<M extends Model> {

    public abstract void render(M model);
}
