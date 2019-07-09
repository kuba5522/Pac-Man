package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


class Ghost extends Sprite {
    private int PositionX;
    private int PositionY;
    private int StartPositionX = 90;
    private int StartPositionY = 630;
    private boolean CollisionLeft;
    private boolean CollisionRight;
    private boolean CollisionTop;
    private boolean CollisionBottom;
    private int Direction;                  //1-> prawo, 2-> góra, 3-> lewo, 4-> dół

    Ghost() {

        CollisionLeft = false;
        CollisionRight = false;
        CollisionTop = false;
        CollisionBottom = false;
        Direction = 1;
        PositionX = StartPositionX;
        PositionY = StartPositionY;
        this.setTexture (  new Texture ( "ghost_yellow.png" ));
        this.setRegion ( 0, 0, 600,600);
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
    void Dispose(){ this.getTexture ().dispose (); }
    void ToStartPos(){PositionX = StartPositionX; PositionY = StartPositionY;}
}
