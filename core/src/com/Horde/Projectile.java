package com.Horde;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
 * Projectile is shot by the player, and does damage to any drones it hits
 */

public class Projectile extends Entity
{
	final static int SPEED = 2;

	final static int DFLT_WIDTH = 10;
	final static int DFLT_HEIGHT = 10;

	final static int RANGE = 60;

	int damage = 150;

	int timeAlive;

	public Projectile(float x, float y)
	{
		setWidth(DFLT_WIDTH);
		setHeight(DFLT_HEIGHT);

		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);

		setBounds(getX(), getY(), getWidth(), getHeight());

		setOrigin(getWidth() / 2, getHeight() / 2);
	}	

	public void act(float deltaTime)
	{
		super.act(deltaTime);

		timeAlive++;

		setBounds(getX(), getY(), getWidth(), getHeight());

		checkForCollision();

		if(timeAlive > RANGE)
		{
			destroy();
		}
	}

	public void checkForCollision()
	{
		/*
		 * The projectile can go through multiple enemies, losing damage each time
		 * When damage is reduced to 0, the projectile is destroyed
		 */
		
		float enemyHealth = 0;
		
		Drone drone;
		Queen queen;

		for(Actor a : getStage().getActors())
		{
			if(a instanceof Drone)
			{
				drone = (Drone)a;

				if(getDistanceFrom(drone) < getWidth())
				{
					enemyHealth = drone.health;

					// Math.abs is needed because of a weird bug
					drone.takeDamage(Math.abs(damage));

					damage -= Math.abs(enemyHealth);

					if(damage <= 0)
					{
						destroy();
					}
				}
			}
			else if(a instanceof Queen)
			{
				queen = (Queen)a;

				if(getDistanceFrom(queen) < getWidth())
				{
					enemyHealth = queen.health;

					// Math.abs is needed because of a weird bug
					queen.takeDamage(Math.abs(damage));

					damage -= Math.abs(enemyHealth);

					if(damage <= 0)
					{
						destroy();
					}
				}
			}
		}
	}

	@Override
	public void draw(Batch batch, float alpha)
	{
		batch.draw(TextureLibrary.getTexture("projectile"), getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(), -getRotation());
	}

	@Override
	public void destroy()
	{
		if(getStage() != null)
		{
			getStage().addActor(new Corpse(getX() + getOriginX(), getY() + getOriginY(), "projectile"));
		}
		remove();
	}
}
