package com.Horde;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Horde extends ApplicationAdapter
{
	static Stage stage;

	static ShapeRenderer shapeRenderer;

	static Player player;
	
	static Random random;
	
	static SpriteBatch batch;

	static OrthographicCamera camera = new OrthographicCamera();

	@Override
	public void create()
	{
		random = new Random();
		
		camera.setToOrtho(false, 1920, 1080);

		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		stage = new Stage();

		shapeRenderer = new ShapeRenderer();

		Gdx.input.setInputProcessor(stage);

		spawnPlayer();
		
		spawnQueen();
	}
	
	public static void spawnPlayer()
	{
		player = new Player(stage.getWidth() / 2 - 300, stage.getHeight() / 2);

		stage.addActor(player);
	}

	public static void spawnQueen()
	{	
		stage.addActor(new Queen(random.nextInt((int)stage.getWidth() / 2), random.nextInt((int)stage.getHeight() / 2)));	
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		pollInput();
		
		stage.getCamera().update();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public static void pollInput()
	{
		/*
		 * Some computers cannot register UP, LEFT, and SPACE at the same time
		 * Could be an issue with Windows or Lenovo keyboards
		 * In any case, WASD works fine
		 */

		if(player != null)
		{
			if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
			{
				player.moveForward();
			}

			if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
			{
				player.moveBackward();
			}

			if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
			{
				player.rotateBy(-2);
			}

			if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			{
				player.rotateBy(2);
			}

			if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
			{
				player.shoot();
			}

			if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			{
				player.dropBomb();
			}
		}
		else
		{
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
			{
				spawnPlayer();
			}
		}
	}

	@Override
	public void dispose()
	{
		// The garbage collector will not automatically destroy these
		// So I have to do it by hand
		
		batch.dispose();
		stage.dispose();
	}
}
