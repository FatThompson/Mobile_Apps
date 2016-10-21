package edu.ggc.matthew.colors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    final private String TAG = "Colors.MainActivity";

    //converts a number 0-100 to a 15^2 number
    final double SWAY = 2.55;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar alphaSeek = (SeekBar) findViewById(R.id.alphaSeekBar);
        final SeekBar redSeek = (SeekBar) findViewById(R.id.redSeekBar);
        final SeekBar greenSeek = (SeekBar) findViewById(R.id.greenSeekBar);
        final SeekBar blueSeek = (SeekBar) findViewById(R.id.blueSeekBar);
        //Add the seekbar listener
        SeekBarListener(alphaSeek);
        SeekBarListener(redSeek);
        SeekBarListener(greenSeek);
        SeekBarListener(blueSeek);
    }

    public void SeekBarListener(SeekBar mySeekBar) {
        //SWAY is a movement to a number = 15^2 sothat I can
        //MOD the result to a HEX value.
        //In theory
        //May be able to set by ARGB by 0-255
        //then pull the hex from the view

        //nvm change by the value and reset the box by all on any movement
        //Not effecient but it will work

        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Change the color of the view
                changeColorView();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void changeColorView(){

        final SeekBar alphaSeek = (SeekBar) findViewById(R.id.alphaSeekBar);
        final SeekBar redSeek = (SeekBar) findViewById(R.id.redSeekBar);
        final SeekBar greenSeek = (SeekBar) findViewById(R.id.greenSeekBar);
        final SeekBar blueSeek = (SeekBar) findViewById(R.id.blueSeekBar);
        //from here, gather the progress and convert the int val to 255 multiple
        //Then we can modify page from that.
        int alphaProgressShift = (int) Math.round(alphaSeek.getProgress()*SWAY);
        Log.i(TAG, "Progress Changed. It is at " + alphaProgressShift);
        int redProgressShift = (int) Math.round(redSeek.getProgress()*SWAY);
        Log.i(TAG, "Progress Changed. It is at " + redProgressShift);
        int greenProgressShift = (int) Math.round(greenSeek.getProgress()*SWAY);
        Log.i(TAG, "Progress Changed. It is at " + greenProgressShift);
        int blueProgressShift = (int) Math.round(blueSeek.getProgress()*SWAY);
        Log.i(TAG, "Progress Changed. It is at " + blueProgressShift);
    }
}
