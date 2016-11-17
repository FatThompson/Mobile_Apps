package lutz.ggc.edu.ggctarsosdspandroid;

/**
 * Created by matthew on 11/16/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * The following code was used from StackOverflow address
 * http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen
 * This was the only splace I
 * found that could make the splash screen work.
 */
public class AboutActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_about);

        /* New Handler to start the Menu-Activity
         * and close this AboutActivity-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(AboutActivity.this,TarsosDSPActivity.class);
                AboutActivity.this.startActivity(mainIntent);
                AboutActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}