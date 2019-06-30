package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import javax.swing.text.View;


public class GdxGame extends ApplicationAdapter {
	private final int Columns = 16;
	private final int Rows = 9;
	private Sprite[][] blocks = new Sprite[Columns][Rows];
	private byte[][] level = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							  {0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0},
							  {0,1,0,1,1,0,1,1,0,1,0,0,1,0,1,0},
							  {0,1,0,1,1,0,1,1,0,1,0,0,1,0,1,0},
							  {0,1,0,0,0,0,0,1,1,1,1,1,1,1,1,0},
							  {0,1,1,1,1,1,1,1,0,1,0,1,0,0,1,0},
							  {0,1,0,0,0,0,0,1,0,1,0,1,0,0,1,0},
							  {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
							  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};


	private SpriteBatch batch;
	private final int Time_delay = 10;
	private PackMan packMan;
	private Point point;
	private OrthographicCamera camera;
	private float blockSize;
	private long lastSpawn = 0;
	private int ax =0;
    private int ay =0;
	private Sprite Player;
	@Override
	public void create() {
		packMan = new PackMan ();
		point = new Point ();
		batch = new SpriteBatch ();
		camera = new OrthographicCamera ();
		camera.setToOrtho ( false );
		blockSize = camera.viewportWidth / Columns;
		for (int i = 0; i < Columns; i++) {
			for (int j = 0; j < Rows; j++) {
                    if (level[j][i] == 0)
                        blocks[i][j] = new Sprite (  new Texture ( "wall.png" ) );
                    if(level[j][i] == 1)
                    	blocks[i][j] = new Sprite ( point.image );
				}
			}

		Player = new Sprite ( packMan.GetImage () );
		Player.setScale ( 0.1f );
		}


	@Override
	public void render() {
		super.render();
		long now = TimeUtils.millis ();
		int accelX = (int)Gdx.input.getAccelerometerX();
		int accelY = (int)Gdx.input.getAccelerometerY();
		BitmapFont font = new BitmapFont ();

		Gdx.gl.glClearColor ( 0, 0, 0, 1 );
		Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

		batch.setProjectionMatrix ( camera.combined );

		batch.begin ();
		drawBoard(blocks,Columns, Rows, blockSize, batch);


		if(packMan.GetPositionX () % 90 == 0 && packMan.GetPositionY () % 90 == 0){
			ax = accelX;
			ay = accelY;
			CollectPoint();
			packMan.ResetCollision ();
			CheckCollision(packMan);
            MoveWithDirectionChange ( accelX, accelY );
		}

		Player.setCenter (  packMan.GetPositionX ()+45, packMan.GetPositionY ()+45 );
		Player.draw ( batch );
            if (now - lastSpawn > Time_delay) {
                Move(packMan, ax, ay);
				Player.draw ( batch );
                lastSpawn = now;
            }

		font.draw(batch, packMan.GetPositionX ()+ " " +packMan.GetPositionY (), 300, 100);
		font.draw ( batch, "bot: "+packMan.GetCollisionBottom ()+" top: "+packMan.GetCollisionTop ()+" rig: "+packMan.GetCollisionRight ()+" left: "+packMan.GetCollisionLeft (), 300, 200 );
		font.draw ( batch, packMan.GetDirection ()+" ", 200, 200 );
		font.draw ( batch, packMan.GetPositionX ()/90+" "+ packMan.GetPositionY ()/90, 500, 100 );
		font.draw ( batch, packMan.GetPoints ()+" ", 600, 600 );
		batch.end ();
	}

	private void CollectPoint() {
		int x = packMan.GetPositionX ()/90;
		int y = packMan.GetPositionY ()/90;

		if(level[8-(8-y)][x] == 1)
		{
			level[8-(8-y)][x] = 2;
			packMan.AddPoint ();
			blocks[x][y].setColor ( 0,0,0,1 );
		}

	}

	private void MoveWithDirectionChange(int accelX, int accelY) {
		int max1 = Math.abs ( accelX );
		int max2 = Math.abs ( accelY );
		if(max1 > max2){
			if(accelX > 0)
				Player.setRotation ( -90 );
			else
				Player.setRotation ( 90 );
		}
		else{
			if(accelY > 0)
				Player.setRotation ( 0 );
			else
				Player.setRotation ( 180 );

		}

	}

	private void Move(PackMan packMan, int accelX, int accelY) {
		int max1 = Math.abs ( accelX );
		int max2 = Math.abs ( accelY );
		if(max1 > max2)
		{
			if(accelX > 0 && !packMan.GetCollisionBottom () )
				packMan.SetPositionY ( packMan.GetPositionY () - 10 );
			if(accelX <= 0 && !packMan.GetCollisionTop ())
				packMan.SetPositionY ( packMan.GetPositionY () + 10 );
		}
		else{
			if(accelY > 0 && !packMan.GetCollisionRight ()) {
				packMan.SetPositionX ( packMan.GetPositionX () + 10 );

			}
			if(accelY <= 0 && !packMan.GetCollisionLeft ())
				packMan.SetPositionX ( packMan.GetPositionX ()-10 );
		}

	}

	private void CheckCollision(PackMan packMan) {
		int x = packMan.GetPositionX ()/90;
		int y = packMan.GetPositionY ()/90;

		if ( level[8-(8-y)][x+1] == 0)
			packMan.CollisionRight ();
		if ( level[8-(8-y)][x-1] == 0)
			packMan.CollisionLeft ();
		if ( level[8-(7-y)][x] == 0)
			packMan.CollisionTop ();
		if ( level[8-(9-y)][x] == 0)				//8  na 15
			packMan.CollisionBottom ();

		/*for (int i = 0; i <columns; i++) {
			for (int j = 0; j < rows; j++) {
				try {
					if (level[j][i] == 0 ) {
						if ((blocks[i][j].getX () == packMan.GetPositionX () + 90 && blocks[i][j].getY () == packMan.GetPositionY ()))
							packMan.CollisionRight ();
						if ((blocks[i][j].getX () == packMan.GetPositionX () - 90 && blocks[i][j].getY () == packMan.GetPositionY ()))
							packMan.CollisionLeft ();
						if ((blocks[i][j].getX () == packMan.GetPositionX () && blocks[i][j].getY () == packMan.GetPositionY () + 90))
							packMan.CollisionTop ();
						if ((blocks[i][j].getX () == packMan.GetPositionX () && blocks[i][j].getY () == packMan.GetPositionY () - 90))
							packMan.CollisionBottom ();
					}
				} catch (Exception ignored) { }
			}

		}*/
	}


	@Override
	public void dispose() {
		batch.dispose ();
		packMan.Dispose ();



	}


	private void drawBoard(Sprite[][] blocks, int columns, int rows, float blockSize, SpriteBatch batch) {
		for (int j = 0; j < columns; j++) {
			for (int i = 0; i < rows; i++) {
				try {
					blocks[j][i].setBounds ( j * blockSize, i * blockSize, blockSize, blockSize );
					blocks[j][i].draw ( batch );
				} catch (Exception ignored) {
				}
			}
		}
	}
}

