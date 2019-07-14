package com.mygdx.game;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;



public class MainActivity extends AppCompatActivity
{
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler ();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable () {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility ( View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        setContentView ( R.layout.activity_main );
        mContentView = findViewById ( R.id.fullscreen_content );
        hide ();
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar ();
        if (actionBar != null) {
            actionBar.hide ();
        }
        mHideHandler.postDelayed ( mHidePart2Runnable, UI_ANIMATION_DELAY );
    }


    public void clickStartButton(View view) {
        Intent intent = new Intent ( this, AndroidLauncher.class );
        startActivity ( intent );
    }

    public void clickExitButton(View view) {
        finish ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
