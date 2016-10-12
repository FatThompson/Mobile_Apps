package edu.ggc.mhompson.convert;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

        //get the data
        String response = getData("USD","GBP");
        Log.i("Response:  ",response);

        //parse the data
        //in a JSON format? concert to JSON?


        //return/finsh the thread
        Log.i("CurrConv","Backgrouded Ended" );
        return null;
    }

    private String getData(String base, String convertTo){
        //create the variables
        String urlString = "http://api.fixer.io/latest?base="+base+"&symbols="+convertTo;
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream=null;
        String response = "";

        //run data
        try {
            //setup the url connection
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

            //settings
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //get the input stream and set it to a buffer
            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Compile the data to response
            String temp = "";
            while((temp = bufferedReader.readLine()) != null)
                response += temp;
//            Log.d("CurrConv:", "Response:  " + response);
        }catch(MalformedURLException MURLE){
            Log.e("CurrConv", "MalformedURLException:  "+MURLE);
        }catch(IOException IOE) {
            Log.e("CurrConv", "IOException:  " + IOE);
            Log.e("CurrConv", "URL ERROR:  " + urlConnection.getErrorStream());
        }catch(Exception e){
            Log.e("CurrConv","Other Exception:  "+e.getStackTrace());
            e.printStackTrace();
        }finally {
            // FORCE close the conection
            urlConnection.disconnect();
        }

        //return the GET request
        return response;
    }


}
