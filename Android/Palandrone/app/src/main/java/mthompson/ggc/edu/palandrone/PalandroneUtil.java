package mthompson.ggc.edu.palandrone;

/**
 * Created by User on 9/7/2016.
 */
public class PalandroneUtil {

    public static boolean isPalandrone(String inputString){
        String reverseString = "";
        for ( int i = inputString.length() - 1 ; i >= 0 ; i-- )
            reverseString = reverseString + inputString.charAt(i);
        if (inputString.equals(reverseString))
            return true;
        else return false;
    }
}
