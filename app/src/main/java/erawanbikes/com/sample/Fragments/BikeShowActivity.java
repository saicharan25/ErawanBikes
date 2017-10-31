package erawanbikes.com.sample.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import erawanbikes.com.sample.R;

/**
 * Created by acer on 10/24/2017.
 */

public class BikeShowActivity extends Activity {
    TextView bike_name, starttrip_time, endtrip_time, pickup_location_point, weekday_price, weekend_price;
    Button confirm_ride;
    String weekday, weekend, bikename, starttrip, endtrip, picklocation;
    ImageView bike;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_show);

        bike = (ImageView) findViewById(R.id.bike);
        bike_name = (TextView) findViewById(R.id.bike_name);
        weekday_price = (TextView) findViewById(R.id.weekday_price);
        weekend_price = (TextView) findViewById(R.id.weekend_price);
        starttrip_time = (TextView) findViewById(R.id.starttrip_time);
        endtrip_time = (TextView) findViewById(R.id.endtrip_time);
        pickup_location_point = (TextView) findViewById(R.id.pickup_location_point);


        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            bikename = arguments.getString("bikename");
            weekday = arguments.getString("weekday_price");
            weekend = arguments.getString("weekend_price");
            starttrip = arguments.getString("from_date");
            endtrip = arguments.getString("to_date");
            picklocation = arguments.getString("location");
        }
        bike_name.setText(bikename);
        weekday_price.setText("₹" + weekday + " /hr.");
        weekend_price.setText("₹" + weekend + " /hr.");
        starttrip_time.setText(starttrip);
        endtrip_time.setText(endtrip);
        pickup_location_point.setText(picklocation);
    }

}
