package com.gamedesire.pszemek.recruitment.actors.primary;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gamedesire.pszemek.recruitment.actors.archetypes.SpaceInvadersActor;
import com.gamedesire.pszemek.recruitment.actors.interfaces.IDamageable;
import com.gamedesire.pszemek.recruitment.utilities.AssetRouting;
import com.gamedesire.pszemek.recruitment.utilities.Constants;

/**
 * Created by Ciemek on 30/04/16.
 */
public class EnemyActor extends SpaceInvadersActor implements IDamageable {

    //todo: this is lame architecture, because at the end of a tick there is a need to iterate over
    //todo:   ALL of the actors, to check whether it's alive or not.
    //todo:   - come up with solution that will pass info to actorholder in order to delete this actor.
    boolean     dead;
    boolean     shoot;


    public EnemyActor(Vector2 location, Vector2 direction) {
        super(AssetRouting.getEnemy001Sprite(), location, direction);
    }

    public EnemyActor(float locationX, float locationY, Vector2 direction) {
        this(new Vector2(locationX, locationY), direction);
    }


    @Override
    public void create() {
        velocityValue = Constants.VELOCITY_VALUE_ENEMY_SLOW;
        rateOfFireIntervalMillis = Constants.RATEOFFIRE_INTERVAL_BASE_ENEMY001;
        lastFiredMillis = System.currentTimeMillis() + (long)(MathUtils.random(0.75f, 2f) * 1000);
        shoot = false;
        onSpawn();
    }

    @Override
    public void update() {
        updatePosition();
        //todo: maybe here check for hp amount?
        if (actualHealthPoints == 0)
            onDeath();

        if (System.currentTimeMillis() - lastFiredMillis > rateOfFireIntervalMillis * MathUtils.random(0.85f, 1.25f)) {
            shoot = true;
            lastFiredMillis = System.currentTimeMillis();
        }

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onSpawn() {
        maxHealthPoints = 10;
        actualHealthPoints = maxHealthPoints;
        actualShieldPoints = 0;
        dead = false;
    }

    @Override
    public float onHit(float damageDealt) {

        actualShieldPoints -= damageDealt;

        if (actualShieldPoints <= 0) {
            actualHealthPoints += actualShieldPoints;
            actualShieldPoints = 0;
            if(actualHealthPoints <= 0) {
                actualHealthPoints = 0;
            }

        }
        
        System.err.println("DAMAGE=> dmg: " + damageDealt + ", remaining: " + actualHealthPoints);
        return actualHealthPoints;
    }

    @Override
    public void onDeath() {
        System.err.println("DEATH, hp: " + actualHealthPoints + ", sp: " + actualShieldPoints);
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShot() {
        shoot = false;
    }
}