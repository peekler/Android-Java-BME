package hu.aut.android.recylerviewtodo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.aut.android.recylerviewtodo.R;
import hu.aut.android.recylerviewtodo.data.Todo;
import hu.aut.android.recylerviewtodo.touch.TouchHelperNotifier;

public class TodoRecylerAdapter extends
        RecyclerView.Adapter<TodoRecylerAdapter.ViewHolder> implements TouchHelperNotifier {

    private List<Todo> todoList = new ArrayList<Todo>();

    public TodoRecylerAdapter() {
        for (int i = 0; i < 20; i++) {
            todoList.add(new Todo("Todo "+i, false));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View todoRowView =
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.todo_row, viewGroup, false);
        return new ViewHolder(todoRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Todo todo = todoList.get(position);
        viewHolder.tvTitle.setText(todo.getTitle());
        viewHolder.cbDone.setChecked(todo.isDone());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addTodo(Todo todo) {
        todoList.add(0, todo);

        //notifyDataSetChanged();

        notifyItemInserted(0);
    }

    @Override
    public void onItemDismissed(int position) {
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todoList, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cbDone;
        public TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}
