package com.Horde;

import com.badlogic.gdx.graphics.g2d.Batch;

/*
 * A player can move, shoot, and drop bombs
 * Input is handled in Horde.pollInput()
 */

public class Player extends Entity
{
	final static float SPEED = 0.5f;

	final static int DFLT_WIDTH = 25;
	final static int DFLT_HEIGHT = 30;

	final static int MAX_HEALTH = 100;
	
	final static int PROJECTILE_SPEED = 8;

	final static int FIRE_DELAY = 0;
	final static int BOMB_DELAY = 10;
	
	int turnCounter;

	int timeSinceLastShot;
	int timeSinceLastBomb;

	public Player(float x, float y)
	{
		setWidth(DFLT_WIDTH);
		setHeight(DFLT_HEIGHT);

		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);
		
		setBounds(getX(), getY(), getWidth(), getHeight());

		setOrigin(getWidth() / 2, getHeight() / 2);

		health = MAX_HEALTH;
	}	

	@Override
	public void act(float deltaTime)
	{
		timeSinceLastShot++;
		timeSinceLastBomb++;

		moveBy(xVelocity, yVelocity);

		xVelocity *= 0.9;
		yVelocity *= 0.9;

		bounceOffEdges();
		
		rotateBy(rotationSpeed);

		setBounds(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float alpha)
	{				
		batch.draw(TextureLibrary.getTexture("player"), getX(),getY(), 
				getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), -getRotation());
	}

	public void shoot()
	{
		if(timeSinceLastShot > FIRE_DELAY)
		{
			Projectile p = new Projectile(getX() + getWidth() / 2, getY() + getHeight() / 2);
			p.xVelocity = PROJECTILE_SPEED * (float)Math.sin(Math.toRadians(getRotation()));
			p.yVelocity = PROJECTILE_SPEED * (float)Math.cos(Math.toRadians(getRotation()));
			
			getStage().addActor(p);
		}

		timeSinceLastShot = 0;
	}
	
	public void dropBomb()
	{
		if(timeSinceLastBomb > BOMB_DELAY)
		{
			Bomb b = new Bomb(getX() + getWidth() / 2, getY() + getHeight() / 2);

			getStage().addActor(b);
		}

		timeSinceLastBomb = 0;
	}
	
	public void moveForward()
	{
		xVelocity += SPEED * Math.sin(Math.toRadians(getRotation()));
		yVelocity += SPEED * Math.cos(Math.toRadians(getRotation()));
	}

	public void moveBackward()
	{
		xVelocity -= SPEED * Math.sin(Math.toRadians(getRotation()));
		yVelocity -= SPEED * Math.cos(Math.toRadians(getRotation()));
	}
	
	public void takeDamage(int damage)
	{
		health -= damage;
		
		if(health <= 0)
		{
			this.destroy();
		}
	}
	
	public void destroy()
	{
		super.destroy();
		Horde.player = null;
	}
}
