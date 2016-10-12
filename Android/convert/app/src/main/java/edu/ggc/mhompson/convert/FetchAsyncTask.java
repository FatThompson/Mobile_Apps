package edu.ggc.mhompson.convert;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by matthew on 10/5/16.
 */

public class FetchAsyncTask extends AsyncTask<String, Void, Double>{

    /**
     * Main Method
     * @param conversions
     * @return
     */
    @Override
    protected Double doInBackground(String... conversions) {
        Log.i("CurrConv","Backgrouded..." );

        //get the data
        String response = getData(conversions[0],conversions[1]);
        Log.i("Response:  ",response);

        //parse the data/ return converion rate
        double conversionRate = getConverionRate(response,conversions[1]);
        Log.i("CurrConv","Conversion Rate:  " + conversionRate);

        //return/finish the thread
        Log.i("CurrConv","Backgrouded Ended" );
        return conversionRate;
    }

    /**
     * Returns the conversion rate
     * @param response
     * @param convertTo
     * @return
     */
    private double getConverionRate(String response, String convertTo){
        //SAMPLE
        //   {"base":"USD","date":"2016-10-12","rates":{"GBP":0.81661}}
        //if error, returns 0
        double conversionRate=0;
        try{
            //parse into a json
            JSONObject responceJSON = new JSONObject(response);

            //get the conversion rate
            JSONObject rates = responceJSON.getJSONObject("rates");
            conversionRate = rates.getDouble(convertTo);

//            Log.i("CurrConv","Conversion Rate:  " + conversionRate);
        }catch (JSONException JSONE){
            Log.e("CurrConv","JSONException:  "+JSONE);
        }catch (Exception E){
            Log.e("CurrConv","Other Exception:  "+ E);
        }
        //return the conversion rate
        return conversionRate;
    }

    /**
     * returns the POST GET data from the website
     * @param base
     * @param convertTo
     * @return
     */
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
