package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    public static String logincheck = "LogInCheck";
    SharedPreferences check;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        check = getSharedPreferences(logincheck, 0);
        boolean checked = check.getBoolean(logincheck, false);

        if (checked) {
            intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
        else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
