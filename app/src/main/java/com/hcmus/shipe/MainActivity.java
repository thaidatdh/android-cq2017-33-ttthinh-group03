package com.hcmus.shipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.hcmus.Utils.*;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);

    }
}
