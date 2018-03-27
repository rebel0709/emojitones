package com.emojitones.keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.emojitones.keyboard.fragments.InstallKeyboardFragment;

public class InstallKeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_installkeyboard);
        setSupportActionBar((Toolbar) findViewById(R.id.installkeyboard_toolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, InstallKeyboardFragment.newInstance()).commit();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                finish();
                break;
        }
        return true;
    }
}
