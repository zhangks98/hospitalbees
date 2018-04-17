package tomorrow.ntu.edu.sg.hospitalbees.adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import tomorrow.ntu.edu.sg.hospitalbees.R;

public class BookingHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String[] Items = {"NUH", "NUH", "NUH", "Ng Teng Fong Hospital", "Jurong Polyclinic", "SGH", "NUH", "NUH", "Ng Teng Fong Hospital", "Jurong Polyclinic", "SGH", "SGH"};
    String[] Date = {"31082018", "24072018", "21062018", "20052018", "17042018", "15032018", "13022018", "10012018", "08122017", "05112017", "03102017", "31082017"};



    public BookingHistoryAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.recent_card_layout,parent,false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).recentcvtitletext.setText(Items[position]);
        ((Item)holder).recentcvdatetext.setText(Date[position]);




    }

    @Override
    public int getItemCount() {
        return Items.length;
    }

    public static class Item extends RecyclerView.ViewHolder {
        TextView recentcvtitletext;
        TextView recentcvdatetext;



        public Item(View itemView) {
            super(itemView);
            recentcvtitletext = (TextView) itemView.findViewById(R.id.recent_cv_title);
            recentcvdatetext = (TextView) itemView.findViewById(R.id.recent_cv_date);


        }
    }
}
