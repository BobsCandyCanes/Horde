package com.Horde;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class Entity extends Group
{	
	float xVelocity;
	float yVelocity;

	float rotationSpeed;

	float health;

	public Entity()
	{	
	}

	public float getVelocity()
	{
		return Math.abs(xVelocity) + Math.abs(yVelocity);
	}

	@Override
	public void act(float deltaTime)
	{
		moveBy(xVelocity, yVelocity);

		rotateBy(rotationSpeed);

		setBounds(getX(), getY(), getWidth(), getHeight());

		for(Actor a : getChildren())
		{
			a.act(deltaTime);
		}
	}

	public void bounceOffEdges()
	{
		//Keeps entities from going off the edge of the world

		if(getStage() != null)
		{
			if(getX() < 0)
			{
				setX(0);
				xVelocity *= -1;
			}
			else if(getX() + getWidth() > getStage().getWidth())
			{
				setX(getStage().getWidth() - getWidth());
				xVelocity *= -1;
			}
			if(getY() < 0)
			{
				setY(0);
				yVelocity *= -1;
			}
			else if(getY() + getHeight() > getStage().getHeight())
			{
				setY(getStage().getHeight() - getHeight());
				yVelocity *= -1;
			}
		}
	}

	public double getDistanceFrom(Entity e)
	{
		double xDiff = getXDistanceFrom(e);
		double yDiff = getYDistanceFrom(e);

		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
	}

	public double getXDistanceFrom(Entity e)
	{
		return e.getX() - this.getX();
	}

	public double getYDistanceFrom(Entity e)
	{
		return e.getY() - this.getY();
	}

	public double calcAngleTo(Entity target)
	{
		double xDifference = getXDistanceFrom(target);
		double yDifference = getYDistanceFrom(target);

		return Math.atan2(-yDifference, xDifference);
	}

	public void destroy()
	{
		remove();
	}
}
