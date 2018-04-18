package tomorrow.ntu.edu.sg.hospitalbees;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;

import static tomorrow.ntu.edu.sg.hospitalbees.LocationDuration.travellist;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



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


public class ClinicAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    Context context;

    String clinics[] = {"Ng Teng Fong Hospital", "NTU Fullerton Health", "Jurong Polyclinic"};

    String traveltime[];
    String queuelength[] = {"5", "2", "7"};

    public static String clinicdetails = "ClinicChoice";
    public static String queuetime = "QueueTime";
    public static String traveldetails = "TravelDetails";

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

    public String getTotalTime(String traveltime, String queuelength) {
        int traveltimeint = Integer.parseInt(traveltime);
        int queuelengthint = Integer.parseInt(queuelength);
        int totaltimeint = traveltimeint + (queuelengthint * 7);
        String totaltime = Integer.toString(totaltimeint);
        return totaltime;
    }
    public String getQueuetime(String queuelength) {
        return Integer.toString(Integer.parseInt(queuelength) * 7);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ((ClinicAdapter.Item)holder).clinicname.setText(clinics[position]);
        ((ClinicAdapter.Item)holder).clinictraveltime.setText(traveltime[position] + " minutes");
        ((ClinicAdapter.Item)holder).clinicqueuelength.setText(queuelength[position] + " person");
        ((ClinicAdapter.Item)holder).clinictotaltime.setText(getTotalTime(traveltime[position], queuelength[position]) + " minutes");
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(traveldetails, traveltime[position]).apply();
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(clinicdetails, clinics[position]).apply();
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(queuetime, getQueuetime(queuelength[position])).apply();

                Intent intent = new Intent(context, BookingDetails.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount()  {
        String temp = PreferenceManager.getDefaultSharedPreferences(context).getString(travellist, "QueueNotFound");
        traveltime = temp.split(",");
        return clinics.length;
    }

    public class Item extends RecyclerViewHolder {

        TextView clinicname;
        TextView clinictraveltime;
        TextView clinicqueuelength;
        TextView clinictotaltime;

        public Item(View itemView) {
            super(itemView);
            clinicname = (TextView) itemView.findViewById(R.id.clinic_title);
            clinictraveltime = (TextView) itemView.findViewById(R.id.clinic_travel_edit);
            clinicqueuelength = (TextView) itemView.findViewById(R.id.clinic_queue_edit);
            clinictotaltime = (TextView) itemView.findViewById(R.id.clinic_total_edit);

        }
    }


}
