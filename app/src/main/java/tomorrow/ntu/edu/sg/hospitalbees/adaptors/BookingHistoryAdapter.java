package tomorrow.ntu.edu.sg.hospitalbees.adaptors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import tomorrow.ntu.edu.sg.hospitalbees.R;
import tomorrow.ntu.edu.sg.hospitalbees.models.Booking;


public class BookingHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Booking[] mBookingHistoryList;


    public BookingHistoryAdapter () {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.booking_history_list_item, parent, false);
        return new BookingHistoryViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Booking historyItem = mBookingHistoryList[position];
        ((BookingHistoryViewHolder) holder).mHospitalNameTextView.setText(historyItem.getHospitalName());
        ((BookingHistoryViewHolder) holder).mBookingStatusTextView.setText(historyItem.getStatus());
        DateFormat formatter = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        ((BookingHistoryViewHolder) holder).mBookingDateTextView.setText(formatter.format(historyItem.getBookingTime()));
    }


    @Override
    public int getItemCount() {
        return (mBookingHistoryList == null) ? 0 : mBookingHistoryList.length;
    }


    public static class BookingHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView mHospitalNameTextView;

        TextView mBookingDateTextView;

        TextView mBookingStatusTextView;


        public BookingHistoryViewHolder(View itemView) {
            super(itemView);
            mHospitalNameTextView = itemView.findViewById(R.id.tv_history_hospital_name);
            mBookingDateTextView = itemView.findViewById(R.id.tv_history_booking_date);
            mBookingStatusTextView = itemView.findViewById(R.id.tv_history_booking_status);
        }
    }
}
