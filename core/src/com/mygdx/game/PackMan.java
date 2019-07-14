package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


class PackMan extends Sprite {
    private Texture textureOpen;
    private Texture textureClose;
    private int lives;
    private int PositionX;
    private int PositionY;
    private int StartPositionX = 630;
    private int StartPositionY = 630;
    private boolean CollisionLeft;
    private boolean CollisionRight;
    private boolean CollisionTop;
    private boolean CollisionBottom;
    private int Direction;                  //1-> prawo, 2-> góra, 3-> lewo, 4-> dół
    private int Points;
    private boolean Angry;



    PackMan(){
        textureOpen = new Texture ( "packman.png" );
        textureClose =  new Texture ( "packmanclose.png" );
        CollisionLeft = false;
        CollisionRight = false;
        CollisionTop = false;
        CollisionBottom = false;
        Direction = 1;
        lives = 3;
        Points = 0;
        ToStartPos ();
        PositionX = StartPositionX;
        PositionY = StartPositionY;
        Angry = false;
        setTexture (  new Texture ( "packman.png" ));
        setRegion ( 0, 0, 600,600);
        setColor(1, 1, 1, 1);
        setSize(600, 600);
        setOrigin(getWidth ()/2, getHeight ()/2);
        setScale ( 0.05f );

    }

    void SetPositionX(int positionX)
    {
        PositionX = positionX;
    }
    void SetPositionY(int positionY)
    {
        PositionY = positionY;
    }
    int GetPositionY(){
        return PositionY;
    }
    int GetPositionX(){
        return PositionX;
    }
    void CollisionRight(){
        CollisionRight = true;
    }
    void CollisionLeft(){
        CollisionLeft = true;
    }
    void CollisionTop(){
        CollisionTop = true;
    }
    void CollisionBottom(){
        CollisionBottom = true;
    }
    boolean GetCollisionRight(){ return CollisionRight; }
    boolean GetCollisionLeft(){ return CollisionLeft; }
    boolean GetCollisionTop(){ return CollisionTop; }
    boolean GetCollisionBottom(){ return CollisionBottom; }
    void SetDirection(int direction){Direction = direction;}
    int GetDirection(){ return Direction;}
    void ResetCollision(){ CollisionBottom = false;CollisionTop = false;CollisionLeft = false;CollisionRight =false;}
    void ClearPoints(){ Points = 0;}
    void AddPoint(){Points += 1; }
    int GetPoints(){ return Points;}
    void SetTextureClose(){ setTexture ( textureClose); }
    void SetTextureOpen(){ setTexture ( textureOpen );}
    void Dispose(){this.getTexture ().dispose ();}
    int GetCurrentLives(){return lives;}
    void DecreaseLives(){lives -= 1;}
    void ToStartPos(){PositionX = StartPositionX; PositionY = StartPositionY;}
    void SetAngryMode(){Angry = !Angry;}
    boolean GetAngryMode(){return Angry;}







}
