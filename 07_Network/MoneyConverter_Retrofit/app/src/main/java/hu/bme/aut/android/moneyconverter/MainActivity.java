package hu.bme.aut.android.moneyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import hu.bme.aut.android.moneyconverter.data.MoneyResult;
import hu.bme.aut.android.moneyconverter.network.MoneyAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private final String URL_BASE =
            "https://api.exchangeratesapi.io";

    private Button btnGetRates;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MoneyAPI moneyAPI = retrofit.create(MoneyAPI.class);


        tvResult = findViewById(R.id.tvResult);

        btnGetRates = findViewById(R.id.btnGetRates);

        btnGetRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moneyAPI.getRates("USD").enqueue(new Callback<MoneyResult>() {
                    @Override
                    public void onResponse(Call<MoneyResult> call, Response<MoneyResult> response) {
                        String data =
                                ""+response.body().getRates().gethUF()+"\n"+
                                        response.body().getRates().geteUR();
                        tvResult.setText(data);
                    }

                    @Override
                    public void onFailure(Call<MoneyResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: "+
                                t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}
