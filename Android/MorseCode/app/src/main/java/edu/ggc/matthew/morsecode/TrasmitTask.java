package edu.ggc.matthew.morsecode;

import android.os.AsyncTask;
import android.util.Log;
import java.util.LinkedList;


/**
 * Created by matthew on 11/3/16.
 */


class TrasmitTask extends AsyncTask<String, Void, Double> {
    private final String TAG = "MorseCode TrasmitTask";
    private String message;


    @Override
    protected Double doInBackground(String... msg) {

        Log.v(TAG, "running doInBackground() ....");
        message = msg[0];
        int defer = 500;
        LinkedList<Signal> schedule = MorseCode.genOnOffSchedule(message, defer);
        long begin = System.currentTimeMillis();

        while (!schedule.isEmpty()) {
            Signal signal = schedule.removeFirst();
            boolean arrival = false;
            while(!arrival)
                arrival = (System.currentTimeMillis() - begin) > signal.getOnset();

        }

        return null;
    }



    public void updateUI(Signal signal){
        Log.i(TAG,"in UpdateUI.");
    }

}

