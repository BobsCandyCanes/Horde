package com.Horde;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

// A queen spawns drones and cannot be killed

public class Queen extends Entity
{
	final static int DFLT_WIDTH = 30;
	final static int DFLT_HEIGHT = 30;

	int spawnCounter = 5;

	int numDrones;

	public Queen(float x, float y)
	{
		health = 500;

		setWidth(DFLT_WIDTH);
		setHeight(DFLT_HEIGHT);

		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);

		setBounds(getX(), getY(), getWidth(), getHeight());

		setOrigin(getWidth() / 2, getHeight() / 2);
	}

	@Override
	public void draw(Batch batch, float alpha)
	{
		batch.draw(TextureLibrary.getTexture("queen"), getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(), -getRotation());
	}

	public void takeDamage(int damage)
	{
		health -= damage;

		if(health <= 0)
		{
			this.destroy();
		}
	}

	@Override
	public void act(float deltaTime)
	{	
		updateNumDrones();

		//Gradually spawns more drones each time
		if(numDrones < spawnCounter)
		{
			for(int i = 0; i < spawnCounter; i++)
			{
				spawnDrone();
			}

			spawnCounter += 20;
		}

		moveBy(xVelocity, yVelocity);

		rotateBy(rotationSpeed);

		setBounds(getX(), getY(), getWidth(), getHeight());
	}

	public void updateNumDrones()
	{
		numDrones = 0;

		if(getStage() != null)
		{
			for(Actor a : getStage().getActors())
			{
				if(a instanceof Drone)
				{
					numDrones++;
				}
			}
		}
	}

	public void spawnDrone()
	{
		// Drone creation is handled by DronePool
		// This just asks for a free drone from the pool
		Drone drone = DronePool.obtain();
		drone.init(getX() + getOriginX(), getY() + getOriginY());
		getStage().addActor(drone);
	}

	public void destroy()
	{
		Horde.spawnQueen();
		Horde.spawnQueen();
		super.destroy();
	}
}
