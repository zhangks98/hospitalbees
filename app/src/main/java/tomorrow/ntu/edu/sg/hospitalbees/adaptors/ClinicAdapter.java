package tomorrow.ntu.edu.sg.hospitalbees.adaptors;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tomorrow.ntu.edu.sg.hospitalbees.R;
import tomorrow.ntu.edu.sg.hospitalbees.models.Hospital;

//import static tomorrow.ntu.edu.sg.hospitalbees.LocationDuration.travellist;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicAdapterViewHolder> {


    private Hospital[] mHospitalList;

    private final ClinicAdapterOnClickHandler mClickHandler;

    public interface ClinicAdapterOnClickHandler {
        void onHospitalItemClick(Hospital chosenHospital);
    }

    public class ClinicAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mClinicTitleTextView;
        TextView mClinicTravelTimeTextView;
        TextView mClinicQueueLengthTextView;
        TextView mClinicTotalETATextView;

        public ClinicAdapterViewHolder(View itemView) {
            super(itemView);

            mClinicTitleTextView = itemView.findViewById(R.id.clinic_title);
            mClinicQueueLengthTextView = itemView.findViewById(R.id.clinic_queue_edit);
            mClinicTravelTimeTextView = itemView.findViewById(R.id.clinic_travel_edit);
            mClinicTotalETATextView = itemView.findViewById(R.id.clinic_total_edit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Hospital chosenHospital = mHospitalList[clickedPosition];
            mClickHandler.onHospitalItemClick(chosenHospital);
        }
    }

    public ClinicAdapter(ClinicAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    public void setHospitalList (Hospital[] hospitals) {
        this.mHospitalList = hospitals;
        notifyDataSetChanged();
    }

    @Override
    public ClinicAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View clinicCardView = inflater.inflate(R.layout.clinic_card_layout, parent, false);
        return new ClinicAdapterViewHolder(clinicCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicAdapterViewHolder holder, int position) {
        Hospital thisHospital = mHospitalList[position];
        holder.mClinicTitleTextView.setText(thisHospital.getName());
        holder.mClinicQueueLengthTextView.setText(String.valueOf(thisHospital.getQueueLength()));
        holder.mClinicTravelTimeTextView.setText(String.valueOf(thisHospital.getTravelTime()) + " Min");
        holder.mClinicTotalETATextView.setText(String.valueOf(thisHospital.getTotalETA()) + " Min");
    }

    @Override
    public int getItemCount()  {
        return (mHospitalList == null) ? 0 : mHospitalList.length;
    }


}
