package edu.ggc.matthew.colors;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

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

    private void SeekBarListener(SeekBar mySeekBar) {
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

    private void changeColorView(){

        final SeekBar alphaSeek = (SeekBar) findViewById(R.id.alphaSeekBar);
        final SeekBar redSeek = (SeekBar) findViewById(R.id.redSeekBar);
        final SeekBar greenSeek = (SeekBar) findViewById(R.id.greenSeekBar);
        final SeekBar blueSeek = (SeekBar) findViewById(R.id.blueSeekBar);

        //from here, gather the progress and convert the int val to 255 multiple
        //Then we can modify page from that.
        int alphaProgressShift = (int) Math.round(alphaSeek.getProgress()*SWAY);
        Log.i(TAG, "Alpha Progress Changed. It is at " + alphaProgressShift);
        int redProgressShift = (int) Math.round(redSeek.getProgress()*SWAY);
        Log.i(TAG, "Red Progress Changed. It is at " + redProgressShift);
        int greenProgressShift = (int) Math.round(greenSeek.getProgress()*SWAY);
        Log.i(TAG, "Green Progress Changed. It is at " + greenProgressShift);
        int blueProgressShift = (int) Math.round(blueSeek.getProgress()*SWAY);
        Log.i(TAG, "Blue Progress Changed. It is at " + blueProgressShift);

        //change the text of the text frame color
        final TextView colorDumpTV= (TextView) findViewById(R.id.colorDumpTextView);
        String allHex = convertAllToHex(
                alphaProgressShift,redProgressShift,
                greenProgressShift,blueProgressShift);
        colorDumpTV.setText(allHex);
        Log.i(TAG, "Color: " + allHex );

        //Check alpha if is to light
        final TextView alphaWarn = (TextView) findViewById(R.id.warnAlphaTooLow);
        if(alphaProgressShift<50) {
            alphaProgressShift = 255;
            alphaWarn.setVisibility(View.VISIBLE);
            Log.w(TAG,"WARN: Alpha too light, changing to FF");
            String alphaWarnText = getString(R.string.Alpha_Warn_Text);
            alphaWarnText = alphaWarnText + "\nReal Color Shown is: "+convertAllToHex(
                    alphaProgressShift,redProgressShift,
                    greenProgressShift,blueProgressShift);
            alphaWarn.setText(alphaWarnText);
        }
        else alphaWarn.setVisibility(View.INVISIBLE);

        colorDumpTV.setTextColor(Color.argb(alphaProgressShift,redProgressShift,greenProgressShift,blueProgressShift));



    }
    private String convertAllToHex(int avalue,int rvalue,int gvalue,int bvalue){
        String hex = "#" + convertToHex(avalue)+convertToHex(rvalue)+convertToHex(gvalue)+convertToHex(bvalue);
        return hex;
    }
    private String convertToHex(int value){
        String hex =  Integer.toHexString(value);
        if(value < 16) hex = "0"+hex;
        return hex;
    }
}
