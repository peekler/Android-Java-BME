package hu.ait.android.uiviewdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;
    private Random rand = new Random(System.currentTimeMillis());
    private Timer timer = null;

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            pb.setProgress(rand.nextInt(1000));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.progressBar);
        pb.setMax(1000);
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setProgress(rand.nextInt(1000));
            }
        });

        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse(
                "android.resource://"+getPackageName()+"/"+R.raw.small);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 1000, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
