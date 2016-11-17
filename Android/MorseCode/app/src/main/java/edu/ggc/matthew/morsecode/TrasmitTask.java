package edu.ggc.matthew.morsecode;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.LinkedList;


/**
 * Created by matthew on 11/3/16.
 */


class TrasmitTask extends AsyncTask<String, Void, Double> {
    private final String TAG = "MorseCode TrasmitTask";
    private String message;

    WeakReference<Activity> wMainActivity;

    /**
     * Needed inorder to pull the data
     * @param activity
     */
    public TrasmitTask(Activity activity) {
        super();
        wMainActivity = new WeakReference<Activity>(activity);
    }
    @Override
    protected Double doInBackground(String... msg) {

//        Log.v(TAG, "running doInBackground() ....");
////        message = msg[0];
//        int defer = 500;
//        LinkedList<Signal> schedule = MorseCode.genOnOffSchedule(message, defer);
////        long begin = System.currentTimeMillis();
//        Log.i(TAG,"Before Loops");
//        while (!schedule.isEmpty()) {
//            Signal signal = schedule.removeFirst();
//            boolean arrival = false;
////            while(!arrival)

//            arrival = (System.currentTimeMillis() - begin) > signal.getOnset();
            updateUI();
        //}

        return null;
    }
    public void updateUI(){
        Log.i(TAG,"in UpdateUI.");
        Activity activity;
//Signal signal
        try{
            activity = wMainActivity.get();
            if(activity != null){
                View morseDisplay = (View) activity.findViewById(R.id.MorseDisplay);
                morseDisplay.setBackgroundColor(Color.BLACK);
                morseDisplay.setBackgroundColor(Color.WHITE);

            }else Log.e(TAG,"ACTIVITY IS NULL");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

