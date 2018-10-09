package hu.aut.android.recylerviewtodo.touch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.method.Touch;

public class TodoItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private TouchHelperNotifier touchHelperNotifier;

    public TodoItemTouchHelperCallback(TouchHelperNotifier touchHelperNotifier) {
        this.touchHelperNotifier = touchHelperNotifier;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        touchHelperNotifier.onItemMoved(
                viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        touchHelperNotifier.onItemDismissed(viewHolder.getAdapterPosition());
    }
}