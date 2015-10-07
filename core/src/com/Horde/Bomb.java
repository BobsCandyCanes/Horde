package com.Horde;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
A bomb sits in place for a certain amount of time,
then explodes and kills all drones in a certain radius
*/

public class Bomb extends Entity
{
	final static int DFLT_WIDTH = 14;
	final static int DFLT_HEIGHT = 14;

	final static int RADIUS = 300;

	final static int DAMAGE = 150;

	int delay = 100;

	int timeAlive;

	public Bomb(float x, float y)
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

		if(timeAlive == delay)
		{
			explode();
		}
	}

	public void explode()
	{	
		/*
		 * For some reason I have to repeat this code 10 times
		 * for it to get all of the drones
		 * Fuck the world
		 */
		
		for(int i = 0; i < 10; i++)
		{
			for(Actor a : getStage().getActors())
			{
				if(a instanceof Drone)
				{	
					Drone d = (Drone)a;

					if(getDistanceFrom(d) < RADIUS)
					{
						d.takeDamage(DAMAGE);
					}
				}
				else if (a instanceof Queen)
				{
					Queen q = (Queen)a;

					if(getDistanceFrom(q) < RADIUS)
					{
						q.takeDamage(DAMAGE);
					}
				}
			}
		}

		destroy();
	}


	@Override
	public void draw(Batch batch, float alpha)
	{
		//Draws the bomb itself, and also a ring to show the radius
		
		batch.draw(TextureLibrary.getTexture("bomb"), getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(), -getRotation());

		batch.draw(TextureLibrary.getTexture("ring"), getX() - RADIUS, getY() - RADIUS, getOriginX(), getOriginY(),
				RADIUS * 2, RADIUS * 2, getScaleX(), getScaleY(), -getRotation());
	}
}
