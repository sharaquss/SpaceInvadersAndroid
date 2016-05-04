package com.gamedesire.pszemek.recruitment.actors.primary;


import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 30/04/16.
 */
public class HeroActor extends SpaceInvadersActor {


    public HeroActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getHeroSprite(), location, direction);

    }

    public HeroActor(float locationX, float locationY, float directionX, float directionY) {
        this(new Vector2(locationX, locationY), new Vector2(directionX, directionY));
    }

    @Override
    public void create() {
        rateOfFireIntervalMillis = Constants.RATEOFFIRE_INTERVAL_BASE_PLAYER;
        velocityValue = Constants.VELOCITY_VALUE_PLAYER;
    }

    @Override
    public void update() {
        updatePosition();
    }

    @Override
    public void dispose() {

    }

}