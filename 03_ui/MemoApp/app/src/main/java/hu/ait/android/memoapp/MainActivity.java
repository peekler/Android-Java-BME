package hu.ait.android.memoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        Button btnAdd = findViewById(R.id.btnAdd);
        final LinearLayout layoutContent = findViewById(R.id.layoutContent);
        final EditText etMemo = findViewById(R.id.etMemo);

        initAddButton(btnAdd, layoutContent, etMemo);
    }

    private void initAddButton(Button btnAdd, final LinearLayout layoutContent, final EditText etMemo) {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etMemo.getText())) {
                    handleItemAdd(etMemo, layoutContent);
                } else {
                    etMemo.setError(getString(R.string.error_empty));
                }
            }
        });
    }

    private void handleItemAdd(EditText etMemo, final LinearLayout layoutContent) {
        final View rowMemo = getLayoutInflater().inflate(
                R.layout.row_memo, null, false);

        TextView tvMemo = rowMemo.findViewById(R.id.tvMemo);
        tvMemo.setText(etMemo.getText());
        Button btnDelete = rowMemo.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutContent.removeView(rowMemo);
            }
        });
        layoutContent.addView(rowMemo);
    }
}
