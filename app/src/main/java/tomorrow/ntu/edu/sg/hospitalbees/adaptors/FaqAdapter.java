package tomorrow.ntu.edu.sg.hospitalbees.adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tomorrow.ntu.edu.sg.hospitalbees.R;


/**
 * The type Faq adapter.
 */
public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * The Context.
     */
    Context context;
    /**
     * The Author.
     */
    int[] author = {R.drawable.userimage, R.drawable.userimage, R.drawable.userimage, R.drawable.userimage, R.drawable.userimage};
    /**
     * The Author name.
     */
    String[] author_name = {"Dr. Kaishuo Zhang", "Dr. Fuhank", "Dr. Vansh", "Dr. Chris", "Dr. Gerald"};
    /**
     * The Answered date.
     */
    String[] answered_date = {"Answered Jan 20, 2018", "Answered Jan 14, 2018", "Answered Jan 06, 2018", "Answered Jan 03, 2018", "Answered Jan 02, 2018"};
    /**
     * The Number of views.
     */
    String[] number_of_views = {"312 views", "125 views", "23 views", "11 views", "9 views"};
    /**
     * The Faq content.
     */
    String[] faq_content = {
            "The most suitable syrup is Honey. If a cough persists for longer than 2 weeks, it is recommended to visit a doctor.",
            "Do not drink cold drinks and take proper care. Cough syrups might have a side effect. Complete any antibiotic course you start.",
            "Reduce in intake of cold or icy products to recover faster.",
            "Do not smoke cigarette as it can result in the cough worsening",
            "Take some cough lozenges and follow the dosage accordingly. Do not take too much. If it starts to worsen, visit a doctor immediately."

    };

    /**
     * Instantiates a new Faq adapter.
     *
     * @param context the context
     */
    public FaqAdapter(Context context){
        this.context = context;
    }
    
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View faq = inflater.inflate(R.layout.faq_card_layout,parent,false);
        FaqAdapter.Item faqitem = new FaqAdapter.Item(faq);
        return faqitem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FaqAdapter.Item)holder).faqauthor.setImageResource(author[position]);
        ((FaqAdapter.Item)holder).faqauthorname.setText(author_name[position]);
        ((FaqAdapter.Item)holder).faqanswereddate.setText(answered_date[position]);
        ((FaqAdapter.Item)holder).faqnumberofviews.setText(number_of_views[position]);
        ((FaqAdapter.Item)holder).faqcontent.setText(faq_content[position]);
    }

    @Override
    public int getItemCount()  {
        return author_name.length;
    }

    /**
     * The type Item.
     */
    public class Item extends RecyclerView.ViewHolder {

        /**
         * The Faqauthor.
         */
        ImageView faqauthor;
        /**
         * The Faqauthorname.
         */
        TextView faqauthorname;
        /**
         * The Faqanswereddate.
         */
        TextView faqanswereddate;
        /**
         * The Faqnumberofviews.
         */
        TextView faqnumberofviews;
        /**
         * The Faqcontent.
         */
        TextView faqcontent;

        /**
         * Instantiates a new Item.
         *
         * @param itemView the item view
         */
        public Item(View itemView) {
            super(itemView);
            faqauthor = (ImageView) itemView.findViewById(R.id.user_image);
            faqauthorname = (TextView) itemView.findViewById(R.id.faq_author);
            faqanswereddate = (TextView) itemView.findViewById(R.id.faq_answered_date);
            faqnumberofviews = (TextView) itemView.findViewById(R.id.faq_number_views);
            faqcontent = (TextView) itemView.findViewById(R.id.faq_content);
        }
    }
}
