package mthompson.ggc.edu.palandrone;

/**
 * Created by User on 9/7/2016.
 */
public class PalandroneUtil {

    public static boolean isPalandrone(String text){
        int length = text.length();
        while(text.length() > 2){
            if(!(text.substring(0,0).equals(text.substring(text.length()-1))))
                return false;
        }
        return true;
    }
}
