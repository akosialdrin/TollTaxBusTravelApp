package com.example.authentication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    //Variables

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        //Hooks

        image=findViewById(R.id.imageView);
        title=findViewById(R.id.textView);


        image.setAnimation(topAnim);
        title.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MenuActivity.class);

                Pair [] pairs = new Pair[2];
                pairs [0] = new Pair <View, String>(image,"logo_Image");
                pairs [1] = new Pair <View, String>(title,"logo_Text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                    startActivity(intent,options.toBundle());
                }

            }
        },SPLASH_SCREEN);

    }
}