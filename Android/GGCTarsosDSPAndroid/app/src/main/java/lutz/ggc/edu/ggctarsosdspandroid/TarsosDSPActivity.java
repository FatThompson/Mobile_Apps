package lutz.ggc.edu.ggctarsosdspandroid;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

public class TarsosDSPActivity extends AppCompatActivity {

    private ArrayList<Float> pitchList =  new ArrayList<Float>();
    private final int PITCH_LIST_MAX = 25;
    private final int PITCH_LIST_MIN_TO_DISPLAY = 7;
	private final int MAX_PITCH_CHANGE = 3;
    private float changeInPitch;
    private MediaPlayer mediaPlayer;
    Thread pitchThread;


    private String TAG = "TarsosDSPActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarsos_dsp);

        createAudioRecognizer(savedInstanceState);

        mediaPlayer = MediaPlayer.create(this, R.raw.ggc_alma_mater);
        startMusic();


	}


    /***
     * Music Start
     *
     *
     *
     *
     *
     *
     *
     *
     */

    private void changeLyrics(int filePos) {
        filePos = (int) filePos / 1000;
        Object[][] lyrics_array = {
            {01, ""},
            {10, "We have gained wisdom and honor"},
            {14, "From our home of green and grey"},
            {18, "We will go forth and remember"},
            {22, "All we've learned along the way"},
            {26, "And with knowledge and compassion"},
            {30, "We will build communities"},
            {34, "Leading by example"},
            {39, "And with dignity"},
            {43, "Georgia Gwinnett"},
            {47, "We'll ne'er forget"},
            {51, "How we have grown"},
            {54, "And those that we have met"},
            {59, "Georgia Gwinnett"},
            {63, "With love and respect"},
            {68, "Our Alma Mater"},
            {72, "Georgia Gwinnett"},
            {75, "Our Alma Mater"},
            {80, "Georgia Gwinnett"},
            {86, ""}
        };

        //select the lyric
        for (int i = 0; i < lyrics_array.length; i++) {

            if ((int) lyrics_array[i][0] == Math.floor(filePos)) {

                //update screen
//                $('#lyrics').html(lyrics_array[i][1]);
                updateScreen(lyrics_array[i][1]);
            } else {

            }
        }
    }

    private void updateScreen(Object lyrics_array){
        Log.i(TAG,"the Lyric: "+lyrics_array.toString());

        TextView lyrics = (TextView) findViewById(R.id.lyrics);
        lyrics.setText("Lyrics: " + lyrics_array.toString());
    }
    /**
     *
     *
     */
    private void startMusic(){
        Log.i(TAG,"startMusic");
        try {
            mediaPlayer.start();
        }catch (Exception e){

        }
        changeLyrics(01);
    }

    private void stopMusic(){
        Log.i(TAG,"stopMusic");
        try {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(this, R.raw.ggc_alma_mater);
        }catch (Exception e){}

        changeLyrics(01);

    }

    private void pauseMusic(){
        Log.i(TAG,"pauseMusic");
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    private void resetMusic(){
        Log.i(TAG,"resetMusic");
        stopMusic();
        startMusic();
    }


    /***
     * Music buttons
     *
     *
     *
     *
     */
    public void startStopMusic(View view){
        if(mediaPlayer.isPlaying() || mediaPlayer.isLooping()) stopMusic();
        else startMusic();

    }
    public void startMusic(View view){startMusic();}
    public void stopMusic(View view){stopMusic();}
    public void pauseMusic(View view){pauseMusic();}

    public void resetMusic(View view){resetMusic();}





    /***
     *
     *
     * Note recognizer
     *
     *
     *
     */


    /**
     * Seperated to make things a little easier
     * @param savedInstanceState
     */
	protected void createAudioRecognizer(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

        dispatcher.addAudioProcessor(new PitchProcessor(PitchEstimationAlgorithm.FFT_YIN, 22050, 1024,
                new PitchDetectionHandler() {

                    @Override
                    public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                            AudioEvent audioEvent) {
                        final float pitchInHz = pitchDetectionResult.getPitch();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayPitch(pitchInHz);
                            }
                        });

                    }
                }));
        pitchThread = new Thread(dispatcher,"Audio Dispatcher");
        pitchThread.start();
    }




    /**
     * Updates pitchLog and displays the pitch
     * @param pitch
     */
	public void displayPitch(float pitch){

        changeLyrics(mediaPlayer.getCurrentPosition());
		pitch = lowPassFilter(pitch);

		String pitchInfo[] = new String[3];
		//check to see if long enough
		if(pitchList.size() > PITCH_LIST_MIN_TO_DISPLAY){
			pitchInfo = getNoteInfo(pitch);
		} else pitchInfo = getNoteInfo(0);

        TextView txvNote = (TextView) findViewById(R.id.txvNote);
		TextView txvFlat = (TextView) findViewById(R.id.txvFlat);
		TextView txvOct = (TextView) findViewById(R.id.txvOct);

		txvNote.setText(pitchInfo[0]+"");
		txvFlat.setText(pitchInfo[1] + "");
		txvOct.setText(pitchInfo[2] + "");

    }

    /**
     * Converts pitch to note with flats and octive
     * @param pitch
     * @return
     */
	private String[] getNoteInfo(float pitch){
		String letter = "--";
		String flat = "";
		String oct = "";
		String[] pitchInfo = new String[3];

		//check what value it is
		if      (15.00   <=  pitch && pitch < 16.84  ) { letter = "C"; flat = "    ";     oct = " 0  " ; }
		else if (16.84   <=  pitch && pitch < 17.84  ) { letter = "D"; flat = " \u266D "; oct = " 0  " ; }
		else if (17.84   <=  pitch && pitch < 18.90  ) { letter = "D"; flat = "    ";     oct = " 0  " ; }
		else if (18.90   <=  pitch && pitch < 20.03  ) { letter = "E"; flat = " \u266D "; oct = " 0  " ; }
		else if (20.03   <=  pitch && pitch < 21.22  ) { letter = "E"; flat = "    ";     oct = " 0  " ; }
		else if (21.22   <=  pitch && pitch < 22.48  ) { letter = "F"; flat = "    ";     oct = " 0  " ; }
		else if (22.48   <=  pitch && pitch < 23.81  ) { letter = "G"; flat = " \u266D "; oct = " 0  " ; }
		else if (23.81   <=  pitch && pitch < 25.23  ) { letter = "G"; flat = "    ";     oct = " 0  " ; }
		else if (25.23   <=  pitch && pitch < 26.73  ) { letter = "A"; flat = " \u266D "; oct = " 1  " ; }
		else if (26.73   <=  pitch && pitch < 28.32  ) { letter = "A"; flat = "    ";     oct = " 1  " ; }
		else if (28.32   <=  pitch && pitch < 30.01  ) { letter = "B"; flat = " \u266D "; oct = " 1  " ; }
		else if (30.01   <=  pitch && pitch < 31.79  ) { letter = "B"; flat = "    ";     oct = " 1  " ; }
		else if (31.79   <=  pitch && pitch < 33.68  ) { letter = "C"; flat = "    ";     oct = " 1  " ; }
		else if (33.68   <=  pitch && pitch < 35.68  ) { letter = "D"; flat = " \u266D "; oct = " 1  " ; }
		else if (35.68   <=  pitch && pitch < 37.80  ) { letter = "D"; flat = "    ";     oct = " 1  " ; }
		else if (37.80   <=  pitch && pitch < 40.05  ) { letter = "E"; flat = " \u266D "; oct = " 1  " ; }
		else if (40.05   <=  pitch && pitch < 42.43  ) { letter = "E"; flat = "    ";     oct = " 1  " ; }
		else if (42.43   <=  pitch && pitch < 44.95  ) { letter = "F"; flat = "    ";     oct = " 1  " ; }
		else if (44.95   <=  pitch && pitch < 47.63  ) { letter = "G"; flat = " \u266D "; oct = " 1  " ; }
		else if (47.63   <=  pitch && pitch < 50.46  ) { letter = "G"; flat = "    ";     oct = " 1  " ; }
		else if (50.46   <=  pitch && pitch < 53.46  ) { letter = "A"; flat = " \u266D "; oct = " 2  " ; }
		else if (53.46   <=  pitch && pitch < 56.64  ) { letter = "A"; flat = "    ";     oct = " 2  " ; }
		else if (56.64   <=  pitch && pitch < 60.01  ) { letter = "B"; flat = " \u266D "; oct = " 2  " ; }
		else if (60.01   <=  pitch && pitch < 63.58  ) { letter = "B"; flat = "    ";     oct = " 2  " ; }
		else if (63.58   <=  pitch && pitch < 67.36  ) { letter = "C"; flat = "    ";     oct = " 2  " ; }
		else if (67.36   <=  pitch && pitch < 71.36  ) { letter = "D"; flat = " \u266D "; oct = " 2  " ; }
		else if (71.36   <=  pitch && pitch < 75.60  ) { letter = "D"; flat = "    ";     oct = " 2  " ; }
		else if (75.60   <=  pitch && pitch < 80.10  ) { letter = "E"; flat = " \u266D "; oct = " 2  " ; }
		else if (80.10   <=  pitch && pitch < 84.86  ) { letter = "E"; flat = "    ";     oct = " 2  " ; }
		else if (84.86   <=  pitch && pitch < 89.91  ) { letter = "F"; flat = "    ";     oct = " 2  " ; }
		else if (89.91   <=  pitch && pitch < 95.25  ) { letter = "G"; flat = " \u266D "; oct = " 2  " ; }
		else if (95.25   <=  pitch && pitch < 100.92 ) { letter = "G"; flat = "    ";     oct = " 2  " ; }
		else if (100.92  <=  pitch && pitch < 106.92 ) { letter = "A"; flat = " \u266D "; oct = " 3  " ; }
		else if (106.92  <=  pitch && pitch < 113.27 ) { letter = "A"; flat = "    ";     oct = " 3  " ; }
		else if (113.27  <=  pitch && pitch < 120.01 ) { letter = "B"; flat = " \u266D "; oct = " 3  " ; }
		else if (120.01  <=  pitch && pitch < 127.14 ) { letter = "B"; flat = "    ";     oct = " 3  " ; }
		else if (127.14  <=  pitch && pitch < 134.70 ) { letter = "C"; flat = "    ";     oct = " 3  " ; }
		else if (134.70  <=  pitch && pitch < 142.71 ) { letter = "D"; flat = " \u266D "; oct = " 3  " ; }
		else if (142.71  <=  pitch && pitch < 151.20 ) { letter = "D"; flat = "    ";     oct = " 3  " ; }
		else if (151.20  <=  pitch && pitch < 160.19 ) { letter = "E"; flat = " \u266D "; oct = " 3  " ; }
		else if (160.19  <=  pitch && pitch < 169.71 ) { letter = "E"; flat = "    ";     oct = " 3  " ; }
		else if (169.71  <=  pitch && pitch < 179.81 ) { letter = "F"; flat = "    ";     oct = " 3  " ; }
		else if (179.81  <=  pitch && pitch < 190.50 ) { letter = "G"; flat = " \u266D "; oct = " 3  " ; }
		else if (190.50  <=  pitch && pitch < 201.83 ) { letter = "G"; flat = "    ";     oct = " 3  " ; }
		else if (201.83  <=  pitch && pitch < 213.83 ) { letter = "A"; flat = " \u266D "; oct = " 4  " ; }
		else if (213.83  <=  pitch && pitch < 226.54 ) { letter = "A"; flat = "    ";     oct = " 4  " ; }
		else if (226.54  <=  pitch && pitch < 240.01 ) { letter = "B"; flat = " \u266D "; oct = " 4  " ; }
		else if (240.01  <=  pitch && pitch < 254.29 ) { letter = "B"; flat = "    ";     oct = " 4  " ; }
		else if (254.29  <=  pitch && pitch < 269.41 ) { letter = "C"; flat = "    ";     oct = " 4  " ; }
		else if (269.41  <=  pitch && pitch < 285.42 ) { letter = "D"; flat = " \u266D "; oct = " 4  " ; }
		else if (285.42  <=  pitch && pitch < 302.40 ) { letter = "D"; flat = "    ";     oct = " 4  " ; }
		else if (302.40  <=  pitch && pitch < 320.38 ) { letter = "E"; flat = " \u266D "; oct = " 4  " ; }
		else if (320.38  <=  pitch && pitch < 339.43 ) { letter = "E"; flat = "    ";     oct = " 4  " ; }
		else if (339.43  <=  pitch && pitch < 359.61 ) { letter = "F"; flat = "    ";     oct = " 4  " ; }
		else if (359.61  <=  pitch && pitch < 381.00 ) { letter = "G"; flat = " \u266D "; oct = " 4  " ; }
		else if (381.00  <=  pitch && pitch < 403.65 ) { letter = "G"; flat = "    ";     oct = " 4  " ; }
		else if (403.65  <=  pitch && pitch < 427.65 ) { letter = "A"; flat = " \u266D "; oct = " 5  " ; }
		else if (427.65  <=  pitch && pitch < 453.08 ) { letter = "A"; flat = "    ";     oct = " 5  " ; }
		else if (453.08  <=  pitch && pitch < 480.02 ) { letter = "B"; flat = " \u266D "; oct = " 5  " ; }
		else if (480.02  <=  pitch && pitch < 508.57 ) { letter = "B"; flat = "    ";     oct = " 5  " ; }
		else if (508.57  <=  pitch && pitch < 538.81 ) { letter = "C"; flat = "    ";     oct = " 5  " ; }
		else if (538.81  <=  pitch && pitch < 570.85 ) { letter = "D"; flat = " \u266D "; oct = " 5  " ; }
		else if (570.85  <=  pitch && pitch < 604.79 ) { letter = "D"; flat = "    ";     oct = " 5  " ; }
		else if (604.79  <=  pitch && pitch < 640.75 ) { letter = "E"; flat = " \u266D "; oct = " 5  " ; }
		else if (640.75  <=  pitch && pitch < 678.86 ) { letter = "E"; flat = "    ";     oct = " 5  " ; }
		else if (678.86  <=  pitch && pitch < 719.23 ) { letter = "F"; flat = "    ";     oct = " 5  " ; }
		else if (719.23  <=  pitch && pitch < 761.99 ) { letter = "G"; flat = " \u266D "; oct = " 5  " ; }
		else if (761.99  <=  pitch && pitch < 807.30 ) { letter = "G"; flat = "    ";     oct = " 5  " ; }
		else if (807.30  <=  pitch && pitch < 855.31 ) { letter = "A"; flat = " \u266D "; oct = " 6  " ; }
		else if (855.31  <=  pitch && pitch < 906.17 ) { letter = "A"; flat = "    ";     oct = " 6  " ; }
		else if (906.17  <=  pitch && pitch < 960.05 ) { letter = "B"; flat = " \u266D "; oct = " 6  " ; }
		else if (960.05  <=  pitch && pitch < 1017.14) { letter = "B"; flat = "    ";     oct = " 6  " ; }
		else if (1017.14 <=  pitch && pitch < 1077.62) { letter = "C"; flat = "    ";     oct = " 6  " ; }
		else if (1077.62 <=  pitch && pitch < 1141.70) { letter = "D"; flat = " \u266D "; oct = " 6  " ; }
		else if (1141.70 <=  pitch && pitch < 1209.59) { letter = "D"; flat = "    ";     oct = " 6  " ; }
		else if (1209.59 <=  pitch && pitch < 1281.51) { letter = "E"; flat = " \u266D "; oct = " 6  " ; }
		else if (1281.51 <=  pitch && pitch < 1357.71) { letter = "E"; flat = "    ";     oct = " 6  " ; }
		else if (1357.71 <=  pitch && pitch < 1438.45) { letter = "F"; flat = "    ";     oct = " 6  " ; }
		else if (1438.45 <=  pitch && pitch < 1523.98) { letter = "G"; flat = " \u266D "; oct = " 6  " ; }
		else if (1523.98 <=  pitch && pitch < 1614.60) { letter = "G"; flat = "    ";     oct = " 6  " ; }
		else if (1614.60 <=  pitch && pitch < 1710.61) { letter = "A"; flat = " \u266D "; oct = " 7  " ; }
		else if (1710.61 <=  pitch && pitch < 1812.33) { letter = "A"; flat = "    ";     oct = " 7  " ; }
		else if (1812.33 <=  pitch && pitch < 1920.10) { letter = "B"; flat = " \u266D "; oct = " 7  " ; }
		else if (1920.10 <=  pitch && pitch < 2034.27) { letter = "B"; flat = "    ";     oct = " 7  " ; }
		else if (2034.27 <=  pitch && pitch < 2155.23) { letter = "C"; flat = "    ";     oct = " 7  " ; }
		else if (2155.23 <=  pitch && pitch < 2283.39) { letter = "D"; flat = " \u266D "; oct = " 7  " ; }
		else if (2283.39 <=  pitch && pitch < 2419.17) { letter = "D"; flat = "    ";     oct = " 7  " ; }
		else if (2419.17 <=  pitch && pitch < 2563.02) { letter = "E"; flat = " \u266D "; oct = " 7  " ; }
		else if (2563.02 <=  pitch && pitch < 2715.43) { letter = "E"; flat = "    ";     oct = " 7  " ; }
		else if (2715.43 <=  pitch && pitch < 2876.90) { letter = "F"; flat = "    ";     oct = " 7  " ; }
		else if (2876.90 <=  pitch && pitch < 3047.96) { letter = "G"; flat = " \u266D "; oct = " 7  " ; }
		else if (3047.96 <=  pitch && pitch < 3229.20) { letter = "G"; flat = "    ";     oct = " 7  " ; }
		else if (3229.20 <=  pitch && pitch < 3421.22) { letter = "A"; flat = " \u266D "; oct = " 8  " ; }
		else if (3421.22 <=  pitch && pitch < 3624.66) { letter = "A"; flat = "    ";     oct = " 8  " ; }
		else if (3624.66 <=  pitch && pitch < 3840.19) { letter = "B"; flat = " \u266D "; oct = " 8  " ; }
		else if (3840.19 <=  pitch && pitch < 4068.54) { letter = "B"; flat = "    ";     oct = " 8  " ; }
		else if (4068.54 <=  pitch && pitch < 4310.47) { letter = "C"; flat = "    ";     oct = " 8  " ; }
		else if (4310.47 <=  pitch && pitch < 4566.78) { letter = "D"; flat = " \u266D "; oct = " 8  " ; }
		else if (4566.78 <=  pitch && pitch < 4838.33) { letter = "D"; flat = "    ";     oct = " 8  " ; }
		else if (4838.33 <=  pitch && pitch < 5126.04) { letter = "E"; flat = " \u266D "; oct = " 8  " ; }
		else if (5126.04 <=  pitch && pitch < 5430.85) { letter = "E"; flat = "    ";     oct = " 8  " ; }
		else if (5430.85 <=  pitch && pitch < 5753.78) { letter = "F"; flat = "    ";     oct = " 8  " ; }
		else if (5753.78 <=  pitch && pitch < 6095.92) { letter = "G"; flat = " \u266D "; oct = " 8  " ; }
		else if (6095.92 <=  pitch && pitch < 6458.41) { letter = "G"; flat = "    ";     oct = " 8  " ; }
		else if (6458.41 <=  pitch && pitch < 6842.44) { letter = "A"; flat = " \u266D "; oct = " 9  " ; }
		else if (6842.44 <=  pitch && pitch < 7249.31) { letter = "A"; flat = "    ";     oct = " 9  " ; }
		else if (7249.31 <=  pitch && pitch < 7680.38) { letter = "B"; flat = " \u266D "; oct = " 9  " ; }
		else if (7680.38 <=  pitch && pitch < 3951.07) { letter = "B"; flat = "    ";     oct = " 9  " ; }
		else {letter = "--"; flat = ""; oct = "";}

		pitchInfo[0] = letter;
		pitchInfo[1] = flat;
		pitchInfo[2] = oct;

//		Log.i("Pitch Gathering",""+pitchInfo[0]+pitchInfo[1]+pitchInfo[2]);
        return pitchInfo;
	}

    /**
     * Creation of a low pass filter
	 * Takes the average of the arraylist.
     * @param pitch
     * @return
     */
    private float lowPassFilter(float pitch) {
        //add to the end of the list
        if(pitch >= 15) pitchList.add(new Float(pitch));
        //else remove one
        else if(pitchList.size() > 0) pitchList.remove(0);

        //if list is too long, trim down, and
        //remove the first choice.
        if(pitchList.size()>= PITCH_LIST_MAX) if(pitchList.size() > 0)
            pitchList.remove(0);


        pitch = getAverage(pitchList);

        //helps make more snappy on changing
        if(Math.abs(pitch-changeInPitch) > MAX_PITCH_CHANGE) if(pitchList.size() > 0)
			pitchList.remove(0);

		//update the pitch
		changeInPitch = pitch;

        if(pitch < 15) if(pitchList.size() > 0)
			pitchList.remove(0);

//        Log.i("PitchLIst","pitch: "+pitch);
        return pitch;
    }

    /**
     * gets the average of an array list
     * @param arraylist
     * @return average as float
     */
    private float getAverage(ArrayList<Float> arraylist) {
        float temp = 0;
        int arraylist_size = arraylist.size();

        if(arraylist_size>0) {
            for (int i = 0; i < arraylist_size; i++) {
                temp += arraylist.get(i);
            }
            temp /= arraylist_size;
        }
        else return 0;

        return temp;
    }


    /***
     *
     *
     *
     * Baseline stuff
     *
     *
     *
     *
     *
     *
     *
     */

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tarsos_ds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tarsos_ds,
                    container, false);
            return rootView;
        }
    }

    /**
     * This is based on the Android API
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    //do nothing
                    break;
            case R.id.radio_female:
                if (checked)
                    //do nothing
                    break;
        }
    }


}
