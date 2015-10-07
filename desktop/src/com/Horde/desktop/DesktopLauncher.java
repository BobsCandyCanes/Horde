package com.Horde.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Horde.Horde;

public class DesktopLauncher 
{
	public static void main (String[] args) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Horde";
		config.width = 1920;
		config.height = 1000;
		new LwjglApplication(new Horde(), config);
	}
}