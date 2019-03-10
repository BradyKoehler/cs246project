package com.bradykoehler.cs246project;

import android.content.Intent;
import android.view.View;


public class MyOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), GridActivity.class));
    }
}
