package edu.ggc.mhompson.convert;

import android.os.AsyncTask;
import android.util.Log;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.Console;
//import java.io.InputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;


/**
 * Created by matthew on 10/5/16.
 */

public class FetchAsyncTask extends AsyncTask<String, Void, Double>{


    @Override
    protected Double doInBackground(String... uri) {
        Log.i("CurrConv","Backgrouded..." );

        //call the long proccess
//
//        try {
//            URL url = new URL("http://www.android.com/");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            try {
//                urlConnection.setDoOutput(true);
//                urlConnection.setChunkedStreamingMode(0);
//
//                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//                writeStream(out);
//
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                readStream(in);
//            } catch (IOException IOE) {
//                Log.e("e","IOException"+IOE);
//
//            }finally
//            {
//                urlConnection.disconnect();
//            }
//        }catch(MalformedURLException MURLE){
//            Log.e("CurrConv", "MalformedURLException"+MURLE);
//        }catch(IOException IOE){
//            Log.e("e","IOException"+IOE);
//        }
//

        return null;

    }
//
//    private void writeStream(OutputStream out) {
//        Log.i("CurrConv","writeStream"+out);
//    }
//
//    private void readStream(InputStream in) {
//        Log.i("CurrConv","inStream"+in);
//    }

}
