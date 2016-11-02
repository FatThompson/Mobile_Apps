package edu.ggc.matthew.colors;

import android.content.Intent;
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

    /**
     * The main logic of the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Run the text change
        changeColorView();

        //Gather seekBars
        final SeekBar alphaSeek = (SeekBar) findViewById(R.id.alphaSeekBar);
        final SeekBar redSeek = (SeekBar) findViewById(R.id.redSeekBar);
        final SeekBar greenSeek = (SeekBar) findViewById(R.id.greenSeekBar);
        final SeekBar blueSeek = (SeekBar) findViewById(R.id.blueSeekBar);

        //Add the seekBar listener
        SeekBarListener(alphaSeek);
        SeekBarListener(redSeek);
        SeekBarListener(greenSeek);
        SeekBarListener(blueSeek);

        //Change the background Alpha
        final View activity_main_background = findViewById(R.id.activity_main);
        activity_main_background.getBackground().setAlpha(120);
    }

    /**
     * Opens the about activity.
     * Assigned the button by the XML
     * @param v
     */
    protected void openAbout(View v) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    /**
     * Assigns the listener to the seekBars more
     * generically
     * @param mySeekBar
     */
    private void SeekBarListener(SeekBar mySeekBar) {
        //add the listener generically
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Unused
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColorView();
            }

            /**
             * small popup for the user
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                final TextView alphaWarn = (TextView) findViewById(R.id.warnAlphaTooLow);
//                alphaWarn.setText(R.string.Alpha_Warn_Text);
//                alphaWarn.setVisibility(View.VISIBLE);
            }

            /**
             * change the values then get ride of pop up
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                changeColorView();
//                final TextView alphaWarn = (TextView) findViewById(R.id.warnAlphaTooLow);
//                alphaWarn.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * This is the logic behind the changing the textview for the
     * display and change the alpha of the background
     * (Based on the image in document)
     */
    private void changeColorView() {
        //gather the seek bars
        final SeekBar alphaSeek = (SeekBar) findViewById(R.id.alphaSeekBar);
        final SeekBar redSeek = (SeekBar) findViewById(R.id.redSeekBar);
        final SeekBar greenSeek = (SeekBar) findViewById(R.id.greenSeekBar);
        final SeekBar blueSeek = (SeekBar) findViewById(R.id.blueSeekBar);

        //from here, gather the progress and convert the int val to 255 multiple
        //I posted my test logs behind statements for the time being
        int alphaProgressShift = (int) Math.round(alphaSeek.getProgress() * SWAY);//Log.i(TAG, "Alpha Progress Changed. It is at " + alphaProgressShift);
        int redProgressShift = (int) Math.round(redSeek.getProgress() * SWAY);    //Log.i(TAG, "Red Progress Changed. It is at " + redProgressShift);
        int greenProgressShift = (int) Math.round(greenSeek.getProgress() * SWAY);//Log.i(TAG, "Green Progress Changed. It is at " + greenProgressShift);
        int blueProgressShift = (int) Math.round(blueSeek.getProgress() * SWAY);  //Log.i(TAG, "Blue Progress Changed. It is at " + blueProgressShift);

        //gather the textView
        final TextView colorDumpTV = (TextView) findViewById(R.id.colorDumpTextView);

        //Gather the text for the TEXT view
        String allHex = convertAllToHex(
                alphaProgressShift, redProgressShift,
                greenProgressShift, blueProgressShift);
        colorDumpTV.setText(allHex);

        //deal with color shift
        int colorInt = Color.argb(alphaProgressShift, redProgressShift, greenProgressShift, blueProgressShift);
        colorDumpTV.setTextColor(getLightDark(colorInt));
        colorDumpTV.setBackgroundColor(colorInt);
        Log.i(TAG, "Color to display: " + allHex);


    }
    private int getLightDark(int color){
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        Log.i(TAG, "set text color to average of " + (red+blue+green)/3 + ".");
        if((red+blue+green)/3 < 100)
            return Color.WHITE;
        return Color.BLACK;
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
