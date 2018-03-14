package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }


    public void backButton(View view) {
        startActivity(new Intent(this, HomePage.class));

    }

    public void searchButton(View view) {
    }

    public void feverButton(View view) {
    }

    public void coughButton(View view) {
    }

    public void diarrhoeaButton(View view) {
    }

    public void headacheButton(View view) {
    }

    public void sorethroatButton(View view) {
    }
}
