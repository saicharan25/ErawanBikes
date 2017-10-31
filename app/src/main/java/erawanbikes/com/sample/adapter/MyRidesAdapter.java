package erawanbikes.com.sample.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import erawanbikes.com.sample.Models.Data;
import erawanbikes.com.sample.R;

/**
 * Created by acer on 10/26/2017.
 */

public class MyRidesAdapter  extends RecyclerView.Adapter<MyRidesAdapter.ViewHolder> {
    private List<Data> dataitems;
    private Context context;

    public MyRidesAdapter( Context context,List<Data> dataitems) {
        this.dataitems = dataitems;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myrides_layout, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.ride_date.setText(dataitems.get(position).getCreated_at());
        viewHolder.ride_bike.setText(dataitems.get(position).getBike_model_name());
        viewHolder.ride_price.setText("₹"  + dataitems.get(position).getTotal_price());
        viewHolder.ride_location.setText(dataitems.get(position).getWarehouse_name());
        viewHolder.ride_status.setText(dataitems.get(position).getTrip_status());

    }
    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView ride_card;
        public TextView ride_date,ride_bike,ride_price,ride_location,ride_status;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            ride_card = (CardView) itemLayoutView.findViewById(R.id.ride_card);
            ride_location = (TextView) itemLayoutView.findViewById(R.id.ride_location);
            ride_date = (TextView) itemLayoutView.findViewById(R.id.ride_date);
            ride_bike = (TextView) itemLayoutView.findViewById(R.id.ride_bike);
            ride_price = (TextView) itemLayoutView.findViewById(R.id.ride_price);
            ride_status = (TextView) itemLayoutView.findViewById(R.id.ride_status);

        }
    }
    // Return the size of your dataitems (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataitems.size();
    }
}


