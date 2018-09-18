package hu.aut.android.helloandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupUI();

    }

    private void setupUI() {
        final TextView tvTime = findViewById(R.id.tvTime);
        Button btnPress = findViewById(R.id.btnPress);
        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = new Date(System.currentTimeMillis()).toString();

                tvTime.setText(time);

                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSecond = new Intent();
                intentSecond.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intentSecond);
            }
        });
    }
}
