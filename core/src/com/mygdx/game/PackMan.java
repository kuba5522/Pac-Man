package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;


class PackMan {
     Texture image;
    private int lives;
    private int PositionX = 630;
    private int PositionY = 630;
    private boolean CollisionLeft;
    private boolean CollisionRight;
    private boolean CollisionTop;
    private boolean CollisionBottom;
    private int Direction;                  //1-> prawo, 2-> góra, 3-> lewo, 4-> dół
    private int Points;



    PackMan(){
        image = new Texture ( "packman.png" );
        CollisionLeft = false;
        CollisionRight = false;
        CollisionTop = false;
        CollisionBottom = false;
        Direction = 1;
        lives = 3;
        Points = 0;
    }


    void Dispose(){
        image.dispose ();
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
    Texture GetImage(){
        return image;
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





}
