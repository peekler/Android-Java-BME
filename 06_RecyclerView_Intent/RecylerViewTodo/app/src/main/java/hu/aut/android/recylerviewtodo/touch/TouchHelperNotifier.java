package hu.aut.android.recylerviewtodo.touch;


public interface TouchHelperNotifier {

    public void onItemDismissed(int position);

    public void onItemMoved(int fromPosition, int toPosition);
}
