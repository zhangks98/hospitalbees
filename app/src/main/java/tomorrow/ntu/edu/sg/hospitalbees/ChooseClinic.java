package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import static tomorrow.ntu.edu.sg.hospitalbees.ClinicAdapter.clinicdetails;

public class ChooseClinic extends AppCompatActivity{


    RecyclerView clinicRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clinic);

        clinicRV = (RecyclerView) findViewById(R.id.clinic_recycler_view);
        clinicRV.setHasFixedSize(true);
        clinicRV.setLayoutManager(new LinearLayoutManager(this));
        clinicRV.setAdapter(new ClinicAdapter(this));
    }

}
