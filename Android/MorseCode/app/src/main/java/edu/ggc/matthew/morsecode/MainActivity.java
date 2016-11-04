package edu.ggc.matthew.morsecode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final String TAG="MorseCode Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void openAbout(View view) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    protected void transmitCode(View view){
        Log.i(TAG,"Transmit Button Clicked.");
        TrasmitTask trasmitTask = new TrasmitTask(MainActivity.this);
        trasmitTask.execute();
    }



}