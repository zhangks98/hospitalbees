package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The class for splash activity which is the loading screen (blue screen) in between pages
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences user = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE);
        if (user.getBoolean(getString(R.string.pref_user_is_logged_in_key), getResources().getBoolean(R.bool.pref_is_logged_in_default))) {
            startActivity(new Intent(this, HomePage.class));
        }
        else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        this.finish();
    }
}
