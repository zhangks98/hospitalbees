package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static tomorrow.ntu.edu.sg.hospitalbees.SplashActivity.logincheck;

public class LoginActivity extends AppCompatActivity {



    private static final String TAG = "LoginActivity";
    public static String logindetails = "LoginDetails";
    SharedPreferences check;

    EditText editName, editPassword;
    String admin[] = {"Gerald", "Qinan", "Fuhank", "Kaishuo", "Vansh"};
    String pass[] = {"pass1", "pass2", "pass3", "pass4", "pass5"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        check = getSharedPreferences(logincheck, 0);
        boolean checked = check.getBoolean(logincheck, false);

        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);




    }

    public void logInButton(View view) {
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        int i;
        for (i = 0; i< admin.length; i++) {
            if ((name.equals(admin[i])) && (password.equals(pass[i]))) {
                SharedPreferences.Editor editor = check.edit();
                editor.putBoolean(logincheck, true);
                editor.apply();
                PreferenceManager.getDefaultSharedPreferences(this).edit().putString(logindetails, name).apply();
                startActivity(new Intent(this, HomePage.class));
                this.finish();
                break;
            }
        }
        if (i == admin.length) {
            Toast.makeText(getApplicationContext(), "WRONG CREDENTIALS", Toast.LENGTH_LONG).show();

        }


        Log.i(TAG, "clickHandler: name~" + name + ", password~" + password);


    }


    public void signUpButton(View view) {
    }

    public void forgetPasswordButton(View view) {

    }
}
