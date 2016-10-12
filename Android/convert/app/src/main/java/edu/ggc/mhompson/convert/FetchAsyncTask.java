package edu.ggc.mhompson.convert;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by matthew on 10/5/16.
 */

public class FetchAsyncTask extends AsyncTask<String, Void, Double>{


    @Override
    protected Double doInBackground(String... params) {
        Log.i("CurrConv","Backgrouded..." );

        //call the long proccess

        try {

            URL url = new URL("http://www.google.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                readStream(in);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                //Fine
//                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//                writeStream(out);
                Log.i("temp","Test");
                String temp = "";
                Log.i("CurrConv","The get is " + temp);
            } finally {
                urlConnection.disconnect();
            }

        }catch(MalformedURLException MURLE){
            Log.e("CurrConv", "MalformedURLException:  "+MURLE);
        }catch(IOException IOE) {
            Log.e("CurrConv", "IOException:  " + IOE);
        }catch(Exception e){
            Log.e("CurrConv","Other Exception:  "+e.getStackTrace());
            e.printStackTrace();
        }


        return null;
    }

    private void writeStream(OutputStream out) {
        Log.i("CurrConv","writeStream:  "+out);
    }

    private void readStream(InputStream in) {
        Log.i("CurrConv","inStream"+in);
    }

}
