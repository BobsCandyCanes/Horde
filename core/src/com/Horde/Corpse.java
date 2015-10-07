package com.Horde;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Corpse extends Entity
{
	final static int DFLT_WIDTH = 12;
	final static int DFLT_HEIGHT = 12;

	int timeAlive;

	int maxTimeAlive = 50;

	float oldAlpha;
	float alpha = 1;
	
	float deltaAlpha;
	float deltaSize;

	String objectType = "projectile";

	Color batchColor;

	public Corpse(float x, float y, String objectType)
	{
		setWidth(DFLT_WIDTH);
		setHeight(DFLT_HEIGHT);

		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);

		setOrigin(getWidth() / 2, getHeight() / 2);

		this.objectType = objectType;
		
		init();
	}	
	
	public void init()
	{
		if(objectType.equals("drone"))
		{
			maxTimeAlive = 50;
		}
		else if(objectType.equals("projectile"))
		{
			maxTimeAlive = 5;
		}
		
		deltaAlpha = 1f / maxTimeAlive;
		deltaSize = 5f / maxTimeAlive;
	}

	public void act(float deltaTime)
	{
		super.act(deltaTime);

		timeAlive++;

		if(timeAlive > maxTimeAlive)
		{
			destroy();
		}

		update();
	}

	public void update()
	{
		if(objectType.equals("drone"))
		{
			alpha -= 0.1;
		}
		else if(objectType.equals("projectile"))
		{	
			alpha -= deltaAlpha;
			
			setWidth(getWidth() + deltaSize);
			setX(getX() - deltaSize / 2f);
			setHeight(getWidth() + deltaSize);
			setY(getY() - deltaSize / 2f);
		}
	}

	@Override
	public void draw(Batch batch, float alpha)
	{
		batchColor = batch.getColor();
		oldAlpha = batchColor.a;
		batchColor.a = this.alpha;
		batch.setColor(batchColor);

		batch.draw(TextureLibrary.getTexture(objectType), getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(), -getRotation());

		batchColor.a = oldAlpha;
		batch.setColor(batchColor);
	}
}
