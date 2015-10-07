package com.Horde;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// Stores textures all in one place, rather than keeping an instance in each object
// To get a texture, just call getTexture() statically

public class TextureLibrary 
{
	static TextureAtlas spritesheet;
	
	public static TextureRegion getTexture(String name)
	{
		if(spritesheet == null)
		{
			initSpritesheet();
		}
		
		return spritesheet.findRegion(name);
	}
	
	public static void initSpritesheet()
	{
		spritesheet = new TextureAtlas(Gdx.files.internal("spritesheet.pack"));
	}
}
