package tomorrow.ntu.edu.sg.hospitalbees;


import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListener itemClickListener;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}


public class ClinicAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    Context context;
    String clinics[] = {"Ng Teng Fong Hospital", "Fullerton Health"};
    public static String clinicdetails = "ClinicChoice";




    public ClinicAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View clinic = inflater.inflate(R.layout.clinic_card_layout, parent, false);
        ClinicAdapter.Item clinicitem = new ClinicAdapter.Item(clinic);

        return clinicitem;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ((ClinicAdapter.Item)holder).clinicname.setText(clinics[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(clinicdetails, clinics[position]).apply();

                Intent intent = new Intent(context, ChooseClinic.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()  {
        return clinics.length;
    }

    public class Item extends RecyclerViewHolder {

        TextView clinicname;

        public Item(View itemView) {
            super(itemView);
            clinicname = (TextView) itemView.findViewById(R.id.clinic_title);

        }
    }


}
