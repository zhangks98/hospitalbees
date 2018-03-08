package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void clickHandler3(View view) {
        startActivity(new Intent (this, MainActivity.class));

    }

    public void clickHandler4(View view) {
        startActivity(new Intent (this, ChooseClinic.class));
    }

    public void clickHandler5(View view) {
    }


    public void clickHandler6(View view) {
    }


    public void clickHandler7(View view) {
    }


    public void clickHandler8(View view) {
        startActivity(new Intent (this, Alerts.class));
    }
}
