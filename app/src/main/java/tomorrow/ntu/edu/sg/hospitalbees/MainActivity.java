package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText editName, editPassword;
    String admin[] = {"Gerald", "Qinan", "Fuhank", "Kaishuo", "Vansh"};
    String pass[] = {"pass1", "pass2", "pass3", "pass4", "pass5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);




    }

    public void logInButton(View view) {
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        int i;
        for (i = 0; i< admin.length; i++) {
            if ((name.equals(admin[i])) && (password.equals(pass[i]))) {
                startActivity(new Intent(MainActivity.this, HomePage.class));
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
