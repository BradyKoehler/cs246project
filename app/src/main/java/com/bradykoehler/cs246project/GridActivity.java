package com.bradykoehler.cs246project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        // initialize toolbar
        Toolbar myToolbar = findViewById(R.id.gridToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void selectTile(View view) {
//        startActivity(new Intent(GridActivity.this, ActivityTileSel.class));
    }
}
