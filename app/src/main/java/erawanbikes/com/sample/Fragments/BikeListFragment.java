package erawanbikes.com.sample.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.Bikes;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.Retrofitservices.ErawanbikesServices;
import erawanbikes.com.sample.Retrofitservices.PostData;
import erawanbikes.com.sample.adapter.BikesAdapter;


/**
 * Created by acer on 10/19/2017.
 */

public class BikeListFragment extends Fragment {

    private String response;
    RecyclerView recyclerView;
    BikesAdapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManger;
    List<Bikes> bikes_list = new ArrayList<Bikes>();
    String location,from_time,to_time;

    public BikeListFragment() {
        // Required empty public constructor
    }

    public static BikeListFragment newInstance() {
        return new BikeListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
             location = bundle.getString("location");
             from_time = bundle.getString("from_date");
             to_time = bundle.getString("to_date");
        }
        return inflater.inflate(R.layout.fragment_bikes_all, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        new MyAsyncTask().execute(new String[]{""});

    }
    private class MyAsyncTask  extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog;
        ArrayList paramsList = new ArrayList();

        MyAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                this.asyncDialog = new ProgressDialog(getActivity());
                this.asyncDialog.setMessage(getActivity().getString(R.string.loading));
                this.asyncDialog.show();
                response = "";
                this.paramsList.add(new BasicNameValuePair("location", location));
                this.paramsList.add(new BasicNameValuePair("from_date",from_time));
                this.paramsList.add(new BasicNameValuePair("to_date", to_time));

                Helper.storeLocally("location", location, getActivity());
                Log.d("location",location);
                Helper.storeLocally("from_date", from_time, getActivity());
                Helper.storeLocally("to_date", to_time, getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(String aDouble) {
            super.onPostExecute(aDouble);
            try {
                this.asyncDialog.dismiss();
                rvAdapter = new BikesAdapter(getActivity(),bikes_list);
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
                    //   Helper.showSnackBar(LoginFragment.this.getView(),"Enter Correct Details!");
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
            }
            return response;
        }
    }
}
