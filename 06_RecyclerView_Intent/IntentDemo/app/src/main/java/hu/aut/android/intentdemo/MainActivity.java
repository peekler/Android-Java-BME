package hu.aut.android.intentdemo;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void intentClick(View view) {
        //intentSearch();

        //phoneCall();
        //intentSend();

        intentWaze();
    }

    private void intentSearch() {
        Intent intentSearch = new Intent(Intent.ACTION_WEB_SEARCH);
        intentSearch.putExtra(SearchManager.QUERY, "Balaton");
        try {
            startActivity(intentSearch);
        }catch (ActivityNotFoundException ae) {
            ae.printStackTrace();
        }
    }

    private void phoneCall() {
        Intent intentCall = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:+3612312312"));
        startActivity(intentCall);
    }

    private void intentSend() {
        Intent intentSend = new Intent(Intent.ACTION_SEND);
        intentSend.setType("text/plain");
        //intentSend.setPackage("com.facebook.katana");
        intentSend.putExtra(Intent.EXTRA_TEXT, "Android share demo");
        startActivity(intentSend);
        //startActivity(Intent.createChooser(intentSend,
        //        "Select share app"));
    }

    private void intentWaze() {
        String wazeUri = "waze://?favorite=Home&navigate=yes";
        //String wazeUri = "waze://?ll=40.761043, -73.980545&navigate=yes";


        Intent intentTest = new Intent(Intent.ACTION_VIEW);
        intentTest.setData(Uri.parse(wazeUri));
        startActivity(intentTest);
    }
}
