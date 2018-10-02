package hu.bme.android.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import hu.bme.android.fragmentdemo.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(LoginFragment.TAG);
    }

    private void showFragment(String tag) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.layoutContent,
                new LoginFragment(),tag);
        transaction.addToBackStack(null);

        transaction.commit();
    }

}
