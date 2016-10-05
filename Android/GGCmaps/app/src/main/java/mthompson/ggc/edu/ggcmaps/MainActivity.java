package mthompson.ggc.edu.ggcmaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void openMaps(View v) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    }
    protected void openCampus(View v) {
        startActivity(new Intent(MainActivity.this, GGCMapImageActivity.class));
    }
    protected void openAbout(View v) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }
}
