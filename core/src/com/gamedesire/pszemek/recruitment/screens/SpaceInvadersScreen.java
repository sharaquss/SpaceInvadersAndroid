package com.gamedesire.pszemek.recruitment.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamedesire.pszemek.recruitment.MainGameClass;
import com.gamedesire.pszemek.recruitment.utilities.Constants;
import com.gamedesire.pszemek.recruitment.actors.ActorHolder;
import com.gamedesire.pszemek.recruitment.input.TouchProcessor;
import com.gamedesire.pszemek.recruitment.input.TouchProcessorDesktop;
import com.gamedesire.pszemek.recruitment.input.TouchProcessorMobile;
import com.gamedesire.pszemek.recruitment.ui.DebugUI;
import com.gamedesire.pszemek.recruitment.utilities.Utils;

/**
 * Created by Ciemek on 30/04/16.
 */
public class SpaceInvadersScreen implements Screen {

    ActorHolder actorHolder;

    private MainGameClass   mainGameClass;
    private TouchProcessor  touchProcessor;
    private Viewport        viewport;
    private DebugUI         debugUI;
    private OrthographicCamera camera;

    private Sprite backgroundSprite;
    private ShapeRenderer backgroundGradient;



    public SpaceInvadersScreen(MainGameClass game) {
        mainGameClass = game;
        actorHolder = new ActorHolder();
    }


    @Override
    public void show() {
        actorHolder.spawnHero();
        actorHolder.beginNewLevel();


        backgroundSprite = new Sprite(new Texture("ui_bg_main_tile.png"));
        //todo: figure out why this camera shit is not working
//        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.PREF_WIDTH, Constants.PREF_HEIGHT, camera);

        camera = new OrthographicCamera();

        Utils.initialize();

        backgroundGradient = new ShapeRenderer();
        backgroundSprite.setPosition(10, Gdx.graphics.getHeight() - backgroundSprite.getHeight() - 10);
        debugUI = new DebugUI(mainGameClass.getSpriteBatch());
        Gdx.input.setInputProcessor(getValidInputProcessor());
    }



    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        setRenderBackground();

        mainGameClass.getSpriteBatch().begin();
        mainGameClass.getSpriteBatch().draw(backgroundSprite, backgroundSprite.getX(), backgroundSprite.getY());
        mainGameClass.getSpriteBatch().setProjectionMatrix(debugUI.getStage().getCamera().combined);

        actorHolder.updateAll();
        actorHolder.renderAll(mainGameClass.getSpriteBatch());

        mainGameClass.getSpriteBatch().end();


        //drawing debug ui
        debugUI.render();
        debugUI.getStage().draw();
    }

    private void setRenderBackground() {
        //      todo: figure out why setting projection matrix destroys gradient (???)
//        backgroundGradient.setProjectionMatrix(camera.combined);

        mainGameClass.getSpriteBatch().setProjectionMatrix(camera.combined);


        backgroundGradient.begin(ShapeRenderer.ShapeType.Filled);
        backgroundGradient.rect(
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                Utils.getColorFrom255(200, 0, 150, 1),
                Utils.getColorFrom255(60, 0, 40, 1),
                Utils.getColorFrom255(100, 0, 100, 1),
                Utils.getColorFrom255(80, 0, 40, 1));
        backgroundGradient.end();
    }


    private TouchProcessor getValidInputProcessor() {
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            touchProcessor = new TouchProcessorMobile();
        else
            touchProcessor = new TouchProcessorDesktop();

        touchProcessor.attachActorSpawner(actorHolder);
        return touchProcessor;
    }


    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
