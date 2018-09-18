package hu.ait.android.layoutinflaterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;
    @BindView(R.id.etTodoText)
    EditText etTodoText;

    @BindView(R.id.radioLow)
    RadioButton radioLow;
    @BindView(R.id.radioMedium)
    RadioButton radioMedium;
    @BindView(R.id.radioHigh)
    RadioButton radioHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSave)
    public void saveClick() {
        final View todoRow = getLayoutInflater().inflate(R.layout.todo_row, null);

        TextView tvTodoTitle = todoRow.findViewById(R.id.tvTodoTitle);
        tvTodoTitle.setText(etTodoText.getText().toString());
        TextView tvTodoDate = todoRow.findViewById(R.id.tvTodoDate);
        tvTodoDate.setText(new Date(System.currentTimeMillis()).toString());

        ImageView ivIcon = todoRow.findViewById(R.id.ivIcon);
        if (radioLow.isChecked()) {
            ivIcon.setImageResource(R.drawable.low);
        } else if (radioMedium.isChecked()) {
            ivIcon.setImageResource(R.drawable.medium);
        } else if (radioHigh.isChecked()) {
            ivIcon.setImageResource(R.drawable.high);
        }

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutContent.removeView(todoRow);
            }
        });


        layoutContent.addView(todoRow, 0);
    }
}
