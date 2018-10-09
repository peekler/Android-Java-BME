package hu.aut.android.recylerviewtodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.aut.android.recylerviewtodo.adapter.TodoRecylerAdapter;
import hu.aut.android.recylerviewtodo.data.Todo;
import hu.aut.android.recylerviewtodo.touch.TodoItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {

    private TodoRecylerAdapter todoRecylerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etTodo = findViewById(R.id.etTodo);


        final RecyclerView recyclerView = findViewById(R.id.recylerView);
        todoRecylerAdapter = new TodoRecylerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(todoRecylerAdapter);

        ItemTouchHelper.Callback callback =
                new TodoItemTouchHelperCallback(todoRecylerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);



        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo(
                    etTodo.getText().toString(),
                    false
                );

                todoRecylerAdapter.addTodo(todo);

                recyclerView.scrollToPosition(0);
            }
        });
    }
}
