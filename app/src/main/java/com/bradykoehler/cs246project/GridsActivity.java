package com.bradykoehler.cs246project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * This activity displays a list of all the current user's grids
 */
public class GridsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("StaticFieldLeak")
    public static RecyclerView recyclerView;

    /**
     * Initialize settings for the activity
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the correct theme
        ThemeManager.setTheme(this);

        // Call override function
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grids);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up floating action button
        initializeFab();

        // Set up action bar drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Set up action bar toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set up navigation listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Grids list view
        recyclerView = findViewById(R.id.grids);
        recyclerView.setHasFixedSize(true);

        // Set up linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Load list of grids
        AcaApi.getGrids(this);
    }

    /**
     * Refresh activity data when resumed
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Load list of grids
        AcaApi.getGrids(this);
    }

    /**
     * Set up floating action button
     */
    private void initializeFab() {

        // Store reference to activity
        final GridsActivity gridsActivity = this;

        // Find button by id
        FloatingActionButton fab = findViewById(R.id.fab);

        // Add onClick listener for adding a new grid
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("New Grid Name");

                // Set up the input
                final EditText input = new EditText(view.getContext());

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up create button
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get grid name
                        String name = input.getText().toString();

                        // Submit new grid data
                        AcaApi.createGrid(gridsActivity, name);
                    }
                });

                // Set up cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Close dialog
                        dialog.cancel();
                    }
                });

                // Display dialog
                builder.show();
            }
        });
    }

    /**
     * Load list of grids into the adapter
     * @param newGridsList
     */
    public void loadGrids(Grid[] newGridsList) {
        RecyclerView.Adapter mAdapter = new GridsAdapter(newGridsList);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Handle a new grid being created
     * @param grid new grid data
     */
    public void addGrid(Grid grid) {
        // Create intent
        Intent intent = new Intent(this, GridActivity.class);

        // Store grid data
        Bundle extras = new Bundle();
        extras.putInt("gridId", grid.getId());
        extras.putString("gridName", grid.getName());

        // Add data to intent
        intent.putExtras(extras);

        // Move to grid view
        startActivity(intent);
    }

    /**
     * Handle back button being pressed based on drawer status
     */
    @Override
    public void onBackPressed() {

        // Find drawer layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Close drawer if open
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        // Close activity if drawer is closed
        else {
            super.onBackPressed();
        }
    }

    /**
     * Handle creation of menu options
     * @param menu action bar menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_main, menu);

        return true;
    }

    /**
     * Handle selection of menu items
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Get id of selected item
        int id = item.getItemId();

        // SettingsActivity pressed
        if (id == R.id.action_settings) {

            // Move to settings activity
            startActivity(new Intent(GridsActivity.this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle navigation item selection
     * @param item selected item
     * @return status
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Get id of clicked item
        int id = item.getItemId();

        // Send Request button selected
        if (id == R.id.nav_request) {

            // Create dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New Image Request");

            // Set up the text input
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Please enter your request here");

            // Add input to dialog
            builder.setView(input);

            // Caller activity
            final GridsActivity activity = this;

            // Initialize send button
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    Log.d("GridsActivity", m_Text);
                    AcaApi.createRequest(activity, m_Text);
                }
            });

            // Initialize cancel button
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Display dialog
            builder.show();
        }

        // Close navigation drawer after selection
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
