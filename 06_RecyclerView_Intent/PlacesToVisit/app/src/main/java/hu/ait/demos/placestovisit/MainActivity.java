package hu.ait.demos.placestovisit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import hu.ait.demos.placestovisit.adapter.PlacesAdapter;
import hu.ait.demos.placestovisit.data.AppDatabase;
import hu.ait.demos.placestovisit.data.Place;
import hu.ait.demos.placestovisit.touch.PlacesListTouchHelperCallback;


public class MainActivity extends AppCompatActivity implements CreateAndEditPlaceDialog.PlaceHandler {

    public static final String KEY_EDIT = "KEY_EDIT";
    private PlacesAdapter placesAdapter;
    private CoordinatorLayout layoutContent;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreatePlaceDialog();
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.action_add:
                                showCreatePlaceDialog();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_about:
                                showSnackBarMessage(getString(R.string.txt_about));
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_help:
                                showSnackBarMessage(getString(R.string.txt_help));
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }

                        return false;
                    }
                });

        setUpToolBar();

        RecyclerView recyclerViewPlaces = (RecyclerView) findViewById(
                R.id.recyclerViewPlaces);
        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(this));

        initPlaces(recyclerViewPlaces);
    }

    private void initPlaces(final RecyclerView recyclerView) {
        new Thread(){
            @Override
            public void run() {
                final List<Place> places =
                        AppDatabase.getAppDatabase(MainActivity.this).placeDao().getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        placesAdapter = new PlacesAdapter(places, MainActivity.this);
                        recyclerView.setAdapter(placesAdapter);

                        PlacesListTouchHelperCallback touchHelperCallback = new PlacesListTouchHelperCallback(
                                placesAdapter);
                        ItemTouchHelper touchHelper = new ItemTouchHelper(
                                touchHelperCallback);
                        touchHelper.attachToRecyclerView(recyclerView);
                    }
                });
            }
        }.start();
    }


    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void showCreatePlaceDialog() {
        new CreateAndEditPlaceDialog().show(getSupportFragmentManager(), "CreateAndEditPlaceDialog");
    }

    public void showEditPlaceDialog(Place place) {
        CreateAndEditPlaceDialog editPlaceDialog = new CreateAndEditPlaceDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EDIT, place);
        editPlaceDialog.setArguments(bundle);

        editPlaceDialog.show(getSupportFragmentManager(), "EditDialog");
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(layoutContent,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showCreatePlaceDialog();
                return true;
            default:
                showCreatePlaceDialog();
                return true;
        }
    }


    @Override
    public void onNewPlaceCreated(final Place place) {
        new Thread(){
            @Override
            public void run() {
               long id = AppDatabase.getAppDatabase(MainActivity.this).
                        placeDao().insertTodo(place);
                place.setPlaceID(id);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        placesAdapter.addPlace(place);
                        showSnackBarMessage(getString(R.string.txt_place_added));
                    }
                });
            }
        }.start();
    }

    @Override
    public void onPlaceUpdated(final Place place) {
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(MainActivity.this).placeDao().update(place);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        placesAdapter.updatePlace(place);
                    }
                });
            }
        }.start();
    }


}

