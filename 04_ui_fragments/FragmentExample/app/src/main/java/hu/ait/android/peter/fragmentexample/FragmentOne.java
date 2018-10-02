package hu.ait.android.peter.fragmentexample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Peter on 2015.03.03..
 */
public class FragmentOne extends Fragment {

    public static final String TAG = "FragmentOne";

    public interface LoginNextHandler{
        public void loginNextPressed(String name);
    }

    private LoginNextHandler loginNextHandler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            loginNextHandler = (LoginNextHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("This activity does " +
                    "not support the LoginNextHandler interface!");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, null);

        final EditText etName = (EditText) rootView.findViewById(R.id.etName);
        Button btnNext = (Button) rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginNextHandler.loginNextPressed(
                        etName.getText().toString());
            }
        });


        return rootView;
    }
}
