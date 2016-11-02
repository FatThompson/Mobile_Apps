package edu.ggc.matthew.morsecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String TAG="MorseCode Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void openAbout(View v) {
        Log.i(TAG, "Opening About");
        try {
            Log.i(TAG,"create intent");
            Intent i = new Intent(this, AboutActivity.class);
            Log.i(TAG,"Launch");
            startActivity(i);


        }catch (Exception e){
            Log.e(TAG, "Failed to open about");
            e.printStackTrace();
        }
    }

}
