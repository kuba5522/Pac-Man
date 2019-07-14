package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;



public class GdxGame extends ApplicationAdapter{
	private static final int Columns = 32;
	private static final int Rows = 18;
	private static Sprite[][] blocks = new Sprite[32][18];
	private static byte[][] level = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
							  		 {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
							  		 {0,1,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,0,1,0},
									 {0,1,0,0,1,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,0,0,1,0},
							 	 	 {0,1,1,1,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,1,0,0,1,0,1,1,1,1,0},
									 {0,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,0,0,0,1,0},
									 {0,1,0,0,0,0,1,0,1,1,1,0,0,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,0,1,0},
									 {0,1,1,1,1,0,1,0,0,1,0,0,0,1,1,1,0,1,0,1,0,1,0,1,0,0,1,0,0,0,1,0},
									 {0,0,1,0,1,1,1,1,0,1,1,1,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,1,1,1,0},
									 {0,0,1,0,1,1,1,1,0,1,1,1,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1,1,1,1,1,0},
									 {0,1,1,1,1,0,1,0,0,1,0,0,0,1,1,1,0,1,0,1,0,1,0,1,0,0,1,0,0,0,1,0},
									 {0,1,0,0,0,0,1,0,1,1,1,0,0,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,0,1,0},
									 {0,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,0,0,0,0,1,0},
									 {0,1,1,1,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,1,0,0,1,0,1,1,1,1,0},
									 {0,1,0,0,1,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1,0,0,1,0},
									 {0,1,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,0,1,0},
									 {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
							  		 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};


	private static SpriteBatch batch;
	private static final int Time_delay = 30;
	private static PackMan packMan;
	private static OrthographicCamera camera;
	private static float blockSize;
	private static float blockSize2;
	private static long lastSpawn = 0;
	private static int ax =0;
    private static int ay =0;
	private static State state = State.Running;
	private Ghost[] ghosts;
	private static BitmapFont font;
	private static Random random;
    private static long time = 0;
    private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private int PointsToCollect;
	@Override
	public void create() {
		packMan = new PackMan ();
		batch = new SpriteBatch ();
		camera = new OrthographicCamera ();
		camera.setToOrtho ( false );
		blockSize = camera.viewportWidth / Columns;
		blockSize2 = blockSize/2;
        packMan = new PackMan ( );

        ghosts = new Ghost[4];
        ghosts[0] = new Ghost (new Texture ( "ghost_yellow.png" ));
		ghosts[1] = new Ghost (new Texture ( "ghost_blue.png" ));
		ghosts[2] = new Ghost (new Texture ( "ghost_red.png" ) );
		ghosts[3] = new Ghost (new Texture ( "ghost_green.png" ) );
		PointsToCollect = Points();
		SetStartGhostsPos(ghosts);
		random = new Random();
		packMan.SetTextureOpen ();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("PressStart.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter ();
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!:/";
		parameter.size = 15;
		state = State.Running;
		font = generator.generateFont(parameter);
		for (int i = 0; i < Columns; i++) {
			for (int j = 0; j < Rows; j++) {
				if (level[j][i] == 0)
					blocks[i][j] = new Sprite ( new Texture ( "wall.png" ) );
				else
					blocks[i][j] = new Sprite ( new Texture ( "point.png" ) );

			}
		}
	}

	private int Points() {
		int sum = 0;
		for (int i =0; i < 32 ; i++)
		{
			for (int j = 0; j < 18; j++)
			{
				if(level[j][i] == 1 )
					sum += 1;
			}
		}
		return sum;
	}

	@Override
	public void render() {
		super.render();
        long now = TimeUtils.millis ();
		if(State.Paused == state) {
			time = now;
			SetStartGhostsPos ( ghosts );
            if(packMan.GetCurrentLives () == 0)
                state = State.EndGame;
            else
                state = State.Running;
		}
		if(State.EndGame == state)
		{
			batch.begin ();
			drawGame ();
			font.draw ( batch, "GAME OVER", (camera.viewportWidth/2)*0.9f, camera.viewportHeight/2 );
			batch.end ();
            if(time + 3000 < now)
                Gdx.app.exit ();

		}
		if(State.Win == state)
		{
			batch.begin ();
			drawGame ();
			font.draw ( batch, "YOU WIN", (camera.viewportWidth/2)*0.9f, camera.viewportHeight/2 );
			batch.end ();
			if(time + 3000 < now)
				Gdx.app.exit ();
			return;
		}
		if(packMan.GetPoints () == PointsToCollect ) {
			time = now;
			state = State.Win;
		}
		if(state == State.Running ) {
			if (time + 3000 < now) {
				int accelX = ( int ) Gdx.input.getAccelerometerX ();
				int accelY = ( int ) Gdx.input.getAccelerometerY ();


				Gdx.gl.glClearColor ( 0, 0, 0, 1 );
				Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

				batch.setProjectionMatrix ( camera.combined );

				batch.begin ();
				drawBoard ( blocks, Columns, Rows, blockSize, batch );


				if (packMan.GetPositionX () % blockSize == 0 && packMan.GetPositionY () % blockSize == 0) {
					ax = accelX;
					ay = accelY;
					packMan.SetTextureClose ();
					CollectPoint ();
					for (Ghost ghost : ghosts)
						CheckGhostCollision ( ghost );
					packMan.ResetCollision ();
					CheckCollisionPackman ( packMan );
					MoveWithDirectionChange ( accelX, accelY );

				}

				if (ghosts[0].GetPositionX () % blockSize == 0 && ghosts[0].GetPositionY () % blockSize == 0) {
					for (Ghost ghost : ghosts) {
						ghost.ResetCollision ();
						CheckCollisionGhost ( ghost );
						MoveGhostWithDirectionChange ( ghost, packMan.GetPositionX (), packMan.GetPositionY () );
					}

				}
				for (Ghost ghost : ghosts) {
					ghost.setCenter ( ghost.GetPositionX () + blockSize2, ghost.GetPositionY () + blockSize2 );
					ghost.draw ( batch );
				}
				packMan.setCenter ( packMan.GetPositionX () + blockSize2, packMan.GetPositionY () + blockSize2 );


				packMan.draw ( batch );


				if (now - lastSpawn > Time_delay) {
					MovePackman ( packMan, ax, ay );
					for (Ghost ghost : ghosts) {
						MoveGhost ( ghost );
						ghost.draw ( batch );
					}
					packMan.draw ( batch );
					packMan.SetTextureOpen ();
					lastSpawn = now;
				}
				font.draw ( batch, packMan.GetPoints () + "/" + PointsToCollect, camera.viewportWidth * 0.9f, camera.viewportHeight * 0.98f );
				font.draw ( batch, "Current lives: " + packMan.GetCurrentLives (), 10, camera.viewportHeight * 0.98f );
				batch.end ();
			} else {

				batch.begin ();
				drawGame ();
				font.draw ( batch, (3000 - (now - time)) / 1000 + "", camera.viewportWidth / 2, camera.viewportHeight / 2 );
				batch.end ();
			}
		}

	}

    private void drawGame() {
        Gdx.gl.glClearColor ( 0, 0, 0, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );
        drawBoard ( blocks, Columns, Rows, blockSize, batch );
        packMan.draw ( batch );
        for (Ghost ghost: ghosts)
            ghost.draw ( batch );
        font.draw ( batch, packMan.GetPoints () + "/"+120, camera.viewportWidth*0.9f, camera.viewportHeight*0.98f );
        font.draw ( batch,"Current lives: "+packMan.GetCurrentLives (), 10,camera.viewportHeight*0.98f  );

    }

    private void CheckGhostCollision(Ghost ghost) {
            if (packMan.GetPositionX () == ghost.GetPositionX () && packMan.GetPositionY () == ghost.GetPositionY ()) {
                packMan.DecreaseLives ();
                packMan.ToStartPos ();
                ghost.ToStartPos ();
                state = State.Paused;
            }
	}

	private void MoveGhost(Ghost ghost) {
		if(ghost.GetDirection () == 1){
			ghost.SetPositionX ( ghost.GetPositionX () + 5 );
		}
		else if(ghost.GetDirection () == 2)
		{
			ghost.SetPositionY ( ghost.GetPositionY () + 5 );
		}
		else if(ghost.GetDirection () == 3){
			ghost.SetPositionX ( ghost.GetPositionX () - 5 );
		}
		else{
			ghost.SetPositionY ( ghost.GetPositionY () - 5 );
		}
	}

	private void MoveGhostWithDirectionChange(Ghost ghost, int x, int y) {
		int GhostX = ghost.GetPositionX ();
		int GhostY = ghost.GetPositionY ();
		if(Math.abs ( GhostY-y) > Math.abs ( GhostX - x  )) {
			if (GhostY > y) {                        //jest u gory
				if (!ghost.GetCollisionBottom ()) {
					ghost.SetDirection ( 4 );
				} else if (!ghost.GetCollisionLeft () || !ghost.GetCollisionRight ()) {
					if (!ghost.GetCollisionLeft () && !ghost.GetCollisionRight ()) {
						if (random.nextBoolean ()) {
							ghost.SetDirection ( 1 );                            //prawo 1
						} else {
							ghost.SetDirection ( 3 );
						}                                                            //lewo 3
					} else if (!ghost.GetCollisionLeft ()) {
						ghost.SetDirection ( 3 );
					} else {
						ghost.SetDirection ( 1 );
					}
				} else {
					ghost.SetDirection ( 2 );
				}

			}
			else {                            //jest na dole
				if (!ghost.GetCollisionTop ()) {
					ghost.SetDirection ( 2 );
				} else if (!ghost.GetCollisionLeft () || !ghost.GetCollisionRight ()) {
					if (!ghost.GetCollisionLeft () && !ghost.GetCollisionRight ()) {
						if (random.nextBoolean ()) {
							ghost.SetDirection ( 1 );
						} else {
							ghost.SetDirection ( 3 );
						}
					} else if (!ghost.GetCollisionLeft ()) {
						ghost.SetDirection ( 3 );
					} else {
						ghost.SetDirection ( 1 );
					}
				} else {
					ghost.SetDirection ( 4 );
				}
			}
		}
		else {
			if (GhostX > x)                    //jest na prawo
			{
				if (!ghost.GetCollisionLeft ()) {
					ghost.SetDirection ( 3 );
				} else if (!ghost.GetCollisionTop () || !ghost.GetCollisionBottom ()) {
					if (!ghost.GetCollisionTop () && !ghost.GetCollisionBottom ()) {
						if (random.nextBoolean ()) {
							ghost.SetDirection ( 2 );
						} else {
							ghost.SetDirection ( 4 );
						}
					} else if (!ghost.GetCollisionTop ()) {
						ghost.SetDirection ( 2 );
					} else {
						ghost.SetDirection ( 4 );
					}
				} else {
					ghost.SetDirection ( 1 );
				}
			}
			else  {                                //jest na lewo
				if (!ghost.GetCollisionRight ()) {
					ghost.SetDirection ( 1 );
				} else if (!ghost.GetCollisionTop () || !ghost.GetCollisionBottom ()) {
					if (!ghost.GetCollisionTop () && !ghost.GetCollisionBottom ()) {
						if (random.nextBoolean ()) {
							ghost.SetDirection ( 2 );
						} else {
							ghost.SetDirection ( 4 );
						}
					} else if (!ghost.GetCollisionTop ()) {
						ghost.SetDirection ( 2 );
					} else {
						ghost.SetDirection ( 4 );
					}
				} else {
					ghost.SetDirection ( 3 );
				}
			}
		}
	}

	private void CollectPoint() {
		int x = Math.round ( packMan.GetPositionX ()/blockSize );
		int y =Math.round( packMan.GetPositionY ()/blockSize);

		if(level[17-(17-y)][x] == 1)
		{
			level[17-(17-y)][x] = 2;
			packMan.AddPoint ();
			blocks[x][y].setColor ( 0,0,0,1 );
		}
		if(level[17-(17-y)][x] == 3)
		{
			packMan.SetAngryMode ();
			packMan.AddPoint ();
			blocks[x][y].setColor ( 0,0,0,1 );
		}

	}

	private void MoveWithDirectionChange(int accelX, int accelY) {
		int max1 = Math.abs ( accelX );
		int max2 = Math.abs ( accelY );
		if(max1 > max2){
			if(accelX > 0)
				packMan.setRotation ( -90 );
			else
				packMan.setRotation ( 90 );
		}
		else{
			if(accelY > 0)
				packMan.setRotation ( 0 );
			else
				packMan.setRotation ( 180 );

		}

	}

	private void MovePackman(PackMan packMan, int accelX, int accelY) {
		int max1 = Math.abs ( accelX );
		int max2 = Math.abs ( accelY );
		if(max1 > max2)
		{
			if(accelX > 0 && !packMan.GetCollisionBottom () )
				packMan.SetPositionY ( packMan.GetPositionY () - 5 );
			if(accelX <= 0 && !packMan.GetCollisionTop ())
				packMan.SetPositionY ( packMan.GetPositionY () + 5 );
		}
		else{
			if(accelY > 0 && !packMan.GetCollisionRight ()) {
				packMan.SetPositionX ( packMan.GetPositionX () + 5 );

			}
			if(accelY <= 0 && !packMan.GetCollisionLeft ())
				packMan.SetPositionX ( packMan.GetPositionX ()-5 );
		}

	}




	private void CheckCollisionPackman(PackMan packMan) {
        int x = Math.round ( packMan.GetPositionX ()/blockSize );
        int y =Math.round( packMan.GetPositionY ()/blockSize);

		if ( level[17-(17-y)][x+1] == 0)
			packMan.CollisionRight ();
		if ( level[17-(17-y)][x-1] == 0)
			packMan.CollisionLeft ();
		if ( level[17-(16-y)][x] == 0)
			packMan.CollisionTop ();
		if ( level[17-(18-y)][x] == 0)				//8  na 15 // 17 na 31
			packMan.CollisionBottom ();
	}



    private void CheckCollisionGhost(Ghost ghost) {
        int x = Math.round ( ghost.GetPositionX ()/blockSize );
        int y = Math.round( ghost.GetPositionY ()/blockSize);

        if ( level[17-(17-y)][x+1] == 0)
            ghost.CollisionRight ();
        if ( level[17-(17-y)][x-1] == 0)
			ghost.CollisionLeft ();
        if ( level[17-(16-y)][x] == 0)
			ghost.CollisionTop ();
        if ( level[17-(18-y)][x] == 0)				//8  na 15 // 17 na 31
			ghost.CollisionBottom ();
    }

	@Override
	public void dispose() {
	    super.dispose ();
	    batch.dispose ();
	    batch = null;
        for (Ghost ghost: ghosts) {
            ghost.Dispose ();
            ghosts = null;
        }
		System.gc ();
		font.dispose ();
		font = null;
		packMan.Dispose ();
		packMan = null;
		generator.dispose ();
		generator = null;
		parameter = null;
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


	private void SetStartGhostsPos(Ghost[] ghosts) {
		int x = (int)blockSize;
		ghosts[0].SetPositionX ( x );
		ghosts[0].SetPositionY ( x );

		ghosts[1].SetPositionX ( x );
		ghosts[1].SetPositionY ( ((Rows-2)*x)) ;

		ghosts[2].SetPositionX ( ((Columns-2)*x) );
		ghosts[2].SetPositionY ( ((Rows-2)*x));

		ghosts[3].SetPositionX ( ((Columns-2)*x) );
		ghosts[3].SetPositionY ( x );
	}
	public enum State{
		Running, Paused, EndGame, Win
	}

}

