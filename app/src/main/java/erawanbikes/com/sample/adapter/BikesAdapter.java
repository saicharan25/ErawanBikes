package erawanbikes.com.sample.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.net.URL;
import java.util.List;
import erawanbikes.com.sample.Fragments.AllBikesListActivity;
import erawanbikes.com.sample.Fragments.BikeShowActivity;
import erawanbikes.com.sample.Fragments.UserBookingActivity;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.Bikes;
import erawanbikes.com.sample.R;
import static erawanbikes.com.sample.Fragments.AllBikesListActivity.mContext;

/**
 * Created by acer on 10/18/2017.
 */

public class BikesAdapter  extends RecyclerView.Adapter<BikesAdapter.ViewHolder> {
    private List<Bikes> bikeitems;
    private Context context;

    public BikesAdapter( Context context,List<Bikes> bikeitems) {
        this.bikeitems = bikeitems;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bikes_list_row, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            String image = bikeitems.get(position).getBike_image();
        viewHolder.bike_name.setText(bikeitems.get(position).getBike_model());
        viewHolder.weekday_price.setText("₹"  +  bikeitems.get(position).getWeekDay_price() +"/hr.");
        viewHolder.weekend_price.setText("₹"  +  bikeitems.get(position).getWeekEnd_price() +"/hr.");

        Log.d("Image","image: "+bikeitems.get(position).getBike_image());
        Log.d("Image1","image1: "+image);

       // viewHolder.bike_image.setImageResource(bikeitems.get(position).getBike_image());

        Picasso.with(AllBikesListActivity.mContext).load("http://erawanbikes.com/api/bikes"
                + bikeitems.get(position).getBike_image()).into(viewHolder.bike_image);

        viewHolder.bike_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Card clicked clicked",Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                bundle.putString("bikename",bikeitems.get(position).getBike_model());
                bundle.putString("bikeid",bikeitems.get(position).getBike_id());
                bundle.putString("bike_image",bikeitems.get(position).getBike_image());
                bundle.putString("weekday_price",bikeitems.get(position).getWeekDay_price());
                bundle.putString("weekend_price",bikeitems.get(position).getWeekEnd_price());
                bundle.putString("from_date",Helper.getLocalValue(context, "from_date"));
                bundle.putString("to_date",Helper.getLocalValue(context, "to_date"));
                bundle.putString("location",Helper.getLocalValue(context, "location"));

                Log.d("from_date",Helper.getLocalValue(context, "from_date"));
                Log.d("to_date",Helper.getLocalValue(context, "to_date"));
                Log.d("location", Helper.getLocalValue(context, "location"));

                Intent i = new Intent(context, BikeShowActivity.class);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        viewHolder.bike_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bookbutton clicked clicked",Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                bundle.putString("bikename",bikeitems.get(position).getBike_model());
                bundle.putString("bike_id",bikeitems.get(position).getBike_id());
              //  bundle.putString("bike_image",bikeitems.get(position).getBike_image());
                bundle.putString("weekday_price",bikeitems.get(position).getWeekDay_price());
                bundle.putString("weekend_price",bikeitems.get(position).getWeekEnd_price());
                bundle.putString("from_date",Helper.getLocalValue(context, "from_date"));
                bundle.putString("to_date",Helper.getLocalValue(context, "to_date"));
                bundle.putString("location",Helper.getLocalValue(context, "location"));

                Log.d("from_date",Helper.getLocalValue(context, "from_date"));
                Log.d("to_date",Helper.getLocalValue(context, "to_date"));
                Log.d("location", Helper.getLocalValue(context, "location"));

                Intent i = new Intent(context, UserBookingActivity.class);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

    }
    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView bike_card;
        public TextView bike_name,weekday_price,weekend_price,starttrip_time,endtrip_time;
        public ImageView bike_image;
        public Button bike_book;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            bike_card = (CardView) itemLayoutView.findViewById(R.id.bike_card);
            bike_name = (TextView) itemLayoutView.findViewById(R.id.bike_name);
            bike_image = (ImageView) itemLayoutView.findViewById(R.id.bike_image);
            weekday_price = (TextView) itemLayoutView.findViewById(R.id.weekday_price);
            weekend_price = (TextView) itemLayoutView.findViewById(R.id.weekend_price);
            starttrip_time = (TextView) itemLayoutView.findViewById(R.id.starttrip_time);
            endtrip_time = (TextView) itemLayoutView.findViewById(R.id.endtrip_time);
            bike_book = (Button) itemLayoutView.findViewById(R.id.bike_book);
        }
    }
    // Return the size of your bikeitems (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bikeitems.size();
    }
}



