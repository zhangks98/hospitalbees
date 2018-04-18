package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The class for login activity
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private EditText editName, editPassword;

    String user[] = {"90908080", "42576543", "77766689"};

    String pass[] = {"pass1", "pass2", "pass3"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);

    }

    /**
     * Method that allows the User to log in the APP on by clicking the log in button.
     * User can only log in if he keyed in the matching username and password to the database.
     *
     * @param view the log in button view
     *
     */
    public void logInButton(View view) {
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        int i;
        for (i = 0; i< user.length; i++) {
                if ((name.equals(user[i])) && (password.equals(pass[i]))) {
                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.pref_user), Context.MODE_PRIVATE).edit();
                    editor.putBoolean(getString(R.string.pref_user_is_logged_in_key), true);
                    editor.putString(getString(R.string.pref_user_phone_number_key), user[i]);
                    editor.apply();
                    startActivity(new Intent(this, HomePage.class));
                    this.finish();
                    break;
                }
        }
        if (i == user.length) {
            Toast.makeText(getApplicationContext(), "WRONG CREDENTIALS", Toast.LENGTH_SHORT).show();

        }
        Log.d(TAG, "clickHandler: name~" + name + ", password~" + password);

    }


    /**
     * Sign up button.
     *
     * @param view the view
     */
    public void signUpButton(View view) {
    }

    /**
     * Forget password button.
     *
     * @param view the view
     */
    public void forgetPasswordButton(View view) {

    }
}
