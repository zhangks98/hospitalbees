package tomorrow.ntu.edu.sg.hospitalbees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tomorrow.ntu.edu.sg.hospitalbees.adaptors.FaqAdapter;

public class FAQ extends AppCompatActivity implements View.OnClickListener{

    RecyclerView faqRV;
    TextView quickSelect;
    Button fever, cough, diarrhoea, headache, sorethroat ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faqRV = (RecyclerView) findViewById(R.id.faq_recycler_view);
        faqRV.setLayoutManager(new LinearLayoutManager(this));
        faqRV.setAdapter(new FaqAdapter(this));

        quickSelect = (TextView) findViewById(R.id.quickSelect);
        fever = (Button) findViewById(R.id.fever);
        cough = (Button) findViewById(R.id.cough);
        diarrhoea = (Button) findViewById(R.id.diarrhoea);
        headache = (Button) findViewById(R.id.headache);
        sorethroat = (Button) findViewById(R.id.sorethroat);

        cough.setOnClickListener(this);
    }


    public void backButton(View view) {
        startActivity(new Intent(this, HomePage.class));

    }

    public void searchButton(View view) {
    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.cough){
            quickSelect.setVisibility(View.GONE);
            fever.setVisibility(View.GONE);
            cough.setVisibility(View.GONE);
            diarrhoea.setVisibility(View.GONE);
            headache.setVisibility(View.GONE);
            sorethroat.setVisibility(View.GONE);
            faqRV.setVisibility(View.VISIBLE);


        }
    }
}
