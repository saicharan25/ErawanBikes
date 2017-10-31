package erawanbikes.com.sample.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.Bikes;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.PostData;
import erawanbikes.com.sample.adapter.BikesAdapter;

/**
 * Created by acer on 10/23/2017.
 */

public class AllBikesListActivity extends Activity {
    String location, from_time, to_time;
    RecyclerView recyclerView;
    BikesAdapter rvAdapter;
    public static Context mContext;
    List<Bikes> bikes_list = new ArrayList<Bikes>();
    public String response;
    private TextView bike_name, pickup_time, delivery_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bikelist);

        mContext =AllBikesListActivity.this;
        Intent in = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            location = bundle.getString("location");
            from_time = bundle.getString("from_date");
            to_time = bundle.getString("to_date");
        }
        bike_name = (TextView) findViewById(R.id.bike_name);
        bike_name.setText(location);
        pickup_time = (TextView) findViewById(R.id.pickup_time);
        pickup_time.setText(from_time);
        delivery_time = (TextView) findViewById(R.id.delivery_time);
        delivery_time.setText(to_time);

        ImageView backimage = (ImageView) findViewById(R.id.backimage);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_bikes);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new MyAsyncTask().execute(new String[]{""});
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog;
        ArrayList paramsList = new ArrayList();

        MyAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                this.asyncDialog = new ProgressDialog(getApplicationContext());
                this.asyncDialog.setMessage(getApplicationContext().getString(R.string.loading));
                this.asyncDialog.show();
                response = "";
                this.paramsList.add(new BasicNameValuePair("location", location));
                this.paramsList.add(new BasicNameValuePair("from_date", from_time));
                this.paramsList.add(new BasicNameValuePair("to_date", to_time));

                Helper.storeLocally("location", location, AllBikesListActivity.this);
                Log.d("location local", location);
                Helper.storeLocally("from_date", from_time, AllBikesListActivity.this);
                Helper.storeLocally("to_date", to_time, AllBikesListActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(String aDouble) {
            super.onPostExecute(aDouble);
            try {
                this.asyncDialog.dismiss();
                rvAdapter = new BikesAdapter(getApplicationContext(), bikes_list);
                try {
                    JSONObject response = new JSONObject(aDouble);
                    JSONArray posts = response.optJSONArray("data");
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.optJSONObject(i);
                        Bikes bikes = new Bikes();
                        bikes.setBike_model(post.getString("bike_model"));
                        bikes.setBike_id(post.getString("bike_id"));
                        bikes.setWeekDay_price(post.getString("weekDay_price"));
                        bikes.setWeekEnd_price(post.getString("weekEnd_price"));
                        //  bikes.setBike_image(post.getString("bike_image"));
                        bikes_list.add(bikes);
                    }
                    recyclerView.setAdapter(rvAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            try {
                response = PostData.postData(this.paramsList, "http://erawanbikes.com/api/bikes");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AllBikesListActivity.mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return response;
        }
    }
}

