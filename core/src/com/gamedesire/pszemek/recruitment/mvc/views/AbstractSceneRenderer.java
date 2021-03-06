package com.gamedesire.pszemek.recruitment.mvc.views;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ciemek on 08/05/16.
 *
 * Base class for View components of the game.
 */
public abstract class AbstractSceneRenderer implements Disposable {

    protected SpriteBatch   spriteBatch;
    protected Camera        camera;
    protected Viewport      viewport;


    public AbstractSceneRenderer(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }


    protected abstract void create();

    public abstract void render(float deltaTime);

    public abstract void resize(int width, int height);

    public final Camera getCamera() { return camera; }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

}
