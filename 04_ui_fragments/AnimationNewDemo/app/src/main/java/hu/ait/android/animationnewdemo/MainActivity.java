package hu.ait.android.animationnewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnAnim = findViewById(R.id.btnAnim);

        final Animation anim = AnimationUtils.loadAnimation(
                MainActivity.this,
                R.anim.demo_anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        final LinearLayout layoutContent = findViewById(R.id.layoutContent);

        btnAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAnim.startAnimation(anim);

                layoutContent.startAnimation(anim);
            }
        });

    }
}
