package com.Horde;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Drone extends Entity implements Poolable
{
	final static int SPEED = 2;

	final static int DFLT_WIDTH = 12;
	final static int DFLT_HEIGHT = 12;

	final static int MAX_HEALTH = 100;

	final static int AGGRO_RANGE = 300;

	final static int DAMAGE = 10;

	static Random random = new Random();

	boolean alive = false;

	int turnCounter;

	public Drone()
	{
		setWidth(DFLT_WIDTH);
		setHeight(DFLT_HEIGHT);

		setOrigin(getWidth() / 2, getHeight() / 2);
		
		setZIndex(0);
	}	

	public void init(float x, float y)
	{
		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);

		setBounds(getX(), getY(), getWidth(), getHeight());

		health = MAX_HEALTH;

		alive = true;
	}

	@Override
	public void act(float deltaTime)
	{
		if(alive)
		{
			turnCounter++;

			moveBy(xVelocity, yVelocity);

			bounceOffEdges();

			rotateBy(rotationSpeed);

			setBounds(getX(), getY(), getWidth(), getHeight());

			decideBehavior();
		}
	}

	public void decideBehavior()
	{
		if(Horde.player != null && getDistanceFrom(Horde.player) < AGGRO_RANGE)
		{
			goTowardsPlayer();

			checkForCollision();
		}
		else
		{
			wander();
		}
	}

	public void goTowardsPlayer()
	{
		double angleToPlayer = calcAngleTo(Horde.player);

		xVelocity = (float)(SPEED * Math.cos(angleToPlayer));
		yVelocity = -(float)(SPEED * Math.sin(angleToPlayer));
	}

	public void checkForCollision()
	{	
		Player player = Horde.player;
		
		if(player != null)
		{
			if(getDistanceFrom(player) < getWidth())
			{
				player.takeDamage(DAMAGE);
				this.destroy();
			}
		}
	}
	
	public void wander()
	{
		if(turnCounter >= 40)
		{
			int randXVel = (random.nextInt(SPEED + SPEED + 1) - SPEED);
			int randYVel = (random.nextInt(SPEED + SPEED + 1) - SPEED);

			xVelocity = randXVel;
			yVelocity = randYVel;

			turnCounter = 0;
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha)
	{		
		if(alive)
		{
			batch.draw(TextureLibrary.getTexture("drone"), getX(), getY(), getOriginX(), getOriginY(),
					getWidth(), getHeight(), getScaleX(), getScaleY(), -getRotation());
		}
	} 

	public void takeDamage(int damage)
	{
		health -= damage;

		if(health <= 0)
		{
			this.destroy();
		}
	}

	public void reset()
	{
		setX(0);
		setY(0);
		setRotation(0);
		xVelocity = 0;
		yVelocity = 0;
	}

	public void destroy()
	{
		getStage().addActor(new Corpse(getX() + getOriginX(), getY() + getOriginY(), "drone"));
		this.remove();
		DronePool.free(this);
		alive = false;
	}	
}
