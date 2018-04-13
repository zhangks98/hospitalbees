package tomorrow.ntu.edu.sg.hospitalbees;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ClinicList extends AppCompatActivity {

    RecyclerView clinicRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);

        clinicRV = (RecyclerView) findViewById(R.id.clinic_recycler_view);
        clinicRV.setHasFixedSize(true);
        clinicRV.setLayoutManager(new LinearLayoutManager(this));
        clinicRV.setAdapter(new ClinicAdapter(this));
    }
}
