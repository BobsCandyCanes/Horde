package com.Horde;

import com.badlogic.gdx.utils.Pool;

public final class DronePool
{	
	static Pool<Drone> pool = new Pool<Drone>()
	{
		@Override
		protected Drone newObject()
		{
			return new Drone();
		}
	};

	public static Drone obtain()
	{
		return pool.obtain();
	}
	
	public static void free(Drone d)
	{
		pool.free(d);
	}
	
	public static int getFree()
	{
		return pool.getFree();
	}
}
