package erawanbikes.com.sample.Fragments;

import android.app.ProgressDialog;
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
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.Data;
import erawanbikes.com.sample.R;
import erawanbikes.com.sample.adapter.MyRidesAdapter;
/**
 * Created by acer on 10/17/2017.
 */

public class MyRidesFragement extends Fragment {

    private String response;
    RecyclerView recyclerView;
    MyRidesAdapter rvAdapter;
    List<Data> dataitems = new ArrayList<Data>();
    String location, from_time, to_time;
    public static final String url = "http://erawanbikes.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            location = bundle.getString("location");
            from_time = bundle.getString("from_date");
            to_time = bundle.getString("to_date");
        }
        return inflater.inflate(R.layout.fragment_myrides, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_myrides);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        new MyAsyncTask().execute(url);

      /*  Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErawanbikesServices service = retrofit.create(ErawanbikesServices.class);

        Call<Data> call = service.my_rides(Helper.getAuthenticationHeader(getActivity()));
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                rvAdapter = new MyRidesAdapter(getActivity(), dataitems);
                try {
                    JSONObject jsonobj = new JSONObject(response);
                    JSONArray posts = jsonobj.optJSONArray("data");
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.optJSONObject(i);
                        Data bikes = new Data();
                        bikes.setBike_model_name(post.getString("bike_model_name"));
                        bikes.setTotal_price(post.getString("total_price"));
                        bikes.setTrip_status(post.getString("trip_status"));
                        bikes.setWarehouse_name(post.getString("warehouse_name"));
                        dataitems.add(bikes);
                    }
                    recyclerView.setAdapter(rvAdapter);
                } catch (JSONException e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog;

        MyAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                this.asyncDialog = new ProgressDialog(getActivity());
                this.asyncDialog.setMessage(getActivity().getString(R.string.loading));
                this.asyncDialog.show();
                MyRidesFragement.this.response = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(String aDouble) {
            super.onPostExecute(aDouble);
            try {
                this.asyncDialog.dismiss();
                rvAdapter = new MyRidesAdapter(getActivity(), dataitems);
                try {
                    JSONObject response = new JSONObject(aDouble);
                    JSONArray posts = response.optJSONArray("data");
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject post = posts.optJSONObject(i);
                        Data bikes = new Data();
                        bikes.setBike_model_name(post.getString("bike_model_name"));
                        bikes.setTotal_price(post.getString("total_price"));
                        bikes.setTrip_status(post.getString("trip_status"));
                        bikes.setWarehouse_name(post.getString("location"));
                        dataitems.add(bikes);
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
                MyRidesFragement.this.response = postData("http://erawanbikes.com/api/bookings");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MyRidesFragement.this.response;
        }
    }
    private String postData(String serviceURL) {
        HttpClient httpclient = new DefaultHttpClient();
        String responseString = "";
        HttpPost httppost = new HttpPost(serviceURL);
        try {

            httppost.setHeader("Content-Type", "application/json");
            httppost.setHeader("Authorization", Helper.getAuthenticationHeader(getActivity()));
            responseString = EntityUtils.toString(httpclient.execute(httppost).getEntity(), CharEncoding.UTF_8);
            Log.i("Tag", "postData: " + responseString);
            return responseString;
        } catch (ClientProtocolException e) {
            return responseString;
        } catch (IOException e2) {
            return responseString;
        }
    }
}
