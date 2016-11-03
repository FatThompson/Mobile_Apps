package edu.ggc.matthew.examtwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SeekBar;
import android.widget.TextView;

import javax.net.ssl.SSLPeerUnverifiedException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //deal with seekbars
        final SeekBar widthSeekBar =(SeekBar) findViewById(R.id.width);
        final SeekBar heightSeekBar =(SeekBar) findViewById(R.id.height);

        SeekBarListener(widthSeekBar);
        SeekBarListener(heightSeekBar);

//        final Spinner tileSize =(Spinner) findViewById(R.id.spinner);
//        tileSize.setOnItemClickListener(new Spinner.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                final TextView results =(TextView) findViewById(R.id.results);
//                results.setVisibility(View.INVISIBLE);
//            }
//        });
    }

    /**
     * Calculates the amount of tiles needed based on
     * spinner and seekbars
     * @param view
     */
    public void calculateTiles(View view) {

        final SeekBar widthSeekBar =(SeekBar) findViewById(R.id.width);
        final SeekBar heightSeekBar =(SeekBar) findViewById(R.id.height);

        int widthInt = widthSeekBar.getProgress() + 8;
        int heightInt = heightSeekBar.getProgress() + 8;

        final Spinner tileSize =(Spinner) findViewById(R.id.spinner);
        String tileText = tileSize.getSelectedItem().toString();
        double multiplier = 1.5;
        if(tileText.equals("12in X 12in")) multiplier = 1;

        int area = widthInt * heightInt;
        double tilesNeeded = area/multiplier;
        if(tilesNeeded != Math.floor(tilesNeeded)) tilesNeeded++;
        int tiles = (int) Math.floor(tilesNeeded);

        final TextView results =(TextView) findViewById(R.id.results);
        results.setText("Room size: " + widthInt + "ft, " + heightInt + "ft.\n"
                + "You Need " + tiles + "."
        );
        results.setVisibility(View.VISIBLE);

    }

    private void SeekBarListener(SeekBar mySeekBar) {
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            /**
             * make results go away
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                final TextView results = (TextView) findViewById(R.id.results);
                results.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
