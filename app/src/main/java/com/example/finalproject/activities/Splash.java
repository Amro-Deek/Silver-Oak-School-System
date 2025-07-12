package com.example.finalproject.activities; // replace with your actual package

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.deema.Login;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView schoolName = findViewById(R.id.schoolName);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        // Start animation slightly delayed
        new Handler().postDelayed(() -> {
            schoolName.setVisibility(TextView.VISIBLE);
            schoolName.startAnimation(bottomAnim);
        }, 500);

        // Transition to login screen
        new Handler().postDelayed(() -> {
            startActivity(new Intent(Splash.this, LoginActivity.class));
            finish();
        }, 2500);
    }
}
