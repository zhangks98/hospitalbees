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

/**
 * The type Clinic adapter.
 */
public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicAdapterViewHolder> {


    private Hospital[] mHospitalList;

    private final ClinicAdapterOnClickHandler mClickHandler;

    /**
     * The interface Clinic adapter on click handler.
     */
    public interface ClinicAdapterOnClickHandler {
        /**
         * On hospital item click.
         *
         * @param chosenHospital the chosen hospital
         */
        void onHospitalItemClick(Hospital chosenHospital);
    }

    /**
     * The type Clinic adapter view holder.
     */
    public class ClinicAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The M clinic title text view.
         */
        TextView mClinicTitleTextView;
        /**
         * The M clinic travel time text view.
         */
        TextView mClinicTravelTimeTextView;
        /**
         * The M clinic queue length text view.
         */
        TextView mClinicQueueLengthTextView;
        /**
         * The M clinic total eta text view.
         */
        TextView mClinicTotalETATextView;

        /**
         * Instantiates a new Clinic adapter view holder.
         *
         * @param itemView the item view
         */
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

    /**
     * Instantiates a new Clinic adapter.
     *
     * @param clickHandler the click handler
     */
    public ClinicAdapter(ClinicAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    /**
     * Sets hospital list.
     *
     * @param hospitals the hospitals
     */
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
