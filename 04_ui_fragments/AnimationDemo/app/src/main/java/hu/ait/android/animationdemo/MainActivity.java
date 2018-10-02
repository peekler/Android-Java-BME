package hu.ait.android.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout layoutContent = findViewById(R.id.layoutContent);
        final Button btnPlayAnim = findViewById(R.id.btnPlayAnim);
        final TextView tvMessage = findViewById(R.id.tvMessage);

        final Animation anim = AnimationUtils.loadAnimation(
                MainActivity.this,
                R.anim.myanim);
        final Animation sendAnim = AnimationUtils.loadAnimation(
                MainActivity.this,
                R.anim.send_anim);
        
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(MainActivity.this, "ANIM FINISHED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        

        btnPlayAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnPlayAnim.startAnimation(anim);
                //layoutContent.startAnimation(anim);
                tvMessage.startAnimation(sendAnim);
            }
        });
    }
}
